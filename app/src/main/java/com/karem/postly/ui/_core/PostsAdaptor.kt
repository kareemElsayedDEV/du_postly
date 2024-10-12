package com.karem.postly.ui._core

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.karem.postly.R
import com.karem.postly.databinding.TilePostBinding
import com.karem.postly.domain.models.PostDto

class PostsAdaptor :
    RecyclerView.Adapter<PostsAdaptor.PostsViewHolder>() {
    private var posts: List<PostDto> = emptyList()
    private var favoriteList: List<Int> = emptyList()

    private var onFavorite: ((Int) -> Unit)? = null

    fun setOnFavorite(onFavorite: (Int) -> Unit) {
        this.onFavorite = onFavorite
    }

    fun submitPosts(posts: List<PostDto>) {
        this.posts = posts
        notifyDataSetChanged()
    }

    fun submitFavorites(favoriteList: List<Int>) {
        this.favoriteList = favoriteList
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        val item = posts[position]
        onFavorite?.invoke(item.id)
        this.notifyItemRemoved(position)
    }


    class PostsViewHolder(val binding: TilePostBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        return PostsViewHolder(
            TilePostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = posts.size

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        val post = posts[position]
        holder.binding.apply {
            this.tvBody.text = post.body
            this.tvTitle.text = post.userName
            this.rvComments.adapter = CommentAdaptor(post.comments.take(2))

            if (favoriteList.contains(post.id)) {
                this.ivFavorite.setImageResource(R.drawable.baseline_star_24)
            } else {
                this.ivFavorite.setImageResource(R.drawable.baseline_star_border_24)
            }

            this.ivFavorite.setOnClickListener {
                onFavorite?.invoke(post.id)
            }
        }
    }
}