package smartshop.co.mz.vp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import smartshop.co.mz.ui.ImagemTelaCheia;
import smartshop.co.mz.model.ProdutoImagem;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private int poss;
    public List<ProdutoImagem> mListt;

    public ViewPagerAdapter(Context context, List<ProdutoImagem> mListt , int poss){

        this.mListt = mListt;
        this.context = context;
        this.poss = poss;

    }
    @Override
    public int getCount() {
        return mListt.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view == object;

    }

    @NonNull

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        Picasso.get().load(mListt.get(position).getImg()).fit().centerCrop().into(imageView);
        container.addView(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , ImagemTelaCheia.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("imagem" , (Serializable) mListt);
                intent.putExtra("posicao" , poss);
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });
        return imageView;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
       //destroyItem(container, position, object);
    }
}
