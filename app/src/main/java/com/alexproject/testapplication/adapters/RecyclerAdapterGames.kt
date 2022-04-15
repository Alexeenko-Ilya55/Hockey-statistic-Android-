package com.alexproject.testapplication.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.alexproject.domain.models.Games
import com.alexproject.domain.models.Response
import com.alexproject.domain.useCases.FavoritesUseCase
import com.alexproject.testapplication.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecyclerAdapterGames(
    private val games: Games,
    private val coroutineScope: LifecycleCoroutineScope,
    private val navController: NavController,
    private val favoritesUseCase: FavoritesUseCase
) : RecyclerView.Adapter<RecyclerAdapterGames.RecyclerHolder>() {

    inner class RecyclerHolder(item: View) : RecyclerView.ViewHolder(item) {
        val homeTeamName = item.findViewById<TextView>(R.id.homeTeamName)!!
        val awayTeamName = item.findViewById<TextView>(R.id.awayTeamName)!!
        val homeTeamScore = item.findViewById<TextView>(R.id.homeTeamScore)!!
        val awayTeamScore = item.findViewById<TextView>(R.id.awayTeamScore)!!
        val timeStartGame = item.findViewById<TextView>(R.id.timeStartGame)!!
        val homeTeamEmblem = item.findViewById<ImageView>(R.id.homeTeamEmblem)!!
        val awayTeamEmblem = item.findViewById<ImageView>(R.id.awayTeamEmblem)!!
        val buttonFavorites = item.findViewById<ImageButton>(R.id.buttonFavorites)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.game_item_layout, parent, false)
        return RecyclerHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        holder.apply {
            val game = games.response[position]
            fillDataInItem(holder, game)
            buttonFavorites.setOnClickListener {
                coroutineScope.launch(Dispatchers.IO) {
                    if (game.bookmarkEnable)
                        favoritesUseCase.deleteFromFavoritesFavorites(game)
                    else
                        favoritesUseCase.addToFavorites(game)
                }
                game.bookmarkEnable = !game.bookmarkEnable
                notifyDataSetChanged()
            }
            itemView.setOnClickListener {
                navController.navigate(R.id.fragmentGame)
            }
        }

    }


    private fun fillDataInItem(holder: RecyclerHolder, game: Response) {
        holder.apply {
            homeTeamName.text = game.teams.home.name
            awayTeamName.text = game.teams.away.name

            Picasso.get().load(game.teams.home.logo).into(homeTeamEmblem)
            Picasso.get().load(game.teams.away.logo).into(awayTeamEmblem)

            if (game.status == "NS") {
                homeTeamScore.isVisible = false
                awayTeamScore.isVisible = false
                timeStartGame.isVisible = true
                timeStartGame.text = game.time
            } else {
                timeStartGame.isVisible = false
                homeTeamScore.isVisible = true
                awayTeamScore.isVisible = true
                homeTeamScore.text = game.scores.home.toString()
                awayTeamScore.text = game.scores.away.toString()
            }

            if (game.bookmarkEnable)
                buttonFavorites.setImageResource(R.drawable.favorites_enable)
            else {
                buttonFavorites.setImageResource(R.drawable.nav_menu_favorites)
            }
        }
    }

    override fun getItemCount(): Int {
        return games.response.size
    }
}

