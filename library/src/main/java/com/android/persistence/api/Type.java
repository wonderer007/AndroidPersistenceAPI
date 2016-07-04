package com.android.persistence.api;

public enum Type {

    INTEGER("integer"), FLOAT("float"), TEXT("text");

    private String type;

    public String getType() {
        return type;
    }

    Type(String type){
        this.type = type;
    }
}
