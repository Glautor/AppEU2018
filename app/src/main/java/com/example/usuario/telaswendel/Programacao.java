package com.example.usuario.telaswendel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Programacao extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    List<TextView> textViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programacao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        TextView t1 = (TextView) findViewById(R.id.textView2);
        TextView t2 = (TextView) findViewById(R.id.textView3);
        TextView t3 = (TextView) findViewById(R.id.textView4);
        textViews = new ArrayList<TextView>();
        textViews.add(t1);
        textViews.add(t2);
        textViews.add(t3);


//        new CarregaProgramacao().execute();
//
//        setContentView(R.layout.activity_programacao);
//
//        ListView listaDeProgramacao = (ListView) findViewById(R.id.lista_prog);
//
//        List<Programacao> programacao = todosAsProgramacoes();
//
//        ListView listaDeProgramacao = (ListView) findViewById(R.id.lista);
//
//        //métodos

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.programacao, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            final Activity activity = this;
            IntentIntegrator integrator = new IntentIntegrator(activity);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
            integrator.setPrompt("Camera Scan");
            integrator.setCameraId(0);
            integrator.initiateScan();

        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {



        } else if(id == R.id.nav_share){

            Intent intent = new Intent(getApplicationContext(), Areas.class);
            startActivity(intent);


        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(getApplicationContext(), Config.class);
            startActivity(intent);



        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public class CarregaProgramacao extends AsyncTask<Void, Void, List<Resumo>> {
        private ProgressDialog load;

        @Override
        protected List<Resumo> doInBackground(Void... params){
            BufferedReader reader = null;
            StringBuffer buffer = new StringBuffer();

            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "database-name").build();

            Resumo inicialR =  db.resumoDao().findByName("“A GUERRA POR OUTROS MEIOS”: AS TRANSFORMAÇÕES SOCIAIS DO CRIME EM FORTALEZA.");
            Resumo finalR = db.resumoDao().findByName("AVALIAÇÃO DA QUALIDADE DA ÁGUA COM PRÁTICAS DIDÁTICAS UTILIZANDO KITS DE BAIXO CUSTO: ESTUDO DE CASO EM FORQUILHA, CEARÁ.");
            if(inicialR != null && finalR != null){
                int ids[] = {1,2,3};
                List<Resumo> resumos = db.resumoDao().loadAllByIds(ids);
                return  resumos;
            }else{
                try{
                    InputStream inputStream = getResources().openRawResource(R.raw.programacao);
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    String linha = "";
                    while ((linha = reader.readLine()) != null) {
                        buffer.append(linha);

                    }
                    reader.close();
                    JSONObject jsonObj = new JSONObject(buffer.toString());
                    JSONArray array = jsonObj.getJSONArray("resumos");
                    for(int i =0;i<array.length();i++) {
                        JSONObject objArray = array.getJSONObject(i);
                        String titulo = objArray.getString("Res_Titulo");
                        String bolsista_nome = objArray.getString("Res_BolPrinc");
                        String hora = objArray.getString("Res_Hora");
                        String minuto;
                        if(objArray.getString("Res_Minut") == null) {
                            minuto = objArray.getString("Res_Minut");
                        }else{
                            minuto = "00";
                        }

                        String dia = objArray.getString("Res_Dia");
                        Resumo resumo = new Resumo(titulo,bolsista_nome,hora,minuto,dia);
                        db.resumoDao().insertAll(resumo);

                    }
                    int ids[] = {1,2,3};
                    List<Resumo> resumos = db.resumoDao().loadAllByIds(ids);
                    return  resumos;
                }catch (Exception e){
                    e.printStackTrace();
                    if(reader != null){
                        try {
                            reader.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
            List<Resumo> resumos = null;
            return resumos;

        }

        @Override
        protected void onPostExecute(List<Resumo> param) {
            if(param != null){
                for(int i=0;i<param.size();i++){
                    TextView t = textViews.get(i);
                    t.setText(param.get(i).getTitulo());
                }
            }

            load.dismiss();
        }
        @Override
        protected void onPreExecute(){
            load = ProgressDialog.show(Programacao.this, "Por favor Aguarde ...", "Recuperando a programação...");
        }

    }


}
