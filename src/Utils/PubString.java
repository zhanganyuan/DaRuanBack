package Utils;

/**
 * Created by anyuan on 2016/11/10.
 */
public class PubString {
    //Action层操作类型
    final public static String SYNC_TYPE = "sync_type";
    final public static String SYNC_USER_ITEM_TYPE = "sync_user_item_type";
    final public static String SYNC_USER_INFO_TYPE = "sync_user_info_type";
    final public static String SYNC_USER_CHANNEL_TYPE = "sync_user_channel_type";
    //对数据库操作类型
    final public static String QUERY_TYPE = "query_type";
    final public static String ADD_TYPE = "add_type";
    final public static String UPDATE_TYPE = "update_type";
    final public static String DELETE_TYPE = "delete_type";
    //...

    //返回Map类型
    final public static String TABLE_NAME = "table_name";

    final public static String PROCESSED_RESULT = "processed_result";
    final public static String PROCESSED_RESULT_OF_INFO = "processed_result_of_info";
    final public static String PROCESSED_RESULT_OF_ITEM = "processed_result_of_item";
    final public static String PROCESSED_RESULT_OF_CHANNEL = "processed_result_of_channel";

    //...

    //请求的表名类型
    final public static String TABLE_CHANNEL = "channel";
    final public static String TABLE_USER_FAVORITED_ITEM = "user_favorited_item";
    final public static String TABLE_USER_INFO = "user_info";
    final public static String TABLE_USER_SUBSCRIBED_CHANNEL = "user_subscribed_channel";


    //Map keys
    //Query
    final public static String NAME = "name";
    final public static String RELATION = "relation";
    final public static String VALUE = "value";


    //Delete
    final public static String OP_CHANNEL_ID = "op_channel_id";
    final public static String OP_USER_ID = "op_user_id";
    final public static String OP_LINK = "op_link";
    //Add & Update
    final public static String SINGLE_ROW = "single_row";

}
