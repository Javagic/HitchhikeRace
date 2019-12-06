package com.example.hitchhikerace.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hitchhikerace.R
import kotlinx.android.synthetic.main.screen_main.*

class MenuScreenFragment : Fragment(), MainScreenView {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.screen_main, null)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_car_start.setOnClickListener {
            val fragment = CreateEventViewImpl()
            fragmentManager?.beginTransaction()
                ?.replace(R.id.mainContainer, fragment)
                ?.commit()
        }
    }
}