package com.example.praneet.todo;

import java.io.Serializable;

public class ToDo implements Serializable {

    private String title, subtitle;
    private boolean checked;

    public ToDo() {

    }

    public ToDo(String title, String subtitle, boolean checked) {
        this.title = title;
        this.subtitle = subtitle;
        this.checked = checked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
