package com.karem.postly.ui.splash

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.karem.postly.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Thread.sleep(5000)
        if (viewModel.isLoggedIn())
            findNavController().navigate(R.id.action_splashFragment_to_postsFragment)
        else
            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)

    }

}