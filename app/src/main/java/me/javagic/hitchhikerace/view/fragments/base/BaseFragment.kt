package me.javagic.hitchhikerace.view.fragments.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import me.javagic.hitchhikerace.utils.hideKeyboard
import me.javagic.hitchhikerace.utils.navController
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment : Fragment() {

    protected val compositeDisposable = CompositeDisposable()

    protected val screenTitle by lazy {
        arguments?.get("screenTitle") as? String
    }

    abstract fun getLayoutId(): Int

    abstract fun initView(view: View, savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

    protected open fun close() {
        activity.hideKeyboard()
        navController()?.popBackStack()
    }
}