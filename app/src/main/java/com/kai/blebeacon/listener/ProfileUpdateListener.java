package com.kai.blebeacon.listener;

public interface ProfileUpdateListener {
    void profileUpdateSuccessful();
    void profileUpdateFailure(String message);
}
