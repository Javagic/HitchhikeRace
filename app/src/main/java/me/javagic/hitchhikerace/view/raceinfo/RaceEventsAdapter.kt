package me.javagic.hitchhikerace.view.raceinfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.javagic.hitchhikerace.R
import me.javagic.hitchhikerace.data.database.entity.RaceEventEntity
import me.javagic.hitchhikerace.domain.EventTypeMapper
import kotlinx.android.synthetic.main.item_race_event.view.*

class RaceEventsAdapter(private val onRaceClick: (RaceEventEntity) -> Unit) :
    RecyclerView.Adapter<RaceEventsAdapter.RacesHistoryViewHolder>() {

    private val data: MutableList<RaceEventEntity> = mutableListOf()
    private var mapper = EventTypeMapper()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RacesHistoryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_race_event, parent, false)
        return RacesHistoryViewHolder(view, onRaceClick)
    }

    fun submitList(list: List<RaceEventEntity>) {
        data.clear()
        data.addAll(list)
        mapper = EventTypeMapper()
        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RacesHistoryViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class RacesHistoryViewHolder(
        itemView: View,
        private val onRaceClick: (RaceEventEntity) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(event: RaceEventEntity) {
            itemView.setOnClickListener { onRaceClick(event) }
            //TODO грязь
            itemView.tvRaceEventTitle.text = mapper.eventToString(
                itemView.tvRaceEventTitle.context,
                event
            )
        }
    }
}