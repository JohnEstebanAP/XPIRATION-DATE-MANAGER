package com.johnestebanap.xpiration_date_manager.ui.alertas

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.johnestebanap.xpiration_date_manager.R
import androidx.lifecycle.ViewModelProviders
import com.johnestebanap.xpiration_date_manager.database.DatabaseAccess
import kotlinx.android.synthetic.main.fragment_alertas.*


class AlertasFragment : Fragment() {

    private lateinit var alertasViewModel: AlertasFragmentViewModel
    private lateinit var adapter: RecyclerAdapter
    private lateinit var items: List<ItemList>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        alertasViewModel =
            ViewModelProviders.of(this).get(AlertasFragmentViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_alertas, container, false)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initValues()

        //val intent = Intent(this@AlertasFragment.context, PrincipalNotificaciones::class.java)
        //startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val ft: FragmentTransaction = requireFragmentManager().beginTransaction()
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false)
        }
        ft.detach(this).attach(this).commit()
    }

    fun initValues() {
        var manager: LinearLayoutManager = LinearLayoutManager(activity?.applicationContext)
        rvLista.setLayoutManager(manager)

        items = getItems();
        adapter = RecyclerAdapter(items)
        rvLista.setAdapter(adapter)
    }

    fun getItems(): List<ItemList> {

        val databaseAccess = DatabaseAccess.getInstance(activity?.applicationContext)
        databaseAccess.open()
        val cantidadDatos = databaseAccess.cantidadAlertas2()
        val consultaregistro = databaseAccess.getAllHistorialAlertas(6, cantidadDatos)

        databaseAccess.close()

        val itemLists = ArrayList<ItemList>();

        for (i in 0 until cantidadDatos) {
            itemLists.add(
                ItemList(
                    consultaregistro[i][0],
                    consultaregistro[i][1],
                    consultaregistro[i][2],
                    consultaregistro[i][3],
                    consultaregistro[i][4],
                    consultaregistro[i][5]
                )
            )
        }
        return itemLists
    }
}



