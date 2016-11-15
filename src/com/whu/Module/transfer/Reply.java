package com.whu.Module.transfer;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by anyuan on 2016/11/7.
 */
public class Reply implements Serializable{
    private String replyType;
    private boolean success;
    private Map<String,Object> content;

    /**
     * constructor
     */
    public Reply() {
    }

    public Reply(String replyType, boolean success, Map<String, Object> content) {
        this.replyType = replyType;
        this.success = success;
        this.content = content;
    }

    /**
     * sets & gets
     */
    public String getReplyType() {
        return replyType;
    }

    public void setReplyType(String replyType) {
        this.replyType = replyType;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Map<String, Object> getContent() {
        return content;
    }

    public void setContent(Map<String, Object> content) {
        this.content = content;
    }
}
