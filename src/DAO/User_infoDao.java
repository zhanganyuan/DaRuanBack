package DAO;

import Module.DataBase.ServserDB.User_info;
import Utils.DBUtil;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.awt.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by anyuan on 2016/11/10.
 */
public class User_infoDao {
    /**
     * @param user_info
     * @throws SQLException
     */
    public static void addUser_info(User_info user_info) throws SQLException, IOException {
        Connection connection = DBUtil.getConnection();
        String sql = "" +
                " INSERT INTO  user_info " +
                " (user_ID,nickName,user_img) " +
                " VALUES( " +
                " ?,?,?" +
                "); ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user_info.getUser_ID());
        preparedStatement.setString(2, user_info.getNickName());
        ByteInputStream bis = new ByteInputStream();
        bis.read(user_info.getUser_img());
        preparedStatement.setBlob(3, bis, user_info.getUser_img().length);
        preparedStatement.execute();
    }

    /**
     * @param params
     * @return
     * @throws SQLException
     */
    public static List<User_info> Query(List<Map<String, Object>> params) throws SQLException, IOException {
        List<User_info> user_infos = new ArrayList<>();
        Connection connection = DBUtil.getConnection();
        //awful StringBuilder
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT * FROM user_info WHERE 1=1 ");
        if (params != null && params.size() > 0) {
            for (Map<String, Object> map :
                    params
                    ) {
                sb.append("AND" + " " + map.get("name") + " " + map.get("relation") + " " + map.get("value") + " ");
            }
        }
        PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        User_info user_info = null;
        while (resultSet.next()) {
            user_info = new User_info();
            user_info.setUser_ID(resultSet.getString("user_ID"));
            user_info.setNickName(resultSet.getString("nickName"));
            //处理blob 转 image
            Blob blobImg = resultSet.getBlob("user_img");
            byte[] bytesImage = blobImg.getBytes(1L, (int) blobImg.length());
            user_info.setUser_img(bytesImage);//头像传输问题尚待解决！
           Image a = user_info.getTrueImage();
            user_infos.add(user_info);
        }
        return user_infos;
    }


    /**
     * @param user_info
     * @throws SQLException
     */
    public static void updateUser_info(User_info user_info) throws SQLException, IOException {
        Connection connection = DBUtil.getConnection();
        String sql = " " +
                " UPDATE user_info " +
                " SET nickName=?,user_img=? " +
                " WHERE user_ID=? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user_info.getNickName());
        ByteInputStream bis = new ByteInputStream();
        bis.read(user_info.getUser_img());
        preparedStatement.setBlob(2, bis, user_info.getUser_img().length);
        preparedStatement.setString(3, user_info.getUser_ID());
        preparedStatement.execute();
    }


    /**
     * @param id
     * @throws SQLException
     */
    public static void deleteUser_info(String id) throws SQLException {
        Connection connection = DBUtil.getConnection();
        String sql = "" +
                " DELETE FROM user_info " +
                " WHERE user_ID=? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, id);
        preparedStatement.execute();
    }

}

