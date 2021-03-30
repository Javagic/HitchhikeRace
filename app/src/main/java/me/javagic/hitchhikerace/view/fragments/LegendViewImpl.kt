package me.javagic.hitchhikerace.view.fragments

import android.os.Bundle
import android.view.View
import me.javagic.hitchhikerace.R
import me.javagic.hitchhikerace.view.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.screen_current_statistic.*

class LegendViewImpl : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.screen_current_statistic

    override fun initView(view: View, savedInstanceState: Bundle?) {
        tvStatistic.text = getString(R.string.legend)
    }

}