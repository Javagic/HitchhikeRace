package me.javagic.hitchhikerace

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.screen_race_settings.*
import me.javagic.hitchhikerace.app.RaceApplication
import me.javagic.hitchhikerace.data.PreferenceManager
import me.javagic.hitchhikerace.domain.ExcelInteractor
import me.javagic.hitchhikerace.utils.navigateOnClick
import me.javagic.hitchhikerace.view.fragments.base.BaseFragment
import javax.inject.Inject


class RaceSettingsViewImpl : BaseFragment() {

    @Inject
    lateinit var preferenceManager: PreferenceManager

    @Inject
    lateinit var excelInteractor: ExcelInteractor

    override fun getLayoutId(): Int = R.layout.screen_race_settings

    override fun initView(view: View, savedInstanceState: Bundle?) {
        RaceApplication.appComponent.inject(this)
        btnCrewManagment.navigateOnClick(R.id.settings_to_crew_management)
//        btnCurrentStatistic.navigateOnClick(R.id.settings_to_current_statistic) gone
        btnLegend.setOnClickListener { openLegendLink() }
        btnMap.setOnClickListener { openMapLink() }
        btnEventsList.setOnClickListener {
            Navigation.findNavController(btnEventsList).navigate(
                R.id.settings_to_events_list,
                bundleOf("raceId" to preferenceManager.getCurrentRace())
            )
        }
        btnExportStatistic.setOnClickListener {
            excelInteractor.processFile(it.context, PreferenceManager().getCurrentRace())
        }
    }

    private fun openLegendLink() {
        try {
            val uri: Uri = Uri.parse(preferenceManager.getLegend())
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        } catch (e: Exception) {
        }
    }

    private fun openMapLink() {
        try {
            val uri: Uri = Uri.parse(preferenceManager.getMap())
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        } catch (e: Exception) {
        }
    }
}