package com.karem.postly.ui.posts

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.karem.postly.MainActivity
import com.karem.postly.databinding.FragmentPostsBinding
import com.karem.postly.ui._core.PostsAdaptor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class PostsFragment : Fragment() {


    val postsAdaptor by lazy {
        val adaptor = PostsAdaptor()
        binding.rvPosts.adapter = adaptor
        adaptor
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        mainActivity.appBar.tvTitle.text = "Posts"

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

        viewModel.favoriteLiveData.observe(viewLifecycleOwner) {
            postsAdaptor.submitFavorites(it)
        }

        postsAdaptor.setOnFavorite {
            viewModel.togglePost(it)
        }

        onScroll()
    }

    private fun onScroll() {
        binding.rvPosts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
                val lastVisibleItemPosition = layoutManager?.findLastVisibleItemPosition() ?: return

                if (lastVisibleItemPosition == postsAdaptor.itemCount - 1) {
                    viewModel.loadMoreData()
                }
            }
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }


    private val binding by lazy {
        FragmentPostsBinding.inflate(layoutInflater)
    }

    private val viewModel: PostsViewModel by viewModels()
    private val mainActivity by lazy { requireActivity() as MainActivity }

}