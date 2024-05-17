package com.example.common;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.Utils;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;
import com.tencent.mmkv.MMKV;

public class CommonModule {

    public static void init(Application application){
        Utils.init(application);
        MMKV.initialize(application);

        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, refreshLayout) -> {
            refreshLayout.setPrimaryColorsId(R.color.purple_500, android.R.color.white);
            return new ClassicsHeader(context);
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            return new ClassicsFooter(context).setDrawableSize(20);
        });
    }

}
