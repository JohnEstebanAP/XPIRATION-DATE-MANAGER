package com.johnestebanap.xpiration_date_manager.ui.alertas

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
//import kotlinx.android.synthetic.main.activity_opciones_producto.*

class opcionesProducto : AppCompatActivity(){

   /*private fun opcionesProductos() {
       val opciones = arrayOf<CharSequence>(
           "Poner al frente",
           "Realizar promoción",
           "Poner en descuento",
           "Eliminar producto",
           "Cancelar"
       )
       val databaseAccess = DatabaseAccess.getInstance(applicationContext)
       databaseAccess.open()
       val alertOpciones = AlertDialog.Builder(this@opcionesProducto)
       alertOpciones.setTitle("¿Que desea hacer con el producto?")
       alertOpciones.setItems(
           opciones
       ) { dialogInterface, i ->
           if (opciones[i] == "Poner al frente") {
               databaseAccess.updateEstadoPro("1",1)
               Toast.makeText(applicationContext,"Se cambiado el estado del producto a 'Al frente'", Toast.LENGTH_LONG).show()
           }else if (opciones[i] == "Realizar promoción") {
               databaseAccess.updateEstadoPro(1,2)
               Toast.makeText(applicationContext,"Se cambiado el estado del producto a 'En descuento'", Toast.LENGTH_LONG).show()
           }else if (opciones[i] == "Poner en descuento") {
               databaseAccess.updateEstadoPro(1,3)
               Toast.makeText(applicationContext,"Se cambiado el estado del producto a 'En promoción'", Toast.LENGTH_LONG).show()
           }else if (opciones[i] == "Eliminar producto") {
               databaseAccess.deleteProducto(1)
               Toast.makeText(applicationContext,"Se ha eliminado el producto", Toast.LENGTH_LONG).show()
           } else {
               dialogInterface.dismiss()
           }
           databaseAccess.close()
       }
       alertOpciones.show()

   }

   override fun onClick(v: View?) {
       opcionesProductos()
   }

   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_opciones_producto)

   }*/
}