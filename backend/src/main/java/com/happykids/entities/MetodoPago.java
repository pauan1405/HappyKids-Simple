package com.happykids.entities;

import jakarta.persistence.*;


@Entity
@Table(name = "tmmetodo")

public class MetodoPago {

    @Id
    @Column(name = "pkcodmetodo")
    private Integer codMetodo;

    @Column(name = "dmetodo", nullable = false, length = 50)
    private String dmetodo;

    public Integer getCodMetodo() {
        return codMetodo;
    }

    public void setCodMetodo(Integer codMetodo) {
        this.codMetodo = codMetodo;
    }

    public String getDmetodo() {
        return dmetodo;
    }

    public void setDmetodo(String dmetodo) {
        this.dmetodo = dmetodo;
    }
    
    
}
