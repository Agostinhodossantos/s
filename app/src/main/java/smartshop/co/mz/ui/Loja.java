package smartshop.co.mz.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import smartshop.co.mz.R;
import smartshop.co.mz.model.LojaModel;
import smartshop.co.mz.model.Produto;
import smartshop.co.mz.model.Usuario;
import smartshop.co.mz.rv.Rv_produtos;

public class Loja extends AppCompatActivity {


    private static final int NUM_COLUNAS = 2 ;
    private ImageView img_loja;
    private TextView tv_loja;
    private RecyclerView rv_produtos;
    private Toolbar toolbar;
    private List<Produto> produtoList = new ArrayList<>();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String uid , uid_user = "";
    private String nome ,img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loja);

        img_loja = findViewById(R.id.img_loja);
        tv_loja = findViewById(R.id.tv_nome_loja);
        rv_produtos = findViewById(R.id.rv_produtos_loja);
       // toolbar = findViewById(R.id.toolbar);

        inicializarFirebase();

        Intent intent = getIntent();

        if(getIntent()!=null && getIntent().getExtras()!=null){
            Bundle bundle = getIntent().getExtras();
            if(!bundle.getString("uid").equals(null)) {
                uid = bundle.getString("uid");
                uid_user = bundle.getString("uid_user");
                img = bundle.getString("img");
                nome = bundle.getString("nome");

                buscarProdutosLoja(img , nome);
            }
        }

        produtoPrincipal();

    }


    private void inicializarFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    private void buscarProdutosLoja(String img , String nome) {
        Picasso.get().load(img).into(img_loja);
        tv_loja.setText(nome);
    }

    public void produtoPrincipal(){
        //teste//

        databaseReference.child("Usuario").child(uid_user).child("Loja").child(uid).child("Produtos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Produto lojaModel = snapshot.getValue(Produto.class);
                    produtoList.add(lojaModel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        RecyclerView myrecyclerView = findViewById(R.id.rv_produtos_loja);
        Rv_produtos adapter = new Rv_produtos(Loja.this ,produtoList);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(NUM_COLUNAS , LinearLayoutManager.VERTICAL);
        myrecyclerView.setLayoutManager(layoutManager);
        myrecyclerView.setAdapter(adapter);

    }
}
