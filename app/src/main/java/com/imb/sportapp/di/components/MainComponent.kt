package com.imb.sportapp.di.components

import androidx.lifecycle.ViewModel
import com.imb.sportapp.DI
import com.imb.sportapp.di.scopes.ScreenScope
import com.imb.sportapp.repos.LeagueRepository
import com.imb.sportapp.repos.TeamRepository
import com.imb.sportapp.viewmodel.LeaguesViewModel
import com.imb.sportapp.viewmodel.TeamsViewModel
import com.imb.sportapp.viewmodel.ViewModelKey
import com.imb.sportapp.viewmodel.ViewModelProvider
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@Component(modules = [MainModule::class])
@ScreenScope
interface MainComponent {

    fun viewModelProvider(): ViewModelProvider

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun leagueRepository(repository: LeagueRepository): Builder

        @BindsInstance
        fun teamRepository(repository: TeamRepository): Builder

        fun build(): MainComponent
    }

    companion object {

        fun create() =
            DaggerMainComponent.builder().leagueRepository(DI.networkComponent.leagueRepository())
                .teamRepository(DI.networkComponent.teamRepository())
                .build()
    }
}

@Module
abstract class MainModule {

    @Binds
    @IntoMap
    @ViewModelKey(LeaguesViewModel::class)
    @ScreenScope
    abstract fun leagueViewModel(viewModel: LeaguesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TeamsViewModel::class)
    @ScreenScope
    abstract fun teamsViewModel(viewModel: TeamsViewModel): ViewModel

}