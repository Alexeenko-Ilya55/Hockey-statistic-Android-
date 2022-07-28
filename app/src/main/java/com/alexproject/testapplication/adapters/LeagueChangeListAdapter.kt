package com.alexproject.testapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.alexproject.domain.models.League
import com.alexproject.testapplication.R
import com.alexproject.testapplication.contracts.LeagueClickListener
import com.alexproject.testapplication.databinding.LeagueItemLayoutBinding
import com.squareup.picasso.Picasso

class LeagueChangeListAdapter(
    leagues: List<League>,
    private val clickListener: LeagueClickListener,
    private val favoriteLeague: League
) : RecyclerView.Adapter<LeagueChangeListAdapter.LeagueHolder>() {

    private val leagueList = mutableListOf<League>()

    init {
        leagueList.add(favoriteLeague)
        leagueList.addAll(leagues)
        leagueList.removeAt(leagueList.lastIndexOf(favoriteLeague))
    }

    inner class LeagueHolder(private val binding: LeagueItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(league: League) {
            if (league.id == favoriteLeague.id)
                binding.itemLeague.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.favoriteLeagueBackground
                    )
                )
            else
                binding.itemLeague.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.transparent
                    )
                )
            binding.leagueName.text = league.name
            Picasso.get().load(league.logo).into(binding.leagueLabel)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueHolder {
        val binding =
            LeagueItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LeagueHolder(binding)
    }

    override fun onBindViewHolder(holder: LeagueHolder, position: Int) {
        holder.bind(leagueList[position])
        holder.itemView.setOnClickListener {
            clickListener.itemClicked(leagueList[position])
        }
    }

    override fun getItemCount(): Int {
        return leagueList.size
    }
}

