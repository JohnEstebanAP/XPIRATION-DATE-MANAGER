package com.johnestebanap.xpiration_date_manager.ui.alertas

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.NavUtils
import com.johnestebanap.xpiration_date_manager.R
import com.johnestebanap.xpiration_date_manager.database.DatabaseAccess
import kotlinx.android.synthetic.main.activity_info_producto.*
import java.text.SimpleDateFormat
import java.util.*

class InfoProductoActivity : AppCompatActivity() {

    var fechaHoy: Date? = null
    var fecha = ""
    var cedula: String? = null
    var codigoProducto: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_producto)

        val formato = SimpleDateFormat("yyyy-MM-dd")

        val date = formato.format(Date())
        fechaHoy = formato.parse(date);
        fecha = fechaHoy.toString()

        //  cedula = ActivityLogin().getDatos()
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        cedula = prefs.getString("cedula", null)
        codigoProducto = getIntent().getStringExtra("codigo")
        val codigoAlerta = getIntent().getStringExtra("codigoAlerta")

        //Toast.makeText(this, "el codigo es: $cedula", Toast.LENGTH_LONG).show()
        val databaseAccess = DatabaseAccess.getInstance(applicationContext)
        databaseAccess.open()
        val valores = databaseAccess.getProducto2(codigoProducto)
        databaseAccess.close()

        val cod_Producto: String? = valores[0]
        val nom_Producto: String? = valores[1]
        val nombre_marca: String? = valores[2]
        val cantidad: String? = valores[3]
        val fecha_Producto: String? = valores[4]
        val fecha_caducidad: String? = valores[5]
        val fecha_ingreso_Producto: String? = valores[6]
        val cod_estadoPro: String? = valores[7]
        val cod_estanteria: String? = valores[8]

        et_codigo.setText(cod_Producto)
        et_nombre_producto.setText(nom_Producto)
        et_marca.setText(nombre_marca)
        et_fecha_ex.setText(fecha_Producto)
        et_fecha_ca.setText(fecha_caducidad)
        et_fecha_ingreso.setText(fecha_ingreso_Producto)
        et_estanteria.setText(cod_estanteria)
        et_estado.setText(cod_estadoPro)
        et_cantidad.setText(cantidad)

        btn_aceptar.setOnClickListener() {
            opcionesProductos(codigoAlerta!!)
        }

        btn_cancelar.setOnClickListener() {
            val intent = Intent(this, AlertasActivity::class.java)
            //startActivity(intent)

            NavUtils.navigateUpTo(this, intent)
            finish()
        }
    }

    private fun opcionesProductos(cod_historial: String) {
        val opciones = arrayOf<CharSequence>(
            "Poner al frente",
            "Realizar promoción",
            "Poner en descuento",
            "Eliminar producto",
            "Cancelar"
        )
        val databaseAccess = DatabaseAccess.getInstance(applicationContext)
        databaseAccess.open()
        val alertOpciones = AlertDialog.Builder(this)
        alertOpciones.setTitle("¿Que desea hacer con el producto?")
        alertOpciones.setItems(
            opciones
        ) { dialogInterface, i ->
            if (opciones[i] == "Poner al frente") {
                databaseAccess.updateEstadoPro(codigoProducto, "1")
                databaseAccess.insertUsuarioHistorialAlertas(fecha, cod_historial, cedula, "1")
                databaseAccess.updateEstadoHistorialAlertas(cod_historial)
                Toast.makeText(
                    applicationContext,
                    "Se cambiado el estado del producto a 'Al frente'",
                    Toast.LENGTH_LONG
                ).show()

                val intent = Intent(this, AlertasActivity::class.java)
                //startActivity(intent)

                NavUtils.navigateUpTo(this, intent)
                finish()

            } else if (opciones[i] == "Realizar promoción") {
                databaseAccess.updateEstadoPro(codigoProducto, "2")
                databaseAccess.insertUsuarioHistorialAlertas(fecha, cod_historial, cedula, "2")
                databaseAccess.updateEstadoHistorialAlertas(cod_historial)
                Toast.makeText(
                    applicationContext,
                    "Se cambiado el estado del producto a 'En promoción'",
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(this, AlertasActivity::class.java)
                //startActivity(intent)

                NavUtils.navigateUpTo(this, intent)
                finish()

            } else if (opciones[i] == "Poner en descuento") {
                databaseAccess.updateEstadoPro(codigoProducto, "3")
                databaseAccess.insertUsuarioHistorialAlertas(fecha, cod_historial, cedula, "3")
                databaseAccess.updateEstadoHistorialAlertas(cod_historial)
                Toast.makeText(
                    applicationContext,
                    "Se cambiado el estado del producto a 'En descuento'",
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(this, AlertasActivity::class.java)
                //startActivity(intent)

                NavUtils.navigateUpTo(this, intent)
                finish()
            } else if (opciones[i] == "Eliminar producto") {
                databaseAccess.deleteProducto(codigoProducto)
                //     databaseAccess.insertUsuarioHistorialAlertas(fecha, cod_historial, cedula, "3")
                databaseAccess.updateEstadoHistorialAlertas(cod_historial)
                Toast.makeText(applicationContext, "Se ha eliminado el producto", Toast.LENGTH_LONG)
                    .show()
                val intent = Intent(this, AlertasActivity::class.java)
                //startActivity(intent)

                NavUtils.navigateUpTo(this, intent)
                finish()
            } else {
                dialogInterface.dismiss()
            }
            databaseAccess.close()
        }
        alertOpciones.show()
    }
}