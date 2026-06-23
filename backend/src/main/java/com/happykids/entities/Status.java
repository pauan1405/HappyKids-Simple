package com.happykids.entities;

import jakarta.persistence.*;


@Entity
@Table(name = "tmstatus")

public class Status {

    @Id
    @Column(name = "pkcod_status")
    private Integer codStatus;

    @Column(name = "dstatus", nullable = false, length = 10)
    private String dstatus;

    public Integer getCodStatus() {
        return codStatus;
    }

    public void setCodStatus(Integer codStatus) {
        this.codStatus = codStatus;
    }

    public String getDstatus() {
        return dstatus;
    }

    public void setDstatus(String dstatus) {
        this.dstatus = dstatus;
    }
    
}
