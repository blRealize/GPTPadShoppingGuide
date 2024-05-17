package com.example.login

import android.view.View
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ClickUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.SnackbarUtils
import com.example.common.base.BaseActivity
import com.example.common.router.AppRouter
import com.example.common.utils.ProgressUtils
import com.example.login.databinding.ActivityLoginBinding

/**
 * 登录界面
 */
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginModel>(),
    View.OnFocusChangeListener,View.OnClickListener{

    //是否是登陆操作
    private var isLogin = true

    override fun initBinding(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun initViewModel(): Class<LoginModel> {
        return LoginModel::class.java
    }

    override fun initView() {
        binding.loginEtPassword.onFocusChangeListener = this
        binding.loginEtRpassword.onFocusChangeListener = this

        ClickUtils.applyGlobalDebouncing(binding.loginBtnLogin,this)
        ClickUtils.applyGlobalDebouncing(binding.loginTvSign,this)

        //登录成功回调
        model.loginSuccess.observe(this){
            SnackbarUtils.with(binding.root).setMessage("登录成功").showSuccess()
            ProgressUtils.dismiss()
            AppRouter.launchMain()
            finish()
        }
        //注册失败回调
        model.registerSuccess.observe(this){
            ProgressUtils.dismiss()
            SnackbarUtils.with(binding.root).setMessage("注册成功").showSuccess()
            binding.loginTvSign.text = "注册"
            binding.loginBtnLogin.text ="登录"
            binding.loginEtRpassword.visibility = View.GONE
            binding.loginEtEmail.visibility = View.VISIBLE
            binding.loginEtNickname.visibility = View.GONE
            isLogin = !isLogin;
        }

        //操作失败回调
        model.error.observe(this){
            ProgressUtils.dismiss()
            SnackbarUtils.with(binding.root).setMessage(it).showError()
        }
    }

    override fun onFocusChange(p0: View?, p1: Boolean) {
        if (p1){
            binding.landOwlView.open()
        }else {
            binding.landOwlView.close()
        }
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.login_btn_login -> {
                if (isLogin) {
                    login();  //登陆
                } else {
                    sign();  //注册
                }
            }
            R.id.login_tv_sign -> {
                if (isLogin) {
                    //置换注册界面
                    binding.loginTvSign.text ="登录"
                    binding.loginBtnLogin.text ="注册"
                    binding.loginEtNickname.visibility = View.VISIBLE
                    binding.loginEtRpassword.visibility =View.VISIBLE
                    binding.loginEtEmail.visibility = View.VISIBLE
                } else {
                    //置换登陆界面
                    binding.loginTvSign.setText("注册");
                    binding.loginBtnLogin.setText("登录");
                    binding.loginEtNickname.visibility = View.GONE
                    binding.loginEtRpassword.visibility = View.GONE
                    binding.loginEtEmail.visibility = View.VISIBLE
                }
                isLogin = !isLogin;
            }
        }
    }

    /**
     * 执行登陆动作
     */
    fun login() {
        val email: String = binding.loginEtEmail.text.toString()
        val password: String = binding.loginEtPassword.text.toString()
        if (email.isEmpty() || password.isEmpty()) {
            SnackbarUtils.with(binding.root).setMessage("邮箱或密码不能为空").showError(true)
            return
        }
        ProgressUtils.show(this, "正在登陆...")
        model.login(email,password)
    }

    /**
     * 执行注册动作
     */
    fun sign() {
        val email: String = binding.loginEtEmail.text.toString()
        val username: String = binding.loginEtNickname.text.toString()
        val password: String = binding.loginEtPassword.text.toString()
        val rpassword: String = binding.loginEtRpassword.text.toString()
        if (email.isEmpty() || username.isEmpty() || password.isEmpty() || rpassword.isEmpty()) {
            SnackbarUtils.with(binding.root).setMessage("请填写必要信息").showError(true)
            return
        }
        if (password != rpassword) {
            SnackbarUtils.with(binding.root).setMessage("两次密码不一致").showError(true)
            return
        }
        ProgressUtils.show(this, "正在注册...")
        model.signup(username, password, email)
    }

}