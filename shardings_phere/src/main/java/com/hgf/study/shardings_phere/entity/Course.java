package com.hgf.study.shardings_phere.entity;

import java.io.Serializable;

/**
 * @author 黄耿锋
 * @date 2023/9/26 17:31
 **/
public class Course implements Serializable {

    private Long cid;

    private String cname;

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
