package com.pretang.kotlinweatherapp.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import com.pretang.kotlinweatherapp.R
import com.pretang.kotlinweatherapp.domain.commands.RequestForecastCommand
import com.pretang.kotlinweatherapp.domain.model.ForecastList
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {

    private val items = listOf(
            "Mon 6/23 - Sunny - 31/17",
            "Tue 6/24 - Foggy - 21/8",
            "Wed 6/25 - Cloudy - 22/17",
            "Thurs 6/26 - Rainy - 18/11",
            "Fri 6/27 - Foggy - 21/10",
            "Sat 6/28 - TRAPPED IN WEATHERSTATION - 23/18",
            "Sun 6/29 - Sunny - 20/7"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        doAsync {
            val result = RequestForecastCommand("94043").excute()
            uiThread { recycler.adapter = RecyclerAdapter(result) }
        }
    }

    private class RecyclerAdapter(val weekForecast: ForecastList) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
        override fun getItemCount(): Int = weekForecast.dailyForecast.size

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            with(weekForecast.dailyForecast[position]) {
                holder?.textView?.text = "$date - $description - $high/$low"
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
            return ViewHolder(TextView(parent?.context))
        }

        class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
    }
}
