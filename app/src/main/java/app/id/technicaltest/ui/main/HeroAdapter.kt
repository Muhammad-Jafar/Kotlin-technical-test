package app.id.technicaltest.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.id.technicaltest.data.model.Hero
import app.id.technicaltest.databinding.ItemHeroBinding
import app.id.technicaltest.utils.Constanta

class HeroAdapter(private val listHero: ArrayList<Hero>) :
    ListAdapter<Hero, HeroAdapter.ViewHolder>(Constanta.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemHeroBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder.binding) {
        val (id, avatar, name, desc) = listHero[position]

        heroImage.setImageResource(avatar)
        heroName.text = name
        heroDesc.text = desc

        holder.itemView.setOnClickListener {
            val direction = HomeFragmentDirections.actionHomeToDetailHero(listHero[position])
            it.findNavController().navigate(direction)
        }
    }

    class ViewHolder(var binding: ItemHeroBinding) : RecyclerView.ViewHolder(binding.root)
}
