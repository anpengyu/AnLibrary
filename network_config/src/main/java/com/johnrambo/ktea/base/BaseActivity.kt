package com.johnrambo.ktea.base

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.johnrambo.ktea.Settings
import com.johnrambo.ktea.common.createLoadingDialog
import com.johnrambo.ktea.common.delayUI
import com.johnrambo.ktea.net.http.TokenLose
import com.johnrambo.ktea.rxbus.RxBus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Job
import java.util.concurrent.TimeUnit

/**
 * Create by JohnRambo at 2018/7/31
 */
abstract class BaseActivity : AppCompatActivity() {

//    private val loadingView by lazy { createLoadingViewInFrameLayout(this) }

    protected val jobs by lazy { mutableListOf<Job>() }
    protected val disposables by lazy { CompositeDisposable() }
    protected open var loadingMilliSeconds = 0L

    @Volatile
    private var showLoadingTimes = 0

    private val loadingProgress by lazy {
        createLoadingDialog(this) { showLoadingTimes = 0 }
    }

    companion object {
        val loginObservable by lazy {
            RxBus.BUS.toObservable(TokenLose::class.java)
                    .throttleFirst(10, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())!!
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Settings.activityStack().add(this)
        val handler = Handler()
        with(window.decorView) {
            post {
                handler.post {
                    /*this as FrameLayout
                    addView(loadingView)*/
                    initData()
                }
            }
        }
        disposables.add(
                loginObservable.subscribe { tokenLoseDoing() }
        )
    }

    // 显示加载框
    fun showLoading() {
        ++showLoadingTimes
        if (!loadingProgress.isShowing) {
            loadingProgress.show()
        }
//        loadingView.visibility = View.VISIBLE
    }

    // 关闭加载框
    fun dismissLoading() {
        if (showLoadingTimes > 0) {
            --showLoadingTimes
        }
        if (showLoadingTimes == 0) {
            if (loadingProgress.isShowing) delayUI(loadingMilliSeconds) {
                if (loadingProgress.isShowing) loadingProgress.dismiss()
            }
        }
//        loadingView.visibility=View.GONE
    }

    /**
     * 本方法初始化数据
     */
    abstract fun initData()

    /**
     * token丢失时需要要做的事情
     */
    open fun tokenLoseDoing() = Unit

    override fun onDestroy() {
        if (loadingProgress.isShowing) {
            loadingProgress.cancel()
        }
        /*with(window.decorView) {
            this as FrameLayout
            removeView(loadingView)
        }*/
//        Settings.activityStack().remove(this)
        disposables.clear()
        super.onDestroy()
    }

}