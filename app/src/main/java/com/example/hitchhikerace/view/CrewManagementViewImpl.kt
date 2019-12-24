package com.example.hitchhikerace.view

import android.os.Bundle
import android.view.View
import com.example.hitchhikerace.R
import kotlinx.android.synthetic.main.screen_crew_managment.*

class CrewManagementViewImpl : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.screen_crew_managment

    override fun initView(view: View, savedInstanceState: Bundle?) {
        etCrew.setText(PreferenceManager().getCrewList().joinToString(" "))
        etCheckPoint.setText(PreferenceManager().getCheckPointList().joinToString(" "))
        btnCreateEvent.setOnClickListener {
            PreferenceManager().saveCrewList(etCrew.text.toString())
            PreferenceManager().saveCheckPointList(etCheckPoint.text.toString())
            close()
        }
        btnCancel.setOnClickListener {
            close()
        }
    }

}