package com.fsoft.quizsystem.object.constant;

public enum SystemAuthorities {
    /* PERMISSIONS FOR USER QUERY */
    USER_READ, USER_UPDATE,
    /* PERMISSIONS FOR ROLE QUERY */
    SYSTEM_ROLE_READ, SYSTEM_ROLE_CREATE, SYSTEM_ROLE_UPDATE, SYSTEM_ROLE_DELETE,
    /* PERMISSIONS FOR QUIZ QUERY */
    QUIZ_READ, QUIZ_CREATE, QUIZ_UPDATE, QUIZ_DELETE,
    /* PERMISSIONS FOR CATEGORY QUERY */
    CATEGORY_CREATE, CATEGORY_UPDATE, CATEGORY_DELETE
}