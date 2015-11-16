/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author esteban
 */
public class Paquete implements Serializable{
    private String id_sesion;
    private String[] param;

    public String getId_sesion() {
        return id_sesion;
    }

    public void setId_sesion(String id_sesion) {
        this.id_sesion = id_sesion;
    }

    public String[] getParam() {
        return param;
    }

    public void setParam(String[] param) {
        this.param = param;
    }
    
    
    
}
