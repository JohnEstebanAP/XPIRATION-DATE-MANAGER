package com.johnestebanap.xpiration_date_manager.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.johnestebanap.xpiration_date_manager.MenuDesplegableActivity
import com.johnestebanap.xpiration_date_manager.R
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        //--------------------------------------------------------------------------------------------------------------------///
        //se reciben los valores que se enviaron desde el login,  Como el correo
        //setup
        val nombre =intent.getStringExtra("nombre")
        val email = intent.getStringExtra("email")
        val contrasenia = intent.getStringExtra("contrasenia")
        val cedula = intent.getStringExtra("cedula")

        setup(email ?: "", nombre ?: "")

        //Segundo metodo para detener e ejecucion de un codigo por un timepo
        Handler(Looper.getMainLooper()).postDelayed({
            showHome(email ?: "", nombre ?: "", contrasenia ?: "", cedula ?: "")
        }, 1500)
    }

    @SuppressLint("SetTextI18n")
    private fun setup(email: String, nombre: String) {
        tvnombre.text = "${getString(R.string.Bienvenido)} $nombre"
        tvcorreo.text = email
    }
    private fun showHome(email: String, nombre: String, contrasenia: String, cedula: String) {

        val intent = Intent(this, MenuDesplegableActivity::class.java).apply {
            putExtra("nombre", nombre)
            putExtra("email", email)
            putExtra("contrasenia", contrasenia)
            putExtra("cedula", cedula)
        }
        startActivity(intent)
        finish()
    }
}