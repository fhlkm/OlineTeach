package com.china.online.teach;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by hanlu.feng on 9/11/2017.
 */

public class DetailActivity extends AppCompatActivity {

    ImageView mImageView;
    TextView video_description;
    Button mPrevious;
    Button mNext;
    TextView mVideoIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail");
        initUI();
    }
    private void initUI(){
        mImageView = (ImageView)findViewById(R.id.video_play);
        video_description = (TextView)findViewById(R.id.video_detail_description);
        mPrevious = (Button)findViewById(R.id.previous_video);
        mNext = (Button)findViewById(R.id.next_video);
        mVideoIndex = (TextView)findViewById(R.id.current_video_index);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            closeActivity();
        }
        return true;
    }

    private void closeActivity(){
        this.finish();
    }
}
