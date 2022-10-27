package com.kai.blebeacon.ui.profile;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kai.blebeacon.R;
import com.kai.blebeacon.databinding.FragmentProfileBinding;
import com.kai.blebeacon.model.User;

import java.util.function.ObjIntConsumer;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    FragmentProfileBinding binding;
    SharedPreferences sharedPreferences;
    String jwtToken;
    String id;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater,container,false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        profileViewModel.getMessageLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(!s.isEmpty()){
                    displayMessage(s);
                }
            }
        });
        profileViewModel.getUpdateLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    displayMessage("Profile Updated successfully");
                    navigateToHome();
                }
            }
        });
        profileViewModel.getUserLiveData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user != null){
                    displayUserDetails(user);
                }
            }
        });
    }

    private void displayUserDetails(User user) {
        binding.editTextProfileFirstName.setText(user.getFirstName());
        binding.editTextProfileLastName.setText(user.getLastName());
        binding.editTextTextProfieEmail.setText(user.getEmail());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");

        profileViewModel.profileRetrival(jwtToken);

        binding.buttonProfileCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelClicked();
            }
        });
        binding.buttonProfileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProfileUpdateClicked();
            }
        });
    }

    private void onCancelClicked() {
        navigateToHome();
    }

    private void navigateToHome() {
        NavHostFragment.findNavController(this).navigate(R.id.action_ProfileFragment_to_HomeFragment);
    }

    private void onProfileUpdateClicked(){
        User user;
        String firstName = binding.editTextProfileFirstName.getText().toString();
        String lastName = binding.editTextProfileLastName.getText().toString();
        user = new User(firstName,lastName,id,jwtToken);
        if(user != null){
            profileViewModel.profileUpdate(user);
        }
        else{
            displayMessage("Please enter information before updating the profile");
        }
    }

    private void displayMessage(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

}