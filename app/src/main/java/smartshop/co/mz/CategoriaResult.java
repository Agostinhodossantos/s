package smartshop.co.mz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;
import android.widget.Toast;

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
import smartshop.co.mz.model.Usuario;
import smartshop.co.mz.rv.Rv_produtos;

public class CategoriaResult extends AppCompatActivity {

    private TextView tv_pronvicia  , tv_distrito , tv_categoria , tv_sub_categoria;
    private List<Produto> produtoList = new ArrayList<>();
    private int width = 0;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Rv_produtos adapter;
    private String pronvicia , categoria , sub_categoria;
    public String id_user = "";
    final static String APP_PREFS = "app_prefs";
    final static String id_KEY = "id";
    private SharedPreferences prefs;
    private String localizacao_user = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_result);

        pegarTamanhoWidth();
        inicializarFirebase();




        prefs = getSharedPreferences(APP_PREFS , MODE_PRIVATE);
        id_user = prefs.getString(id_KEY , null);



        final Intent intent = getIntent();
        if(getIntent()!=null && getIntent().getExtras()!=null){
            Bundle bundle = getIntent().getExtras();



                //pronvicia = intent.getExtras().getString("pronvicia").trim().toLowerCase();
                categoria = intent.getExtras().getString("categoria").trim().toLowerCase();
                sub_categoria = intent.getExtras().getString("sub_categoria").trim().toLowerCase();

                inserirDadosFilro();

        }

        userInfo();


    }

    private void inserirDadosFilro() {
//        tv_distrito.setText(distrito);
//        tv_pronvicia.setText(pronvicia);
//        tv_sub_categoria.setText(sub_categoria);
//        tv_categoria.setText(categoria);

    }
    private void userInfo() {
        if (id_user != null && id_user.isEmpty()){

            databaseReference.child("Usuario").child(id_user).child("dados").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                        Usuario usuario = objSnapshot.getValue(Usuario.class);

                        localizacao_user = usuario.getLocalizacao();

                    }

                    adicionarProduto();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }else {
            adicionarProduto();
        }
    }



    public void adicionarProdutoDestaque(){

        databaseReference.child("ProdutoDestaque").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    Produto produto = objSnapshot.getValue(Produto.class);

                    if(
                            produto.getCategoria().trim().toLowerCase().contains(categoria)
                                    || produto.getSub_categoria().trim().toLowerCase().contains(sub_categoria)
                                    || produto.getNome().trim().toLowerCase().contains(categoria)
                                    ||produto.getNome().trim().toLowerCase().contains(sub_categoria)

                    ){
                        //verificando se o user esta logado //
                       if (id_user != null && id_user.isEmpty()){
                           if (produto.getProvincia().equalsIgnoreCase(localizacao_user)){
                               produtoList.add(produto);
                           }
                       }else {
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


        RecyclerView myrecyclerView = findViewById(R.id.rv_produtos);
        adapter = new Rv_produtos(this ,produtoList);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(NUM_COLUNAS , LinearLayoutManager.VERTICAL);
        myrecyclerView.setLayoutManager(layoutManager);
        myrecyclerView.setAdapter(adapter);
    }


    public void adicionarProduto(){

        databaseReference.child("Produto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                produtoList.clear();

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    Produto produto = objSnapshot.getValue(Produto.class);

                    if(
                            produto.getCategoria().trim().toLowerCase().contains(categoria)
                            || produto.getSub_categoria().trim().toLowerCase().contains(sub_categoria)
                            || produto.getNome().trim().toLowerCase().contains(categoria)
                            ||produto.getNome().trim().toLowerCase().contains(sub_categoria)

                    ){

                        if (id_user != null && !id_user.isEmpty()){
                            if (produto.getProvincia().equalsIgnoreCase(localizacao_user)){
                                produtoList.add(produto);
                            }
                        }else {
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
