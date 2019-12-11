package com.example.hitchhikerace.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hitchhikerace.R
import com.example.hitchhikerace.database.DataBaseInteractorImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.screen_current_statistic.*

class CurrentStatisticViewImpl : Fragment() {

    private val disposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.screen_current_statistic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DataBaseInteractorImpl().getStatistic()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                view.context?.let {
                    tvStatistic.text = EventTypeMapper().mapEventEntity(it, list)
                }
            }, {})
            .let { disposable.add(it) }
    }

}