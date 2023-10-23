package com.pinto.storyappkt.ui.main.list_story

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import com.pinto.storyappkt.data.models.stories.Story
import com.pinto.storyappkt.databinding.CardStoriesBinding

class StoriesAdapter(
    private val callback: (
        story: Story,
        imageView: View,
        nameView: View,
        descView: View,
    ) -> Unit,
) : PagingDataAdapter<Story, StoriesViewHolder>(DIFF_CALLBACK) {
    override fun onBindViewHolder(holder: StoriesViewHolder, position: Int) {
        val item = getItem(position)
        holder.view.root.setOnClickListener {
            if (item != null) {
                callback.invoke(
                    item,
                    holder.view.ivItemPhoto,
                    holder.view.tvItemName,
                    holder.view.tvItemDesc,
                )
            }
        }
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesViewHolder {
        val view = CardStoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoriesViewHolder(view)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}

class StoriesViewHolder(val view: CardStoriesBinding) : RecyclerView.ViewHolder(view.root) {
    fun bind(item: Story) {
        view.story = item

        val drawable = CircularProgressDrawable(view.root.context)
        drawable.strokeWidth = 5f
        drawable.centerRadius = 30f
        drawable.start()

        view.ivItemPhoto.load(item.photoUrl)
        {
            placeholder(drawable)
            allowHardware(false)
        }

        view.executePendingBindings()
    }

}
