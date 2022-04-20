package com.johnestebanap.xpiration_date_manager.ui.base_datos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.johnestebanap.xpiration_date_manager.R

class BaseDatosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_datos)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.parentLayoutBaseDatos, BaseDatosFragment())
        // fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}