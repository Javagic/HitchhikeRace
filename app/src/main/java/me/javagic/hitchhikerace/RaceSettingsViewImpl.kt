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
            excelInteractor.processFile(it.context)
        }
    }

    private fun openLegendLink() {
        val uri: Uri = Uri.parse("https://vk.com/wall-158114445_4669")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    private fun openMapLink() {
        val uri: Uri = Uri.parse("https://yandex.ru/maps/?um=constructor:d2371def1d81fd6c1b97c721c2e3b8ee31603666c1106900174e80a13b1dfe3e&source=constructorLink")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}