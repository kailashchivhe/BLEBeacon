package com.kai.blebeacon.ui.login;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.kai.blebeacon.databinding.FragmentLoginBinding;
import com.kai.blebeacon.model.LoginData;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor spEditor;
    LoginViewModel loginViewModel;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        loginViewModel.getLoginLiveData().observe(getViewLifecycleOwner(), new Observer<LoginData>() {
            @Override
            public void onChanged(LoginData loginData) {
                if(loginData != null){
                    //save data in shared preferences and goto home
                    spEditor.putString("jwtToken", loginData.getJwtToken());
                    spEditor.apply();
                    navigateToHome();
                }
            }
        });
        loginViewModel.getMessageLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(!s.isEmpty()){
                    displayMessageToast(s);
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sharedPreferences = getActivity().getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
        String jwtToken = sharedPreferences.getString("jwtToken", "");
        spEditor = sharedPreferences.edit();

        if(jwtToken.length() != 0 ){
            navigateToHome();
        }

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginClicked();
            }
        });
        binding.buttonLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegisterClicked();
            }
        });
    }

    private void onRegisterClicked() {
        NavHostFragment.findNavController(this).navigate(R.id.action_LoginFragment_to_RegisterFragment);
    }

    private void onLoginClicked() {
        String email = binding.editTextLoginEmail.getText().toString();
        String password = binding.editTextLoginPassword.getText().toString();
        if(!email.isEmpty() && !password.isEmpty()){
            //do login
            loginViewModel.login(email,password);
        }
        else{
            displayMessageToast("Please enter email and password before login");
        }
    }

    private void displayMessageToast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    private void navigateToHome() {
        displayMessageToast("User Logged In");
        //Navigate to home
        NavHostFragment.findNavController(this).navigate(R.id.action_LoginFragment_to_HomeFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}