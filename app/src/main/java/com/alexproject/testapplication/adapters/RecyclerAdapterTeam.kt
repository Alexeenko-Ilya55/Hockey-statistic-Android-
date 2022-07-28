package com.alexproject.testapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alexproject.domain.models.Team
import com.alexproject.testapplication.R
import com.alexproject.testapplication.contracts.TeamClickListener
import com.squareup.picasso.Picasso

class RecyclerAdapterTeam(
    private val teamList: List<Team>,
    private val clickListener: TeamClickListener
) : RecyclerView.Adapter<RecyclerAdapterTeam.RecyclerHolder>() {

    inner class RecyclerHolder(item: View) : RecyclerView.ViewHolder(item) {
        val teamName = item.findViewById<TextView>(R.id.leagueName)!!
        val teamEmblem = item.findViewById<ImageView>(R.id.leagueLabel)!!
        val buttonFavorites = item.findViewById<ImageButton>(R.id.buttonFavorites)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.team_item_layout, parent, false)
        return RecyclerHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        holder.apply {
            val team = teamList[position]
            fillDataInItem(holder, team)
            buttonFavorites.setOnClickListener {
                team.isFavorite = !team.isFavorite
                clickListener.buttonTeamFavoriteClicked(team.id, team.isFavorite)
            }
            itemView.setOnClickListener {
                clickListener.itemTeamClicked(team.id)
            }
        }
    }

    private fun fillDataInItem(holder: RecyclerHolder, team: Team) {
        holder.apply {
            teamName.text = team.name
            Picasso.get().load(team.logo).into(teamEmblem)
            if (team.isFavorite)
                buttonFavorites.setImageResource(R.drawable.favorites_enable)
            else {
                buttonFavorites.setImageResource(R.drawable.nav_menu_favorites)
            }
        }
    }

    override fun getItemCount(): Int {
        return teamList.size
    }
}