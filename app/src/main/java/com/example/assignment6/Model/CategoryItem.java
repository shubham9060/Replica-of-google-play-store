package com.example.assignment6.Model;

public class CategoryItem {
   private final String title;
    private final String url;

    public CategoryItem(String title, String url) {
        this.title = title;
        this.url = url;
    }


    public String getTitle() {
     return title;
 }

    public String getBody() {
        return url;
    }


}
