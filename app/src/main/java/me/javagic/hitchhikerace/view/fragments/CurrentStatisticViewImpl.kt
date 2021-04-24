package me.javagic.hitchhikerace.view.fragments

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.View
import me.javagic.hitchhikerace.R
import me.javagic.hitchhikerace.app.RaceApplication
import me.javagic.hitchhikerace.domain.RaceEventInteractor
import me.javagic.hitchhikerace.data.PreferenceManager
import me.javagic.hitchhikerace.domain.EventTypeMapper
import me.javagic.hitchhikerace.view.fragments.base.BaseFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.screen_current_statistic.*
import javax.inject.Inject

@Deprecated(message = "Не используется вроде")
class CurrentStatisticViewImpl : BaseFragment() {

    @Inject
    lateinit var eventInteractor: RaceEventInteractor

    private val disposable = CompositeDisposable()

    override fun getLayoutId(): Int = R.layout.screen_current_statistic

    override fun initView(view: View, savedInstanceState: Bundle?) {
        RaceApplication.appComponent.inject(this)
        eventInteractor.getAllRaceEventList(PreferenceManager().getCurrentRace())
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