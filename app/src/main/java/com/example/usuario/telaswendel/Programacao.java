package com.example.usuario.telaswendel;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
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

//    List<TextView> textViews = new ArrayList<TextView>();
    ArrayList<String> dados = new ArrayList();
    ArrayAdapter<String> adapter;

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


//        TextView t1 = (TextView) findViewById(R.id.textView2);
//        TextView t2 = (TextView) findViewById(R.id.textView3);
//        TextView t3 = (TextView) findViewById(R.id.textView4);
//        textViews = new ArrayList<TextView>();
//        textViews.add(t1);
//        textViews.add(t2);
//        textViews.add(t3);


        ListView listview = (ListView) findViewById(R.id.listViewProg);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dados);

        listview.setAdapter(adapter);

        new CarregaProgramacao().execute();

        final EditText busca = (EditText) findViewById(R.id.busca);
        busca.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }
        });

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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gallery) {
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
//
//                int ids[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,149,150,151,152,153,154,155,156,157,158,159,160,161,162,163,164,165,166,167,168,169,170,171,172,173,174,175,176,177,178,179,180,181,182,183,184,185,186,187,188,189,190,191,192,193,194,195,196,197,198,199,200,201,202,203,204,205,206,207,208,209,210,211,212,213,214,215,216,217,218,219,220,221,222,223,224,225,226,227,228,229,230,231,232,233,234,235,236,237,238,239,240,241,242,243,244,245,246,247,248,249,250,251,252,253,254,255,256,257,258,259,260,261,262,263,264,265,266,267,268,269,270,271,272,273,274,275,276,277,278,279,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,315,316,317,318,319,320,321,322,323,324,325,326,327,328,329,330,331,332,333,334,335,336,337,338,339,340,341,342,343,344,345,346,347,348,349,350,351,352,353,354,355,356,357,358,359,360,361,362,363,364,365,366,367,368,369,370,371,372,373,374,375,376,377,378,379,380,381,382,383,384,385,386,387,388,389,390,391,392,393,394,395,396,397,398,399,400,401,402,403,404,405,406,407,408,409,410,411,412,413,414,415,416,417,418,419,420,421,422,423,424,425,426,427,428,429,430,431,432,433,434,435,436,437,438,439,440,441,442,443,444,445,446,447,448,449,450,451,452,453,454,455,456,457,458,459,460,461,462,463,464,465,466,467,468,469,470,471,472,473,474,475,476,477,478,479,480,481,482,483,484,485,486,487,488,489,490,491,492,493,494,495,496,497,498,499};
//
////                int ids[] = new int[3];
////
////                ids[1] = 1;
////                ids[2] = 2;
////                ids[3] = 3;
//
////                int ids[] = new int[finalR.getId()];
////                for (int i=1; i<finalR.getId(); i++){
////                    ids[i]=i;
////                }
//
////                int ids[] = new int[5];
////                for (int i = 1; i < 5; i++){
////                    ids[i] = i;
////                }
//
////                ArrayList<Integer> ids = new ArrayList<Integer>();
////
////                ids.add(1);
////                ids.add(2);
////                ids.add(3);
//
//                List<Resumo> resumos = db.resumoDao().loadAllByIds(ids);
//
//                if (resumos != null) {
//                    for (int i = 0; i < 499; i++) {
//                        dados.add(resumos.get(i).getTitulo());
//                    }
//                }

//                return  resumos;
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

//                    int ids[] = new int[4];
//
//                    ids[0] = 0;
//                    ids[1] = 1;
//                    ids[2] = 2;
//                    ids[3] = 3;


//                    int valores[] = new int[finalR.getId()];
//                    for (int i=1; i<finalR.getId(); i++){
//                        valores[i]=i;
//                    }

//                    int[] ids = new int[finalR.getId()];

                    int ids[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,149,150,151,152,153,154,155,156,157,158,159,160,161,162,163,164,165,166,167,168,169,170,171,172,173,174,175,176,177,178,179,180,181,182,183,184,185,186,187,188,189,190,191,192,193,194,195,196,197,198,199,200,201,202,203,204,205,206,207,208,209,210,211,212,213,214,215,216,217,218,219,220,221,222,223,224,225,226,227,228,229,230,231,232,233,234,235,236,237,238,239,240,241,242,243,244,245,246,247,248,249,250,251,252,253,254,255,256,257,258,259,260,261,262,263,264,265,266,267,268,269,270,271,272,273,274,275,276,277,278,279,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,315,316,317,318,319,320,321,322,323,324,325,326,327,328,329,330,331,332,333,334,335,336,337,338,339,340,341,342,343,344,345,346,347,348,349,350,351,352,353,354,355,356,357,358,359,360,361,362,363,364,365,366,367,368,369,370,371,372,373,374,375,376,377,378,379,380,381,382,383,384,385,386,387,388,389,390,391,392,393,394,395,396,397,398,399,400,401,402,403,404,405,406,407,408,409,410,411,412,413,414,415,416,417,418,419,420,421,422,423,424,425,426,427,428,429,430,431,432,433,434,435,436,437,438,439,440,441,442,443,444,445,446,447,448,449,450,451,452,453,454,455,456,457,458,459,460,461,462,463,464,465,466,467,468,469,470,471,472,473,474,475,476,477,478,479,480,481,482,483,484,485,486,487,488,489,490,491,492,493,494,495,496,497,498,499};
//
//                    ArrayList<Integer> ids = new ArrayList<Integer>();
//
//                    ids.add(1);
//                    ids.add(2);
//                    ids.add(3);

                    List<Resumo> resumos = db.resumoDao().loadAllByIds(ids);

                    if (resumos != null) {
                        for (int i = 0; i < 50; i++) {
                            dados.add(resumos.get(i).getTitulo());
                        }
                    }

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

            }

            load.dismiss();
        }
        @Override
        protected void onPreExecute(){
            load = ProgressDialog.show(Programacao.this, "Por favor Aguarde ...", "Recuperando a programação...");
        }

    }


}
