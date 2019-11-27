package smartshop.co.mz.vp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import smartshop.co.mz.ImagemTelaCheia;
import smartshop.co.mz.MainActivity;
import smartshop.co.mz.MainLoja;
import smartshop.co.mz.model.Produto;
import smartshop.co.mz.model.ProdutoImagem;

public class ViewPagerAdapterPublicidade extends PagerAdapter {

    private Context context;
    public List<ProdutoImagem> mListt;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private List<Produto> produtoList = new ArrayList<>();

    public ViewPagerAdapterPublicidade(Context context, List<ProdutoImagem> mListt ){

        this.mListt = mListt;
        this.context = context;
        inicializarFirebase();

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
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        ImageView imageView = new ImageView(context);
        Picasso.get().load(mListt.get(position).getImg()).fit().centerCrop().into(imageView);
        container.addView(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                produtoDestaque(mListt.get(position).getUuid());

            }
        });

        return imageView;

    }


    private void produtoDestaque(final String id){

        databaseReference.child("ProdutoDestaque").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Produto produto  = objSnapshot.getValue(Produto.class);

                    if (String.valueOf(produto.getPosicao()).equals(id)){


                        Intent intent = new Intent(context , MainLoja.class);

                        intent.putExtra("id_produto" , produto.getUid());
                        intent.putExtra("id_user" ,produto.getIdUser());
                        intent.putExtra("preco" , produto.getPreco());
                        intent.putExtra("categoria" , produto.getCategoria());
                        intent.putExtra("titulo" , produto.getNome());
                        intent.putExtra("tempo" , produto.getTempo());
                        intent.putExtra("pronvicia" , produto.getDistrito());
                        intent.putExtra("posicao" ,String.valueOf(produto.getPosicao()));
                        intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                        break;
                    }

                }

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

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
       //destroyItem(container, position, object);
    }
}
