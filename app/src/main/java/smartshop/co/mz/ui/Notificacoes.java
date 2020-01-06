package smartshop.co.mz.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import smartshop.co.mz.R;
import smartshop.co.mz.model.Notificacao_model;
import smartshop.co.mz.rv.Rv_Notificacoes;
import smartshop.co.mz.rv.Rv_loja;

public class Notificacoes extends AppCompatActivity {


    private Toolbar toolbar;
    private List<Notificacao_model> notificacao_modelList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacoes);

         toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        buscarComentarios();





    }

    private void buscarComentarios() {

        for (int i = 4 ; i <= 5 ; i++){
            notificacao_modelList.add(new Notificacao_model("1", "", "Foi cancelado a tua compra de Smart Phone podsoijdosofddvjdfofdfdjnfif kdjf", "ha 20 minutos", "", R.drawable.ic_business_center_black_1, 1));
            notificacao_modelList.add(new Notificacao_model("1", "", "Foi cancelado a tua compra de Smart Phone podsoijdosofddvjdfofdfdjnfif kdjf", "ha 20 minutos", "", R.drawable.ic_business_center_black_1, 1));
        }

        inicializarLojaUi();
    }

    private void inicializarLojaUi() {
        RecyclerView recyclerView = findViewById(R.id.rv_notificaceos);
        Rv_Notificacoes comentarios = new Rv_Notificacoes(this , notificacao_modelList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(comentarios);
    }

}
