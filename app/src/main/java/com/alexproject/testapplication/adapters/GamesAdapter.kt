package com.alexproject.testapplication.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.alexproject.domain.models.Game
import com.alexproject.testapplication.R
import com.alexproject.testapplication.contracts.GameClickListener
import com.alexproject.testapplication.objects.Status
import com.squareup.picasso.Picasso

class GamesAdapter(
    private val games: List<Game>,
    private val clickListener: GameClickListener,
) : RecyclerView.Adapter<GamesAdapter.RecyclerHolder>() {

    lateinit var holder: RecyclerHolder

    inner class RecyclerHolder(item: View) : RecyclerView.ViewHolder(item) {
        val homeTeamName = item.findViewById<TextView>(R.id.homeTeamName)!!
        val awayTeamName = item.findViewById<TextView>(R.id.awayTeamName)!!
        val homeTeamScore = item.findViewById<TextView>(R.id.homeTeamScore)!!
        val awayTeamScore = item.findViewById<TextView>(R.id.awayTeamScore)!!
        val timeStartGame = item.findViewById<TextView>(R.id.timeStartGame)!!
        val homeTeamEmblem = item.findViewById<ImageView>(R.id.homeTeamEmblem)!!
        val awayTeamEmblem = item.findViewById<ImageView>(R.id.awayTeamEmblem)!!
        val buttonFavorites = item.findViewById<ImageButton>(R.id.buttonFavorites)!!
        val periodGame = item.findViewById<TextView>(R.id.periodGame)!!
        val timerGame = item.findViewById<TextView>(R.id.timerGame)!!
        val breakGame = item.findViewById<TextView>(R.id.breakGame)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.game_item_layout, parent, false)
        return RecyclerHolder(view)
    }


    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        val game = games[position]
        fillDataInItem(holder, game)
        holder.buttonFavorites.setOnClickListener {
            game.isFavorite = !game.isFavorite
            clickListener.buttonGameFavoriteClicked(game.id, game.isFavorite)
        }
        holder.itemView.setOnClickListener {
            clickListener.itemGameClicked(game.id)
        }
    }

    private fun fillDataInItem(holder: RecyclerHolder, game: Game) {
        this.holder = holder
        holder.apply {
            homeTeamName.text = game.homeTeam.name
            awayTeamName.text = game.awayTeam.name

            Picasso.get().load(game.homeTeam.logo).into(homeTeamEmblem)
            Picasso.get().load(game.awayTeam.logo).into(awayTeamEmblem)

            when (game.status) {
                Status.GAME_NOT_STARTED.get -> gameNotStarted(game)
                Status.FIRST_PERIOD.get -> game.timer?.let { gameActive(game) }
                Status.SECOND_PERIOD.get -> game.timer?.let { gameActive(game) }
                Status.THIRD_PERIOD.get -> game.timer?.let { gameActive(game) }
                Status.OVER_TIME.get -> game.timer?.let { gameActive(game) }
                Status.BREAK_TIME.get -> gameBreak(game)
                Status.GAME_FINISHED.get -> gameFinished(game)
                Status.AFTER_OVER_TIME.get -> gameFinishedAfterMainTime(game)
                Status.AFTER_PENALTIES.get -> gameFinishedAfterMainTime(game)
            }

            if (game.isFavorite)
                buttonFavorites.setImageResource(R.drawable.favorites_enable)
            else {
                buttonFavorites.setImageResource(R.drawable.nav_menu_favorites)
            }
        }
    }

    private fun gameNotStarted(game: Game) {
        holder.apply {
            homeTeamScore.isVisible = false
            awayTeamScore.isVisible = false
            timeStartGame.isVisible = true
            periodGame.isVisible = false
            timerGame.isVisible = false
            breakGame.isVisible = false

            timeStartGame.text = game.time
        }
    }

    private fun gameBreak(game: Game) {
        holder.apply {
            homeTeamScore.isVisible = true
            awayTeamScore.isVisible = true
            timeStartGame.isVisible = false
            periodGame.isVisible = false
            timerGame.isVisible = false
            breakGame.isVisible = true
            homeTeamScore.text = game.homeScores.toString()
            awayTeamScore.text = game.awayScores.toString()
            awayTeamScore.setTextColor(itemView.context.getColor(R.color.colorLiveScore))
            homeTeamScore.setTextColor(itemView.context.getColor(R.color.colorLiveScore))
            breakGame.text = itemView.context.getString(R.string.breakGAME)
        }
    }

    private fun gameActive(game: Game) {
        holder.apply {
            homeTeamScore.isVisible = true
            awayTeamScore.isVisible = true
            timeStartGame.isVisible = false
            periodGame.isVisible = true
            timerGame.isVisible = true
            breakGame.isVisible = false
            awayTeamScore.setTextColor(itemView.context.getColor(R.color.colorLiveScore))
            homeTeamScore.setTextColor(itemView.context.getColor(R.color.colorLiveScore))
            homeTeamScore.text = game.homeScores.toString()
            awayTeamScore.text = game.awayScores.toString()
            periodGame.text = game.status
            timerGame.text = game.timer
        }
    }

    private fun gameFinished(game: Game) {
        holder.apply {
            homeTeamScore.isVisible = true
            awayTeamScore.isVisible = true
            timeStartGame.isVisible = false
            periodGame.isVisible = false
            timerGame.isVisible = false
            breakGame.isVisible = false
            awayTeamScore.setTextColor(itemView.context.getColor(R.color.black))
            homeTeamScore.setTextColor(itemView.context.getColor(R.color.black))
            awayTeamScore.text = game.awayScores.toString()
            homeTeamScore.text = game.homeScores.toString()
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun gameFinishedAfterMainTime(game: Game) {
        holder.apply {
            homeTeamScore.isVisible = true
            awayTeamScore.isVisible = true
            timeStartGame.isVisible = false
            periodGame.isVisible = true
            timerGame.isVisible = true
            breakGame.isVisible = false
            awayTeamScore.setTextColor(itemView.context.getColor(R.color.black))
            homeTeamScore.setTextColor(itemView.context.getColor(R.color.black))


            homeTeamScore.text = game.homeScores.toString()
            awayTeamScore.text = game.awayScores.toString()
            periodGame.text = itemView.context.getString(R.string.after)
            if (game.status == Status.AFTER_OVER_TIME.get)
                timerGame.text = itemView.context.getString(R.string.overtime)
            else
                timerGame.text = itemView.context.getString(R.string.penalty)
        }
    }

    override fun getItemCount(): Int {
        return games.size
    }
}
