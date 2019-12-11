package com.example.hitchhikerace.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.hitchhikerace.R
import com.example.hitchhikerace.database.RaceEventType
import kotlinx.android.synthetic.main.screen_main.*

class MainScreenViewImpl : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.screen_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnCarStart.setOnClickListener {
            view.findNavController()
                .navigate(
                    R.id.main_to_create_event,
                    bundleOf("event_type" to RaceEventType.CAR_START)
                )
        }
        btnCrewManagment.setOnClickListener {
            view.findNavController()
                .navigate(
                    R.id.main_to_crew_management
                )
        }
        btnCurrentStatistic.setOnClickListener {
            view.findNavController().navigate(R.id.main_to_current_statistic)
        }
    }
}