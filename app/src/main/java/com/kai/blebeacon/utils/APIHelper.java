package com.kai.blebeacon.utils;

import androidx.annotation.NonNull;

import com.kai.blebeacon.listener.GetItemsListener;
import com.kai.blebeacon.listener.LoginListener;
import com.kai.blebeacon.listener.ProfileRetrivalListener;
import com.kai.blebeacon.listener.ProfileUpdateListener;
import com.kai.blebeacon.listener.RegisterationListener;
import com.kai.blebeacon.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class APIHelper {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String TAG = "APIHelper";

    public APIHelper() {
    }

    public static void login(String email, String password, LoginListener loginListener) {
        FormBody formBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url("https://node-authenticator-uncc.herokuapp.com/api/auth/login")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    try {
                        JSONObject res = new JSONObject(response.body().string());
                        String id = res.getString("id");
                        String jwtToken = res.getString("token");
                        String customerId = res.getString("customerId");

                        loginListener.loginSuccessfull(id, jwtToken, customerId);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        JSONObject loginFailure = new JSONObject(response.body().string());
                        loginListener.loginFailure(loginFailure.getString("message"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public static void register(String email, String password, String firstName, String lastName, RegisterationListener registerationListener) {
        FormBody formBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .add("firstName", firstName)
                .add("lastName", lastName)
                .build();

        Request request = new Request.Builder()
                .url("https://node-authenticator-uncc.herokuapp.com/api/auth/signup")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    JSONObject jsonMessage = null;
                    try {
                        jsonMessage = new JSONObject(response.body().string());
                        registerationListener.registerationSuccessful();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    JSONObject jsonErrorMessage = null;
                    try {
                        jsonErrorMessage = new JSONObject(response.body().string());
                        registerationListener.registerationFailure(jsonErrorMessage.getString("message"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }

    public static void profileRetrival(String jwtToken, ProfileRetrivalListener profileRetrivalListener){


        HttpUrl url = HttpUrl.parse("https://node-authenticator-uncc.herokuapp.com/api/auth/profile").newBuilder()
                .addQueryParameter("token",jwtToken)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    try {
                        JSONObject res = new JSONObject(response.body().string());
                        JSONObject user = res.getJSONObject("data");
                        String email = user.getString("email");
                        String firstname = user.getString("firstName");
                        String lastname = user.getString("lastName");

                        profileRetrivalListener.profileRetrivalSuccessful(new User(firstname,lastname,email));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        JSONObject profileRetrivalFailure = new JSONObject(response.body().toString());
                        profileRetrivalListener.profileRetrivalFailure(profileRetrivalFailure.getString("message"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static void profileUpdate(User user, ProfileUpdateListener profileUpdateListener){

        FormBody formBody = new FormBody.Builder()
                .add("id", user.getId())
                .add("firstName", user.getFirstName())
                .add("lastName", user.getLastName())
                .build();

        HttpUrl url = HttpUrl.parse("https://node-authenticator-uncc.herokuapp.com/api/auth/profile").newBuilder()
                .addQueryParameter("token",user.getJwtToken())
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    profileUpdateListener.profileUpdateSuccessful();
                } else {
                    try {
                        JSONObject updateFailure = new JSONObject(response.body().string());
                        profileUpdateListener.profileUpdateFailure(updateFailure.getString("message"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public static void getItems(String region, GetItemsListener getItemsListener){

    }
}
