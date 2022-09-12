package com.auyon.labrickmortyjoseauyon

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs


class CharacterDetailsFragment : Fragment(R.layout.fragment_character_details) {
lateinit var TextCharacterDetails : TextView
lateinit var backButton: Button
val args: CharacterDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        TextCharacterDetails = view.findViewById(R.id.character_details)
        TextCharacterDetails.text = args.character.toString()
        backButton = view.findViewById(R.id.buttonBack)

        setListeners()
    }

    private fun setListeners() {
        val action =  requireView().findNavController().navigate(CharacterDetailsFragmentDirections.actionCharacterDetailsFragmentToCharactersFragment())
    }


}