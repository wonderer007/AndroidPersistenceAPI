package com.apa.sample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyModel m1 = new MyModel(11, "hello1", 1, 23.32);

        MyModelRepository repo = new MyModelRepository(MyModel.class);
        repo.save(m1);

       Toast.makeText(this, ""+repo.getAll().get(0).getValue(), Toast.LENGTH_SHORT ).show();

    }
}
