package com.alexproject.testapplication.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.alexproject.testapplication.R
import com.alexproject.testapplication.databinding.StatisticTeamItemBinding
import com.alexproject.testapplication.databinding.TableHeaderBinding
import com.alexproject.testapplication.models.StatisticTable
import com.squareup.picasso.Picasso

class StatisticTableAdapter : RecyclerView.Adapter<StatisticsHolder>() {

    var statistic = listOf<StatisticTable>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsHolder {
        return when (viewType) {
            R.layout.statistic_team_item -> StatisticsHolder.TeamStatisticsViewHolder(
                StatisticTeamItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.table_header -> StatisticsHolder.GroupsViewHolder(
                TableHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: StatisticsHolder, position: Int) {
        when (holder) {
            is StatisticsHolder.TeamStatisticsViewHolder -> holder.bind(
                statistic[position] as StatisticTable.TeamStatistic
            )
            is StatisticsHolder.GroupsViewHolder -> holder.bind(
                (statistic[position] as StatisticTable.Group).nameGroup
            )
        }
    }

    override fun getItemCount() = statistic.size

    override fun getItemViewType(position: Int): Int {
        return when (statistic[position]) {
            is StatisticTable.TeamStatistic -> R.layout.statistic_team_item
            is StatisticTable.Group -> R.layout.table_header
        }
    }
}

sealed class StatisticsHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class TeamStatisticsViewHolder(
        private val binding: StatisticTeamItemBinding
    ) : StatisticsHolder(binding) {

        fun bind(teamStatistic: StatisticTable.TeamStatistic) {
            binding.apply {
                position.text = teamStatistic.position.toString()
                points.text = teamStatistic.points.toString()
                team.text = teamStatistic.team.name
                games.text = itemView.context.getString(
                    R.string.gamesTeamStatistic,
                    teamStatistic.loseOvertime + teamStatistic.lose +
                            teamStatistic.winOvertime + teamStatistic.win
                )
                goals.text = itemView.context.getString(
                    R.string.goalsTeamStatistic,
                    teamStatistic.againstGoals,
                    teamStatistic.forGoals
                )
                Picasso.get().load(teamStatistic.team.logo).into(logoTeam)
            }
        }
    }

    class GroupsViewHolder(
        private val binding: TableHeaderBinding
    ) : StatisticsHolder(binding) {

        fun bind(groupName: String) {
            binding.apply {
                nameGroup.text = groupName
                teamsHeader.text = itemView.context.getString(R.string.team)
                gamesHeader.text = itemView.context.getString(R.string.games)
                goalsHeader.text = itemView.context.getString(R.string.goals)
                pointsHeader.text = itemView.context.getString(R.string.points)
            }
        }
    }
}