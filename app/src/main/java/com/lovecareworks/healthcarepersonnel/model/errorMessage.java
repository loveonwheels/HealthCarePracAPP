package com.lovecareworks.healthcarepersonnel.model;

/**
 * Created by ghost on 2/11/16.
 */
public class errorMessage {

    private boolean satus = false;

    public errorMessage(boolean satus, String message) {
        this.satus = satus;
        this.message = message;
    }

    private String message = "";

    public errorMessage() {
    }

    public boolean isSatus() {
        return satus;
    }

    public void setSatus(boolean satus) {
        this.satus = satus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
