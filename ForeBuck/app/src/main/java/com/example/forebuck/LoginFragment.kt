package com.example.forebuck

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment

class LoginFragment(
    private val orderType: String,
    private val onLoginSuccess: () -> Unit
) : Fragment() {

    private lateinit var dbHelper: UserDatabaseHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dbHelper = UserDatabaseHelper(requireContext())

        val layout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)

            val email = EditText(context).apply { hint = "Email" }
            val pass = EditText(context).apply {
                hint = "Password"
                inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            }

            val loginButton = Button(context).apply {
                text = "Login"
                setOnClickListener {
                    val emailText = email.text.toString()
                    val passText = pass.text.toString()

                    if (dbHelper.checkUser(emailText, passText)) {
                        Log.d("ForeBuckLogin", "Login success: $emailText")
                        onLoginSuccess()
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, OrderFragment())
                            .addToBackStack(null)
                            .commit()
                    } else {
                        Toast.makeText(context, "Login gagal: Email atau password salah", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            val registerButton = Button(context).apply {
                text = "Register"
                setOnClickListener {
                    val emailText = email.text.toString()
                    val passText = pass.text.toString()

                    if (emailText.isNotEmpty() && passText.isNotEmpty()) {
                        val success = dbHelper.registerUser(emailText, passText)
                        if (success) {
                            Toast.makeText(context, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Email sudah terdaftar.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Email dan password wajib diisi.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            addView(email)
            addView(pass)
            addView(loginButton)
            addView(registerButton)
        }

        return layout
    }
}
