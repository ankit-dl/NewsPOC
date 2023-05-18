package com.robosoft.newspoc.di


import com.robosoft.newspoc.Const.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



    fun provideApiService(retrofit: Retrofit) = retrofit.create(NewsAPI::class.java)


    fun provideRetrofit(client: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    fun provideHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }



val appModule = module {
    single { provideHttpClient() }
    single { provideRetrofit(get()) }
    single { provideApiService(get()) }
}
