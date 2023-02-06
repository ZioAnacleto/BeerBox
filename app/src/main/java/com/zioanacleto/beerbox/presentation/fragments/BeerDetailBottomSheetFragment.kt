package com.zioanacleto.beerbox.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zioanacleto.basecomponents.loadImage
import com.zioanacleto.basecomponents.withArgs
import com.zioanacleto.beerbox.R
import com.zioanacleto.beerbox.databinding.LayoutBeerDetailBottomSheetBinding
import com.zioanacleto.models.responses.entities.BeerModel

class BeerDetailBottomSheetFragment: BottomSheetDialogFragment() {

    private lateinit var viewBinding: LayoutBeerDetailBottomSheetBinding

    private val beerModel: BeerModel? by lazy {
        arguments?.getParcelable(BUNDLE_BEER_MODEL)
    }

    override fun getTheme(): Int = R.style.BeerBoxRoundedCornersDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutBeerDetailBottomSheetBinding.inflate(inflater, container, false)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    private fun setupView(){
        beerModel?.let{
            with(viewBinding){
                beerImageView.loadImage(it.imageThumbnail)
                beerNameTextView.text = it.name
                beerTagTextView.text = it.tagline
                beerDescriptionTextView.text = it.description
            }
        }
    }

    companion object{
        const val BUNDLE_BEER_MODEL = "BUNDLE_BEER_MODEL"
        const val TAG = "BeerDetailBottomSheetFragment"

        fun newInstance(beer: BeerModel?) = BeerDetailBottomSheetFragment().withArgs{
            putParcelable(BUNDLE_BEER_MODEL, beer)
        }
    }
}