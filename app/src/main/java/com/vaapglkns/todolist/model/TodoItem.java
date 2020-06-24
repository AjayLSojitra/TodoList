package com.vaapglkns.todolist.model;

public class TodoItem {
    private String id;
    private String title;
    private String desc;
    private String imagePath;
    private boolean isChecked;

    public TodoItem(String id, String title, String desc, String imagePath, boolean isChecked) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.imagePath = imagePath;
        this.isChecked = isChecked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
