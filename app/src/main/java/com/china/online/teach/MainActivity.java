package com.china.online.teach;

import android.app.Activity;
import android.os.Bundle;

import com.mikepenz.materialdrawer.DrawerBuilder;

/**
 * Created by hanlu on 9/1/17.
 */

public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        new DrawerBuilder().withActivity(this).build();
    }
}
