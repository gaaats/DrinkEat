package com.quizappdrink.drinkeat.api


import com.google.gson.annotations.SerializedName

data class QuizResponseItem(
    @SerializedName("answer")
    val answer: String?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("question")
    val question: String?
)