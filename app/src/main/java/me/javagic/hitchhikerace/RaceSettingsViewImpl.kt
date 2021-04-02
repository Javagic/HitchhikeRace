package me.javagic.hitchhikerace

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import me.javagic.hitchhikerace.app.RaceApplication
import me.javagic.hitchhikerace.utils.navigateOnClick
import me.javagic.hitchhikerace.view.fragments.base.BaseFragment
import me.javagic.hitchhikerace.data.PreferenceManager
import kotlinx.android.synthetic.main.screen_race_settings.*
import me.javagic.hitchhikerace.domain.ExelInteractor
import javax.inject.Inject

class RaceSettingsViewImpl : BaseFragment() {

    @Inject
    lateinit var preferenceManager: PreferenceManager
    @Inject
    lateinit var exelInteractor: ExelInteractor

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
        btnExportStatistic.setOnClickListener {
            exelInteractor.processFile(it.context)
        }
    }

}