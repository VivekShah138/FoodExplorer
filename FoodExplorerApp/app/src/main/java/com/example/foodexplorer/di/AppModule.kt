package com.example.foodexplorer.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.foodexplorer.data.data_source.local.FoodItemDao
import com.example.foodexplorer.data.data_source.local.FoodItemDatabase
import com.example.foodexplorer.data.data_source.remote.ApiClient
import com.example.foodexplorer.data.data_source.remote.FoodItemsApi
import com.example.foodexplorer.data.repository.FoodItemLocalLocalRepositoryImpl
import com.example.foodexplorer.data.repository.FoodItemsRemoteRepositoryImpl
import com.example.foodexplorer.domain.repository.FoodItemLocalRepository
import com.example.foodexplorer.domain.repository.FoodItemsRemoteRepository
import com.example.foodexplorer.domain.usecases.DoesItemExists
import com.example.foodexplorer.domain.usecases.FoodItemsUseCaseWrapper
import com.example.foodexplorer.domain.usecases.GetAllFavFoodItems
import com.example.foodexplorer.domain.usecases.GetAllFoodItems
import com.example.foodexplorer.domain.usecases.GetFoodItemById
import com.example.foodexplorer.domain.usecases.InsertFoodItem
import com.example.foodexplorer.domain.usecases.InsertFoodItemsFromApi
import com.example.foodexplorer.domain.usecases.InsertFoodItemsFromAssets
import com.example.foodexplorer.domain.usecases.UpdateFavFlag
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFoodItemDatabase(app: Application): FoodItemDatabase {
        return Room.databaseBuilder(
            context = app,
            klass = FoodItemDatabase::class.java,
            name = FoodItemDatabase.DATABASE_NAME
        ).build()
    }


    @Provides
    @Singleton
    fun provideFoodItemDao(db: FoodItemDatabase): FoodItemDao {
        return db.foodItemDao
    }

    @Provides
    @Singleton
    fun provideFoodItemRepository(
        @ApplicationContext context: Context,
        foodItemDao: FoodItemDao
    ): FoodItemLocalRepository {
        return FoodItemLocalLocalRepositoryImpl(context = context,foodItemDao = foodItemDao)
    }

    @Provides
    @Singleton
    fun provideFoodItemsApi(): FoodItemsApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ApiClient.BASE_URL_FOOD_ITEMS)
            .build()
            .create(FoodItemsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFoodItemRemoteRepository(
        foodItemsApi: FoodItemsApi
    ): FoodItemsRemoteRepository {
        return FoodItemsRemoteRepositoryImpl(foodItemsApi = foodItemsApi)
    }

    @Provides
    @Singleton
    fun provideFoodItemsUseCaseWrapper(
        foodItemLocalRepository: FoodItemLocalRepository,
        foodItemsRemoteRepository: FoodItemsRemoteRepository
    ): FoodItemsUseCaseWrapper{
        return FoodItemsUseCaseWrapper(
            doesItemExists = DoesItemExists(foodItemLocalRepository = foodItemLocalRepository),
            getAllFoodItems = GetAllFoodItems(foodItemLocalRepository = foodItemLocalRepository),
            getFoodItemById = GetFoodItemById(foodItemLocalRepository = foodItemLocalRepository),
            getAllFavFoodItems = GetAllFavFoodItems(foodItemLocalRepository = foodItemLocalRepository),
            insertFoodItem = InsertFoodItem(foodItemLocalRepository = foodItemLocalRepository),
            insertFoodItemsFromApi = InsertFoodItemsFromApi(foodItemLocalRepository = foodItemLocalRepository,foodItemsRemoteRepository = foodItemsRemoteRepository),
            insertFoodItemsFromAssets = InsertFoodItemsFromAssets(foodItemLocalRepository = foodItemLocalRepository),
            updateFavFlag = UpdateFavFlag(foodItemLocalRepository = foodItemLocalRepository)
        )
    }
}