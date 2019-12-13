package com.example.hitchhikerace.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hitchhikerace.R
import com.example.hitchhikerace.database.DataBaseInteractorImpl
import com.example.hitchhikerace.database.RaceEventType
import com.example.hitchhikerace.database.hideKeyboard
import com.example.hitchhikerace.database.navController
import kotlinx.android.synthetic.main.screen_base_event.*

class CreateBaseEventViewImpl : Fragment() {

    private val eventType by lazy {
        arguments?.get("event_type") as RaceEventType
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.screen_base_event, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val childFragment = EventTypeMapper().getFeatureRaceEventFragment(view.context, eventType)
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.eventFeatureContainer, childFragment).commit()
        tvEventTitle.text = getString(childFragment.getEventTitle())
        btnCreateEvent.setOnClickListener {
            DataBaseInteractorImpl().addEvent(childFragment.createRaceEventViewModel())
            close()
        }
        btnCancel.setOnClickListener {
            close()
        }
    }


    private fun close() {
        activity.hideKeyboard()
        navController()?.popBackStack()
    }

}