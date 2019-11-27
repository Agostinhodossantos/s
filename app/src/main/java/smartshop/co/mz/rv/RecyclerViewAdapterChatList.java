package smartshop.co.mz.rv;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import smartshop.co.mz.R;
import smartshop.co.mz.chat.Chat;
import smartshop.co.mz.model.ChatList;


public class RecyclerViewAdapterChatList extends RecyclerView.Adapter<RecyclerViewAdapterChatList.MyViewHoder> {

        Context mContext;
        List<ChatList> mData;

    public RecyclerViewAdapterChatList(Context mContext, List<ChatList> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }



    @Override
    public MyViewHoder onCreateViewHolder( ViewGroup viewGroup, int i) {

        View v;

        v = LayoutInflater.from(mContext).inflate(R.layout.card_char_row, viewGroup,false);

        MyViewHoder viewHoder = new MyViewHoder(v);

        return viewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHoder holder, final int position) {

        holder.tv_data.setText(mData.get(position).getNome());
        holder.tv_data.setText(mData.get(position).getTempo());
        holder.tv_nome.setText(mData.get(position).getNome());
        holder.tv_ultima_mensagem.setText(mData.get(position).getMensagem());
        Picasso.get().load(mData.get(position).getImg()).into(holder.img);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext , Chat.class);
                mContext.startActivity(intent);

            }
        });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHoder extends RecyclerView.ViewHolder{

        private TextView tv_nome , tv_ultima_mensagem , tv_data ;
        private CircleImageView img;

        CardView cardView;

        public MyViewHoder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_chat);
            tv_nome  = itemView.findViewById(R.id.user_nome);
            tv_ultima_mensagem = itemView.findViewById(R.id.ultima_mensagem);
            tv_data = itemView.findViewById(R.id.data);
            img = itemView.findViewById(R.id.img_perfil);



        }
    }



}
