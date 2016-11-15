package com.whu.DAO;

import com.whu.Module.DataBase.ServserDB.Channel;
import com.whu.Utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by anyuan on 2016/11/10.
 */
public class ChannelDao {

    /**
     * addChannels:channels表中插入数据
     *
     * @param channel
     * @throws SQLException
     */
    public static void addChannel(Channel channel) throws SQLException {
        Connection connection = DBUtil.getConnection();
        String sql = "" +
                " INSERT INTO  channel " +
                " (channel_ID,link,title,description,lastBuildDate) " +
                " VALUES( " +
                " DEFAULT,?,?,?,?" +
                "); ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, channel.getLink().trim());
        preparedStatement.setString(2, channel.getTitle());
        preparedStatement.setString(3, channel.getDescription());
        preparedStatement.setDate(4, (Date) channel.getLastBuildDate());
        preparedStatement.execute();
    }

    /**
     * Query
     * @param params
     * @return
     * @throws SQLException
     */
    public static List<Channel> Query(List<Map<String, Object>> params) throws SQLException {
        List<Channel> channels = new ArrayList<>();
        Connection connection = DBUtil.getConnection();
        //awful StringBuilder
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT * FROM channel WHERE 1=1 ");
        if (params != null && params.size() > 0) {
            for (Map<String, Object> map :
                    params
                    ) {
                sb.append("AND" + " " + map.get("name") + " " + map.get("relation") + " " + map.get("value") + " ");
            }
        }
        PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        Channel channel = null;
        while (resultSet.next()) {
            channel = new Channel();
            channel.setChannel_ID(resultSet.getInt("channel_ID"));
            channel.setLink(resultSet.getString("link").trim());
            channel.setTitle(resultSet.getString("title"));
            channel.setDescription(resultSet.getString("description"));
            channel.setLastBuildDate(resultSet.getDate("lastBuildDate"));
            channels.add(channel);
        }
        return channels;
    }

    /**
     *
     * @param channel
     * @throws SQLException
     */
    public static void updateChannel(Channel channel) throws SQLException {
        Connection connection = DBUtil.getConnection();
        String sql = " " +
                " UPDATE channel " +
                " SET link=?,title=?,description=?,lastBuildDate=? " +
                " WHERE channel_ID=? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, channel.getLink().trim());
        preparedStatement.setString(2, channel.getTitle());
        preparedStatement.setString(3, channel.getDescription());
        preparedStatement.setDate(4, (Date) channel.getLastBuildDate());
        preparedStatement.setInt(5, channel.getChannel_ID());
        preparedStatement.execute();
    }


    /**
     *
     * @param id
     * @throws SQLException
     */
    public static void deleteChannel(int id) throws SQLException {
        Connection connection = DBUtil.getConnection();
        String sql = "" +
                " DELETE FROM channel " +
                " WHERE channel_ID=? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

}