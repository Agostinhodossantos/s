package smartshop.co.mz.publicar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.crypto.Mac;

import smartshop.co.mz.Login;
import smartshop.co.mz.MainActivity;
import smartshop.co.mz.R;
import smartshop.co.mz.VerificarCampos;
import smartshop.co.mz.model.Descricao;
import smartshop.co.mz.model.DescricaoProduto;
import smartshop.co.mz.model.Produto;
import smartshop.co.mz.model.ProdutoImagem;
import smartshop.co.mz.model.dataHelp;
import smartshop.co.mz.publicar.pick_img.model.Picture;
import smartshop.co.mz.rv.CustomAdapter;
import smartshop.co.mz.rv.Rv_produtos;

public class InformacoesPublicar extends AppCompatActivity {

    List<Descricao> AcesdeCarroseCaminhonetes  ;
    List<Descricao> AcesdeMotoseQuadriciclos   ;
    List<Descricao> AcesePeçasparaCamiões      ;
    List<Descricao> AcessóriosePeçasNáuticos   ;
    List<Descricao> Ferramentas                ;
    List<Descricao> GNV                        ;
    List<Descricao> LimpezaAutomotivo          ;
    List<Descricao> NavegadoresGPS             ;
    List<Descricao> PeçasdeCarroseCaminhonetes ;
    List<Descricao> PeçasdeMaquinariaPesada    ;
    List<Descricao> PeçasdeMotoseQuadriciclos  ;
    List<Descricao> Perfomance                 ;
    List<Descricao> Pneus                      ;
    List<Descricao> Rodas                      ;
    List<Descricao> SegurançaVeicular          ;
    List<Descricao> ServiçosProgramados        ;
    List<Descricao> SomAutomotivo              ;
    List<Descricao> Tuning                     ;
    List<Descricao> ArquitecturaeDesenho       ;
    List<Descricao> Embalagem                  ;
    List<Descricao> EquipamentoComercial       ;
    List<Descricao> EquipamentodeSegurança     ;
    List<Descricao> Equipamentoparaescritórios ;
    List<Descricao> EquipamentoparaIndústrias  ;
    List<Descricao> IndústriaAgropecuaria      ;
    List<Descricao> IndústriaGráficaeImpressão ;
    List<Descricao> IndústriaTêxtil            ;
    List<Descricao> MaterialdePromoção         ;
    List<Descricao> Bebidas                    ;
    List<Descricao> Comestíveis                ;
    List<Descricao> ComidaPreparadaeCatering   ;
    List<Descricao> Frescos                    ;
    List<Descricao> AnifibioseRépteis          ;
    List<Descricao> AveiroeAcessórios          ;
    List<Descricao> Cachorros                  ;
    List<Descricao> Cavalos                    ;
    List<Descricao> Coelhos                    ;
    List<Descricao> Gatos                      ;
    List<Descricao> Insectos                   ;
    List<Descricao> Peixes                     ;
    List<Descricao> Roedores                   ;
    List<Descricao> Antiguidades               ;
    List<Descricao> CédulaseMoedas             ;
    List<Descricao> ColecionaveiseEsportes     ;
    List<Descricao> Esculturas                 ;
    List<Descricao> Filatelia                  ;
    List<Descricao> MilateriaeAfins            ;
    List<Descricao> Posteres                   ;
    List<Descricao> Arte                       ;
    List<Descricao> ArtigosdeArmarinho         ;
    List<Descricao> EspelhosdeMosaico          ;
    List<Descricao> Formasparasabonetes        ;
    List<Descricao> InsumosparaFazervelas      ;
    List<Descricao> MateriaisEscolares         ;
    List<Descricao> PeçasparaChaveiros         ;
    List<Descricao> AlimentaçãoparaBebé        ;
    List<Descricao> AndadoreseMiniveículos     ;
    List<Descricao> ArtigosdeBebéparabanho     ;
    List<Descricao> ArtigosdeMaternidade       ;
    List<Descricao> BrinquedosparaBebés        ;
    List<Descricao> Cercadinho                 ;
    List<Descricao> ChupetaseMordedores        ;
    List<Descricao> HigieneeCuidadoscomobebé   ;
    List<Descricao> PasseiodoBebé              ;
    List<Descricao> RoupasdeBebé               ;
    List<Descricao> SaúdedeBebé                ;
    List<Descricao> SegurançaparaBebé          ;
    List<Descricao> ArtigosparaCabeleireiros   ;
    List<Descricao> Barbearia                  ;
    List<Descricao> CuidadoscomaPele           ;
    List<Descricao> CuidadoscomoCabelo         ;
    List<Descricao> Depilação                  ;
    List<Descricao> ElectrodomésticoseBeleza   ;
    List<Descricao> Farmácia                   ;
    List<Descricao> HigienePessoal             ;
    List<Descricao> ManicureePedicure          ;
    List<Descricao> Maquiagem                  ;
    List<Descricao> Perfumes                   ;
    List<Descricao> SplashCorporal             ;
    List<Descricao> TratamentosdeBeleza        ;
    List<Descricao> AlbunseFigurinhas          ;
    List<Descricao> Anti_stresseEngenho        ;
    List<Descricao> ArLivreePlayground         ;
    List<Descricao> BlocoseConstrução          ;
    List<Descricao> BonecoseBonecas            ;
    List<Descricao> BrinquedosdeAguaePraia     ;
    List<Descricao> BrinquedosdePegadinhas     ;
    List<Descricao> BrinquedosdeProfissões     ;
    List<Descricao> BrinquedosparaBebes        ;
    List<Descricao> CasinhaseBarracas          ;
    List<Descricao> Desenho_PinturaeArtesanatos;
    List<Descricao> FontocheseMarionetas       ;
    List<Descricao> Hobbies                    ;
    List<Descricao> IntrumentosMusicais        ;
    List<Descricao> JogosdeSalão               ;
    List<Descricao> JogosdeTabuleiroeCartas    ;
    List<Descricao> JogosEletronicos           ;
    List<Descricao> LancadoresdeBrinquedo      ;
    List<Descricao> MesaseCadeiras             ;
    List<Descricao> MiniVeiculoseBicicletas    ;
    List<Descricao> Pelucias                   ;
    List<Descricao> PescinaseInflaveis         ;
    List<Descricao> UpaUpa                     ;
    List<Descricao> VeiculosdeBrinquedo        ;
    List<Descricao> AcessóriosdaModa           ;
    List<Descricao> BegagemeBolsas             ;
    List<Descricao> BermudaseShorts            ;
    List<Descricao> Blusas                     ;
    List<Descricao> Calcas                     ;
    List<Descricao> Camisas                    ;
    List<Descricao> Camisetas                  ;
    List<Descricao> CamisolaseMoletons         ;
    List<Descricao> Casacos                    ;
    List<Descricao> Leggings                   ;
    List<Descricao> LotesdeRoupa               ;
    List<Descricao> Macacão                    ;
    List<Descricao> ModaÍntimaeLingerie        ;
    List<Descricao> ModaPraia                  ;
    List<Descricao> RoupadeDança               ;
    List<Descricao> RoupaparaBebés             ;
    List<Descricao> Saías                      ;
    List<Descricao> Sapatos                    ;
    List<Descricao> Sueteres_CardiganseColetes ;
    List<Descricao> Ternos                     ;
    List<Descricao> Uniformes                  ;
    List<Descricao> Vestidos                   ;
    List<Descricao> Banheiros                  ;
    List<Descricao> ColchoeseCamasBox          ;
    List<Descricao> CortinaseAcessórios        ;
    List<Descricao> CuidadosdeCasaeLavandaria  ;
    List<Descricao> EnfeiteseDecoraçãodeCasa   ;
    List<Descricao> IluminaçãoResidecial       ;
    List<Descricao> JardinseExteriores         ;
    List<Descricao> MóveisparaCasa             ;
    List<Descricao> OrganizaçãoparaCasa        ;
    List<Descricao> SegurançaparaCasa          ;
    List<Descricao> TêxteisdeCasaeDecoração    ;
    List<Descricao> UtilidadesDomésticas       ;
    List<Descricao> AcessóriosparaCelulares    ;
    List<Descricao> TelfoneseTablets           ;
    List<Descricao> ÓculosdeRealidadeVirtual   ;
    List<Descricao> PeçasparaCelular           ;
    List<Descricao> SmartwatcheseAcessórios    ;
    List<Descricao> TelefoniaFixaeSemFio       ;
    List<Descricao> VoIp                       ;
    List<Descricao> WalkieTalkies              ;
    List<Descricao> Acessóriosparacameras      ;
    List<Descricao> AlbunsePorta_retratos      ;
    List<Descricao> Cabos                      ;
    List<Descricao> Cameras                    ;
    List<Descricao> DroneseAcessorios          ;
    List<Descricao> EquipamentosdeRevelacao    ;
    List<Descricao> Filmadoras                 ;
    List<Descricao> IntrumentosÓpticos         ;
    List<Descricao> LenteseFiltros             ;
    List<Descricao> PeçasparaCameras           ;
    List<Descricao> AreVentilação              ;
    List<Descricao> BebedoresePurificadores    ;
    List<Descricao> Cuidadopessoal             ;
    List<Descricao> FornoeFogões               ;
    List<Descricao> GeladeiraseFreezers        ;
    List<Descricao> Lavadores                  ;
    List<Descricao> PecaseAcessórios           ;
    List<Descricao> PequenosEletrodomesticos   ;
    List<Descricao> AcessóriosparaÁudioeVideo  ;
    List<Descricao> AparelhosDVDeBluray        ;
    List<Descricao> Áudio                      ;
    List<Descricao> BolsaseEstojos             ;
    List<Descricao> ControlesRemotos           ;
    List<Descricao> MediaStreaming             ;
    List<Descricao> PecaseComponetesEletricos  ;
    List<Descricao> PeçasparaTv                ;
    List<Descricao> PilhaseCarregadores        ;
    List<Descricao> ProjetoreseTelas           ;
    List<Descricao> tv                         ;
    List<Descricao> ArtesMarcciaseBoxe         ;
    List<Descricao> Badminton                  ;
    List<Descricao> Baseball                   ;
    List<Descricao> Basquete                   ;
    List<Descricao> Camping_CacaePesca         ;
    List<Descricao> Canoas_CaiqueseInflaveis   ;
    List<Descricao> Ciclismo                   ;
    List<Descricao> Equitação                  ;
    List<Descricao> Esgrima                    ;
    List<Descricao> EsquieSnowboard            ;
    List<Descricao> FitnesseMusculacao         ;
    List<Descricao> Futebol                    ;
    List<Descricao> FutebolAmericano           ;
    List<Descricao> Golfe                      ;
    List<Descricao> Handebol                   ;
    List<Descricao> Hockey                     ;
    List<Descricao> Jogosdesalao               ;
    List<Descricao> Kitesurf                   ;
    List<Descricao> Mergulho                   ;
    List<Descricao> ModaFitness                ;
    List<Descricao> MonitoreseRelogios         ;
    List<Descricao> Natação                    ;
    List<Descricao> Paintball                  ;
    List<Descricao> Parapente                  ;
    List<Descricao> Patin_GinasticaeDanca      ;
    List<Descricao> PatineteseScooters         ;
    List<Descricao> PilateseYoga               ;
    List<Descricao> Rapel_MontanhiscoeEscalada ;
    List<Descricao> Rugby                      ;
    List<Descricao> SkateboardeSandboard       ;
    List<Descricao> Slackline                  ;
    List<Descricao> SuplementoseShakers        ;
    List<Descricao> SurfeBodyboard             ;
    List<Descricao> TeniseSquash               ;
    List<Descricao> Voleiball                  ;
    List<Descricao> Wakeboard                  ;
    List<Descricao> Windsurfe                  ;
    List<Descricao> Aberturas                  ;
    List<Descricao> Construção                 ;
    List<Descricao> Encanamento                ;
    List<Descricao> EnergiaEletrica            ;
    List<Descricao> LojadeTintas               ;
    List<Descricao> MobiliárioparaBanheiros    ;
    List<Descricao> MobiliárioparaCozinhas     ;
    List<Descricao> PisoseRejuntes             ;
    List<Descricao> Artigosparafestas          ;
    List<Descricao> Convites                   ;
    List<Descricao> DecoraçãodeFesta           ;
    List<Descricao> DescartáveisparaFestas     ;
    List<Descricao> Espuma_SerpentinaseConfete ;
    List<Descricao> Fantasias                  ;
    List<Descricao> KitsImprimiveisparaFestas  ;
    List<Descricao> Lembrancinhas              ;
    List<Descricao> PlaquinhasparaFestas       ;
    List<Descricao> AcessóriosparaConsolas     ;
    List<Descricao> Consolas                   ;
    List<Descricao> FliperamaseArcades         ;
    List<Descricao> PecasparaConsolas          ;
    List<Descricao> VideoGames                 ;
    List<Descricao> AcessóriosdeAntiestatica   ;
    List<Descricao> Armazenamento              ;
    List<Descricao> CaboseHubsUSB              ;
    List<Descricao> ComponentesparaPC          ;
    List<Descricao> ConectividadeeRedes        ;
    List<Descricao> EstabilizadoreseNoBreaks   ;
    List<Descricao> Impressão                  ;
    List<Descricao> LeitoreseScanners          ;
    List<Descricao> LimpesadePCs               ;
    List<Descricao> MonitoreseAcessórios       ;
    List<Descricao> Mouses_TecladoseControles  ;
    List<Descricao> PalmseHandhelds            ;
    List<Descricao> Computador                 ;
    List<Descricao> Laptop                     ;
    List<Descricao> PortaCDs_CaixasEnvelopes   ;
    List<Descricao> PortateiseAcessorios       ;
    List<Descricao> ProjectoreseTelas          ;
    List<Descricao> Software                   ;
    List<Descricao> TableseAcessórios          ;
    List<Descricao> WebcamseÁudioparaPC        ;
    List<Descricao> EventosEsportivos          ;
    List<Descricao> Exposição                  ;
    List<Descricao> Ingressos                  ;
    List<Descricao> Shows                      ;
    List<Descricao> TeatroeCultura             ;
    List<Descricao> BateriasePercussão         ;
    List<Descricao> CaixasdeSom                ;
    List<Descricao> EquipamentosparaDJ         ;
    List<Descricao> EstudiodeGravação          ;
    List<Descricao> IntrumentosdeCorda         ;
    List<Descricao> InstrumentosdeSopro        ;
    List<Descricao> Metronomos                 ;
    List<Descricao> MicrofoneseAmplificadores  ;
    List<Descricao> PartituraseLetras          ;
    List<Descricao> PedaiseAcessórios          ;
    List<Descricao> PianoeTeclados             ;
    List<Descricao> AcessóriosParaRelogios     ;
    List<Descricao> ArtigosdeJoalharia         ;
    List<Descricao> CanetaseLapiseirasdeluxo   ;
    List<Descricao> JóiaseBijuterias           ;
    List<Descricao> MonitoreseCronómetros      ;
    List<Descricao> Pedraspreciosas            ;
    List<Descricao> Piercings                  ;
    List<Descricao> PortaJóias                 ;
    List<Descricao> Relógios                   ;
    List<Descricao> Smartwatches               ;
    List<Descricao> Catalogos                  ;
    List<Descricao> HQs                        ;
    List<Descricao> Livros                     ;
    List<Descricao> Revistas                   ;

