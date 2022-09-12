package com.auyon.labrickmortyjoseauyon

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CharactersFragment : Fragment(R.layout.fragment_characters) , CharacterAdapter.CharacterListener {
private lateinit var recyclerView: RecyclerView
private lateinit var characterList : MutableList<Character>
private lateinit var buttonSort: Button


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycleCharacter)

        buttonSort = view.findViewById(R.id.buttonZA)
        setupRecycler()
        setListeners(buttonSort)
    }



    private fun setupRecycler() {
        characterList = Database.getCharacters()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = CharacterAdapter(characterList, this)
    }

    private  fun setListeners(button: Button){
        button.setOnClickListener {
            characterList.sortBy { character -> character.name }
            recyclerView.adapter!!.notifyDataSetChanged()
        }
    }

    override fun onPlaceClicked(character: Character, position: Int) {
        val action = requireView().findNavController().navigate(CharactersFragmentDirections
            .actionCharactersFragmentToCharacterDetailsFragment(character))
        return action
    }

}