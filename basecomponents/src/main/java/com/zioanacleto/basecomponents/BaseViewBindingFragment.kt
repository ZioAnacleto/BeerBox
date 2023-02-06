package com.zioanacleto.basecomponents

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 *  Base class to manage [ViewBinding] for a Fragment,
 *  assigning to this class a [BaseViewModel] instance
 */
abstract class BaseViewBindingFragment: Fragment() {

    abstract val viewModel: BaseViewModel
    var _binding: ViewBinding? = null
        private set

    inline fun <reified T: ViewBinding> requireViewBinding(): T{
        if(_binding is T)
            return _binding as T
        else
            throw Exception("Binding not initialized!")
    }

    fun initViewBinding(viewBinding: ViewBinding){
        this._binding = viewBinding
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }
}