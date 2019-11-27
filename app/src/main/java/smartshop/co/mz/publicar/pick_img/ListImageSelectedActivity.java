package smartshop.co.mz.publicar.pick_img;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import smartshop.co.mz.R;
import smartshop.co.mz.publicar.pick_img.adapters.ImageSelectedAdapter;
import smartshop.co.mz.publicar.pick_img.model.Picture;

public class ListImageSelectedActivity extends AppCompatActivity {

    private RecyclerView recyclerViewImageSelected;
    ImageSelectedAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_image_selected);
        recyclerViewImageSelected=findViewById(R.id.recyclerViewSelected);

        Intent intent=getIntent();
        List<Picture> picturesSelected=intent.getParcelableArrayListExtra("listpicture");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewImageSelected.setLayoutManager(layoutManager);

        int[] a  = new int[9];
        a[0] = 1;

        adapter=new ImageSelectedAdapter(this,picturesSelected);
        recyclerViewImageSelected.setAdapter(adapter);

        Log.d("soluonghinh",picturesSelected.size()+"");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(adapter!=null){
            recyclerViewImageSelected=null;
            adapter=null;
            Runtime.getRuntime().gc();
        }
        Log.d("destroy","destroy_______________________");
    }
}
