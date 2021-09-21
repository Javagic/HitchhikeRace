package me.javagic.hitchhikerace.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import me.javagic.hitchhikerace.R
import me.javagic.hitchhikerace.app.RaceApplication
import me.javagic.hitchhikerace.data.PreferenceManager
import me.javagic.hitchhikerace.utils.isVisible
import me.javagic.hitchhikerace.utils.showToast
import me.javagic.hitchhikerace.utils.startSplashScreenAnimation
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RaceApplication.appComponent.inject(this)
        this.supportActionBar?.hide()
        //TODO тут падение если активити задестроилось
        setContentView(R.layout.activity_main)
        val raceStarted = preferenceManager.getCurrentRace() != -1L
        if (raceStarted) {
            initRaceControllers()
            findNavController(R.id.main_menu_host_fragment).setGraph(
                R.navigation.race_navigation,
                intent.extras
            )
        } else {
            startSplashScreenAnimation {
                initRaceControllers()
                findNavController(R.id.main_menu_host_fragment).setGraph(
                    R.navigation.main_menu_navigation,
                    intent.extras
                )
            }
        }
        intent?.data?.path?.let { data ->
            try {
                var start = data.indexOf("$") + 1
                var end = data.indexOf("$", start)
                preferenceManager.saveLegend(data.substring(start, end))
                start = data.indexOf("$", end + 1) + 1
                end = data.indexOf("$", start)
                preferenceManager.saveMap(data.substring(start, end))
                showToast()
            } catch (e: Exception) {
            }
        }
    }

    private fun initRaceControllers() {
        stubLayout1.isVisible = false
        stubLayout2.isVisible = false
        ivCompass.isVisible = false
        ivCompassBackground.isVisible = false
        container_main.isVisible = true
    }

//    override fun onSupportNavigateUp() = findNavController(R.navigation.navigation).navigateUp()

}
