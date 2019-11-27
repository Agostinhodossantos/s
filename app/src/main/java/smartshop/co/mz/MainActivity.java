package smartshop.co.mz;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import java.lang.*;

import android.os.Handler;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.multidex.MultiDex;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.internal.util.QueueDrainHelper;
import smartshop.co.mz.chat.ChatListMain;
import smartshop.co.mz.model.Localizacao;
import smartshop.co.mz.model.Produto;

import smartshop.co.mz.model.ProdutoImagem;
import smartshop.co.mz.model.Usuario;
import smartshop.co.mz.model.dataHelp;
import smartshop.co.mz.publicar.Publicar;

import smartshop.co.mz.rv.Rv_produtos;
import smartshop.co.mz.vp.ViewPagerAdapter;
import smartshop.co.mz.vp.ViewPagerAdapterPublicidade;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private List<Produto> produtoDestaque = new ArrayList<>();
    private List<ProdutoImagem> mList   = new ArrayList<>();
    private List<Produto> produtoList = new ArrayList<>();
    private List<Usuario> usuarioList = new ArrayList<>();
    private List<Localizacao> localizacaoList = new ArrayList<>();
    private CardView cv_assecorios_paraveiculos , cv_casas , cv_telfones_tablet , cv_computador ,cv_moda , cv_viaturas;
    private TextView tv_filtrar , pesquisar;
    private View mView;
    private AlertDialog dialog;
    private Spinner spinnerLocPronvincia, spinnerLocDestrito , spinnerCategoriaPrincipal , spinnerSubCategoria;
    private ArrayAdapter<String> adapter ;
    private String[] localizacaoPronvincias;
    private String[] localizacaoMaputo;
    private String[] localizacaoGaza;
    private String[] localizacaoInhabane;
    private String[] localizacaoManica;
    private String[] localizacaoNampula;
    private String[] localizacaoNiassa;
    private String[] localizacaoSofala;
    private String[] localizacaoTete;
    private String[] localizacaoZambezia;
    private String[] categoriaPrincipal;
    private String[] sub_categoria_array;
    private String[] localizacaoCabo_Del;
    private String[] nullArray = new String[1];
    private String provincia = "";
    private String distrito = "";
    private String categoria = "";
    private String sub_categoria = "";
    private ImageView img_ponto1,img_ponto2 ,img_ponto3 ,img_ponto4;
    public String id_user = "";
    final static String APP_PREFS = "app_prefs";
    final static String id_KEY = "id";
    private SharedPreferences prefs;
    int NUM_COLUNAS = 2;
    private long precoMin = 0;
    private long precoMax = 0;
    private CircleImageView img_perfil;
    private TextView tv_perifl_user;
    ViewPager viewPager;
    private AdView adView;
    int current_position =0;
    private String localizacao_user = "";
    private EditText ed_preco_min , ed_preco_max;
    private SwipeRefreshLayout refreshLayout;
    private ProgressBar progressBar;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.
    private String categoriaPrincipalstr , subCategoria;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //admob//
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        MobileAds.initialize(this, getString(R.string.admob_banner_id));



        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        progressBar = findViewById(R.id.progress_circular);
