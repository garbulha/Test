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

            private val mList = arrayListOf(main1(baseContext),main2(baseContext))

            override fun getItemCount(): Int {
                return mList.size
            }

            override fun onCreateSlidingPanel(position: Int): ISlidingUpPanel<*> {
                val panel = mList[position]
                if (position == 0) {
                    panel.slideState = EXPANDED
                } else {
                    panel.slideState = HIDDEN
                }

                return panel
            }

            override fun onBindView(panel: ISlidingUpPanel<*>, position: Int) {
                if (mList.size == 0)
                    return

                val BasePanel = panel as Base
                BasePanel.isClickable = true
                BasePanel.setOnClickListener {
                    if (panel.slideState != EXPANDED) {
                        sliding_up_panel_layout.expandPanel()
                    } else {
                        sliding_up_panel_layout.collapsePanel()
                    }
                }
            }
        })













    }


}