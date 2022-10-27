package com.kai.blebeacon.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kai.blebeacon.listener.GetItemsListener;
import com.kai.blebeacon.model.Item;
import com.kai.blebeacon.utils.APIHelper;

import java.util.List;

public class HomeViewModel extends AndroidViewModel implements GetItemsListener {

    MutableLiveData<String> messageLiveData;
    MutableLiveData<List<Item>> itemListMutableLiveData;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        messageLiveData = new MutableLiveData<>();
        itemListMutableLiveData = new MutableLiveData<>();
    }

    public void getItems(String region){
        APIHelper.getItems(region,this);
    }

    @Override
    public void getItemsSuccessfull(List<Item> itemList) {
        itemListMutableLiveData.postValue(itemList);
    }

    @Override
    public void getItemsFailure(String message) {
        messageLiveData.postValue(message);
    }

    public MutableLiveData<String> getMessageLiveData() {
        return messageLiveData;
    }

    public MutableLiveData<List<Item>> getItemListMutableLiveData() {
        return itemListMutableLiveData;
    }
}