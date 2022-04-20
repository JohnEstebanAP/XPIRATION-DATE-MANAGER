package com.johnestebanap.xpiration_date_manager.funcion_notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.work.*
import androidx.work.WorkerParameters
import com.johnestebanap.xpiration_date_manager.database.DatabaseAccess
import com.johnestebanap.xpiration_date_manager.ui.alertas.AlertasActivity
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess
import com.johnestebanap.xpiration_date_manager.R

class WorkNoti(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    var cod_producto = Array<String>(size = 100, init = { index -> ("") })

    var cod_productoAtendido = Array<String>(size = 100, init = { index -> ("") })
    var bandera = false
    var bandera2 = false
    var bandera3 = false

    private var pendingIntent: PendingIntent? = null
    private var siPendingIntent: PendingIntent? = null
    private var noPendingIntent: PendingIntent? = null
    private val CHANNEL_ID = "NOTIFICATION"
    val NOTIFICACTION_ID = 0

    var fechaDate: Date? = null

    fun setSiPendingIntent(codigo: String) {
        val intent = Intent(applicationContext, AlertasActivity::class.java).apply {
            putExtra("codigo", codigo)
        }
        val stackBuilder = TaskStackBuilder.create(applicationContext)
        stackBuilder.addNextIntent(intent)
        siPendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    fun setNoPendingIntent() {
        val stackBuilder = TaskStackBuilder.create(applicationContext)
        noPendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    fun setPendingIntent(codigo: String) {
        val intent = Intent(applicationContext, AlertasActivity::class.java).apply {
            putExtra("codigo", codigo)
        }
        val stackBuilder = TaskStackBuilder.create(applicationContext)
        stackBuilder.addParentStack(AlertasActivity::class.java)
        stackBuilder.addNextIntent(intent)
        pendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT)

    }

    fun sumarRestarDiasFecha(fecha: Date, dias: Int): Date {

        var calendar = Calendar.getInstance()
        calendar.time = fecha // Configuramos la fecha que se recibe

        calendar.add(
            Calendar.DAY_OF_YEAR,
            -dias
        ) // numero de días a añadir, o restar en caso de días<0

        return calendar.time // Devuelve el objeto Date con los nuevos días añadidos
    }

    override fun doWork(): Result {
/*
        val currentDate = Calendar.getInstance()
        val dueDate = Calendar.getInstance()
        // Set Execution around 05:00:00 AM
        dueDate.set(Calendar.HOUR_OF_DAY, 11)
        dueDate.set(Calendar.MINUTE, 30)
        dueDate.set(Calendar.SECOND, 0)
   /*     if (dueDate.before(currentDate)) {
            dueDate.add(Calendar.HOUR_OF_DAY, 24)
        }*/
        val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis
        val dailyWorkRequest = OneTimeWorkRequestBuilder<WorkNoti>()
            .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
            .addTag("tag2")
            .build()
        WorkManager.getInstance(applicationContext)
            .enqueue(dailyWorkRequest)

*/
        val titulo = inputData.getString("titulo")
        val detalle = inputData.getString("detalle")
        val id = inputData.getLong("idnoti", 0).toInt()

        //aqui ba la notificacion---------

        val formato = SimpleDateFormat("yyyy-MM-dd")

        var date = formato.format(Date())
        fechaDate = formato.parse(date);

        val databaseAccess = DatabaseAccess.getInstance(applicationContext)
        databaseAccess.open()
        var cantidadN = databaseAccess.CantidadNotificaciones2()
        var cantidad = databaseAccess.totalProductos()

        var fechas = databaseAccess.fechaCaducidad(cantidad)

        var fechasAmarillas = Array<Date>(size = cantidad, init = { index -> Date("1/1/2000") })
        var fechasRojas = Array<Date>(size = cantidad, init = { index -> Date("1/1/2000") })
        var fechas2 = Array<Date>(size = cantidad, init = { index -> Date("1/1/2000") })

        for (i in 0 until cantidad) {
            fechas2[i] = formato.parse(fechas[i][0])
            fechasAmarillas[i] = sumarRestarDiasFecha(fechas2[i], 28)
            fechasRojas[i] = sumarRestarDiasFecha(fechas2[i], 3)

        }

        var fechaprueba = "2020-11-17"
        var fechapruebaA = "2020-10-04"
        var fechaprueba2 = formato.parse(fechaprueba)
        var fechapruebaA2 = formato.parse(fechapruebaA)

        cod_productoAtendido = databaseAccess.SelectCodProducto()
        var j = 0

        for (i in 0 until cantidad) {
            bandera = false
            cod_producto[i] = fechas[i][1]
            if (fechasAmarillas[i] == fechapruebaA2) { //Amarilla
                //bandera = true
                j = 0
                do {
                    if (cod_productoAtendido.isNotEmpty()) {
                        if (cod_producto[i].equals(cod_productoAtendido[j])) {
                            bandera = false
                            break
                        } else {
                            bandera = true
                        }
                    } else {
                        bandera = true
                        break
                    }
                    j++
                } while (j < 3)

                if (bandera) {

                    pendings(cod_producto[i])
                    oreo(titulo!!, detalle!!, fechas[i][1])

                }
            } else if (fechasRojas[i] == fechaprueba2) { //Roja
                //bandera = true

                j = 0
                do {
                    if (cod_productoAtendido.isNotEmpty()) {
                        if (cod_producto[i].equals(cod_productoAtendido[j])) {
                            bandera = false
                            break
                        } else {
                            bandera = true
                        }
                    } else {
                        bandera = true
                        break
                    }
                    j++
                } while (j < 3)

                /*for (j in 0 until cod_productoAtendido.size) {
                    //var producto1 = cod_producto[i]
                    //var producto2 = cod_productoAtendido[j]

                    if (cod_productoAtendido.isNotEmpty()) {
                        if (cod_producto[i].equals(cod_productoAtendido[j]))/*cod_producto[i].equals("6") || cod_producto[i].equals("20") || cod_producto[i].equals(
                                "5"
                            )*/ {
                            bandera = false
                            break
                        } else {
                            bandera = true
                        }
                    } else {
                        bandera = true
                        break
                    }
                }*/

                if (bandera) {

                    pendings(cod_producto[i])
                    oreo1(titulo!!, detalle!!, fechas[i][1])

                }
            }
        }
        databaseAccess.close()
        //---------------------------------
        return Result.success()
    }

    companion object {
        fun GuardarNoti(data: Data) {

            var tag = "tag1"

            var noti = OneTimeWorkRequest.Builder(WorkNoti::class.java)
                .setInitialDelay(20, TimeUnit.SECONDS).addTag(tag)
                .setInputData(data!!).build()

            val instance1 = WorkManager.getInstance()
            instance1.enqueue(noti)

            /*val currentDate = Calendar.getInstance()
            val dueDate = Calendar.getInstance()
            // Set Execution around 05:00:00 AM
            dueDate.set(Calendar.HOUR_OF_DAY, 4)
            dueDate.set(Calendar.MINUTE, 55)
            dueDate.set(Calendar.SECOND, 0)
            if (dueDate.before(currentDate)) {
                dueDate.add(Calendar.HOUR_OF_DAY, 24)
            }
            val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis
            val dailyWorkRequest = OneTimeWorkRequestBuilder<WorkNoti>()
                .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
                .addTag(tag)
                .build()
            WorkManager.getInstance()
                .enqueue(dailyWorkRequest)*/


            /*var noti = PeriodicWorkRequest.Builder(
                WorkNoti::class.java,
                1,
                TimeUnit.MINUTES,
                40,
                TimeUnit.SECONDS
            )
                //.setInitialDelay(1, TimeUnit.MINUTES)
                .addTag(tag!!)
                .setInputData(data!!)
                .build()
            //   .setConstraints(constraints);

            //  val instance1 = WorkManager.getInstance()
            // instance1.enqueue(noti)

            val instance = WorkManager.getInstance()
            //instance?.enqueueUniquePeriodicWork(tag!!, ExistingPeriodicWorkPolicy.REPLACE, noti)
            instance.enqueueUniquePeriodicWork(tag, ExistingPeriodicWorkPolicy.KEEP, noti)
            */
            /*
            ExistingPeriodicWorkPolicy.REPLACE asegura que si hay trabajo pendiente etiquetado con uniqueWorkName,
            se cancelará y se ejecutará el nuevo trabajo.

            ExistingPeriodicWorkPolicy.KEEP ejecutará el nuevo PeriodicWorkRequest solo si no hay
            trabajo pendiente etiquetado con uniqueWorkName.
            */
        }
    }

    private fun pendings(codigo: String) {
        setSiPendingIntent(codigo)
        //setNoPendingIntent()
        setPendingIntent(codigo)
    }

    private fun oreo(t: String, d: String, codigo: String) {

        val id = "message"
        val nm =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(applicationContext, id)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nc = NotificationChannel(id, "nuevo", NotificationManager.IMPORTANCE_HIGH)
            nc.description = "Notificacion FCM"
            nc.setShowBadge(true)
            assert(nm != null)
            nm.createNotificationChannel(nc)
        }

        builder.setAutoCancel(true)
        builder.setSmallIcon(R.drawable.notification_yellow)
        builder.setContentTitle("Alerta")
        builder.setContentText("Faltan 4 semanas para que el producto $codigo caduzca")
        builder.color = Color.YELLOW
        builder.priority = NotificationCompat.PRIORITY_HIGH
        builder.setCategory(NotificationCompat.CATEGORY_MESSAGE)
        builder.setLights(Color.YELLOW, 1000, 1000)
        builder.setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND)
        builder.setContentIntent(pendingIntent)
        builder.addAction(
            R.drawable.notification_yellow,
            "Tomar la alerta",
            siPendingIntent
        )
        /*builder.addAction(
            R.drawable.notification_yellow,
            "Dejar para despues",
            noPendingIntent
        )*/

        val random = Random()
        val idNotify = random.nextInt(8000)

        assert(nm != null)
        nm.notify(idNotify, builder.build())

        val databaseAccess = DatabaseAccess.getInstance(applicationContext)
        databaseAccess.open()
        var productosRepetidos = databaseAccess.SelectCodProductoRepetido("3")
        var cantidadRepetidos = databaseAccess.cantidadCodProductoRepetido("3")

        for (j in 0 until cantidadRepetidos) {
            if (codigo.equals(productosRepetidos[j][0])) {
                bandera2 = true
                break
            } else {
                bandera2 = false
            }
        }
        if (bandera2) {

        } else {
            databaseAccess.insertHistorialAlertas(
                idNotify.toString(),
                fechaDate.toString(),
                codigo,
                "3",
                "2"
            )
        }

        databaseAccess.close()
    }

    private fun oreo1(t: String, d: String, codigo: String) {

        val id = "message"
        val nm =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(applicationContext, id)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nc = NotificationChannel(id, "nuevo", NotificationManager.IMPORTANCE_HIGH)
            nc.description = "Notificacion FCM"
            nc.setShowBadge(true)
            assert(nm != null)
            nm.createNotificationChannel(nc)
        }

        builder.setAutoCancel(true)
        builder.setSmallIcon(R.drawable.notification_red)
        builder.setContentTitle("¡Peligro!")
        builder.setContentText("Faltan 3 dias para que el producto $codigo caduzca")
        builder.color = Color.RED
        builder.priority = NotificationCompat.PRIORITY_HIGH
        builder.setCategory(NotificationCompat.CATEGORY_MESSAGE)
        builder.setLights(Color.RED, 1000, 1000)
        builder.setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND)
        builder.setContentIntent(pendingIntent)
        builder.addAction(
            R.drawable.notification_red,
            "Tomar la alerta",
            siPendingIntent
        )
        /*builder.addAction(
            R.drawable.notification_red,
            "Dejar para despues",
            noPendingIntent
        )*/

        val random = Random()
        val idNotify = random.nextInt(8000)

        assert(nm != null)
        nm.notify(idNotify, builder.build())

        val databaseAccess = DatabaseAccess.getInstance(applicationContext)
        databaseAccess.open()

        var productosRepetidosAmarillos = databaseAccess.SelectCodProductoRepetido("3")
        var productosRepetidosRojos = databaseAccess.SelectCodProductoRepetido("2")
        var cantidadRepetidosAmarillos = databaseAccess.cantidadCodProductoRepetido("3")
        var cantidadRepetidosRojos = databaseAccess.cantidadCodProductoRepetido("2")
        var posicion = ""

        for (j in 0 until cantidadRepetidosAmarillos) {
            if (codigo.equals(productosRepetidosAmarillos[j][0])) {
                posicion = productosRepetidosAmarillos[j][1]
                bandera2 = true
                break
            } else {
                bandera2 = false
            }
        }
        if (bandera2) {
            databaseAccess.updateCaducidadAlertas(posicion)
        } else {
            for (j in 0 until cantidadRepetidosRojos) {
                if (codigo.equals(productosRepetidosRojos[j][0])) {
                    bandera3 = true
                    break
                } else {
                    bandera3 = false
                }
            }
            if (bandera3) {

            } else {
                databaseAccess.insertHistorialAlertas(
                    idNotify.toString(),
                    fechaDate.toString(),
                    codigo,
                    "2",
                    "2"
                )
            }
        }

        databaseAccess.close()
    }
}

