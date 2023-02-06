package com.zioanacleto.beerbox.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zioanacleto.basecomponents.BaseViewBindingFragment
import com.zioanacleto.basecomponents.hide
import com.zioanacleto.basecomponents.hideKeyboard
import com.zioanacleto.basecomponents.show
import com.zioanacleto.beerbox.data.MainViewModel
import com.zioanacleto.beerbox.databinding.FragmentMainBinding
import com.zioanacleto.beerbox.presentation.adapters.BeersAdapter
import com.zioanacleto.beerbox.presentation.adapters.BeersAdapter.Companion.BEER_TYPE
import com.zioanacleto.beerbox.presentation.adapters.BeersAdapter.Companion.SHIMMER_TYPE
import com.zioanacleto.beerbox.presentation.interfaces.MainFlowContoller
import com.zioanacleto.beerbox.presentation.listeners.PaginationScrollListener
import com.zioanacleto.models.responses.entities.BeerModel
import com.zioanacleto.models.responses.entities.GetBeersResponseModel
import com.zioanacleto.models.responses.entities.RowWrapper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment: BaseViewBindingFragment(), MainFlowContoller {

    override val viewModel: MainViewModel by viewModels()
    private val viewBinding: FragmentMainBinding
        get() = requireViewBinding()

    //to manage correctly shimmer we must use same adapter instance and update it
    private var adapter: BeersAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //calling api services when creating fragment
        viewModel.getAllBeers()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater, container, false)
        initViewBinding(binding)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        observeData()
    }

    private fun setupView() {
        with(viewBinding) {
            mainSearchView.setOnQueryTextListener(object : OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    activity?.hideKeyboard()
                    if (!query.isNullOrEmpty()) {
                        viewModel.setSearchMode(query)
                        viewModel.searchBeers(query)
                    }
                    else {
                        viewModel.setSearchMode(null)
                        viewModel.getAllBeers()
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if(newText.isNullOrEmpty()) {
                        viewModel.setSearchMode(null)
                        viewModel.getAllBeers()
                    }
                    return true
                }
            })
            mainSearchView.setOnCloseListener {
                activity?.hideKeyboard()
                activity?.currentFocus?.clearFocus()
                false
            }
        }
    }

    override fun onBeerClicked(beer: BeerModel) {
        val bottomSheet = BeerDetailBottomSheetFragment.newInstance(beer)
        bottomSheet.show(childFragmentManager, BeerDetailBottomSheetFragment.TAG)
    }

    private fun observeData(){
        viewModel.getBeersLiveData.observe(viewLifecycleOwner){
            when{
                it.isLoading() -> {
                    showShimmerLayout()
                }
                it.isSuccess() ->{
                    setupMainAdapter(it.data)
                }
                else ->{
                    if(viewModel.isSearchMode())
                        showEmptyResponseView()
                    else
                        showErrorView()
                }
            }
        }
        viewModel.getBeersForPaginationLiveData.observe(viewLifecycleOwner){
            when{
                it.isSuccess() ->{
                    appendResult(it.data)
                }
                else ->{
                    //nothing to be done
                }
            }
        }
    }

    private fun setupMainAdapter(response: GetBeersResponseModel?){
        with(viewBinding){
            errorViewTextView.hide()
            noResultsViewTextView.hide()
            beersRecyclerView.show()

            val list = arrayListOf<RowWrapper<*>>()
            response?.beers?.forEach{beer ->
                list.add(
                    RowWrapper(BEER_TYPE, beer)
                )
            }

            adapter = BeersAdapter(list, this@MainFragment)
            beersRecyclerView.setCustomAdapter(adapter, isShimmer = false)
        }
    }

    private fun appendResult(response: GetBeersResponseModel?){
        val list = arrayListOf<RowWrapper<*>>()
        response?.beers?.forEach{beer ->
            list.add(
                RowWrapper(BEER_TYPE, beer)
            )
        }

        adapter?.updateDataset(list)
    }

    private fun showErrorView(){
        with(viewBinding){
            errorViewTextView.show()
            beersRecyclerView.hide()
            noResultsViewTextView.hide()
        }
    }

    private fun showShimmerLayout(){
        with(viewBinding){
            errorViewTextView.hide()
            noResultsViewTextView.hide()
            beersRecyclerView.show()

            val list = arrayListOf<RowWrapper<*>>(
                RowWrapper(SHIMMER_TYPE, null),
                RowWrapper(SHIMMER_TYPE, null),
                RowWrapper(SHIMMER_TYPE, null),
                RowWrapper(SHIMMER_TYPE, null),
                RowWrapper(SHIMMER_TYPE, null)
            )
            adapter = BeersAdapter(list, this@MainFragment)
            beersRecyclerView.setCustomAdapter(adapter, isShimmer = true)
        }
    }

    private fun showEmptyResponseView(){
        with(viewBinding){
            errorViewTextView.hide()
            beersRecyclerView.hide()
            noResultsViewTextView.show()
        }
    }

    private fun RecyclerView.setCustomAdapter(adapter: BeersAdapter?, isShimmer: Boolean){
        this.adapter = adapter
        this.layoutManager = LinearLayoutManager(context)
        overScrollMode = View.OVER_SCROLL_NEVER

        if(!isShimmer){
            addOnScrollListener(
                PaginationScrollListener(
                    adapter,
                    layoutManager as LinearLayoutManager
                ){ pageNumber ->
                    if(viewModel.isSearchMode())
                        viewModel.searchBeersPaginated(pageNumber)
                    else
                        viewModel.getAllBeersPaginated(pageNumber)
                }
            )
        }
    }
}