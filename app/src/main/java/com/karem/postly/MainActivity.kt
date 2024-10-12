package com.karem.postly

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.karem.postly.databinding.ActivityMainBinding
import com.karem.postly.databinding.ItemAppBarBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val bottomNavigationFragmentsId =
        listOf(R.id.postsFragment, R.id.favoriteFragment)

    private val bottomNavigationNotAllowedList =
        listOf(R.id.loginFragment, R.id.splashFragment)

    private val backButtonNotAllowedList =
        listOf(R.id.loginFragment, R.id.postsFragment, R.id.splashFragment, R.id.favoriteFragment)


    @SuppressLint("RestrictedApi")
    override fun onStart() {
        super.onStart()
        val navController = findNavController(binding.navHosFragment.id);

        appBar.btnBack.setOnClickListener { navController.popBackStack() }
        appBar.btnLogout.setOnClickListener {
            viewModel.logoutUseCase()
            navController.navigate(R.id.action_global_splashFragment)
        }


        lifecycleScope.launch {
            navController.currentBackStack.collect { stackEntries ->
                val current = navController.currentDestination!!.id;

                if (bottomNavigationFragmentsId.contains(current) && stackEntries.size > 2) {
                    navController.popBackStack(navController.graph.id, inclusive = false)
                    navController.navigate(current)
                }
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.navView.visibility =
                if (bottomNavigationNotAllowedList.contains(destination.id)) View.INVISIBLE else View.VISIBLE

            appBar.btnBack.visibility =
                if (backButtonNotAllowedList.contains(destination.id)) View.INVISIBLE else View.VISIBLE

            appBar.btnLogout.visibility =
                if (viewModel.isUserLoggedInUseCase()) View.VISIBLE else View.INVISIBLE
        }

        binding.navView.setupWithNavController(navController)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    val appBar: ItemAppBarBinding
        get() = binding.appBar

    private val viewModel: MainActivityViewModel by viewModels()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

}