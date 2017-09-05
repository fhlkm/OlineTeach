package com.china.online.teach;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

/**
 * Created by hanlu on 9/1/17.
 */

public class MainActivity extends Activity {
    private Drawer mDrawer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Online-Teach");
        SecondaryDrawerItem search = new SecondaryDrawerItem().withIdentifier(2).withName("Search Catalog");
        SecondaryDrawerItem full_catalog = new SecondaryDrawerItem().withIdentifier(2).withName("Full Catalog");
        SecondaryDrawerItem my_catalog = new SecondaryDrawerItem().withIdentifier(2).withName("My Enrollments");
        mDrawer = new DrawerBuilder().withActivity(this).addDrawerItems(
                item1,
                new DividerDrawerItem(),
                search,
//                new SecondaryDrawerItem(),
                full_catalog,
//                new SecondaryDrawerItem(),
                my_catalog
        ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        return true;
                    }
                })
                .build();
    }
    private void initUI(){


    }
}
