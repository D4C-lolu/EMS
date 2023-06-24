package com.encentral.entities;

public enum Role {
    EMPLOYEE(0),
    ADMIN(1);

    final int val;

    Role(int val){
        this.val = val;
    }

    public static Role fromInteger(int x){
        switch(x){
            case 1:
                return EMPLOYEE;
            case 2:
                return ADMIN;
            default:
                return null;
        }
    }
}
