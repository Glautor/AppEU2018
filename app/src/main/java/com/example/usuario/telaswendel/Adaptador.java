package com.example.usuario.telaswendel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

public class Adaptador extends BaseAdapter {

    private static LayoutInflater inflater = null;

    Context contexto;
    List<Check> checkins;


    public Adaptador(Context contexto, List<Check> checkins){
        this.contexto = contexto;
        this.checkins = checkins;

        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return checkins.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final View view = inflater.inflate(R.layout.elemento_lista,null);
        TextView checkin = (TextView) view.findViewById(R.id.checkinText);
        TextView checkout = (TextView) view.findViewById(R.id.checkoutText);
        ImageView servidor = (ImageView) view.findViewById(R.id.servidorCheck);

        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        checkin.setText(fmt.format(checkins.get(position).getDHourIn()));

        if(checkins.get(position).getDHourOut() != null){
            checkout.setText(fmt.format(checkins.get(position).getDHourOut()));
        }else{
            checkout.setText("Por fazer");
        }

        if(checkins.get(position).isAtServidor() == false){
            servidor.setImageResource(R.drawable.nao_ok_serv);
        }else{
            servidor.setImageResource(R.drawable.ok_serv);
        }
        return view;
    }
}
