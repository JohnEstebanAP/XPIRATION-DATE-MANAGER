package com.johnestebanap.xpiration_date_manager.ui.base_datos

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.johnestebanap.xpiration_date_manager.R
import com.johnestebanap.xpiration_date_manager.database.DatabaseAccess
import kotlinx.android.synthetic.main.fragment_base_datos.*
import kotlinx.android.synthetic.main.fragment_base_datos.view.*

class BaseDatosFragment : Fragment() {

    var titulo = arrayOf(
        "Código",
        "Nombre",
        "Marca",
        "Cantidad",
        "Código Proveedor",
        "Código Estantería",
        "Código Pasillo",
        "Fecha Producto",
        "Caducidad",
        "Ingreso del Producto",
        "Estado del producto",
        "Código de Caducidad",
        "Estado de Caducidad"
    )


    var myTableRow = arrayOfNulls<TableRow>(200)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_base_datos, container, false)
/*
        val intent = Intent(activity, InventarioActivity::class.java)
        startActivity(intent)*/

        val databaseAccess = DatabaseAccess.getInstance(activity?.applicationContext)
        databaseAccess.open()
        val cantidadDatos = databaseAccess.Cantidaregistros()
        val consultaregistro = databaseAccess.getAllProductos(13, cantidadDatos)
        databaseAccess.close()
        val tabla = createTableLayout(cantidadDatos, 13, consultaregistro)
        root.parentLayout2.removeAllViews()
        root.parentLayout2.addView(tabla)

        botonConsulta1(root)
        botonConsulta2(root)
        botonConsulta3(root)


        return root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_regitrar_productos.setOnClickListener() {
            val intent = Intent(this@BaseDatosFragment.context, RegistrarProductoActivity::class.java)
            startActivity(intent)
        }
    }


    fun botonConsulta1(root: View): View {
        root.btnConsulta1.setOnClickListener() {
            val databaseAccess = DatabaseAccess.getInstance(activity?.applicationContext)
            databaseAccess.open()
            val cantidadDatos = databaseAccess.Cantidaregistros()
            val consultaregistro = databaseAccess.getAllProductos(13, cantidadDatos)
            databaseAccess.close()
            val tabla = createTableLayout(cantidadDatos, 13, consultaregistro)
            root.parentLayout2.removeAllViews()
            root.parentLayout2.addView(tabla)
        }
        return root
    }

    fun botonConsulta2(root: View): View {
        root.btnConsulta2.setOnClickListener() {
            val databaseAccess = DatabaseAccess.getInstance(activity?.applicationContext)
            databaseAccess.open()
            val cantidadDatos = databaseAccess.cantidadProductosFechamenos3()
            val consultaregistro = databaseAccess.productosFechamenos3(13, cantidadDatos)
            databaseAccess.close()
            val tabla = createTableLayout(cantidadDatos, 13, consultaregistro)
            root.parentLayout2.removeAllViews()
            root.parentLayout2.addView(tabla)
        }
        return root
    }

    fun botonConsulta3(root: View): View {
        root.btnConsulta3.setOnClickListener() {
            val databaseAccess = DatabaseAccess.getInstance(activity?.applicationContext)
            databaseAccess.open()
            val cantidadDatos = databaseAccess.cantidadProductosFechaProximo()
            val consultaregistro = databaseAccess.productosFechaProximo(13, cantidadDatos)
            databaseAccess.close()
            val tabla = createTableLayout(cantidadDatos, 13, consultaregistro)
            root.parentLayout2.removeAllViews()
            root.parentLayout2.addView(tabla)

        }
        return root
    }

    fun createTableLayout(
        rowCount: Int,
        columnCount: Int,
        registrosInventario: Array<Array<String>>
    ): TableLayout? {
        // 1) Create a tableLayout and its params
        val tableLayoutParams = TableLayout.LayoutParams()

        val tableLayout = TableLayout(activity)
        //  tableLayout.setBackgroundColor(Color.BLACK);
        // 2) create tableRow params
        val tableRowParams = TableRow.LayoutParams()

        tableRowParams.weight = 1f


        val tableRow = TableRow(activity)
        for (i in 0 until columnCount) {
            val textView = TextView(activity)
            textView.gravity = Gravity.CENTER
            textView.setPadding(10, 10, 10, 10)
            textView.background = resources.getDrawable(R.drawable.textview_border_titulo)
            textView.setTextColor(Color.BLACK)
            textView.text = titulo[i]
            tableRow.addView(textView, tableRowParams)


        }
        tableLayout.addView(tableRow, tableLayoutParams)


        for (i in 0 until rowCount) {
            // 3) create tableRow
            val tableRow = TableRow(activity)
            // tableRow.setBackgroundColor(Color.BLACK);
            for (j in 0 until columnCount) {

                // 4) create textView
                val textView = TextView(activity)
                textView.gravity = Gravity.CENTER
                textView.setPadding(10, 10, 10, 10)
                textView.setTextColor(Color.parseColor("#2E342F"))

                if (registrosInventario[i][11].equals("1")) {
                    textView.background = resources.getDrawable(R.drawable.textview_color1)
                }
                if (registrosInventario[i][11].equals("2")) {
                    textView.background = resources.getDrawable(R.drawable.textview_rojo)
                }
                if (registrosInventario[i][11].equals("3")) {
                    textView.background = resources.getDrawable(R.drawable.textview_amarillo)
                }
                if (registrosInventario[i][11].equals("4")) {
                    textView.background = resources.getDrawable(R.drawable.textview_border)
                }

                textView.text = registrosInventario[i][j]
                tableRow.addView(textView, tableRowParams)


            }
            tableLayout.addView(tableRow, tableLayoutParams)
            myTableRow[i] = tableRow
        }
        return tableLayout
    }
}