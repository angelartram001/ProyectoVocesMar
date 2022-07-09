package com.example.index.OpcionesMenu.Convocatorias;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.index.MenuUser;
import com.example.index.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DescripConvocatoria extends AppCompatActivity {

    private EditText solname,solgmail,soledad,solcoment;
    private FirebaseAuth auth;
    private DatabaseReference dbref;
    private Button btnacep,btndene;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descrip_convocatoria);

        solname = (EditText) findViewById(R.id.cont_sol_name);
        solgmail = (EditText) findViewById(R.id.cont_sol_correo);
        soledad = (EditText) findViewById(R.id.cont_sol_edad);
        solcoment = (EditText) findViewById(R.id.cont_sol_motivo);

        auth = FirebaseAuth.getInstance();
        dbref = FirebaseDatabase.getInstance().getReference();

        LoadingData();

    }
    public void LoadingData(){

        String name = getIntent().getStringExtra("name").trim();
        String gmail = getIntent().getStringExtra("gmail").trim();
        int edad = getIntent().getIntExtra("edad",1);
        String coment = getIntent().getStringExtra("coment").trim();

        solname.setText(name);
        solgmail.setText(gmail);
        soledad.setText(edad+"");
        solcoment.setText(coment);

        solname.setEnabled(false);
        solgmail.setEnabled(false);
        soledad.setEnabled(false);
        solcoment.setEnabled(false);
    }

    public void onStart(){
        super.onStart();

        btnacep = (Button)findViewById(R.id.solbtnaceptar);
        btnacep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = getIntent().getStringExtra("id");
                Map<String,Object> user = new HashMap<>();
                user.put("active",true);
                dbref.child("Usuarios").child(id).updateChildren(user);
                startActivity(new Intent(DescripConvocatoria.this, MenuUser.class));
                finish();
            }
        });

        btndene = (Button) findViewById(R.id.solbtnrechazar);
        btndene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DescripConvocatoria.this, MenuUser.class));
                finish();
            }
        });
    }


}
