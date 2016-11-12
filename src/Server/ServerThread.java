package Server;

import Control.User_favorited_itemAction;
import Control.User_infoAction;
import Control.User_subscribed_channelAction;
import DAO.ChannelDao;
import DAO.User_favorited_itemDao;
import DAO.User_infoDao;
import DAO.User_subscribed_channelDao;
import Module.DataBase.ServserDB.Channel;
import Module.DataBase.ServserDB.User_favorited_item;
import Module.DataBase.ServserDB.User_info;
import Module.DataBase.ServserDB.User_subscribed_channel;
import Module.transfer.Reply;
import Module.transfer.Request;
import Utils.PubString;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * Created by anyuan on 2016/11/10.
 */
public class ServerThread extends Thread {

    Socket socket = null;
    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream is;
        OutputStream os;
        try {
            is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            //接受来自客户端的请求并进行处理
            Request request = (Request) ois.readObject();
            //***重要函数，后面的函数都是对这个处理请求函数的细化
            Reply reply = doRequest(request);
            //服务器输出流，并输出处理结果给客户端
            os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(reply);
            oos.flush();
            socket.close();
            System.out.println(reply.getReplyType());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /*
    服务器处理函数
     */

    /**
     * doRequest:服务器端处理客户端来的请求,并且产生Reply
     * @param request
     * @return
     */
    private static Reply doRequest(Request request) throws SQLException, IOException {
        Reply reply = null;
        switch (request.getRequestType()){
            case PubString.SYNC_TYPE:
                reply = doSync(request);
                break;
            case PubString.SYNC_USER_CHANNEL_TYPE:
                reply = doSyncUserChannel(request);
                break;
            case PubString.SYNC_USER_INFO_TYPE:
                reply = doSyncUserInfo(request);
                break;
            case PubString.SYNC_USER_ITEM_TYPE:
                reply = doSyncUserItem(request);
                break;
            case PubString.QUERY_TYPE:
                reply = doQuery(request);
                break;
            case PubString.ADD_TYPE:
                reply = doAdd(request);
                break;
            case PubString.UPDATE_TYPE:
                reply = doUpdate(request);
                break;
            case PubString.DELETE_TYPE:
                reply = doDelete(request);
                break;
            default:
                //default action
        }
        return reply;
    }

    /**
     * doSyncUserItem:执行用户收藏文章的同步操作
     * @param request
     * @return
     * @throws SQLException
     */
    private static Reply doSyncUserItem(Request request) throws SQLException {
        Reply reply = new Reply();
        String user_ID = (String) request.getParams().get(0).get(PubString.OP_USER_ID);
        List<User_favorited_item> favItems = User_favorited_itemAction.QueryAllFItemOfUser(user_ID);
        Map<String,Object> content = new HashMap<>();
        content.put(PubString.TABLE_NAME,request.getTable());
        content.put(PubString.PROCESSED_RESULT,favItems);
        reply.setReplyType(PubString.SYNC_USER_ITEM_TYPE);
        reply.setContent(content);
        reply.setSuccess(true);
        return reply;
    }

    /**
     * doSyncUserInfo:执行用户个人信息的同步操作
     * @param request
     * @return
     * @throws SQLException
     */
    private static Reply doSyncUserInfo(Request request) throws SQLException, IOException {
        Reply reply = new Reply();
        String user_ID = (String) request.getParams().get(0).get(PubString.OP_USER_ID);
        User_info info = User_infoAction.QueryInfoOfUser(user_ID);
        Map<String,Object> content = new HashMap<>();
        content.put(PubString.TABLE_NAME,request.getTable());
        content.put(PubString.PROCESSED_RESULT,info);
        reply.setReplyType(PubString.SYNC_USER_INFO_TYPE);
        reply.setContent(content);
        reply.setSuccess(true);
        return reply;
    }

    /**
     * doSyncUserChannel:执行用户订阅频道的同步操作
     * @param request
     * @return
     * @throws SQLException
     */
    private static Reply doSyncUserChannel(Request request) throws SQLException {
        Reply reply = new Reply();
        String user_ID = (String) request.getParams().get(0).get(PubString.OP_USER_ID);
        List<User_subscribed_channel> subChannels = User_subscribed_channelAction.QueryAllSChannelOfUser(user_ID);
        Map<String,Object> content = new HashMap<>();
        content.put(PubString.TABLE_NAME,request.getTable());
        content.put(PubString.PROCESSED_RESULT,subChannels);
        reply.setReplyType(PubString.SYNC_USER_CHANNEL_TYPE);
        reply.setContent(content);
        reply.setSuccess(true);
        return reply;
    }

    /**
     * doSync:用户所有信息同步
     * @param request
     * @return
     * @throws SQLException
     */
    private static Reply doSync(Request request) throws SQLException, IOException {
        Reply replyUserInfo = doSyncUserInfo(request);
        Reply replyUserChannel = doSyncUserChannel(request);
        Reply replyUserItem = doSyncUserItem(request);
        Object userInfo = replyUserInfo.getContent().get(PubString.PROCESSED_RESULT);
        Object userChannel = replyUserChannel.getContent().get(PubString.PROCESSED_RESULT);
        Object userItem = replyUserItem.getContent().get(PubString.PROCESSED_RESULT);
        Map<String,Object> content = new HashMap<>();
        content.put(PubString.PROCESSED_RESULT_OF_INFO,userInfo);
        content.put(PubString.PROCESSED_RESULT_OF_CHANNEL,userChannel);
        content.put(PubString.PROCESSED_RESULT_OF_ITEM,userItem);
        Reply reply = new Reply(PubString.SYNC_TYPE,true,content);
        return reply;
    }

    /**
     * doQuery:对数据库进行query操作，并且把查询结果封装成Reply
     * @param request
     * @return
     * @throws SQLException
     */
    private static Reply doQuery(Request request) throws SQLException, IOException {
        Reply reply = null;
        switch (request.getTable()){
            case PubString.TABLE_CHANNEL:
                reply = doQueryChannel(request);
                break;
            case PubString.TABLE_USER_FAVORITED_ITEM:
                reply = doQueryUserFavItem(request);
                break;
            case PubString.TABLE_USER_INFO:
                reply = doQueryUserInfo(request);
                break;
            case PubString.TABLE_USER_SUBSCRIBED_CHANNEL:
                reply = doQueryUserSubChannel(request);
                break;
            default:
        }
        return reply;
    }

    /**
     * doQueryUserSubChannel:具体对表user_subscribed_channel的query操作
     * @param request
     * @return
     * @throws SQLException
     */
    private static Reply doQueryUserSubChannel(Request request) throws SQLException {
        //查询数据库操作
        List<User_subscribed_channel> userSubChannels = User_subscribed_channelDao.Query(request.getParams());
        //封装Reply
        Reply reply = new Reply();
        Map<String,Object> content = new HashMap<>();
        content.put(PubString.TABLE_NAME,request.getTable());
        content.put(PubString.PROCESSED_RESULT,userSubChannels);
        reply.setContent(content);
        reply.setReplyType(PubString.QUERY_TYPE);
        reply.setSuccess(true);
        return reply;
    }

    /**
     *doQueryUserInfo:具体对表user_info的query操作
     * @param request
     * @return
     * @throws SQLException
     */
    private static Reply doQueryUserInfo(Request request) throws SQLException, IOException {
        //查询数据库操作
        List<User_info> userInfos = User_infoDao.Query(request.getParams());
        //封装Reply
        Reply reply = new Reply();
        Map<String,Object> content = new HashMap<>();
        content.put(PubString.TABLE_NAME,request.getTable());
        content.put(PubString.PROCESSED_RESULT,userInfos);
        reply.setContent(content);
        reply.setReplyType(PubString.QUERY_TYPE);
        reply.setSuccess(true);
        return reply;
    }

    /**
     * doQueryUserFavItem:具体对表user_favorited_item的query操作
     * @param request
     * @return
     * @throws SQLException
     */
    private static Reply doQueryUserFavItem(Request request) throws SQLException {
        //查询数据库操作
        List<User_favorited_item> userFavItems = User_favorited_itemDao.Query(request.getParams());
        //封装Reply
        Reply reply = new Reply();
        Map<String,Object> content = new HashMap<>();
        content.put(PubString.TABLE_NAME,request.getTable());
        content.put(PubString.PROCESSED_RESULT,userFavItems);
        reply.setContent(content);
        reply.setReplyType(PubString.QUERY_TYPE);
        reply.setSuccess(true);
        return reply;
    }

    /**
     * doQueryChannel:具体对表channel的query操作
     * @param request
     * @return
     * @throws SQLException
     */
    private static Reply doQueryChannel(Request request) throws SQLException {
        //查询数据库操作
        List<Channel> channels = ChannelDao.Query(request.getParams());
        //封装Reply
        Reply reply = new Reply();
        Map<String,Object> content = new HashMap<>();
        content.put(PubString.TABLE_NAME,request.getTable());
        content.put(PubString.PROCESSED_RESULT,channels);
        reply.setContent(content);
        reply.setReplyType(PubString.QUERY_TYPE);
        reply.setSuccess(true);
        return reply;
    }

    /**
     * doAdd:对数据库进行add操作，并且把查询结果封装成Reply
     * @param request
     * @return
     * @throws SQLException
     */
    private static Reply doAdd(Request request) throws SQLException, IOException {
        Reply reply = null;
        switch (request.getTable()){
            case PubString.TABLE_CHANNEL:
                reply = doAddOfChannel(request);
                break;
            case PubString.TABLE_USER_FAVORITED_ITEM:
                reply = doAddOfUserFavItem(request);
                break;
            case PubString.TABLE_USER_INFO:
                reply = doAddOfUserInfo(request);
                break;
            case PubString.TABLE_USER_SUBSCRIBED_CHANNEL:
                reply = doAddOfUserSubChannel(request);
                break;
            default:
                //do nothing

        }
        return reply;
    }

    /**
     * doAddOfUserSubChannel:具体对表user_subscribed_channel的add操作
     * @param request
     * @return
     * @throws SQLException
     */
    private static Reply doAddOfUserSubChannel(Request request) throws SQLException {
        List<Map<String,Object>> userSubChannels = request.getParams();
        for (Map<String, Object> map :
                userSubChannels) {
            User_subscribed_channelDao.addUser_subscribed_channel((User_subscribed_channel) map.get(PubString.SINGLE_ROW));
        }
        Reply reply = new Reply(PubString.ADD_TYPE,true,null);
        return reply;
    }

    /**
     * doAddOfUserInfo:具体对表user_info的add操作
     * @param request
     * @return
     * @throws SQLException
     */
    private static Reply doAddOfUserInfo(Request request) throws SQLException, IOException {
        List<Map<String,Object>> userInfos = request.getParams();
        for (Map<String, Object> map :
                userInfos) {
            User_infoDao.addUser_info((User_info) map.get(PubString.SINGLE_ROW));
        }
        Reply reply = new Reply(PubString.ADD_TYPE,true,null);
        return reply;
    }

    /**
     * doAddOfUserFavItem:具体对表user_favorited_item的add操作
     * @param request
     * @return
     * @throws SQLException
     */
    private static Reply doAddOfUserFavItem(Request request) throws SQLException {
        List<Map<String,Object>> userFavItems = request.getParams();
        for (Map<String, Object> map :
                userFavItems) {
            User_favorited_itemDao.addUser_favorited_item((User_favorited_item) map.get(PubString.SINGLE_ROW));
        }
        Reply reply = new Reply(PubString.ADD_TYPE,true,null);
        return reply;
    }

    /**
     * doAddOfChannel:具体对表channel的add操作
     * @param request
     * @return
     * @throws SQLException
     */
    private static Reply doAddOfChannel(Request request) throws SQLException {
        List<Map<String,Object>> channels = request.getParams();
        for (Map<String, Object> map :
                channels) {
            ChannelDao.addChannel((Channel) map.get(PubString.SINGLE_ROW));
        }
        Reply reply = new Reply(PubString.ADD_TYPE,true,null);
        return reply;
    }


    /**
     * doUpdate:对数据库进行update操作，并且把查询结果封装成Reply
     * @param request
     * @return
     * @throws SQLException
     */
    private static Reply doUpdate(Request request) throws SQLException, IOException {
        Reply reply = null;
        switch (request.getTable()){
            case PubString.TABLE_CHANNEL:
                reply = doUpdateOfChannel(request);
                break;
            case PubString.TABLE_USER_FAVORITED_ITEM:
                reply = doUpdateOfUserFavItem(request);
                break;
            case PubString.TABLE_USER_INFO:
                reply = doUpdateOfUserInfo(request);
                break;
            case PubString.TABLE_USER_SUBSCRIBED_CHANNEL:
                reply = doUpdateOfUserSubChannel(request);
                break;
            default:

        }
        return reply;
    }

    /**
     * doUpdateOfUserSubChannel:具体对表user_subscribed_channel的update操作
     * @param request
     * @return
     * @throws SQLException
     */
    private static Reply doUpdateOfUserSubChannel(Request request) throws SQLException {
        List<Map<String,Object>> userSubChannels = request.getParams();
        for (Map<String, Object> map :
                userSubChannels) {
            User_subscribed_channelDao.updateUser_subscribed_channel((User_subscribed_channel) map.get(PubString.SINGLE_ROW));
        }
        Reply reply = new Reply(PubString.UPDATE_TYPE,true,null);
        return reply;
    }

    /**
     * doUpdateOfUserInfo:具体对表user_info的update操作
     * @param request
     * @return
     * @throws SQLException
     */
    private static Reply doUpdateOfUserInfo(Request request) throws SQLException, IOException {
        List<Map<String,Object>> userInfos = request.getParams();
        for (Map<String, Object> map :
                userInfos) {
            User_infoDao.updateUser_info((User_info) map.get(PubString.SINGLE_ROW));
        }
        Reply reply = new Reply(PubString.UPDATE_TYPE,true,null);
        return reply;
    }

    /**
     * doUpdateOfUserFavItem:具体对表user_favorited_item的update操作
     * @param request
     * @return
     * @throws SQLException
     */
    private static Reply doUpdateOfUserFavItem(Request request) throws SQLException {
        List<Map<String,Object>> userFavItems = request.getParams();
        for (Map<String, Object> map :
                userFavItems) {
            User_favorited_itemDao.updateUser_favorited_item((User_favorited_item) map.get(PubString.SINGLE_ROW));
        }
        Reply reply = new Reply(PubString.UPDATE_TYPE,true,null);
        return reply;
    }

    /**
     * doUpdateOfChannel:具体对表channel的update操作
     * @param request
     * @return
     * @throws SQLException
     */
    private static Reply doUpdateOfChannel(Request request) throws SQLException {
        List<Map<String,Object>> channels = request.getParams();
        for (Map<String, Object> map :
                channels) {
            ChannelDao.updateChannel((Channel) map.get(PubString.SINGLE_ROW));
        }
        Reply reply = new Reply(PubString.UPDATE_TYPE,true,null);
        return reply;
    }

    /**
     * doDelete:对数据库进行delete操作，并且把查询结果封装成Reply
     * @param request
     * @return
     * @throws SQLException
     */
    private static Reply doDelete(Request request) throws SQLException {
        Reply reply = null;
        switch (request.getTable()){
            case PubString.TABLE_CHANNEL:
                reply = doDeleteOfChannel(request);
                break;
            case PubString.TABLE_USER_FAVORITED_ITEM:
                reply = doDeleteOfUserFavItem(request);
                break;
            case PubString.TABLE_USER_INFO:
                reply = doDeleteOfUserInfo(request);
                break;
            case PubString.TABLE_USER_SUBSCRIBED_CHANNEL:
                reply = doDeleteOfUserSubChannel(request);
                break;
            default:

        }
        return reply;
    }

    /**
     * doDeleteOfUserSubChannel:具体对表user_subscribed_channel的delete操作
     * @param request
     * @return
     * @throws SQLException
     */
    private static Reply doDeleteOfUserSubChannel(Request request) throws SQLException {
        List<Map<String,Object>> deleteRows = request.getParams();
        for (Map<String, Object> map :
                deleteRows) {
            User_subscribed_channelDao.deleteUser_subscribed_channel((String)map.get(PubString.OP_USER_ID), (String)map.get(PubString.OP_LINK));
        }
        Reply reply = new Reply(PubString.DELETE_TYPE,true,null);
        return reply;
    }

    /**
     * doDeleteOfUserInfo:具体对表user_info的delete操作
     * @param request
     * @return
     * @throws SQLException
     */
    private static Reply doDeleteOfUserInfo(Request request) throws SQLException {
        List<Map<String,Object>> delete_ids = request.getParams();
        for (Map<String, Object> map :
                delete_ids) {
            User_infoDao.deleteUser_info((String) map.get(PubString.OP_USER_ID));
        }
        Reply reply = new Reply(PubString.DELETE_TYPE,true,null);
        return reply;
    }

    /**
     * doDeleteOfUserFavItem:具体对表user_favorited_item的delete操作
     * @param request
     * @return
     * @throws SQLException
     */
    private static Reply doDeleteOfUserFavItem(Request request) throws SQLException {
        List<Map<String,Object>> deleteRows = request.getParams();
        for (Map<String, Object> map :
                deleteRows) {
            User_favorited_itemDao.deleteUser_favorited_item((String)map.get(PubString.OP_USER_ID), (String)map.get(PubString.OP_LINK));
        }
        Reply reply = new Reply(PubString.DELETE_TYPE,true,null);
        return reply;
    }


    /**
     * doDeleteOfChannel:具体对表channel的delete操作
     * @param request
     * @return
     * @throws SQLException
     */
    private static Reply doDeleteOfChannel(Request request) throws SQLException {
        List<Map<String,Object>> delete_ids = request.getParams();
        for (Map<String, Object> map :
                delete_ids) {
            ChannelDao.deleteChannel((Integer) map.get(PubString.OP_CHANNEL_ID));
        }
        Reply reply = new Reply(PubString.DELETE_TYPE,true,null);
        return reply;
    }

}
