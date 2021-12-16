package com.johnrambo.ktea.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.johnrambo.ktea.ktExtends.activity

/**
 * Create by JohnRambo at 2018/9/3
 */
abstract class LoadingFragment : Fragment() {

    /*private val loadingView by lazy {
        SpinKitView(context).apply {
            setIndeterminateDrawable(Circle().apply {
                color = resources.getColor(R.color.loading_view)
            })
            visibility = View.GONE
        }
    }*/

    fun showLoading() {
//        loadingView.visibility = View.VISIBLE
        activity<BaseActivity> { showLoading() }
    }

    fun dismissLoading() {
//        loadingView.visibility = View.GONE
        activity<BaseActivity> { dismissLoading() }
    }

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /*when (view) {
            is FrameLayout -> loadingView.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT).apply {
                gravity = Gravity.CENTER
                view.addView(loadingView)
            }
            is ConstraintLayout -> {
                // 在约束布局中居中, 注意:ConstraintSet.PARENT_ID
                loadingView.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT).apply {
                    leftToLeft = ConstraintSet.PARENT_ID
                    rightToRight = ConstraintSet.PARENT_ID
                    topToTop = ConstraintSet.PARENT_ID
                    bottomToBottom = ConstraintSet.PARENT_ID
                }
                view.addView(loadingView)
            }
            else -> throw IllegalArgumentException("LoadingFragment的父布局必须为FrameLayout或者ConstraintLayout")
        }*/
        initData()
    }

    abstract fun initData()
}