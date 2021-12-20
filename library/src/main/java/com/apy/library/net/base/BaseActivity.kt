package com.apy.library.net.base

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.apy.library.net.net.http.TokenLose
import com.apy.library.net.rxbus.RxBus
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

    /**
     * 本方法初始化数据
     */
    abstract fun initData()

    /**
     * token丢失时需要要做的事情
     */
    open fun tokenLoseDoing() = Unit

    override fun onDestroy() {
//        Settings.activityStack().remove(this)
        disposables.clear()
        super.onDestroy()
    }

}