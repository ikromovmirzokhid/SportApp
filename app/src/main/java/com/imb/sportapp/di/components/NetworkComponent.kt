package com.imb.sportapp.di.components

import android.content.Context
import com.imb.sportapp.network.LeagueApi
import com.imb.sportapp.network.TeamApi
import com.imb.sportapp.repos.LeagueRepository
import com.imb.sportapp.repos.LeagueRepositoryImpl
import com.imb.sportapp.repos.TeamRepository
import com.imb.sportapp.repos.TeamRepositoryImp
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Component(modules = [NetworkModule::class])
@Singleton
interface NetworkComponent {

    fun leagueRepository(): LeagueRepository

    fun teamRepository(): TeamRepository

    @Component.Factory
    interface Factory {
        fun buildNetworkComponent(@BindsInstance context: Context): NetworkComponent
    }
}

@Module
object NetworkModule {

    const val BASE_URL = "https://www.thesportsdb.com/api/v1/json/"

    @JvmStatic
    @Singleton
    @Provides
    fun provideRetrofit(context: Context): Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
//            .client(
//                OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS)
//                    .connectTimeout(60, TimeUnit.SECONDS)
//                    .addInterceptor(ChuckInterceptor(context)).build()
//            )
            .build()

    @JvmStatic
    @Singleton
    @Provides
    fun provideLeagueApi(retrofit: Retrofit): LeagueApi = retrofit.create(LeagueApi::class.java)


    @JvmStatic
    @Singleton
    @Provides
    fun provideLeagueRepository(api: LeagueApi): LeagueRepository = LeagueRepositoryImpl(api)

    @JvmStatic
    @Singleton
    @Provides
    fun provideTeamApi(retrofit: Retrofit): TeamApi = retrofit.create(TeamApi::class.java)

    @JvmStatic
    @Singleton
    @Provides
    fun provideTeamRepository(api: TeamApi): TeamRepository = TeamRepositoryImp(api)

}


