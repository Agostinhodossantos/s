package smartshop.co.mz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import smartshop.co.mz.model.ProdutoImagem;
import smartshop.co.mz.vp.ViewPagerAdapterFull;

public class ImagemTelaCheia extends AppCompatActivity {

    private List<ProdutoImagem> mList = new ArrayList<>();
    private int posicao = 0;
    private ViewPager viewPager ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagem_tela_cheia);

        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();

        mList = (List<ProdutoImagem>) bundle.getSerializable("imagem");

        if (intent != null ){

           //posicao = Integer.parseInt(intent.getStringExtra("posicao"));

        }

        viewPager = findViewById(R.id.vp_publicidade_id);
        ViewPagerAdapterFull mAdapterIntro = new ViewPagerAdapterFull(this,mList);
        viewPager.setAdapter(mAdapterIntro);
        viewPager.setCurrentItem(posicao);
    }
}
