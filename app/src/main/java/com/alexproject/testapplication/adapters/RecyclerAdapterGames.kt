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
import com.alexproject.domain.models.Game
import com.alexproject.domain.useCases.AddGameToFavoritesUseCase
import com.alexproject.domain.useCases.DeleteGameFromFavoritesUseCase
import com.alexproject.testapplication.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecyclerAdapterGames(
    private val games: List<Game>,
    private val coroutineScope: LifecycleCoroutineScope,
    private val navController: NavController,
    private val addGameToFavoritesUseCase: AddGameToFavoritesUseCase,
    private val deleteGameFromFavoritesUseCase: DeleteGameFromFavoritesUseCase
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
            val game = games[position]
            fillDataInItem(holder, game)
            buttonFavorites.setOnClickListener {
                game.isFavorite = !game.isFavorite
                coroutineScope.launch(Dispatchers.IO) {
                    if (game.isFavorite)
                        addGameToFavoritesUseCase.addGameToFavorites(game.id)
                    else
                        deleteGameFromFavoritesUseCase.deleteGameFromFavorites(game.id)
                }
                notifyDataSetChanged()
            }
            itemView.setOnClickListener {
                navController.navigate(R.id.fragmentGame)
            }
        }

    }


    private fun fillDataInItem(holder: RecyclerHolder, game: Game) {
        holder.apply {
            homeTeamName.text = game.homeTeam.name
            awayTeamName.text = game.awayTeam.name

            Picasso.get().load(game.homeTeam.logo).into(homeTeamEmblem)
            Picasso.get().load(game.awayTeam.logo).into(awayTeamEmblem)

            if (game.status == "NS") {
                homeTeamScore.isVisible = false
                awayTeamScore.isVisible = false
                timeStartGame.isVisible = true
                timeStartGame.text = game.time
            } else {
                timeStartGame.isVisible = false
                homeTeamScore.isVisible = true
                awayTeamScore.isVisible = true
                homeTeamScore.text = game.homeScores.toString()
                awayTeamScore.text = game.awayScores.toString()
            }

            if (game.isFavorite)
                buttonFavorites.setImageResource(R.drawable.favorites_enable)
            else {
                buttonFavorites.setImageResource(R.drawable.nav_menu_favorites)
            }
        }
    }

    override fun getItemCount(): Int {
        return games.size
    }
}