//
//        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                adicionarProduto();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        refreshLayout.setRefreshing(false);
//                    }
//                },4000);
//            }
//        });
        //closse  admob//

        nullArray[0] = " ";
        //pegar UID do usuarios depois de se inscrever //
        prefs = getSharedPreferences(APP_PREFS , MODE_PRIVATE);
        id_user = prefs.getString(id_KEY , null);


        MultiDex.install(this);
        pegarTamanhoWidth();
        inicializarFirebase();
        inicializarLocalizacao();

        userInfo();
        adicionarImg();




        img_ponto1 = findViewById(R.id.img_ponto1);
        img_ponto2 = findViewById(R.id.img_ponto2);
        img_ponto3 = findViewById(R.id.img_ponto3);
        img_ponto4 = findViewById(R.id.img_ponto4);

        cv_computador = findViewById(R.id.card_computador);
        cv_moda = findViewById(R.id.card_moda);
        cv_casas = findViewById(R.id.card_hamburger);
        cv_telfones_tablet = findViewById(R.id.card_telfones_tablet);
        cv_assecorios_paraveiculos   = findViewById(R.id.card_acessorios);
        cv_viaturas = findViewById(R.id.card_viatura);

        cv_viaturas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , PesquisarCategoria.class);
                intent.putExtra("pesquisa" , "Carros");
                startActivity(intent);
            }
        });


        cv_computador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this , PesquisarCategoria.class);
                intent.putExtra("pesquisa" , "Computador");
                startActivity(intent);

            }
        });

        cv_assecorios_paraveiculos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , PesquisarCategoria.class);
                intent.putExtra("pesquisa" , "Acessórios para veículos");
                startActivity(intent);
            }
        });


        cv_telfones_tablet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , PesquisarCategoria.class);
                intent.putExtra("pesquisa" , "Telfones e Tablets");
                startActivity(intent);
            }
        });

        cv_moda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , PesquisarCategoria.class);
                intent.putExtra("pesquisa" , "Calçados ,Roupa");
                startActivity(intent);
            }
        });


        cv_casas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               dialogCategoria();

            }
        });


        pesquisar = findViewById(R.id.pesquisar);
        pesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(MainActivity.this , Pesquisar.class);
                startActivity(intent);
            }
        });

        tv_filtrar = findViewById(R.id.btn_filtrar);

        tv_filtrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chamarFiltro();

            }
        });



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }




    private void chamarFiltro() {


        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        mView = getLayoutInflater().inflate(R.layout.dialog_filtrar, null);


        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        mView.findViewById(R.id.img_cancelar_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        mView.findViewById(R.id.btn_filtrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ed_preco_min = mView.findViewById(R.id.tv_preco_min);
                ed_preco_max = mView.findViewById(R.id.tv_preco_max);

                if (ed_preco_min.getText().toString().trim().equals("")){
                    ed_preco_min.requestFocus();
                    ed_preco_min.setHintTextColor(Color.RED);
                }else if (ed_preco_max.getText().toString().trim().equals("")){
                   ed_preco_max.requestFocus();
                   ed_preco_max.setHintTextColor(Color.RED);
                }else {

                    precoMin = Long.parseLong(ed_preco_min.getText().toString().trim());
                    precoMax = Long.parseLong(ed_preco_max.getText().toString().trim());
                    filtrar();
                }





            }
        });

        spinnerLocPronvincia = mView.findViewById(R.id.sp_provincias);
        spinnerLocDestrito = mView.findViewById(R.id.sp_distritos);
        spinnerCategoriaPrincipal = mView.findViewById(R.id.sp_categoria);
        spinnerSubCategoria = mView.findViewById(R.id.sp_sub_categoria);
        ed_preco_max = mView.findViewById(R.id.tv_preco_max);
        ed_preco_min = mView.findViewById(R.id.tv_preco_min);

        selecionarCategoria();
        selecionarPronvicia();
    }


    private void selecionarCategoria() {
        categoriaPrincipal = getResources().getStringArray(R.array.categorias_de_produtos);

        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, categoriaPrincipal);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoriaPrincipal.setAdapter(adapter);

        spinnerCategoriaPrincipal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, View view, int position, long id) {


                categoria = parent.getSelectedItem().toString();

                switch (position){

                    case 0:
                        //  <item>Acessórios para veículos</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_acessorios_para_veiculos);

                        break;
                    case 1:

                        //  <item>Agro , Indústria e Comércio</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_angro_industria);

                        break;

                    case 2:
                        // <item>alimentos</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_alimentos_bebidas);


                        break;
                    case 3:
                        //  <item>Animais</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_animais);

                        break;
                    case 4:
                        /// <item>Antiguidade e Colecções</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_antiguidades);

                        break;
                    case 5:
                        // <item>Arte , Papelaria e Armarinho</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categorias_artes_papelaria);

                        break;
                    case 6:

                        //<item>Bebés</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_alimentos_bebidas);

                        break;
                    case 7:
                        // <item>Beleza e cuidado pessoal</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_beleza_quidados_pessoais);


                        break;
                    case 8:

                        // <item>Brinquedos e Hobbies</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_brinquedos_hobies);

                        break;

                    case 9:

                        //<item>Calçados ,Roupa e Bolsas</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_calcados_roupa);

                        break;

                    case 10:
                        // <item>Casa , Moveis e Decoração</item>
                        sub_categoria_array = getResources().getStringArray(R.array.subcategoria_casas_decoracao);

                        break;

                    case 11:
                        //<item>Carros, Motos e Outros</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categorias_carros);
                        break;

                    case 12:
                        // <item>Telfones e Tablets</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_telfones_tables);
                        break;

                    case 13:

                        //<item>Câmaras e Acessórios</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categorias_cameras_acessorios);
                        break;

                    case 14:
                        ///item>Electrodomésticos</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_eletrodomesticos);
                        break;

                    case 15:

                        // <item>Electrónicos , Áudio e Vídeos</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_eletronicos);
                        break;

                    case 16:
                        //<item>Esporte e Fitness</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_esporte_fitness);
                        break;

                    case 17:

                        //<item>Ferramentas e Construção</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_ferramentas_e_construcao);
                        break;

                    case 18:

                        //<item>Festas</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_festas);
                        break;

                    case 19:

                        //<item>Games</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_games);
                        break;

                    case 20:

                        //<item>Imóveis</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categorias_imoeis);
                        break;

                    case 21:
                        //<item>Informática</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_informatica);
                        break;

                    case 22:
                        //<item>Ingressos</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_ingressos);
                        break;

                    case 23:

                        // <item>Instrumentos Musicais</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_instrumentos_musicais);
                        break;

                    case 24:
                        //<item>Jóias e Relógios</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_joias);
                        break;

                    case 25:

                        //<item>Livros, Revistas e Comics</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_livros_revistas);
                        break;

                    case 26:

                        // <item>Música ,Filmes e Seriados</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_musica_filmes);
                        break;

                    case 27:

                        // <item>Animais</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_animais);
                        break;

                    case 28:
                        //  <item>Saúde</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_saude);
                        break;

                    case 29:
                        //  <item>Serviços</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_servicos);
                        break;

                }

                if (sub_categoria_array != null){
                    adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, sub_categoria_array );
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerSubCategoria.setAdapter(adapter);

                    spinnerSubCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            sub_categoria = adapterView.getSelectedItem().toString();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    private void filtrar() {
        Intent intent  = new Intent(MainActivity.this , Filtro.class);
        intent.putExtra("categoria", categoria );
        intent.putExtra("sub_categoria" , sub_categoria);
        intent.putExtra("distrito" , distrito);
        intent.putExtra("pronvicia", provincia);
        intent.putExtra("precoMin" , precoMin);
        intent.putExtra("precoMax" , precoMax);

        startActivity(intent);
    }


    public void selecionarPronvicia() {

        //este arrayList guarda a referencia de cada array de provincias //
        final List<dataHelp> meuDadoList = new ArrayList<>();

        meuDadoList.add(new dataHelp("Maputo", localizacaoMaputo));
        meuDadoList.add(new dataHelp("Gaza", localizacaoGaza));
        meuDadoList.add(new dataHelp("Inhambane", localizacaoInhabane));
        meuDadoList.add(new dataHelp("Sofala", localizacaoSofala));
        meuDadoList.add(new dataHelp("Manica", localizacaoManica));
        meuDadoList.add(new dataHelp("Tete", localizacaoTete));
        meuDadoList.add(new dataHelp("Zambezia", localizacaoZambezia));
        meuDadoList.add(new dataHelp("Nampula", localizacaoNampula));
        meuDadoList.add(new dataHelp("Niassa", localizacaoNiassa));
        meuDadoList.add(new dataHelp("Cabo Delgado", localizacaoCabo_Del));

        inicializarLocalizacao();

        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, localizacaoPronvincias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocPronvincia.setAdapter(adapter);

        spinnerLocPronvincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                provincia = parent.getSelectedItem().toString();
                for (int i = 0; i < meuDadoList.size(); i++) {
                    if (provincia.equalsIgnoreCase(meuDadoList.get(i).getProvicia())) {


                        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, meuDadoList.get(i).getDestrico());
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerLocDestrito.setAdapter(adapter);

                        spinnerLocDestrito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                distrito = parent.getSelectedItem().toString();

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        break;
                    }
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;


    }

    private void inicializarLocalizacao() {
        localizacaoPronvincias =  getResources().getStringArray(R.array.provincias);
        localizacaoMaputo      =  getResources().getStringArray(R.array.distrito_maputo);
        localizacaoGaza        =  getResources().getStringArray(R.array.distrito_gaza);
        localizacaoInhabane    =  getResources().getStringArray(R.array.distrito_inhambane);
        localizacaoManica      =  getResources().getStringArray(R.array.distrito_manica);
        localizacaoNampula     =  getResources().getStringArray(R.array.distrito_nampula);
        localizacaoNiassa      =  getResources().getStringArray(R.array.distrito_niassa);
        localizacaoSofala      =  getResources().getStringArray(R.array.distrito_sofala);
        localizacaoTete        =  getResources().getStringArray(R.array.distrito_tete);
        localizacaoZambezia    =  getResources().getStringArray(R.array.distrito_zambezia);
        localizacaoCabo_Del    =  getResources().getStringArray(R.array.distrito_cabo_delgado);
    }

    public void produtoPrincipal(){
        //teste//

        RecyclerView myrecyclerView = findViewById(R.id.rv_produtos);
        Rv_produtos adapter = new Rv_produtos(MainActivity.this ,produtoList);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(NUM_COLUNAS , LinearLayoutManager.VERTICAL);
        myrecyclerView.setLayoutManager(layoutManager);
        myrecyclerView.setAdapter(adapter);

    }

    private Runnable adicionarProduto = new Runnable() {
        @Override
        public void run() {
            databaseReference.child("Produto").orderByChild("posicao").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    produtoList.clear();
                    for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {

                        Produto produto  = objSnapshot.getValue(Produto.class);

                        if (localizacao_user != null){
                            if (produto.getProvincia().equalsIgnoreCase(localizacao_user)){
                                produtoList.add(produto);
                            }
                        }else {
                            produtoList.add(produto);
                        }



                    }

                    produtoDestaque();



                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
    } ;







    private void userInfo(){
        //verificar se o usuario esta logado

        if (id_user != null && !id_user.isEmpty()){
            databaseReference.child("Usuario").child(id_user).child("dados").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                        Usuario usuario = objSnapshot.getValue(Usuario.class);

                        localizacao_user = usuario.getLocalizacao();

                    }


                    new Thread(adicionarProduto).run();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }else {
            localizacao_user = null;
            new Thread(adicionarProduto).run();

        }





    }

    private void adicionarImg() {


        databaseReference.child("Publicidade").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProdutoImagem imagem = new ProdutoImagem();
                mList.clear();

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    imagem = objSnapshot.getValue(ProdutoImagem.class);
                    mList.add(imagem);
                }

                viewPager = findViewById(R.id.vp_publicidade_id);
                ViewPagerAdapterPublicidade pagerAdapter = new ViewPagerAdapterPublicidade(getApplicationContext() , mList);
                viewPager.setAdapter(pagerAdapter);

                slider();


            }



            private void slider() {

                final Handler handler = new Handler();
                final Runnable Update = new Runnable() {
                    public void run() {
                        if (currentPage == mList.size() ) {
                            currentPage = 0;
                        }
                        viewPager.setCurrentItem(currentPage++, true);

                        if (currentPage == 0){
                            img_ponto1.setBackgroundResource(R.drawable.ponto_indicador_activo);
                        }else {
                            img_ponto1.setBackgroundResource(R.drawable.ponto_indicador);
                        }

                        if (currentPage == 1){
                            img_ponto2.setBackgroundResource(R.drawable.ponto_indicador_activo);
                        }else {
                            img_ponto2.setBackgroundResource(R.drawable.ponto_indicador);
                        }

                        if (currentPage == 2){
                            img_ponto3.setBackgroundResource(R.drawable.ponto_indicador_activo);
                        }else {
                            img_ponto3.setBackgroundResource(R.drawable.ponto_indicador);
                        }

                        if (currentPage == 3){
                            img_ponto4.setBackgroundResource(R.drawable.ponto_indicador_activo);
                        }else {
                            img_ponto4.setBackgroundResource(R.drawable.ponto_indicador);
                        }

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

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void produtoDestaque(){

        databaseReference.child("ProdutoDestaque").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {

                    Produto produto  = objSnapshot.getValue(Produto.class);

                    if (localizacao_user != null){
                        if (produto.getProvincia().equalsIgnoreCase(localizacao_user)){
                            produtoList.add(produto);
                        }
                    }else {
                        produtoList.add(produto);
                    }


                }

                Collections.reverse(produtoList);
                produtoPrincipal();
                progressBar.setVisibility(View.GONE);
                //esse metodo vai buscar todos os produtos em destaque



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void inicializarFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }
    private void EnviarApp(){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        String message = "Compre e Venda tudo o que quiser pelo aplicativo Smartshop Descubra a melhor experiencia de comprar e vender pela internet *https://smartshop61.webnode.pt*";

        i.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(i, "choose one"));
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            userInfo();
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
            mView = getLayoutInflater().inflate(R.layout.dialog_list_localizacao, null);

            mBuilder.setView(mView);
            dialog = mBuilder.create();
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();


            CardView card_maputo , card_gaza , card_inhambane , card_sofala , card_manica , card_tete ,card_zambezia ,card_nampula , card_niassa ,card_cabo;
                    card_maputo    = mView.findViewById(R.id.card_localizacao_maputo);
                    card_gaza      = mView.findViewById(R.id.card_localizacao_gaza);
                    card_inhambane = mView.findViewById(R.id.card_localizacao_inhabane);
                    card_sofala    = mView.findViewById(R.id.card_localizacao_sofala);
                    card_manica    = mView.findViewById(R.id.card_localizacao_manica);
                    card_tete      = mView.findViewById(R.id.card_localizacao_tete);
                    card_zambezia  = mView.findViewById(R.id.card_localizacao_zambezia);
                    card_nampula   = mView.findViewById(R.id.card_localizacao_nampula);
                    card_niassa    = mView.findViewById(R.id.card_localizacao_niassa);
                    card_cabo      = mView.findViewById(R.id.card_localizacao_cabo_del);

                    if (localizacao_user.equals(localizacaoPronvincias[0])){
                        card_maputo.setCardBackgroundColor(getResources().getColor(R.color.colorAccent1));
                    }
                    else if (localizacao_user.equals(localizacaoPronvincias[1])){
                        card_gaza.setCardBackgroundColor(getResources().getColor(R.color.colorAccent1));
                    }
                    else if (localizacao_user.equals(localizacaoPronvincias[2])){
                        card_inhambane.setCardBackgroundColor(getResources().getColor(R.color.colorAccent1));
                    }
                    else if (localizacao_user.equals(localizacaoPronvincias[3])){
                        card_sofala.setCardBackgroundColor(getResources().getColor(R.color.colorAccent1));
                    }
                    else if (localizacao_user.equals(localizacaoPronvincias[4])){
                        card_manica.setCardBackgroundColor(getResources().getColor(R.color.colorAccent1));
                    }
                    else if (localizacao_user.equals(localizacaoPronvincias[5])){
                        card_tete.setCardBackgroundColor(getResources().getColor(R.color.colorAccent1));
                    }
                    else if (localizacao_user.equals(localizacaoPronvincias[6])){
                        card_zambezia.setCardBackgroundColor(getResources().getColor(R.color.colorAccent1));
                    }
                    else if (localizacao_user.equals(localizacaoPronvincias[7])){
                        card_nampula.setCardBackgroundColor(getResources().getColor(R.color.colorAccent1));
                    } else if (localizacao_user.equals(localizacaoPronvincias[8])){
                        card_niassa.setCardBackgroundColor(getResources().getColor(R.color.colorAccent1));
                    } else if (localizacao_user.equals(localizacaoPronvincias[9])){
                        card_cabo.setCardBackgroundColor(getResources().getColor(R.color.colorAccent1));
                    }
                    card_maputo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            localizacao_user = localizacaoPronvincias[0];
                            introduzirLocalizacao();
                            dialog.dismiss();

                        }
                    });
                    card_gaza.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            localizacao_user = localizacaoPronvincias[1];
                            introduzirLocalizacao();
                            dialog.dismiss();

                        }
                    });
                    card_inhambane.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            localizacao_user = localizacaoPronvincias[2];
                            introduzirLocalizacao();
                            dialog.dismiss();
                        }
                    });
                    card_sofala.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            localizacao_user = localizacaoPronvincias[3];
                            introduzirLocalizacao();
                            dialog.dismiss();
                        }
                    });
                    card_manica.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            localizacao_user = localizacaoPronvincias[4];
                            introduzirLocalizacao();
                            dialog.dismiss();
                        }
                    });
                    card_tete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            localizacao_user = localizacaoPronvincias[5];
                            introduzirLocalizacao();
                            dialog.dismiss();
                        }
                    });
                    card_zambezia.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            localizacao_user = localizacaoPronvincias[6];
                            introduzirLocalizacao();
                            dialog.dismiss();
                        }
                    });
                    card_nampula.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            localizacao_user = localizacaoPronvincias[7];
                            introduzirLocalizacao();
                            dialog.dismiss();
                        }
                    });
                    card_niassa.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            localizacao_user = localizacaoPronvincias[8];
                            introduzirLocalizacao();
                            dialog.dismiss();
                        }
                    });
                    card_cabo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            localizacao_user = localizacaoPronvincias[9];
                            introduzirLocalizacao();
                            dialog.dismiss();
                        }
                    });


            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void introduzirLocalizacao() {

        if (id_user != null && id_user.isEmpty()){

            databaseReference.child("Usuario").child(id_user).child("dados").child(id_user).child("localizacao").setValue(localizacao_user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    produtoList.clear();
                    new Thread(adicionarProduto).run();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "falha tente novamente", Toast.LENGTH_SHORT).show();
                }
            });

            produtoList.clear();
            new Thread(adicionarProduto).run();
        }else {
            Intent intent = new Intent(MainActivity.this , Login.class);
            startActivity(intent);
        }


    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action


            if(id_user != null && !id_user.isEmpty() ){
                Intent intent = new Intent(MainActivity.this, Publicar.class);
                startActivity(intent);
            }else {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }

        }else if(id == R.id.categoria){
            dialogCategoria();
        } else if (id == R.id.compartilhar) {
            EnviarApp();
        }  else if (id == R.id.doar) {

            startActivity(new Intent(this , Donativo.class));
        } else if (id == R.id.contacto) {

            final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
            mView = getLayoutInflater().inflate(R.layout.contacto, null);


            mBuilder.setView(mView);
            dialog = mBuilder.create();
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();

            final EditText ed_messagem = mView.findViewById(R.id.ed_mensagem);
            Button btn_enviar = mView.findViewById(R.id.btn_enviar);

            btn_enviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (new VerificarCampos().isCampoVazio(ed_messagem.getText().toString())){
                        ed_messagem.setHintTextColor(Color.RED);
                    }else {
                        databaseReference.child("Mensagem").child(UUID.randomUUID().toString()).setValue(ed_messagem.getText().toString());
                        databaseReference.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                ed_messagem.setText("");
                                ed_messagem.setHint("mensagem enviado");
                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            });
            ImageView imageView = mView.findViewById(R.id.id_cancel);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;


    }



    public void setOnline(final String mensa) {
        //esse metodo insere no banco de dados o estado do usuario se esta online ou offline //

        databaseReference.child("Usuario").child(id_user).child("dados").child("estado").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Usuario usuario1 = objSnapshot.getValue(Usuario.class);
                    usuario1.setEstado(mensa);
                    databaseReference.child("Usuario").child(id_user).child("dados").child("dados").setValue(usuario1);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String data(){
        Date date = new Date();
        SimpleDateFormat format  =  new SimpleDateFormat("dd/MM/yyyy");
        String data = format.format(date);
        format  =  new SimpleDateFormat("hh:mm");
        String time = format.format(date);

        return data;
    }



    private void dialogCategoria(){

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mView = getLayoutInflater().inflate(R.layout.dialog_categorias, null);

        ImageView img_voltar  = mView.findViewById(R.id.dialog_voltar);
        img_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();


        ListView listView_categorias  = mView.findViewById(R.id.list_item);

        categoriaPrincipal  = getResources().getStringArray(R.array.categorias_de_produtos);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this , android.R.layout.simple_list_item_1 , categoriaPrincipal);
        listView_categorias.setAdapter(adapter);

        listView_categorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                categoria = (String)  adapterView.getItemAtPosition(position);

                switch (position){

                    case 0:
                        //  <item>Acessórios para veículos</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_acessorios_para_veiculos);

                        break;
                    case 1:

                        //  <item>Agro , Indústria e Comércio</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_angro_industria);

                        break;

                    case 2:
                        // <item>alimentos</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_alimentos_bebidas);


                        break;
                    case 3:
                        //  <item>Animais</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_animais);

                        break;
                    case 4:
                        /// <item>Antiguidade e Colecções</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_antiguidades);

                        break;
                    case 5:
                        // <item>Arte , Papelaria e Armarinho</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categorias_artes_papelaria);

                        break;
                    case 6:

                        //<item>Bebés</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_alimentos_bebidas);

                        break;
                    case 7:
                        // <item>Beleza e cuidado pessoal</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_beleza_quidados_pessoais);


                        break;
                    case 8:

                        // <item>Brinquedos e Hobbies</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_brinquedos_hobies);

                        break;

                    case 9:

                        //<item>Calçados ,Roupa e Bolsas</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_calcados_roupa);

                        break;

                    case 10:
                        // <item>Casa , Moveis e Decoração</item>
                        sub_categoria_array = getResources().getStringArray(R.array.subcategoria_casas_decoracao);

                        break;

                    case 11:
                        //<item>Carros, Motos e Outros</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categorias_carros);
                        break;

                    case 12:
                        // <item>Telfones e Tablets</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_telfones_tables);
                        break;

                    case 13:

                        //<item>Câmaras e Acessórios</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categorias_cameras_acessorios);
                        break;

                    case 14:
                        ///item>Electrodomésticos</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_eletrodomesticos);
                        break;

                    case 15:

                        // <item>Electrónicos , Áudio e Vídeos</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_eletronicos);
                        break;

                    case 16:
                        //<item>Esporte e Fitness</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_esporte_fitness);
                        break;

                    case 17:

                        //<item>Ferramentas e Construção</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_ferramentas_e_construcao);
                        break;

                    case 18:

                        //<item>Festas</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_festas);
                        break;

                    case 19:

                        //<item>Games</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_games);
                        break;

                    case 20:

                        //<item>Imóveis</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categorias_imoeis);
                        break;

                    case 21:
                        //<item>Informática</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_informatica);
                        break;

                    case 22:
                        //<item>Ingressos</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_ingressos);
                        break;

                    case 23:

                        // <item>Instrumentos Musicais</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_instrumentos_musicais);
                        break;

                    case 24:
                        //<item>Jóias e Relógios</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_joias);
                        break;

                    case 25:

                        //<item>Livros, Revistas e Comics</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_livros_revistas);
                        break;

                    case 26:

                        // <item>Música ,Filmes e Seriados</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_musica_filmes);
                        break;

                    case 27:

                        // <item>Animais</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_animais);
                        break;

                    case 28:
                        //  <item>Saúde</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_saude);
                        break;

                    case 29:
                        //  <item>Serviços</item>
                        sub_categoria_array = getResources().getStringArray(R.array.sub_categoria_servicos);
                        break;

                }

                dialog.dismiss();

                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                mView = getLayoutInflater().inflate(R.layout.dialog_sub_categorias, null);

                ImageView img_voltar = mView.findViewById(R.id.dialog_voltar);
                img_voltar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        dialogCategoria();
                    }
                });



                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();


                ListView listView_sub_categorias  = mView.findViewById(R.id.list_item);

                final ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this , android.R.layout.simple_list_item_1 , sub_categoria_array);
                listView_sub_categorias.setAdapter(adapter);

                listView_sub_categorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        sub_categoria = (String)  adapterView.getItemAtPosition(i);

                        Intent intent = new Intent(MainActivity.this , CategoriaResult.class);
                        intent.putExtra("categoria" , categoria);
                        intent.putExtra("sub_categoria" ,sub_categoria);
                        dialog.dismiss();
                        startActivity(intent);


                    }
                });


            }
        });


    }

    private void sub_categoria_method() {


    }


    private void addNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Hello my name is agostinho dos santos")
                .setContentText("esse e content");

        Intent intent = new Intent(this , MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent ,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 , builder.build());


    }

    @Override
    protected void onRestart() {


        super.onRestart();
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
