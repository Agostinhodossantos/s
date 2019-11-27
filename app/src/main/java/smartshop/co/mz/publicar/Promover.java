package smartshop.co.mz.publicar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import smartshop.co.mz.MainLoja;
import smartshop.co.mz.R;
import smartshop.co.mz.model.Produto;

public class Promover extends AppCompatActivity {

   private Button btn_comprar_basico , btn_comprar_pro ,btn_comprar_exclusivo;
   private ExpandableRelativeLayout relativeLayout;
   private static final int  precoPro    = 200;
   private static final int  precoBasico = 200;
   private static final int  precoExclusivo = 600;
    private List<Produto> produtoList = new ArrayList<>();
    Produto produto  = new Produto();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    final static String APP_PREFS = "app_prefs";
    final static String id_KEY = "id";
    private SharedPreferences prefs;
    private String id_usuario;

    private String text;
    private String numero = "843655568";
    String id_produto ;
    String preco ;
    String categoria   ;
    String titulo   ;
    String id_user ;
    String tempo ;
    String pronvincia ;
    String posicao_do_anucio ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promover);


        text = "ola";
        prefs = getSharedPreferences(APP_PREFS , MODE_PRIVATE);
        id_usuario  = prefs.getString(id_KEY , null);

        Intent intent = getIntent();

        if(getIntent()!=null && getIntent().getExtras()!=null){
            Bundle bundle = getIntent().getExtras();
            if(!bundle.getString("id_produto").equals(null)) {

              id_produto =        intent.getExtras().getString("id_produto");
              preco       =       intent.getExtras().getString("preco");
              categoria   =       intent.getExtras().getString("categoria");
              titulo    =         intent.getExtras().getString("titulo");
              id_user =           intent.getExtras().getString("id_user");
              tempo =             intent.getExtras().getString("tempo");
              pronvincia =        intent.getExtras().getString("pronvicia");
              posicao_do_anucio = intent.getExtras().getString("posicao");


            }
        }


        btn_comprar_basico = findViewById(R.id.btn_comprar_basico);
        btn_comprar_pro = findViewById(R.id.btn_comprar_pro);
        btn_comprar_exclusivo = findViewById(R.id.btn_comprar_exclusivo);

        btn_comprar_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = "Quero promover meu produto de id"+posicao_do_anucio+"no pacote pro de"+precoPro+"MT";
               passarEnviar();

            }
        });
        btn_comprar_basico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                text = "Quero promover meu produto de id"+posicao_do_anucio+"no pacote basico de "+precoBasico+"MT";
                passarEnviar();


            }
        });

        btn_comprar_exclusivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = "Quero promover meu produto de id"+posicao_do_anucio+"no pacote exclusivo de "+precoBasico+"MT";
                passarEnviar();
            }
        });
    }

    private void passarEnviar() {

        PackageManager pm=getPackageManager();
        try {
            Intent sendIntent = new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:" + ""+numero +"?body=smart shop"));
            sendIntent.setPackage("com.whatsapp");
            startActivity(sendIntent);

        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"it may be you dont have whatsapp",Toast.LENGTH_LONG).show();

        }

    }

    public void getDadosProduto(){
        databaseReference.child("Usuario").child(id_usuario).child("Produto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    Produto produto = objSnapshot.getValue(Produto.class);


                        produto.setNome(produto.getNome());
                        produto.setUid(produto.getUid());
                        produto.setIdUser(produto.getIdUser());
                        produto.setCategoria(produto.getCategoria());
                        produto.setSub_categoria(produto.getSub_categoria());
                        produto.setProvincia(produto.getProvincia());
                        produto.setDistrito(produto.getDistrito());
                        produto.setTempo(produto.getTempo());
                        produto.setPreco(produto.getPreco());

                        databaseReference.child("Produto").child(produto.getUid()).setValue(produto);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public  void promover(){
        getDadosProduto();
    }

    private void inicializarFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

}
