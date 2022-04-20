package com.johnestebanap.xpiration_date_manager.funcion_notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
//import kotlinx.android.synthetic.main.activity_notificaciones.*
import java.text.SimpleDateFormat
import java.util.*

class notificaciones : AppCompatActivity() {
/*
    private var pendingIntent: PendingIntent? = null
    private var siPendingIntent: PendingIntent? = null
    private var noPendingIntent: PendingIntent? = null
    private val CHANNEL_ID = "NOTIFICATION"
    val NOTIFICACTION_ID = 0

    var actual: Calendar = Calendar.getInstance()
    var calendar: Calendar = Calendar.getInstance()

    private val minutos = 0
    private var hora: Int = 0
    private var dia: Int = 0
    private var mes: Int = 0
    private var anio: Int = 0

    fun sumarRestarDiasFecha(fecha: Date, dias: Int): Date {

        var calendar = Calendar.getInstance()
        calendar.time = fecha // Configuramos la fecha que se recibe

        calendar.add(
            Calendar.DAY_OF_YEAR,
            -dias
        ) // numero de días a añadir, o restar en caso de días<0

        return calendar.time // Devuelve el objeto Date con los nuevos días añadidos
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notificaciones)

        anio = actual.get(Calendar.YEAR);
        mes = actual.get(Calendar.MONTH);
        dia = actual.get(Calendar.DAY_OF_MONTH);

        btn_fecha_ex.setOnClickListener{
            anio = actual.get(Calendar.YEAR);
            mes = actual.get(Calendar.MONTH);
            dia = actual.get(Calendar.DAY_OF_MONTH);

            val datePickerDialog = DatePickerDialog(
                this@notificaciones, DatePickerDialog.OnDateSetListener
                { view, y, m, d ->
                    calendar[Calendar.DAY_OF_MONTH] = d
                    calendar[Calendar.MONTH] = m
                    calendar[Calendar.YEAR] = y
                    val format = SimpleDateFormat("dd/MM/yyyy")
                    val strDate = format.format(calendar.time)
                    tv_fecha_ex.setText(strDate)
                }, anio, mes, dia
            )
            datePickerDialog.show()
        }

        val formato = SimpleDateFormat("yyyy-MM-dd")
        var fechaDate: Date? = null
        var date = formato.format(Date())
        fechaDate = formato.parse(date);

        //Toast.makeText(this, "la fecha es $date", Toast.LENGTH_SHORT).show()

        var amarillo = sumarRestarDiasFecha(fechaDate, 28)
        var rojo = sumarRestarDiasFecha(fechaDate, 3)

        var rojo2: String = formato.format(rojo.getTime())
        var amarillo2: String = formato.format(amarillo.getTime())

        Toast.makeText(this, "la fecha es $amarillo2", Toast.LENGTH_LONG).show()
        Toast.makeText(this, "la fecha es $rojo2", Toast.LENGTH_LONG).show()
        var fechaProducto = "2020-10-04"
        var fechaProducto2 = "2020-10-29"
        var cod_producto = 1234567

        boton1.setOnClickListener() {
            if (fechaProducto == amarillo2) {
                setPendingIntent()
                setSiPendingIntent()
                setNoPendingIntent()
                CreateNotificationChannel()
                createNotification1(cod_producto)
            }
        }
        boton2.setOnClickListener() {
            if (fechaProducto2 == rojo2) {
                setPendingIntent()
                setSiPendingIntent()
                setNoPendingIntent()
                CreateNotificationChannel()
                createNotification2(cod_producto)
            }
        }

        boton1.setOnClickListener {
            setPendingIntent()
            setSiPendingIntent()
            setNoPendingIntent()
            CreateNotificationChannel()
            createNotification1()
        }
        boton2.setOnClickListener {
            setPendingIntent()
            setSiPendingIntent()
            setNoPendingIntent()
            CreateNotificationChannel()
            createNotification2(cod_producto:String)
        }
    }

    private fun setSiPendingIntent() {
        val intent = Intent(this, AlertasFragment::class.java)
        val stackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addNextIntent(intent)
        siPendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun setNoPendingIntent() {
        val intent = Intent(this@notificaciones, AlertasFragment::class.java)
        val stackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addNextIntent(intent)
        noPendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun setPendingIntent() {
        val intent = Intent(this, AlertasFragment::class.java)
        val stackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addNextIntent(intent)
        pendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun CreateNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "Notificación"
            val descripcion = "Descripcion"
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                name,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.description = descripcion
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun createNotification() {
        val builder = NotificationCompat.Builder(
            applicationContext, CHANNEL_ID
        )
        builder.setSmallIcon(R.drawable.notification_green)
        builder.setContentTitle("Estado")
        builder.setContentText("El producto 12345 está normal")
        builder.color = Color.GREEN
        builder.priority = NotificationCompat.PRIORITY_HIGH
        builder.setCategory(NotificationCompat.CATEGORY_MESSAGE)
        builder.setLights(Color.GREEN, 1000, 1000)
        builder.setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND)
        builder.setContentIntent(pendingIntent)
        builder.addAction(R.drawable.notification_green, "Tomar la alerta", siPendingIntent)
        builder.addAction(R.drawable.notification_green, "Dejar para despues", noPendingIntent)
        val notificationManagerCompat = NotificationManagerCompat.from(
            applicationContext
        )
        notificationManagerCompat.notify(NOTIFICACTION_ID, builder.build())
    }

    private fun createNotification1(cod_producto:Int){
        val builder = NotificationCompat.Builder(
            applicationContext, CHANNEL_ID
        )
        builder.setSmallIcon(R.drawable.notification_yellow)
        builder.setContentTitle("Alerta")
        //builder.setContentText("Faltan 3 semanas para que el producto 12345 caduzca")
        builder.setContentText("Faltan 3 semanas para que el producto $cod_producto caduzca")
        builder.color = Color.YELLOW
        builder.priority = NotificationCompat.PRIORITY_HIGH
        builder.setCategory(NotificationCompat.CATEGORY_MESSAGE)
        builder.setLights(Color.YELLOW, 1000, 1000)
        builder.setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND)
        builder.setContentIntent(pendingIntent)
        builder.addAction(R.drawable.notification_yellow, "Tomar la alerta", siPendingIntent)
        builder.addAction(R.drawable.notification_yellow, "Dejar para despues", noPendingIntent)
        val notificationManagerCompat = NotificationManagerCompat.from(
            applicationContext
        )
        notificationManagerCompat.notify(NOTIFICACTION_ID, builder.build())
    }

    private fun createNotification2(cod_producto:Int) {
        val builder = NotificationCompat.Builder(
            applicationContext, CHANNEL_ID
        )
        builder.setSmallIcon(R.drawable.notification_red)
        builder.setContentTitle("¡Peligro!")
        builder.setContentText("Faltan 3 dias para que el producto $cod_producto caduzca")
        builder.color = Color.RED
        builder.priority = NotificationCompat.PRIORITY_HIGH
        builder.setCategory(NotificationCompat.CATEGORY_MESSAGE)
        builder.setLights(Color.RED, 1000, 1000)
        builder.setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND)
        builder.setContentIntent(pendingIntent)
        builder.addAction(R.drawable.notification_red, "Tomar la alerta", siPendingIntent)
        builder.addAction(R.drawable.notification_red, "Dejar para despues", noPendingIntent)
        val notificationManagerCompat = NotificationManagerCompat.from(
            applicationContext
        )
        notificationManagerCompat.notify(NOTIFICACTION_ID, builder.build())
    }

    open fun eliminarNotify(tag: String) {
    WorkManager.getInstance(this).cancelAllWorkByTag(tag)
    }
    private fun generateKey(): String? {
    return UUID.randomUUID().toString()
    }
    private fun GuardarData(titulo: String, detalle: String, id_noti: Int): Data? {
    return Data.Builder()
        .putString("titulo", titulo)
        .putString("detalle", detalle)
        .putInt("id_noti", id_noti).build()
    }

    private fun getDateRojo(): String? {
    //Desplegamos la fecha
    val cal = Calendar.getInstance()
    cal.time = Date()
    //Le cambiamos el dia rojo 3 dias
    cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 3);
    val fecha = cal.getTime();



    val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    val date = Date()*/

