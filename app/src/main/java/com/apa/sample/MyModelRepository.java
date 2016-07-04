package com.apa.sample;

import com.android.persistence.api.ApaRepository;

public class MyModelRepository extends ApaRepository<MyModel>{

    public MyModelRepository(Class classObject){
        super(classObject);
    }

    @Override
    protected ApaRepository getInstance() {
        return this;
    }
}
