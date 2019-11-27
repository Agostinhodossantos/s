package smartshop.co.mz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

import de.hdodenhof.circleimageview.CircleImageView;
import smartshop.co.mz.model.Produto;
import smartshop.co.mz.model.Usuario;
import smartshop.co.mz.rv.Rv_produtos;


public class PesquisarCategoria extends AppCompatActivity {

    private List<Produto> produtoList = new ArrayList<>();
    private CircleImageView img_produto ;
    private TextView tv_produto;
    private Rv_produtos adapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String categoria = "";
    private int img;
    private int  NUM_COLUNAS = 2;
    public String id_user = "";
    final static String APP_PREFS = "app_prefs";
    final static String id_KEY = "id";
    private SharedPreferences prefs;
    private String localizacao_user = "";
    private String tag1 = "";
    private String tag2 = "";
    private String tag3 = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar_categorias);

        tv_produto = findViewById(R.id.tv_titulo_pro);
        img_produto = findViewById(R.id.img_produto);
        pegarTamanhoWidth();
        inicializarFirebase();

        prefs = getSharedPreferences(APP_PREFS , MODE_PRIVATE);
        id_user = prefs.getString(id_KEY , null);

        userInfo1();


        Intent intent = getIntent();
        if(getIntent()!=null && getIntent().getExtras()!=null){
            Bundle bundle = getIntent().getExtras();
            if(!bundle.getString("pesquisa").equals(null)) {

                categoria = intent.getExtras().getString("pesquisa").trim();


                if (categoria.trim().equalsIgnoreCase("Carros")){

                    img_produto.setImageResource(R.drawable.icone_car);
                    tv_produto.setText(categoria);
                    tag1 = "viatura";
                    tag2 = "carros";
                    tag3 = "Carros, Motos e Outros";


                }else if (categoria.trim().equalsIgnoreCase("Acessórios para veículos")){

                    img_produto.setImageResource(R.drawable.icone_tool);
                    tv_produto.setText(categoria);
                    tag1= "Acessórios para veículos";
                    tag2 = "Aces de Carros";
                    tag3 = "Rodas";


                }else if (categoria.trim().equalsIgnoreCase("Computador")){

                    img_produto.setImageResource(R.drawable.computer_icone);
                    tv_produto.setText(categoria);
                    tag1 = "Computador";
                    tag2 = "PC";
                    tag3 = "Laptop";


                }else if(categoria.trim().equalsIgnoreCase("Telfones e Tablets")){

                    img_produto.setImageResource(R.drawable.icone_smart_phone);
                    tv_produto.setText(categoria);
                    tag1 = "Telfone";
                    tag2 = "Tablet";
                    tag3 = "Telfones e Tablets";

                }else if(categoria.trim().equalsIgnoreCase("Calçados ,Roupa")){

                    img_produto.setImageResource(R.drawable.roupa_icone);
                    tv_produto.setText(categoria);
                    tag1 = "Calçados ,Roupa e Bolsas";
                    tag2 = "Calcas";
                    tag3 = "Sapatos";


                }else {
                    startActivity(new Intent(PesquisarCategoria.this , Pesquisar.class));
                    finish();

                }
            }

        }

        userInfo();

    }


    public void adicionarProdutoDestaque(){

       databaseReference.child("ProdutoDestaque").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               categoria.trim().toLowerCase();
               for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                   Produto produto = objSnapshot.getValue(Produto.class);

                   if (produto.getNome().contains(categoria)


                           || produto.getCategoria().trim().toLowerCase().contains(tag1.trim().toLowerCase())
                           || produto.getSub_categoria().trim().toLowerCase().contains(tag1.trim().toLowerCase())
                           || produto.getNome().trim().toLowerCase().contains(tag1.trim().toLowerCase())

                           || produto.getCategoria().trim().toLowerCase().contains(tag2.trim().toLowerCase())
                           || produto.getSub_categoria().trim().toLowerCase().contains(tag2.trim().toLowerCase())
                           || produto.getNome().trim().toLowerCase().contains(tag2.trim().toLowerCase())

                           || produto.getCategoria().trim().toLowerCase().contains(tag3.trim().toLowerCase())
                           || produto.getSub_categoria().trim().toLowerCase().contains(tag3.trim().toLowerCase())
                           || produto.getNome().trim().toLowerCase().contains(tag3.trim().toLowerCase())

                           || produto.getCategoria().trim().toLowerCase().contains(categoria)
                           || produto.getSub_categoria().trim().toLowerCase().contains(categoria)
                           || produto.getNome().trim().toLowerCase().contains(categoria)
                   ){

                       if (!localizacao_user.isEmpty() && localizacao_user != null){

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

        RecyclerView myrecyclerView = findViewById(R.id.rv_result);
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
               categoria.trim().toLowerCase();
               for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                   Produto produto = objSnapshot.getValue(Produto.class);

                   if (produto.getNome().contains(categoria)


                           || produto.getCategoria().trim().toLowerCase().contains(tag1.trim().toLowerCase())
                           || produto.getSub_categoria().trim().toLowerCase().contains(tag1.trim().toLowerCase())
                           || produto.getNome().trim().toLowerCase().contains(tag1.trim().toLowerCase())

                           || produto.getCategoria().trim().toLowerCase().contains(tag2.trim().toLowerCase())
                           || produto.getSub_categoria().trim().toLowerCase().contains(tag2.trim().toLowerCase())
                           || produto.getNome().trim().toLowerCase().contains(tag2.trim().toLowerCase())

                           || produto.getCategoria().trim().toLowerCase().contains(tag3.trim().toLowerCase())
                           || produto.getSub_categoria().trim().toLowerCase().contains(tag3.trim().toLowerCase())
                           || produto.getNome().trim().toLowerCase().contains(tag3.trim().toLowerCase())



                           || produto.getCategoria().trim().toLowerCase().contains(categoria)
                           || produto.getSub_categoria().trim().toLowerCase().contains(categoria)
                           || produto.getNome().trim().toLowerCase().contains(categoria)


                   ){

                       if (!localizacao_user.isEmpty() && localizacao_user != null){

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



    private void userInfo() {


        if (id_user != null && !id_user.isEmpty()){

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




    private void userInfo1() {

        databaseReference.child("Usuario").child(id_user).child("dados").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Usuario usuario = objSnapshot.getValue(Usuario.class);
                    localizacao_user = usuario.getLocalizacao();

                }

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
