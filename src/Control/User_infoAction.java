package Control;

import DAO.User_infoDao;
import Module.DataBase.ServserDB.User_info;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by anyuan on 2016/11/10.
 */
public class User_infoAction {

    /**
     * QueryInfoOfUser:查找一个用户的个人信息
     * @param user_ID
     * @return
     * @throws SQLException
     */
    public static User_info QueryInfoOfUser(String user_ID) throws SQLException, IOException {
        User_infoDao uiDao = new User_infoDao();
        QueryParams queryParams = new QueryParams();
        queryParams.addParam("user_ID","=",user_ID);
        return uiDao.Query(queryParams.getParams()).get(0);
    }
}
