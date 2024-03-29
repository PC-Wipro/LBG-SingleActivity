package com.lbg.project.lbgTest.viewModel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lbg.project.R
import com.lbg.project.catsMock.MockFavouriteCatsResponse
import com.lbg.project.catsMock.MocksCatsDataModel
import com.lbg.project.catsMock.toResponseApiCats
import com.lbg.project.catsMock.toResponseApiFavCats
import com.lbg.project.catsMock.toResponseCats
import com.lbg.project.catsMock.toResponseFavCats
import com.lbg.project.data.database.LBGDatabase
import com.lbg.project.data.models.catData.CatResponse
import com.lbg.project.data.models.catData.FavouriteCatsItem
import com.lbg.project.data.network.NetworkResult
import com.lbg.project.data.repositories.CatsRepositoryImpl
import com.lbg.project.data.services.CatsService
import com.lbg.project.data.services.cats.CatApiServiceHelperImpl
import com.lbg.project.data.services.cats.CatsDatabaseHelperImpl
import com.lbg.project.domain.repositories.CatsRepository
import com.lbg.project.domain.usecase.cats.GetCatsUseCase
import com.lbg.project.domain.usecase.cats.GetCatsUseCaseImpl
import com.lbg.project.domain.usecase.cats.GetFavCatsUseCase
import com.lbg.project.domain.usecase.cats.GetFavCatsUseCaseImpl
import com.lbg.project.presentation.ui.features.cats.viewModel.CatsViewModel
import com.lbg.project.utils.Constants
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import kotlin.test.junit.JUnitAsserter.assertEquals

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CatsViewModelTest {
    private lateinit var mCatsRepo: CatsRepository
    private lateinit var mCatUseCase: GetCatsUseCase
    private lateinit var mFavCatUseCase: GetFavCatsUseCase
    private val application: Application = mock()
    private lateinit var mViewModel: CatsViewModel


    @get:Rule
    val testInstantTaskExecutorRules: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var catService: CatsService

    private val testDispatcher = StandardTestDispatcher()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        val databaseReference = mock(LBGDatabase::class.java)
        val apiHelper = CatApiServiceHelperImpl(catService)
        val dbHelper = CatsDatabaseHelperImpl(databaseReference)
        mCatsRepo = CatsRepositoryImpl(apiHelper, dbHelper)
        Dispatchers.setMain(testDispatcher)
        mCatUseCase = GetCatsUseCaseImpl(mCatsRepo)
        mFavCatUseCase = GetFavCatsUseCaseImpl(mCatsRepo)

        mViewModel = CatsViewModel(mCatUseCase, mFavCatUseCase)
    }

    @Test
    fun testGetEmptyData() = runTest(UnconfinedTestDispatcher()) {
        val expectedRepositories = Response.success(listOf<CatResponse>())
        // Mock the API response
        `when`(catService.fetchCatsImages(0)).thenReturn(expectedRepositories)
        // Call the method under test
        val result = catService.fetchCatsImages(0)
        // Verify that the API method is called with the correct username
        verify(catService).fetchCatsImages(0)
        // Verify that the result matches the expected repositories
        assert(result == expectedRepositories)
    }

    @Test
    fun testGetCatsApiData() = runTest(UnconfinedTestDispatcher()) {
        Dispatchers.setMain(Dispatchers.Unconfined)
        val mockCatsData = MocksCatsDataModel()
        val response = toResponseApiCats(mockCatsData)
        val verifyData = toResponseCats(mockCatsData)
        `when`(catService.fetchCatsImages(10)).thenReturn(response)// Mock the API response
        verify(catService).fetchCatsImages(10)
        mViewModel.getCatsData()
        testDispatcher.scheduler.advanceUntilIdle() // Let the coroutine complete and changes propagate
        val result = mViewModel.state.value.cats
        assertEquals(application.getString(R.string.both_are_not_equal), result, verifyData)
    }

    @Test
    fun testGetFavEmptyData() = runTest(UnconfinedTestDispatcher()) {
        val expectedRepositories = Response.success(listOf<FavouriteCatsItem>())
        // Mock the API response
        `when`(catService.fetchFavouriteCats("0")).thenReturn(expectedRepositories)
        // Call the method under test
        val result = catService.fetchFavouriteCats("0")
        // Verify that the API method is called with the correct username
        verify(catService).fetchFavouriteCats("0")
        // Verify that the result matches the expected repositories
        assert(result == expectedRepositories)
    }


    @Test
    fun testFetchFavouriteCatsSuccessState() = runTest(UnconfinedTestDispatcher()) {
        Dispatchers.setMain(Dispatchers.Unconfined)
        val mockCatsData = MockFavouriteCatsResponse()
        val apiResponse = toResponseApiFavCats(mockCatsData)
        val verifyData = toResponseFavCats(mockCatsData)

        whenever(catService.fetchFavouriteCats(Constants.SUB_ID)).thenReturn(apiResponse)

        // Act
        val result = mCatsRepo.fetchTestFavouriteCats(Constants.SUB_ID).toList()

        // Assert
        assert(result[1] is NetworkResult.Success)
        assertEquals(
            application.getString(R.string.both_are_not_equal),
            result[1].data?.size,
            verifyData.data?.size
        )
        assertEquals(
            application.getString(R.string.both_are_not_equal),
            result[1].data?.get(0)?.image?.url,
            verifyData.data?.get(0)?.url
        )
    }

    @Test
    fun testFetchFavouriteCatsErrorState() = runTest(UnconfinedTestDispatcher()) {

        // Define a sample error response for the service
        val errorResponse = Response.error<List<FavouriteCatsItem>>(
            400, "Error message".toResponseBody(
                "application/json".toMediaType()
            )
        )
        // Set up the mock to return the error response
        //`when`(catService.fetchFavouriteCats(TestTags.SUB_ID)).thenReturn(errorResponse)
        val result = mFavCatUseCase.execute().toList()
        assert(result[1] is NetworkResult.Error)
    }

    @Test
    fun testFetchFavouriteCatsException() = runTest(UnconfinedTestDispatcher()) {

        // Set up the mock to throw an exception

        val result = mFavCatUseCase.execute().toList()
        assert(result[1] is NetworkResult.Error)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
    }


}