
package jp.co.kayo.android.localplayer.fragment;

/***
 * Copyright (c) 2010-2012 yokmama. All rights reserved.
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import jp.co.kayo.android.localplayer.R;
import jp.co.kayo.android.localplayer.appwidget.AppWidgetHelper;
import jp.co.kayo.android.localplayer.consts.SystemConsts;
import jp.co.kayo.android.localplayer.core.ContentManager;
import jp.co.kayo.android.localplayer.core.ServiceBinderHolder;
import jp.co.kayo.android.localplayer.service.IMediaPlayerService;
import jp.co.kayo.android.localplayer.util.Funcs;
import jp.co.kayo.android.localplayer.util.Logger;
import jp.co.kayo.android.localplayer.util.MyPreferenceManager;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class ControlFragment extends Fragment implements OnClickListener,
        ContentManager {
    final long BTN_DELAY_TIME = 250;
    ImageButton btnRew;
    ImageButton btnPlay;
    ImageButton btnFf;
    ImageButton btnSfl;
    ImageButton btnRept1;
    MyPreferenceManager mPref;
    float dt;
    private SeekBar seekBar;
    private Timer watcher = null;
    TextView txtStart;
    TextView txtEnd;
    long current_media_id = -1;

    Animation bshowanim;
    Animation bhideanim;
    
    boolean mIsInLongpressFF,mIsInLongpressREW;
    private final Runnable mRunnableFF = new Runnable() {
        public void run() {
            if (mIsInLongpressFF && btnFf.isEnabled()) {
                handler.postDelayed(this, BTN_DELAY_TIME);
                seekSkip(2000);
            }
        }
    };
    private final Runnable mRunnableREW = new Runnable() {
        public void run() {
            if (mIsInLongpressREW && btnRew.isEnabled()) {
                handler.postDelayed(this, BTN_DELAY_TIME);
                seekSkip(-2000);
            }
        }
    };
    
    Handler handler = new MyHandler(this);
    

    static class MyHandler extends Handler {
        WeakReference<ControlFragment> refCon;

        public MyHandler(ControlFragment f) {
            refCon = new WeakReference<ControlFragment>(f);
        }

        @Override
        public void handleMessage(Message msg) {
            ControlFragment con = refCon.get();
            if (con != null) {
                con.txtStart.setText(Funcs.makeTimeString((Long) msg.obj, true));
            }
        }
    };

    OnSeekBarChangeListener seekbarChangeListener = new OnSeekBarChangeListener() {
        boolean fromTouch = false;

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            fromTouch = false;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            fromTouch = true;
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                boolean fromUser) {
            if (fromTouch) {
                // タッチの場合に処理をする
                IMediaPlayerService binder = getBinder();
                if (binder != null) {
                    try {
                        int msec = (int) (seekBar.getProgress() * dt);
                        binder.seek(msec);
                    } catch (RemoteException e) {
                    }
                }
            }

        }
    };

    private IMediaPlayerService getBinder() {
        if (getActivity() instanceof ServiceBinderHolder) {
            return ((ServiceBinderHolder) getActivity()).getBinder();
        } else {
            return null;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setHasOptionsMenu(true);
        mPref = new MyPreferenceManager(getActivity());

    }

    final int BTN_PLAY_ID = 0;
    final int BTN_PAUSE_ID = 1;
    final int BTN_PREV_ID = 2;
    final int BTN_NEXT_ID = 3;
    final int BTN_SHFLON_ID = 4;
    final int BTN_SHFLOFF_ID = 5;
    final int BTN_REPOFF_ID = 6;
    final int BTN_REPON_ID = 7;
    final int BTN_REPONE_ID = 8;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        media_button = activity.getTheme().obtainStyledAttributes(
                new int[] {
                        R.attr.playButtonIcon, R.attr.pauseButtonIcon,
                        R.attr.previousButtonIcon, R.attr.nextButtonIcon,
                        R.attr.shuffleOnButtonIcon,
                        R.attr.shuffleOffButtonIcon,
                        R.attr.repeatOffButtonIcon, R.attr.repeatOnButtonIcon,
                        R.attr.repeatOneButtonIcon
                });
    }

    TypedArray media_button;

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Logger.d("ControlFragment onResume");
        IMediaPlayerService binder = getBinder();
        // if (binder != null) {
        if (watcher == null) {
            watcher = new Timer(true);
            watcher.schedule(new WatcherTask(), 0, 1000);
        }
        // }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (watcher != null) {
            watcher.cancel();
            watcher = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.control, container, false);

        btnRew = (ImageButton) root.findViewById(R.id.btnRew);
        btnPlay = (ImageButton) root.findViewById(R.id.btnPlay);
        btnFf = (ImageButton) root.findViewById(R.id.btnFf);
        btnSfl = (ImageButton) root.findViewById(R.id.btnSfl);
        btnRept1 = (ImageButton) root.findViewById(R.id.btnRept1);
        seekBar = (SeekBar) root.findViewById(R.id.SeekBar01);
        txtStart = (TextView) root.findViewById(R.id.txtStart);
        txtEnd = (TextView) root.findViewById(R.id.txtEnd);

        btnRew.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnFf.setOnClickListener(this);
        btnSfl.setOnClickListener(this);
        btnRept1.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(seekbarChangeListener);
        
        btnFf.setOnTouchListener(new OnTouchListener() {
            
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if ((event.getAction() == MotionEvent.ACTION_CANCEL)
                        || (event.getAction() == MotionEvent.ACTION_UP)) {
                    mIsInLongpressFF = false;
                }
                return false;
            }
        });
        
        btnFf.setOnLongClickListener(new OnLongClickListener() {
            
            @Override
            public boolean onLongClick(View v) {
                mIsInLongpressFF = true;
                handler.post(mRunnableFF);
                return true;
            }
        });
        
        btnRew.setOnTouchListener(new OnTouchListener() {
            
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if ((event.getAction() == MotionEvent.ACTION_CANCEL)
                        || (event.getAction() == MotionEvent.ACTION_UP)) {
                    mIsInLongpressREW = false;
                }
                return false;
            }
        });
        
        btnRew.setOnLongClickListener(new OnLongClickListener() {
            
            @Override
            public boolean onLongClick(View v) {
                mIsInLongpressREW = true;
                handler.post(mRunnableREW);
                return true;
            }
        });

        bshowanim = AnimationUtils.loadAnimation(getActivity(), R.anim.bshow);
        bhideanim = AnimationUtils.loadAnimation(getActivity(), R.anim.bhide);

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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

    public void onBufferingUpdate(int percent) {
        seekBar.setSecondaryProgress(percent * 10);
    }

    private void seekSkip(long seek){
        IMediaPlayerService binder = getBinder();
        if (binder != null) {
            try{
                long totalTime = binder.getDuration(); //current duration
                long seekTime = binder.getSeekPosition(); //current position
                seekTime += seek; // rewinde 2sec
                if ( seekTime < 0){
                    binder.rew();
                }
                else if ( totalTime < seekTime ){
                    binder.ff();
                }
                else{
                    binder.seek((int)seekTime);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    // コントロールの初期設定（曲がある場合は表示、ない場合は非表示）接続時に呼ばれる
    public boolean resumeControl() {
        View view = getActivity().findViewById(R.id.controlLinearLayout01);
        if (view != null) {
            IMediaPlayerService binder = getBinder();
            if (binder != null) {
                try {
                    if (binder.getCount() > 0) {
                        view.setVisibility(View.VISIBLE);
                        return true;
                    }
                } catch (RemoteException e) {
                }
            }
            view.setVisibility(View.GONE);
        }
        return false;
    }

    public void hideControl(boolean keep) {
        View view = getActivity().findViewById(R.id.controlLinearLayout01);
        if (view != null) {
            if (keep)
                view.setEnabled(false);
            if (view.getVisibility() != View.GONE) {
                view.setVisibility(View.GONE);
                view.startAnimation(bhideanim);
            }
        }
    }

    public void showControl(boolean keep) {
        View view = getActivity().findViewById(R.id.controlLinearLayout01);
        if (view != null) {
            if (keep) {
                view.setEnabled(true);
            }
            if (view.getVisibility() != View.VISIBLE && view.isEnabled()) {
                view.setVisibility(View.VISIBLE);
                view.startAnimation(bshowanim);
            }
        }
    }

    public void updateView() {
        int stat = 0;

        IMediaPlayerService binder = getBinder();
        if (binder != null) {
            try {
                stat = binder.stat();
            } catch (RemoteException e) {
            }
        }

        if (stat != 0) {
            btnFf.setEnabled((stat & AppWidgetHelper.FLG_HASNEXT) > 0);
            btnRew.setEnabled((stat & AppWidgetHelper.FLG_HASFOWD) > 0);
            if((stat & AppWidgetHelper.FLG_HASLIST) > 0){
                btnPlay.setEnabled(true);
            }else{
                btnPlay.setEnabled(false);
            }
        }

        // Bind済み
        if (binder != null) {
            try {
                long duration = binder.getDuration();
                Logger.d("duration=" + duration);
                if (duration > 0) {
                    dt = duration / 1000.0f;
                } else {
                    dt = 1;
                }

                txtEnd.setText(Funcs.makeTimeString(duration, false));
                if ((stat & AppWidgetHelper.FLG_PLAY) > 0) {
                    // 再生中
                    btnPlay.setBackgroundDrawable(media_button
                            .getDrawable(BTN_PAUSE_ID));
                    // btnPlay.setBackgroundResource(R.drawable.pause_button);
                    if (watcher == null) {
                        watcher = new Timer(true);
                        watcher.schedule(new WatcherTask(), 0, 1000);
                    }
                    showControl(true);
                    // view.setEnabled(true);
                } else {
                    // 非再生中
                    btnPlay.setBackgroundDrawable(media_button
                            .getDrawable(BTN_PLAY_ID));
                    // btnPlay.setBackgroundResource(R.drawable.play_button);
                    long time = mPref.getHideTime();
                    if ((stat & AppWidgetHelper.FLG_HASLIST) > 0 || time == 0) {
                        showControl(true);
                    } else {
                        hideControl(true);
                    }
                }

                // setTitle
                if (getActivity() instanceof ServiceBinderHolder) {
                    ((ServiceBinderHolder) getActivity())
                            .setActionBarSubTitle(stat);
                }

                // リピートフラグ
                if ((stat & AppWidgetHelper.FLG_LOOP1) > 0) {
                    setRepeatView(SystemConsts.FLG_REPEAT_ONCE);
                } else if ((stat & AppWidgetHelper.FLG_LOOP2) > 0) {
                    setRepeatView(SystemConsts.FLG_REPEAT_ALL);
                } else {
                    setRepeatView(SystemConsts.FLG_REPEAT_NO);
                }

                // シャッフルフラグ
                if ((stat & AppWidgetHelper.FLG_SHUFFLE) > 0) {
                    btnSfl.setBackgroundDrawable(media_button
                            .getDrawable(BTN_SHFLON_ID));
                    // btnSfl.setBackgroundResource(R.drawable.shuffle_button_on);
                } else {
                    btnSfl.setBackgroundDrawable(media_button
                            .getDrawable(BTN_SHFLOFF_ID));
                    // btnSfl.setBackgroundResource(R.drawable.shuffle_button_off);
                }
            } catch (RemoteException e) {
            }
        }
    }

    private void setRepeatView(int flg) {
        switch (flg) {
            case SystemConsts.FLG_REPEAT_NO:
                btnRept1.setBackgroundDrawable(media_button
                        .getDrawable(BTN_REPOFF_ID));
                // btnRept1.setBackgroundResource(R.drawable.repeat_button_off);
                break;
            case SystemConsts.FLG_REPEAT_ONCE:
                btnRept1.setBackgroundDrawable(media_button
                        .getDrawable(BTN_REPONE_ID));
                // btnRept1.setBackgroundResource(R.drawable.repeat_button_one);
                break;
            case SystemConsts.FLG_REPEAT_ALL:
                btnRept1.setBackgroundDrawable(media_button
                        .getDrawable(BTN_REPON_ID));
                // btnRept1.setBackgroundResource(R.drawable.repeat_button_on);
                break;
        }
    }

    private void rew() {
        try {
            IMediaPlayerService binder = getBinder();
            if (binder != null) {
                binder.rew();
            }
        } catch (RemoteException e) {
        }
    }

    private void play() {
        int stat;
        try {
            IMediaPlayerService binder = getBinder();
            if (binder != null) {
                stat = binder.stat();
                if ((stat & AppWidgetHelper.FLG_PLAY) > 0) {
                    binder.pause();
                } else {
                    binder.play();
                }
            }
        } catch (RemoteException e) {
        }
    }

    private void ff() {
        try {
            IMediaPlayerService binder = getBinder();
            if (binder != null) {
                binder.ff();
            }
        } catch (RemoteException e) {
        }
    }

    private void sfl() {
        try {
            IMediaPlayerService binder = getBinder();
            if (binder != null) {
                binder.shuffle();
            }
        } catch (RemoteException e) {
        }
    }

    private void rept() {
        try {
            IMediaPlayerService binder = getBinder();
            if (binder != null) {
                binder.repeat();
            }
        } catch (RemoteException e) {
        }
    }

    private class WatcherTask extends TimerTask {
        long pos;
        int prog;

        @Override
        public void run() {
            try {
                // 現在の曲の再生位置を取得し、進捗バーに値を設定します
                IMediaPlayerService binder = getBinder();
                if (binder != null) {
                    pos = binder.getSeekPosition();
                    prog = (int) (pos / dt);
                    seekBar.setProgress(prog);
                    handler.sendMessage(handler.obtainMessage(0, pos));
                }
            } catch (RemoteException e) {
            }
        }
    }

    @Override
    public void reload() {
    }

    @Override
    public void release() {
    }

    @Override
    public void changedMedia() {
        showControl(true);
    }

    @Override
    public String getName(Context context) {
        return null;
    }
}
