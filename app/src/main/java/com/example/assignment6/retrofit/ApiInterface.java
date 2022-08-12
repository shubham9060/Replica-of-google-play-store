package com.example.assignment6.retrofit;

import com.example.assignment6.Model.CategoryItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {
    @GET("photos")
    Call<List<CategoryItem>> getposts(@Query("albumId") int page);
}
