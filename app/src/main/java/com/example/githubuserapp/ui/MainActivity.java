package com.example.githubuserapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubuserapp.R;
import com.example.githubuserapp.data.response.GithubSearchResponse;
import com.example.githubuserapp.data.response.GithubUser;
import com.example.githubuserapp.data.retrofit.api_config;
import com.example.githubuserapp.data.retrofit.api_services;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Github_user_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        api_services api_services = api_config.getapi_services();
        Call<GithubSearchResponse> call = api_services.searchUsers("ilham");

        call.enqueue(new Callback<GithubSearchResponse>() {
            @Override
            public void onResponse(Call<GithubSearchResponse> call, Response<GithubSearchResponse> response) {
                Log.d("Response", response.toString());
                if (response.isSuccessful() && response.body() != null) {
                    List<GithubUser> users = response.body().getUsers();
                    if (users.isEmpty()) {
                        Toast.makeText(MainActivity.this, "No users found", Toast.LENGTH_SHORT).show();
                    } else {
                        adapter = new Github_user_adapter(users);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to get users", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GithubSearchResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Misalnya, menyimpan posisi item di RecyclerView
        if (recyclerView != null && recyclerView.getLayoutManager() != null) {
            int position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            outState.putInt("position", position);
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Misalnya, memulihkan posisi item di RecyclerView
        int position = savedInstanceState.getInt("position", 0);
        if (recyclerView != null && recyclerView.getLayoutManager() != null) {
            recyclerView.getLayoutManager().scrollToPosition(position);
        }
    }
}