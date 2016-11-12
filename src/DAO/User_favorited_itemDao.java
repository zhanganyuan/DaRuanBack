package DAO;

import Module.DataBase.ServserDB.User_favorited_item;
import Utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by anyuan on 2016/11/10.
 */
public class User_favorited_itemDao {


    /**
     *
     * @param user_favorited_item
     * @throws SQLException
     */
    public static void addUser_favorited_item(User_favorited_item user_favorited_item) throws SQLException {
        Connection connection = DBUtil.getConnection();
        String sql = "" +
                " INSERT INTO  user_favorited_item " +
                " (user_ID,title,description,link,pubDate,channel_ID) " +
                " VALUES( " +
                " ?,?,?,?,?,?" +
                "); ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,user_favorited_item.getUser_ID());
        preparedStatement.setString(2,user_favorited_item.getTitle());
        preparedStatement.setString(3,user_favorited_item.getDescription());
        preparedStatement.setString(4,user_favorited_item.getLink().trim());
        preparedStatement.setDate(5, (Date) user_favorited_item.getPubDate());
        preparedStatement.setInt(6,user_favorited_item.getChannel_ID());
        preparedStatement.execute();
    }

    /**
     *
     * @param params
     * @return
     * @throws SQLException
     */
    public static List<User_favorited_item> Query(List<Map<String, Object>> params) throws SQLException {
        List<User_favorited_item> user_favorited_items = new ArrayList<>();
        Connection connection = DBUtil.getConnection();
        //awful StringBuilder
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT * FROM user_favorited_item WHERE 1=1 ");
        if (params != null && params.size() > 0) {
            for (Map<String, Object> map :
                    params
                    ) {
                sb.append("AND" + " " + map.get("name") + " " + map.get("relation") + " " + map.get("value") + " ");
            }
        }
        PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        User_favorited_item user_favorited_item = null;
        while (resultSet.next()) {
            user_favorited_item = new User_favorited_item();
            user_favorited_item.setUser_ID(resultSet.getString("user_ID"));
            user_favorited_item.setTitle(resultSet.getString("title"));
            user_favorited_item.setDescription(resultSet.getString("description"));
            user_favorited_item.setLink(resultSet.getString("link").trim());
            user_favorited_item.setPubDate(resultSet.getDate("pubDate"));
            user_favorited_item.setChannel_ID(resultSet.getInt("channel_ID"));
            user_favorited_items.add(user_favorited_item);
        }
        return user_favorited_items;
    }

    /**
     *
     * @param user_favorited_item
     * @throws SQLException
     */
    public static void updateUser_favorited_item(User_favorited_item user_favorited_item) throws SQLException {
        Connection connection = DBUtil.getConnection();
        String sql = " " +
                " UPDATE user_favorited_item " +
                " SET title=?,description=?,pubDate=?,channel_ID=? " +
                " WHERE user_ID=? AND  link=? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,user_favorited_item.getTitle());
        preparedStatement.setString(2,user_favorited_item.getDescription());
        preparedStatement.setDate(3, (Date) user_favorited_item.getPubDate());
        preparedStatement.setInt(4,user_favorited_item.getChannel_ID());
        preparedStatement.setString(5,user_favorited_item.getUser_ID());
        preparedStatement.setString(6,user_favorited_item.getLink().trim());
        preparedStatement.execute();
    }

    /**
     *
     * @param user_id
     * @param link
     * @throws SQLException
     */
    public static void deleteUser_favorited_item(String user_id,String link) throws SQLException {
        Connection connection = DBUtil.getConnection();
        String sql = "" +
                " DELETE FROM user_favorited_item " +
                " WHERE user_ID=? AND link=? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user_id);
        preparedStatement.setString(2, link.trim());
        preparedStatement.execute();
    }




}


