package com.alexproject.testapplication.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.alexproject.domain.models.EventsAdapterItem
import com.alexproject.domain.models.Game
import com.alexproject.testapplication.R
import com.alexproject.testapplication.databinding.GameEventsItemBinding
import com.alexproject.testapplication.databinding.GameEventsPeriodItemBinding
import com.alexproject.testapplication.databinding.KBinding
import com.alexproject.testapplication.objects.EMPTY_STRING
import com.alexproject.testapplication.objects.GOAL

class T(
    private val game: Game,
) : RecyclerView.Adapter<RecyclerTHolder>() {

    var eventsItem = listOf<EventsAdapterItem>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerTHolder {
        return when (viewType) {
            R.layout.k -> RecyclerTHolder.EventsViewHolder(
                KBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.game_events_period_item -> RecyclerTHolder.PeriodsViewHolder(
                GameEventsPeriodItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerTHolder, position: Int) {
        when (holder) {
            is RecyclerTHolder.EventsViewHolder -> holder.bind(
                eventsItem[position] as EventsAdapterItem.GameEvents,
                game.homeTeam.id
            )
            is RecyclerTHolder.PeriodsViewHolder -> holder.bind(
                (eventsItem[position] as EventsAdapterItem.Period).number
            )
        }
    }

    override fun getItemCount() = eventsItem.size

    override fun getItemViewType(position: Int): Int {
        return when (eventsItem[position]) {
            is EventsAdapterItem.GameEvents -> R.layout.k
            is EventsAdapterItem.Period -> R.layout.game_events_period_item
        }
    }
}

sealed class RecyclerTHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class EventsViewHolder(private val binding: KBinding) : RecyclerTHolder(binding) {

        fun bind(gameEvents: EventsAdapterItem.GameEvents, homeTeamId: Int) {
            binding.gameEventsItemView.apply {
                setSide(gameEvents.team.id == homeTeamId)
                if (gameEvents.type == GOAL){
                    setImage(R.drawable.goal)
                } else {
                    setImage(R.drawable.penalty)
                }
                setPlayerText(gameEvents.players ?: "team ${gameEvents.team.name}")
                setTimerText(gameEvents.minute)
                setAssistsText(parseAssists(gameEvents.assistsFirst,gameEvents.assistsSecond))
            }
        }

        private fun parseAssists(firstAssists: String?, secondAssists: String?): String {
            return when {
                firstAssists.isNullOrEmpty() -> EMPTY_STRING
                secondAssists.isNullOrEmpty() -> firstAssists
                else -> itemView.context.getString(
                    R.string.assists,
                    firstAssists,
                    secondAssists
                )
            }
        }
    }

    class PeriodsViewHolder(
        private val binding: GameEventsPeriodItemBinding
    ) : RecyclerTHolder(binding) {

        fun bind(gamePeriod: String) {
            binding.period.text = itemView.context.getString(R.string.period, gamePeriod)
            //binding.score.text = itemView.context.getString(R.string.score, score)
        }
    }

}