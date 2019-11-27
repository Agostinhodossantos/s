package smartshop.co.mz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import smartshop.co.mz.model.Produto;
import smartshop.co.mz.rv.Rv_produtos;

public class Filtro extends AppCompatActivity {

    private TextView tv_pronvicia  , tv_distrito , tv_categoria , tv_sub_categoria;
    private List<Produto> produtoList = new ArrayList<>();
    private int width = 0;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Rv_produtos adapter;
    private String pronvicia , distrito , categoria , sub_categoria;
    private long precoMin , precoMax ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro);

        pegarTamanhoWidth();
        inicializarFirebase();


        tv_categoria = findViewById(R.id.tv_categoria);
        tv_sub_categoria = findViewById(R.id.tv_sub_categoria);
        tv_pronvicia = findViewById(R.id.tv_pronvicia);
        tv_distrito = findViewById(R.id.tv_distrito);

        final Intent intent = getIntent();
        if(getIntent()!=null && getIntent().getExtras()!=null){
            Bundle bundle = getIntent().getExtras();
            if(!bundle.getString("pronvicia").equals(null)) {

                pronvicia = intent.getExtras().getString("pronvicia").trim().toLowerCase();
                distrito = intent.getExtras().getString("distrito").trim().toLowerCase();
                categoria = intent.getExtras().getString("categoria").trim().toLowerCase();
                sub_categoria = intent.getExtras().getString("sub_categoria").trim().toLowerCase();
                precoMax = intent.getExtras().getLong("precoMax");
                precoMin = intent.getExtras().getLong("precoMin");

                inserirDadosFilro();
            }
        }




    }

    private void inserirDadosFilro() {
        tv_distrito.setText(distrito);
        tv_pronvicia.setText(pronvicia);
        tv_sub_categoria.setText(sub_categoria);
        tv_categoria.setText(categoria);

        adicionarProduto();
    }

    public void adicionarProdutoDestaque(){

        databaseReference.child("ProdutoDestaque").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    Produto produto = objSnapshot.getValue(Produto.class);

                    if(produto.getProvincia().trim().toLowerCase().contains(pronvicia)
                            && produto.getDistrito().trim().toLowerCase().contains(distrito)
                            && produto.getCategoria().trim().toLowerCase().contains(categoria)
                            && produto.getSub_categoria().trim().toLowerCase().contains(sub_categoria)
                    ){

                        produtoList.add(produto);
                        if (precoMin != 0 && precoMax != 0)
                            produtoList.clear();
                        if (Long.parseLong(produto.getPreco()) < precoMax && Long.parseLong(produto.getPreco()) > precoMin){
                            produtoList.add(produto);
                        }

                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Collections.reverse(produtoList);
        int NUM_COLUNAS = 2;

        RecyclerView myrecyclerView = findViewById(R.id.rv_filro);
        adapter = new Rv_produtos(this ,produtoList);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(NUM_COLUNAS , LinearLayoutManager.VERTICAL);
        myrecyclerView.setLayoutManager(layoutManager);
        myrecyclerView.setAdapter(adapter);

    }

    public void adicionarProduto(){

        databaseReference.child("Produto").orderByChild("posicao").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                produtoList.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    Produto produto = objSnapshot.getValue(Produto.class);

                    if(produto.getProvincia().trim().toLowerCase().contains(pronvicia)
                            && produto.getDistrito().trim().toLowerCase().contains(distrito)
                            && produto.getCategoria().trim().toLowerCase().contains(categoria)
                            && produto.getSub_categoria().trim().toLowerCase().contains(sub_categoria)
                    ){

                        produtoList.add(produto);
                        if (precoMin != 0 && precoMax != 0)
                            produtoList.clear();
                        if (Long.parseLong(produto.getPreco()) < precoMax && Long.parseLong(produto.getPreco()) > precoMin){
                            produtoList.add(produto);
                        }

                    }


                }

                adicionarProdutoDestaque();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void pegarTamanhoWidth(){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        switch (metrics.densityDpi){
            case DisplayMetrics.DENSITY_LOW:
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                break;
            case DisplayMetrics.DENSITY_HIGH:

            case DisplayMetrics.DENSITY_XHIGH:



        }

    }

    private void inicializarFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }
}
