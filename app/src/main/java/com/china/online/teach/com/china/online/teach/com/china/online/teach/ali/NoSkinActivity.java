package com.china.online.teach.com.china.online.teach.com.china.online.teach.ali;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.aliyun.vodplayer.downloader.AliyunDownloadInfoListener;
import com.aliyun.vodplayer.downloader.AliyunDownloadManager;
import com.aliyun.vodplayer.downloader.AliyunDownloadMediaInfo;
import com.aliyun.vodplayer.downloader.AliyunRefreshPlayAuthCallback;
import com.aliyun.vodplayer.media.AliyunDataSource;
import com.aliyun.vodplayer.media.AliyunLocalSource;
import com.aliyun.vodplayer.media.AliyunPlayAuth;
import com.aliyun.vodplayer.media.AliyunVodPlayer;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.aliyun.vodplayerview.utils.ScreenUtils;
import com.china.online.teach.R;
import com.china.online.teach.utils.Formatter;

import java.util.List;

public class NoSkinActivity extends AppCompatActivity {

    private static final String TAG = NoSkinActivity.class.getSimpleName();

    private SurfaceView surfaceView;

    private Button prepareBtn;
    private Button playBtn;
    private Button pauseBtn;
    private Button releaseBtn;
    private Button changeQualityBtn;
    private Button replayBtn;
    private Button downBtn;
    private Button downStopBtn;
    private AliyunDownloadManager aliyunDownloadManager;
    private AliyunDownloadMediaInfo downloadInfo;

    private TextView positionTxt;
    private TextView durationTxt;
    private SeekBar progressBar;

    private TextView videoWidthTxt;
    private TextView videoHeightTxt;

    private AliyunVodPlayer aliyunVodPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noskin);

        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);

        prepareBtn = (Button) findViewById(R.id.prepare);
        playBtn = (Button) findViewById(R.id.play);
        pauseBtn = (Button) findViewById(R.id.pause);
        releaseBtn = (Button) findViewById(R.id.release);
        changeQualityBtn = (Button) findViewById(R.id.change_quality);
        replayBtn = (Button) findViewById(R.id.replay);
        downBtn = (Button) findViewById(R.id.download);
        downStopBtn = (Button) findViewById(R.id.downloadStop);

        positionTxt = (TextView) findViewById(R.id.currentPosition);
        durationTxt = (TextView) findViewById(R.id.totalDuration);
        progressBar = (SeekBar) findViewById(R.id.progress);

        videoWidthTxt = (TextView) findViewById(R.id.width);
        videoHeightTxt = (TextView) findViewById(R.id.height);
//        prepareBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                aliyunVodPlayer.prepareAsync();
//            }
//        });
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aliyunVodPlayer.start();
            }
        });
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aliyunVodPlayer.pause();
            }
        });
        releaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aliyunVodPlayer.stop();
                aliyunVodPlayer.release();
            }
        });
        changeQualityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aliyunVodPlayer.changeQuality(IAliyunVodPlayer.QualityValue.QUALITY_FLUENT);
            }
        });
        replayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replay();
            }
        });


        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    aliyunVodPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.d(TAG, "surfaceCreated");
                aliyunVodPlayer.setDisplay(holder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.d(TAG, "surfaceChanged");
                aliyunVodPlayer.surfaceChanged();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.d(TAG, "surfaceDestroyed");
            }
        });

        initVodPlayer();
        setPlaySource();
        aliyunVodPlayer.prepareAsync();


        aliyunDownloadManager = AliyunDownloadManager.getInstance(NoSkinActivity.this.getApplicationContext());
        downBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donwloadVideo();
            }
        });
        downStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(downloadInfo != null) {
                    aliyunDownloadManager.stopDownloadMedia(downloadInfo);
                }
            }
        });
    }

    private void initVodPlayer() {
        aliyunVodPlayer = new AliyunVodPlayer(this);
        aliyunVodPlayer.setOnPreparedListener(new IAliyunVodPlayer.OnPreparedListener() {
            @Override
            public void onPrepared() {
                Toast.makeText(NoSkinActivity.this, "准备成功", Toast.LENGTH_SHORT).show();
                //准备成功之后可以调用start方法开始播放
                showVideoProgressInfo();
                showVideoSizeInfo();
                aliyunVodPlayer.start();
            }
        });
        aliyunVodPlayer.setOnErrorListener(new IAliyunVodPlayer.OnErrorListener() {
            @Override
            public void onError(int arg0, int arg1, String msg) {
                Toast.makeText(NoSkinActivity.this, "失败！！！！原因：" + msg, Toast.LENGTH_SHORT).show();
            }
        });
        aliyunVodPlayer.setOnCompletionListener(new IAliyunVodPlayer.OnCompletionListener() {
            @Override
            public void onCompletion() {
                Log.d(TAG, "播放结束--- ");
                stopUpdateTimer();
            }
        });
        aliyunVodPlayer.setOnBufferingUpdateListener(new IAliyunVodPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(int percent) {
                Log.d(TAG, "缓冲进度更新--- " + percent);
                updateBufferingProgress(percent);
            }
        });
