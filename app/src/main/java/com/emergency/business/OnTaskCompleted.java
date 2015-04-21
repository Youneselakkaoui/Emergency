package com.emergency.business;

import com.emergency.emergency.dto.ManageUserOut;

/**
 * Created by elmehdiharabida on 21/04/2015.
 */
public interface OnTaskCompleted<Return> {
    void onTaskCompleted(Return manageUserOut);
}
