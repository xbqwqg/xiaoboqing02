package com.leyou;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_group")
public class Group {

    @Id
    private Long id;
    private Long cid;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Group(Long cid, String name) {
        this.cid = cid;
        this.name = name;
    }

    public Group() {
    }
}
