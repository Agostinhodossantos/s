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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import smartshop.co.mz.ui.MainActivity;
import smartshop.co.mz.ui.MainLoja;
import smartshop.co.mz.R;
import smartshop.co.mz.model.Produto;
import smartshop.co.mz.model.ProdutoImagem;
import smartshop.co.mz.publicar.Editar;
import smartshop.co.mz.publicar.Promover;


public class Rv_produtos_user extends RecyclerView.Adapter<Rv_produtos_user.MyViewHoder> implements Filterable {

    private Context mContext;
    private List<Produto> mData;
    private List<Produto> mDataFull;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    StorageReference mStorageRef;

    public Rv_produtos_user(Context mContext, List<Produto> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mDataFull = new ArrayList<>(mData);
    }



    @Override
    public MyViewHoder onCreateViewHolder( ViewGroup viewGroup, int i) {

        View v;

        v = LayoutInflater.from(mContext).inflate(R.layout.card_produto_user, viewGroup,false);

        MyViewHoder viewHoder = new MyViewHoder(v);

        return viewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHoder holder, final int position) {

        inicializarFirebase();
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

        try {

            if (mData.get(position).getImg() != null && !mData.get(position).getImg().trim().equals("") ){

                Picasso.get().load(mData.get(position).getImg()).fit().centerCrop().into(holder.img);

            }
            else
            {

            }
        }catch (Exception e){

        }



        holder.relativeLayout.toggle();
        holder.img_mais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.relativeLayout.toggle();

            }
        });
        holder.tv_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext , Editar.class);
                intent.putExtra("id_produto" ,mData.get(position).getUid());
                intent.putExtra("id_user" , mData.get(position).getIdUser());
                mContext.startActivity(intent);
            }
        });

        holder.tv_deletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String pos = mData.get(position).getUid();
                final String userPos = mData.get(position).getIdUser();

                databaseReference.child("Produto").child(pos).removeValue();
                databaseReference.child("Usuario").child(userPos).child("Produto").child(pos).removeValue();


                deletarImg(userPos , pos);

            }


            private void deletarImg(String userPoss ,String produtoId) {
                final List<ProdutoImagem> imagemList = new ArrayList<>();
                mStorageRef = FirebaseStorage.getInstance().getReference("Imagem");

                databaseReference.child("Usuario").child(userPoss).child("Produto").child(produtoId).child("Imagem").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                            ProdutoImagem imagem = objSnapshot.getValue(ProdutoImagem.class);
                            imagemList.add(imagem);
                            final StorageReference riversRef = mStorageRef.child(imagem.getUuid());
                            riversRef.delete();
                        }

                        Toast.makeText(mContext, "Deletado com sucesso", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(mContext , MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);



                        //todo remover img //


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        holder.tv_promover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext , Promover.class);
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


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext , MainLoja.class);
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
        private ImageView img_mais;
        private TextView tv_localizaao;
        private ExpandableRelativeLayout relativeLayout;
        private CardView cardView;
        private TextView tv_editar , tv_deletar , tv_promover;

        public MyViewHoder(@NonNull View itemView) {
            super(itemView);

            tv_nome = itemView.findViewById(R.id.tv_nome_id);
            tv_localizaao = itemView.findViewById(R.id.tv_localizacao);
            tv_preco = itemView.findViewById(R.id.tv_preco_id);
            tv_tempo = itemView.findViewById(R.id.tv_tempo);
            img = itemView.findViewById(R.id.img_produto_id);
            img_mais = itemView.findViewById(R.id.btn_mais);
            cardView = itemView.findViewById(R.id.cardview_id);
            relativeLayout = itemView.findViewById(R.id.expanded_menu);
            tv_deletar = itemView.findViewById(R.id.tv_deletar);
            tv_editar = itemView.findViewById(R.id.tv_editar);
            tv_promover = itemView.findViewById(R.id.tv_promover);


        }
    }

    private void inicializarFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

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
