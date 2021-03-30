package me.javagic.hitchhikerace.view.eventcreation

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import me.javagic.hitchhikerace.R
import me.javagic.hitchhikerace.data.database.entity.RaceEventEntity
import me.javagic.hitchhikerace.data.pojo.RaceEventType
import me.javagic.hitchhikerace.utils.showKeyboard
import me.javagic.hitchhikerace.view.fragments.base.BaseRaceEventView
import me.javagic.hitchhikerace.view.RaceEventViewModel
import kotlinx.android.synthetic.main.screen_other.*

class OtherViewImpl : BaseRaceEventView() {

    override fun getLayoutId(): Int = R.layout.screen_other

    override fun initView(view: View, savedInstanceState: Bundle?) {
        timePicker.init()
        raceEvent?.run {
            etMainText.setText(mainText)
            timePicker.hours = hour.toInt()
            timePicker.minutes = minute.toInt()
        }
        etMainText.showKeyboard()
    }

    override fun createRaceEventViewModel(): RaceEventViewModel = RaceEventViewModel(
        RaceEventType.CUSTOM,
        etMainText.text.toString(),
        "",
        "",
        timePicker.hours.toString(),
        timePicker.minutes.toString(),
        getCurrentLocation().latitude,
        getCurrentLocation().longitude
    )

    override fun getEventTitle(): Int = R.string.other

    companion object {
        fun instance(raceEntity: RaceEventEntity?): OtherViewImpl {
            return OtherViewImpl().apply {
                arguments = bundleOf("raceEvent" to raceEntity)
            }
        }
    }
}