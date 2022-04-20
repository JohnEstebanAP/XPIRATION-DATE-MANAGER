package com.johnestebanap.xpiration_date_manager.funcion_notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.Data
import com.johnestebanap.xpiration_date_manager.R

class PrincipalNotificacionesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal_notificaciones)

        /*
        anio = actual.get(Calendar.YEAR);
        mes = actual.get(Calendar.MONTH);
        dia = actual.get(Calendar.DAY_OF_MONTH);

        calendar[Calendar.DAY_OF_MONTH] = dia
        calendar[Calendar.MONTH] = mes
        calendar[Calendar.YEAR] = anio
        val format = SimpleDateFormat("yyyy/MM/dd")
        val strDate = format.format(calendar.time)
        tv_fecha.setText(strDate)

        hora = actual.get(Calendar.HOUR_OF_DAY)
        minuto = actual.get(Calendar.MINUTE)

        calendar[Calendar.HOUR_OF_DAY] = hora
        calendar[Calendar.MINUTE] = minuto
        tv_hora.setText(String.format("%02d:%02d", hora, minuto))*/

        // val tag = generateKey()
        // val Alerttime = calendar.getTimeInMillis() - System.currentTimeMillis()
        val random = (Math.random() * 50 + 1).toInt()

        val data = GuardarData("Notificacion De prueba", "soy un detalle", random)
        WorkNoti.GuardarNoti(data)
    }

    /*
        private fun generateKey(): String {

            return UUID.randomUUID().toString()
        }*/

    fun GuardarData(titulo: String, detalle: String, id_noti: Int): Data {

        return Data.Builder()
            .putString("titulo", titulo)
            .putString("detalle", detalle)
            .putInt("id_noti", id_noti).build()

    }
}