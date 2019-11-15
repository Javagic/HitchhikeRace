package com.example.hitchhikerace.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
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
        DataBaseInteractorImpl().getAllRaceEventList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                view.context?.let {
                    tvStatistic.text = EventTypeMapper().mapEventEntity(it, list)
                    tvStatistic.setOnLongClickListener {
                        (context?.getSystemService(CLIPBOARD_SERVICE) as? ClipboardManager)
                            ?.let {
                                val clip = ClipData.newPlainText("statistic", tvStatistic.text)
                                it.primaryClip = clip
                            }
                        true
                    }
                }
            }, {})
            .let { disposable.add(it) }
    }

}