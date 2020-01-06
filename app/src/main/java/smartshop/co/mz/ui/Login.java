package smartshop.co.mz.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import smartshop.co.mz.R;
import smartshop.co.mz.model.Usuario;

public class Login extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Button btn_entrar , btn_logar;
    private CircleImageView img_perfil;
    private EditText ed_email;
    private EditText ed_password;
    private EditText ed_nome;
    private static final String code_name = "258";
    private FirebaseAuth mAuth;
    final static String APP_PREFS = "app_prefs";
    final static String id_KEY = "id";
    private SharedPreferences prefs;
    public String id ;
    private String email ;
    private String password;
    private String nome;
    private Uri mSelectedUri = null;
    private CallbackManager callbackManager;
    LoginButton loginButton;
    View mView;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MultiDex.install(this);
        inicializarFirebase();
        mAuth = FirebaseAuth.getInstance();

        prefs = getSharedPreferences(APP_PREFS , MODE_PRIVATE);
        id = prefs.getString(id_KEY , null);

        if (id != null){
            Intent intent = new Intent(Login.this , MainActivity.class );
            intent.putExtra("id",id);
            startActivity(intent);
            finish();
        }

       /// FacebookSdk.sdkInitialize(getApplicationContext());
       /// AppEventsLogger.activateApp(this);

        //facebook login//
        callbackManager = CallbackManager.Factory.create();


        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

                // [START_EXCLUDE]
                upDateUi(null);
                // [END_EXCLUDE]
            }

            @Override
            public void onError(FacebookException error) {

                // [START_EXCLUDE]
                upDateUi(null);
                // [END_EXCLUDE]
            }
        });


        btn_entrar = findViewById(R.id.btn_enviar);
        btn_logar = findViewById(R.id.btn_logar);
        ed_email = findViewById(R.id.ed_email);
        ed_password = findViewById(R.id.ed_password);
        ed_nome = findViewById(R.id.ed_nome);
        img_perfil = findViewById(R.id.img_perfil);

        img_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPhoto();
            }
        });

        btn_logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chamarDialog();
            }
        });

        btn_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificarCampos();
            }
        });

    }


    private void handleFacebookAccessToken(AccessToken token) {


        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mView = getLayoutInflater().inflate(R.layout.dialog_progress, null);


        TextView mensagemDial =  mView.findViewById(R.id.tv_progress);
        mensagemDial.setText("cadastrando");
        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        dialog.setCancelable(false);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            upDateUi(user);
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            upDateUi(null);
                        }

                        // [START_EXCLUDE]

                        // [END_EXCLUDE]
                    }
                });
    }
    // [END aut


    private void upDateUi(FirebaseUser user) {
       if (user != null) {


            final Usuario usuario = new Usuario();
            usuario.setEmail(user.getEmail());
            usuario.setPassword(user.getUid());
            usuario.setNome(user.getDisplayName());
            usuario.setPhoto(user.getPhotoUrl().toString());
            usuario.setEstado("online");
            usuario.setLocalizacao("Maputo");
            usuario.setData(data());
            usuario.setUid(user.getUid());

            databaseReference.child("Usuario").child(usuario.getUid()).setValue(usuario);
            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    databaseReference.child("Usuario").child(usuario.getUid()).child("dados").child(usuario.getUid()).setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            id = usuario.getUid().trim();
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString(id_KEY, id);
                            editor.commit();

                            Intent intent = new Intent(Login.this, MainActivity.class);
                            intent.putExtra("id", usuario.getUid());
                            intent.putExtra("email", usuario.getEmail());

                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();

                            dialog.dismiss();
                        }
                    });


                }


                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }else {
            dialog.dismiss();
            Toast.makeText(this, "falha tente novamente", Toast.LENGTH_SHORT).show();
        }
    }


    private void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0){
            if (data.getData()!=null){
                mSelectedUri = data.getData();
            }

            if (requestCode == 0) {
                if (mSelectedUri == null){
                    img_perfil.requestFocus();
                    Toast.makeText(this, "coloca foto de perfil", Toast.LENGTH_SHORT).show();
                }else {


                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mSelectedUri);
                        img_perfil.setImageDrawable(new BitmapDrawable(bitmap));
                    } catch (IOException e) {
                    }
                }


            }
        }else{

            callbackManager.onActivityResult(requestCode ,resultCode ,data);
        }
    }

    private void inicializarFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    private void verificarCampos() {
        VerificarCampos verificarCampos = new VerificarCampos();

        nome = ed_nome.getText().toString();

        email = ed_email.getText().toString().trim();

        password = ed_password.getText().toString().trim();

        if (verificarCampos.isCampoVazio(nome)){
            ed_nome.requestFocus();
            ed_nome.setHintTextColor(Color.RED);
        }
        else if (verificarCampos.isCampoVazio(email)) {
            ed_email.requestFocus();
            ed_email.setHintTextColor(Color.RED);
            if (verificarCampos.isEmailValido(email)){
                Toast.makeText(this, "email invalido", Toast.LENGTH_SHORT).show();
                ed_email.requestFocus();
            }
        } else if (verificarCampos.isCampoVazio(password)) {
            ed_password.requestFocus();
            ed_password.setHintTextColor(Color.RED);
            if (ed_password.getText().toString().trim().length() < 6){
                ed_password.requestFocus();
                Toast.makeText(this, "Password deve ter mais de 6 digitos", Toast.LENGTH_SHORT).show();
            }
        } else {
            //validar photo //
            if (mSelectedUri == null){
                img_perfil.setCircleBackgroundColor(Color.RED);
                Toast.makeText(this, "coloca foto de perfil", Toast.LENGTH_SHORT).show();
            }else {
                inserirDados();
            }

        }
    }

    private void inserirDados() {



        View mView;
        final AlertDialog dialog;

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mView = getLayoutInflater().inflate(R.layout.dialog_progress, null);


        TextView mensagemDial =  mView.findViewById(R.id.tv_progress);
        mensagemDial.setText("cadastrando");
        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        dialog.setCancelable(false);




        mAuth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    String filename = UUID.randomUUID().toString().concat(data());
                    final StorageReference ref = FirebaseStorage.getInstance().getReference("/Imagem/perfil" + filename);
                    ref.putFile(mSelectedUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {


                                            final Usuario usuario = new Usuario();
                                            usuario.setEmail(ed_email.getText().toString().trim());
                                            usuario.setPassword(ed_password.getText().toString().trim());
                                            usuario.setNome(ed_nome.getText().toString());
                                            usuario.setPhoto(uri.toString());
                                            usuario.setEstado("online");
                                            usuario.setLocalizacao("Maputo");
                                            usuario.setData(data());
                                            usuario.setUid(mAuth.getCurrentUser().getUid());

                                            databaseReference.child("Usuario").child(usuario.getUid()).setValue(usuario);
                                            databaseReference.addChildEventListener(new ChildEventListener() {
                                                @Override
                                                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                                    databaseReference.child("Usuario").child(usuario.getUid()).child("dados").child(usuario.getUid()).setValue(usuario);
                                                    databaseReference.addChildEventListener(new ChildEventListener() {
                                                        @Override
                                                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                                            id =  usuario.getUid().trim();
                                                            SharedPreferences.Editor editor = prefs.edit();
                                                            editor.putString(id_KEY , id);
                                                            editor.commit();

                                                            Intent intent = new Intent(Login.this , MainActivity.class );
                                                            intent.putExtra("id", usuario.getUid());
                                                            intent.putExtra("email" , usuario.getEmail() );

                                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                            startActivity(intent);
                                                            finish();

                                                        }

                                                        @Override
                                                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                                        }

                                                        @Override
                                                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                                                        }

                                                        @Override
                                                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    });
                                                }

                                                @Override
                                                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                                }

                                                @Override
                                                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                                                }

                                                @Override
                                                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });



                                        }
                                    });


                                }
                            });



                }else {


                    Toast.makeText(Login.this, "ocorreu um erro tente novamente", Toast.LENGTH_SHORT).show();

                    dialog.dismiss();



                }
            }
        });



    }

    private void logando(){


    }
    private void chamarDialog() {
        View mView;
        AlertDialog dialog;

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mView = getLayoutInflater().inflate(R.layout.card_login, null);

        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        final EditText ed_email      = mView.findViewById(R.id.ed_email);
        final EditText ed_password   = mView.findViewById(R.id.ed_password);
        Button btn_enviar  = mView.findViewById(R.id.btn_enviar);

        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = ed_email.getText().toString().trim();
                password = ed_password.getText().toString().trim();

                VerificarCampos campos = new VerificarCampos();

                if (campos.isCampoVazio(email)){
                    ed_email.requestFocus();
                    ed_email.setHintTextColor(Color.RED);
                }else if(campos.isCampoVazio(password)){
                    ed_password.requestFocus();
                    ed_password.setHintTextColor(Color.RED);
                }else {
                    verificarUsuarioDB();

                }
            }
        });

    }

    private void verificarUsuarioDB() {

        databaseReference.child("Usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    Usuario usuario = objSnapshot.getValue(Usuario.class);
                    if (usuario.getEmail().trim().equals(email)){
                        if (usuario.getPassword().equals(password)){

                            id =  usuario.getUid().trim();
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString(id_KEY , id);
                            editor.commit();

                            Intent intent = new Intent(Login.this , MainActivity.class );
                            intent.putExtra("id", usuario.getUid());
                            intent.putExtra("email" , usuario.getEmail() );

                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();

                            break;
                        }else {

                            usuarioNaoEncontrado();
                        }
                        break;
                    }else {
                        usuarioNaoEncontrado();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void usuarioNaoEncontrado() {
        Toast.makeText(this, "Email ou Password incorecto", Toast.LENGTH_SHORT).show();
    }

    private String data(){
        Date date = new Date();
        SimpleDateFormat format  =  new SimpleDateFormat("dd/MM/yyyy");
        String data = format.format(date);
        format  =  new SimpleDateFormat("hh:mm");
        String time = format.format(date);

        return data;
    }



}
