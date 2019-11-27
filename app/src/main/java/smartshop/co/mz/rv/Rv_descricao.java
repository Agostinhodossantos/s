package smartshop.co.mz.rv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import smartshop.co.mz.R;
import smartshop.co.mz.model.Comentario;
import smartshop.co.mz.model.Descricao;
import smartshop.co.mz.model.DescricaoProduto;


public class Rv_descricao extends RecyclerView.Adapter<Rv_descricao.MyViewHoder> {

        Context mContext;
        List<Descricao> mData;

    public Rv_descricao(Context mContext, List<Descricao> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }



    @Override
    public MyViewHoder onCreateViewHolder( ViewGroup viewGroup, int i) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.card_descricao, viewGroup,false);

        MyViewHoder viewHoder = new MyViewHoder(v);

        return viewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHoder holder, final int position) {
        holder.tv_conceito.setText(mData.get(position).getConceito());
        holder.tv_definicao.setText(mData.get(position).getDefinicao());
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHoder extends RecyclerView.ViewHolder{


        private TextView tv_conceito;
        private TextView tv_definicao;
        CardView cardView;

        public MyViewHoder(@NonNull View itemView) {
            super(itemView);

            tv_definicao = itemView.findViewById(R.id.definicao);
            tv_conceito  = itemView.findViewById(R.id.conceito);

        }
    }



}
