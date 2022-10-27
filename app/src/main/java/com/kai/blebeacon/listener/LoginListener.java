package com.kai.blebeacon.listener;

public interface LoginListener {
    void loginSuccessfull(String id, String jwtoken, String customerId);
    void loginFailure(String message);
}
