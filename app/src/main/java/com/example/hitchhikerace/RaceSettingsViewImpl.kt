package com.example.hitchhikerace

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.example.hitchhikerace.app.RaceApplication
import com.example.hitchhikerace.utils.navigateOnClick
import com.example.hitchhikerace.view.BaseFragment
import com.example.hitchhikerace.view.PreferenceManager
import kotlinx.android.synthetic.main.screen_race_settings.*
import javax.inject.Inject

class RaceSettingsViewImpl : BaseFragment() {

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun getLayoutId(): Int = R.layout.screen_race_settings

    override fun initView(view: View, savedInstanceState: Bundle?) {
        RaceApplication.appComponent.inject(this)
        btnCrewManagment.navigateOnClick(R.id.settings_to_crew_management)
        btnCurrentStatistic.navigateOnClick(R.id.settings_to_current_statistic)
        btnLegend.navigateOnClick(R.id.settings_to_legend)
        btnEventsList.setOnClickListener {
            Navigation.findNavController(btnEventsList).navigate(
                R.id.settings_to_events_list,
                bundleOf("raceId" to preferenceManager.getCurrentRace())
            )
        }
    }

}