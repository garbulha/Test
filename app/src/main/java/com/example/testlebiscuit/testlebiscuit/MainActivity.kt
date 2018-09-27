package com.example.testlebiscuit.testlebiscuit

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.xw.repo.supl.ISlidingUpPanel
import com.xw.repo.supl.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.mainactivity.*
import android.view.View
import com.xw.repo.supl.SlidingUpPanelLayout.EXPANDED
import com.xw.repo.supl.SlidingUpPanelLayout.HIDDEN


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainactivity)


        sliding_up_panel_layout.setAdapter(object : SlidingUpPanelLayout.Adapter() {

            private val mSize = arrayListOf("a","a","a","a","a").size

            override fun getItemCount(): Int {
                return mSize
            }

            override fun onCreateSlidingPanel(position: Int): ISlidingUpPanel<*> {
                val panel = WeatherPanelView(this@DemoActivity1)
                if (position == 0) {
                    panel.setSlideState(EXPANDED)
                } else {
                    panel.setSlideState(HIDDEN)
                }

                return panel
            }

            override fun onBindView(panel: ISlidingUpPanel<*>, position: Int) {
                if (mSize == 0)
                    return

                val BasePanel = panel as BaseWeatherPanelView
                BasePanel.setWeatherModel(mWeatherList.get(position))
                BasePanel.setClickable(true)
                BasePanel.setOnClickListener(View.OnClickListener {
                    if (panel.slideState != EXPANDED) {
                        sliding_up_panel_layout.expandPanel()
                    } else {
                        sliding_up_panel_layout.collapsePanel()
                    }
                })
            }
        })













    }


}