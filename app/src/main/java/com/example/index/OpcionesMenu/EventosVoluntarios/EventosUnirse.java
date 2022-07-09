package com.example.index.OpcionesMenu.EventosVoluntarios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.index.MapaEvento;
import com.example.index.OpcionesMenu.EventosAdministrador.CrearEventos;
import com.example.index.OpcionesMenu.EventosAdministrador.EditarEventos;
import com.example.index.R;
import com.example.index.Splash.SplashEventoCreado;
import com.example.index.Splash.SplashEventoRegistrado;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EventosUnirse extends AppCompatActivity {

    private EditText xdescrip;
    private TextView xtitulo,xubicacion;
    private Button xjoin,xnotjoin,xbtnmapa;
    private FirebaseAuth auth;
    private DatabaseReference joinreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos_unirse);

        auth = FirebaseAuth.getInstance();
        joinreference = FirebaseDatabase.getInstance().getReference();

        xtitulo = (TextView) findViewById(R.id.joineventname);
        xubicacion = (TextView) findViewById(R.id.joineventdireccion);
        xdescrip = (EditText) findViewById(R.id.joineventdescripcion);
        xjoin = (Button) findViewById(R.id.joinbtnasistencia);
        xnotjoin = (Button) findViewById(R.id.joinbtncancelar);
        xbtnmapa = (Button)findViewById(R.id.btnmapaunirse);
        ConfigDefaul();
        RefreshMap();
        LoadingData();

    }
    public void onStart(){
        super.onStart();

        xjoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InscribirseEvento();
            }
        });
    }

    public void RefreshMap(){
        xbtnmapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EventosUnirse.this, MapaEvento.class));
            }
        });
    }

    public void LoadingData(){
        String name = getIntent().getStringExtra("titulo");
        String desc = getIntent().getStringExtra("descripcion");
        String ubic = getIntent().getStringExtra("ubicacion");

        xtitulo.setText(name);
        xdescrip.setText(desc);
        xubicacion.setText(ubic);
    }

    public void ConfigDefaul(){
        xnotjoin.setBackgroundResource(R.color.sombra);
        xnotjoin.setEnabled(false);
        xdescrip.setEnabled(false);
    }
    public void InscribirseEvento(){
        Map<String,Object> xuserx = new HashMap<>();
        String codeevent = getIntent().getStringExtra("id");
        String codeuser = auth.getCurrentUser().getUid();
        xuserx.put("Codigo de usuario",codeuser);//(String,Objeto)
        joinreference.child("Inscripcion/"+codeevent).child(codeuser).setValue(xuserx);
        msm("Su registro se ha realizado con exito");
    }
    public void msm (String m){
        Toast.makeText(this,m,Toast.LENGTH_LONG).show();}
}