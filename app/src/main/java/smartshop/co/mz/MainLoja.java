package smartshop.co.mz;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import smartshop.co.mz.chat.Chat;
import smartshop.co.mz.model.Comentario;
import smartshop.co.mz.model.DadosProduto;
import smartshop.co.mz.model.Descricao;
import smartshop.co.mz.model.DescricaoProduto;
import smartshop.co.mz.model.Produto;
import smartshop.co.mz.model.ProdutoImagem;
import smartshop.co.mz.model.Usuario;
import smartshop.co.mz.publicar.Publicar;
import smartshop.co.mz.rv.Rv_comentarios;
import smartshop.co.mz.rv.Rv_descricao;
import smartshop.co.mz.rv.Rv_produtos;
import smartshop.co.mz.vp.ViewPagerAdapter;

public class MainLoja extends AppCompatActivity {


    private ViewPager mViewPager;
    private long posicaoComentario = 0;
    private TextView tv_localizacao;
    private List<ProdutoImagem> mList = new ArrayList<>();
    private List<Comentario> comentarioList = new ArrayList<>();
    private List<Comentario> comentarioListLim = new ArrayList<>();
    private List<DescricaoProduto> descricaoProdutoList = new ArrayList<>();
    private List<Descricao> descricaoList = new ArrayList<>();
    private List<Produto> produtoListRelacionado = new ArrayList<>();
    private List<Usuario> usuariosList = new ArrayList<>();
    private List<Produto> produtoListRelacionadoClone = new ArrayList<>();
    private TextView nome_loja , numero_img , tv_descricao , tv_titulo , tv_tempo , tv_estado;
    private Button   btn_ligar;
    private Button  btn_preco ,btn_todos_comentarios;
    private EditText ed_comentario;
    private ImageView  btn_enviar_comentario;
    private int posicao_actual;
    private String id_produto , id_user;
    private String  preco , categoria , titulo;
    private ExpandableRelativeLayout relativeLayout1;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Rv_produtos adapter;
    Usuario usuario;
    private String data , latitude , logitude , tempo;
    final static String APP_PREFS = "app_prefs";
    final static String id_KEY = "id";
    private SharedPreferences prefs;
    private String id = "";
    private String estado;
    private String pronvincia;
    String   img = "" ;
    String  nome = "";
    String number = "0";
    ViewPager viewPager;
    private AdView adView;
    int NUM_COLUNAS = 2;
    private String posicao_do_anucio;
    View mView;
    AlertDialog dialog;
    LinearLayout ll_negociael;
    int poss = 0;
    int visitas  = 0 ;
    int contactos = 0;
    private static  final int  REQUEST_CALL = 1;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 800;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 5000; // time in milliseconds between successive task executions.




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_loja);

        inicializarFirebase();
        prefs = getSharedPreferences(APP_PREFS , MODE_PRIVATE);
        id = prefs.getString(id_KEY , null);
        pegarTamanhoWidth();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        MobileAds.initialize(this, "ca-app-pub-6402233568713542/7461230424");



        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        adView = findViewById(R.id.adView2);
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


        Intent intent = getIntent();

        if(getIntent()!=null && getIntent().getExtras()!=null){
            Bundle bundle = getIntent().getExtras();
            if(!bundle.getString("id_produto").equals(null)) {

                id_produto = intent.getExtras().getString("id_produto");
                preco     = intent.getExtras().getString("preco");
                categoria  = intent.getExtras().getString("categoria");
                titulo = intent.getExtras().getString("titulo");
                id_user = intent.getExtras().getString("id_user");
                tempo = intent.getExtras().getString("tempo");
                pronvincia = intent.getExtras().getString("pronvicia");
                posicao_do_anucio = intent.getExtras().getString("posicao");


            }
        }





        findViewById(R.id.tv_localizacao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainLoja.this , MapsActivity.class));
                Intent intent1 =  new Intent(MainLoja.this , MapsActivity.class);
                intent1.putExtra("latitude" , latitude);
                intent1.putExtra("logitude" , logitude);
                intent1.putExtra("nome_do_vendedor" , "");
            }
        });



        btn_todos_comentarios = findViewById(R.id.btn_mais_comentarios);
        tv_descricao = findViewById(R.id.tv_descricao);
        numero_img = findViewById(R.id.numero_de_img);
        tv_tempo = findViewById(R.id.tv_tempo);
        tv_titulo = findViewById(R.id.tv_titulo);
        nome_loja = findViewById(R.id.tv_nome_loja);
        tv_estado = findViewById(R.id.tv_estado);
        tv_localizacao = findViewById(R.id.tv_localizacao);
        ll_negociael = findViewById(R.id.ll_negociavel);



        //thread que vai inseir produtos relacionados //
        new Thread(inserirProdutoRelacionado).start();
        userInfo();
        contarVisitas();



        ed_comentario   = findViewById(R.id.ed_comentario);
        btn_enviar_comentario = findViewById(R.id.btn_enviar_comentario);
        tv_estado.setText("#"+posicao_do_anucio);


        nome_loja   =  findViewById(R.id.tv_nome_loja);
        btn_ligar = findViewById(R.id.ligar);
        btn_preco = findViewById(R.id.btn_preco);


        btn_todos_comentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                todosComentarios();
            }
        });
        btn_enviar_comentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarComentario();
            }
        });




        btn_ligar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainLoja.this  , Chat.class);
