package br.ufc.prograd.cgpa.euufc;

import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ResumosTest extends ActivityInstrumentationTestCase2<Resumos> {
    private Resumos mainActivity;
    private Button button;
    private TextView textNome;
    private ListView listview;


    public ResumosTest() {
        super(Resumos.class);
    }
    // Inicializa os componentes que serão testados
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mainActivity = getActivity();
        textNome = (TextView) mainActivity.findViewById(R.id.textNomeNav);
        listview = (ListView) mainActivity.findViewById(R.id.listViewProg);

    }
    // Boa prática, verifica se os componentes foram incializados corretamente antes de continuar
    public void testPreconditions() {
        assertNotNull("mainActivity is null", mainActivity);
        assertNotNull("text is null", textNome);
        assertNotNull("list is null", listview);
    }
    // Testa o clique no botão, essa anotação é necessária porque o componente foi criado pela UIThread
    @UiThreadTest
    public void testTextView() {
        // textNome.performClick();
        // button.performClick();
        textNome.setText("GLAUTON CARDOSO SANTOS");
        assertEquals("GLAUTON CARDOSO SANTOS",textNome.getText());
    }

    @UiThreadTest
    public void test2() {
        double lat, log;
        boolean qrcode = false;

        lat=-3.7460349;
        log=-38.5720989;
        Location ica = new Location("");
        ica.setLatitude(lat);
        ica.setLongitude(log);

        Location myLocation = new Location("");
        myLocation.setLatitude(lat);
        myLocation.setLongitude(log);

        //int distancia = (int) myLocation.distanceTo(ica);
        if (myLocation.distanceTo(ica) < 200.0) {
            qrcode=true;
        }

        assertEquals(true, qrcode);

    }

    @UiThreadTest
    public void test3() {
        double lat, log;
        double myLat, myLog;
        boolean qrcode2 = false;

        //fortaleza
        lat=-3.7327144;
        log=-38.5269981;
        Location ica = new Location("Fortaleza");
        ica.setLatitude(lat);
        ica.setLongitude(log);

        //quixada
        myLat=-4.9684385;
        myLog=-39.0161259;
        Location myLocation = new Location("Quixada");
        myLocation.setLatitude(myLat);
        myLocation.setLongitude(myLog);


        float distance = 0;
        Location crntLocation = new Location("crntlocation");
        crntLocation.setLatitude(myLat);
        crntLocation.setLongitude(myLog);

        Location newLocation = new Location("newlocation");
        newLocation.setLatitude(lat);
        newLocation.setLongitude(log);


        distance = crntLocation.distanceTo(newLocation);  //in meters
        //int distancia= (int) distance;

        //double dist = crntLocation.distanceTo(newLocation) ;  //in km * 0.001

        // distance =crntLocation.distanceTo(newLocation) / 1000; // in km



        assertEquals(false, distance<200.0);
        //assertEquals(100, distancia);

    }
}

