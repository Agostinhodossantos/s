package smartshop.co.mz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import de.hdodenhof.circleimageview.CircleImageView;

public class Donativo extends AppCompatActivity {

    CircleImageView  img_mpeza , img_visa  , img_paypal;
    ExpandableRelativeLayout numeroLayout;
    CircleImageView img ;
    TextView        tv  ;
    private boolean clicou = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donativo);

        img = findViewById(R.id.img_demo);
        tv =  findViewById(R.id.tv_demo);
        img_mpeza = findViewById(R.id.img_mpesa);
        img_paypal = findViewById(R.id.img_paypal);
        img_visa = findViewById(R.id.img_visa);
        numeroLayout = findViewById(R.id.numero);

        img_mpeza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                img.setImageResource(R.drawable.mpesa);
                tv.setText("843655568");
                numeroLayout.expand();

            }
        });

        img_paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img.setImageResource(R.drawable.paypal);
                tv.setText("agostinhodev@gmail.com");
                numeroLayout.expand();


            }
        });
        img_visa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img.setImageResource(R.drawable.milenium);
                tv.setText("numero da conta: 461682171");
                numeroLayout.expand();

            }
        });

    }
}
