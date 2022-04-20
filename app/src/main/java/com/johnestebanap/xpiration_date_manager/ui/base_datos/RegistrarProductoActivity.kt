package com.johnestebanap.xpiration_date_manager.ui.base_datos

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.zxing.integration.android.IntentIntegrator
import com.johnestebanap.xpiration_date_manager.R
import java.text.SimpleDateFormat
import java.util.*

class RegistrarProductoActivity : AppCompatActivity() {

    var actual: Calendar = Calendar.getInstance()
    var calendar: Calendar = Calendar.getInstance()

    private var dia: Int = 0
    private var mes: Int = 0
    private var anio: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_producto)

        val databaseAccess = DatabaseAccess.getInstance(this)
        databaseAccess.open()

        val cantidadProductos = databaseAccess.cantidadDatosTabla("Producto")
        val cantidadEstanteria = databaseAccess.cantidadDatosTabla("Estanteria")
        val cantidadProveedor = databaseAccess.cantidadDatosTabla("Proveedor")

        val autoTextMarca =
            databaseAccess.getDatosTabla(cantidadProductos, "nombre_marca", "Producto")
        val autoTextestantaria =
            databaseAccess.getDatosTabla(cantidadEstanteria, "cod_estanteria", "Estanteria")
        val autoTextProveedor =
            databaseAccess.getDatosTabla(cantidadProveedor, "nom_proveedor", "Proveedor")

        var adapter = ArrayAdapter(this, android.R.layout.select_dialog_item, autoTextMarca)
        et_marca.threshold = 1 //will start working from first character
        et_marca.setAdapter(adapter)

        adapter = ArrayAdapter(this, android.R.layout.select_dialog_item, autoTextProveedor)
        et_proveedor.threshold = 1 //will start working from first character
        et_proveedor.setAdapter(adapter)

        adapter = ArrayAdapter(this, android.R.layout.select_dialog_item, autoTextestantaria)
        et_estanteria.threshold = 1 //will start working from first character
        et_estanteria.setAdapter(adapter)

        databaseAccess.close()

        btn_escanear.setOnClickListener() {
            scanCode()
        }

        btn_aceptar.setOnClickListener() {
            editarProducto()
        }

        btn_cancelar.setOnClickListener {
            limpiarDatos()
        }

        btn_fecha_ex.setOnClickListener {
            anio = actual.get(Calendar.YEAR);
            mes = actual.get(Calendar.MONTH);
            dia = actual.get(Calendar.DAY_OF_MONTH);

            val datePickerDialog = DatePickerDialog(
                this@RegistrarProductos, DatePickerDialog.OnDateSetListener
                { view, y, m, d ->
                    calendar[Calendar.DAY_OF_MONTH] = d
                    calendar[Calendar.MONTH] = m
                    calendar[Calendar.YEAR] = y
                    val format = SimpleDateFormat("yyyy/MM/dd")
                    val strDate = format.format(calendar.time)
                    tv_fecha_ex.setText(strDate)
                }, anio, mes, dia
            )
            datePickerDialog.show()
        }

        btn_fecha_ca.setOnClickListener {
            anio = actual.get(Calendar.YEAR);
            mes = actual.get(Calendar.MONTH);
            dia = actual.get(Calendar.DAY_OF_MONTH);

            val datePickerDialog = DatePickerDialog(
                this@RegistrarProductos, DatePickerDialog.OnDateSetListener
                { view, y, m, d ->
                    calendar[Calendar.DAY_OF_MONTH] = d
                    calendar[Calendar.MONTH] = m
                    calendar[Calendar.YEAR] = y
                    val format = SimpleDateFormat("yyyy/MM/dd")
                    val strDate = format.format(calendar.time)
                    tv_fecha_ca.setText(strDate)
                }, anio, mes, dia
            )
            datePickerDialog.show()
        }

        btn_fecha_ingreso.setOnClickListener {
            anio = actual.get(Calendar.YEAR);
            mes = actual.get(Calendar.MONTH);
            dia = actual.get(Calendar.DAY_OF_MONTH);

            val datePickerDialog = DatePickerDialog(
                this@RegistrarProductos, DatePickerDialog.OnDateSetListener
                { view, y, m, d ->
                    calendar[Calendar.DAY_OF_MONTH] = d
                    calendar[Calendar.MONTH] = m
                    calendar[Calendar.YEAR] = y
                    val format = SimpleDateFormat("yyyy/MM/dd")
                    val strDate = format.format(calendar.time)
                    tv_fecha_ingreso.setText(strDate)
                }, anio, mes, dia
            )
            datePickerDialog.show()
        }
    }

    fun scanCode() {
        var integrator = IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct::class.java);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning Code");
        integrator.initiateScan();
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        Toast.makeText(this, "codigo " + result.getContents() + ".", Toast.LENGTH_SHORT).show()
        if (result != null) {

            if (result.getContents() != null) {


                var codigoProducto = result.getContents()

                val existeProducto = ExistenciaProducto(codigoProducto)
                if (existeProducto == false) {
                    et_codigo.setText(codigoProducto)
                } else {
                    showAlert(codigoProducto)
                }
            }

        } else {
            Toast.makeText(this, "A ocurido un Error", Toast.LENGTH_SHORT).show()
        }

    }

    fun ExistenciaProducto(cod_Producto: String): Boolean {
        val databaseAccess = DatabaseAccess.getInstance(this)
        databaseAccess.open()
        val cantidadProducto = databaseAccess.getProductoScanerCantidad(cod_Producto)
        var ExisteProducto = false

        if (cantidadProducto == 0) {
            ExisteProducto = false
        } else {
            ExisteProducto = true
        }

        databaseAccess.close()
        return ExisteProducto
    }

    fun editarProducto() {

        var estado = "4"
        var cod_cad = "4"
        var cod_Producto = et_codigo.text.toString()
        var nom_Producto = et_nombre_producto.text.toString()
        var nombre_marca = et_marca.text.toString()
        var cantidad = et_cantidad.text.toString()
        var cod_proveedor = et_proveedor.text.toString()
        var fecha_expedicion = tv_fecha_ex.text.toString()
        var fecha_caducidad = tv_fecha_ca.text.toString()
        var fecha_registro = tv_fecha_ingreso.text.toString()
        var cod_estanteria = et_estanteria.text.toString()

        if (et_codigo.text.isNotEmpty() && et_nombre_producto.text.isNotEmpty()
            && et_marca.text.isNotEmpty() && et_cantidad.text.isNotEmpty() &&
            tv_fecha_ex.text.isNotEmpty() && tv_fecha_ca.text.isNotEmpty() &&
            tv_fecha_ingreso.text.isNotEmpty() && et_estanteria.text.isNotEmpty()
        ) {

            val existeProducto = ExistenciaProducto(cod_Producto)
            if (existeProducto == false) {
                val databaseAccess = DatabaseAccess.getInstance(this)
                databaseAccess.open()
                cod_proveedor =
                    databaseAccess.getCodProvedor(cod_proveedor)//hace una consulta donde busca el código del Proveedor con el nombre del proveedor
                databaseAccess.insertProductoScanerRegistro(
                    cod_Producto,
                    nom_Producto,
                    nombre_marca,
                    cantidad,
                    fecha_expedicion,
                    fecha_caducidad,
                    fecha_registro,
                    cod_estanteria
                )

                databaseAccess.insertProductoProveedor(cod_Producto, cod_proveedor)
                Toast.makeText(this, "Inserción  de producto exitosa", Toast.LENGTH_SHORT).show()
                databaseAccess.close()

                limpiarDatos()

            } else {
                showAlert(cod_Producto)
            }
        } else {
            showAlert2()
        }

    }


    private fun showAlert(codigoProducto: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Existe un producto con este código: $codigoProducto")
        builder.setPositiveButton("aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


    private fun showAlert2() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Tiene que llenar todos los campos")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun limpiarDatos() {
        et_codigo.setText("")
        et_nombre_producto.setText("")
        et_marca.setText("")
        et_cantidad.setText("")
        tv_fecha_ex.setText("Fecha de expedición")
        tv_fecha_ca.setText("Fecha de caducidad")
        tv_fecha_ingreso.setText("Fecha de registro")
        et_estanteria.setText("")
        et_proveedor.setText("")
    }
}
