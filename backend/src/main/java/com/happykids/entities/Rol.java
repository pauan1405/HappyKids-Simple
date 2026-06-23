package com.happykids.entities;

import jakarta.persistence.*;


@Entity
@Table(name = "tmrol")

public class Rol {

    @Id
    @Column(name = "pkcodrol_usuario")
    private Integer codRol;

    @Column(name = "drol", nullable = false, length = 12)
    private String drol;

    public Integer getCodRol() {
        return codRol;
    }

    public void setCodRol(Integer codRol) {
        this.codRol = codRol;
    }

    public String getDrol() {
        return drol;
    }

    public void setDrol(String drol) {
        this.drol = drol;
    }
    
}
