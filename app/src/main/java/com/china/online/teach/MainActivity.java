package com.china.online.teach;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanlu on 9/1/17.
 */

public class MainActivity extends Activity {
    private Drawer mDrawer;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mRecyclerAdapter;
    private List<Item> viewData = new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;
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
        loadData();
        initUI();

    }
    private void initUI(){
        mRecyclerView = findViewById(R.id.video_recycle);
        mLinearLayoutManager= new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerAdapter = new RecyclerAdapter(this, viewData);
        mRecyclerView.setAdapter(mRecyclerAdapter);

    }

    private void loadData(){
        viewData.add(new Item("PicOne", R.drawable.one));
        viewData.add(new Item("PicTwo", R.drawable.two));
        viewData.add(new Item("PicThree", R.drawable.three));
        viewData.add(new Item("PicFour", R.drawable.four));
    }
}
