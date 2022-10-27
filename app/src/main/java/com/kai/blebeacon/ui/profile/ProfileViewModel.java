package com.kai.blebeacon.ui.profile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.kai.blebeacon.listener.ProfileRetrivalListener;
import com.kai.blebeacon.listener.ProfileUpdateListener;
import com.kai.blebeacon.model.User;
import com.kai.blebeacon.utils.APIHelper;

public class ProfileViewModel extends AndroidViewModel implements ProfileUpdateListener, ProfileRetrivalListener {

    MutableLiveData<String> messageLiveData;
    MutableLiveData<Boolean> updateLiveData;
    MutableLiveData<User> userLiveData;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        messageLiveData = new MutableLiveData<>();
        updateLiveData = new MutableLiveData<>();
        userLiveData = new MutableLiveData<>();
    }

    public void profileRetrival(String jwtoken){
        APIHelper.profileRetrival(jwtoken,this);
    }
    public void profileUpdate(User user){
        APIHelper.profileUpdate(user,this);
    }

    @Override
    public void profileUpdateSuccessful() {
        updateLiveData.postValue(true);
    }

    @Override
    public void profileUpdateFailure(String message) {
        messageLiveData.postValue(message);
    }

    public MutableLiveData<Boolean> getUpdateLiveData() {
        return updateLiveData;
    }

    public MutableLiveData<String> getMessageLiveData() {
        return messageLiveData;
    }

    @Override
    public void profileRetrivalSuccessful(User user) {
        userLiveData.postValue(user);
    }

    @Override
    public void profileRetrivalFailure(String message) {
        messageLiveData.postValue(message);
    }

    public MutableLiveData<User> getUserLiveData() {
        return userLiveData;
    }
}