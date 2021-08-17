package me.javagic.hitchhikerace.view.raceshistory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_races_history.view.*
import me.javagic.hitchhikerace.R
import me.javagic.hitchhikerace.data.database.entity.SingleRaceEntity

class RacesHistoryAdapter(
    private val onRaceClick: (SingleRaceEntity) -> Unit,
    private val onRaceLongClick: (SingleRaceEntity) -> Unit
) :
    RecyclerView.Adapter<RacesHistoryAdapter.RacesHistoryViewHolder>() {

    private val data: MutableList<SingleRaceEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RacesHistoryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_races_history, parent, false)
        return RacesHistoryViewHolder(view, onRaceClick, onRaceLongClick)
    }

    fun submitList(list: List<SingleRaceEntity>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RacesHistoryViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class RacesHistoryViewHolder(
        itemView: View,
        private val onRaceClick: (SingleRaceEntity) -> Unit,
        private val onRaceLongClick: (SingleRaceEntity) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(race: SingleRaceEntity) = itemView.apply {
            setOnClickListener { onRaceClick(race) }
            setOnLongClickListener {
                onRaceLongClick(race)
                true
            }
            tvRacesHistoryTitle.text = race.name
            tvRacesHistoryStartDate.text = context.getString(R.string.date_from, race.dateStart)
            tvRacesHistoryEndDate.text = context.getString(R.string.date_to, race.dateEnd)
        }
    }
}