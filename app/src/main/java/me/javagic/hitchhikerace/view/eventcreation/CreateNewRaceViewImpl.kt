package me.javagic.hitchhikerace.view.eventcreation

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import me.javagic.hitchhikerace.R
import me.javagic.hitchhikerace.app.RaceApplication
import me.javagic.hitchhikerace.domain.RaceEventInteractor
import me.javagic.hitchhikerace.data.database.entity.SingleRaceEntity
import me.javagic.hitchhikerace.domain.SingleRaceInteractor
import me.javagic.hitchhikerace.utils.dateFormat
import me.javagic.hitchhikerace.utils.showDatePickerDialogOnClick
import me.javagic.hitchhikerace.utils.showKeyboard
import me.javagic.hitchhikerace.utils.subscribeWithErrorLogConsumer
import me.javagic.hitchhikerace.view.fragments.base.BaseFragment
import me.javagic.hitchhikerace.data.PreferenceManager
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