package com.example.hitchhikerace.view.activity

import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.hitchhikerace.R
import com.example.hitchhikerace.app.RaceApplication
import com.example.hitchhikerace.utils.isVisible
import com.example.hitchhikerace.view.PreferenceManager
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
            initUi()
            findNavController(R.id.main_menu_host_fragment).setGraph(
                R.navigation.race_navigation,
                intent.extras
            )
        } else {
            startAnimations()
        }
    }

    fun showToast() {
        val layout = layoutInflater.inflate(R.layout.toast_confirmation, null)
        val toast = Toast(applicationContext)
        toast.setGravity(Gravity.BOTTOM, 0, 0)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
    }

    private fun startAnimations() {
        ivCompass.isVisible = true
        ivCompassBackground.isVisible = true
        stubLayout2.isVisible = true
        val anim = AnimationUtils.loadAnimation(this, R.anim.alpha)
        anim.reset()
        stubLayout2.clearAnimation()
        stubLayout2.startAnimation(anim)

        val rocketAnim = AnimationUtils.loadAnimation(this, R.anim.rocket_trans)
        rocketAnim.reset()
        ivCompass.clearAnimation()
        ivCompass.startAnimation(rocketAnim)

        val titleAnim = AnimationUtils.loadAnimation(this, R.anim.title_trans)
        titleAnim.reset()
        ivCompassBackground.clearAnimation()
        ivCompassBackground.startAnimation(titleAnim)

        Handler().postDelayed({
            initUi()
            findNavController(R.id.main_menu_host_fragment).setGraph(
                R.navigation.main_menu_navigation,
                intent.extras
            )
        }, LIFE_TIME)
    }

    private fun initUi() {
        stubLayout1.isVisible = false
        stubLayout2.isVisible = false
        ivCompass.isVisible = false
        ivCompassBackground.isVisible = false
        container_main.isVisible = true
    }

    companion object {
        val LIFE_TIME: Long = 1150
    }

//    override fun onSupportNavigateUp() = findNavController(R.navigation.navigation).navigateUp()

}
