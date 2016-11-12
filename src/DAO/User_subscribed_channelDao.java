package DAO;

import Module.DataBase.ServserDB.User_subscribed_channel;
import Utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by anyuan on 2016/11/10.
 */
public class User_subscribed_channelDao {


    /**
     *
     * @param user_subscribed_channel
     * @throws SQLException
     */
    public static void addUser_subscribed_channel(User_subscribed_channel user_subscribed_channel) throws SQLException {
        Connection connection = DBUtil.getConnection();
        String sql = "" +
                " INSERT INTO  user_subscribed_channel " +
                " (user_ID,title,description,link,pubDate,channel_ID) " +
                " VALUES( " +
                " ?,?,?,?,?,?" +
                "); ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,user_subscribed_channel.getUser_ID());
        preparedStatement.setString(2,user_subscribed_channel.getTitle());
        preparedStatement.setString(3,user_subscribed_channel.getDescription());
        preparedStatement.setString(4,user_subscribed_channel.getLink().trim());
        preparedStatement.setDate(5, (Date) user_subscribed_channel.getPubDate());
        preparedStatement.setInt(6,user_subscribed_channel.getChannel_ID());
        preparedStatement.execute();
    }

    /**
     *
     * @param params
     * @return
     * @throws SQLException
     */
    public static List<User_subscribed_channel> Query(List<Map<String, Object>> params) throws SQLException {
        List<User_subscribed_channel> user_subscribed_channels = new ArrayList<>();
        Connection connection = DBUtil.getConnection();
        //awful StringBuilder
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT * FROM user_subscribed_channel WHERE 1=1 ");
        if (params != null && params.size() > 0) {
            for (Map<String, Object> map :
                    params
                    ) {
                sb.append("AND" + " " + map.get("name") + " " + map.get("relation") + " " + map.get("value") + " ");
            }
        }
        PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        User_subscribed_channel user_subscribed_channel = null;
        while (resultSet.next()) {
            user_subscribed_channel = new User_subscribed_channel();
            user_subscribed_channel.setUser_ID(resultSet.getString("user_ID"));
            user_subscribed_channel.setTitle(resultSet.getString("title"));
            user_subscribed_channel.setDescription(resultSet.getString("description"));
            user_subscribed_channel.setLink(resultSet.getString("link").trim());
            user_subscribed_channel.setPubDate(resultSet.getDate("pubDate"));
            user_subscribed_channel.setChannel_ID(resultSet.getInt("channel_ID"));
            user_subscribed_channels.add(user_subscribed_channel);
        }
        return user_subscribed_channels;
    }

    /**
     *
     * @param user_subscribed_channel
     * @throws SQLException
     */
    public static void updateUser_subscribed_channel(User_subscribed_channel user_subscribed_channel) throws SQLException {
        Connection connection = DBUtil.getConnection();
        String sql = " " +
                " UPDATE user_subscribed_channel " +
                " SET title=?,description=?,pubDate=?,channel_ID=? " +
                " WHERE user_ID=? AND  link=? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,user_subscribed_channel.getTitle());
        preparedStatement.setString(2,user_subscribed_channel.getDescription());
        preparedStatement.setDate(3, (Date) user_subscribed_channel.getPubDate());
        preparedStatement.setInt(4,user_subscribed_channel.getChannel_ID());
        preparedStatement.setString(5,user_subscribed_channel.getUser_ID());
        preparedStatement.setString(6,user_subscribed_channel.getLink().trim());
        preparedStatement.execute();
    }

    /**
     *
     * @param user_id
     * @param link
     * @throws SQLException
     */
    public static void deleteUser_subscribed_channel(String user_id,String link) throws SQLException {
        Connection connection = DBUtil.getConnection();
        String sql = "" +
                " DELETE FROM user_subscribed_channel " +
                " WHERE user_ID=? AND link=? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user_id);
        preparedStatement.setString(2, link.trim());
        preparedStatement.execute();
    }

}
