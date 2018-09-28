package br.ufc.prograd.cgpa.euufc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

public class AdaptadorP extends BaseAdapter {

    private static LayoutInflater inflater = null;

    Context contexto;
    List<Programacao> programacao;


    public AdaptadorP(Context contexto, List<Programacao> checkins){
        this.contexto = contexto;
        this.programacao = checkins;

        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return programacao.size();
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

        final View view = inflater.inflate(R.layout.prog_elemento_lista,null);
        TextView tema = (TextView) view.findViewById(R.id.temaLabel);
        TextView hora = (TextView) view.findViewById(R.id.horaLabel);
        TextView local = (TextView) view.findViewById(R.id.localLabel);

        tema.setText(programacao.get(position).getTema());
        hora.setText(programacao.get(position).getHorario());
        local.setText(programacao.get(position).getLocal());

//        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//
//        checkin.setText(fmt.format(checkins.get(position).getDHourIn()));
//
//        if(checkins.get(position).getDHourOut() != null){
//            checkout.setText(fmt.format(checkins.get(position).getDHourOut()));
//        }else{
//            checkout.setText("Por fazer");
//        }
//
//        if(checkins.get(position).isAtServidor() == false){
//            servidor.setImageResource(R.drawable.nao_ok_serv);
//        }else{
//            servidor.setImageResource(R.drawable.ok_serv);
//        }
        return view;
    }
}
