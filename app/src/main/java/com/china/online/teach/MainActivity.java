package com.china.online.teach;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;

/**
 * Created by hanlu on 9/1/17.
 */

public class MainActivity extends Activity {

    private Gallery mGallery;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mGallery = (Gallery)findViewById(R.id.gallery);
        try {
            mGallery.setAdapter(new ImageAdapter(this));
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                MainActivity.this.setTitle(String.valueOf(position));
            }
        });
    }

    /*
     * class ImageAdapter is used to control gallery source and operation.
     */
    private class ImageAdapter extends BaseAdapter {
        private Context mContext;
        Integer[] posterID = { R.drawable.one, R.drawable.two,
                R.drawable.three, R.drawable.four};
        public ImageAdapter(Context c) throws IllegalArgumentException, IllegalAccessException{
            mContext = c;

        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub

            return posterID.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub

            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            ImageView i = new ImageView (mContext);
            
            i.setImageResource(posterID[position]);
            i.setScaleType(ImageView.ScaleType.FIT_XY);

            i.setLayoutParams(new Gallery.LayoutParams(1000, 2000));
            return i;
        }

    };
}