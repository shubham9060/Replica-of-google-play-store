package com.example.assignment6.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment6.retrofit.ApiInterface;
import com.example.assignment6.Model.AllCategory;
import com.example.assignment6.Model.CategoryItem;
import com.example.assignment6.R;
import com.example.assignment6.retrofit.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder> {
    private final Context context;
    private final List<AllCategory> allCategoryList;
    private int page = 1;

    public MainRecyclerAdapter(Context context, List<AllCategory> allCategoryList) {
        this.context = context;
        this.allCategoryList = allCategoryList;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate
                (R.layout.main_recycler_row_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.categoryTitle.setText(allCategoryList.get(position).getCategoryTitle());
        LinearLayoutManager layoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false);
        CategoryItemRecyclerAdapter itemRecyclerAdapter = new CategoryItemRecyclerAdapter
                (context, allCategoryList.get(position).getCategoryItemList());
        holder.itemrecycler.setLayoutManager(layoutManager);
        holder.itemrecycler.setAdapter(itemRecyclerAdapter);
        holder.itemrecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (layoutManager.findLastCompletelyVisibleItemPosition() == 49) {
                    ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                    page++;

                    apiInterface.getposts(page).enqueue(new Callback<List<CategoryItem>>() {
                        @Override
                        public void onResponse(@NonNull Call<List<CategoryItem>> call,
                                               @NonNull Response<List<CategoryItem>> response) {
                            assert response.body() != null;
                            if (response.body().size() > 0) {
                                List<CategoryItem> categoryItemList = response.body();
                                CategoryItemRecyclerAdapter itemRecyclerAdapter =
                                        new CategoryItemRecyclerAdapter(context, categoryItemList);
                                holder.itemrecycler.setLayoutManager(layoutManager);
                                holder.itemrecycler.setAdapter(itemRecyclerAdapter);

                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<CategoryItem>> call,
                                              @NonNull Throwable t) {

                            Log.e("error", t.getLocalizedMessage());
                        }
                    });
                }

            }


        });
    }

    @Override
    public int getItemCount() {
        return allCategoryList.size();
    }

    public static final class MainViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTitle;
        RecyclerView itemrecycler;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTitle = itemView.findViewById(R.id.tvCatTitle);
            itemrecycler = itemView.findViewById(R.id.item_recycler);

        }
    }


}
