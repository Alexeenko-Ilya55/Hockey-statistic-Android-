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
import com.alexproject.testapplication.objects.EMPTY_STRING
import com.alexproject.testapplication.objects.GOAL

class GameEventsAdapter(
    private val game: Game,
) : RecyclerView.Adapter<RecyclerHolder>() {

    var eventsItem = listOf<EventsAdapterItem>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        return when (viewType) {
            R.layout.game_events_item -> RecyclerHolder.EventsViewHolder(
                GameEventsItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.game_events_period_item -> RecyclerHolder.PeriodsViewHolder(
                GameEventsPeriodItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        when (holder) {
            is RecyclerHolder.EventsViewHolder -> holder.bind(
                eventsItem[position] as EventsAdapterItem.GameEvents,
                game.homeTeam.id
            )
            is RecyclerHolder.PeriodsViewHolder -> holder.bind(
                (eventsItem[position] as EventsAdapterItem.Period).number
            )
        }
    }

    override fun getItemCount() = eventsItem.size

    override fun getItemViewType(position: Int): Int {
        return when (eventsItem[position]) {
            is EventsAdapterItem.GameEvents -> R.layout.game_events_item
            is EventsAdapterItem.Period -> R.layout.game_events_period_item
        }
    }
}

sealed class RecyclerHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class EventsViewHolder(private val binding: GameEventsItemBinding) : RecyclerHolder(binding) {

        fun bind(gameEvents: EventsAdapterItem.GameEvents, homeTeamId: Int) {
            if (gameEvents.team.id == homeTeamId)
                homeTeamBindEvent(gameEvents)
            else
                awayTeamBindEvents(gameEvents)
        }

        private fun awayTeamBindEvents(gameEvents: EventsAdapterItem.GameEvents) {
            binding.apply {
                groupAway.isVisible = true
                groupHome.isVisible = false
                if (gameEvents.type == GOAL) {
                    awayPlayer.isVisible = true
                    awayAssists.isVisible = true
                    awayPlayerRemove.isVisible = false
                    awayPlayer.text = gameEvents.players
                    awayAssists.text = setAssists(gameEvents.assistsFirst, gameEvents.assistsSecond)
                    awayEventImage.setImageResource(R.drawable.goal)
                    awayTimerEvent.text = gameEvents.minute
                } else {
                    awayPlayer.isVisible = false
                    awayAssists.isVisible = false
                    awayEventImage.setImageResource(R.drawable.penalty)
                    awayTimerEvent.text = gameEvents.minute
                    awayPlayerRemove.isVisible = true
                    awayPlayerRemove.text = itemView.context.getString(
                        R.string.penaltyComment,
                        gameEvents.players,
                        gameEvents.comment
                    )
                }
            }
        }

        private fun homeTeamBindEvent(gameEvents: EventsAdapterItem.GameEvents) {
            binding.apply {
                groupAway.isVisible = false
                groupHome.isVisible = true
                if (gameEvents.type == GOAL) {
                    homePlayer.isVisible = true
                    homeAssists.isVisible = true
                    homePlayerRemove.isVisible = false

                    homePlayer.text = gameEvents.players
                    homeAssists.text = setAssists(gameEvents.assistsFirst, gameEvents.assistsSecond)
                    homeEventImage.setImageResource(R.drawable.goal)
                    homeTimerEvent.text = gameEvents.minute
                } else {
                    homePlayer.isVisible = false
                    homeAssists.isVisible = false
                    homeEventImage.setImageResource(R.drawable.penalty)
                    homeTimerEvent.text = gameEvents.minute
                    homePlayerRemove.isVisible = true
                    homePlayerRemove.text = itemView.context.getString(
                        R.string.penaltyComment,
                        gameEvents.players,
                        gameEvents.comment
                    )
                }
            }
        }

        private fun setAssists(firstAssists: String?, secondAssists: String?): String {
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
    ) : RecyclerHolder(binding) {

        fun bind(gamePeriod: String) {
            binding.period.text = itemView.context.getString(R.string.period, gamePeriod)
            //binding.score.text = itemView.context.getString(R.string.score, score)
        }
    }

}

