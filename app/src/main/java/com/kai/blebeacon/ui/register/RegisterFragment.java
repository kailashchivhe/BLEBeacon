package com.kai.blebeacon.ui.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.kai.blebeacon.R;
import com.kai.blebeacon.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    RegisterViewModel registerViewModel;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        registerViewModel.getMessageLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(!s.isEmpty()){
                    displayMessage(s);
                }
            }
        });
        registerViewModel.getRegisterLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    displayMessage("User successfully registered");
                    navigateToLogin();
                }
            }
        });
    }

    private void navigateToLogin() {
        NavHostFragment.findNavController(this).navigate(R.id.action_RegisterFragment_to_LoginFragment);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.buttonRegisterSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegistrationClicked();
            }
        });
        binding.buttonRegisterCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelClicked();
            }
        });
    }

    private void onCancelClicked() {
        navigateToLogin();
    }

    private void onRegistrationClicked() {
        String email = binding.editTextRegisterEmail.getText().toString();
        String password = binding.editTextRegisterPassword.getText().toString();
        String firstName = binding.editTextRegisterFirstName.getText().toString();
        String lastName = binding.editTextRegisterLastName.getText().toString();

        if(!email.isEmpty() && !password.isEmpty() && !firstName.isEmpty() && !lastName.isEmpty()){
            registerViewModel.register(email,password,firstName,lastName);
        }
        else{
            displayMessage("Please enter information before you register");
        }
    }

    private void displayMessage(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}