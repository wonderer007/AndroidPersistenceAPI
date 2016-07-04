package com.android.persistence.api;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter<T> {

    private static DatabaseAdapter databaseAdapter;

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    private DatabaseAdapter(){
        databaseHelper = new DatabaseHelper(ApaConfiguration.getInstance().getDatabaseName(), ApaConfiguration.getInstance().getDatabaseVersion());
    }

    public static DatabaseAdapter getInstance(){
        if(databaseAdapter == null) {
            databaseAdapter = new DatabaseAdapter();
        }
        return databaseAdapter;
    }

    public synchronized void executeSQL(String sql){
        try {
            database = databaseHelper.getWritableDatabase();
            database.execSQL(sql);
            database.close();
        }
        catch (SQLException ex){
            database.close();
        }
    }

    public synchronized List<T> getAll(ApaRepository entity){
        List<T> list = new ArrayList<>();
        try {
            database = databaseHelper.getReadableDatabase();
            Cursor cursor = database.rawQuery("select * from " + entity.getEntity().getSimpleName(), null);
            if(cursor!=null){
                cursor.moveToFirst();
            }

            int records = cursor.getCount();
            for(int i=0;i<records;i++){
                int columns = cursor.getColumnCount();
                List<Object> values = new ArrayList<>();
                for(int j=0;j<columns;j++){
                    int type = cursor.getType(j);
                    if(type == Cursor.FIELD_TYPE_INTEGER){
                        values.add(cursor.getInt(j));
                    } else if(type == Cursor.FIELD_TYPE_FLOAT){
                        Float floatValue = new Float(cursor.getFloat(j));
                        Double doubleValue = new Double(floatValue.toString());
                        values.add(doubleValue);
                    } else if(type == Cursor.FIELD_TYPE_STRING){
                        values.add(cursor.getString(j));
                    }
                }
                Object obj = Class.forName(entity.getEntity().getName()).getDeclaredConstructors()[0].newInstance(values.toArray());
                list.add((T)obj);
                cursor.moveToNext();
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        database.close();
        return list;
    }

    public synchronized long insert(String tableName, ContentValues contentValues){
        database = databaseHelper.getWritableDatabase();
        long row = database.insert(tableName, null, contentValues);
        database.close();
        return row;
    }
}
