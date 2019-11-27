package smartshop.co.mz.vp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.List;

import smartshop.co.mz.model.ProdutoImagem;

public class ViewPagerAdapterFull extends PagerAdapter {

    private Context context;
    public List<ProdutoImagem> mListt;

    public ViewPagerAdapterFull(Context context, List<ProdutoImagem> mListt){

        this.mListt = mListt;
        this.context = context;

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
        Picasso.get().load(mListt.get(position).getImg()).into(imageView);
        container.addView(imageView);
        return imageView;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
       //destroyItem(container, position, object);
    }
}
