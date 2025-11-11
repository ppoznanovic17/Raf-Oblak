package com.raf.raf_cloud_back.security;

public class Permission {
    
    // User permissions
    public static final String CAN_CREATE_USERS = "can_create_users";
    public static final String CAN_READ_USERS = "can_read_users";
    public static final String CAN_UPDATE_USERS = "can_update_users";
    public static final String CAN_DELETE_USERS = "can_delete_users";
    
    // Machine permissions
    public static final String CAN_SEARCH_MACHINES = "can_search_machines";
    public static final String CAN_START_MACHINES = "can_start_machines";
    public static final String CAN_STOP_MACHINES = "can_stop_machines";
    public static final String CAN_RESTART_MACHINES = "can_restart_machines";
    public static final String CAN_CREATE_MACHINES = "can_create_machines";
    public static final String CAN_DESTROY_MACHINES = "can_destroy_machines";
    
    private Permission() {
    }
}


