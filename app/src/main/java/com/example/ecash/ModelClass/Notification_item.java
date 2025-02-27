package com.example.ecash.ModelClass;

public class Notification_item {

    private int imageView;
    String title;
    String description;
    String date;

    public Notification_item(int imageView, String title, String description, String date) {
        this.imageView = imageView;
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public int getImageView() {
        return imageView;
    }
}
