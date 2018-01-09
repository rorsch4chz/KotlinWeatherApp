package com.pretang.kotlinweatherapp.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import com.pretang.kotlinweatherapp.R
import com.pretang.kotlinweatherapp.domain.commands.RequestForecastCommand
import com.pretang.kotlinweatherapp.domain.model.Forecast
import com.pretang.kotlinweatherapp.domain.model.ForecastList
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_forecast.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity(), ToolbarManager {

    override val toolbar by lazy { find<Toolbar>(R.id.toolrbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()

        recycler.layoutManager = LinearLayoutManager(this)
        attachToScroll(recycler)
    }

    override fun onResume() {
        super.onResume()
        doAsync {
            val result = RequestForecastCommand("94043").excute()
            uiThread {
                recycler.adapter = RecyclerAdapter(result) {
                    startActivity<DetailActivity>(DetailActivity.ID to it.id,
                            DetailActivity.CITY_NAME to result.city)
                }
                toolbarTitle = "${result.city} (${result.country})"
            }
        }
    }

    private class RecyclerAdapter(val weekForecast: ForecastList, val itemClick: (Forecast) -> Unit) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
        override fun getItemCount(): Int = weekForecast.dailyForecast.size

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            holder?.bindForecast(weekForecast.dailyForecast[position])
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
            val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_forecast, parent, false)
            return ViewHolder(view, itemClick)
        }

        class ViewHolder(view: View, val itemClick: (Forecast) -> Unit) : RecyclerView.ViewHolder(view) {

            fun bindForecast(forecast: Forecast) {
                with(forecast) {
                    Picasso.with(itemView.context).load(iconUrl).into(itemView.icon)
                    itemView.date.text = date
                    itemView.description.text = description
                    itemView.maxTemperature.text = "${high.toString()}"
                    itemView.minTemperature.text = "${low.toString()}"
                    itemView.setOnClickListener { itemClick(forecast) }
                }
            }
        }
    }
}

