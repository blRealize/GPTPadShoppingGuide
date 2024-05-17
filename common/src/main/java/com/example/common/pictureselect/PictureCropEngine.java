package com.example.common.pictureselect;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.engine.CropFileEngine;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropImageEngine;

import java.util.ArrayList;

/**
 * crop裁剪
 */
public class PictureCropEngine implements CropFileEngine {
    @Override
    public void onStartCrop(Fragment fragment, Uri srcUri, Uri destinationUri, ArrayList<String> dataSource, int requestCode) {
        UCrop uCrop = UCrop.of(srcUri, destinationUri, dataSource);
        uCrop.setImageEngine(new UCropImageEngine() {
            @Override
            public void loadImage(Context context, String url, ImageView imageView) {
                Glide.with(context).load(url).into(imageView);
            }

            @Override
            public void loadImage(Context context, Uri url, int maxWidth, int maxHeight, OnCallbackListener<Bitmap> call) {

            }
        });
        UCrop.Options options = new UCrop.Options();
        options.withAspectRatio(16,9);
        options.withMaxResultSize(4096,4096);

        uCrop.withOptions(options);
        if (fragment.getActivity()!=null){
            uCrop.start(fragment.getActivity(), fragment, requestCode);
        }
    }

}
