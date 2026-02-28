package com.wordpresssync.app

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wordpresssync.app.api.model.WpPost
import com.wordpresssync.app.databinding.ItemPostBinding

class PostsAdapter(private var posts: List<WpPost>) : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount() = posts.size

    fun updatePosts(newPosts: List<WpPost>) {
        posts = newPosts
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: WpPost) {
            binding.postTitle.text = post.title?.rendered?.let { Html.fromHtml(it, Html.FROM_HTML_MODE_LEGACY) }
            binding.postDate.text = post.date
            binding.postExcerpt.text = post.excerpt?.rendered?.let { Html.fromHtml(it, Html.FROM_HTML_MODE_LEGACY) }?.toString()?.trim()
        }
    }
}
