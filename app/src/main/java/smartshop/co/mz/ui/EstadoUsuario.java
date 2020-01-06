package smartshop.co.mz.ui;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

import smartshop.co.mz.R;
import smartshop.co.mz.model.Usuario;

public class EstadoUsuario extends Application implements
        Application.ActivityLifecycleCallbacks {




    String APP_PREFS = "app_prefs";
    String id_KEY = "id";
    private SharedPreferences prefs = getSharedPreferences(APP_PREFS , MODE_PRIVATE);
    String id = prefs.getString(id_KEY , null);

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();


   public void setOnline(final String mensa) {


        databaseReference.child("Usuario").child(id).child("dados").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    Usuario  usuario1 = objSnapshot.getValue(Usuario.class);
                    usuario1.setEstado(mensa);
                    databaseReference.child("Usuario").child(id).child("dados").setValue(usuario1);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
        setOnline("online");
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        setOnline("online");
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        setOnline("online");
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        setOnline("Ultima vez online "+data());
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        setOnline("Ultima vez online "+data());
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {
        setOnline("Ultima vez online "+data());
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        setOnline("Ultima vez online "+data());
    }

    private String data(){
        Date date = new Date();
        SimpleDateFormat format  =  new SimpleDateFormat("dd/MM/yyyy");
        String data = format.format(date);
        format  =  new SimpleDateFormat("hh:mm");
        String time = format.format(date);

        data = data+" "+getString(R.string.a)+" "+time;


        return data;
    }
}
