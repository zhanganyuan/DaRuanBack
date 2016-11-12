package Module.transfer;

import Utils.PubString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by anyuan on 2016/11/7.
 */
public class Request implements Serializable {


    private String requestType;
    private String table;
    List<Map<String, Object>> params;


    /**
     * constructor
     */

    public Request() {
        this.params = new ArrayList<>();
    }

    public Request(String requestType, String table) {
        this.requestType = requestType;
        this.table = table;
        this.params = new ArrayList<>();
    }


    public Request(String requestType, String table, List<Map<String, Object>> params) {
        this.requestType = requestType;
        this.table = table;
        this.params = params;
    }

    /**
     * gets & sets
     */

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<Map<String, Object>> getParams() {
        return params;
    }

    public void setParams(List<Map<String, Object>> params) {
        this.params = params;
    }

    /**
     * @param name
     * @param relation
     * @param value
     */
    public void addQueryParam(String name, String relation, String value) {
        Map<String, Object> param = new HashMap<>();
        param.put(PubString.NAME, name);
        param.put(PubString.RELATION, relation);
        param.put(PubString.VALUE, value);
        List<Map<String, Object>> params = this.getParams();
        params.add(param);
        this.setParams(params);
    }

    /**
     * addAddOrUpdateParam:
     *
     * @param table
     */
    public void addAddOrUpdateParam(Object table) {
        Map<String, Object> param = new HashMap<>();
        param.put(PubString.SINGLE_ROW, table);
        List<Map<String, Object>> params = this.getParams();
        params.add(param);
        this.setParams(params);
    }

    /**
     *
     * @param id
     */
    public void addDeleteOrSyncParam(String name,String id) {
        Map<String, Object> param = new HashMap<>();
        switch (name){
            case "channel_ID":
                param.put(PubString.OP_CHANNEL_ID,id);
                break;
            case "user_ID":
                param.put(PubString.OP_USER_ID,id);
                break;
            case "link":
                param.put(PubString.OP_LINK,id);
                break;
            default:
                //nothing to do
        }
        List<Map<String, Object>> params = this.getParams();
        params.add(param);
        this.setParams(params);
    }

}
