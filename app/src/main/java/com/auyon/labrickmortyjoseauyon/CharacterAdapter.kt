package com.auyon.labrickmortyjoseauyon
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.load

class CharacterAdapter(
    private val dataSet: MutableList<Character>,
    private val characterListener: CharacterListener
):
    RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    interface CharacterListener {
        fun onPlaceClicked(character: Character, position: Int)
    }

    class ViewHolder(private var view: View,
                     private val listener: CharacterListener
    ) : RecyclerView.ViewHolder(view) {
        private val textName: TextView = view.findViewById(R.id.text_itemPlace_name)
        private val textType: TextView = view.findViewById(R.id.Type)
        private val textStatus: TextView = view.findViewById(R.id.Status)
        private val layout : ConstraintLayout = view.findViewById(R.id.layout_itemCharacter)
        private val characterImage1 : ImageView = view.findViewById(R.id.image1)
       // private val characterImage2 : ImageView = view.findViewById(R.id.image1)
        private lateinit var character: Character

        fun setData(character: Character) {
            this.character = character
            textName.text = character.name
            textStatus.text = character.status
            textType.text = character.species

            setListeners()
            setImage()
        }

        private fun setListeners() {
            layout.setOnClickListener {
                listener.onPlaceClicked(character, this.adapterPosition)
            }
        }

        private fun setImage() {
            // Cargamos la imagen con placeholder e ícono de error incluidos
            layout.setOnClickListener {
                characterImage1.load("https://rickandmortyapi.com/api/character/avatar/1.jpeg") {
                    error(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
                    placeholder(R.drawable.ic_launcher_foreground)
                }
            }

    }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_characters, parent, false)

        return ViewHolder(view, characterListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

}