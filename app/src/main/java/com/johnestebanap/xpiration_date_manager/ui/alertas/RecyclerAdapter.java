package com.johnestebanap.xpiration_date_manager.ui.alertas;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.johnestebanap.xpiration_date_manager.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {

    private List<ItemList> items;


    public static class RecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //context de la activity
        Context context;
        private ImageView imgItem;
        private TextView tvTitulo;
        private TextView tvDescripcion;
        CardView cardViewHistorialAlertas;
        TextView tvCodigoHistorialAlertas;
        TextView tvCodigoProducto;
        TextView tvFechaCaducidad;
        View viewEstado;

        public RecyclerHolder(View itemView) {
            super(itemView);

            context = itemView.getContext();
            imgItem = (ImageView) itemView.findViewById(R.id.imgItem);
            tvTitulo = (TextView) itemView.findViewById(R.id.tvTitulo);
            tvDescripcion = (TextView) itemView.findViewById(R.id.tvDescripcion);
            cardViewHistorialAlertas = (CardView) itemView.findViewById(R.id.cardViewHistorialAlertas);
            tvCodigoProducto = (TextView) itemView.findViewById(R.id.tvCodigoProducto);
            tvCodigoHistorialAlertas = (TextView) itemView.findViewById(R.id.tvCodigoHistorialAlertas);
            tvFechaCaducidad = (TextView) itemView.findViewById(R.id.tvFechaAlerta);
        }

        void setOnClickListener() {
            cardViewHistorialAlertas.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, InfoProductoActivity.class);
            intent.putExtra("codigo", tvCodigoProducto.getText());
            intent.putExtra("codigoAlerta", tvCodigoHistorialAlertas.getText());
            context.startActivity(intent);

        }
    }


    public RecyclerAdapter(List<ItemList> items) {
        this.items = items;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_view_historial_alertas, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        ItemList item = items.get(position);
        holder.setOnClickListener();

        String caducidad = item.getCodigoCaducidad();
        String estadoAlerta = item.getEstadoAlerta();
        String estadoCaducidad = null;
        String tomarMedidas = null;

        if (caducidad.equals("2") || caducidad.equals("1")) {
            holder.imgItem.setImageResource(R.drawable.notification_red);
            holder.tvTitulo.setText("¡Producto en rojo!");
            holder.tvTitulo.setTextColor(holder.tvTitulo.getContext().getResources().getColor(R.color.colorRojo));
            estadoCaducidad = "le faltan 3 días para vencerse";
            tomarMedidas = "deberia tomar medidas rapidas";
        } else /*if(caducidad.equals("3"))*/ {
            holder.tvCodigoProducto.setText(item.getCodigohistorial());
            holder.imgItem.setImageResource(R.drawable.notification_yellow);
            holder.tvTitulo.setText("¡Producto en amarillo!");
            holder.tvTitulo.setTextColor(holder.tvTitulo.getContext().getResources().getColor(R.color.colorPrimaryLught1));
            estadoCaducidad = "le faltan 3 Semanas para vencerse";
            tomarMedidas = "deberá prestarle atención a este producto antes de que venza.";
        }

        if (estadoAlerta.equals("1")) {
        } else {

            //holder.viewEstado.setBackgroundResource(R.drawable.style_alerta_sin_tomar);
            //holder.viewEstado.setBackground(holder.viewEstado.getContext().getResources().getDrawable(R.drawable.style_alerta_sin_tomar));
            //rl.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.red));
            //holder.viewEstado.setBackgroundResource(R.color.colorRojo);
            //holder.viewEstado.setBackgroundColor(holder.viewEstado.getContext().getResources().getColor(R.color.colorPrimaryLught1));//holder.viewEstado.setBackgroundResource(R.color.colorVerde);

        }

        holder.tvCodigoProducto.setText(item.getCodigoProducto());
        holder.tvFechaCaducidad.setText(item.getFechaCaducidad());
        String mensaje = "El producto: " + item.getNombreProducto() + " " + estadoCaducidad + ", " + tomarMedidas + ".";

        holder.tvDescripcion.setText(mensaje);
        holder.tvCodigoHistorialAlertas.setText(item.getCodigohistorial());
        //set events
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
