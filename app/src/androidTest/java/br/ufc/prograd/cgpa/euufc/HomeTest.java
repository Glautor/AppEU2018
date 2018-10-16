package br.ufc.prograd.cgpa.euufc;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CheckinTest extends ActivityInstrumentationTestCase2<Home> {

    public static final String LOGIN_ARQUIVO = "ArquivoLogin";
    private Home homeActivity;
    private EditText editCpf;
    private Button loginButton;
    private TextView nomeText;
    Button checkin;
    SharedPreferences infoLogin;
    Context context;
    private String resultado;

    public CheckinTest() {
        super(Home.class);
    }


    // Inicializa os componentes que serão testados
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        homeActivity = getActivity();
        context = getInstrumentation().getTargetContext();
        checkin = (Button) homeActivity.findViewById(R.id.checkin);
        infoLogin = context.getSharedPreferences(LOGIN_ARQUIVO,Context.MODE_PRIVATE);
    }

    // Boa prática, verifica se os componentes foram incializados corretamente antes de continuar
    public void testPreconditions() {
        assertNotNull("home is null", homeActivity);
    }

    @UiThreadTest
    public void test2(){
       // checkin.

    }

    @UiThreadTest
    public void test3(){
        SharedPreferences infoLogin = context.getSharedPreferences(LOGIN_ARQUIVO, 0);
        String nome = infoLogin.getString("nome", "teste");
        String cpf = infoLogin.getString("cpf", "teste");
        assertEquals("GLAUTON CARDOSO SANTOS", nome);
        assertEquals("06905756377", cpf);
    }
}
