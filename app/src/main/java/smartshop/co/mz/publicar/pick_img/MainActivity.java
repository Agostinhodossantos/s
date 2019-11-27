package smartshop.co.mz.publicar.pick_img;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;

import smartshop.co.mz.R;
import smartshop.co.mz.publicar.InformacoesPublicar;
import smartshop.co.mz.publicar.pick_img.adapters.GalleryItemAdapter;
import smartshop.co.mz.publicar.pick_img.model.Picture;
import smartshop.co.mz.publicar.pick_img.utils.ConstantDataManager;
import smartshop.co.mz.publicar.pick_img.utils.Libraries;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewGallery;
    private ArrayList<Picture> pictures;
    GalleryItemAdapter adapter;
    Handler handler;

    private Button imageViewButtonSend;
    private TextView textViewSelectedCount;
    private ConstraintLayout constraintLayoutSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);


        imageViewButtonSend = findViewById(R.id.button_send);
        textViewSelectedCount = findViewById(R.id.textViewSeletedCount);
        constraintLayoutSend = findViewById(R.id.layoutSend);


        Button imageViewSendDetail = findViewById(R.id.button_send);
        imageViewSendDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDetail = new Intent(MainActivity.this, InformacoesPublicar.class);

                ArrayList<Picture> picturesSelected = adapter.getAllPictureSelected();
                intentDetail.putParcelableArrayListExtra("listpicture", picturesSelected);
                startActivity(intentDetail);

            }
        });

        pictures = new ArrayList<>();
        recyclerViewGallery = findViewById(R.id.recyclerViewGallery);
        recyclerViewGallery.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new GalleryItemAdapter(this, pictures, new GalleryItemAdapter.ItemSelectedChangeListener() {
            @Override
            public void onItemSelectedChange(int number) {
                if (number > 0) {
                    constraintLayoutSend.setVisibility(View.VISIBLE);
                    textViewSelectedCount.setText(number + "");
                } else {
                    constraintLayoutSend.setVisibility(View.GONE);
                }
            }
        });
        recyclerViewGallery.setAdapter(adapter);
        handler = new Handler();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //lay hinh tu camera
            Libraries.requestPermissionStorage(MainActivity.this);
        } else {
            new Thread() {

                @Override
                public void run() {
                    Looper.prepare();
                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            pictures.clear();
                            pictures.addAll(Picture.getGalleryPhotos(MainActivity.this));
                            adapter.notifyDataSetChanged();
                            //imageListRecyclerAdapter.addAll(getGalleryPhotos());
                            //checkImageStatus();
                        }
                    });
                    Looper.loop();
                }


            }.start();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ConstantDataManager.PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                new Thread() {

                    @Override
                    public void run() {
                        Looper.prepare();
                        handler.post(new Runnable() {

                            @Override
                            public void run() {
                                pictures.clear();
                                pictures.addAll(Picture.getGalleryPhotos(MainActivity.this));
                                adapter.notifyDataSetChanged();
                                //imageListRecyclerAdapter.addAll(getGalleryPhotos());
                                //checkImageStatus();
                            }
                        });
                        Looper.loop();
                    }
                }.start();

            } else {
                //deny
                Libraries.requestPermissionStorageDeny(MainActivity.this);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


}
