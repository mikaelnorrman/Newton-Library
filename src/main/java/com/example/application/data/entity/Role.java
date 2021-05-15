package com.example.application.data.entity;

public enum Role {

    INACTIVE(1), USER_NO_ACCESS(4), USER(5), ADMIN(2), SUPERADMIN(3);

    private final int id_user_role;

    Role(int id_user_role) {
        this.id_user_role = id_user_role;
    }

    public int getId_user_role() {
        return id_user_role;
    }
}
