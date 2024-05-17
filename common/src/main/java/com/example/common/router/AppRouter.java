package com.example.common.router;

import com.blankj.utilcode.util.ActivityUtils;

public class AppRouter {

    //主页路径
    private static final String PAGER_MAIN = "com.hq.gptstore.MainActivity";
    //登录路径
    private static final String PAGER_LOGIN = "com.example.login.LoginActivity";
    /**
     * 进入主页
     */
    public static void launchMain(){
        launch(PAGER_MAIN);
    }

    /**
     * 进入登录页
     */
    public static void launchLogin(){
        launch(PAGER_LOGIN);
    }

    private static void launch(String path){
        try {
            Class classMain = Class.forName(path);
            ActivityUtils.startActivity(classMain);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
