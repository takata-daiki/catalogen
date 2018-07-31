package com.masterofcode.android.gallerytestdemo.gallery_demo.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.masterofcode.android.gallerytestdemo.gallery_demo.R;
import com.masterofcode.android.gallerytestdemo.gallery_demo.activities.PictureViewPagerActivity;
import com.masterofcode.android.gallerytestdemo.gallery_demo.objects.PictureData;
import com.masterofcode.android.gallerytestdemo.gallery_demo.objects.PictureInfoParcelable;
import com.masterofcode.android.gallerytestdemo.gallery_demo.utils.BitmapUtils;
import com.masterofcode.android.gallerytestdemo.gallery_demo.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by boss1088 on 5/19/14.
 */
public class GridGalleryFragment extends Fragment {

    GridLayout mGridLayout;
    HashMap<ImageView, PictureData> mPicturesData = new HashMap<ImageView, PictureData>();
    private BitmapUtils mBitmapUtils;

    public GridGalleryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_grid_gallery, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int columnNum;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            columnNum = 3;
        } else {
            columnNum = 4;
        }

        int padding = getResources().getDimensionPixelSize(R.dimen.image_padding);
        mBitmapUtils = new BitmapUtils(getActivity(), padding, columnNum);

        mGridLayout = (GridLayout) getView().findViewById(R.id.gridLayout);
        mGridLayout.setColumnCount(columnNum);

        Resources resources = getResources();
        ArrayList<PictureData> pictures = mBitmapUtils.loadPhotos(resources);

        for (int i = 0; i < pictures.size(); ++i) {
            PictureData pictureData = pictures.get(i);
            BitmapDrawable thumbnailDrawable =
                    new BitmapDrawable(resources, pictureData.getThumbnail());
            ImageView imageView = new ImageView(getActivity());
            imageView.setPadding(padding, padding, padding, padding);
            imageView.setOnClickListener(thumbnailClickListener);
            imageView.setImageDrawable(thumbnailDrawable);
            mPicturesData.put(imageView, pictureData);
            mGridLayout.addView(imageView);
        }
    }

    private View.OnClickListener thumbnailClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            ArrayList<PictureInfoParcelable> list = new ArrayList<>();
            int currentItem = -1;
            for (int i = 0; i < mPicturesData.size(); i++) {
                View view = mGridLayout.getChildAt(i);
                if (v.equals(view)) {
                    currentItem = i;
                }

                int[] screenLocation = new int[2];
                view.getLocationOnScreen(screenLocation);
                PictureData data = mPicturesData.get(view);
                list.add(new PictureInfoParcelable(data.getResourceId(), screenLocation[0], screenLocation[1],
                                                    view.getWidth(), view.getHeight()));
            }

            Intent subActivity = new Intent(getActivity(), PictureViewPagerActivity.class);
            int orientation = getResources().getConfiguration().orientation;
            subActivity.
                    putExtra(Constants.PACKAGE + ".currentItem", currentItem).
                    putExtra(Constants.PACKAGE + ".orientation", orientation).
                    putExtra(Constants.PACKAGE + ".pictureObjects", list);

            startActivity(subActivity);

            // Override transitions: we don't want the normal window animation in addition
            // to our custom one
            getActivity().overridePendingTransition(0, 0);
        }
    };
}
