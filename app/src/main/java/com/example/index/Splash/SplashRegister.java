package com.example.index.Splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.example.index.Login;
import com.example.index.MenuUser;
import com.example.index.R;
import com.example.index.SolicitudConvocatoria;
import com.google.firebase.auth.FirebaseAuth;

public class SplashRegister extends AppCompatActivity {

    private LottieAnimationView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_register);

        img =  findViewById(R.id.fondoanimado);
        animacion(img,R.raw.checkblue);

    }

    public void animacion(LottieAnimationView lotie , int ani){
        lotie.setAnimation(ani);
        lotie.setRepeatCount(1);
        lotie.playAnimation();

    }

    public void retro(View view){
        startActivity(new Intent(this, Login.class));

    }
}