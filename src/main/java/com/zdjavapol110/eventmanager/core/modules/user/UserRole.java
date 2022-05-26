package com.zdjavapol110.eventmanager.core.modules.user;

public enum UserRole {
    ADMIN, STANDARD_USER;

    public static String getName() {
        return String.valueOf(ADMIN);
    }
}
