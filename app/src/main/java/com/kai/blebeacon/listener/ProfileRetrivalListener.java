package com.kai.blebeacon.listener;

import com.kai.blebeacon.model.User;

public interface ProfileRetrivalListener {
    void profileRetrivalSuccessful(User user);
    void profileRetrivalFailure(String message);
}
