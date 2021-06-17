package com.android_app_project.app.data.remote

import com.android_app_project.app.BuildConfig
import com.android_app_project.app.data.models.LoginResult
import com.android_app_project.app.data.models.PingResult
import com.android_app_project.app.data.models.RestResult
import com.android_app_project.app.data.models.UserCreationResult
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface SampleRemoteDataSource {

    @GET("info/ping?content-type=application/json")
    @Headers("Content-type: application/json")
    suspend fun ping(): PingResult

    @GET("info/rest?content-type=application/json")
    @Headers("Content-type: application/json")
    suspend fun restInfo(): RestResult

    //@POST("")
    //@Headers("")

    @GET("/login")
    suspend fun readUsers(@Query("login") login: String, @Query("password")password: String): Int

    @GET("/create_user")
    suspend fun createUser(
            @Query("login") username: String,
            @Query("password")password: String,
            @Query("email")email: String,
            @Query("nom")firstname: String,
            @Query("prenom")lastname: String
            ): Int

    @GET("/collecte_data")
    suspend fun collectData(@Query("id_user") id_user: Int): Array<String>

    @GET("/send_data")
    suspend fun sendData(@Query("id_user") id_user: Int,
                         @Query("luminosite")luminosite: String,
                         @Query("batterie")batterie: String,
                         @Query("pression")pression: String,
                         @Query("temperature")temperature: String,
                         @Query("gps")gps: String,): Int



    // Équivalenent en kotlin d'élément « static »
    companion object {
        /**
         * Création d'un singleton pour la simplicité, mais normalement nous utilisons plutôt un
         * injecteur de dépendances.
         */
        val instance = build()

        /**
         * Création de l'objet qui nous permettra de faire les appels d'API
         */
        private fun build(): SampleRemoteDataSource {
            val gson = GsonBuilder().create() // JSON deserializer/serializer

            // Create the OkHttp Instance
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE))
                .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                    val request = chain.request().newBuilder().addHeader("Accept", "application/json").build()
                    chain.proceed(request)
                })
                .build()

            return Retrofit.Builder()
                .baseUrl(BuildConfig.REMOTE_URI) // écrire en dur un lien est une TRÈS MAUVAISE IDÉE !
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(SampleRemoteDataSource::class.java)
        }
    }
}