package com.whu.Client;

import com.whu.Module.DataBase.ServserDB.Channel;
import com.whu.Module.DataBase.ServserDB.User_favorited_item;
import com.whu.Module.DataBase.ServserDB.User_info;
import com.whu.Module.DataBase.ServserDB.User_subscribed_channel;
import com.whu.Module.transfer.Reply;
import com.whu.Module.transfer.Request;
import com.whu.Utils.PubString;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by anyuan on 2016/11/10.
 */
public class ClientThread extends Thread {

    private Reply reply;
    private Request request;

    /**
     * constructors
     */
    public ClientThread() {
    }

    public ClientThread(Request request) {
        this.request = request;
        this.reply = new Reply();
    }

    public ClientThread(Request request, Reply reply) {
        this.reply = reply;
        this.request = request;
    }

    /**
     * gets & sets
     */

    public Reply getReply() {
        return reply;
    }

    public void setReply(Reply reply) {
        this.reply = reply;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    /**
     * gets & sets
     */

    @Override
    public void run() {
        try {
            Socket socket = new Socket("127.0.0.1", 8888);
            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);

            oos.writeObject(request);
            oos.flush();
            //获取输入流用来读取服务器端响应信息
            InputStream is = socket.getInputStream();


            ObjectInputStream ois = new ObjectInputStream(is);
            reply = (Reply) ois.readObject();
            if (reply.isSuccess()) {
                switch (reply.getReplyType()) {
                    case PubString.SYNC_TYPE:
                        doSync(reply);
                        break;
                    case PubString.SYNC_USER_CHANNEL_TYPE:
                        doSyncUserChannel(reply);
                        break;
                    case PubString.SYNC_USER_INFO_TYPE:
                        doSyncUserInfo(reply);
                        break;
                    case PubString.SYNC_USER_ITEM_TYPE:
                        doSyncUserItem(reply);
                        break;
                    case PubString.QUERY_TYPE:
                        doQuery(reply);
                        break;
                    case PubString.ADD_TYPE:
                        doADD(reply);
                        break;
                    case PubString.UPDATE_TYPE:
                        doUpdate(reply);
                        break;
                    case PubString.DELETE_TYPE:
                        doDelete(reply);
                        break;
                    default:
                        //default action
                }

            } else {
                System.out.println("服务器处理失败！********");
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void doSyncUserItem(Reply reply) {

    }

    private void doSyncUserInfo(Reply reply) {

    }

    private void doSyncUserChannel(Reply reply) {
        List<User_subscribed_channel> userSubChannels = (List<User_subscribed_channel>) reply.getContent().get(PubString.PROCESSED_RESULT);
        //example of the processing code
        for (User_subscribed_channel map :
                userSubChannels) {
            System.out.println(map.getUser_ID() + "　" + map.getLink() + " " + map.getDescription());
        }
    }

    private void doSync(Reply reply) throws IOException, SQLException {
        List<User_subscribed_channel> userSubChannels = (List<User_subscribed_channel>) reply.getContent().get(PubString.PROCESSED_RESULT_OF_CHANNEL);
        User_info userInfo = (User_info) reply.getContent().get(PubString.PROCESSED_RESULT_OF_INFO);
        List<User_favorited_item> userFavItems = (List<User_favorited_item>) reply.getContent().get(PubString.PROCESSED_RESULT_OF_ITEM);
        //example of the processing code
        System.out.println("用户订阅的频道：");
        for (User_subscribed_channel map :
                userSubChannels) {
            System.out.println("用户ID:" + map.getUser_ID() + " 频道title；" + map.getTitle() + " 频道描述：" + map.getDescription() + " 频道RSS地址："
                    + map.getLink() + " 频道更新日期：" + map.getPubDate() + " 频道ID：" + map.getChannel_ID());
        }
        System.out.println("用户个人信息：");

//        byte[] userImage = userInfo.getUser_img();
        //byte[]转化成图片
//        ByteArrayInputStream bis = new ByteArrayInputStream(userImage);
//        BufferedImage img = ImageIO.read(bis);
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        ImageIO.write(img,"jpg",bos);
        //
        FileOutputStream fos = new FileOutputStream("头像.jpg");
        fos.write(userInfo.getUser_img());


        System.out.println("用户ID：" + userInfo.getUser_ID() + " 用户昵称：" + userInfo.getNickName() + " 用户头像：" + userInfo.getTrueImage());
        System.out.println("用户个人收藏的文章：");
        for (User_favorited_item map :
                userFavItems) {
            System.out.println("用户ID：" + map.getUser_ID() + " 文章标题：" + map.getTitle() + " 文章描述：" + map.getDescription() + " 文章原地址：" + map.getLink() +
                    "  文章更新日期：" + map.getPubDate() + " 所属频道ID" + map.getChannel_ID());
        }
    }

    /*
    客户端处理服务器返回数据的方法
     */

    /**
     * doQuery:处理查询返回数据
     *
     * @param reply
     */
    private static void doQuery(Reply reply) {
        List<Channel> channels = (List<Channel>) reply.getContent().get(PubString.PROCESSED_RESULT);
        //example of the processing code
        for (Channel channel :
                channels) {
            System.out.println(channel.getChannel_ID() + "　" + channel.getLink() + " " + channel.getDescription());
        }
    }

    private static void doADD(Reply reply) {

    }

    private static void doUpdate(Reply reply) {

    }

    private static void doDelete(Reply reply) {

    }
}
