package com.android.persistence.api;

import android.content.ContentValues;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class ApaRepository<T> {

    private EntityBuilder<T> entityBuilder;
    private Class entity;

    public ApaRepository(final Class classObject){
        this.entity = classObject;
        if (entity.isAnnotationPresent(Entity.class)) {
            Field[] fields = entity.getDeclaredFields();
            Collections.sort(Arrays.asList(fields), new Comparator<Field>() {
                @Override
                public int compare(Field o1, Field o2) {
                    Order or1 = o1.getAnnotation(Order.class);
                    Order or2 = o2.getAnnotation(Order.class);

                    if (or1 != null && or2 != null) {
                        return or1.value() - or2.value();
                    } else
                    if (or1 != null && or2 == null) {
                        return -1;
                    } else
                    if (or1 == null && or2 != null) {
                        return 1;
                    }
                    return o1.getName().compareTo(o2.getName());
                }
            });
            List<Attrb> attributes = new ArrayList<>();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)) {
                    Column column = field.getAnnotation(Column.class);
                    Attrb attr = new Attrb(column.name(), column.primaryKey(), column.notNull(), column.uniqueKey(), column.type(), column.autoIncrement());
                    attributes.add(attr);
                }
            }
            entityBuilder = new EntityBuilder(getInstance(), entity.getSimpleName(), attributes);
        }
    }

    public void save(ApaEntity entity){
        if(entityBuilder!=null){
            entityBuilder.save(entity);
        }
    }

    public List<T> getAll(){
        if(entityBuilder!=null){
            return entityBuilder.getAll();
        }
        return null;
    }

    protected abstract ApaRepository getInstance();


    public Class getEntity() {
        return entity;
    }

    public ContentValues getFieldValues(ApaEntity entity) {
        ContentValues contentValues = new ContentValues();
        Field[] fields = entity.getEntityClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                if (field.isAnnotationPresent(Column.class)) {
                    field.setAccessible(true);
                    Column column = field.getAnnotation(Column.class);
                    if(!column.autoIncrement()) {
                        Object obj = field.get(entity.getEntityInstance());
                        if(obj instanceof Integer){
                            contentValues.put(column.name(), (int)obj);
                        } else if(obj instanceof Double){
                            contentValues.put(column.name(), (double)obj);
                        } else if(obj instanceof Float){
                            contentValues.put(column.name(), (float)obj);
                        } else if(obj instanceof String){
                            contentValues.put(column.name(), (String)obj);
                        } else if(obj instanceof Boolean){
                            contentValues.put(column.name(), (boolean)obj);
                        } else if(obj instanceof Long){
                            contentValues.put(column.name(), (long)obj);
                        }
                    }
                }
            } catch (IllegalAccessException exception) {
                exception.printStackTrace();
            }
        }
        return contentValues;
    }
}
