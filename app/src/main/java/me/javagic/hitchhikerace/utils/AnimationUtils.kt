package me.javagic.hitchhikerace.utils

import android.app.Activity
import android.os.Handler
import android.view.animation.AnimationUtils
import me.javagic.hitchhikerace.R
import kotlinx.android.synthetic.main.activity_main.*

private const val ANIMATION_TIME: Long = 1150

fun Activity.startSplashScreenAnimation(onAnimationEnd: () -> Unit) {
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
        onAnimationEnd()
    }, ANIMATION_TIME)
}