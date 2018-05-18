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
public enum PermissionAccessEnum {
    
    ALL(0),
    READ(1),
    CREATE(2),
    UPDATE(3),
    DELETE(4);
    
    private final int id;

    PermissionAccessEnum(int id) {
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
