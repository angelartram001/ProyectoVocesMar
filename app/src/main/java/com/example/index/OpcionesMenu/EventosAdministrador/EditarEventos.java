
package com.example.index.OpcionesMenu.EventosAdministrador;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.index.MapaEvento;
import com.example.index.MenuUser;
import com.example.index.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class EditarEventos extends AppCompatActivity {

    private EditText xtitulo,xdescrip,xubicacion;
    private Button xeditar,xcancelar,xmapa;
    private TextView xguardar;
    private DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_editar_eventos);
        dbref = FirebaseDatabase.getInstance().getReference();

        xtitulo = (EditText) findViewById(R.id.editeventnombre);
        xdescrip = (EditText) findViewById(R.id.editeventdescripcion);
        xubicacion = (EditText) findViewById(R.id.editeventdireccion);
        xeditar = (Button) findViewById(R.id.editeventbtneditar);
        xcancelar = (Button) findViewById(R.id.editeventbtncancelar);
        xguardar = (TextView) findViewById(R.id.editeventSave);
        xmapa = (Button) findViewById(R.id.btnmap);
        DefaulConf();
        xguardar.setVisibility(View.INVISIBLE);
        RefreshMap();
        LoadingData();
    }
    public void onStart(){
        super.onStart();

        xeditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModeEdit(v);
            }
        });

        xcancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditarEventos.this, MenuUser.class));
            }
        });

        xguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = getIntent().getStringExtra("idevento");
                Map<String,Object> xevent = new HashMap<>();
                xevent.put("name",xtitulo.getText().toString());
                xevent.put("descripcion",xdescrip.getText().toString());
                xevent.put("ubicacion",xubicacion.getText().toString());
                dbref.child("Eventos").child(id).updateChildren(xevent);
                DefaulConf();
                xguardar.setVisibility(View.INVISIBLE);
                msm("Los datos se modificaron exitosamente");
            }
        });
    }

    public void RefreshMap(){
        xmapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditarEventos.this, MapaEvento.class));
            }
        });
    }

    public void LoadingData(){
        String name = getIntent().getStringExtra("name");
        String desc = getIntent().getStringExtra("descripcion");
        String ubic = getIntent().getStringExtra("ubicacion");

        xtitulo.setText(name);
        xdescrip.setText(desc);
        xubicacion.setText(ubic);
    }
    public void DefaulConf(){
        xtitulo.setEnabled(false);
        xtitulo.setBackgroundResource(R.color.white);
        xdescrip.setEnabled(false);
        xdescrip.setBackgroundResource(R.color.white);
        xubicacion.setEnabled(false);
        xubicacion.setBackgroundResource(R.color.white);
    }
    public void ModeEdit(View v){
        xtitulo.setEnabled(true);
        xtitulo.setBackgroundResource(R.color.editperf);
        xdescrip.setEnabled(true);
        xdescrip.setBackgroundResource(R.color.editperf);
        xubicacion.setEnabled(true);
        xubicacion.setBackgroundResource(R.color.editperf);
        xguardar.setVisibility(View.VISIBLE);
    }
    public void msm (String m){
        Toast.makeText(EditarEventos.this,m,Toast.LENGTH_LONG).show();}

}