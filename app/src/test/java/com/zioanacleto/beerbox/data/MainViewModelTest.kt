package com.zioanacleto.beerbox.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.zioanacleto.beerbox.MainCoroutineRule
import com.zioanacleto.beerbox.TestDispatcherProvider
import com.zioanacleto.models.mappers.DataMapper
import com.zioanacleto.models.responses.dto.GetBeersResponseDTO
import com.zioanacleto.models.responses.entities.GetBeersResponseModel
import com.zioanacleto.network.repositories.GetBeersRepository
import com.zioanacleto.network.resources.Resource
import com.zioanacleto.network.resources.Status
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private val dispatcherProvider = TestDispatcherProvider(coroutineRule.dispatcher)
    private lateinit var repository: GetBeersRepository
    private lateinit var dataMapper: DataMapper<GetBeersResponseDTO, GetBeersResponseModel>

    @Rule
    @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = mockk()
        dataMapper = mockk()
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun test_setSearchMode() {
        val sut = createSut()
        sut.setSearchMode("test")
        assert(sut.isSearchMode())

        sut.setSearchMode(null)
        assert(!sut.isSearchMode())
    }

    @Test
    fun test_getAllBeers_dataIsNull_resourceIsError() = runTest{
        val sut = createSut()
        val response = Resource.success(null)

        coEvery { repository.getBeers(any()) } returns response

        sut.getAllBeers()

        coVerify { repository.getBeers(any()) }

        assert(sut.getBeersLiveData.value?.isError() == true)
    }

    @Test
    fun test_getAllBeers_dataIsNotNullAndEmpty_resourceIsError() = runTest{
        val sut = createSut()
        val responseData = mockResponse()
        val response = Resource.success(responseData)

        coEvery { repository.getBeers(any()) } returns response

        sut.getAllBeers()

        coVerify { repository.getBeers(any()) }

        assert(sut.getBeersLiveData.value?.isError() == true)
    }

    @Test
    fun test_getAllBeers_dataIsNotNullAndNotEmpty_resourceIsSuccess() = runTest(coroutineRule.dispatcher){
        val sut = createSut()
        val responseDataMapped = mockResponseMapped()
        val responseData = mockResponse(false)
        val response = Resource.success(responseData)

        coEvery { repository.getBeers(any()) } returns response
        every { dataMapper.map(responseData) } returns responseDataMapped

        sut.getAllBeers()

        coVerify { repository.getBeers(any()) }

        assert(sut.getBeersLiveData.value?.isSuccess() == true)
    }

    @Test
    fun test_getAllBeers_exceptionCatched_resourceIsError() = runTest(coroutineRule.dispatcher){
        val sut = createSut()
        val responseData = mockResponse(false)
        val response = Resource.success(responseData)

        coEvery { repository.getBeers(any()) } returns response

        sut.getAllBeers()

        coVerify { repository.getBeers(any()) }

        assert(sut.getBeersLiveData.value?.isError() == true)
    }

    @Test
    fun test_getAllBeersPaginated_dataIsNull_resourceIsError() = runTest {
        val sut = createSut()
        val response = Resource.success(null)

        coEvery { repository.getBeers(any()) } returns response

        sut.getAllBeersPaginated(1)

        coVerify { repository.getBeers(any()) }

        assert(sut.getBeersForPaginationLiveData.value?.isError() == true)
    }

    @Test
    fun test_searchBeers_dataIsNull_resourceIsError() = runTest{
        val sut = createSut()
        val response = Resource.success(null)

        coEvery { repository.searchBeers(any(), any()) } returns response

        sut.searchBeers("")

        coVerify { repository.searchBeers(any(), any()) }

        assert(sut.getBeersLiveData.value?.isError() == true)
    }

    @Test
    fun test_searchBeers_dataIsNotNullAndEmpty_resourceIsError() = runTest{
        val sut = createSut()
        val responseData = mockResponse()
        val response = Resource.success(responseData)

        coEvery { repository.searchBeers(any(), any()) } returns response

        sut.searchBeers("")

        coVerify { repository.searchBeers(any(), any()) }

        assert(sut.getBeersLiveData.value?.isError() == true)
    }

    @Test
    fun test_searchBeers_dataIsNotNullAndNotEmpty_resourceIsSuccess() = runTest(coroutineRule.dispatcher){
        val sut = createSut()
        val responseDataMapped = mockResponseMapped()
        val responseData = mockResponse(false)
        val response = Resource.success(responseData)

        coEvery { repository.searchBeers(any(), any()) } returns response
        every { dataMapper.map(responseData) } returns responseDataMapped

        sut.searchBeers("")

        coVerify { repository.searchBeers(any(), any()) }

        assert(sut.getBeersLiveData.value?.isSuccess() == true)
    }

    @Test
    fun test_searchBeers_exceptionCatched_resourceIsError() = runTest(coroutineRule.dispatcher){
        val sut = createSut()
        val responseData = mockResponse(false)
        val response = Resource.success(responseData)

        coEvery { repository.searchBeers(any(), any()) } returns response

        sut.searchBeers("")

        coVerify { repository.searchBeers(any(), any()) }

        assert(sut.getBeersLiveData.value?.isError() == true)
    }

    @Test
    fun test_searchBeersPaginated_dataIsNull_resourceIsError() = runTest {
        val sut = createSut()
        val response = Resource.success(null)

        coEvery { repository.searchBeers(any(), any()) } returns response

        sut.searchBeersPaginated(1)

        coVerify { repository.searchBeers(any(), any()) }

        assert(sut.getBeersForPaginationLiveData.value?.isError() == true)
    }

    private fun createSut() = MainViewModel(
        dispatcherProvider,
        repository,
        dataMapper
    )

    private fun mockResponse(isEmpty: Boolean = true) = GetBeersResponseDTO().apply {
        if (!isEmpty)
            add(
                GetBeersResponseDTO.GetBeersResponseItemDTO(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
                )
            )
    }

    private fun mockResponseMapped() = GetBeersResponseModel(
        arrayListOf()
    )
}