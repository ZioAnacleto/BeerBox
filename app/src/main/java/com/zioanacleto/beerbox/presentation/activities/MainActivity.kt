package com.zioanacleto.beerbox.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.zioanacleto.beerbox.R
import com.zioanacleto.beerbox.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        if(Timber.treeCount < 1)
            Timber.plant(Timber.DebugTree())

        with(viewBinding){
            navController =
                (supportFragmentManager.findFragmentById(navHostFragment.id) as? NavHostFragment)?.navController
            navController?.navigate(R.id.action_global_mainFragment)
        }
    }
}