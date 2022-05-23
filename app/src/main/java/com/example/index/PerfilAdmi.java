package com.example.index;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PerfilAdmi extends AppCompatActivity {

    FirebaseAuth auth;
    DatabaseReference dbref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_admi);

        auth = FirebaseAuth.getInstance();
        dbref = FirebaseDatabase.getInstance().getReference();
    }

    public void DataPerfil(){

        String ID = auth.getCurrentUser().getUid();
        dbref.child("Usuarios").child(ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                if(snapshot.exists()){
                    String name = snapshot.child("name").getValue().toString();
                    String last = snapshot.child("lastname").getValue().toString();
                    String gmail = snapshot.child("gmail").getValue().toString();
                    String pass = snapshot.child("password").getValue().toString();
                    String activo = snapshot.child("active").getValue().toString();
                    String permisos = snapshot.child("permisos").getValue().toString();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void CerrarSeccionAd(View view){
        auth.signOut();
        startActivity(new Intent(PerfilAdmi.this,MainActivity.class));
        finish();
    }
}