package com.packt.login.di

import android.content.Context
import com.packt.common.domain.LoginRepository
import com.packt.login.data.AuthService
import com.packt.login.data.datasource.local.LoginLocalDataSource
import com.packt.login.data.repository.LoginRepositoryImpl
import com.packt.login.data.datasource.remote.LoginRemoteDataSource
import com.packt.login.domain.use_case.DoLoginUseCase
import com.packt.login.domain.use_case.DoLoginUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginServiceModule {

    @Provides
    @Singleton
    fun provideLoginService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideLoginLocalDataSource(@ApplicationContext context: Context): LoginLocalDataSource {
        return LoginLocalDataSource(context)
    }

    @Provides
    @Singleton
    fun provideLoginRemoteDataSource(loginService: AuthService): LoginRemoteDataSource {
        return LoginRemoteDataSource(loginService)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideLoginRepository(localDataSource: LoginLocalDataSource, remoteDataSource: LoginRemoteDataSource): LoginRepository {
        return LoginRepositoryImpl(localDataSource, remoteDataSource)
    }

    @Provides
    fun provideDoLoginUseCase(loginRepository: LoginRepository): DoLoginUseCase {
        return DoLoginUseCaseImpl(loginRepository)
    }
}

