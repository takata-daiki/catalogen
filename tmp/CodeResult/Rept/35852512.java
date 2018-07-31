package jp.co.kayo.android.localplayer.ui;
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

import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import jp.co.kayo.android.localplayer.MainActivity;
import jp.co.kayo.android.localplayer.PlayActivity;
import jp.co.kayo.android.localplayer.R;
import jp.co.kayo.android.localplayer.TweetActivity;
import jp.co.kayo.android.localplayer.appwidget.AppWidgetHelper;
import jp.co.kayo.android.localplayer.consts.MediaConsts.AudioAlbum;
import jp.co.kayo.android.localplayer.consts.MediaConsts.AudioMedia;
import jp.co.kayo.android.localplayer.consts.AnalyticsConsts;
import jp.co.kayo.android.localplayer.consts.SystemConsts;
import jp.co.kayo.android.localplayer.core.ContentManager;
import jp.co.kayo.android.localplayer.core.ServiceBinderHolder;
import jp.co.kayo.android.localplayer.provider.ContentsUtils;
import jp.co.kayo.android.localplayer.service.IMediaPlayerService;
import jp.co.kayo.android.localplayer.util.AnalyticsHelper;
import jp.co.kayo.android.localplayer.util.Funcs;
import jp.co.kayo.android.localplayer.util.Logger;
import jp.co.kayo.android.localplayer.util.ViewCache;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class ControlFragment extends Fragment implements OnClickListener,
        ContentManager {
    ImageButton btnRew;
    ImageButton btnPlay;
    ImageButton btnFf;
    ImageButton btnSfl;
    ImageButton btnRept1;
    SharedPreferences pref;
    float dt;
    private SeekBar seekBar;
    private Timer watcher = null;
    TextView txtStart;
    TextView txtEnd;
    long current_media_id = -1;
    private AnalyticsHelper mTracker;

    Animation bshowanim;
    Animation bhideanim;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            txtStart.setText(Funcs.makeTimeString((Long) msg.obj));
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
                // ????????????
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
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());

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
                new int[] { R.attr.playButtonIcon, R.attr.pauseButtonIcon,
                        R.attr.previousButtonIcon, R.attr.nextButtonIcon,
                        R.attr.shuffleOnButtonIcon,
                        R.attr.shuffleOffButtonIcon,
                        R.attr.repeatOffButtonIcon, R.attr.repeatOnButtonIcon,
                        R.attr.repeatOneButtonIcon });
    }

    TypedArray media_button;

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Logger.d("ControlFragment onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (watcher != null) {
            watcher.cancel();
            watcher = null;
        }
        // Google Analytics????
        mTracker.stopSession();
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

        bshowanim = AnimationUtils.loadAnimation(getActivity(), R.anim.bshow);
        bhideanim = AnimationUtils.loadAnimation(getActivity(), R.anim.bhide);

        mTracker = new AnalyticsHelper(getActivity());
        mTracker.startNewSession();
        mTracker.trackPageView(AnalyticsConsts.PAGE_CONTROL_FRAGMENT);

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SystemConsts.REQUEST_ALBUMART) {
                if (data != null) {
                    String album_key = data.getStringExtra("AlbumartActivity");
                    if (album_key != null) {
                        Hashtable<String, String> tbl = ContentsUtils
                                .getAlbum(getActivity(), new String[] {
                                        AudioAlbum.ALBUM, AudioAlbum.ALBUM_KEY,
                                        AudioAlbum.ARTIST }, album_key);
                        if (tbl != null) {
                            ViewCache viewcache = (ViewCache) getFragmentManager()
                                    .findFragmentByTag(SystemConsts.TAG_CACHE);
                            if (viewcache != null) {
                                viewcache.releaseImage(
                                        tbl.get(AudioAlbum.ALBUM),
                                        tbl.get(AudioAlbum.ARTIST));

                                Fragment current = null;
                                if (getActivity() instanceof MainActivity) {
                                    current = ((MainActivity) getActivity())
                                            .getCurrentFragment();
                                } else if (getActivity() instanceof PlayActivity) {
                                    current = ((PlayActivity) getActivity())
                                            .getSongFragment();
                                }
                                if (current != null
                                        && current instanceof ContentManager) {
                                    ContentManager cmr = (ContentManager) current;
                                    cmr.reload();
                                }
                            }
                        }
                    }
                }
            } else if (requestCode == SystemConsts.REQUEST_PLAYVIEW) {

            }
        }
    }

    public void share() {
        //Bind?????????????
        if(getBinder() == null){
            return;
        }
        
        long media_id;
        try {
            media_id = getBinder().getMediaId();
            if (media_id > 0) {
                Hashtable<String, String> tbl = ContentsUtils.getMedia(
                        getActivity(), new String[] { AudioMedia.ALBUM,
                                AudioMedia.ALBUM_KEY, AudioMedia.ARTIST,
                                AudioMedia.TITLE }, media_id);
                if (tbl != null) {
                    boolean b = pref.getBoolean("key.autoTweet", false);
                    if (b) {
                        TweetActivity.tweet(
                                getActivity(),
                                TweetActivity.getTweetString(pref,
                                        tbl.get(AudioMedia.TITLE),
                                        tbl.get(AudioMedia.ARTIST)), mTracker);
                    } else {
                        // ?????????
                        Intent i = new Intent(getActivity(),
                                TweetActivity.class);
                        i.putExtra("album", tbl.get(AudioMedia.ALBUM));
                        i.putExtra("artist", tbl.get(AudioMedia.ARTIST));
                        i.putExtra("title", tbl.get(AudioMedia.TITLE));
                        i.putExtra("key", tbl.get(AudioMedia.ALBUM_KEY));
                        startActivityForResult(i, SystemConsts.REQUEST_TWEET);
                    }
                }
            } else {
                int pos = getBinder().getPosition();
                if (pos >= 0 && getBinder().getCount() > pos) {
                    String[] values = getBinder().getMediaD(pos);
                    if (values != null) {
                        String title = values[0];
                        String album = values[1];
                        String artist = values[2];
                        boolean b = pref.getBoolean("key.autoTweet", false);
                        if (b) {
                            TweetActivity.tweet(getActivity(), TweetActivity
                                    .getTweetString(pref, title, artist),
                                    mTracker);
                        } else {
                            // ?????????
                            Intent i = new Intent(getActivity(),
                                    TweetActivity.class);
                            i.putExtra("album", album);
                            i.putExtra("artist", artist);
                            i.putExtra("title", title);
                            startActivityForResult(i,
                                    SystemConsts.REQUEST_TWEET);
                        }
                    }
                }
            }
        } catch (RemoteException e) {
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

    public void onBufferingUpdate(int percent) {
        seekBar.setSecondaryProgress(percent * 10);
    }

    // ???????????????????????????????????????
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
            btnPlay.setEnabled(true);
        }

        // Bind??
        if (binder != null) {
            try {
                long duration = binder.getDuration();
                Logger.d("duration=" + duration);
                if (duration > 0) {
                    dt = duration / 1000.0f;
                } else {
                    dt = 1;
                }

                txtEnd.setText(Funcs.makeTimeString(duration));
                if ((stat & AppWidgetHelper.FLG_PLAY) > 0) {
                    // ???
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
                    // ????
                    btnPlay.setBackgroundDrawable(media_button
                            .getDrawable(BTN_PLAY_ID));
                    // btnPlay.setBackgroundResource(R.drawable.play_button);
                    if ((stat & AppWidgetHelper.FLG_HASLIST) > 0) {
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

                // ???????
                if ((stat & AppWidgetHelper.FLG_LOOP1) > 0) {
                    setRepeatView(SystemConsts.FLG_REPEAT_ONCE);
                } else if ((stat & AppWidgetHelper.FLG_LOOP2) > 0) {
                    setRepeatView(SystemConsts.FLG_REPEAT_ALL);
                } else {
                    setRepeatView(SystemConsts.FLG_REPEAT_NO);
                }

                // ????????
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
            getBinder().rew();
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
                    if (getActivity() instanceof PlayActivity) {
                        PlayActivity playactivity = (PlayActivity) getActivity();
                        if (playactivity.playAlbum()) {
                            return;
                        }

                    }
                    binder.play();
                }
            }
        } catch (RemoteException e) {
        }
    }

    private void ff() {
        try {
            getBinder().ff();
        } catch (RemoteException e) {
        }
    }

    private void sfl() {
        try {
            getBinder().shuffle();
        } catch (RemoteException e) {
        }
    }

    private void rept() {
        try {
            getBinder().repeat();
        } catch (RemoteException e) {
        }
    }

    private class WatcherTask extends TimerTask {
        @Override
        public void run() {
            try {
                // ??????????????????????????
                IMediaPlayerService binder = getBinder();
                if (binder != null) {
                    long pos = binder.getSeekPosition();
                    int prog = (int) (pos / dt);
                    seekBar.setProgress(prog);

                    Message msg = handler.obtainMessage();
                    msg.obj = new Long(pos);
                    handler.sendMessage(msg);
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
}
