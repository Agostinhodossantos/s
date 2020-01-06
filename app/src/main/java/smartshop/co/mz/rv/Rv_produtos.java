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

import smartshop.co.mz.ui.Loja_Produto;
import smartshop.co.mz.R;
import smartshop.co.mz.model.Produto;


public class Rv_produtos extends RecyclerView.Adapter<Rv_produtos.MyViewHoder> implements Filterable {

        Context mContext;
        List<Produto> mData;
        List<Produto> mDataFull;

    public Rv_produtos(Context mContext, List<Produto> mData) {
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

           // strPrimeiraMalicula =  mData.get(position).getLocalizacao().substring(0 , 1).toUpperCase()+mData.get(position).getLocalizacao().substring(1).toLowerCase();

        }

        holder.tv_localizaao.setText(strPrimeiraMalicula);
        holder.tv_tempo.setText(mData.get(position).getTempo());
        holder.tv_preco.setText(mData.get(position).getPreco()+" MT");
        if (mData.get(position).getImg() != null && !mData.get(position).getImg().trim().equals("") ){

            Picasso.get().load(mData.get(position).getImg()).fit().centerCrop().into(holder.img);

        }
        else
        {

        }


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext , Loja_Produto.class);
                intent.putExtra("id_produto" ,mData.get(position).getUid());
                intent.putExtra("id_user" , mData.get(position).getIdUser());
                intent.putExtra("preco" , mData.get(position).getPreco());
                intent.putExtra("categoria" , mData.get(position).getCategoria());
                intent.putExtra("titulo" , mData.get(position).getNome());
                intent.putExtra("tempo" , mData.get(position).getTempo());
                intent.putExtra("pronvicia" , mData.get(position).getDistrito());
                intent.putExtra("posicao" ,String.valueOf(mData.get(position).getPosicao()));
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
        private TextView tv_tempo;
        private TextView tv_localizaao;
        CardView cardView;

        public MyViewHoder(@NonNull View itemView) {
            super(itemView);

            tv_nome = itemView.findViewById(R.id.tv_nome_id);
            tv_localizaao = itemView.findViewById(R.id.tv_localizacao);
            tv_preco = itemView.findViewById(R.id.tv_preco_id);
            tv_tempo = itemView.findViewById(R.id.tv_tempo);
            img = itemView.findViewById(R.id.img_produto_id);
            cardView = itemView.findViewById(R.id.cardview_id);


        }
    }

    public boolean filtroAvancado(String provincia  , String distrito , String categoria , String sub_categoria){
        List<Produto> filteredList = new ArrayList<>();
        filteredList.addAll(mDataFull);

        for (Produto item : mDataFull){
            if (item.getDistrito().toLowerCase().contains(provincia) && item.getCategoria().toLowerCase().contains(categoria) ){
                filteredList.add(item);
            }
        }

        mData.clear();
        mData.addAll(filteredList);
        notifyDataSetChanged();

        return true;
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