//                startActivity(intent);

                contactar();
            }
        });
        inicializarLoja();

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
                break;
            case DisplayMetrics.DENSITY_XHIGH:


        }

    }

    private void numeroDeimagem(int num , int poss) {


        if (num == 0){
            poss = 0;
        }

        posicao_actual++;
        numero_img.setText(num+"/"+poss);
    }

    private void enviarComentario() {

        if (id != null && !id.isEmpty()){
            String comentario   =  ed_comentario.getText().toString();

            VerificarCampos campos  =  new VerificarCampos() ;

            if (campos.isCampoVazio(comentario)){
                ed_comentario.setHint("Sem comentario!");
                ed_comentario.requestFocus();
            }else {

                ed_comentario.setText("");

            }

            final Comentario comentario1 =  new Comentario();


            databaseReference.child("Usuario").child(id).child("dados").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                        usuario = objSnapshot.getValue(Usuario.class);
                        nome = usuario.getNome();
                        img  = usuario.getPhoto();

                    }




                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            comentario1.setNome(nome);
            comentario1.setImg(img);
            comentario1.setComentario(comentario);
            comentario1.setData(data());
            comentario1.setUid(UUID.randomUUID().toString());
            comentario1.setPosicao(comentarioList.size());

            databaseReference.child("Usuario").child(id_user).child("Produto").child(id_produto).child("Comentario").child(comentario1.getUid()).setValue(comentario1).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    new Thread(buscarComentarios).start();
                    inserirComentarios();
                }
            });
        }else {
            Intent intent = new Intent(MainLoja.this , Login.class);
            startActivity(intent);

        }




    }



    private void inserirSliderImagem() {

        databaseReference.child("Usuario").child(id_user).child("Produto").child(id_produto).child("Imagem").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot obgjSnapshot : dataSnapshot.getChildren()){
                    ProdutoImagem imagem  =  obgjSnapshot.getValue(ProdutoImagem.class);
                    mList.add(imagem);

                }


                viewPager = findViewById(R.id.vp_produto_id);
                final ViewPagerAdapter mViewPager = new ViewPagerAdapter(MainLoja.this , mList ,poss);
                viewPager.setAdapter(mViewPager);

                poss = viewPager.getCurrentItem();

                numeroDeimagem(mList.size() , poss+1);

                slider();
                viewPager.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(MainLoja.this , ImagemTelaCheia.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("imagem" , (Serializable) mList);
                        intent.putExtra("position" , poss);
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }
                });
                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        poss = viewPager.getCurrentItem()+1;
                        numeroDeimagem(mList.size() , poss);
                    }

                    @Override
                    public void onPageSelected(int position) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                        numeroDeimagem(mList.size() , poss);
                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    private void adicionarImagem() {


        inserirSliderImagem();
    }


    private void inicializarLoja() {


        inserirComentarios();
        inicializarProduto();
        adicionarImagem();

    }


    private void inserirComentarios() {

        comentarioListLim.clear();

        for (int i = 0 ; i < comentarioList.size() ; i++){
            comentarioListLim.add(comentarioList.get(i));
            if (comentarioListLim.size() == 5){
                break;
            }
        }

        if (comentarioList.size() > 4){
            btn_todos_comentarios.setVisibility(View.VISIBLE);
        }else {
            btn_todos_comentarios.setVisibility(View.GONE);
        }


        RecyclerView recyclerView = findViewById(R.id.rv_comentarios);
        Rv_comentarios comentarios = new Rv_comentarios(this , comentarioListLim);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(comentarios);

    }

    private void todosComentarios() {

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mView = getLayoutInflater().inflate(R.layout.todos_comentarios, null);


        RecyclerView recyclerView = mView.findViewById(R.id.rv_todos_comentarios);
        ImageView imgCancel = mView.findViewById(R.id.id_img);

        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Rv_comentarios comentarios = new Rv_comentarios(this , comentarioList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(comentarios);

    }

    private void inicializarProduto() {

        new Thread(buscarComentarios).start();
        buscarDetalhes();
        inserirDescricao();


    }

    private void inicializarProdutosRelacionados() {

        //filtar produto por categoria//
        ////

        RecyclerView myrecyclerView = findViewById(R.id.rv_produtos_main);
        Rv_produtos adapter = new Rv_produtos(this ,produtoListRelacionado);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(NUM_COLUNAS , LinearLayoutManager.VERTICAL);
        myrecyclerView.setLayoutManager(layoutManager);
        myrecyclerView.setAdapter(adapter);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 10){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted
                Uri smsUri = Uri.parse(number);
                Intent intent = new Intent(Intent.ACTION_VIEW, smsUri);
                intent.putExtra("sms_body", "sms text");
                intent.putExtra("address" , new String(number));
                intent.setType("vnd.android-dir/mms-sms");
                startActivity(intent);
                dialog.dismiss();

            } else {
                // permission denied
            }
        }else{
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                ligar();
            }else {
                Toast.makeText(this, "permicao nao concedida!", Toast.LENGTH_SHORT).show();
            }
        }


    }

    private void contactar(){


        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mView = getLayoutInflater().inflate(R.layout.dialog_contacto, null);


        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        CircleImageView imgWhatsapp = mView.findViewById(R.id.whatsapp);
        CircleImageView imgCall = mView.findViewById(R.id.call);
        CircleImageView imgMessange = mView.findViewById(R.id.message);

        imgCall.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {



                //permicao// para fazer chamadas
                ligar();
                contarContactos();



            }
        });



        imgMessange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                contarContactos();


                if (ContextCompat.checkSelfPermission(MainLoja.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    // request permission (see result in onRequestPermissionsResult() method)
                    ActivityCompat.requestPermissions(MainLoja.this,
                            new String[]{Manifest.permission.SEND_SMS},
                            10);
                } else {
                    // permission already granted run sms send

                    Uri smsUri = Uri.parse(number);
                    Intent intent = new Intent(Intent.ACTION_VIEW, smsUri);
                    intent.putExtra("sms_body", "sms text");
                    intent.putExtra("address" , new String(number));
                    intent.setType("vnd.android-dir/mms-sms");
                    startActivity(intent);
                    dialog.dismiss();
                }
            }




        });

        imgWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                contarContactos();
                PackageManager pm=getPackageManager();
                try {
                    Intent sendIntent = new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:" + ""+number +"?body=" + ""));
                    sendIntent.setPackage("com.whatsapp");
                    startActivity(sendIntent);

                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(MainLoja.this,"it may be you dont have whatsapp",Toast.LENGTH_LONG).show();

                }

            }
        });

    }


    private void ligar(){

        if (ContextCompat.checkSelfPermission(MainLoja.this ,
                Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainLoja.this ,
                    new String[]{Manifest.permission.CALL_PHONE }, REQUEST_CALL);
        }else {
            Intent intent = new Intent(Intent.ACTION_CALL , Uri.parse("tel:"+number));
            startActivity(intent);
            dialog.dismiss();
        }

    }
    private void userInfo(){

        if (id != null && !id.isEmpty()){

            databaseReference.child("Usuario").child(id).child("dados").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                        usuario = objSnapshot.getValue(Usuario.class);

                        nome = usuario.getNome();
                        img  = usuario.getPhoto();

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


    }


    public void contarVisitas(){

        databaseReference.child("Usuario").child(id_user).child("DadosProduto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    DadosProduto produto = objSnapshot.getValue(DadosProduto.class);

                    visitas = produto.getVisitas();
                    contactos = produto.getContactos();
                }



            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                DadosProduto dadosProduto = new DadosProduto();
                dadosProduto.setContactos(contactos);
                dadosProduto.setVisitas(visitas+1);
                databaseReference.child("Usuario").child(id_user).child("DadosProduto").child("1").setValue(dadosProduto);


            }
        },5000);


    }


    public void contarContactos(){

        databaseReference.child("Usuario").child(id_user).child("DadosProduto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    DadosProduto produto = objSnapshot.getValue(DadosProduto.class);

                    visitas = produto.getVisitas();
                    contactos = produto.getContactos();
                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                DadosProduto dadosProduto = new DadosProduto();
                dadosProduto.setContactos(contactos+1);
                dadosProduto.setVisitas(visitas);
                databaseReference.child("Usuario").child(id_user).child("DadosProduto").child("1").setValue(dadosProduto);



            }
        },1000);



    }



    private void slider() {

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == mList.size() ) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    private void buscarDadosProduto(){

    }
    private void filtrar() {
        //estte metodo filtra os os produtos paara aparecersomentes da quela categoria //
        adapter.getFilter().filter(categoria.trim());
    }

    private Runnable inserirProdutoRelacionado = new Runnable() {
        @Override
        public void run() {
            databaseReference.child("Produto").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    produtoListRelacionado.clear();
                    for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                        Produto produto = objSnapshot.getValue(Produto.class);
                        if (produto.getCategoria().equals(categoria))
                            produtoListRelacionado.add(produto);
                        if (produtoListRelacionado.size() == 4){
                            break;
                        }
                    }
                    inicializarProdutosRelacionados();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    };

    private void inserirDescricao() {

        databaseReference.child("Usuario").child(id_user).child("Produto").child(id_produto).child("Detalhe").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                descricaoList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Descricao descricao = snapshot.getValue(Descricao.class);
                    descricaoList.add(descricao);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        for (int i = 0 ; i  <  descricaoList.size() ; i++){
            if (descricaoList.get(i).getDefinicao().trim().equals("")){
                descricaoList.remove(i);
            }
        }
        RecyclerView recyclerView = findViewById(R.id.rv_destalhes);
        Rv_descricao descricao = new Rv_descricao(MainLoja.this , descricaoList );
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(descricao);


    }




    public void informacoesUser(){
        databaseReference.child("Usuario").child(id_user).child("dados").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuariosList.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    Usuario usuario = objSnapshot.getValue(Usuario.class);
                    usuariosList.add(usuario);
                }

                if (usuariosList.size() > 0){
                    nome_loja.setText(usuariosList.get(0).getNome());

                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void buscarDetalhes() {

        inserirDescricao();

        databaseReference.child("Usuario").child(id_user).child("Produto").child(id_produto).child("Descricao").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                descricaoProdutoList.clear();

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){

                    DescricaoProduto produto = objSnapshot.getValue(DescricaoProduto.class);
                    descricaoProdutoList.add(produto);

                    tv_localizacao.setText(pronvincia +","+produto.getBairo());
                    tv_descricao.setText(produto.getDescricao());

                    btn_preco.setText(preco+" MT");
                    tv_titulo.setText(titulo);
                    tv_tempo.setText("Adicionado em: "+tempo);
                    number =  produto.getContacto();
                   if (produto.getNegociavel().trim().toLowerCase().equals("sim")){
                       ll_negociael.setVisibility(View.VISIBLE);
                   }else {
                       ll_negociael.setVisibility(View.GONE);
                   }

                }

                inserirDescricao();
                informacoesUser();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        inserirDescricao();

    }


    private void inicializarFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }


    private Runnable buscarComentarios = new Runnable() {

        @Override
        public void run() {
            databaseReference.child("Usuario").child(id_user).child("Produto").child(id_produto).child("Comentario").orderByChild("posicao").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    comentarioList.clear();
                    for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {

                        Comentario comentario  = objSnapshot.getValue(Comentario.class);
                        comentarioList.add(comentario);


                    }
                    //inverter list//
                    Collections.reverse(comentarioList);
                    inserirComentarios();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
    };

    private String data(){
        Date date = new Date();
        Locale locale = new  Locale("pt");
        Locale.setDefault(locale);
        SimpleDateFormat format  =  new SimpleDateFormat("dd/MM/yyyy");
        data = format.format(date);
        format  =  new SimpleDateFormat("hh:mm" , Locale.getDefault());
        String time = format.format(date);



        data = data+" "+getString(R.string.a)+" "+time;

        return data;
    }


}
