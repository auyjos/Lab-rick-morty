package com.auyon.lab10rickmorty_joseauyon

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.room.Room
import coil.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterDetailsFragment : Fragment(R.layout.fragment_character_details) {

    private val args: CharacterDetailsFragmentArgs by navArgs()
    private lateinit var txtName: TextView
    private lateinit var txtSpecies: TextView
    private lateinit var txtGender: TextView
    private lateinit var txtStatus: TextView
    private lateinit var txtOrigin: TextView
    private lateinit var txtEpisodes: TextView
    private lateinit var editName: TextInputEditText
    private lateinit var editSpecies: TextInputEditText
    private lateinit var editGender: TextInputEditText
    private lateinit var editStatus: TextInputEditText
    private lateinit var editOrigin: TextInputEditText
    private lateinit var editEpisodes: TextInputEditText
    private lateinit var imageCharacter: ImageView
    private lateinit var toolbar: MaterialToolbar
    private lateinit var database: Database
    private lateinit var guardarDatos: Button
    private lateinit var actualizarDatosbtn : Button
    private lateinit var borrarDatosbtn: Button
    private lateinit var currentUser: Character
    private var createUser: Boolean = false
   // private val characterList: MutableList<Character> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.apply {
          txtName = findViewById(R.id.text_characterDetails_name)
            txtSpecies = findViewById(R.id.text_characterDetails_species)
           txtGender = findViewById(R.id.text_characterDetails_gender)
            txtStatus = findViewById(R.id.text_characterDetails_status)
            txtOrigin = findViewById(R.id.text_characterDetails_origin)
            txtEpisodes = findViewById(R.id.text_characterDetails_episodes)
            editName = findViewById(R.id.text_characterDetails_name)
            editSpecies = findViewById(R.id.text_characterDetails_species)
            editGender = findViewById(R.id.text_characterDetails_gender)
            editStatus = findViewById(R.id.text_characterDetails_status)
            this@CharacterDetailsFragment.editOrigin =
                findViewById(R.id.text_characterDetails_origin)
            editEpisodes = findViewById(R.id.text_characterDetails_episodes)

            imageCharacter = findViewById(R.id.image_characterDetails)
            toolbar = findViewById(R.id.toolbar_characterDetails)
            guardarDatos = findViewById(R.id.guardarDatos)
            actualizarDatosbtn = findViewById(R.id.actualizarDatos)
            borrarDatosbtn = findViewById(R.id.borrarDatos)
        }
        database = Room.databaseBuilder(
            requireContext(),
            Database::class.java,
            "Database"
        ).build()

        setToolbar()
        getCharacter()
        if (args.id == 1) {
            createUser = true
        } else {
            fetchUser()
        }
        setListeners()
        initData()
    }




    private fun setToolbar() {
        val navController = findNavController()
        val appbarConfig = AppBarConfiguration(navController.graph)

        toolbar.setupWithNavController(navController, appbarConfig)
    }

    private fun getCharacter() {
        RetrofitInstance.api.getCharacter(args.id).enqueue(object : Callback<Character> {
            override fun onResponse(call: Call<Character>, response: Response<Character>) {
                if (response.isSuccessful && response.body() != null) {
                    setData(response.body()!!)


                }
            }

            override fun onFailure(call: Call<Character>, t: Throwable) {
                Toast.makeText(requireContext(),
                    getString(R.string.error_fetching),
                    Toast.LENGTH_LONG).show()
            }



        })
    }
    private fun setData(character: Character) {
        character.apply {
            txtName.text = name
            txtSpecies.text = species
           txtStatus.text = status
            txtGender.text = gender
            txtOrigin.text = origin
            txtEpisodes.text = episode.toString()
            imageCharacter.load(image) {
                placeholder(R.drawable.ic_downloading)
                transformations(CircleCropTransformation())
                error(R.drawable.ic_error)
                memoryCachePolicy(CachePolicy.ENABLED)
            }
        }
    }


    private fun initData() {
        guardarDatos.text = if (createUser)
            "Crear"
        else
            "Actualizar"
    }


    private fun fetchUser() {
        CoroutineScope(Dispatchers.IO).launch {
            val user = database.charDao().getUserById(args.id)
            CoroutineScope(Dispatchers.Main).launch {
                if (user !== null) {
                    editName.setText(user.name)
                    editSpecies.setText(user.species)
                    editStatus.setText(user.status)
                    editGender.setText(user.gender)
                    editOrigin.setText(user.origin)
                    editEpisodes.setText(user.episode.toString())
                    currentUser = user

                } else {
                    Toast.makeText(
                        requireContext(),
                        "No se encontro usuario con ID ${args.id}",
                        Toast.LENGTH_LONG
                    ).show()
                    requireActivity().onBackPressed()
                }
            }
        }
    }


    private fun updateUser() {
        val updatedUser = currentUser.copy(

            name = editName.text.toString(),
            species = editSpecies.text.toString(),
            status = editStatus.text.toString(),
            gender= editGender.text.toString(),
           origin = txtOrigin.toString(),
            episode= txtEpisodes.toString().toInt()
        )

        CoroutineScope(Dispatchers.IO).launch {
            database.charDao().update(updatedUser)
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(
                    requireContext(),
                    "Usuario actualizado exitosamente",
                    Toast.LENGTH_LONG
                ).show()
                requireActivity().onBackPressed()
            }
        }
    }

    private fun deleteUser() {
        CoroutineScope(Dispatchers.IO).launch {
            val users = database.charDao().deleteAll()
            CoroutineScope(Dispatchers.Main).launch {
                if (users > 0) {
                    Toast.makeText(
                        requireContext(),
                        "Se han eliminado $users usuarios",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Error al tratar de eliminar usuarios",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setListeners() {
        val action  = CharacterDetailsFragmentDirections.actionCharacterDetailsFragmentToCharacterListFragment()
        guardarDatos.setOnClickListener {
            requireView().findNavController().navigate(action)
        }
        borrarDatosbtn.setOnClickListener {
            deleteUser()
        }
        actualizarDatosbtn.setOnClickListener {
            updateUser()
        }

    }

}

