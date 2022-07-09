package com.example.index;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.index.OpcionesMenu.EventosAdministrador.CrearEventos;

public class Inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
    }

    public void btniniciar(View view) {
        startActivity(new Intent(Inicio.this,Login.class));
    }
    public void btnvoluntario(View view) {
        startActivity(new Intent(Inicio.this,SolicitudConvocatoria.class));
    }
    public void btnnosotros(View view) {
        startActivity(new Intent(Inicio.this, CrearEventos.class));
    }
    public void btnmanten(View view) {
    }
}