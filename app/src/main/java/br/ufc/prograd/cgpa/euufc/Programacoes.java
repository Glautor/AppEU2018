package br.ufc.prograd.cgpa.euufc;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;


public class Programacoes extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String LOGIN_ARQUIVO = "ArquivoLogin";
    NavigationView navigationView;
    //ListView listViewP;
    public Spinner sp;
    public String[] datas = {"24/10", "25/10", "26/10","27/10","Atividades Permantes"};
    TextView txProg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programacao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, datas);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        txProg.setMovementMethod(new ScrollingMovementMethod());

        txProg = (TextView) findViewById(R.id.textoProgramacao);
        txProg.canScrollHorizontally(0);
        sp = (Spinner) findViewById(R.id.spinner);
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("24/10")){
                    txProg.setText(R.string.prog_dia_24);
                }
                if(parent.getItemAtPosition(position).equals("25/10")){
                    txProg.setText(R.string.prog_dia_25);
                }
                if(parent.getItemAtPosition(position).equals("26/10")){
                    txProg.setText(R.string.prog_dia_26);
                }
                if(parent.getItemAtPosition(position).equals("27/10")){
                    txProg.setText(R.string.prog_dia_27);
                }
                if(parent.getItemAtPosition(position).equals("Atividades Permantes")){
                    txProg.setText(R.string.ativ_perm);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //listViewP = (ListView) findViewById(R.id.listViewP);

        //CarregaProgramacao carregaProgramacao = new CarregaProgramacao();

        //carregaProgramacao.execute();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view_programacao);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        TextView textNome = (TextView) header.findViewById(R.id.textNomeNav);
        SharedPreferences infoUser = getSharedPreferences(LOGIN_ARQUIVO,0);
        textNome.setText(infoUser.getString("nome","@aluno"));


//        ListView listView = findViewById(R.id.lvProg);
//        listView.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });
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
    protected void onStart() {
        super.onStart();
        Menu drawer_menu = navigationView.getMenu();
        MenuItem menuItem;
        menuItem = drawer_menu.findItem(R.id.nav_share);
        if(!menuItem.isChecked())
        {
            menuItem.setChecked(true);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gallery) {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(getApplicationContext(), Resumos.class);
            startActivity(intent);

        } else if(id == R.id.nav_share){


        } else if (id == R.id.nav_send) {
            SharedPreferences.Editor prefsEditor = getSharedPreferences(LOGIN_ARQUIVO, 0).edit();
            prefsEditor.clear();
            prefsEditor.commit();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
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


//    class CarregaProgramacao extends AsyncTask<Void, Void, List<Programacao>>{
//        @Override
//        protected List<Programacao> doInBackground(Void... voids) {
//            Programacao programacao = new Programacao("Estilo", "Comunicação", "4:00", "24", "Encontros", "Fulano", "ICA");
//
//            AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-programacao").build();
//            db.programacaoDAO().insertAll(programacao);
//            List<Programacao> lista_programacao = db.programacaoDAO().getAll();
//
//
//            return lista_programacao;
//        }
//
//        @Override
//        protected void onPreExecute(){
//            //Codigo
//        }
//
//        @Override
//        protected void onPostExecute(List<Programacao> param){
//            listViewP.setAdapter(new AdaptadorP(getApplicationContext(), param));
//        }
//    }

}
