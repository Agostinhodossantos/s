package smartshop.co.mz.publicar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import smartshop.co.mz.ui.Login;
import smartshop.co.mz.R;
import smartshop.co.mz.model.DadosProduto;
import smartshop.co.mz.model.Produto;
import smartshop.co.mz.publicar.pick_img.MainActivity;
import smartshop.co.mz.rv.Rv_produtos_user;

public class Publicar extends AppCompatActivity {

    private Button btn_publicar ;
    private TextView tv_visitas , tv_contactos , tv_publicacoes;
    private RecyclerView rv_publicacoes;
    private int width = 0;
    private List<Produto> produtoList = new ArrayList<>();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    final static String APP_PREFS = "app_prefs";
    final static String id_KEY = "id";
    private SharedPreferences prefs;
    private String id_usuario = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar);

        prefs = getSharedPreferences(APP_PREFS , MODE_PRIVATE);
        id_usuario  = prefs.getString(id_KEY , null);

        btn_publicar = findViewById(R.id.btn_publicar);
        tv_contactos = findViewById(R.id.tv_contactos);
        tv_visitas = findViewById(R.id.tv_visitas);
        tv_publicacoes = findViewById(R.id.tv_publicacoes);
        rv_publicacoes = findViewById(R.id.rv_publicacoes);



        btn_publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =  new Intent(Publicar.this , MainActivity.class);
                intent.putExtra("publicar" , "0");
                startActivity(intent);
            }
        });

        pegarTamanhoWidth();
        inicializarFirebase();


        adicionarProduto();
    }

    private void inicializarPublicacoes() {



        int NUM_COLUNAS = 2;

        Rv_produtos_user adapter = new Rv_produtos_user(this ,produtoList);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(NUM_COLUNAS , LinearLayoutManager.VERTICAL);
        rv_publicacoes.setLayoutManager(layoutManager);
        rv_publicacoes.setAdapter(adapter);
    }

    private void adicionarProduto() {


        if (id_usuario != null && !id_usuario.isEmpty()){


            databaseReference.child("Usuario").child(id_usuario).child("Produto").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    produtoList.clear();
                    for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                        Produto produto  = objSnapshot.getValue(Produto.class);
                        produtoList.add(produto);

                    }
                    inicializarPublicacoes();
                    actualizarDadosUser();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }else {
            Intent intent = new Intent(Publicar.this , Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }

    }


    private void actualizarDadosUser() {
        if (id_usuario != null && !id_usuario.isEmpty()){
            tv_publicacoes.setText(produtoList.size()+"");

            databaseReference.child("Usuario").child(id_usuario).child("DadosProduto").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                        DadosProduto produto = objSnapshot.getValue(DadosProduto.class);
                        Toast.makeText(Publicar.this, ""+produto.getContactos(), Toast.LENGTH_SHORT).show();




                        tv_contactos.setText(produto.getContactos()+"");

                        tv_visitas.setText(produto.getVisitas()+"");


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private int pegarTamanhoWidth(){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        width = metrics.widthPixels;
        return width;
    }

    private void inicializarFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }
}
