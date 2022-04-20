package com.johnestebanap.xpiration_date_manager.ui.alertas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.johnestebanap.xpiration_date_manager.R

class AlertasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alertas)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.parentLayoutAlertas, AlertasFragment())
        //fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
        //NavUtils.navigateUpTo(this, intent)
        //getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        //finish()
    }
}