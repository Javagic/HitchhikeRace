package com.example.hitchhikerace.view

import android.os.Bundle
import android.view.View
import com.example.hitchhikerace.R
import kotlinx.android.synthetic.main.screen_current_statistic.*

class LegendViewImpl : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.screen_current_statistic

    override fun initView(view: View, savedInstanceState: Bundle?) {
        tvStatistic.text = getString(R.string.legend)
    }

}