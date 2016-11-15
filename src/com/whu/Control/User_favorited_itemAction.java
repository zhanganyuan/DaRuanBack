package com.whu.Control;

import com.whu.DAO.User_favorited_itemDao;
import com.whu.Module.DataBase.ServserDB.User_favorited_item;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by anyuan on 2016/11/10.
 */
public class User_favorited_itemAction {


    /**
     * QueryAllFItemOfUser:查找一个用户所收藏的所有文章
     * @param user_ID
     * @return
     * @throws SQLException
     */
    public static List<User_favorited_item> QueryAllFItemOfUser(String user_ID) throws SQLException {
        User_favorited_itemDao ufiDao = new User_favorited_itemDao();
        QueryParams queryParams = new QueryParams();
        queryParams.addParam("user_ID","=",user_ID);
        return ufiDao.Query(queryParams.getParams());
    }




}
