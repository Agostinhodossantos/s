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

import smartshop.co.mz.ui.MainLoja;
import smartshop.co.mz.R;
import smartshop.co.mz.model.Produto;


public class Rv_minhas_publicacoes extends RecyclerView.Adapter<Rv_minhas_publicacoes.MyViewHoder> implements Filterable {

        Context mContext;
        List<Produto> mData;
        List<Produto> mDataFull;

    public Rv_minhas_publicacoes(Context mContext, List<Produto> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mDataFull = new ArrayList<>(mData);
    }



    @Override
    public MyViewHoder onCreateViewHolder( ViewGroup viewGroup, int i) {

        View v;

        v = LayoutInflater.from(mContext).inflate(R.layout.card_produto, viewGroup,false);

        MyViewHoder viewHoder = new MyViewHoder(v);

        return viewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHoder holder, final int position) {

        String strPrimeiraMalicula = mData.get(position).getNome();

        if (mData.get(position).getNome().length() > 2){

          strPrimeiraMalicula =  mData.get(position).getNome().substring(0 , 1).toUpperCase()+mData.get(position).getNome().substring(1).toLowerCase();

        }
        holder.tv_nome.setText(strPrimeiraMalicula);

        strPrimeiraMalicula = mData.get(position).getDistrito();

        if (mData.get(position).getNome().length() > 2){

            strPrimeiraMalicula =  mData.get(position).getDistrito().substring(0 , 1).toUpperCase()+mData.get(position).getDistrito().substring(1).toLowerCase();

        }

        holder.tv_localizaao.setText(strPrimeiraMalicula);

        holder.tv_preco.setText(mData.get(position).getPreco()+" MT");
        Picasso.get().load(mData.get(position).getImg()).into(holder.img);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext , MainLoja.class);
                intent.putExtra("id" ,mData.get(position).getUid());
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
        private TextView tv_preco;
        private ImageView img;
        private TextView tv_localizaao;
        CardView cardView;

        public MyViewHoder(@NonNull View itemView) {
            super(itemView);

            tv_nome = itemView.findViewById(R.id.tv_nome_id);
            tv_localizaao = itemView.findViewById(R.id.tv_localizacao);
            tv_preco = itemView.findViewById(R.id.tv_preco_id);
            img = itemView.findViewById(R.id.img_produto_id);
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
           List<Produto> filteredList = new ArrayList<>();

           if (constraint == null || constraint.length() == 0){
               filteredList.addAll(mDataFull);
           }else {
               String filterPattern = constraint.toString().toLowerCase().trim();
               for (Produto item : mDataFull){
                   if (item.getNome().toLowerCase().contains(filterPattern) || item.getCategoria().toLowerCase().contains(filterPattern)){
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
