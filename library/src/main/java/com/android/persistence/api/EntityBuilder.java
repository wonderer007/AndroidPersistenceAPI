package com.android.persistence.api;

import android.text.TextUtils;

import java.util.List;

public class EntityBuilder<T> {

    private final ApaRepository<T> classObject;
    private String entityName;
    private List<Attrb> attributes;

    public EntityBuilder(ApaRepository classObject, String entityName, List<Attrb> attributes) {
        this.classObject = classObject;
        this.entityName = entityName;
        this.attributes = attributes;
        create();
    }

    private void create(){
        String sql = "create table if not exists "+ entityName + " ("+TextUtils.join(",", attributes)+");";
        DatabaseAdapter.getInstance().executeSQL(sql);
    }

    public void save(ApaEntity entity){
        DatabaseAdapter.getInstance().insert(entityName, classObject.getFieldValues(entity));
    }

    public List<T> getAll(){
        return DatabaseAdapter.getInstance().getAll(classObject);
    }
}
