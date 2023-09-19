package com.zmei.api_anime

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zmei.api_anime.databinding.ImageItemBinding

class AnimeAdapter(private val loadMoreListener: () ->Unit) : RecyclerView.Adapter<AnimeAdapter.AnimeHolder>() {
    private val animeList = ArrayList<Image_Anime>()

    class AnimeHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = ImageItemBinding.bind(item)
        fun bind(imageAnime: Image_Anime) = with(binding) {
            Glide.with(root.context)
                .load(imageAnime.imageId.url)
                .into(binding.imageViewAnime)
            textTitle.text = imageAnime.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return AnimeHolder(view)
    }

    override fun getItemCount(): Int {
        return animeList.size
    }

    override fun onBindViewHolder(holder: AnimeHolder, position: Int) {
        holder.bind(animeList[position])
        if(position == animeList.size-1){
            loadMoreListener()
        }
    }

    fun addImage(imageAnime: Image_Anime) {
        animeList.add(imageAnime)
        notifyDataSetChanged()
    }
}