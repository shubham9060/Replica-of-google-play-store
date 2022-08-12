package com.example.assignment6.Model;

import java.util.List;

public class AllCategory {
    String CategoryTitle;
    List<CategoryItem> categoryItemList;

    public AllCategory(String categoryTitle, List<CategoryItem> categoryItemList) {
        this.CategoryTitle = categoryTitle;
        this.categoryItemList = categoryItemList;
    }

    public List<CategoryItem> getCategoryItemList() {
        return categoryItemList;
    }

    public String getCategoryTitle() {
        return CategoryTitle;
    }

}
