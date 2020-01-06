package smartshop.co.mz.rv;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import smartshop.co.mz.ui.Loja;
import smartshop.co.mz.R;
import smartshop.co.mz.model.LojaModel;


public class Rv_loja extends RecyclerView.Adapter<Rv_loja.MyViewHoder> implements Filterable {

        Context mContext;
        List<LojaModel> mData;
        List<LojaModel> mDataFull;

    public Rv_loja(Context mContext, List<LojaModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mDataFull = new ArrayList<>(mData);
    }



    @Override
    public MyViewHoder onCreateViewHolder( ViewGroup viewGroup, int i) {

        View v;

        v = LayoutInflater.from(mContext).inflate(R.layout.card_loja_row, viewGroup,false);

        MyViewHoder viewHoder = new MyViewHoder(v);

        return viewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHoder holder, final int position) {

        String strPrimeiraMalicula = mData.get(position).getNome();


        try {
            if (mData.get(position).getNome() != null){
                if (mData.get(position).getNome().length() > 2){
                    strPrimeiraMalicula =  mData.get(position).getNome().substring(0 , 1).toUpperCase()+mData.get(position).getNome().substring(1).toLowerCase();
                    holder.tv_nome.setText(strPrimeiraMalicula);

                }
            }

            if (mData.get(position).getLocalizacao() != null){
                if (mData.get(position).getLocalizacao().length() > 2){
                    strPrimeiraMalicula =  mData.get(position).getLocalizacao().substring(0 , 1).toUpperCase()+mData.get(position).getLocalizacao().substring(1).toLowerCase();
                }
            }


        }catch (Exception e){

        }


        holder.tv_localizaao.setText(strPrimeiraMalicula);

        Picasso.get().load(mData.get(position).getImg()).into(holder.img);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext , Loja.class);
                intent.putExtra("nome", mData.get(position).getNome());
                intent.putExtra("img" , mData.get(position).getImg());
                intent.putExtra("uid", mData.get(position).getUid());
                intent.putExtra("uid_user", mData.get(position).getUid_user());
                mContext.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHoder extends RecyclerView.ViewHolder{

        private TextView tv_nome;
        private ImageView img;
        private TextView tv_localizaao;
        CardView cardView;

        public MyViewHoder(@NonNull View itemView) {
            super(itemView);

            tv_nome = itemView.findViewById(R.id.tv_nome_loja);
            tv_localizaao = itemView.findViewById(R.id.tv_localizacao);
            img = itemView.findViewById(R.id.img_loja_id);
            cardView = itemView.findViewById(R.id.cardview_id);


        }
    }


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter  = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
           List<LojaModel> filteredList = new ArrayList<>();

           if (constraint == null || constraint.length() == 0){
               filteredList.addAll(mDataFull);
           }else {
               String filterPattern = constraint.toString().toLowerCase().trim();
               for (LojaModel item : mDataFull){
                   if (item.getNome().toLowerCase().contains(filterPattern) || item.getNome().toLowerCase().contains(filterPattern)){
                       filteredList.add(item);
                   }
               }
           }

           FilterResults results = new FilterResults();
           results.values = filteredList;
           return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            mData.clear();
            mData.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };
}
