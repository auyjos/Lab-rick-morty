package com.auyon.lab10rickmorty_joseauyon

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.auyon.lab10rickmorty_joseauyon.R
import com.auyon.lab10rickmorty_joseauyon.RetrofitInstance
import com.auyon.lab10rickmorty_joseauyon.Character
import com.auyon.lab10rickmorty_joseauyon.CharactersResponse
import com.auyon.lab10rickmorty_joseauyon.CharacterAdapter
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterListFragment : Fragment(R.layout.fragment_character_list), CharacterAdapter.RecyclerViewCharactersEvents {

    private var characters = mutableListOf<Character>()
    private lateinit var adapter: CharacterAdapter
    private lateinit var toolbar: MaterialToolbar
    private lateinit var recyclerCharacters: RecyclerView
    private lateinit var database: Database

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerCharacters = view.findViewById(R.id.recycler_characters)
        toolbar = view.findViewById(R.id.toolbar_characterList)

        setToolbar()
        setListeners()
        getCharacters()
    }

    private fun setToolbar() {
        val navController = findNavController()
        val appbarConfig = AppBarConfiguration(navController.graph)

        toolbar.setupWithNavController(navController, appbarConfig)
    }

    private fun setListeners() {
        toolbar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.menu_item_asc -> {
                    characters.sortBy { character -> character.name }
                    adapter.notifyDataSetChanged()
                    true
                }

                R.id.menu_item_des -> {
                    characters.sortByDescending { character -> character.name }
                    adapter.notifyDataSetChanged()
                    true
                }

                R.id.menu_cerrar ->{
                    System.exit(0)
                    true
                }

                else -> true
            }
        }
    }

    private fun getCharacters() {
        RetrofitInstance.api.getCharacters().enqueue(object: Callback<CharactersResponse> {
            override fun onResponse(
                call: Call<CharactersResponse>,
                response: Response<CharactersResponse>
            ) {
                if (response.isSuccessful) {
                    val res = response.body()?.results
                    setupRecycler(res ?: mutableListOf())
                }
            }

            override fun onFailure(call: Call<CharactersResponse>, t: Throwable) {
                Toast.makeText(requireContext(), getString(R.string.error_fetching), Toast.LENGTH_LONG).show()
            }

        })
    }
    private fun setupRecycler(characters: MutableList<Character>) {

        this.characters = characters

        adapter = CharacterAdapter(this.characters, this)
        recyclerCharacters.layoutManager = LinearLayoutManager(requireContext())
        recyclerCharacters.setHasFixedSize(true)
        recyclerCharacters.adapter = adapter
    }

    override fun onItemClicked(character:Character) {
        val action = CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailsFragment(
            character.id
        )

        requireView().findNavController().navigate(action)
    }

}