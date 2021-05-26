package com.example.application.data.entity;

public enum Role {

    INACTIVE(1), USER_NO_ACCESS(4), USER(5), ADMIN(2), SUPERADMIN(3);

    private final int role_id;

    Role(int role_id) {
        this.role_id = role_id;
    }

    public int getRole_id() {
        return role_id;
    }
}
