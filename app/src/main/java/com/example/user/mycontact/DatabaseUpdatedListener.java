package com.example.user.mycontact;

import android.content.ContentValues;

/**
 * Created by user on 12/7/2016.
 */
public interface DatabaseUpdatedListener {
    void setDatabaseSuccess(String name, String phone,String email);
    void setDatabaseError(String failureMessage);
}

