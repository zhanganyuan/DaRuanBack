package com.whu.Control;

import com.whu.DAO.User_subscribed_channelDao;
import com.whu.Module.DataBase.ServserDB.User_subscribed_channel;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by anyuan on 2016/11/10.
 */
public class User_subscribed_channelAction {

    /**
     * QueryAllSChannelOfUser:查找一个用户所有订阅的频道
     * @param user_ID
     * @return
     * @throws SQLException
     */
    public static List<User_subscribed_channel> QueryAllSChannelOfUser(String user_ID) throws SQLException {
        User_subscribed_channelDao uscDao = new User_subscribed_channelDao();
        QueryParams queryParams = new QueryParams();
        queryParams.addParam("user_ID","=",user_ID);
        return uscDao.Query(queryParams.getParams());
    }
}
