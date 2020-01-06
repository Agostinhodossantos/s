package smartshop.co.mz.publicar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import smartshop.co.mz.ui.MainActivity;
import smartshop.co.mz.R;
import smartshop.co.mz.ui.VerificarCampos;
import smartshop.co.mz.model.DescricaoProduto;
import smartshop.co.mz.model.Produto;
import smartshop.co.mz.model.ProdutoImagem;

public class Editar extends AppCompatActivity {

    final ProdutoImagem imagem = new ProdutoImagem();
    private TextInputEditText tv_titulo , tv_preco , tv_cor , tv_marca , tv_estado , tv_bairo , tv_contacto , tv_descricao;
    private Button btn_publicar , btn_promover;
    private Spinner sp_provincia , sp_distrito , sp_categoria , sp_subcategoria;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String titulo;
    private String preco;
    boolean parrr = false;
    final int finalI = 0;
    private String cor;
    private String estado;
    private String marca;
    private String contacto;
    private String bairo;
    private String descricao;
    private String categoria;
    private String provincia;
    private String distrito;
    private String longitude , latitude;
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
    private String[] localizacaoCabo_Del;
    private String[] categoriaPrincipal;
    private String[] sub_categoria_array;
    private String[] nullArray = new String[1];
    private String[] localizacaoDistrito;
    private String sub_categoria = "";
    final static String APP_PREFS = "app_prefs";
    final static String id_KEY = "id";
    private SharedPreferences prefs;
    private String id = "";
    private CheckBox checkBox;
    private long  position;
    Produto produto   = new Produto();
    DescricaoProduto descricaoProduto = new DescricaoProduto();
    String id_produto  = "";
    String id_user = "";
    String img = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacoes_publicar);


        prefs = getSharedPreferences(APP_PREFS , MODE_PRIVATE);
        id = prefs.getString(id_KEY , null);


        inicializarFirebase();
        new Thread(contarProdutos).start();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null){
            id_produto = bundle.getString("id_produto");
            id_user = bundle.getString("id_user");
        }


        tv_titulo = findViewById(R.id.tv_titulo);
        tv_preco = findViewById(R.id.tv_preco);
        tv_cor = findViewById(R.id.tv_color);
        tv_estado = findViewById(R.id.tv_estado);
        tv_marca = findViewById(R.id.tv_marca);
        tv_contacto = findViewById(R.id.tv_contacto);
        tv_bairo  = findViewById(R.id.tv_bairo);
        tv_descricao = findViewById(R.id.tv_descricao);
        sp_distrito = findViewById(R.id.sp_distritos);
        sp_provincia = findViewById(R.id.sp_provincias);
        sp_categoria = findViewById(R.id.sp_categoria);
        sp_subcategoria = findViewById(R.id.sp_sub_categoria);
        btn_publicar = findViewById(R.id.btn_publicar);
        checkBox = findViewById(R.id.cb_negociavel);


        // btn_promover = (Button) findViewById(R.id.btn_promover);
        selecionarCategoria();
        selecionarPronvicia();


        btn_publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificarCampos();
            }
        });

    }


    private Runnable contarProdutos = new Runnable() {
        @Override
        public void run() {
            databaseReference.child("Produto").child(id_produto).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                       produto = objSnapshot.getValue(Produto.class);
                    }

                    tv_titulo.setText(produto.getNome());
                    tv_preco.setText(produto.getPreco());


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            databaseReference.child("Usuario").child(id_user).child("Produto").child(id_produto).child("Descricao").addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                         descricaoProduto = objSnapshot.getValue(DescricaoProduto.class);
                    }
                    if (descricaoProduto != null){

                        tv_descricao.setText(descricaoProduto.getDescricao());
                        tv_contacto.setText(descricaoProduto.getContacto());

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    };


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

    private void selecionarPronvicia() {
        //este arrayList guarda a referencia de cada array de provincias //


        inicializarLocalizacao();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, localizacaoPronvincias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_provincia.setAdapter(adapter);

        sp_provincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                provincia = parent.getSelectedItem().toString();



                switch (position){

                    case 0:
                        //        <item>Maputo</item>
                        localizacaoDistrito = localizacaoMaputo;
                        break;
                    case 1:
                        //        <item>Gaza</item>
                        localizacaoDistrito = localizacaoGaza;
                        break;
                    case 2:
                        //        <item>Inhambane</item>
                        localizacaoDistrito = localizacaoInhabane;
                        break;
                    case 3:
                        //        <item>Sofala</item>
                        localizacaoDistrito = localizacaoSofala;
                        break;
                    case 4:
                        //        <item>Manica</item>
                        localizacaoDistrito = localizacaoManica;
                        break;
                    case 5:
                        //        <item>Tete</item>
                        localizacaoDistrito = localizacaoTete;
                        break;
                    case 6:
                        //        <item>Zambezia</item>
                        localizacaoDistrito = localizacaoZambezia;
                        break;
                    case 7:
                        //        <item>Nampula</item>
                        localizacaoDistrito = localizacaoNampula;
                        break;
                    case 8:
                        //        <item>Niassa</item>
                        localizacaoDistrito = localizacaoNiassa;
                        break;
                    case 9:
                        //        <item>Cabo Delgado</item>
                        localizacaoDistrito = localizacaoCabo_Del;
                        break;
                    case 10:
                        break;
                    default:
                        break;
                }

                adapter = new ArrayAdapter<String>(Editar.this, android.R.layout.simple_spinner_item, localizacaoDistrito);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_distrito.setAdapter(adapter);

                sp_distrito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        distrito = adapterView.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void verificarCampos() {
        titulo   = tv_titulo.getText().toString().trim();
        preco    = tv_preco.getText().toString().trim();
        cor      = tv_cor.getText().toString().trim();
        estado   = tv_estado.getText().toString().trim();
        marca    = tv_marca.getText().toString().trim();
        contacto = tv_contacto.getText().toString().trim();
        bairo    = tv_bairo.getText().toString().trim();
        descricao = tv_descricao.getText().toString().trim();

        VerificarCampos campos = new VerificarCampos();

        if (campos.isCampoVazio(titulo)){
            tv_titulo.requestFocus();
            tv_titulo.setHintTextColor(Color.RED);
        }else if(campos.isCampoVazio(preco)){
            tv_preco.requestFocus();
            tv_preco.setHintTextColor(Color.RED);
        }else if(campos.isCampoVazio(cor)){
            tv_cor.requestFocus();
            tv_cor.setHintTextColor(Color.RED);
        }else if(campos.isCampoVazio(estado)){
            tv_estado.requestFocus();
            tv_estado.setHintTextColor(Color.RED);
        }else if(campos.isCampoVazio(marca)){
            tv_marca.requestFocus();
            tv_marca.setHintTextColor(Color.RED);
        }else if(campos.isCampoVazio(contacto)) {
            tv_contacto.requestFocus();
            tv_contacto.setHintTextColor(Color.RED);
        }else if(campos.isCampoVazio(bairo)){
            tv_bairo.requestFocus();
            tv_bairo.setHintTextColor(Color.RED);
        }else if (campos.isCampoVazio(descricao)){
            tv_descricao.requestFocus();
            tv_descricao.setHintTextColor(Color.RED);
        }else{
            inserirDados();
        }

    }

    private void inserirDados() {

        chamarDialog();


        produto.setNome(titulo);
        produto.setUid(produto.getUid());
        produto.setIdUser(produto.getIdUser());
        produto.setCategoria(produto.getCategoria());
        produto.setSub_categoria(produto.getSub_categoria());
        produto.setProvincia(produto.getSub_categoria());
        produto.setDistrito(produto.getDistrito());
        produto.setTempo(data());
        produto.setPreco(preco);
        produto.setPosicao(produto.getPosicao());
        produto.setImg(produto.getImg());



        //depois de inserir os dados  no produto vais inseir //

        descricaoProduto.setUid(descricaoProduto.getUid());

        descricaoProduto.setBairo(bairo);
        descricaoProduto.setDescricao(descricao);
        descricaoProduto.setContacto(contacto);
        descricaoProduto.setLatitude("3");
        descricaoProduto.setLongitude("4");
        if ( checkBox.isChecked()){
            descricaoProduto.setNegociavel("sim");
        }else {
            descricaoProduto.setNegociavel("Nao");
        }



        databaseReference.child("Produto").child(produto.getUid()).setValue(produto);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                databaseReference.child("Usuario").child(id).child("Produto").child(produto.getUid()).setValue(produto);
                databaseReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {



                        databaseReference.child("Usuario").child(id).child("Produto").child(produto.getUid()).child("Descricao").child(produto.getUid()).setValue(descricaoProduto);

                        databaseReference.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                passaFim();


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

    private void chamarDialog() {
        View mView;
        AlertDialog dialog;

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mView = getLayoutInflater().inflate(R.layout.dialog_progress, null);


       TextView n = mView.findViewById(R.id.tv_progress);
       n.setText("Actualizando");

        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.setCancelable(false);
        dialog.show();
    }




    private void inicializarFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }


    private void passaFim() {



                Intent intent = new Intent(Editar.this , MainActivity.class );
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

            }






    private void selecionarCategoria() {
        categoriaPrincipal = getResources().getStringArray(R.array.categorias_de_produtos);

        adapter = new ArrayAdapter<String>(Editar.this, android.R.layout.simple_spinner_item, categoriaPrincipal);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_categoria.setAdapter(adapter);

        sp_categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    adapter = new ArrayAdapter<String>(
                            Editar.this, android.R.layout.simple_spinner_item, sub_categoria_array );
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_subcategoria.setAdapter(adapter);

                    sp_subcategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private String data(){
        Date date = new Date();
        SimpleDateFormat format  =  new SimpleDateFormat("dd/MM/yyyy");
        return format.format(date);
    }


}
