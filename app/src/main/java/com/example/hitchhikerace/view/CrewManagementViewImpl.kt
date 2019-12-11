package com.example.hitchhikerace.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hitchhikerace.R
import com.example.hitchhikerace.database.hideKeyboard
import com.example.hitchhikerace.database.navController
import kotlinx.android.synthetic.main.screen_crew_managment.*

class CrewManagementViewImpl : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.screen_crew_managment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnCreateEvent.setOnClickListener {
            PreferenceManager().saveCrewList(etCrew.text.toString())
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