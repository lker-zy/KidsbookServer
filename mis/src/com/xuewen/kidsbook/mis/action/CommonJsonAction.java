package com.xuewen.kidsbook.mis.action;

import com.opensymphony.xwork2.ActionSupport;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by root on 16-3-4.
 */
public class CommonJsonAction extends ActionSupport {
    protected Logger logger = Logger.getLogger(this.getClass().getName());

    protected Map<String, Object> retJsonMap = new HashMap<>();

    public Map<String, Object> getRetJsonMap() {
        return retJsonMap;
    }

    public void setRetJsonMap(Map<String, Object> retJsonMap) {
        this.retJsonMap = retJsonMap;
    }
}
