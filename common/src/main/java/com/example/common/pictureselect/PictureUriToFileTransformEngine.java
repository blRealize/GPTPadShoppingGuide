package com.example.common.pictureselect;

import android.content.Context;

import com.luck.picture.lib.engine.UriToFileTransformEngine;
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener;
import com.luck.picture.lib.utils.SandboxTransformUtils;

/**
 * android q以上沙盒文件处理
 */
public class PictureUriToFileTransformEngine implements UriToFileTransformEngine {
    @Override
    public void onUriToFileAsyncTransform(Context context, String srcPath, String mineType, OnKeyValueResultCallbackListener call) {
        if (call != null) {
            String sandboxPath = SandboxTransformUtils.copyPathToSandbox(context, srcPath, mineType);
            call.onCallback(srcPath,sandboxPath);
        }
    }
}
