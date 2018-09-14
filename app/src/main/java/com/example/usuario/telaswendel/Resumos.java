package com.example.usuario.telaswendel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Resumos extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //    List<TextView> textViews = new ArrayList<TextView>();
    ArrayList<String> dados = new ArrayList();
    ArrayAdapter<String> adapter;
    ArrayList<String> value = new ArrayList<>();
    public static final String LOGIN_ARQUIVO = "ArquivoLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        TextView textNome = (TextView) header.findViewById(R.id.textNomeNav);
        SharedPreferences infoUser = getSharedPreferences(LOGIN_ARQUIVO,0);
        textNome.setText(infoUser.getString("nome","@aluno"));

        String[] resumos = getResources().getStringArray(R.array.resumos);

        ListView listview = (ListView) findViewById(R.id.listViewProg);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, resumos);

        listview.setAdapter(adapter);

//        new CarregaProgramacao().execute();


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

            Intent intent = new Intent(getApplicationContext(), Programacao.class);
            startActivity(intent);


        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(getApplicationContext(), Config.class);
            startActivity(intent);



        } else if (id == R.id.nav_send) {
            SharedPreferences.Editor prefsEditor = getSharedPreferences(LOGIN_ARQUIVO, 0).edit();
            prefsEditor.clear();
            prefsEditor.commit();

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);

//            intent.putExtra("finish", true);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK); // To clean up all activities
            startActivity(intent);
            this.finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



//    public class CarregaProgramacao extends AsyncTask<Void, Void, List<Resumo>> {
//        private ProgressDialog load;
//
//        @Override
//        protected List<Resumo> doInBackground(Void... params){
//            BufferedReader reader = null;
//            StringBuffer buffer = new StringBuffer();
//
//            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
//                    AppDatabase.class, "database-name").build();
//
//            Resumo inicialR =  db.resumoDao().findByName("“A GUERRA POR OUTROS MEIOS”: AS TRANSFORMAÇÕES SOCIAIS DO CRIME EM FORTALEZA.");
//            Resumo finalR = db.resumoDao().findByName("AVALIAÇÃO DA QUALIDADE DA ÁGUA COM PRÁTICAS DIDÁTICAS UTILIZANDO KITS DE BAIXO CUSTO: ESTUDO DE CASO EM FORQUILHA, CEARÁ.");
//            if(inicialR != null && finalR != null){
//
//                List<Resumo> resumos = db.resumoDao().getAll();
//
//                if (resumos != null) {
//                    for (int i = 0; i < finalR.getId(); i++) {
//                        dados.add(resumos.get(i).getTitulo());
//                    }
//                }
//
//                return  resumos;
//            }else{
//                try{
//                    InputStream inputStream = getResources().openRawResource(R.raw.programacao);
//                    reader = new BufferedReader(new InputStreamReader(inputStream));
//                    String linha = "";
//                    while ((linha = reader.readLine()) != null) {
//                        buffer.append(linha);
//
//                    }
//                    reader.close();
//                    JSONObject jsonObj = new JSONObject(buffer.toString());
//                    JSONArray array = jsonObj.getJSONArray("resumos");
//                    for(int i =0;i<array.length();i++) {
//                        JSONObject objArray = array.getJSONObject(i);
//                        String titulo = objArray.getString("Res_Titulo");
//                        String bolsista_nome = objArray.getString("Res_BolPrinc");
//                        String hora = objArray.getString("Res_Hora");
//                        String minuto;
//                        if(objArray.getString("Res_Minut") == null) {
//                            minuto = objArray.getString("Res_Minut");
//                        }else{
//                            minuto = "00";
//                        }
//
//                        String dia = objArray.getString("Res_Dia");
//                        Resumo resumo = new Resumo(titulo,bolsista_nome,hora,minuto,dia);
//                        db.resumoDao().insertAll(resumo);
//
//                    }
//
//
//                    List<Resumo> resumos = db.resumoDao().getAll();
//
//                    if (resumos != null) {
//                        for (int i = 0; i < finalR.getId(); i++) {
//                            dados.add(resumos.get(i).getTitulo());
//                        }
//                    }
//
//                    return  resumos;
//                }catch (Exception e){
//                    e.printStackTrace();
//                    if(reader != null){
//                        try {
//                            reader.close();
//                        } catch (IOException e1) {
//                            e1.printStackTrace();
//                        }
//                    }
//                }
//            }
//            List<Resumo> resumos = null;
//            return resumos;
//
//        }
//
//        @Override
//        protected void onPostExecute(List<Resumo> param) {
//            if(param != null){
//
//            }
//
//            load.dismiss();
//        }
//        @Override
//        protected void onPreExecute(){
//            load = ProgressDialog.show(Resumos.this, "Por favor Aguarde ...", "Recuperando a programação... isso pode levar até 1 minuto");
//        }
//
//    }
//
//    public class CarregaResumos extends AsyncTask<Void, Void, ArrayList<String>> {
//        private ProgressDialog load;
//
//        @Override
//        protected ArrayList<String> doInBackground(Void... voids) {
//
//            // Create URL
//            URL githubEndpoint = null;
//            try {
//                githubEndpoint = new URL("http://sysprppg.ufc.br/eu/2018/Resumos/api/alunos/06905756377");
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//
//            // Create connection
//            HttpURLConnection myConnection =
//                    null;
//            try {
//                myConnection = (HttpURLConnection) githubEndpoint.openConnection();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            myConnection.setRequestProperty("User-Agent", "my-rest-app-v0.1");
//            myConnection.setRequestProperty("Accept",
//                    "application/vnd.github.v3+json");
//            myConnection.setRequestProperty("Contact-Me",
//                    "hathibelagal@example.com");
//
//            try {
//                if (myConnection.getResponseCode() == 200) {
//                    // Success
//                    // Further processing here
//                    InputStream responseBody = myConnection.getInputStream();
//                    InputStreamReader responseBodyReader =
//                            new InputStreamReader(responseBody, "UTF-8");
//
//                    JsonReader jsonReader = new JsonReader(responseBodyReader);
//                    jsonReader.beginObject(); // Start processing the JSON object
//                    while (jsonReader.hasNext()) { // Loop through all keys
//                        String key = jsonReader.nextName(); // Fetch the next key
//                        if (key.equals("nome")) { // Check if desired key
//                            // Fetch the value as a String
//                            value.add(jsonReader.nextString());
//
//                            // Do something with the value
//                            // ...
//
//                            jsonReader.close();
//                            myConnection.disconnect();
//
//                            return value;
////                            break; // Break out of the loop
//                        } else {
//                            jsonReader.skipValue(); // Skip values of other keys
//                            return null;
//                        }
//                    }
//
//                } else {
//                    // Error handling code goes here
////                    myConnection.disconnect();
//
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return value;
//        }
//
//
//        @Override
//        protected void onPostExecute(ArrayList<String> param) {
//            if(param == null){
//                Toast.makeText(getApplicationContext(), "Erro", Toast.LENGTH_SHORT).show();
//            }else{
//                for (int i = 0; i < param.size(); i++) {
////                    Toast.makeText(getApplicationContext(), param.get(i), Toast.LENGTH_SHORT).show();
//                    dados.add(param.get(i));
//                }
//            }
//
////            load.dismiss();
//        }
//        @Override
//        protected void onPreExecute(){
////            load = ProgressDialog.show(Resumos.this, "Por favor Aguarde ...", "Recuperando resumos...");
//        }
//
//    }
}
