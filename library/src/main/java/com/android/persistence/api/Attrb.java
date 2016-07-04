package com.android.persistence.api;

public class Attrb {

    private String name;
    private boolean primaryKey;
    private boolean notNull;
    private boolean uniqueKey;
    private Type type;
    private boolean autoIncrement;

    public Attrb(String name, boolean primaryKey, boolean notNull, boolean uniqueKey, Type type, boolean autoIncrement) {
        this.name = name;
        this.primaryKey = primaryKey;
        this.notNull = notNull;
        this.uniqueKey = uniqueKey;
        this.type = type;
        this.autoIncrement = autoIncrement;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return " "+ name +" "+ type.getType()+ (primaryKey?" primary key":"") + (type==Type.INTEGER && autoIncrement?" autoincrement":"") + (uniqueKey?" unique":"") + (notNull?" not null":"") + " ";
    }
}
