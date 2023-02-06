package com.zioanacleto.beerbox.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zioanacleto.basecomponents.BaseViewModel
import com.zioanacleto.basecomponents.dispatcher.DispatcherProvider
import com.zioanacleto.basecomponents.letOrRun
import com.zioanacleto.models.mappers.DataMapper
import com.zioanacleto.models.responses.dto.GetBeersResponseDTO
import com.zioanacleto.models.responses.entities.GetBeersResponseModel
import com.zioanacleto.network.repositories.GetBeersRepository
import com.zioanacleto.network.resources.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

/**
 *  ViewModel
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val repository: GetBeersRepository,
    private val dataMapper: DataMapper<GetBeersResponseDTO, GetBeersResponseModel>
): BaseViewModel(dispatcherProvider) {

    private val _getBeersLiveData: MutableLiveData<Resource<GetBeersResponseModel>> = MutableLiveData()
    val getBeersLiveData: LiveData<Resource<GetBeersResponseModel>> = _getBeersLiveData

    private val _getBeersForPaginationLiveData: MutableLiveData<Resource<GetBeersResponseModel>> = MutableLiveData()
    val getBeersForPaginationLiveData: LiveData<Resource<GetBeersResponseModel>> = _getBeersForPaginationLiveData

    private var searchedString: String? = null

    fun setSearchMode(query: String?){
        searchedString = query
    }

    fun isSearchMode() = searchedString != null

    fun getAllBeers(){
        cancelChildren()
        _getBeersLiveData.postValue(Resource.loading())

        getBeers(_getBeersLiveData, null)
    }

    fun getAllBeersPaginated(pageNumber: Int){
        cancelChildren()

        getBeers(_getBeersForPaginationLiveData, pageNumber)
    }

    fun searchBeers(query: String?){
        cancelChildren()
        _getBeersLiveData.postValue(Resource.loading())

        searchBeers(_getBeersLiveData, query, null)
    }

    fun searchBeersPaginated(pageNumber: Int){
        cancelChildren()

        searchBeers(_getBeersForPaginationLiveData, searchedString, pageNumber)
    }

    private fun getBeers(liveData: MutableLiveData<Resource<GetBeersResponseModel>>, pageNumber: Int?){
        launchSupervisored {
            try{
                val response = repository.getBeers(pageNumber ?: 1)
                Timber.d("Response: $response")

                letOrRun(response.data, { beers ->
                    postSupervisored {
                        if(beers.isNotEmpty())
                            liveData.postValue(Resource.success(dataMapper.map(beers)))
                        else
                            liveData.postValue(Resource.error(Exception()))
                    }
                }, {
                    postSupervisored {
                        liveData.postValue(Resource.error(Exception()))
                    }
                })
            }
            catch (exception: Exception){
                Timber.d("Error from server: $exception")
                postSupervisored {
                    liveData.postValue(Resource.error(Exception()))
                }
            }
        }
    }

    private fun searchBeers(
        liveData: MutableLiveData<Resource<GetBeersResponseModel>>,
        query: String?,
        pageNumber: Int?
    ){
        launchSupervisored {
            try{
                val response = repository.searchBeers(query, pageNumber ?: 1)
                Timber.d("Response: $response")

                letOrRun(response.data, {
                    postSupervisored {
                        if(it.isNotEmpty())
                            liveData.postValue(Resource.success(dataMapper.map(it)))
                        else
                            liveData.postValue(Resource.error(Exception()))
                    }
                }, {
                    postSupervisored {
                        liveData.postValue(Resource.error(Exception()))
                    }
                })
            }
            catch (exception: Exception){
                Timber.d("Error from server: $exception")
                postSupervisored {
                    liveData.postValue(Resource.error(Exception()))
                }
            }
        }
    }
}