    ProdutoImagem imagem = new ProdutoImagem();
    private TextInputEditText tv_titulo , tv_preco , tv_bairo , tv_contacto , tv_descricao;
    private Button btn_publicar , btn_promover;
    private Spinner sp_provincia , sp_distrito , sp_categoria , sp_subcategoria;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    List<String> listImg = new ArrayList<>();
    List<Descricao> descricaoList = new ArrayList<>();
    String arrayList[];
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
    private List<ProdutoImagem> imagemList = new ArrayList<>();
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
    List<Picture> picturesSelected = new ArrayList<>();
    Produto produto   = new Produto();
    String url;
    StorageReference mStorageRef;
    String rand;
    String img = "";
    View mView;
    private Button btn;
    private RecyclerView recyclerView;
    private CustomAdapter customAdapter;
    public ArrayList<Descricao> editModelArrayList;

    AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacoes_publicar);

        rand = UUID.randomUUID().toString();

        prefs = getSharedPreferences(APP_PREFS , MODE_PRIVATE);
        id = prefs.getString(id_KEY , null);

        Intent intent=getIntent();
        picturesSelected=intent.getParcelableArrayListExtra("listpicture");

        mStorageRef = FirebaseStorage.getInstance().getReference("Imagem");

        inicializarFirebase();
        new Thread(contarProdutos).start();


        inicializarDescricaoData();

        tv_titulo = findViewById(R.id.tv_titulo);
        tv_preco = findViewById(R.id.tv_preco);
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

    private void inicializarDescricaoData() {
         AcesdeCarroseCaminhonetes  = new ArrayList<>();
         AcesdeCarroseCaminhonetes.add( new Descricao(getString(R.string.marca) ,""));
         AcesdeCarroseCaminhonetes.add( new Descricao("Modelo" ,""));
         //
         AcesdeMotoseQuadriciclos   = new ArrayList<>();
         AcesdeMotoseQuadriciclos.add( new Descricao("Marca" , ""));
         AcesdeMotoseQuadriciclos.add( new Descricao("Modelo" , ""));
         AcesdeMotoseQuadriciclos.add( new Descricao("Origem" , ""));
         AcesdeMotoseQuadriciclos.add( new Descricao("Numero de Peca" , ""));

         //fim
         AcesePeçasparaCamiões      = new ArrayList<>();
         AcesePeçasparaCamiões =  AcesdeMotoseQuadriciclos;
         //fim
         AcessóriosePeçasNáuticos   = new ArrayList<>();
         AcessóriosePeçasNáuticos   = AcesdeMotoseQuadriciclos;
         //fim
         Ferramentas                = new ArrayList<>();
         Ferramentas.add( new Descricao("Marca" , ""));
         Ferramentas.add( new Descricao("Modelo" , ""));
         //fim
         GNV                        = new ArrayList<>();
         GNV.add( new Descricao("Marca" , ""));
         GNV.add( new Descricao("Geracao" , ""));
         GNV.add( new Descricao("Modelo" , ""));
         //fim
         LimpezaAutomotivo          = new ArrayList<>();
         LimpezaAutomotivo.add( new Descricao("Marca" ,""));
         LimpezaAutomotivo.add(new Descricao("Numero da Unidade",""));
         LimpezaAutomotivo.add(new Descricao("Peso da Unidade",""));
         LimpezaAutomotivo.add(new Descricao("Volume da Unidade",""));

         NavegadoresGPS             = new ArrayList<>();
         NavegadoresGPS.add( new Descricao("Marca", ""));
         NavegadoresGPS.add( new Descricao("Modelo", ""));
         NavegadoresGPS.add( new Descricao("Cor", ""));
         NavegadoresGPS.add( new Descricao("Tipo", ""));

         PeçasdeCarroseCaminhonetes = new ArrayList<>();
         PeçasdeCarroseCaminhonetes = GNV;

         PeçasdeMaquinariaPesada    = new ArrayList<>();
         PeçasdeMaquinariaPesada = GNV;

         PeçasdeMotoseQuadriciclos  = new ArrayList<>();
         PeçasdeMotoseQuadriciclos  = AcesdeMotoseQuadriciclos;

         Perfomance                 = new ArrayList<>();
         Perfomance.add(new Descricao("Marca", ""));
         Pneus                      = new ArrayList<>();
         Pneus.add( new Descricao("Marca" , ""));
         Pneus.add( new Descricao("Linha" , ""));
         Pneus.add( new Descricao("Modelo" , ""));
         Pneus.add( new Descricao("Tipo de Terreno" , ""));
         Pneus.add( new Descricao("Diametro da roda" , ""));
         Pneus.add( new Descricao("Diametro Externo" , ""));
         Pneus.add( new Descricao("Tipo de veiculo" , ""));
         Rodas                      = new ArrayList<>();
         Rodas.add( new Descricao("Marca" , ""));
         Rodas.add( new Descricao("Modelo",""));
         Rodas.add( new Descricao("Diametro",""));
         Rodas.add( new Descricao("Estado",""));
         Rodas.add( new Descricao("Origem",""));

         SegurançaVeicular          = new ArrayList<>();
         SegurançaVeicular.add( new Descricao("Marca" ,""));
         SegurançaVeicular.add( new Descricao("Modelo" ,""));

         ServiçosProgramados        = new ArrayList<>();
         ServiçosProgramados.add( new Descricao("" ,""));
         ServiçosProgramados = SegurançaVeicular;
         SomAutomotivo              = new ArrayList<>();
         SomAutomotivo.add( new Descricao("Marca" , ""));
         SomAutomotivo.add( new Descricao("Estado" , ""));
         SomAutomotivo.add( new Descricao("Cor" , ""));

         Tuning                     = new ArrayList<>();
         Tuning.add( new Descricao("Estado" ,""));
         Tuning.add( new Descricao("Marca",""));
         Tuning.add( new Descricao("Modelo",""));

         ArquitecturaeDesenho       = new ArrayList<>();
         //ArquitecturaeDesenho.add( new Descricao("",""));
         Embalagem                  = new ArrayList<>();
         EquipamentoComercial       = new ArrayList<>();
         EquipamentodeSegurança     = new ArrayList<>();
         EquipamentodeSegurança = Tuning;

         Equipamentoparaescritórios = new ArrayList<>();
         Equipamentoparaescritórios.add( new Descricao("Marca" , ""));
         Equipamentoparaescritórios.add( new Descricao("Modelo" , ""));
         Equipamentoparaescritórios.add( new Descricao("Estado" , ""));
         Equipamentoparaescritórios.add( new Descricao("Cor" , ""));

         EquipamentoparaIndústrias  = new ArrayList<>();
         EquipamentoparaIndústrias.add( new Descricao("Marca" , ""));
         EquipamentoparaIndústrias.add( new Descricao("Origem" , ""));
         EquipamentoparaIndústrias.add( new Descricao("Estado" , ""));

         IndústriaAgropecuaria      = new ArrayList<>();

         IndústriaGráficaeImpressão = new ArrayList<>();
         IndústriaTêxtil            = new ArrayList<>();
         MaterialdePromoção         = new ArrayList<>();
         Bebidas                    = new ArrayList<>();
         Bebidas.add( new Descricao("Marca" ,""));
         Bebidas.add( new Descricao("Litros" , ""));

         Comestíveis                = new ArrayList<>();
         Comestíveis.add( new Descricao("Tipo" , ""));
         ComidaPreparadaeCatering   = new ArrayList<>();
         ComidaPreparadaeCatering.add( new Descricao("Tipo",""));
         Frescos                    = new ArrayList<>();
         Frescos.add( new Descricao("Tipo" ,""));
        // Frescos.add( new Descricao("Tipo" ,""));
         AnifibioseRépteis          = new ArrayList<>();
         AveiroeAcessórios          = new ArrayList<>();
         Cachorros                  = new ArrayList<>();
         Cachorros.add( new Descricao("Raca" , ""));
         Cachorros.add( new Descricao("Idade" , ""));
         Cachorros.add( new Descricao("Origem" , ""));
         Cachorros.add(new Descricao("Gênero" , ""));
         Cachorros.add(new Descricao("Esperança de vida" , ""));


         Cavalos                    = new ArrayList<>();
         Cavalos = Cachorros;
         Coelhos                    = new ArrayList<>();
         Coelhos = Cavalos;
         Gatos                      = new ArrayList<>();
         Gatos.add( new Descricao("Raca",""));
         Gatos.add( new Descricao("Cor" ,""));
         Gatos.add( new Descricao("Origem" ,""));

         Insectos                   = new ArrayList<>();
         Peixes                     = new ArrayList<>();
         Roedores                   = new ArrayList<>();

         Antiguidades               = new ArrayList<>();
         CédulaseMoedas             = new ArrayList<>();
         CédulaseMoedas.add( new Descricao("Tipo" , ""));
         CédulaseMoedas.add( new Descricao("Ano" , ""));
         CédulaseMoedas.add( new Descricao("Estado" , ""));

         ColecionaveiseEsportes     = new ArrayList<>();
         ColecionaveiseEsportes.add( new Descricao("Tipo" , ""));
         Esculturas                 = new ArrayList<>();
         Filatelia                  = new ArrayList<>();
         MilateriaeAfins            = new ArrayList<>();
         Posteres                   = new ArrayList<>();
         Arte                       = new ArrayList<>();
         ArtigosdeArmarinho         = new ArrayList<>();
         ArtigosdeArmarinho.add(new Descricao("Marca",""));
         ArtigosdeArmarinho.add(new Descricao("Comprimento",""));
         ArtigosdeArmarinho.add(new Descricao("Unidade por pacote",""));
          ArtigosdeArmarinho.add(new Descricao("Modelo",""));

         EspelhosdeMosaico          = new ArrayList<>();
         Formasparasabonetes        = new ArrayList<>();
         InsumosparaFazervelas      = new ArrayList<>();
         MateriaisEscolares         = new ArrayList<>();
         MateriaisEscolares.add( new Descricao("Marca" , ""));
         MateriaisEscolares.add( new Descricao("Estado" , ""));

         PeçasparaChaveiros         = new ArrayList<>();
         AlimentaçãoparaBebé        = new ArrayList<>();
         AlimentaçãoparaBebé.add( new Descricao("Idade" , ""));
         AlimentaçãoparaBebé.add( new Descricao("Quantidade" , ""));
         AlimentaçãoparaBebé.add( new Descricao("Origem" , ""));
         AndadoreseMiniveículos     = new ArrayList<>();
         AndadoreseMiniveículos.add( new Descricao("Modelo" ,""));
         AndadoreseMiniveículos.add( new Descricao("Cor" ,""));
         AndadoreseMiniveículos.add( new Descricao("Tamanho" ,""));
         AndadoreseMiniveículos.add( new Descricao("Estado" ,""));
         ArtigosdeBebéparabanho     = new ArrayList<>();
         ArtigosdeBebéparabanho.add( new Descricao("Estado",""));

         ArtigosdeMaternidade       = new ArrayList<>();
         BrinquedosparaBebés        = new ArrayList<>();
         Cercadinho                 = new ArrayList<>();
         ChupetaseMordedores        = new ArrayList<>();
         HigieneeCuidadoscomobebé   = new ArrayList<>();
         PasseiodoBebé              = new ArrayList<>();
         RoupasdeBebé               = new ArrayList<>();
         SaúdedeBebé                = new ArrayList<>();
         SegurançaparaBebé          = new ArrayList<>();
         ArtigosparaCabeleireiros   = new ArrayList<>();
         Barbearia                  = new ArrayList<>();
         CuidadoscomaPele           = new ArrayList<>();
         CuidadoscomoCabelo         = new ArrayList<>();
         Depilação                  = new ArrayList<>();
         ElectrodomésticoseBeleza   = new ArrayList<>();
         Farmácia                   = new ArrayList<>();
         HigienePessoal             = new ArrayList<>();
         ManicureePedicure          = new ArrayList<>();
         Maquiagem                  = new ArrayList<>();
         Perfumes                   = new ArrayList<>();
         SplashCorporal             = new ArrayList<>();
         TratamentosdeBeleza        = new ArrayList<>();
         AlbunseFigurinhas          = new ArrayList<>();
         Anti_stresseEngenho        = new ArrayList<>();
         ArLivreePlayground         = new ArrayList<>();
         BlocoseConstrução          = new ArrayList<>();
         BonecoseBonecas            = new ArrayList<>();
         BrinquedosdeAguaePraia     = new ArrayList<>();
         BrinquedosdePegadinhas     = new ArrayList<>();
         BrinquedosdeProfissões     = new ArrayList<>();
         BrinquedosdeProfissões.add( new Descricao("Tipo",""));
         BrinquedosdeProfissões.add( new Descricao("Estado",""));

         BrinquedosparaBebes        = new ArrayList<>();
         BrinquedosparaBebes = BrinquedosdeProfissões;
         CasinhaseBarracas          = new ArrayList<>();
         Desenho_PinturaeArtesanatos= new ArrayList<>();
         FontocheseMarionetas       = new ArrayList<>();
         Hobbies                    = new ArrayList<>();
         IntrumentosMusicais        = new ArrayList<>();
         IntrumentosMusicais.add( new Descricao("Marca" , ""));
         IntrumentosMusicais.add( new Descricao("Estado" , ""));
         IntrumentosMusicais.add( new Descricao("Tipo" , ""));
         JogosdeSalão               = new ArrayList<>();
         JogosdeTabuleiroeCartas    = new ArrayList<>();
         JogosEletronicos           = new ArrayList<>();
         JogosEletronicos.add( new Descricao("Tipo" ,""));
         JogosEletronicos.add( new Descricao("Para disposetivo" ,""));
         LancadoresdeBrinquedo      = new ArrayList<>();
         MesaseCadeiras             = new ArrayList<>();
         MiniVeiculoseBicicletas    = new ArrayList<>();
         Pelucias                   = new ArrayList<>();
         PescinaseInflaveis         = new ArrayList<>();
         UpaUpa                     = new ArrayList<>();
         VeiculosdeBrinquedo        = new ArrayList<>();
         AcessóriosdaModa           = new ArrayList<>();
         BegagemeBolsas             = new ArrayList<>();
         BermudaseShorts            = new ArrayList<>();
         BermudaseShorts.add( new Descricao("Marca" , ""));
         BermudaseShorts.add( new Descricao("Tamanho" , ""));
         BermudaseShorts.add( new Descricao("Estado" , ""));
         BermudaseShorts.add( new Descricao("Cor" , ""));
         Blusas                     = new ArrayList<>();
         Blusas.add( new Descricao("Marca" , ""));
         Blusas.add( new Descricao("Tamanho" , ""));
         Blusas.add( new Descricao("Cor" , ""));
         Blusas.add( new Descricao("Estado" , ""));
         Calcas                     = new ArrayList<>();
         Calcas.add( new Descricao("Marca" , ""));
         Calcas.add( new Descricao("Cor" , ""));
         Calcas.add( new Descricao("Tamanho" , ""));
         Calcas.add( new Descricao("Para sexo" , ""));

         Camisas                    = new ArrayList<>();
         Camisas = Blusas;
         Camisetas                  = new ArrayList<>();
         Camisetas = Camisas;

         CamisolaseMoletons         = new ArrayList<>();
         CamisolaseMoletons = Camisetas;

         Casacos                    = new ArrayList<>();
         Casacos.add( new Descricao("Marca" , ""));
         Casacos.add( new Descricao("Cor" , ""));
         Casacos.add( new Descricao("Estado" , ""));
         Casacos.add( new Descricao("Tamanho" , ""));

         Leggings                   = new ArrayList<>();
         Leggings = Casacos;
         LotesdeRoupa               = new ArrayList<>();
         LotesdeRoupa = Casacos;
         Macacão                    = new ArrayList<>();
         Macacão = LotesdeRoupa;
         ModaÍntimaeLingerie        = new ArrayList<>();
         ModaÍntimaeLingerie.add( new Descricao("Tamanho" , ""));
         ModaÍntimaeLingerie.add( new Descricao("Cor" , ""));
         ModaPraia                  = new ArrayList<>();
         ModaPraia.add( new Descricao("Marca" , ""));
         ModaPraia.add( new Descricao("Tamanho" , ""));
         ModaPraia.add( new Descricao("Para sexo" , ""));

         RoupadeDança               = new ArrayList<>();
         RoupadeDança = ModaPraia;
         RoupaparaBebés             = new ArrayList<>();
         RoupasdeBebé.add( new Descricao("Tamanho" ,""));
         RoupasdeBebé.add( new Descricao("Idade" ,""));
         RoupasdeBebé.add( new Descricao("Sexo" ,""));

         Saías                      = new ArrayList<>();
         Saías = Blusas;
         Sapatos                    = new ArrayList<>();
         Sapatos.add( new Descricao("Marca" , ""));
         Sapatos.add( new Descricao("Numero",""));
         Sapatos.add( new Descricao("Cor",""));
         Sapatos.add( new Descricao("Para sexo",""));
         Sueteres_CardiganseColetes = new ArrayList<>();
         Sueteres_CardiganseColetes = Blusas;

         Ternos                     = new ArrayList<>();
         Ternos = Casacos;
         Uniformes                  = new ArrayList<>();
         Uniformes = Blusas;

         Vestidos                   = new ArrayList<>();
         Vestidos.add( new Descricao("Cor" , ""));
         Vestidos.add( new Descricao("Tamanho" , ""));
         Vestidos.add( new Descricao("Estado" , ""));

         Banheiros                  = new ArrayList<>();
         Banheiros = Vestidos;

         ColchoeseCamasBox          = new ArrayList<>();
         ColecionaveiseEsportes.add( new Descricao("Marca",""));
         ColecionaveiseEsportes.add( new Descricao("Cor",""));
         ColecionaveiseEsportes.add( new Descricao("Tamanho",""));


         CortinaseAcessórios        = new ArrayList<>();
         CortinaseAcessórios.add( new Descricao("Tipo",""));
         CortinaseAcessórios.add( new Descricao("Tamanho",""));
         CuidadosdeCasaeLavandaria  = new ArrayList<>();

         EnfeiteseDecoraçãodeCasa   = new ArrayList<>();
         IluminaçãoResidecial       = new ArrayList<>();
         JardinseExteriores         = new ArrayList<>();
         MóveisparaCasa             = new ArrayList<>();
         MóveisparaCasa.add(new Descricao("Marca",""));

         OrganizaçãoparaCasa        = new ArrayList<>();
         SegurançaparaCasa          = new ArrayList<>();
         TêxteisdeCasaeDecoração    = new ArrayList<>();
         UtilidadesDomésticas       = new ArrayList<>();
         AcessóriosparaCelulares    = new ArrayList<>();
         TelfoneseTablets           = new ArrayList<>();
         TelfoneseTablets.add( new Descricao("Marca" ,""));
         TelfoneseTablets.add( new Descricao("Modelo" ,""));
         TelfoneseTablets.add( new Descricao("Cor" ,""));
         TelfoneseTablets.add( new Descricao("Memoria Interna" ,""));
         TelfoneseTablets.add( new Descricao("Ram" ,""));
         TelfoneseTablets.add( new Descricao("Camera" ,""));
         TelfoneseTablets.add( new Descricao("Capacidade da Bateria" ,""));
         TelfoneseTablets.add( new Descricao("Estado" ,""));
         TelfoneseTablets.add( new Descricao("Linha" ,""));

         ÓculosdeRealidadeVirtual   = new ArrayList<>();
         ÓculosdeRealidadeVirtual.add( new Descricao("Marca" ,""));
         ÓculosdeRealidadeVirtual.add( new Descricao("Cor" ,""));

         PeçasparaCelular           = new ArrayList<>();
         PeçasparaCelular.add( new Descricao("Tipo" , ""));
         PeçasparaCelular.add( new Descricao("Estado" , ""));

         SmartwatcheseAcessórios    = new ArrayList<>();
         SmartwatcheseAcessórios = TelfoneseTablets;

         TelefoniaFixaeSemFio       = new ArrayList<>();
         VoIp                       = new ArrayList<>();
         WalkieTalkies              = new ArrayList<>();
         Acessóriosparacameras      = new ArrayList<>();
         AlbunsePorta_retratos      = new ArrayList<>();
         Cabos                      = new ArrayList<>();
         Cabos.add( new Descricao("Tipo" , ""));
         Cabos.add( new Descricao("Compromento" , ""));
         Cabos.add( new Descricao("Cor" , ""));

         Cameras                    = new ArrayList<>();
         Cameras.add( new Descricao("Marca" ,""));
         Cameras.add( new Descricao("Modelo" ,""));
         Cameras.add( new Descricao("Quantidade de Resulucao" ,""));
         Cameras.add( new Descricao("Distancia de escopo" ,""));
         Cameras.add( new Descricao("Estado" ,""));

         DroneseAcessorios          = new ArrayList<>();
         DroneseAcessorios.add( new Descricao("Marca" , ""));
         DroneseAcessorios.add( new Descricao("Modelo" , ""));
         DroneseAcessorios.add( new Descricao("Bateria" , ""));
         DroneseAcessorios.add( new Descricao("Cor" , ""));

         EquipamentosdeRevelacao    = new ArrayList<>();
         Filmadoras                 = new ArrayList<>();
         IntrumentosÓpticos         = new ArrayList<>();
         LenteseFiltros             = new ArrayList<>();
         LenteseFiltros.add(new Descricao("Marca",""));
         LenteseFiltros.add(new Descricao("Modelo",""));
         LenteseFiltros.add(new Descricao("Forma de filtro",""));
         LenteseFiltros.add(new Descricao("Tipo de Filtro",""));
         PeçasparaCameras           = new ArrayList<>();
         AreVentilação              = new ArrayList<>();
         BebedoresePurificadores    = new ArrayList<>();
         Cuidadopessoal             = new ArrayList<>();
         FornoeFogões               = new ArrayList<>();
         GeladeiraseFreezers        = new ArrayList<>();
         Lavadores                  = new ArrayList<>();
         PecaseAcessórios           = new ArrayList<>();
         PecaseAcessórios.add( new Descricao("Tipo",""));

         PequenosEletrodomesticos   = new ArrayList<>();
         PequenosEletrodomesticos.add( new Descricao("Marca" ,""));
         PequenosEletrodomesticos.add( new Descricao("Tipo" ,""));
         PequenosEletrodomesticos.add( new Descricao("Cor" ,""));

         AcessóriosparaÁudioeVideo  = new ArrayList<>();
         AparelhosDVDeBluray        = new ArrayList<>();
         Áudio                      = new ArrayList<>();
         Áudio.add( new Descricao("Marca" , ""));
         Áudio.add( new Descricao("Cor" , ""));
         Áudio.add( new Descricao("Estado" , ""));

         BolsaseEstojos             = new ArrayList<>();
        // BolsaseEstojos.add( new Descricao("" ,""));
         ControlesRemotos           = new ArrayList<>();
         ControlesRemotos.add( new Descricao("Marca" , ""));
         ControlesRemotos.add( new Descricao("Estado" , ""));
         MediaStreaming             = new ArrayList<>();

         PecaseComponetesEletricos  = new ArrayList<>();
         PeçasparaTv                = new ArrayList<>();
         PilhaseCarregadores        = new ArrayList<>();
         ProjetoreseTelas           = new ArrayList<>();
         ProjetoreseTelas.add( new Descricao("Marca" , ""));
         ProjetoreseTelas.add( new Descricao("Estado" , ""));


         tv                         = new ArrayList<>();
         tv.add( new Descricao("Marca" , ""));
         tv.add( new Descricao("Tipo" , ""));
         tv.add( new Descricao("Dimecao" , ""));

         ArtesMarcciaseBoxe         = new ArrayList<>();
         Badminton                  = new ArrayList<>();
         Baseball                   = new ArrayList<>();
         Basquete                   = new ArrayList<>();
         Camping_CacaePesca         = new ArrayList<>();
         Canoas_CaiqueseInflaveis   = new ArrayList<>();
         Ciclismo                   = new ArrayList<>();
         Equitação                  = new ArrayList<>();
         Esgrima                    = new ArrayList<>();
         EsquieSnowboard            = new ArrayList<>();
         FitnesseMusculacao         = new ArrayList<>();
         Futebol                    = new ArrayList<>();
         Futebol.add( new Descricao("Marca" , ""));
         Futebol.add( new Descricao("Tipo" , ""));
         Futebol.add( new Descricao("Estado" , ""));

         FutebolAmericano           = new ArrayList<>();
         FutebolAmericano = Futebol;
         Golfe                      = new ArrayList<>();
         Golfe = Futebol;

         Handebol                   = new ArrayList<>();
         Handebol = Golfe;

         Hockey                     = new ArrayList<>();
         Hockey = Futebol;

         Jogosdesalao               = new ArrayList<>();
         JogosdeSalão = Hockey;

         Kitesurf                   = new ArrayList<>();
         Mergulho                   = new ArrayList<>();
         ModaFitness                = new ArrayList<>();
         MonitoreseRelogios         = new ArrayList<>();
         Natação                    = new ArrayList<>();
         Paintball                  = new ArrayList<>();
         Parapente                  = new ArrayList<>();
         Patin_GinasticaeDanca      = new ArrayList<>();
         PatineteseScooters         = new ArrayList<>();
         PilateseYoga               = new ArrayList<>();
         Rapel_MontanhiscoeEscalada = new ArrayList<>();
         Rugby                      = new ArrayList<>();
         SkateboardeSandboard       = new ArrayList<>();
         Slackline                  = new ArrayList<>();
         SuplementoseShakers        = new ArrayList<>();
         SurfeBodyboard             = new ArrayList<>();
         TeniseSquash               = new ArrayList<>();
         Voleiball                  = new ArrayList<>();
         Wakeboard                  = new ArrayList<>();
         Windsurfe                  = new ArrayList<>();
         Aberturas                  = new ArrayList<>();
         Construção                 = new ArrayList<>();
         Encanamento                = new ArrayList<>();
         EnergiaEletrica            = new ArrayList<>();
         LojadeTintas               = new ArrayList<>();
         MobiliárioparaBanheiros    = new ArrayList<>();
         MobiliárioparaCozinhas     = new ArrayList<>();
         PisoseRejuntes             = new ArrayList<>();
         Artigosparafestas          = new ArrayList<>();
         Convites                   = new ArrayList<>();
         DecoraçãodeFesta           = new ArrayList<>();
         DescartáveisparaFestas     = new ArrayList<>();
         Espuma_SerpentinaseConfete = new ArrayList<>();
         Fantasias                  = new ArrayList<>();
         KitsImprimiveisparaFestas  = new ArrayList<>();
         Lembrancinhas              = new ArrayList<>();
         PlaquinhasparaFestas       = new ArrayList<>();
         AcessóriosparaConsolas     = new ArrayList<>();
         Consolas                   = new ArrayList<>();
         Consolas.add( new Descricao("Marca" , ""));
         Consolas.add( new Descricao("Linha" , ""));
         Consolas.add( new Descricao("Capacidade" , ""));
         Consolas.add( new Descricao("Estado" , ""));
         Consolas.add( new Descricao("Cor" , ""));

         FliperamaseArcades         = new ArrayList<>();
         PecasparaConsolas          = new ArrayList<>();
         VideoGames                 = new ArrayList<>();
         VideoGames.add(new Descricao("Tipo" , ""));

         AcessóriosdeAntiestatica   = new ArrayList<>();
         Armazenamento              = new ArrayList<>();
         Armazenamento.add( new Descricao("Marca" , ""));
         Armazenamento.add( new Descricao("Capacidade" , ""));

         CaboseHubsUSB              = new ArrayList<>();
         CaboseHubsUSB = Cabos;

         ComponentesparaPC          = new ArrayList<>();
         ComponentesparaPC.add( new Descricao("Marca" , ""));
         ComponentesparaPC.add( new Descricao("Tipo" , ""));
         ComponentesparaPC.add( new Descricao("Estado" , ""));

         ConectividadeeRedes        = new ArrayList<>();

         EstabilizadoreseNoBreaks   = new ArrayList<>();
         EstabilizadoreseNoBreaks.add( new Descricao("Marca" , ""));
         EstabilizadoreseNoBreaks.add( new Descricao("Capacidade" , ""));
         EstabilizadoreseNoBreaks.add( new Descricao("Estado" , ""));
         Impressão                  = new ArrayList<>();
         Impressão.add( new Descricao("Marca" , ""));
         Impressão.add( new Descricao("Cor" , ""));
         Impressão.add( new Descricao("Tipo de Impressão" , ""));
         LeitoreseScanners          = new ArrayList<>();

         LimpesadePCs               = new ArrayList<>();
         MonitoreseAcessórios       = new ArrayList<>();
         MonitoreseAcessórios.add( new Descricao("Marca" , ""));
         MonitoreseAcessórios.add( new Descricao("Dimecao" , ""));
         MonitoreseAcessórios.add( new Descricao("Estado" , ""));
         Mouses_TecladoseControles  = new ArrayList<>();
         Mouses_TecladoseControles.add( new Descricao("Marca" , ""));
         Mouses_TecladoseControles.add( new Descricao("Cor" , ""));
         Mouses_TecladoseControles.add( new Descricao("Estado" , ""));

         PalmseHandhelds            = new ArrayList<>();
         Computador                 = new ArrayList<>();
         Computador.add( new Descricao("Marca" , ""));
         Computador.add( new Descricao("Estado" , ""));
         Computador.add( new Descricao("Ram" , ""));
         Computador.add( new Descricao("Disco" , ""));
         Computador.add( new Descricao("Monitor" , ""));

         Laptop                     = new ArrayList<>();
         Laptop.add( new Descricao("Marca" , ""));
         Laptop.add( new Descricao("Modelo" , ""));
         Laptop.add( new Descricao("Ram" , ""));
         Laptop.add( new Descricao("Cor" , ""));
         Laptop.add( new Descricao("Dimecao" , ""));
         Laptop.add( new Descricao("Estado" , ""));

         PortaCDs_CaixasEnvelopes   = new ArrayList<>();
         PortateiseAcessorios       = new ArrayList<>();
         ProjectoreseTelas          = new ArrayList<>();
         Software                   = new ArrayList<>();
         TableseAcessórios          = new ArrayList<>();
         WebcamseÁudioparaPC        = new ArrayList<>();
         EventosEsportivos          = new ArrayList<>();
         EventosEsportivos.add( new Descricao("Local" ,""));
         EventosEsportivos.add( new Descricao("Data" ,""));
         Exposição                  = new ArrayList<>();
         Ingressos                  = new ArrayList<>();
         Ingressos = EventosEsportivos;
         Shows                      = new ArrayList<>();
         Shows = Ingressos;

         TeatroeCultura             = new ArrayList<>();
         BateriasePercussão         = new ArrayList<>();
         CaixasdeSom                = new ArrayList<>();
         CaixasdeSom.add( new Descricao("Marca" ,""));
         CaixasdeSom.add( new Descricao("Estado" ,""));
         CaixasdeSom.add( new Descricao("Origem" ,""));
         EquipamentosparaDJ         = new ArrayList<>();
         EquipamentosparaDJ.add( new Descricao("Marca" , ""));
         EquipamentosparaDJ.add( new Descricao("Tipo" , ""));
         EquipamentosparaDJ.add( new Descricao("Estado" , ""));

         EstudiodeGravação          = new ArrayList<>();
         IntrumentosdeCorda         = new ArrayList<>();
         IntrumentosdeCorda.add( new Descricao("Marca" ,""));
         IntrumentosdeCorda.add( new Descricao("Estado" ,""));

         InstrumentosdeSopro        = new ArrayList<>();
         InstrumentosdeSopro = IntrumentosdeCorda;

         Metronomos                 = new ArrayList<>();
         MicrofoneseAmplificadores  = new ArrayList<>();
         MicrofoneseAmplificadores = InstrumentosdeSopro;

         PartituraseLetras          = new ArrayList<>();
         PedaiseAcessórios          = new ArrayList<>();
         PianoeTeclados             = new ArrayList<>();
         PianoeTeclados.add( new Descricao("Marca" , ""));

         AcessóriosParaRelogios     = new ArrayList<>();
         ArtigosdeJoalharia         = new ArrayList<>();
         CanetaseLapiseirasdeluxo   = new ArrayList<>();
         JóiaseBijuterias           = new ArrayList<>();
         MonitoreseCronómetros      = new ArrayList<>();
         Pedraspreciosas            = new ArrayList<>();
         Piercings                  = new ArrayList<>();
         PortaJóias                 = new ArrayList<>();
         Relógios                   = new ArrayList<>();
         Relógios.add( new Descricao("Marca" , ""));
         Relógios.add( new Descricao("Cor" , ""));

         Smartwatches               = new ArrayList<>();
         Smartwatches = SmartwatcheseAcessórios;
         Catalogos                  = new ArrayList<>();
         HQs                        = new ArrayList<>();
         Livros                     = new ArrayList<>();
         Livros.add( new Descricao("Edicao" ,""));
         Livros.add( new Descricao("Autor" ,""));
         Revistas                   = new ArrayList<>();

    }


    private Runnable contarProdutos = new Runnable() {
        @Override
        public void run() {
            databaseReference.child("Produto").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                        position++;
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

                adapter = new ArrayAdapter<String>(InformacoesPublicar.this, android.R.layout.simple_spinner_item, localizacaoDistrito);
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


    private void inicializarFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }


    private void verificarCampos() {
        titulo   = tv_titulo.getText().toString().trim();
        preco    = tv_preco.getText().toString().trim();
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

        }/*else if(campos.isCampoVazio(marca)){
            tv_marca.requestFocus();
            tv_marca.setHintTextColor(Color.RED);
        }*/else if(campos.isCampoVazio(contacto)) {
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
        publicarImagem();

    }

    private void chamarDialog() {


        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mView = getLayoutInflater().inflate(R.layout.dialog_progress, null);


        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.setCancelable(false);
        dialog.show();
    }




    int o = picturesSelected.size() - 2;
    boolean n = false;
    public void publicarImagem(){


        if (picturesSelected.size() > 20){

        }
        else {


            for (int i = 0; i < picturesSelected.size() ; i++){

                Toast.makeText(this, ""+i, Toast.LENGTH_SHORT).show();
                if (i == 11){
                    break;
                }

                final Uri file = Uri.fromFile(new File(picturesSelected.get(i).getPath()));
                final StorageReference riversRef = mStorageRef.child(UUID.randomUUID().toString());

                final int finalI1 = i;
                riversRef.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {


                                img = uri.toString();


                                imagem.setImg(img);
                                imagem.setUuid(UUID.randomUUID().toString());
                                listImg.add(img);


                            }
                        });

                    }
                });





            }



        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                publicarProduto();

            }
        },9000);


    }

    private void publicarProduto() {


        if (id != null && !id.isEmpty()){
            produto.setNome(titulo);
            produto.setUid(rand);
            produto.setIdUser(id);
            produto.setCategoria(categoria);
            produto.setSub_categoria(sub_categoria);
            produto.setProvincia(provincia);
            produto.setDistrito(distrito);
            produto.setTempo(data());
            produto.setPreco(preco);
            produto.setPosicao(position);
            produto.setImg(img);

            databaseReference.child("Produto").child(produto.getUid()).setValue(produto).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
                    Toast.makeText(InformacoesPublicar.this, "falha", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    databaseReference.child("Usuario").child(id).child("Produto").child(rand).setValue(produto);
                    publicarImagemDb();
                }
            });


        }else {
            Intent intent = new Intent(InformacoesPublicar.this , Login.class);
            startActivity(intent);

        }



    }

    private void publicarImagemDb(){

        for (int i = 0 ; i  < listImg.size() ; i++){
            ProdutoImagem imagemw = new ProdutoImagem();
            imagemw.setUuid(UUID.randomUUID().toString());
            imagemw.setImg(listImg.get(i));

            databaseReference.child("Usuario").child(id).child("Produto").child(rand).child("Imagem").child(imagem.getUuid()+i).setValue(imagemw);

        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                publicarDescricao();

            }
        },4000);


    }
    private void publicarDescricao() {



        DescricaoProduto descricaoProduto  = new DescricaoProduto();

        descricaoProduto.setUid(produto.getUid()+data());
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

        databaseReference.child("Usuario").child(id).child("Produto").child(produto.getUid()).child("Descricao").child(produto.getUid()).setValue(descricaoProduto);


        for (int i = 0; i < CustomAdapter.editModelArrayList.size(); i++){

            Descricao descricao = new Descricao();
            descricao.setDefinicao(CustomAdapter.editModelArrayList.get(i).getDefinicao());
            descricao.setConceito(CustomAdapter.editModelArrayList.get(i).getConceito());
            databaseReference.child("Usuario").child(id).child("Produto").child(produto.getUid()).child("Detalhe").child(UUID.randomUUID().toString()).setValue(descricao);


        }


        passaFim();

    }

    private void passaFim() {

        Intent intent = new Intent(InformacoesPublicar.this , MainActivity.class );
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        picturesSelected.clear();



    }

    private void selecionarCategoria() {
        categoriaPrincipal = getResources().getStringArray(R.array.categorias_de_produtos);

        adapter = new ArrayAdapter<String>(InformacoesPublicar.this, android.R.layout.simple_spinner_item, categoriaPrincipal);
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
                            InformacoesPublicar.this, android.R.layout.simple_spinner_item, sub_categoria_array );
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_subcategoria.setAdapter(adapter);

                    sp_subcategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            sub_categoria = adapterView.getSelectedItem().toString();
                            verificarCategoria();

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

    private void verificarCategoria() {

        Toast.makeText(this, ""+sub_categoria, Toast.LENGTH_SHORT).show();
        switch (sub_categoria){
            case "Aces de Carros e Caminhonetes":
                descricaoList = AcesdeCarroseCaminhonetes;
                break;

            case "Aces de Motos e Quadriciclos":
                descricaoList = AcesdeMotoseQuadriciclos;
                break;

            case "Aces e Peças para Camiões":
                descricaoList = AcesePeçasparaCamiões;
                break;

            case "Acessórios e Peças Náuticos":
                descricaoList = AcessóriosePeçasNáuticos;
                break;

            case "Ferramentas":
                descricaoList = Ferramentas;
                break;

            case "GNV":
                descricaoList = GNV;
                break;

            case "Limpeza Automotivo":
                descricaoList = LimpezaAutomotivo;
                break;

            case "Navegadores GPS":
                descricaoList = NavegadoresGPS;
                break;

            case "Peças de Carros e Caminhonetes":
                descricaoList = PeçasdeCarroseCaminhonetes;
                break;

            case "Peças de Maquinaria Pesada":
                descricaoList = PeçasdeMaquinariaPesada;
                break;

            case "Peças de Motos e Quadriciclos":
                descricaoList = PeçasdeMotoseQuadriciclos;
                break;
            case "Perfomance":
                descricaoList = Perfomance;
                break;
            case "Pneus":
                descricaoList = Pneus;
                break;
            case "Rodas":
                descricaoList = Rodas;
                break;
            case "Segurança Veicular":
                descricaoList = SegurançaVeicular;
                break;

            case "Serviços Programados":
                descricaoList = ServiçosProgramados;
                break;

            case "Som Automotivo":
                descricaoList = SomAutomotivo;
                break;

            case "Tuning":
                descricaoList = Tuning;
                break;

            case "Arquitectura e Desenho":
                descricaoList = ArquitecturaeDesenho;
                break;

            case "Embalagem":
                descricaoList = Embalagem;
                break;

            case "Equipamento Comercial":
                descricaoList = EquipamentoComercial;
                break;

            case "Equipamento de Segurança":
                descricaoList = EquipamentodeSegurança;
                break;

            case "Equipamento para escritórios":
                descricaoList = Equipamentoparaescritórios;
                break;

            case "Equipamento para Indústrias":
                descricaoList = EquipamentoparaIndústrias;
                break;

            case "Indústria Agropecuaria":
                descricaoList = IndústriaAgropecuaria;
                break;

            case "Indústria Gráfica e Impressão":
                descricaoList = IndústriaGráficaeImpressão;
                break;

            case "Indústria Têxtil":
                descricaoList = IndústriaTêxtil;
                break;

            case "Material de Promoção":
                descricaoList = MaterialdePromoção;
                break;

            case "Bebidas":
                descricaoList = Bebidas;
                break;

            case "Comestíveis":
                descricaoList = Comestíveis;
                break;

            case "Comida Preparada e Catering":
                descricaoList = ComidaPreparadaeCatering;
                break;

            case "Frescos":
                descricaoList = Frescos;
                break;

            case "Anifibios e Répteis":
                descricaoList = AnifibioseRépteis;
                break;

            case "Aveiro  e Acessórios":
                descricaoList = AveiroeAcessórios;
                break;

            case "Cachorros":
                descricaoList = Cachorros;

                break;

            case "Cavalos":
                descricaoList = Cavalos;
                break;

            case "Coelhos":
                descricaoList = Coelhos;
                break;

            case "Gatos":
                descricaoList = Gatos;
                break;

            case "Insectos":
                descricaoList = Insectos;
                break;

            case "Peixes":
                descricaoList = Peixes;
                break;

            case "Roedores":
                descricaoList = Roedores;
                break;

            case "Antiguidades":
                descricaoList = Antiguidades;
                break;

            case "Cédulas e Moedas":
                descricaoList = CédulaseMoedas;
                break;

            case "Colecionaveis e Esportes":
                descricaoList = ColecionaveiseEsportes;
                break;

            case "Esculturas":
                descricaoList = Esculturas;
                break;

            case "Filatelia":
                descricaoList = Filatelia;
                break;

            case "Milateria e Afins":
                descricaoList = MilateriaeAfins;
                break;

            case "Posteres":
                descricaoList = Posteres;
                break;

            case "Arte":
                descricaoList = Arte;
                break;

            case "Artigos de Armarinho":
                descricaoList = ArtigosdeArmarinho;
                break;

            case "Espelhos de Mosaico":
                descricaoList = EspelhosdeMosaico;
                break;

            case "Formas para sabonetes":
                descricaoList = Formasparasabonetes;
                break;

            case "Insumos para Fazer velas":
                descricaoList = InsumosparaFazervelas;
                break;

            case "Materiais Escolares":
                descricaoList = MateriaisEscolares;
                break;

            case "Peças para Chaveiros":
                descricaoList = PeçasparaChaveiros;
                break;

            case "Alimentação para Bebé":
                descricaoList = AlimentaçãoparaBebé;
                break;

            case "Andadores e Mini veículos":
                descricaoList = AndadoreseMiniveículos;
                break;

            case "Artigos de Bebé para banho":
                descricaoList = ArtigosdeBebéparabanho;
                break;

            case "Artigos de Maternidade":
                descricaoList = ArtigosdeMaternidade;
                break;

            case "Brinquedos para Bebés":
                descricaoList = BrinquedosparaBebes;
                break;

            case "Cercadinho":
                descricaoList = Cercadinho;
                break;

            case "Chupetas e Mordedores":
                descricaoList = ChupetaseMordedores;
                break;

            case "Higiene e Cuidados com o bebé":
                descricaoList = HigieneeCuidadoscomobebé;
                break;

            case "Passeio do Bebé":
                descricaoList = PasseiodoBebé;
                break;

            case "Roupas de Bebé":
                descricaoList = RoupasdeBebé;
                break;

            case "Saúde de Bebé":
                descricaoList = SaúdedeBebé;
                break;

            case "Segurança para Bebé":
                descricaoList = SegurançaparaBebé;
                break;

            case "Artigos para Cabeleireiros":
                descricaoList = ArtigosparaCabeleireiros;
                break;

            case "Barbearia":
                descricaoList = Barbearia;
                break;

            case "Cuidados com a Pele":
                descricaoList = CuidadoscomaPele;
                break;

            case "Cuidados com o Cabelo":
                descricaoList = CuidadoscomoCabelo;
                break;

            case "Depilação":
                descricaoList = Depilação;
                break;

            case "Electrodomésticos e Beleza":
                descricaoList = ElectrodomésticoseBeleza;
                break;

            case "Farmácia":
                descricaoList = Farmácia;
                break;

            case "Higiene Pessoal":
                descricaoList = HigienePessoal;
                break;

            case "Manicure e Pedicure":
                descricaoList = ManicureePedicure;
                break;

            case "Maquiagem":
                descricaoList = Maquiagem;
                break;

            case "Perfumes":
                descricaoList = Perfumes;
                break;

            case "Splash Corporal":
                descricaoList = SplashCorporal;
                break;

            case "Tratamentos de Beleza":
                descricaoList = TratamentosdeBeleza;
                break;

            case "Albuns e Figurinhas":
                descricaoList = AlbunseFigurinhas;
                break;

            case "Anti-stress e Engenho":
                descricaoList = Anti_stresseEngenho;
                break;

            case "Ar Livre e Playground":
                descricaoList = ArLivreePlayground;
                break;

            case "Blocos e Construção":
                descricaoList = BlocoseConstrução;
                break;

            case "Bonecos e Bonecas":
                descricaoList = BonecoseBonecas;
                break;

            case "Brinquedos de Agua e Praia":
                descricaoList = BrinquedosdeAguaePraia;
                break;

            case "Brinquedos de Pegadinhas":
                descricaoList = BrinquedosdePegadinhas;
                break;

            case "Brinquedos de Profissões":
                descricaoList = BrinquedosdeProfissões;
                break;

            case "Brinquedos para Bebes":
                descricaoList = BrinquedosparaBebés;
                break;

            case "Casinhas e Barracas":
                descricaoList = CasinhaseBarracas;
                break;

            case "Desenho, Pintura e Artesanatos":
                descricaoList = Desenho_PinturaeArtesanatos;
                break;

            case "Fontoches e Marionetas":
                descricaoList = FontocheseMarionetas;
                break;

            case "Hobbies":
                descricaoList = Hobbies;
                break;

            case "Instrumentos Musicais":
                descricaoList = IntrumentosMusicais;
                break;

            case "Jogos de Salão":
                descricaoList = Jogosdesalao;
                break;

            case "Jogos de Tabuleiro e Cartas":
                descricaoList = JogosdeTabuleiroeCartas;
                break;

            case "Jogos Eletronicos":
                descricaoList = JogosEletronicos;
                break;

            case "Lancadores de Brinquedo":
                descricaoList = LancadoresdeBrinquedo;
                break;

            case "Mesas e Cadeiras":
                descricaoList = MesaseCadeiras;
                break;

            case "Mini Veiculos e Bicicletas":
                descricaoList = MiniVeiculoseBicicletas;
                break;

            case "Pelucias":
                descricaoList = Pelucias;
                break;

            case "Pescinas e Inflaveis":
                descricaoList = PescinaseInflaveis;
                break;

            case "Upa Upa":
                descricaoList = UpaUpa;
                break;

            case "Veiculos de Brinquedo":
                descricaoList = VeiculosdeBrinquedo;
                break;

            case "Acessórios da Moda":
                descricaoList = AcessóriosdaModa;
                break;

            case "Begagem e Bolsas":
                descricaoList = BegagemeBolsas;
                break;

            case "Bermudas e Shorts":
                descricaoList = BermudaseShorts;
                break;

            case "Blusas":
                descricaoList = Blusas;
                break;

            case "Calcas":
                descricaoList = Calcas;
                break;

            case "Camisas":
                descricaoList = Camisas;
                break;

            case "Camisetas":
                descricaoList = Camisetas;
                break;

            case "Camisolas e Moletons":
                descricaoList = CamisolaseMoletons;
                break;

            case "Casacos":
                descricaoList = Casacos;
                break;

            case "Leggings":
                descricaoList = Leggings;
                break;

            case "Lotes de Roupa":
                descricaoList = LotesdeRoupa;
                break;

            case "Macacão":
                descricaoList = Macacão;
                break;

            case "Moda Íntima e Lingerie":
                descricaoList = ModaÍntimaeLingerie;
                break;

            case "Moda Praia":
                descricaoList = ModaPraia;
                break;

            case "Roupa de Dança":
                descricaoList = RoupadeDança;
                break;

            case "Roupa para Bebés":
                descricaoList = RoupaparaBebés;
                break;

            case "Saías":
                descricaoList = Saías;
                break;

            case "Sapatos":
                descricaoList = Sapatos;
                break;

            case "Sueteres, Cardigans e Coletes":
                descricaoList = Sueteres_CardiganseColetes;
                break;

            case "Ternos":
                descricaoList = Ternos;
                break;

            case "Uniformes":
                descricaoList = Uniformes;
                break;

            case "Vestidos":
                descricaoList = Vestidos;
                break;

            case "Banheiros":
                descricaoList = Banheiros;
                break;

            case "Colchoes e Camas Box":
                descricaoList = ColchoeseCamasBox;
                break;

            case "Cortinas e Acessórios":
                descricaoList = CortinaseAcessórios;
                break;

            case "Cuidados de Casa e Lavandaria":
                descricaoList = CuidadosdeCasaeLavandaria;
                break;

            case "Enfeites e Decoração de Casa":
                descricaoList = EnfeiteseDecoraçãodeCasa;
                break;

            case "Iluminação Residecial":
                descricaoList = IluminaçãoResidecial;
                break;

            case "Jardins e Exteriores":
                descricaoList = JardinseExteriores;
                break;

            case "Móveis para Casa":
                descricaoList = MóveisparaCasa;
                break;

            case "Organização para Casa":
                descricaoList = OrganizaçãoparaCasa;
                break;

            case "Segurança para Casa":
                descricaoList = SegurançaparaCasa;
                break;

            case "Têxteis de Casa e Decoração":
                descricaoList = TêxteisdeCasaeDecoração;
                break;

            case "Utilidades Domésticas":
                descricaoList = UtilidadesDomésticas;
                break;

            case "Acessórios para Celulares":
                descricaoList = AcessóriosparaCelulares;
                break;

            case "Telfones e Tablets":
                descricaoList = TelfoneseTablets;
                break;

            case "Óculos de Realidade Virtual":
                descricaoList = ÓculosdeRealidadeVirtual;
                break;

            case "Peças para Celular":
                descricaoList = PeçasparaCelular;
                break;

            case "Smartwatches e Acessórios":
                descricaoList = SmartwatcheseAcessórios;
                break;

            case "Telefonia Fixa e Sem Fio":
                descricaoList = TelefoniaFixaeSemFio;
                break;

            case "VoIp":
                descricaoList = VoIp;
                break;

            case "Walkie Talkies":
                descricaoList = WalkieTalkies;
                break;

            case "Acessórios para cameras":
                descricaoList = Acessóriosparacameras;
                break;

            case "Albuns e Porta-retratos":
                descricaoList = AlbunsePorta_retratos;
                break;

            case "Cabos":
                descricaoList = Cabos;
                break;


            case "Cameras":
                descricaoList = Cameras;
                break;


            case "Drones e Acessorios":
                descricaoList = DroneseAcessorios;
                break;


            case "Equipamentos de Revelacao":
                descricaoList = EquipamentosdeRevelacao;
                break;


            case "Filmadoras":
                descricaoList = Filmadoras;
                break;


            case "Instrumentos Ópticos":
                descricaoList =  IntrumentosÓpticos;
                break;


            case "Lentes e Filtros":
                descricaoList = LenteseFiltros;
                break;


            case "Peças para Cameras":
                descricaoList = PeçasparaCameras;
                break;


            case "Ar e Ventilação":
                descricaoList = AreVentilação;
                break;


            case "Bebedores e Purificadores":
                descricaoList = BebedoresePurificadores;
                break;


            case "Cuidado pessoal":
                descricaoList = Cuidadopessoal;
                break;


            case "Forno e Fogões":
                descricaoList = FornoeFogões;
                break;


            case "Geladeiras e Freezers":
                descricaoList = GeladeiraseFreezers;
                break;


            case "Lavadores":
                descricaoList = Lavadores;
                break;


            case "Pecas e Acessórios":
                descricaoList = PecaseAcessórios;
                break;

            case "Pequenos Eletrodomesticos":
                descricaoList = PequenosEletrodomesticos;
                break;

            case "Acessórios para Áudio e Video":
                descricaoList = AcessóriosparaÁudioeVideo;
                break;

            case "Aparelhos DVD e Bluray":
                descricaoList = AparelhosDVDeBluray;
                break;

            case "Áudio":
                descricaoList = Áudio;
                break;

            case "Bolsas e Estojos":
                descricaoList = BolsaseEstojos;
                break;


            case "Controles Remotos":
                descricaoList = ControlesRemotos;
                break;

            case "Media Streaming":
                descricaoList = MediaStreaming;
                break;

            case "Pecas e Componetes Eletricos":
                descricaoList = PecaseComponetesEletricos;
                break;

            case "Peças para Tv":
                descricaoList = PeçasparaTv;
                break;

            case "Pilhas e Carregadores":
                descricaoList = PilhaseCarregadores;
                break;

            case "Projetores e Telas":
                descricaoList = ProjectoreseTelas;
                break;

            case "TV":
                descricaoList = tv;
                break;


            case "Artes Marccias e Boxe":
                descricaoList = ArtesMarcciaseBoxe;
                break;


            case "Badminton":
                descricaoList = Badminton;
                break;


            case "Baseball":
                descricaoList = Baseball;
                break;


            case "Basquete":
                descricaoList = Basquete;
                break;


            case "Camping , Caca e Pesca":
                descricaoList = Camping_CacaePesca;
                break;


            case "Canoas , Caiques e Inflaveis":
                descricaoList = Canoas_CaiqueseInflaveis;
                break;


            case "Ciclismo":
                descricaoList = Ciclismo;
                break;


            case "Equitação":
                descricaoList = Equitação;
                break;


            case "Esgrima":
                descricaoList = Esgrima;
                break;


            case "Esqui e Snowboard":
                descricaoList = EsquieSnowboard;
                break;


            case "Fitness e Musculacao":
                descricaoList = FitnesseMusculacao;
                break;


            case "Futebol":
                descricaoList = Futebol;
                break;


            case "Futebol Americano":
                descricaoList = FutebolAmericano;
                break;


            case "Golfe":
                descricaoList = Golfe;
                break;


            case "Handebol":
                descricaoList = Handebol;
                break;


            case "Hockey":
                descricaoList = Hockey;
                break;


            case "Jogos de salao":
                descricaoList = JogosdeSalão;
                break;


            case "Kitesurf":
                descricaoList = Kitesurf;
                break;


            case "Mergulho":
                descricaoList = Mergulho;
                break;


            case "Moda Fitness":
                descricaoList = ModaFitness;
                break;


            case "Monitores e Relogios":
                descricaoList = MonitoreseRelogios;
                break;


            case "Natação":
                descricaoList = Natação;
                break;


            case "Paintball":
                descricaoList = Paintball;
                break;

            case "Parapente":
                descricaoList = Parapente;
                break;

            case "Patin ,Ginastica e Danca":
                descricaoList = Patin_GinasticaeDanca;
                break;

            case "Patinetes e Scooters":
                descricaoList = PatineteseScooters;
                break;

            case "Pilates e Yoga":
                descricaoList = PilateseYoga;
                break;

            case "Rapel, Montanhisco e Escalada":
                descricaoList = Rapel_MontanhiscoeEscalada;
                break;

            case "Rugby":
                descricaoList = Rugby;
                break;

            case "Skateboard e Sandboard":
                descricaoList = SkateboardeSandboard;
                break;

            case "Slackline":
                descricaoList = Slackline;
                break;

            case "Suplementos e Shakers":
                descricaoList = SuplementoseShakers;
                break;

            case "Surf e Bodyboard":
                descricaoList = SurfeBodyboard;
                break;

            case "Tenis e Squash":
                descricaoList = TeniseSquash;
                break;

            case "Volei":
                descricaoList = Voleiball;
                break;

            case "Wakeboard":
                descricaoList = Wakeboard;
                break;

            case "Windsurfe":
                descricaoList = Windsurfe;
                break;

            case "Aberturas":
                descricaoList = Aberturas;
                break;

            case "Construção":
                descricaoList = Construção;
                break;

            case "Encanamento":
                descricaoList = Encanamento;
                break;

            case "Energia Eletrica":
                descricaoList = EnergiaEletrica;
                break;
            case "Loja de Tintas":
                descricaoList = LojadeTintas;
                break;

            case "Mobiliário para Banheiros":
                descricaoList = MobiliárioparaBanheiros;
                break;

            case "Mobiliário para Cozinhas":
                descricaoList = MobiliárioparaCozinhas;
                break;

            case "Pisos e Rejuntes":
                descricaoList = PisoseRejuntes;
                break;

            case "Artigos para festas":
                descricaoList =  Artigosparafestas;
                break;

            case "Convites":
                descricaoList = Convites;
                break;

            case "Decoração de Festa":
                descricaoList = DecoraçãodeFesta;
                break;

            case "Descartáveis para Festas":
                descricaoList = DescartáveisparaFestas;
                break;

            case "Espuma, Serpentinas e Confete":
                descricaoList = Espuma_SerpentinaseConfete;
                break;

            case "Fantasias":
                descricaoList = Fantasias;
                break;

            case "Kits Imprimiveis para Festas":
                descricaoList = KitsImprimiveisparaFestas;
                break;

            case "Lembrancinhas":
                descricaoList = Lembrancinhas;
                break;

            case "Plaquinhas para Festas":
                descricaoList = PlaquinhasparaFestas;
                break;

            case "Acessórios para Consolas":
                descricaoList = AcessóriosparaConsolas;
                break;

            case "Consolas":
                descricaoList = Consolas;
                break;

            case "Fliperamas e Arcades":
                descricaoList = FliperamaseArcades;
                break;

            case "Pecas para Consolas":
                descricaoList = PecasparaConsolas;
                break;

            case "Video Games":
                descricaoList = VideoGames;
                break;

            case "Acessórios de Antiestatica":
                descricaoList = AcessóriosdeAntiestatica;
                break;

            case "Armazenamento":
                descricaoList = Armazenamento;
                break;

            case "Cabos e Hubs USB":
                descricaoList = CaboseHubsUSB;
                break;

            case "Componentes para PC":
                descricaoList = ComponentesparaPC;
                break;

            case "Conectividade e Redes":
                descricaoList = ConectividadeeRedes;
                break;

            case "Estabilizadores e No Breaks":
                descricaoList = EstabilizadoreseNoBreaks;
                break;

            case "Impressão":
                descricaoList = Impressão;
                break;

            case "Leitores e Scanners":
                descricaoList = LeitoreseScanners;
                break;

            case "Limpesa de PCs":
                descricaoList = LimpesadePCs;
                break;

            case "Monitores e Acessórios":
                descricaoList = MonitoreseAcessórios;
                break;

            case "Mouses, Teclados e Controles":
                descricaoList = Mouses_TecladoseControles;
                break;

            case "Palms e Handhelds":
                descricaoList = PalmseHandhelds;
                break;

            case "Computador":
                descricaoList = Computador;
                break;

            case "Laptop":
                descricaoList = Laptop;
                break;

            case "Porta CDs , Caixas Envelopes":
                descricaoList = PortaCDs_CaixasEnvelopes;
                break;

            case "Portateis e Acessorios":
                descricaoList = PortateiseAcessorios;
                break;

            case "Projectores  e Telas":
                descricaoList = ProjectoreseTelas;
                break;

            case "Software":
                descricaoList = Software;
                break;

            case "Tables e Acessórios":
                descricaoList = TableseAcessórios;
                break;

            case "Webcams e Áudio para PC":
                descricaoList = WebcamseÁudioparaPC;
                break;

            case "Eventos Esportivos":
                descricaoList = EventosEsportivos;
                break;

            case "Exposição":
                descricaoList = Exposição;
                break;

            case "Ingressos":
                descricaoList = Ingressos;
                break;

            case "Shows":
                descricaoList = Shows;
                break;

            case "Teatro e Cultura":
                descricaoList = TeatroeCultura;
                break;

            case "Baterias e Percussão":
                descricaoList = BateriasePercussão;
                break;

            case "Caixas de Som":
                descricaoList= CaixasdeSom;
                break;

            case "Equipamentos para DJ":
                descricaoList = EquipamentosparaDJ;
                break;

            case "Estudio de Gravação":
                descricaoList = EstudiodeGravação;
                break;

            case "Intrumentos de Corda":
                descricaoList = IntrumentosdeCorda;
                break;

            case "Instrumentos de Sopro":
                descricaoList = InstrumentosdeSopro;
                break;

            case "Metronomos":
                descricaoList = Metronomos;
                break;

            case "Microfones e Amplificadores":
                descricaoList = MicrofoneseAmplificadores;
                break;

            case "Partituras e Letras":
                descricaoList = PartituraseLetras;
                break;

            case "Pedais e Acessórios":
                descricaoList = PedaiseAcessórios;
                break;

            case "Piano e Teclados":
                descricaoList = PianoeTeclados;
                break;

            case "Acessórios Para Relogios":
                descricaoList = AcessóriosParaRelogios;
                break;

            case "Artigos de Joalharia":
                descricaoList = ArtigosdeJoalharia;
                break;

            case "Canetas e Lapiseiras de luxo":
                descricaoList = CanetaseLapiseirasdeluxo;
                break;

            case "Jóias e Bijuterias":
                descricaoList = JóiaseBijuterias;
                break;

            case "Monitores e Cronómetros":
                descricaoList = MonitoreseCronómetros;
                break;


            case "Pedras preciosas":
                descricaoList = Pedraspreciosas;
                break;

            case "Piercings":
                descricaoList = Piercings;
                break;

            case "Porta Jóias":
                descricaoList = PortaJóias;
                break;

            case "Relógios":
                descricaoList = Relógios;
                break;

            case "Smartwatches":
                descricaoList = Smartwatches;
                break;

            case "Catalogos":
                descricaoList = Catalogos;
                break;

            case "HQs":
                descricaoList = HQs;
                break;

            case "Livros":
                descricaoList = Livros;
                break;

            case "Revistas":
                descricaoList = Revistas;
                break;
            default:
                descricaoList.clear();
                break;

        }

        recyclerView = (RecyclerView) findViewById(R.id.rv_destalhes);

        customAdapter = new CustomAdapter(InformacoesPublicar.this,(ArrayList<Descricao>) descricaoList);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

    }


    private String data(){
        Date date = new Date();
        SimpleDateFormat format  =  new SimpleDateFormat("dd/MM/yyyy");
        return format.format(date);
    }


}
