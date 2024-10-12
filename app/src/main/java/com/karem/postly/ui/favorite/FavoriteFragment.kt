package com.karem.postly.ui.favorite

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.karem.postly.MainActivity
import com.karem.postly.R
import com.karem.postly.databinding.FragmentFavoriteBinding
import com.karem.postly.databinding.FragmentPostsBinding
import com.karem.postly.ui._core.PostsAdaptor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class FavoriteFragment : Fragment() {


    private val postsAdaptor by lazy {
        val adaptor = PostsAdaptor()
        binding.rvPosts.adapter = adaptor

        val itemTouchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                adaptor.removeItem(position)
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.rvPosts)

        adaptor
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        mainActivity.appBar.tvTitle.text = "Favorites"

        viewModel.postsErrorLiveData.observe(viewLifecycleOwner) {
            it.exception?.printStackTrace()
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
        }

        viewModel.postsLoadingLiveData.observe(viewLifecycleOwner) {
            binding.loading.isVisible = it
        }

        viewModel.postsLiveData.observe(viewLifecycleOwner) {
            postsAdaptor.submitPosts(it)
        }

        viewModel.favoriteLiveData.observe(viewLifecycleOwner) { favoriteList ->
            postsAdaptor.submitPosts(viewModel.postsLiveData.value!!.filter {
                favoriteList.contains(
                    it.id
                )
            })
            postsAdaptor.submitFavorites(favoriteList)
        }

        postsAdaptor.setOnFavorite {
            viewModel.togglePost(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    private val binding by lazy { FragmentPostsBinding.inflate(layoutInflater) }
    private val viewModel: FavoriteViewModel by viewModels()
    private val mainActivity by lazy { requireActivity() as MainActivity }

}