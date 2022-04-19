package com.johnestebanap.xpiration_date_manager.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.johnestebanap.xpiration_date_manager.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //El metodo Thead nos permite utilizar ilos
        //Este puede generar error por lo que locaturamos dentro de un try catch
        //try {
        //    Thread.sleep(3000)//Permite mantener el splash screen por un segundo
        //}catch (e: InterruptedException){
        //    e.printStackTrace()
        //}

        //  setTheme(R.style.SplashTheme)// Especificamos que nuestra activity usara el tema del splash screen

        //comprobar si existe una sesión activa
        session()
    }


    private fun session() {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val nombre = prefs.getString("nombre", null)
        val email = prefs.getString("email", null)
        val contrasenia = prefs.getString("contrasenia", null)
        val cedula = prefs.getString("cedula", null)

        if (email != null && nombre != null && contrasenia != null && cedula != null) {
            //cedulaUsuario = cedula
            showHome(nombre, email, contrasenia, cedula)
        }else{
            startActivity(Intent(this, LoginActivity::class.java))
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

}