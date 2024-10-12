package com.karem.postly.ui._core

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.karem.postly.databinding.TileCommentBinding
import com.karem.postly.domain.models.CommentDto

class CommentAdaptor(val comments: List<CommentDto>) :
    RecyclerView.Adapter<CommentAdaptor.CommentViewHolder>() {

    class CommentViewHolder(val binding: TileCommentBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
            TileCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = comments.size

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments.get(position)
        holder.binding.apply {
            this.tvComment.text = comment.body
            this.tvCommenter.text = comment.name
        }
    }
}