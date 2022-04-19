package com.johnestebanap.xpiration_date_manager.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.johnestebanap.xpiration_date_manager.R
import com.johnestebanap.xpiration_date_manager.database.DatabaseAccess
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setup()
    }


    private fun setup() {

        botonIngresar.setOnClickListener {

            if (etLogin.text.isNotEmpty() && etPassword.text.isNotEmpty()) {// verifica que el campo no este vacio
                val databaseAccess = DatabaseAccess.getInstance(applicationContext)
                databaseAccess.open()
                val validacion = etLogin.text.toString()
                val contraseñaBalidadcion = etPassword.text.toString()

                val cedula: String

                val correo: String

                if (isNumeric(validacion) == true) {
                    cedula = validacion
                    correo = ""
                } else {
                    correo = validacion
                    cedula = "00"
                }

                val valores = databaseAccess.getLogin(cedula, correo)
                databaseAccess.close()
                val nombre: String? = valores[0]
                val cedulauser: String? = valores[1]
                val correouser: String? = valores[2]
                val contrasenia: String? = valores[3]

                if (cedulauser.equals(validacion) || correouser.equals(validacion) && contrasenia.equals(
                        contraseñaBalidadcion
                    )
                ) {
                    showHome(nombre ?: "", correouser ?: "", contrasenia ?: "", cedulauser ?: "")

                } else {
                    showAlert()
                }
            }
        }
    }

    private fun showHome(nombre: String, email: String, contrasenia: String, cedula: String) {

        val homeIntent = Intent(this, WelcomeActivity::class.java).apply {
            putExtra("nombre", nombre)
            putExtra("email", email)
            putExtra("contrasenia", contrasenia)
            putExtra("cedula", cedula)
        }

        startActivity(homeIntent)
        finishAffinity()
    }

    fun isNumeric(cadena: String): Boolean {

        val resultado: Boolean
        resultado = try {
            cadena.toInt()
            true
        } catch (excepcion: NumberFormatException) {
            false
        }
        return resultado
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}