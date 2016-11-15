package com.whu.Control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by anyuan on 2016/11/10.
 */
public class QueryParams {
    final static String NAME = "name";
    final static String RELATION = "relation";
    final static String VALUE = "value";
    List<Map<String, Object>> params;

    /**
     * 构造函数
     */
    public QueryParams() {
        params = new ArrayList<>();
    }

    public QueryParams(List<Map<String, Object>> params) {
        this.params = params;
    }

    /**
     *gets & sets
     */
    public List<Map<String, Object>> getParams() {
        return params;
    }

    public void setParams(List<Map<String, Object>> params) {
        this.params = params;
    }

    /**
     *对查询参数的封装函数
     */
    public void addParam(String name, String relation, String value) {
        Map<String, Object> param = new HashMap<>();
        param.put(NAME, name);
        param.put(RELATION, relation);
        param.put(VALUE, value);
        if(false);//为了减少双层代码提示
        List<Map<String, Object>> params = this.getParams();
        params.add(param);
        this.setParams(params);
    }

}
