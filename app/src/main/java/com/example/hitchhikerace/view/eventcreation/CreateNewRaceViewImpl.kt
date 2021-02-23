package com.example.hitchhikerace.view.eventcreation

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.hitchhikerace.R
import com.example.hitchhikerace.app.RaceApplication
import com.example.hitchhikerace.domain.RaceEventInteractor
import com.example.hitchhikerace.data.database.entity.SingleRaceEntity
import com.example.hitchhikerace.domain.SingleRaceInteractor
import com.example.hitchhikerace.utils.dateFormat
import com.example.hitchhikerace.utils.showDatePickerDialogOnClick
import com.example.hitchhikerace.utils.showKeyboard
import com.example.hitchhikerace.utils.subscribeWithErrorLogConsumer
import com.example.hitchhikerace.view.fragments.base.BaseFragment
import com.example.hitchhikerace.data.PreferenceManager
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.screen_new_race.*
import java.util.*
import javax.inject.Inject

class CreateNewRaceViewImpl : BaseFragment() {

    @Inject
    lateinit var raceInteractor: SingleRaceInteractor

    @Inject
    lateinit var raceEventInteractor: RaceEventInteractor

    override fun getLayoutId(): Int = R.layout.screen_new_race

    override fun initView(view: View, savedInstanceState: Bundle?) {
        RaceApplication.appComponent.inject(this)
        tvScreenTitle.text = screenTitle
        etRaceTitle.showKeyboard()
        btnCreateNewRace.setOnClickListener {
            raceInteractor.addSingleRace(
                SingleRaceEntity(
                    etRaceTitle.text.toString(),
                    etRaceStartDate.text.toString(),
                    etRaceEndDate.text.toString()
                )
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWithErrorLogConsumer {
                    findNavController().setGraph(R.navigation.race_navigation)
                    PreferenceManager().saveCurrentRace(it)
                }
        }
        btnCancelNewRace.setOnClickListener { close() }
        activity?.let {
            etRaceStartDate.showDatePickerDialogOnClick(it, ::dateSetListener)
            etRaceEndDate.showDatePickerDialogOnClick(it, ::dateSetListener)
        }
    }

    private fun dateSetListener(date: Date, editText: EditText) {
        context?.dateFormat?.format(date)?.let {
            editText.setText(it)
        }
    }

}