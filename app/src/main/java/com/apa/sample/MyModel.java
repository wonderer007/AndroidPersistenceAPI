package com.apa.sample;

import com.android.persistence.api.ApaEntity;
import com.android.persistence.api.Column;
import com.android.persistence.api.Entity;
import com.android.persistence.api.Order;
import com.android.persistence.api.Type;

@Entity
public class MyModel extends ApaEntity{

    @Order(value = 1)
    @Column(name="id", primaryKey = true, type = Type.INTEGER)
    private int id;

    @Order(value = 2)
    @Column(name="name", notNull = true)
    private String name;

    @Order(value = 3)
    @Column(name="flag", notNull = true, type = Type.INTEGER)
    private boolean flag;

    @Order(value = 4)
    @Column(name="value", notNull = true, type = Type.FLOAT)
    private double value;

    public MyModel(int id, String name, int flag, double value) {
        this.id = id;
        this.name = name;
        this.flag = flag!=0;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public boolean getFlag() {
        return flag;
    }

    public double getValue() {
        return value;
    }

    @Override
    public Class getEntityClass() {
        return MyModel.class;
    }

    @Override
    public MyModel getEntityInstance(){
        return this;
    }
}
