package smartshop.co.mz.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import smartshop.co.mz.R;
import smartshop.co.mz.criar_loja.Infoloja_0;
import smartshop.co.mz.model.Comentario;
import smartshop.co.mz.model.LojaModel;
import smartshop.co.mz.rv.Rv_comentarios;
import smartshop.co.mz.rv.Rv_loja;

public class LojasOficias extends AppCompatActivity {

    List<LojaModel> lojaList = new ArrayList<>();
    private ImageView btn_back;
    private EditText ed_pesquisar;
    private Button btn_criar_loja;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    final static String APP_PREFS = "app_prefs";
    final static String id_KEY = "id";
    private SharedPreferences prefs;
    private String id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lojas_oficias);

        prefs = getSharedPreferences(APP_PREFS , MODE_PRIVATE);
        id = prefs.getString(id_KEY , null);



        inicializarFirebase();
        buscarDados();

        btn_back = findViewById(R.id.btn_back);
        ed_pesquisar = findViewById(R.id.ed_pesquisar_lojas);


        btn_criar_loja = findViewById(R.id.btn_criar_loja);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_criar_loja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //verificar se o usuario ja tem uma loja//
               verificarLoja();
            }
        });

        ed_pesquisar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                pesquisar(charSequence.toString());

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void pesquisar(String toString) {
        buscarDados();
        for (LojaModel model : lojaList){
            if (model.getNome().toLowerCase().trim().contains(toString.toLowerCase().trim())){

            }else {
                lojaList.remove(model);
            }
        }

        inicializarLojaUi();
    }

    private void verificarLoja() {

        if (id != null){
            databaseReference.child("Usuario").child(id).child("Loja").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getChildrenCount()< 1){
                        criar();
                    }else {
                        go();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else {


            Intent intent = new Intent(LojasOficias.this , Login.class);
            startActivity(intent);

            finish();


        }
    }

    private void criar() {


        Intent intent = new Intent(LojasOficias.this , Infoloja_0.class);
        startActivity(intent);

    }

    private void go() {

        if (id != null){
            Intent intent = new Intent(LojasOficias.this , MInhaLojaList.class);
            startActivity(intent);
        }else {

        }

    }

    private void buscarDados() {



        databaseReference.child("Loja").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // TODO: 12/28/2019 limpar lista//

                lojaList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    LojaModel model = snapshot.getValue(LojaModel.class);
                    lojaList.add(model);

                }

                inicializarLojaUi();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    private void inicializarLojaUi() {


        RecyclerView recyclerView = findViewById(R.id.lojas);
        Rv_loja comentarios = new Rv_loja(this ,lojaList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(comentarios);


    }


    private void inicializarFirebase() {

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }
}
