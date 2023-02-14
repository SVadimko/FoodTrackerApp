package com.vadimko.barcodesearch_domain.di

import android.content.Context
import com.vadimko.barcodesearch_domain.repository.BarcodeSearchRepository
import com.vadimko.barcodesearch_domain.use_case.BarcodeSearch
import com.vadimko.barcodesearch_domain.use_case.GetListsFromResult
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object BarcodeDomainModule {
    @ViewModelScoped
    @Provides
    fun provideBarcodeUseCases(
        repository: BarcodeSearchRepository,
    ): BarcodeSearch {
        return BarcodeSearch(repository)
    }

    @ViewModelScoped
    @Provides
    fun providesGetListFromResult(@ApplicationContext appContext: Context): GetListsFromResult {
        return GetListsFromResult(appContext)
    }
}