    /*return fecha.toString()
    }
    private fun getDateAmarillo(): String? {
    //Desplegamos la fecha
    val cal = Calendar.getstance()
    cal.time = Date()
    //Le cambiamos el dia amarillo 4 semanas 4*7=28
    cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 28);
    val fecha = cal.getTime();
    return fecha.toString()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_notificaciones)

    if(getCaducidad = 3){
        getDateAmarillo()
        val fecha = getDateRojo()
        val fecha2 = fecha!!.toInt()

        val tag = generateKey()
        val alertTime = fecha2 - SystecurrentTimeMillis()
        //val alertTime = fecha2 - System.currentTimeMillis()
        val random = (Math.random() * 50 + 1).toInt()

        val data = GuardarData("Notificación workmanager", "soy un detalle", random)

        notificaciones2.GuardarNoti(alertTime, data, tag)
    }else if(getCaducidad = 2){
        getDateRojo()
        val fecha = getDateAmarillo()
        val fecha2 = fecha!!.toInt()

    val tag = generateKey()
    val alertTime = fecha2 - System.currentTimeMillis()
    val random = (Math.random() * 50 + 1).toInt()

    val data = GuardarData("Notificación workmanager", "soy un detalle", random)

    notificaciones2.GuardarNoti(alertTime, data, tag)
    }

    boton1.setOnClickListener {
    val fecha = getDateAmarillo()
    val fecha2 = fecha!!.toInt()

    val tag = generateKey()
    val alertTime = fecha2 - System.currentTimeMillis()
    val random = (Math.random() * 50 + 1).toInt()

    val data = GuardarData("Notificación workmanager", "soy un detalle", random)

    notificaciones2.GuardarNoti(alertTime, data, tag)
    }
*/
}
