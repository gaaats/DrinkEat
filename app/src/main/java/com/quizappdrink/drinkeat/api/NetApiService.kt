package com.quizappdrink.drinkeat.api

import com.quizappdrink.drinkeat.BuildConfig
import okhttp3.Interceptor
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetApiService {

    companion object {
        const val BASE_URL = "https://api.api-ninjas.com/v1/"
        const val API_KEY = BuildConfig.API_KEY
        const val TEXT_QUERRY = "rome"
    }


    @GET("trivia")
    suspend fun getQuestion(
        @Query("category") category: String = "fooddrink",
        ): Response<QuizResponse>

}

class MyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("X-Api-Key", NetApiService.API_KEY)
            .build()
        return chain.proceed(request)
    }
}