package com.alexproject.repository.models.statistic

data class Games(
    val lose: Lose,
    val lose_overtime: LoseOvertime,
    val played: Int,
    val win: Win,
    val win_overtime: WinOvertime
)