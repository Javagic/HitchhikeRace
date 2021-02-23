package com.example.hitchhikerace.view.eventcreation

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.example.hitchhikerace.R
import com.example.hitchhikerace.data.database.entity.RaceEventEntity
import com.example.hitchhikerace.data.pojo.RaceEventType
import com.example.hitchhikerace.utils.showKeyboard
import com.example.hitchhikerace.view.fragments.base.BaseRaceEventView
import com.example.hitchhikerace.view.RaceEventViewModel
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