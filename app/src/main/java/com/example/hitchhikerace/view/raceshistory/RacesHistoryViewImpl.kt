package com.example.hitchhikerace.view.raceshistory

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hitchhikerace.R
import com.example.hitchhikerace.app.RaceApplication
import com.example.hitchhikerace.domain.SingleRaceInteractor
import com.example.hitchhikerace.utils.isVisible
import com.example.hitchhikerace.utils.subscribeWithErrorLogConsumer
import com.example.hitchhikerace.view.BaseFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.screen_races_history.*
import javax.inject.Inject

class RacesHistoryViewImpl : BaseFragment() {

    @Inject
    lateinit var raceInteractor: SingleRaceInteractor

    private val racesAdapter = RacesHistoryAdapter {
        findNavController().navigate(R.id.races_history_to_race_info, bundleOf("raceId" to it.id))
    }

    override fun getLayoutId(): Int = R.layout.screen_races_history

    override fun initView(view: View, savedInstanceState: Bundle?) {
        RaceApplication.appComponent.inject(this)
        rvRacesHistory.apply {
            adapter = racesAdapter
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
//            addItemDecoration(BaseItemDecoration(20))
        }
        raceInteractor.getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWithErrorLogConsumer {
                racesAdapter.submitList(it)
                tvHistoryEmpty.isVisible = it.isEmpty()
                rvRacesHistory.isVisible = it.isNotEmpty()
            }
            .addTo(compositeDisposable)
    }

}