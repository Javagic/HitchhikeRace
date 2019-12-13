package com.example.hitchhikerace.view.eventcreation

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.example.hitchhikerace.view.BaseRaceEventCreatorView
import com.example.hitchhikerace.view.PreferenceManager
import com.example.hitchhikerace.view.RaceEventViewModel
import kotlinx.android.synthetic.main.screen_crew_meating.*

class CrewMeatingViewImpl : BaseRaceEventCreatorView() {
    override fun getLayoutId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        val arrayAdapter =
            ArrayAdapter<String>(
                view.context,
                android.R.layout.simple_spinner_item,
                PreferenceManager().getCrewList()
            )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCrew.adapter = arrayAdapter
    }

    override fun createRaceEventViewModel(): RaceEventViewModel {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getEventTitle(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}