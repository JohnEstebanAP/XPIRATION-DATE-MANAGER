package com.johnestebanap.xpiration_date_manager

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.johnestebanap.xpiration_date_manager.funcion_notification.WorkNoti
import com.johnestebanap.xpiration_date_manager.R

class MenuDesplegableActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_desplegable)

        val random = (Math.random() * 50 + 1).toInt()

        val data = GuardarData("Notificacion De prueba", "soy un detalle", random)
        WorkNoti.GuardarNoti(data)

        //--------------------------------------------------------------------------------------------------------------------///
        //se reciben los valores que se enviaron desde el login,  Como el nombre, correo y contrasenia del Usuario
        //setup
        val nombre = getIntent().getStringExtra("nombre")
        val email = getIntent().getStringExtra("email")
        val contrasenia = getIntent().getStringExtra("contrasenia")
        val cedula = getIntent().getStringExtra("cedula")
        /*
        val bundle =intent.extras
        val email = bundle?.getString("email")
        */


        //Guardado de datos
        //se guardan los datos en SharedPreferences
        val prefs =
            getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("nombre", nombre)
        prefs.putString("email", email)
        prefs.putString("contrasenia", contrasenia)
        prefs.putString("cedula", cedula)
        prefs.apply()

        //-------------------------------------------------------------------------------------------------------///

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //val fab: FloatingActionButton = findViewById(R.id.fab)
        /*
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_alertas,
                R.id.nav_basedatos,
                R.id.nav_calendario,
                R.id.nav_barras,
                R.id.nav_graficas,
                R.id.nav_supervisor,
                R.id.nav_salir
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    public fun cerrarSecion() {

        //Borrado de datos
        val prefs =
            getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.clear()
        prefs.apply()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_desplegable, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun GuardarData(titulo: String, detalle: String, id_noti: Int): Data {

        return Data.Builder()
            .putString("titulo", titulo)
            .putString("detalle", detalle)
            .putInt("id_noti", id_noti).build()

    }
}
