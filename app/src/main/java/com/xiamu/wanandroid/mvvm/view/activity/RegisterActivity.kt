package com.xiamu.wanandroid.mvvm.view.activity

import android.content.Intent
import androidx.lifecycle.Observer
import com.hss01248.dialog.StyledDialog
import com.xiamu.baselibs.base.BaseVmActivity
import com.xiamu.baselibs.util.toast
import com.xiamu.wanandroid.Constant.AppConstant
import com.xiamu.wanandroid.R
import com.xiamu.wanandroid.databinding.LoginViewmodelBinding
import com.xiamu.wanandroid.mvvm.model.repository.LoginModel
import com.xiamu.wanandroid.mvvm.viewmodel.LoginViewModel
import com.xiamu.wanandroid.util.Preference
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by zhengxiaobo in 2019-11-06
 */
class RegisterActivity : BaseVmActivity<LoginViewmodelBinding, LoginViewModel>(){

    private var user: String by Preference(AppConstant.USER_NAME, "")

    private var pwd: String by Preference(AppConstant.USER_PASSWORD, "")

    private var isLogin by Preference(AppConstant.LOGIN_KEY, false)

    override fun providerVMClass(): Class<LoginViewModel>? = LoginViewModel::class.java

    override fun getLayoutResId(): Int {
        return R.layout.activity_register
    }

    override fun initView() {
        toolbar.run {
            title = resources.getString(R.string.register)
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        tv_register.setOnClickListener {
            if (validate())
                mViewModel.register(et_register_username.text.toString(), et_register_password.text.toString(),
                    et_retry_password.text.toString())
        }

        tv_register_noaccount.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }


    }

    override fun startObserve() {
        super.startObserve()

        mViewModel._uistate.observe(this@RegisterActivity, Observer {

            it.showLoading?.let {
                showLoading()
            }
            it.showSuccess?.let {
                hideLoading()
                user = it.username
                pwd = it.password
                isLogin = true
                finish()
            }
            it.showError?.let {
                hideLoading()
                isLogin = false
                toast(it)
            }
        })
    }

    override fun initData() {

    }

    fun showLoading(){
        StyledDialog.buildMdLoading().show()
    }

    fun hideLoading(){
        StyledDialog.dismissLoading()
    }

    private fun validate(): Boolean {
        var valid = true
        val username: String = et_register_username.text.toString()
        val password: String = et_register_password.text.toString()
        val password2: String = et_retry_password.text.toString()
        if (username.isEmpty()) {
            et_register_username.error = getString(R.string.username_not_empty)
            valid = false
        }
        if (password.isEmpty()) {
            et_register_password.error = getString(R.string.password_not_empty)
            valid = false
        }
        if (password2.isEmpty()) {
            et_retry_password.error = getString(R.string.confirm_password_not_empty)
            valid = false
        }
        if (password != password2) {
            et_retry_password.error = getString(R.string.password_cannot_match)
            valid = false
        }
        return valid
    }

}