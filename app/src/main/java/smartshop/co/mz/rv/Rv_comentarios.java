package smartshop.co.mz.rv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import smartshop.co.mz.R;
import smartshop.co.mz.model.Comentario;


public class Rv_comentarios extends RecyclerView.Adapter<Rv_comentarios.MyViewHoder> {

        Context mContext;
        List<Comentario> mData;

    public Rv_comentarios(Context mContext, List<Comentario> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }



    @Override
    public MyViewHoder onCreateViewHolder( ViewGroup viewGroup, int i) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.card_comentarios, viewGroup,false);

        MyViewHoder viewHoder = new MyViewHoder(v);

        return viewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHoder holder, final int position) {
        holder.tv_nome.setText(mData.get(position).getNome());
        holder.tv_comentario.setText(mData.get(position).getComentario());
        holder.tv_data.setText(mData.get(position).getData());

        if (mData.get(position).getImg().isEmpty()){
        }else {
            Picasso.get().load(mData.get(position).getImg()).into(holder.img);
        }



    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHoder extends RecyclerView.ViewHolder{


        private TextView tv_nome;
        private TextView tv_data;
        private ImageView img;
        private TextView tv_comentario;
        CardView cardView;

        public MyViewHoder(@NonNull View itemView) {
            super(itemView);

            tv_nome = itemView.findViewById(R.id.tv_nome_user_id);
            tv_comentario = itemView.findViewById(R.id.tv_comentario);
            tv_data = itemView.findViewById(R.id.tv_tempo_id);
            img = itemView.findViewById(R.id.img_user_id);

        }
    }



}
