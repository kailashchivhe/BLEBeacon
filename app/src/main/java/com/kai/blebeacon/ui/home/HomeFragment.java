package com.kai.blebeacon.ui.home;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kai.blebeacon.R;
import com.kai.blebeacon.adapter.HomeItemAdapter;
import com.kai.blebeacon.databinding.FragmentHomeBinding;
import com.kai.blebeacon.model.Item;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    SharedPreferences sharedPreferences;
    String jwtToken;
    HomeItemAdapter homeItemAdapter;
    FragmentHomeBinding binding;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_profile) {
            onProfileClicked();
            return true;
        }
        else if (id == R.id.action_logout) {
            onLogoutClicked();
            return true;
        }
        return false;
    }

    private void onProfileClicked() {
        navigateToProfile();
    }

    private void navigateToProfile() {
        NavHostFragment.findNavController(this).navigate(R.id.action_HomeFragment_to_ProfileFragment);
    }

    private void onLogoutClicked(){
        sharedPreferences.edit().remove("jwtToken").commit();
        NavHostFragment.findNavController(this).navigate(R.id.action_HomeFragment_to_LoginFragment);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        homeViewModel.getMessageLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(!s.isEmpty()){
                    displayMessage(s);
                }
            }
        });
        homeViewModel.getItemListMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> itemList) {
                if(itemList.size() > 0){
                    setItemsOnView(itemList);
                }
            }
        });
    }

    private void setItemsOnView(List<Item> itemList) {
        homeItemAdapter = new HomeItemAdapter(itemList);
        binding.recyclerViewHome.setAdapter(homeItemAdapter);
    }

    private void displayMessage(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");

        String region= null;
        homeViewModel.getItems(region);

        binding.recyclerViewHome.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}