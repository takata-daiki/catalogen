
package jp.co.kayo.android.localplayer;

/***
 * Copyright (c) 2010-2012 yokmama. All rights reserved.
 * 
 *      This program is free software; you can redistribute it and/or modify it under
 *      the terms of the GNU General Public License as published by the Free Software
 *      Foundation; either version 2 of the License, or (at your option) any later
 *      version.
 *      
 *      This program is distributed in the hope that it will be useful, but WITHOUT
 *      ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *      FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 *      details.
 *      
 *      You should have received a copy of the GNU General Public License along with
 *      this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 *      Place, Suite 330, Boston, MA 02111-1307 USA
 *
 */

import java.util.Hashtable;

import jp.co.kayo.android.localplayer.appwidget.AppWidgetHelper;
import jp.co.kayo.android.localplayer.consts.MediaConsts.AudioAlbum;
import jp.co.kayo.android.localplayer.consts.MediaConsts.AudioMedia;
import jp.co.kayo.android.localplayer.provider.ContentsUtils;
import jp.co.kayo.android.localplayer.service.IMediaPlayerService;
import jp.co.kayo.android.localplayer.service.IMediaPlayerServiceCallback;
import jp.co.kayo.android.localplayer.service.MediaPlayerService;
import jp.co.kayo.android.localplayer.util.ImageObserver;
import jp.co.kayo.android.localplayer.util.Logger;
import jp.co.kayo.android.localplayer.util.ViewCache;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LockScreenActivity extends FragmentActivity implements
        OnClickListener {
    private IMediaPlayerService binder;
    private ViewCache viewcache;
    private Bitmap sourcebmp;

    Button btnRew;
    Button btnPlay;
    Button btnFf;
    TextView textTitle = null;
    TextView textArtist = null;
    ImageView imageview = null;
    Handler handler = new Handler();

    IMediaPlayerServiceCallback.Stub callback = new IMediaPlayerServiceCallback.Stub() {

        @Override
        public void updateView(boolean updatelist) throws RemoteException {
            LockScreenActivity.this.updateView();
        }

        @Override
        public void updateList() throws RemoteException {
        }

        @Override
        public void onBufferingUpdate(int percent) throws RemoteException {
        }

        @Override
        public void startProgress(long max) throws RemoteException {
            // TODO Auto-generated method stub

        }

        @Override
        public void stopProgress() throws RemoteException {
            // TODO Auto-generated method stub

        }

        @Override
        public void progress(long pos, long max) throws RemoteException {
            // TODO Auto-generated method stub

        }

        @Override
        public void close() throws RemoteException {
            LockScreenActivity.this.finish();
        }
    };

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            binder = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = IMediaPlayerService.Stub.asInterface(service);
            try {
                // サービスからの通知をうけとれるようにコールバックを登録
                binder.registerCallback(callback);
            } catch (RemoteException e) {
            }
            updateView();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock_main);
        Logger.d("onCreate");

        Window window = getWindow();
        // lock pattern を設定していても、
        // このアプリ起動中は画面オン時にロックはでない
        // 別のアプリやホームに移動するときにロックがでる
        window.setFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED,
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

        // lock pattern を設定していない場合、
        // このアプリ起動中は画面オン時にロックはでない
        // window.setFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD,
        // WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        btnRew = (Button) findViewById(R.id.btnRew);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnFf = (Button) findViewById(R.id.btnFf);
        imageview = (ImageView) findViewById(R.id.imageAlbumArt);
        textTitle = (TextView) findViewById(R.id.textTitle);
        textArtist = (TextView) findViewById(R.id.textArtist);

        FragmentManager m = getSupportFragmentManager();
        // キャッシュ
        viewcache = (ViewCache) m.findFragmentByTag("VIEWCACHE");
        if (viewcache == null) {
            viewcache = new ViewCache();
            FragmentTransaction t = m.beginTransaction();
            t.replace(R.id.contents0, viewcache, "VIEWCACHE");
            t.commitAllowingStateLoss();
        }

        btnRew.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnFf.setOnClickListener(this);

        Button btnUnlock = (Button) findViewById(R.id.btnUnlock);
        btnUnlock.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d("onDestroy");
        release();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.d("onPause");
        if (binder != null) {
            try {
                binder.unregisterCallback(callback);
            } catch (RemoteException e) {
            }
            binder = null;
            unbindService(connection);
        }
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d("onResume");
        if (binder == null) {
            // 音楽サービスとの接続
            Intent intent = new Intent(this, MediaPlayerService.class);
            bindService(intent, connection, Service.BIND_AUTO_CREATE);
            // startService(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRew: {
                rew();
            }
                break;
            case R.id.btnPlay: {
                play();
            }
                break;
            case R.id.btnFf: {
                ff();
            }
                break;
            case R.id.btnSfl: {
                sfl();
            }
                break;
            case R.id.btnRept1: {
                rept();
            }
                break;
        // case R.id.btnRept2:{ rept();}break;
        }
    }

    private void updateView() {
        int stat = 0;
        if (binder != null) {
            try {
                stat = binder.stat();
            } catch (RemoteException e) {
            }
        }

        if (stat != 0) {
            btnFf.setEnabled((stat & AppWidgetHelper.FLG_HASNEXT) > 0);
            btnRew.setEnabled((stat & AppWidgetHelper.FLG_HASFOWD) > 0);
            btnPlay.setEnabled(true);
        }

        if (binder != null) {
            if ((stat & AppWidgetHelper.FLG_PLAY) > 0) {
                // 再生中
                btnPlay.setBackgroundResource(R.drawable.media_control_pause);
            } else {
                btnPlay.setBackgroundResource(R.drawable.media_control_play);
            }
        }

        changeMedia();
    }

    private void rew() {
        try {
            binder.rew();
        } catch (RemoteException e) {
        }
    }

    private void play() {
        if (binder != null) {
            int stat;
            try {
                stat = binder.stat();
                if ((stat & AppWidgetHelper.FLG_PLAY) > 0) {
                    binder.pause();
                } else {
                    binder.play();
                }
            } catch (RemoteException e) {
            }
        }
    }

    private void ff() {
        if (binder != null) {
            try {
                binder.ff();
            } catch (RemoteException e) {
            }
        }
    }

    private void sfl() {
        if (binder != null) {
            try {
                binder.shuffle();
            } catch (RemoteException e) {
            }
        }
    }

    private void rept() {
        if (binder != null) {
            try {
                binder.repeat();
            } catch (RemoteException e) {
            }
        }
    }

    private void changeMedia() {
        if (binder != null) {
            try {
                long media_id = binder.getMediaId();
                if (media_id > 0) {
                    Hashtable<String, String> tbl1 = ContentsUtils.getMedia(
                            this, new String[] {
                                    AudioMedia.ALBUM,
                                    AudioMedia.ALBUM_KEY, AudioMedia.ARTIST,
                                    AudioMedia.TITLE
                            }, media_id);
                    String album_key = tbl1.get(AudioMedia.ALBUM_KEY);
                    String title = tbl1.get(AudioMedia.TITLE);
                    String artist = tbl1.get(AudioMedia.ARTIST);

                    textTitle.setText(title);
                    textArtist.setText(artist);

                    setAlbumArt(album_key);
                }
            } catch (RemoteException e) {
            }
        }
    }

    private void setAlbumArt(String key) {
        if (key != null) {
            Hashtable<String, String> tbl2 = ContentsUtils.getAlbum(this,
                    new String[] {
                            AudioAlbum.ALBUM, AudioAlbum.ARTIST,
                            AudioAlbum.ALBUM_KEY
                    }, key);
            String album = tbl2.get(AudioMedia.ALBUM);
            String artist = tbl2.get(AudioMedia.ARTIST);

            viewcache.getUnManagerImage(this, album, artist, key,
                    new MyImageObserver(), getResources().getDimensionPixelSize(
                            R.dimen.albumart_size));
        }
    }

    private void release() {
        if (imageview != null) {
            imageview.setImageBitmap(null);
        }
        if (sourcebmp != null) {
            sourcebmp.recycle();
            sourcebmp = null;
        }
    }

    class MyImageObserver implements ImageObserver {

        @Override
        public void onLoadImage(final Bitmap bm) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    release();
                    sourcebmp = bm;
                    if (imageview != null) {
                        imageview.setImageBitmap(bm);
                    }
                }
            });
        }
    }

}
