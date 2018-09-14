package com.example.usuario.telaswendel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
    Location ica;
    Location bl950;
    Location bl951;
    Location bl953;
    Location ctConv;
    String respostaSer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        checkin = (Button) findViewById(R.id.checkin);
        checkView = (ListView) findViewById(R.id.checkView);

        GetUsuario gc = new GetUsuario();
        gc.execute();

//        respostaSer = null;
//        if(verificaConexao() == true) {
//            EnviaCheck ec = new EnviaCheck();
//            ec.execute();
//        }else{
//            alert("Não conseguimos nos conectar ao servidor. Verifique sua conexão com a internet");
//        }
        BuscaCheck bc = new BuscaCheck();
        bc.execute();

        SharedPreferences infoCheck = getSharedPreferences(CONTROLE_CHECK,0);
        boolean doCheckout = infoCheck.getBoolean("DoCheckout?",false);
        if(doCheckout == true){
            checkin.setText("FAZER CHECKOUT");
        }else{
            checkin.setText("FAZER CHECK-IN");
        }

        final Activity activity = this;


        if(verificaConexao() != true) {
            alert("Não conseguimos nos conectar. Verifique sua conexão");
        }

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

    public void check(View view){
        LocationManager locationManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!gpsEnabled) {
            // Build an alert dialog here that requests that the user enable
            // the location services, then when the user clicks the "OK" button,
            Dialog dialog = new AlertDialog.Builder(this)
                    .setMessage("Precisamos que você ligue seu GPS")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
//                            enableLocationSettings();
                            String provedor = setConfigGPS();

                        }
                    })
                    .create();

            dialog.show();
        }else{
            if(myLocation != null) {
                int distancia = (int) myLocation.distanceTo(ica);
//                if (myLocation.distanceTo(ica) < 200.0 || myLocation.distanceTo(ctConv) < 200 || myLocation.distanceTo(bl953) < 200 || myLocation.distanceTo(bl951) < 200 || myLocation.distanceTo(bl953) < 200) {
                    if (myLocation.distanceTo(ica) > 200.0) {
                    final Activity activity = this;
                    IntentIntegrator integrator = new IntentIntegrator(activity);
                    integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                    integrator.setPrompt("Camera Scan");
                    integrator.setCameraId(0);
                    integrator.initiateScan();
                } else {
                    Toast.makeText(getApplicationContext(), "Você não está na área dos Encontros Universitários", Toast.LENGTH_LONG).show();
                }
            }else{
                myLocation = getLastLocation();
                Toast.makeText(getApplicationContext(), "Não conseguimos acessar sua localização. Aguarde alguns segundos ou reinicie o app.", Toast.LENGTH_LONG).show();
                onStart();
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

    public  boolean verificaConexao() {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }

    @SuppressLint("MissingPermission")
    protected String setConfigGPS(){
        ica = new Location(LocationManager.GPS_PROVIDER);
        ica.setLatitude(-3.7460349);
        ica.setLongitude(-38.5720989);
        bl950 = new Location(LocationManager.GPS_PROVIDER);
        bl950.setLatitude(-3.7459491);
        bl950.setLongitude(-38.5761109);
        bl951 = new Location(LocationManager.GPS_PROVIDER);
        bl951.setLatitude(-3.7463107);
        bl951.setLongitude(-38.576243);
        bl953 = new Location(LocationManager.GPS_PROVIDER);
        bl953.setLatitude(-3.7470905);
        bl953.setLongitude(-38.5754332);
        ctConv = new Location(LocationManager.GPS_PROVIDER);
        ctConv.setLatitude(-3.7447339);
        ctConv.setLongitude(-38.5733587);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getProvider(LocationManager.GPS_PROVIDER);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setCostAllowed(false);

        String providerName = locationManager.getBestProvider(criteria, true);


        //locationManager.requestSingleUpdate(providerName, this, null);//Updates(providerName,1000,0,this);
        locationManager.requestLocationUpdates(providerName,0,0,this);

        myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(myLocation != null) {
            //Toast.makeText(getApplicationContext(), String.valueOf(myLocation.getLongitude()), Toast.LENGTH_LONG).show();
        }else{
            myLocation = getLastLocation();
            if(myLocation != null){
                //Toast.makeText(getApplicationContext(), String.valueOf(myLocation.getLongitude()), Toast.LENGTH_LONG).show();
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

    @SuppressLint("ResourceType")
    @Override
    protected void onStart() {
        super.onStart();

        AjustaCheckout aj = new AjustaCheckout();
        aj.execute();
        // This verification should be done during onStart() because the system calls
        // this method when the user returns to the activity, which ensures the desired
        // location provider is enabled each time the activity resumes from the stopped state.


        LocationManager locationManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

//        BuscaCheck bc = new BuscaCheck();
//        bc.execute();

//        EnviaCheck ec = new EnviaCheck();
//        ec.execute();

        respostaSer = null;
        if(verificaConexao() == true) {
            EnviaCheck ec = new EnviaCheck();
            ec.execute();
        }

        if (!gpsEnabled) {
            // Build an alert dialog here that requests that the user enable
            // the location services, then when the user clicks the "OK" button,
            Dialog dialog = new AlertDialog.Builder(this)
                    .setMessage("Precisamos que você ligue seu GPS")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
//                            enableLocationSettings();
                            String providerName = setConfigGPS();

                        }
                    })
                    .create();

            dialog.show();
        }else{
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                String providerName = setConfigGPS();
            }
        }
    }

    @Override
    protected void onResume(){
        super.onResume();

        BuscaCheck bc = new BuscaCheck();
        bc.execute();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_gallery:item.setChecked(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        BuscaCheck bc = new BuscaCheck();
        bc.execute();
    }
    @Override
    protected void onPause(){
        super.onPause();
        if(locationManager != null){
            locationManager.removeUpdates(this);
            locationManager = null;
        }


    }

    @Override
    protected void onStop(){
        super.onStop();
        if(locationManager != null){
            locationManager.removeUpdates(this);
            locationManager = null;
        }
    }

//    private void enableLocationSettings() {
//        Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//        startActivity(settingsIntent);
//    }

    @Override
    public void onLocationChanged(Location location) {
        @SuppressLint("MissingPermission") Location mylocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //Log.d("CHANGED", "LOCATION UPDATED" + String.valueOf(mylocation.getLongitude()));
        myLocation = location;
        //textView1.setText(String.valueOf(location.getLongitude()));
        //Toast.makeText(getApplicationContext(),String.valueOf(mylocation.getLongitude()), Toast.LENGTH_LONG).show();
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
                RealizaCheck rc = new RealizaCheck(result.getContents());
                rc.execute();
            } else{
//                RealizaCheck rc = new RealizaCheck("icaEU");
//                rc.execute();
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
            Intent intent = new Intent(getApplicationContext(), Resumos.class);
            startActivity(intent);



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

            SharedPreferences.Editor prefsEditorCheck = getSharedPreferences(CONTROLE_CHECK, 0).edit();
            prefsEditorCheck.clear();
            prefsEditorCheck.commit();

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
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

    //Verifica se a próximo check corresponde a um checkin ou a um checkout e o salva no BD
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
                        SimpleDateFormat dataFm = new SimpleDateFormat("dd");
                        SimpleDateFormat horaFm = new SimpleDateFormat("HH");

                        int horaAtual =  Integer.valueOf(horaFm.format(date));
                        int diaAtual = Integer.valueOf(dataFm.format(date));

                        if(horaAtual < 8 || diaAtual < 24){
                            //db.close();
                            return "falha";
                        }
                        if(horaAtual > 20 || diaAtual > 26){
                            //db.close();
                            return "falha";
                        }

                        System.out.println("Data:"+ date);
                        //Salva o checkout no BD e move o registro de id do Check nas preferências para -1 (para que posteriormente venha a ser um valor de id válido do banco)
                        int cid = infoCheck.getInt("LastCheckin", -1);
                        db.checkDao().updateCheckOut(date,cid);
                        Check check = db.checkDao().loadById(cid);

                        DateTime horaInicial = new DateTime(check.getDHourIn());
                        DateTime horaFinal = new DateTime(check.getDHourOut());
                        Duration duracao = new Duration(horaInicial, horaFinal);

                        saveInfoCheckin(false,-1, duracao);
                        //db.close();
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
                        SimpleDateFormat dataFm = new SimpleDateFormat("dd");
                        SimpleDateFormat horaFm = new SimpleDateFormat("HH");

                        int horaAtual =  Integer.valueOf(horaFm.format(date));
                        int diaAtual = Integer.valueOf(dataFm.format(date));

                        if(horaAtual < 8 || diaAtual < 24){
                            //db.close();
                            return "falha";
                        }
                        if(horaAtual > 20 || diaAtual > 26){
                            //db.close();
                            return "falha";
                        }

                        System.out.println("Data:"+ date);
                        Check check = new Check(user.getId(),date,false);
                        //Salva i checkin no BD e move o registro de id do Check nas preferências para o valor do id do checkin atual
                        // (desse modo, na hora do checkout teremos como atualizar facilmente pois temos a info de id salva)
                        db.checkDao().insertAll(check);
                        saveInfoCheckin(true,db.checkDao().loadIdByHourIn(date));
                        //db.close();
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
                if(minutos >= 1 && horas > 0) {
                    textView1.setVisibility(View.VISIBLE);
                    textView1.setText(horas + " horas e " + minutos + " minutos nos Encontros Universitários");
                }
                if(minutos >= 1 && horas == 0) {
                    textView1.setVisibility(View.VISIBLE);
                    textView1.setText(minutos + " minutos nos Encontros Universitários");
                } else{
                    textView1.setVisibility(View.GONE);
                }
                checkin.setText("FAZER CHECK-IN");
            }
            if(param.equals("falha")){
                Toast.makeText(getApplicationContext(), "Horário para acesso não permitido. Check não realizado", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute(){

        }



    }

    public class AjustaCheckout extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... params){

            SharedPreferences infoCheck = getSharedPreferences(CONTROLE_CHECK,0);
            boolean doCheckout = infoCheck.getBoolean("DoCheckout?",false);
            if(doCheckout == true){

                AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "database-name").build();

                SharedPreferences infoLogin = getSharedPreferences(LOGIN_ARQUIVO,0);
                int id = infoLogin.getInt("id",-1);

                User user = db.userDao().findById(id);
                Date date = new Date();
                SimpleDateFormat dataFm = new SimpleDateFormat("dd");
                SimpleDateFormat horaFm = new SimpleDateFormat("HH");

                int horaAtual =  Integer.valueOf(horaFm.format(date));
                int diaAtual = Integer.valueOf(dataFm.format(date));

                //Setando Pontos de Checkout dos dias de evento
                Date diaUm14 = new Date();
                diaUm14.setTime(1540396800000L);
                int dataUm = Integer.valueOf(dataFm.format(diaUm14));
                int hora14 = Integer.valueOf(horaFm.format(diaUm14));
                Date diaUm20 = new Date();
                diaUm20.setTime(1540418400000L);
                int hora20 = Integer.valueOf(horaFm.format(diaUm20));

                Date diaDois14 = new Date();
                diaDois14.setTime(1540483200000L);
                int dataDois = Integer.valueOf(dataFm.format(diaDois14));
                Date diaDois20 = new Date();
                diaDois20.setTime(1540504800000L);


                Date diaTres14 = new Date();
                diaTres14.setTime(1540569600000L);
                int dataTres = Integer.valueOf(dataFm.format(diaTres14));
                Date diaTres20 = new Date();
                diaTres20.setTime(1540591200000L);


                System.out.println("Data:"+ date);
                //Salva o checkout no BD e move o registro de id do Check nas preferências para -1 (para que posteriormente venha a ser um valor de id válido do banco)
                int cid = infoCheck.getInt("LastCheckin", -1);
                Check checkin = db.checkDao().loadById(cid);
                int dateCheckin = Integer.valueOf(dataFm.format(checkin.getDHourIn()));
                int horaCheckin = Integer.valueOf(horaFm.format(checkin.getDHourIn()));



                if(dateCheckin == dataUm){

                    if(horaCheckin < hora14 && horaAtual >= hora14 || horaCheckin < hora14 && diaAtual > dataUm){
                        db.checkDao().updateCheckOut(diaUm14, cid);
                        Check check = db.checkDao().loadById(cid);

                        DateTime horaInicial = new DateTime(check.getDHourIn());
                        DateTime horaFinal = new DateTime(check.getDHourOut());
                        Duration duracao = new Duration(horaInicial, horaFinal);

                        saveInfoCheckin(false,-1, duracao);
                       // db.close();
                        return "checkout";
                    }else{
                        if(horaCheckin < hora20 && horaAtual >= hora20 || horaCheckin < hora20 && diaAtual > dataUm){
                            db.checkDao().updateCheckOut(diaUm20, cid);
                            Check check = db.checkDao().loadById(cid);

                            DateTime horaInicial = new DateTime(check.getDHourIn());
                            DateTime horaFinal = new DateTime(check.getDHourOut());
                            Duration duracao = new Duration(horaInicial, horaFinal);

                            saveInfoCheckin(false,-1, duracao);
                           // db.close();
                            return "checkout";
                        }
                    }
                }

                if(dateCheckin == dataDois){

                    if(horaCheckin < hora14 && horaAtual >= hora14 || horaCheckin < hora14 && diaAtual > dataDois){
                        db.checkDao().updateCheckOut(diaDois14, cid);
                        Check check = db.checkDao().loadById(cid);

                        DateTime horaInicial = new DateTime(check.getDHourIn());
                        DateTime horaFinal = new DateTime(check.getDHourOut());
                        Duration duracao = new Duration(horaInicial, horaFinal);

                        saveInfoCheckin(false,-1, duracao);
                      //  db.close();
                        return "checkout";
                    }else{
                        if(horaCheckin < hora20 && horaAtual >= hora20 || horaCheckin < hora20 && diaAtual > dataDois){
                            db.checkDao().updateCheckOut(diaDois20, cid);
                            Check check = db.checkDao().loadById(cid);

                            DateTime horaInicial = new DateTime(check.getDHourIn());
                            DateTime horaFinal = new DateTime(check.getDHourOut());
                            Duration duracao = new Duration(horaInicial, horaFinal);

                            saveInfoCheckin(false,-1, duracao);
                          //  db.close();
                            return "checkout";
                        }
                    }
                }

                if(dateCheckin == dataTres){

                    if(horaCheckin < hora14 && horaAtual >= hora14 || horaCheckin < hora14 && diaAtual > dataTres){
                        db.checkDao().updateCheckOut(diaTres14, cid);
                        Check check = db.checkDao().loadById(cid);

                        DateTime horaInicial = new DateTime(check.getDHourIn());
                        DateTime horaFinal = new DateTime(check.getDHourOut());
                        Duration duracao = new Duration(horaInicial, horaFinal);

                        saveInfoCheckin(false,-1, duracao);
                        //db.close();
                        return "checkout";
                    }else{
                        if(horaCheckin < hora20 && horaAtual >= hora20 || horaCheckin < hora20 && diaAtual > dataTres){
                            db.checkDao().updateCheckOut(diaTres20, cid);
                            Check check = db.checkDao().loadById(cid);

                            DateTime horaInicial = new DateTime(check.getDHourIn());
                            DateTime horaFinal = new DateTime(check.getDHourOut());
                            Duration duracao = new Duration(horaInicial, horaFinal);

                            saveInfoCheckin(false,-1, duracao);
                            //db.close();
                            return "checkout";
                        }
                    }
                }
                //db.close();
            }

            return "";
        }

        @Override
        protected void onPostExecute(String param) {
            if(param.equals("checkout")){
                Toast.makeText(getApplicationContext(), "Checkout automático realizado com sucesso", Toast.LENGTH_LONG).show();
                SharedPreferences infoCheck = getSharedPreferences(CONTROLE_CHECK,0);
                int horas = infoCheck.getInt("Horas",0);
                int minutos = infoCheck.getInt("Minutos",0);
                if(minutos >= 10) {
                    textView1.setText("Você passou " + horas + ":" + minutos + " nos Encontros Universitários");
                }else{
                    textView1.setText("Você passou " + horas + ":0" + minutos + " nos Encontros Universitários");
                }
                checkin.setText("FAZER CHECK-IN");
            }

        }

        @Override
        protected void onPreExecute(){

        }



    }
    //Procura os checkins e checkouts feitos até o momento para exibir na tela
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
            //db.close();
            return checkins;
        }

        @Override
        protected void onPostExecute(List<Check> param) {
            checkView.setAdapter(new Adaptador(getApplicationContext(),param));
        }

        @Override
        protected void onPreExecute(){

        }

    }

    public class EnviaCheck extends AsyncTask<Void,Void, List<Check>>{
        @Override
        protected List<Check> doInBackground(Void... params){
            final List<Check> checkins;
            final AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "database-name").build();

            SharedPreferences infoLogin = getSharedPreferences(LOGIN_ARQUIVO,0);
            int id = infoLogin.getInt("id",-1);
            String cpf = infoLogin.getString("cpf","");
            checkins = db.checkDao().loadAllByAtServidor(id,false);


            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            String URL = "http://sysprppg.ufc.br/eu/2018/Resumos/api/alunos/frequencia";

//            Check check = new Check();
            for(int i=0;i<checkins.size();i++){
                if(checkins.get(i).getDHourOut() != null){
//                    check = checkins.get(i);
                    try {
                        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        final JSONObject jsonBody = new JSONObject();

                        jsonBody.put("checkin", fmt.format(checkins.get(i).getDHourIn()));
                        jsonBody.put("checkout", fmt.format(checkins.get(i).getDHourOut()));
                        jsonBody.put("cpf", cpf);
                        final String requestBody = jsonBody.toString();


                        final int finalI = i;
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("VOLLEY", response);
//                                if(response.equals("")){
//                                    respostaSer = new String(response);
//                                    checkins.get(finalI).setAtServidor(true);
//                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("VOLLEY", error.toString());
                                respostaSer = error.toString();
                            }
                        }) {
                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8";
                            }

                            @Override
                            public byte[] getBody() throws AuthFailureError {
                                try {
                                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                                } catch (UnsupportedEncodingException uee) {
                                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                                    return null;
                                }
                            }

                            @Override
                            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                                String responseString = "";
                                if (response != null) {
                                    //responseString = String.valueOf(response.statusCode);
                                    try {
                                        responseString = new String(response.data, "UTF-8");
                                        respostaSer = responseString;
                                        if(respostaSer != null && respostaSer.equals("")){
                                            db.checkDao().updateAtServidor(checkins.get(finalI).getId_check(), true);

                                        }else{
                                            Log.i("Erro", responseString);
                                        }
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }

                                    // can get more details such as response.headers
                                }
                                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                            }
                        };

                        requestQueue.add(stringRequest);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
            int ids[] = {id};
            List<Check> checksAtt = db.checkDao().loadAllByIds(ids);
            dados = new String[checksAtt.size()];
            //db.close();
            respostaSer = null;
            return checksAtt;
        }

        @Override
        protected void onPostExecute(List<Check> param) {
//            checkView.setAdapter(new Adaptador(getApplicationContext(),param));
            BuscaCheck bc = new BuscaCheck();
            bc.execute();
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
            //db.close();
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
            if(minutos >= 1 && horas > 0) {
                textView1.setVisibility(View.VISIBLE);
                textView1.setText(horas + " horas e " + minutos + " minutos nos Encontros Universitários");
            }
            if(minutos >= 1 && horas == 0) {
                textView1.setVisibility(View.VISIBLE);
                textView1.setText(minutos + " minutos nos Encontros Universitários");
            } else{
//                Toolbar.LayoutParams params = new Toolbar.LayoutParams(
//                        Toolbar.LayoutParams.WRAP_CONTENT,
//                        Toolbar.LayoutParams.WRAP_CONTENT
//                );
//                params.setMargins(20, 8, 20, 0);
//                checkin.setLayoutParams(params);
                textView1.setVisibility(View.GONE);
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
