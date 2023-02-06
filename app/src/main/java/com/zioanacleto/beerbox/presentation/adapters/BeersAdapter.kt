package com.zioanacleto.beerbox.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.zioanacleto.basecomponents.loadImage
import com.zioanacleto.beerbox.databinding.LayoutBeerModelBinding
import com.zioanacleto.beerbox.databinding.LayoutShimmerModelBinding
import com.zioanacleto.beerbox.presentation.interfaces.MainFlowContoller
import com.zioanacleto.models.responses.entities.BeerModel
import com.zioanacleto.models.responses.entities.RowWrapper

class BeersAdapter(
    private val dataset: ArrayList<RowWrapper<*>>,
    private val clickListener: MainFlowContoller?
): Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(viewType){
            BEER_TYPE -> BeerViewHolder(
                LayoutBeerModelBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> ShimmerViewHolder(
                LayoutShimmerModelBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return dataset[position].viewType
    }

    override fun getItemCount(): Int = dataset.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataset[position].data
        if(item is BeerModel){
            if(holder is BeerViewHolder)
                holder.bind(item)
        }
    }

    fun updateDataset(list: List<RowWrapper<*>>){
        dataset.addAll(list)
        notifyDataSetChanged()
    }

    inner class BeerViewHolder(
        private val viewBinding: LayoutBeerModelBinding
    ): ViewHolder(viewBinding.root){

        fun bind(beer: BeerModel){
            with(viewBinding){
                beerImageView.loadImage(beer.imageThumbnail)
                beerNameTextView.text = beer.name
                beerTagTextView.text = beer.tagline
                beerDescriptionTextView.text = beer.description

                root.setOnClickListener {
                    clickListener?.onBeerClicked(beer)
                }
            }
        }
    }

    inner class ShimmerViewHolder(
        viewBinding: LayoutShimmerModelBinding
    ): ViewHolder(viewBinding.root){

        init{
            with(viewBinding){
                imageViewShimmer.startShimmer()
                titleShimmer.startShimmer()
                tagShimmer.startShimmer()
                descriptionShimmer.startShimmer()
            }
        }
    }

    companion object{
        const val SHIMMER_TYPE = 1
        const val BEER_TYPE = 2
    }

}