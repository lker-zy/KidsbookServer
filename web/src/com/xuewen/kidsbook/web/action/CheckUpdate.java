package com.xuewen.kidsbook.web.action;

import com.opensymphony.xwork2.ActionSupport;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 16-3-5.
 */
public class CheckUpdate extends ActionSupport {
    private String version;
    private Map<String, Object> retJsonMap = new HashMap<>();

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, Object> getRetJsonMap() {
        return retJsonMap;
    }

    public void setRetJsonMap(Map<String, Object> retJsonMap) {
        this.retJsonMap = retJsonMap;
    }

    @Override
    public String execute() {
        retJsonMap.put("errno", "0");
        retJsonMap.put("needUpdate", "true");
        retJsonMap.put("version", "1.2.3");
        retJsonMap.put("verDesc", "This is a new version");

        return SUCCESS;
    }
}
