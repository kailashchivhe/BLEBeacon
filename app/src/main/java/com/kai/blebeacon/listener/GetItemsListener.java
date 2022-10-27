package com.kai.blebeacon.listener;

import com.kai.blebeacon.model.Item;

import java.util.List;

public interface GetItemsListener {
    void getItemsSuccessfull(List<Item> itemList);
    void getItemsFailure(String message);
}
