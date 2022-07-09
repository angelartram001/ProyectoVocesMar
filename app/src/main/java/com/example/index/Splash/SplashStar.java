package com.example.index.Splash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.index.Inicio;
import com.example.index.Login;
import com.example.index.MenuUser;
import com.example.index.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashStar extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashstar);

        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();
        dbref = FirebaseDatabase.getInstance().getReference();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(auth.getCurrentUser()!=null){
                    Condiciones();
                }
                else{
                    startActivity(new Intent(SplashStar.this, Inicio.class));
                    finish();
                }
            }
        },3200);
    }

    public void Condiciones(){

        String ID = auth.getCurrentUser().getUid();
        dbref.child("Usuarios").child(ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                if(snapshot.exists()){
                    String activo = snapshot.child("active").getValue().toString();
                    if(activo.equals("true")){
                        startActivity(new Intent(SplashStar.this, MenuUser.class));
                        finish();
                    }
                    else{
                        startActivity(new Intent(SplashStar.this, Inicio.class));
                        finish();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}