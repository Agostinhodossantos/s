package smartshop.co.mz.chat;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import smartshop.co.mz.R;
import smartshop.co.mz.model.ChatList;
import smartshop.co.mz.rv.RecyclerViewAdapterChatList;

public class ChatListMain extends AppCompatActivity {

    private List<ChatList> chatLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        inicializarChatList();
    }

    private void inicializarChatList() {
        inserirListaChat();
        RecyclerView recyclerView = findViewById(R.id.rv_list_chat);
        RecyclerViewAdapterChatList chat = new RecyclerViewAdapterChatList(this , chatLists);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(chat);
    }

    private void inserirListaChat() {

        chatLists.add(new ChatList("Agostinho dos santos" , "boa tarde como esta" , "06/08/19" ,"https://firebasestorage.googleapis.com/v0/b/mfood-7e236.appspot.com/o/tempimg%2Ftransferir.jpg?alt=media&token=c74d3589-9a79-45da-ae21-65153aab618d","4"));
        chatLists.add(new ChatList("Justino" , "boa tarde como esta" , "07/08/19" ,"https://firebasestorage.googleapis.com/v0/b/mfood-7e236.appspot.com/o/tempimg%2Fdepositphotos_220066854-stock-illustration-pasta-menuwith-frame-of-italian.jpg?alt=media&token=6a0b7253-4962-4f82-b49e-bce1901b8e44","4"));

    }

}
