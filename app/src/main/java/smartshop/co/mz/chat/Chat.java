package smartshop.co.mz.chat;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import smartshop.co.mz.R;
import smartshop.co.mz.ui.VerificarCampos;

public class Chat extends AppCompatActivity {

    TextInputEditText tv_mensagem ;
    ImageButton btn_enviar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        tv_mensagem = findViewById(R.id.text_input_mensagem);
        btn_enviar = findViewById(R.id.btn_enviar);

        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarMensagem();
            }
        });
    }

    private void enviarMensagem() {
        String mensagem = tv_mensagem.getText().toString();
        VerificarCampos verificarCampos = new VerificarCampos();

        if (!verificarCampos.isCampoVazio(mensagem)){

            tv_mensagem.setText("");
        }
    }
}
