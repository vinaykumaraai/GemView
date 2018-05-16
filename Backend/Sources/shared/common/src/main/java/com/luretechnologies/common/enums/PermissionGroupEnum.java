/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luretechnologies.common.enums;

/**
 *
 * @author User
 */
public enum PermissionGroupEnum {
    
    NONE(-1),
    ALL(0),
    USER(100),
    ROLE(200),
    CLIENT(300),
    TRANSACTION(400),
    ENTITY(500),
    HOST(600),
    SETTING(700),
    DASHBOARD(800);
    
    private final int id;

    PermissionGroupEnum(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }
    
}
