package com.emergency.business;

/**
 * Created by elmehdiharabida on 21/04/2015.
 */
public interface OnTaskCompleted<Return> {
    void onTaskCompleted(Return manageUserOut);

    void onPreExecute();
}
