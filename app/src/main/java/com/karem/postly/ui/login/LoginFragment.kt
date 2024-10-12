package com.karem.postly.ui.login

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.afollestad.vvalidator.form
import com.karem.postly.MainActivity
import com.karem.postly.R
import com.karem.postly.databinding.FragmentFavoriteBinding
import com.karem.postly.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint

class LoginFragment : Fragment() {


    override fun onStart() {
        super.onStart()
        mainActivity.appBar.tvTitle.text = getString(R.string.postly)

        form {
            this.useRealTimeValidation(300, disableSubmit = true)


            input(binding.etEmail) {
                isNotEmpty()
                isEmail()
            }
            input(binding.etPassword) {
                isNotEmpty()
                length().atLeast(8).atMost(15)
            }
            submitWith(binding.btnLogin.id) { result ->
                if (result.success()) {
                    viewModel.login(binding.etEmail.text.toString())
                    findNavController().navigate(R.id.action_loginFragment_to_postsFragment)
                }
            }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }


    private val binding by lazy { FragmentLoginBinding.inflate(layoutInflater) }
    private val viewModel: LoginViewModel by viewModels()
    private val mainActivity by lazy { requireActivity() as MainActivity }
}