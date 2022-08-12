package com.example.assignment6.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.assignment6.Adapter.MainRecyclerAdapter;
import com.example.assignment6.constant.Constant;
import com.example.assignment6.retrofit.ApiInterface;
import com.example.assignment6.Model.AllCategory;
import com.example.assignment6.Model.CategoryItem;
import com.example.assignment6.R;
import com.example.assignment6.retrofit.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayStoreMainActivity extends AppCompatActivity {
    private RecyclerView mainCategoryRecycler;
    private ApiInterface apiInterface;
    private List<CategoryItem> categoryItemList;
    private List<AllCategory> allCategoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_store_main);
        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        mainCategoryRecycler = findViewById(R.id.rvHomeScreen);
        getMyData();
    }

    private void getMyData() {
        apiInterface.getposts(1).enqueue(new Callback<List<CategoryItem>>() {
            @Override
            public void onResponse(@NonNull Call<List<CategoryItem>> call, @NonNull Response<List<CategoryItem>> response) {
                assert response.body() != null;
                if (response.body().size() > 0) {
                    categoryItemList = response.body();
                    allCategoryList = new ArrayList<>();
                    allCategoryList.add(new AllCategory(Constant.Category_one, categoryItemList));
                    allCategoryList.add(new AllCategory(Constant.Category_two, categoryItemList));
                    allCategoryList.add(new AllCategory(Constant.Category_three, categoryItemList));
                    allCategoryList.add(new AllCategory(Constant.Category_four, categoryItemList));
                    allCategoryList.add(new AllCategory(Constant.Category_five, categoryItemList));
                    allCategoryList.add(new AllCategory(Constant.Category_six, categoryItemList));
                    setMainCategoryRecycler(allCategoryList);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CategoryItem>> call, @NonNull Throwable t) {
                Toast.makeText(PlayStoreMainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void setMainCategoryRecycler(List<AllCategory> allCategoryList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mainCategoryRecycler.setLayoutManager(layoutManager);
        MainRecyclerAdapter mainRecyclerAdapter = new MainRecyclerAdapter(this, allCategoryList);
        mainCategoryRecycler.setAdapter(mainRecyclerAdapter);
    }
}