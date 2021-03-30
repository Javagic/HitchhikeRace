package me.javagic.hitchhikerace.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import me.javagic.hitchhikerace.R
import me.javagic.hitchhikerace.app.RaceApplication
import me.javagic.hitchhikerace.utils.isVisible
import me.javagic.hitchhikerace.utils.startSplashScreenAnimation
import me.javagic.hitchhikerace.data.PreferenceManager
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RaceApplication.appComponent.inject(this)
        this.supportActionBar?.hide()
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