//        aliyunVodPlayer.setDisplay(surfaceView.getHolder());
        aliyunVodPlayer.setOnChangeQualityListener(new IAliyunVodPlayer.OnChangeQualityListener() {
            @Override
            public void onChangeQualitySuccess(String finalQuality) {
                Log.d(TAG, "切换清晰度成功");

                aliyunVodPlayer.changeQuality(IAliyunVodPlayer.QualityValue.QUALITY_FLUENT);
            }

            @Override
            public void onChangeQualityFail(int code, String msg) {
                Log.d(TAG, "切换清晰度失败。。。" + code + " ,  " + msg);
            }
        });
        aliyunVodPlayer.enableNativeLog();


    }

    private void setPlaySource() {
        String type = getIntent().getStringExtra("type");
        if ("datasource".equals(type)) {
//        构建播放数据源
            AliyunDataSource.AliyunDataSourceBuilder aliyunDataSourceBuilder = new AliyunDataSource.AliyunDataSourceBuilder(this);
            String vid = getIntent().getStringExtra("vid");
            String keyId = getIntent().getStringExtra("keyid");
            String secret = getIntent().getStringExtra("secret");
            String playKey = getIntent().getStringExtra("playKey");
            aliyunDataSourceBuilder.setAccessKeySecret(secret);
            aliyunDataSourceBuilder.setVideoId(vid);
            aliyunDataSourceBuilder.setPlayKey(playKey);
            aliyunDataSourceBuilder.setAccessKeyId(keyId);
            aliyunDataSourceBuilder.setQuality(IAliyunVodPlayer.QualityValue.QUALITY_ORIGINAL);
            AliyunDataSource aliyunDataSource = aliyunDataSourceBuilder.build();
            aliyunVodPlayer.setDataSource(aliyunDataSource);
        } else if ("authInfo".equals(type)) {
            //auth方式

            //NOTE： 注意过期时间。特别是重播的时候，可能已经过期。所以重播的时候最好重新请求一次服务器。
            String vid = getIntent().getStringExtra("vid");
            String authInfo = getIntent().getStringExtra("authinfo");
            AliyunPlayAuth.AliyunPlayAuthBuilder aliyunPlayAuthBuilder = new AliyunPlayAuth.AliyunPlayAuthBuilder();
            aliyunPlayAuthBuilder.setVid(vid);
            aliyunPlayAuthBuilder.setPlayAuth(authInfo);
            aliyunPlayAuthBuilder.setQuality(IAliyunVodPlayer.QualityValue.QUALITY_ORIGINAL);
            aliyunVodPlayer.setAuthInfo(aliyunPlayAuthBuilder.build());
        } else if ("localSource".equals(type)) {
            //本地播放
            String url = getIntent().getStringExtra("url");
            AliyunLocalSource.AliyunLocalSourceBuilder asb = new AliyunLocalSource.AliyunLocalSourceBuilder();
            asb.setSource(url);
            aliyunVodPlayer.setLocalSource(asb.build());
        }
    }

    /**
     * 重播功能。
     */
    private void replay() {
        AliyunVodPlayer tmpVodPlayer = aliyunVodPlayer;
        if (tmpVodPlayer != null) {
            tmpVodPlayer.stop();
            tmpVodPlayer.release();
        }
        aliyunVodPlayer = null;
        stopUpdateTimer();

        initVodPlayer();
        setPlaySource();//如果是用的auth模式，注意过期时间。

        if (surfaceView != null) {
            //刷新surfaceHolder
            surfaceView.setVisibility(View.GONE);
            surfaceView.setVisibility(View.VISIBLE);
        }
        aliyunVodPlayer.prepareAsync();
    }

    private void showVideoSizeInfo() {
        videoWidthTxt.setText("视频宽：" + aliyunVodPlayer.getVideoWidth() + " , ");
        videoHeightTxt.setText("视频高：" + aliyunVodPlayer.getVideoHeight() + "   ");
    }

    private void updateBufferingProgress(int percent) {
        int duration = (int) aliyunVodPlayer.getDuration();
        progressBar.setSecondaryProgress((int) (duration * percent * 1.0f / 100));
    }

    private void showVideoProgressInfo() {
        int curPosition = (int) aliyunVodPlayer.getCurrentPosition();
        positionTxt.setText(Formatter.formatTime(curPosition));
        int duration = (int) aliyunVodPlayer.getDuration();
        durationTxt.setText(Formatter.formatTime(duration));

        progressBar.setMax(duration);
        progressBar.setProgress(curPosition);

        startUpdateTimer();
    }

    private void startUpdateTimer() {
        Log.d(TAG, "播放进度更新--- ");
        progressUpdateTimer.removeMessages(0);
        progressUpdateTimer.sendEmptyMessageDelayed(0, 1000);
    }

    private void stopUpdateTimer() {
        progressUpdateTimer.removeMessages(0);
    }

    private Handler progressUpdateTimer = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            showVideoProgressInfo();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        //保存播放器的状态，供resume恢复使用。
        resumePlayerState();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //onStop中记录下来的状态，在这里恢复使用
        savePlayerState();
    }


    //用来记录前后台切换时的状态，以供恢复。
    private IAliyunVodPlayer.PlayerState mPlayerState;

    private void resumePlayerState() {
        if (mPlayerState == IAliyunVodPlayer.PlayerState.Paused) {
            aliyunVodPlayer.pause();
        } else if (mPlayerState == IAliyunVodPlayer.PlayerState.Started) {
            aliyunVodPlayer.start();
        }
    }

    private void savePlayerState() {
        mPlayerState = aliyunVodPlayer.getPlayerState();
        if (aliyunVodPlayer.isPlaying()) {
            //然后再暂停播放器
            aliyunVodPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        aliyunVodPlayer.stop();
        aliyunVodPlayer.release();
        stopUpdateTimer();
        progressUpdateTimer = null;

        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {                //转为竖屏了。
            //显示状态栏
            this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            surfaceView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

            //设置view的布局，宽高之类
            ViewGroup.LayoutParams surfaceViewLayoutParams = surfaceView.getLayoutParams();
            surfaceViewLayoutParams.height = (int) (ScreenUtils.getWight(this) * 9.0f / 16);
            surfaceViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;

        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {                //转到横屏了。
            //隐藏状态栏
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            surfaceView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN);
            //设置view的布局，宽高
            ViewGroup.LayoutParams surfaceViewLayoutParams = surfaceView.getLayoutParams();
            surfaceViewLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            surfaceViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;

        }
    }


    private void donwloadVideo() {
        String type = getIntent().getStringExtra("type");
        if ("datasource".equals(type)) {
//        构建播放数据源
            AliyunDataSource.AliyunDataSourceBuilder aliyunDataSourceBuilder = new AliyunDataSource.AliyunDataSourceBuilder(this);
            String vid = getIntent().getStringExtra("vid");
            String keyId = getIntent().getStringExtra("keyid");
            String secret = getIntent().getStringExtra("secret");
            String playKey = getIntent().getStringExtra("playKey");
            aliyunDataSourceBuilder.setAccessKeySecret(secret);
            aliyunDataSourceBuilder.setVideoId(vid);
            aliyunDataSourceBuilder.setPlayKey(playKey);
            aliyunDataSourceBuilder.setAccessKeyId(keyId);
            aliyunDataSourceBuilder.setQuality(IAliyunVodPlayer.QualityValue.QUALITY_ORIGINAL);
            AliyunDataSource aliyunDataSource = aliyunDataSourceBuilder.build();

            aliyunDownloadManager.setDownloadInfoListener(downloadInfoListener);
            aliyunDownloadManager.prepareDownloadMedia(aliyunDataSource);
        } else if ("authInfo".equals(type)) {
//        构建播放数据源
            String vid = "c31da43251b148b2a4bdf4f540f19116";
            String authInfo = "c31da43251b148b2a4bdf4f540f19116,eyJTZWN1cml0eVRva2VuIjoiQ0FJU3VBSjFxNkZ0NUIyeWZTaklwS1hIQ05uQm1ybHgvYUdLYlJMZ3BsQU1lZUJvdW9lZW16ejJJSHBLZVhkdUFlQVhzL28wbW1oWjcvWVlsck1xRk1NWUh4eVlOWkl1dHMwUG9WMytKcExGc3QySjZyOEpqc1ZhL0xsanUxMnBzdlhKYXNEVkVmbkFHSjcwR1VpbSt3WjN4YnpsRHh2QU8zV3VMWnlPajdOK2M5MFRSWFBXUkRGYUJkQlFWRTBBenNvR0xpbnBNZis4UHdURG4zYlFiaTV0cGhFdXNXZDIrSVc1ek1yK2pCL0Nsdy9XeCtRUHRwenRBWlcxRmI0T1dxMXlTTkNveHVkN1c3UGMyU3BMa1hodytieHhrYlpQOUVXczNMamZJU0VJczByZGJiV0VyNFV4ZFZVbFB2VmxJY01lOHFpZ3o4OGZrL2ZJaW9INnh5eEtPZXhvU0NuRlRPaWl1cENlUnI3M2FJNXBKT3VsWUNxVmc0bmZiSU9KbWdjbGNHOGRDZ1JHUU5Nb01VUnNFaVlyVGp6SzJ3RlFQcVFob0d3YWdBRkVxb2piNU5XdkZLaEU3UlAvRTE2VkxWcEtVUERjVHU0QUNQLzR2ZDVQU0JvZ3diTGtBVks2Y2hmamdUUk1sby9hdVM1cUp2cXN5a3pIWVlwMFdyR01ESXdCM3RxS0gxaWdIcEVOcWNDancxeHpueFpQSWdYZ2dyMTJqOG9pWCs2aUE2M0JzREZ0dHJtN0xwdHh4c0xPMUlqWlZjYUExbGtIbWU5OXBTamJFUT09IiwiQXV0aEluZm8iOiJ7XCJFeHBpcmVUaW1lXCI6XCIyMDE3LTA3LTI3VDA3OjE5OjMxWlwiLFwiTWVkaWFJZFwiOlwiYzMxZGE0MzI1MWIxNDhiMmE0YmRmNGY1NDBmMTkxMTZcIixcIlNpZ25hdHVyZVwiOlwiWGtTaGcrb3dUa0VydS9ZcE42cmxCNmY0RXowPVwifSIsIlZpZGVvTWV0YSI6eyJTdGF0dXMiOiJOb3JtYWwiLCJWaWRlb0lkIjoiYzMxZGE0MzI1MWIxNDhiMmE0YmRmNGY1NDBmMTkxMTYiLCJUaXRsZSI6IlvllpznvornvorkuI7ngbDlpKrni7xd56ysOOmbhl9oZC5tcDQiLCJDb3ZlclVSTCI6Imh0dHA6Ly90dHZpZGVvdHMucWlhb2h1YXBwLmNvbS9zbmFwc2hvdC9jMzFkYTQzMjUxYjE0OGIyYTRiZGY0ZjU0MGYxOTExNjAwMDAxLmpwZz9hdXRoX2tleT0xNTAxMTQzNDcxLTAtMC1iNjU4YmEzODE3ZWRlN2FlNTZmODk4YjE5MTUyNzM3MiIsIkR1cmF0aW9uIjozNDYuMDJ9LCJBY2Nlc3NLZXlJZCI6IlNUUy5FZnJDY3V3ZlBKY2FvNFFCUFl1b0RVQjV5IiwiUGxheURvbWFpbiI6InR0dmlkZW90cy5xaWFvaHVhcHAuY29tIiwiQWNjZXNzS2V5U2VjcmV0IjoiNEU5YjV3eWNNbXozZzJNTTVIdWlRMzVnekdWZEhuWHdRdTg4aGh6aXNrRFIiLCJSZWdpb24iOiJjbi1zaGFuZ2hhaSIsIkN1c3RvbWVySWQiOjE3NDUyODQwMTYwMTA4Njd9";
            AliyunPlayAuth.AliyunPlayAuthBuilder aliyunPlayAuthBuilder = new AliyunPlayAuth.AliyunPlayAuthBuilder();
            aliyunPlayAuthBuilder.setVid(vid);
            aliyunPlayAuthBuilder.setPlayAuth(authInfo);
            aliyunPlayAuthBuilder.setTitle("~~~");
            aliyunPlayAuthBuilder.setQuality(IAliyunVodPlayer.QualityValue.QUALITY_ORIGINAL);


            AliyunPlayAuth playAuth = aliyunPlayAuthBuilder.build();
            aliyunDownloadManager.prepareDownloadMedia(playAuth);
            aliyunDownloadManager.startDownloadMedia(playAuth, new AliyunRefreshPlayAuthCallback() {
                @Override
                public AliyunPlayAuth refreshPlayAuth(String vid, String quality, String format, String title) {
                    AliyunPlayAuth.AliyunPlayAuthBuilder aliyunPlayAuthBuilder = new AliyunPlayAuth.AliyunPlayAuthBuilder();
                    aliyunPlayAuthBuilder.setVid(vid);
                    aliyunPlayAuthBuilder.setQuality(quality);
                    aliyunPlayAuthBuilder.setFormat(format);
                    aliyunPlayAuthBuilder.setTitle(title);

                    return aliyunPlayAuthBuilder.build();
                }
            });
        }


    }

    AliyunDownloadInfoListener downloadInfoListener = new AliyunDownloadInfoListener() {
        @Override
        public void onPrepared(List<AliyunDownloadMediaInfo> infos) {
            AliyunDataSource.AliyunDataSourceBuilder aliyunDataSourceBuilder = new AliyunDataSource.AliyunDataSourceBuilder(NoSkinActivity.this);
            String vid = getIntent().getStringExtra("vid");
            String keyId = getIntent().getStringExtra("keyid");
            String secret = getIntent().getStringExtra("secret");
            String playKey = getIntent().getStringExtra("playKey");
            aliyunDataSourceBuilder.setAccessKeySecret(secret);
            aliyunDataSourceBuilder.setVideoId(vid);
            aliyunDataSourceBuilder.setPlayKey(playKey);
            aliyunDataSourceBuilder.setAccessKeyId(keyId);
            aliyunDataSourceBuilder.setTitle(infos.get(0).getTitle());
            aliyunDataSourceBuilder.setQuality(infos.get(0).getQuality());
            aliyunDataSourceBuilder.setFormat(infos.get(0).getFormat());
            AliyunDataSource aliyunDataSource = aliyunDataSourceBuilder.build();
            aliyunDownloadManager.startDownloadMedia(aliyunDataSource);
        }

        @Override
        public void onStart(AliyunDownloadMediaInfo info) {

            downloadInfo = info;
            Toast.makeText(NoSkinActivity.this, "下载开始", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onProgress(AliyunDownloadMediaInfo info, int percent) {
            downloadInfo = info;
            Toast.makeText(NoSkinActivity.this, "下载进度。。" + percent + "%", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStop(AliyunDownloadMediaInfo info) {
            downloadInfo = info;
            Toast.makeText(NoSkinActivity.this, "下载停止", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCompletion(AliyunDownloadMediaInfo info) {
            downloadInfo = info;
            Toast.makeText(NoSkinActivity.this, "下载结束", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(AliyunDownloadMediaInfo info, int code, String msg) {
            downloadInfo = info;
            Toast.makeText(NoSkinActivity.this, "下载出错：" + msg, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWait(AliyunDownloadMediaInfo outMediaInfo) {
            downloadInfo = outMediaInfo;
            Toast.makeText(NoSkinActivity.this, "下载等待：", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onM3u8IndexUpdate(AliyunDownloadMediaInfo outMediaInfo, int index) {

        }
    };

}
