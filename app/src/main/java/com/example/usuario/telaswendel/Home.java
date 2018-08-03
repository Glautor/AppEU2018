package com.example.usuario.telaswendel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LocationListener {

    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 0;
    public static final String LOGIN_ARQUIVO = "ArquivoLogin";
    public static final String CONTROLE_CHECK = "ArquivoCheck";
    LocationManager locationManager = null;
    LocationProvider provider = null;
    LocationManager mLocationManager;
    Location myLocation;
    TextView textView1;
    ListView checkView;
    String resultado = "";
    Button checkin;
    String[] dados;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        checkin = (Button) findViewById(R.id.checkin);
        checkView = (ListView) findViewById(R.id.checkView);
        BuscaCheck bc = new BuscaCheck();
        bc.execute();

        GetUsuario gc = new GetUsuario();
        gc.execute();

//        SharedPreferences infoCheck = getSharedPreferences(CONTROLE_CHECK,0);
//        boolean doCheckout = infoCheck.getBoolean("DoCheckout?",false);
//        if(doCheckout == true){
//            checkin.setText("FAZER CHECKOUT");
//        }else{
//            checkin.setText("FAZER CHECK-IN");
//        }
//
//
//        textView1 = (TextView) findViewById(R.id.textView1);
//        int minutos = infoCheck.getInt("Minutos",0);
//        int horas = infoCheck.getInt("Horas",0);
//        if(minutos >= 10) {
//            textView1.setText("Você passou " + horas + ":" + minutos + " nos Encontos Acadêmicos");
//        }else{
//            textView1.setText("Você passou " + horas + ":0" + minutos + " nos Encontos Acadêmicos");
//        }


        final Activity activity = this;

        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Camera Scan");
                integrator.setCameraId(0);
                integrator.initiateScan();
            }
        });


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

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);


                // MY_PERMISSIONS_REQUEST_FINE_LOCATION is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    String providerName = setConfigGPS();


                    // If no suitable provider is found, null is returned.
                    if (providerName != null) {

                    }


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getApplicationContext(),"Não podemos acessar a localização sem sua permissão", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @SuppressLint("MissingPermission")
    protected String setConfigGPS(){
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getProvider(LocationManager.GPS_PROVIDER);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setCostAllowed(false);

        String providerName = locationManager.getBestProvider(criteria, true);


        locationManager.requestSingleUpdate(providerName, this, null);//Updates(providerName,1000,0,this);
        //locationManager.requestLocationUpdates(providerName,1000,0,this);

        myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(myLocation != null) {
            Toast.makeText(getApplicationContext(), String.valueOf(myLocation.getLongitude()), Toast.LENGTH_LONG).show();
        }else{
            myLocation = getLastLocation();
            if(myLocation != null){
                Toast.makeText(getApplicationContext(), String.valueOf(myLocation.getLongitude()), Toast.LENGTH_LONG).show();
            }
        }
        return providerName;
    }

    private Location getLastLocation() {
        mLocationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            @SuppressLint("MissingPermission") Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    @Override
    protected void onStart() {
        super.onStart();

        // This verification should be done during onStart() because the system calls
        // this method when the user returns to the activity, which ensures the desired
        // location provider is enabled each time the activity resumes from the stopped state.

        LocationManager locationManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        BuscaCheck bc = new BuscaCheck();
        bc.execute();

        if (!gpsEnabled) {
            // Build an alert dialog here that requests that the user enable
            // the location services, then when the user clicks the "OK" button,
            Dialog dialog = new AlertDialog.Builder(this)
                    .setMessage("Precisamos que você ligue seu GPS")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            enableLocationSettings();

                        }
                    })
                    .setNegativeButton("Não quero", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    }).create();

            dialog.show();
        }else{
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                String providerName = setConfigGPS();
            }
        }
    }

    private void enableLocationSettings() {
        Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(settingsIntent);
    }

    @Override
    public void onLocationChanged(Location location) {
        @SuppressLint("MissingPermission") Location mylocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Log.d("CHANGED", "LOCATION UPDATED" + String.valueOf(mylocation.getLongitude()));
        //textView1.setText(String.valueOf(location.getLongitude()));
        Toast.makeText(getApplicationContext(),String.valueOf(mylocation.getLongitude()), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }


    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null){
            if(result.getContents() != null){
                alert(result.getContents());
                RealizaCheck rc = new RealizaCheck(result.getContents());
                rc.execute();
            } else{
                RealizaCheck rc = new RealizaCheck("icaEU");
                rc.execute();
                alert("Scan Cancelado");

            }
        } else{
            super.onActivityResult(requestCode, resultCode,data);
        }

    }

    private void alert(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            this.finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }




    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(getApplicationContext(), Programacao.class);
            startActivity(intent);



        } else if(id == R.id.nav_share){
            Intent intent = new Intent(getApplicationContext(), Areas.class);
            startActivity(intent);


        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(getApplicationContext(), Config.class);
            startActivity(intent);


        } else if (id == R.id.nav_send) {
            SharedPreferences.Editor prefsEditor = getSharedPreferences(LOGIN_ARQUIVO, 0).edit();
            prefsEditor.clear();
            prefsEditor.commit();

           SharedPreferences.Editor prefsEditorCheck = getSharedPreferences(CONTROLE_CHECK, 0).edit();
           prefsEditorCheck.clear();
           prefsEditorCheck.commit();

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            this.finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void saveInfoCheckin(boolean checkout, int idCheck){
        SharedPreferences infoLogin = getSharedPreferences(CONTROLE_CHECK,0);
        SharedPreferences.Editor editor = infoLogin.edit();
        editor.putBoolean("DoCheckout?",checkout);
        editor.putInt("LastCheckin",idCheck);

        editor.commit();

        AtualizaUsuario at = new AtualizaUsuario();
        at.execute();

    }

    public void saveInfoCheckin(boolean checkout, int idCheck, Duration duracao){
        SharedPreferences infoCheckin = getSharedPreferences(CONTROLE_CHECK,0);
        int horas = infoCheckin.getInt("Horas",0);
        int minutos = infoCheckin.getInt("Minutos",0);
        SharedPreferences.Editor editor = infoCheckin.edit();
        editor.putBoolean("DoCheckout?",checkout);
        editor.putInt("LastCheckin",idCheck);

        int minutosAdd = (int) duracao.getStandardMinutes() % 60;
        int horasAdd = (int) duracao.getStandardHours();

        int horas_final = horas + horasAdd;
        int minutos_final = minutos + minutosAdd;



        if(minutos_final> 60){
            horas_final = horas_final + (minutos_final/60);
            minutos_final = minutos_final % 60;
        }

        editor.putInt("Horas",horas_final);
        editor.putInt("Minutos",minutos_final);
        editor.commit();


        AtualizaUsuario at = new AtualizaUsuario();
        at.execute();

//        if(minutos >= 10) {
//            textView1.setText("Você passou " + horas + ":" + minutos + " nos Encontos Acadêmicos");
//        }else{
//            textView1.setText("Você passou " + horas + ":0" + minutos + " nos Encontos Acadêmicos");
//        }

    }

    public class RealizaCheck extends AsyncTask<Void, Void, String>{
        private final String qrCode;

        public RealizaCheck(String qC){
            this.qrCode = qC;
        }

        @Override
        protected String doInBackground(Void... params){

            SharedPreferences infoCheck = getSharedPreferences(CONTROLE_CHECK,0);
            boolean doCheckout = infoCheck.getBoolean("DoCheckout?",false);
            if(doCheckout == true){
                if(qrCode != null){
                    if(qrCode.equals("icaEU")){
                        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                                AppDatabase.class, "database-name").build();

                        SharedPreferences infoLogin = getSharedPreferences(LOGIN_ARQUIVO,0);
                        int id = infoLogin.getInt("id",-1);

                        User user = db.userDao().findById(id);
                        Date date = new Date();
                        System.out.println("Data:"+ date);
                        //Salva o checkout no BD e move o registro de id do Check nas preferências para -1 (para que posteriormente venha a ser um valor de id válido do banco)
                        int cid = infoCheck.getInt("LastCheckin", -1);
                        db.checkDao().updateCheckOut(date,cid);
                        Check check = db.checkDao().loadById(cid);

                        DateTime horaInicial = new DateTime(check.getDHourIn());
                        DateTime horaFinal = new DateTime(check.getDHourOut());
                        Duration duracao = new Duration(horaInicial, horaFinal);

                        saveInfoCheckin(false,-1, duracao);

                        return "checkout";
                    }
                }
            }else{
                if(qrCode != null){
                    if(qrCode.equals("icaEU")){
                        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                                AppDatabase.class, "database-name").build();

                        SharedPreferences infoLogin = getSharedPreferences(LOGIN_ARQUIVO,0);
                        int id = infoLogin.getInt("id",-1);
                        User user = db.userDao().findById(id);
                        Date date = new Date();
                        System.out.println("Data:"+ date);
                        Check check = new Check(user.getId(),date,false);
                        //Salva i checkin no BD e move o registro de id do Check nas preferências para o valor do id do checkin atual
                        // (desse modo, na hora do checkout teremos como atualizar facilmente pois temos a info de id salva)
                        db.checkDao().insertAll(check);
                        saveInfoCheckin(true,db.checkDao().loadIdByHourIn(date));
                        return "checkin";
                    }
                }
            }
            return "falha";
        }

        @Override
        protected void onPostExecute(String param) {
            if(param.equals("checkin")) {
                Toast.makeText(getApplicationContext(), "Checkin realizado com sucesso", Toast.LENGTH_LONG).show();
                checkin.setText("FAZER CHECKOUT");
            }
            if(param.equals("checkout")){
                Toast.makeText(getApplicationContext(), "Checkout realizado com sucesso", Toast.LENGTH_LONG).show();
                SharedPreferences infoCheck = getSharedPreferences(CONTROLE_CHECK,0);
                int horas = infoCheck.getInt("Horas",0);
                int minutos = infoCheck.getInt("Minutos",0);
                if(minutos >= 10) {
                    textView1.setText("Você passou " + horas + ":" + minutos + " nos Encontos Acadêmicos");
                }else{
                    textView1.setText("Você passou " + horas + ":0" + minutos + " nos Encontos Acadêmicos");
                }
                checkin.setText("FAZER CHECK-IN");
            }
            if(param.equals("falha")){
                Toast.makeText(getApplicationContext(), "Check não realizado", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute(){

        }



    }

    public class BuscaCheck extends AsyncTask<Void, Void, List<Check>>{


        @Override
        protected List<Check> doInBackground(Void... params){
            List<Check> checkins;
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "database-name").build();

            SharedPreferences infoLogin = getSharedPreferences(LOGIN_ARQUIVO,0);
            int id = infoLogin.getInt("id",-1);

            int ids[] = {id};
            checkins = db.checkDao().loadAllByIds(ids);
            dados = new String[checkins.size()];


//            if(checkins != null && checkins.size()>=1) {
//                for (int i = 0; i < checkins.size(); i++) {
//                    SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//                    //dados[i] = checkins.get(i).getDHourIn().toString();
//                    String info = "Checkin: "+fmt.format(checkins.get(i).getDHourIn())+" | ";
//                    if(checkins.get(i).getDHourOut() != null){
//                        info += "Chekout: "+ fmt.format(checkins.get(i).getDHourOut());
//                    }else{
//                        info += "Chekout: Por fazer";
//                    }
//                    dados[i] = info;
//
//                }
//                return true;
//            }
            return checkins;
        }

        @Override
        protected void onPostExecute(List<Check> param) {
//            if(param == true) {
//                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, dados);
//
//
//                checkView.setAdapter(adapter);
//
//            }
            checkView.setAdapter(new Adaptador(getApplicationContext(),param));

        }

        @Override
        protected void onPreExecute(){

        }

    }


    public class AtualizaUsuario extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params){
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "database-name").build();

            SharedPreferences infoLogin = getSharedPreferences(LOGIN_ARQUIVO,0);
            int id = infoLogin.getInt("id",-1);

            SharedPreferences infoCheckin = getSharedPreferences(CONTROLE_CHECK,0);
            int horas = infoCheckin.getInt("Horas",0);
            int minutos = infoCheckin.getInt("Minutos",0);
            boolean infoCheckout = infoCheckin.getBoolean("DoCheckout?",false);
            int lastCheckinId = infoCheckin.getInt("LastCheckin", -1);
            db.userDao().updateInfoUser(horas,minutos,infoCheckout,lastCheckinId, id);

            return null;
        }

        @Override
        protected void onPostExecute(Void param) {



        }

        @Override
        protected void onPreExecute(){

        }


    }

    public class GetUsuario extends AsyncTask<Void, Void, User> {


        @Override
        protected User doInBackground(Void... params){
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "database-name").build();

            SharedPreferences infoLogin = getSharedPreferences(LOGIN_ARQUIVO,0);
            int id = infoLogin.getInt("id",-1);

            return db.userDao().findById(id);

        }

        @Override
        protected void onPostExecute(User param) {

//            SharedPreferences infoCheck = getSharedPreferences(CONTROLE_CHECK,0);
//            boolean doCheckout = infoCheck.getBoolean("DoCheckout?",false);
            if(param.getInfoCheckout() == true){
                checkin.setText("FAZER CHECKOUT");
            }else{
                checkin.setText("FAZER CHECK-IN");
            }


            textView1 = (TextView) findViewById(R.id.textView1);
            int minutos = param.getMinutos();
            int horas = param.getHoras();

            if(minutos >= 10) {
                textView1.setText("Você passou " + horas + ":" + minutos + " nos Encontos Acadêmicos");
            }else{
                textView1.setText("Você passou " + horas + ":0" + minutos + " nos Encontos Acadêmicos");
            }

            saveInfoCheckin(param.getInfoCheckout(),param.getLastCheckId());
            SharedPreferences infoCheckin = getSharedPreferences(CONTROLE_CHECK,0);
            SharedPreferences.Editor editor = infoCheckin.edit();
            editor.putInt("Horas", horas);
            editor.putInt("Minutos",minutos);
            editor.commit();
        }

        @Override
        protected void onPreExecute(){

        }


    }


}
