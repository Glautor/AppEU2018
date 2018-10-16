package br.ufc.prograd.cgpa.euufc;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    public static final String LOGIN_ARQUIVO = "ArquivoLogin";
    private LoginActivity mainActivity;
    private Home homeActivity;
    private EditText editCpf;
    private Button loginButton;
    private TextView nomeText;
    SharedPreferences infoLogin;
    Context context;
    private String resultado;
    public LoginTest() {
        super(LoginActivity.class);
    }


    // Inicializa os componentes que serão testados
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mainActivity = getActivity();
        context = getInstrumentation().getTargetContext();
        editCpf = (EditText) mainActivity.findViewById(R.id.textCpf);
        loginButton = (Button) mainActivity.findViewById(R.id.email_sign_in_button);
        //nomeText = (TextView) mainActivity.findViewById(R.id.textNomeNav);
        resultado = mainActivity.resultado;
        infoLogin = context.getSharedPreferences(LOGIN_ARQUIVO,Context.MODE_PRIVATE);
    }

    // Boa prática, verifica se os componentes foram incializados corretamente antes de continuar
    public void testPreconditions() {
        assertNotNull("mainActivity is null", mainActivity);
        assertNotNull("button login is null", loginButton);
        assertNotNull("resultado is null", resultado);
    }

    @UiThreadTest
    public void test2(){
        editCpf.setText("06905756377");

        loginButton.performClick();

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
