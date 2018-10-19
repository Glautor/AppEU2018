package br.ufc.prograd.cgpa.euufc;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.hololo.tutorial.library.Step;
import com.hololo.tutorial.library.TutorialActivity;

public class OnBoardActivity extends TutorialActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //adjustFontScale( getResources().getConfiguration());
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        setPrevText("Anterior"); // Previous button text
        setNextText("Próximo"); // Next button text
        setFinishText("Finalizar"); // Finish button text
        setCancelText("Cancelar"); // Cancel button text

        if (height > 500) {
            // Slide 1
            addFragment(new Step.Builder().setTitle("Fazendo CHECK-IN")
                    .setContent("Ao chegar no evento, aponte sua câmera na direção do QR Code para realizar seu CHECK-IN.")
                    .setBackgroundColor(Color.parseColor("#6495ED")) // int background color
                    .setDrawable(R.drawable.qr_code_codigo_crop) // int top drawable
                    .setSummary("")
                    .build());

            // Slide 2
            addFragment(new Step.Builder().setTitle("Fazendo CHECKOUT")
                    .setContent("Ao sair do evento, o procedimento é o mesmo do passo anterior para realizar seu CHECKOUT.")
                    .setBackgroundColor(Color.parseColor("#739FEE")) // int background color
                    .setDrawable(R.drawable.tutorial_checkout) // int top drawable
                    .setSummary("")
                    .build());

            // Slide 3
            addFragment(new Step.Builder().setTitle("Indicadores de Validação")
                    .setContent("Fique atento a cor deste ícone!! Ele ficará verde quando o par de CHECK-IN e CHECKOUT forem validados no servidor. Caso contrário, verifique acesso à internet.")
                    .setBackgroundColor(Color.parseColor("#81A8EF")) // int background color
                    .setDrawable(R.drawable.tutorial_verificacao) // int top drawable
                    .setSummary("")
                    .build());

            // Slide 4
            addFragment(new Step.Builder().setTitle("Disposições Gerais")
                    .setContent("- Realize CHECKOUT antes das 14 horas e um novo CHECK-IN depois das 14 horas;\n - Os ícones verdes do passo anterior indicam que você já ganhou aquelas respectivas horas;")
                    .setBackgroundColor(Color.parseColor("#92b4f1")) // int background color
                    .setDrawable(R.drawable.tutorial_dg) // int top drawable
                    .setSummary("")
                    .build());
        }else{
            // Slide 1
            addFragment(new Step.Builder().setTitle("Fazendo CHECK-IN")
                    .setContent("O Check-In deve ser realizado sempre ao chegar no evento. Quando estiver na aba 'Início', basta clicar no botão 'FAZER CHECK-IN' e posicionar sua câmera na direção do código QR para validar sua presença nos Encontros Universitários.")
                    .setBackgroundColor(Color.parseColor("#6495ED")) // int background color
                    .setSummary("")
                    .build());

            // Slide 2
            addFragment(new Step.Builder().setTitle("Fazendo CHECKOUT")
                    .setContent("O Check-Out deve ser realizado sempre ao sair do evento. Basta realizar o mesmo procedimento do Check-In para validar sua saída dos Encontros Universitários.")
                    .setBackgroundColor(Color.parseColor("#739FEE")) // int background color
                    .setSummary("")
                    .build());

            // Slide 3
            addFragment(new Step.Builder().setTitle("Indicadores de Validação")
                    .setContent(" O ícone na cor VERDE indica que sua presença já foi enviada ao nosso servidor! Caso se ele esteja na cor CINZA, significa que a sua presença ainda não consta no nosso servidor. Nesse caso, verifique sua conexão com a internet.")
                    .setBackgroundColor(Color.parseColor("#81A8EF")) // int background color
                    .setSummary("")
                    .build());

            // Slide 4
            addFragment(new Step.Builder().setTitle("Disposições Gerais")
                    .setContent("1 - Caso já tenha feito Check-In pela manhã, é necessário realizar um novo Check-In depois das 14 horas; \n 2 - não esqueça de fazer o Check-Out; \n 3 - Sua presença só será validada se ela for corretamente enviada ao servidor. Portanto, certifique-se de que todos os ícones validação estão verdes")
                    .setBackgroundColor(Color.parseColor("#92b4f1")) // int background color
                    .setSummary("")
                    .build());
        }

    }

   /* public  void adjustFontScale( Configuration configuration) {

        configuration.fontScale = (float) 1.3;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);

    }*/
   @Override
   public Resources getResources() {
       Resources resources = super.getResources();
       if (resources != null && resources.getConfiguration().fontScale != 1.1) {
           Configuration configuration = resources.getConfiguration();
           configuration.fontScale = (float)1.1;
           resources.updateConfiguration(configuration, resources.getDisplayMetrics());
       }

       return resources;
   }
    @Override
    protected void onStop() {
        super.onStop();
        Resources resources = super.getResources();
        if (resources != null && resources.getConfiguration().fontScale != 1) {
            Configuration configuration = resources.getConfiguration();
            configuration.fontScale = 1;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
    }
    @Override
    public void finishTutorial() {
        Intent intent = new Intent(
                OnBoardActivity.this,LoginActivity.class
        );
        startActivity(intent);
        onStop();
        finish();
    }

}
