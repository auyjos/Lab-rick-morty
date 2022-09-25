package com.auyon.lab10rickmorty_joseauyon

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputLayout

class LogInFragment : Fragment(R.layout.fragment_log_in) {


    private lateinit var inputKey: TextInputLayout
    private lateinit var inputValue: TextInputLayout
    private lateinit var buttonLog: Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inputKey = view.findViewById(R.id.email_input)
        inputValue = view.findViewById(R.id.password_input)
        buttonLog = view.findViewById(R.id.guardarInfo)
        sharedPreferences = requireContext().getSharedPreferences("correo", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        setListeners()
    }

    private fun setListeners() {
        buttonLog.setOnClickListener {
            val key = inputKey.editText!!.text.toString()
            val value = inputValue.editText!!.text.toString()

            editor.putString("auy201579@uvg.edu.gt", key)
            editor.putString("auy201579@uvg.edu.gt", value)
            editor.apply()


            val emailLog = sharedPreferences.getString("auy201579@uvg.edu.gt", null)
            val passLog = sharedPreferences.getString("auy201579@uvg.edu.gt", null)

            if (emailLog.equals(key) && passLog.equals(value)) {
                Toast.makeText(context, "Login", Toast.LENGTH_SHORT).show()
                val actionNav = LogInFragmentDirections.actionLogInFragmentToCharacterListFragment()
                requireView().findNavController().navigate(actionNav)
            }
            else {
                Toast.makeText(context, "Incorrect", Toast.LENGTH_LONG).show()
            }
        }
    }
}
