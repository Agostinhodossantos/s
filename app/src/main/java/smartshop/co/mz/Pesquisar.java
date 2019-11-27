package smartshop.co.mz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import smartshop.co.mz.model.Produto;
import smartshop.co.mz.model.Usuario;
import smartshop.co.mz.rv.Rv_produtos;


public class Pesquisar extends AppCompatActivity {

    private List<Produto> produtoList = new ArrayList<>();
    private EditText pesquisar ;
    private Rv_produtos adapter;
    private ImageView btn_pesquisar , btn_voltar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String pesquisa = "";
    private int  NUM_COLUNAS = 2;
    public String id_user = "";
    final static String APP_PREFS = "app_prefs";
    final static String id_KEY = "id";
    private SharedPreferences prefs;
    private String localizacao_user = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar);

        pegarTamanhoWidth();
        inicializarFirebase();

        prefs = getSharedPreferences(APP_PREFS , MODE_PRIVATE);
        id_user = prefs.getString(id_KEY , null);


        btn_voltar = findViewById(R.id.btn_back);
        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        pesquisar = findViewById(R.id.pesquisar);
        btn_pesquisar = findViewById(R.id.img_pesquisar);

        userInfo();

        pesquisar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pesquisa = charSequence.toString();
                userInfo();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                userInfo();
            }
        });

            btn_pesquisar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pesquisa = pesquisar.getText().toString().toLowerCase();
                   userInfo();
                }
            });


    }

    private void userInfo() {

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


    }

    private void produtosDestaque() {
              databaseReference.child("ProdutoDestaque").equalTo(localizacao_user).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    pesquisa.toLowerCase().trim();

                    for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                        Produto produto  = objSnapshot.getValue(Produto.class);

                        if (!pesquisa.isEmpty() && !pesquisa.equals("")){

                            if (produto.getCategoria().toLowerCase().trim().contains(pesquisa)
                                    || produto.getNome().toLowerCase().trim().contains(pesquisa)
                                    ||produto.getSub_categoria().trim().toLowerCase().contains(pesquisa)
                            ){


                                if (produto.getProvincia().equalsIgnoreCase(localizacao_user)){
                                    produtoList.add(produto);
                                }else{

                                }

                            }

                        }else {
                            // produtoList.add(produto);
                        }


                    }

                    //esse metodo vai buscar todos os produtos em destaque



                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });




            Collections.reverse(produtoList);

            RecyclerView myrecyclerView = findViewById(R.id.rv_result);
            adapter = new Rv_produtos(Pesquisar.this ,produtoList);
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(NUM_COLUNAS , LinearLayoutManager.VERTICAL);
            myrecyclerView.setLayoutManager(layoutManager);
            myrecyclerView.setAdapter(adapter);
        }



    public void adicionarProduto(){

       databaseReference.child("Produto").orderByChild("posicao").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


               produtoList.clear();
               pesquisa.toLowerCase().trim();
               for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                   Produto produto = objSnapshot.getValue(Produto.class);

                   //se o pesquisar nao for null vai adicionar resultado da pesquisa //
                   ////
                   if (!pesquisa.isEmpty() && !pesquisa.equals("")){
                       if (produto.getCategoria().toLowerCase().trim().contains(pesquisa)
                               || produto.getNome().toLowerCase().trim().contains(pesquisa)
                               ||produto.getSub_categoria().trim().toLowerCase().contains(pesquisa)
                       ){


                           if (produto.getProvincia().equalsIgnoreCase(localizacao_user)){
                               produtoList.add(produto);
                           }else{

                           }

                       }

                   }else {
                      // produtoList.add(produto);
                   }

               }

             produtosDestaque();
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
