package com.example.index;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.index.Clases.Usuarios;
import com.example.index.Splash.SplashRegister;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SolicitudConvocatoria extends AppCompatActivity {

    EditText rname,rgmail,rpass,rcpass,redad,rpost;
    Usuarios user = null;

    private FirebaseAuth auth;
    private DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_convocatoria);

        rname = (EditText) findViewById(R.id.regname);
        rgmail = (EditText) findViewById(R.id.reggmail);
        rpass = (EditText) findViewById(R.id.regpass);
        rcpass = (EditText) findViewById(R.id.regcpass);
        redad = (EditText) findViewById(R.id.regedad);
        rpost = (EditText) findViewById(R.id.regpostulacion);

        auth = FirebaseAuth.getInstance();
        dbReference = FirebaseDatabase.getInstance().getReference();

    }

    public void RegistrarConvocatoria(View view){

        ValidarInfo();
    }

    public void AgregarUsuario(String xnom,String xgma,String xpas,int xedad,String xpos){

        auth.createUserWithEmailAndPassword(xgma,xpas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    user = new Usuarios();
                    user.setName(xnom);
                    user.setGmail(xgma);
                    user.setPassword(xpas);
                    user.setEdad(xedad);
                    user.setPostulacion(xpos);
                    user.setActive(false);   //True para activar su cuenta
                    user.setPermisos(false); //True para ser administrador
                    String id = auth.getCurrentUser().getUid();
                    user.setId(id);
                    dbReference.child("Usuarios").child(user.getId()).setValue(user);

                }
            }
        });
    }

    public void ValidarInfo(){

        String name = rname.getText().toString();
        String gmail = rgmail.getText().toString();
        String pass = rpass.getText().toString();
        String cpass = rcpass.getText().toString();
        String campedad = redad.getText().toString();
        String postulation = rpost.getText().toString();

        if(!name.isEmpty() && !postulation.isEmpty() && !gmail.isEmpty()
                && !campedad.isEmpty() && !pass.isEmpty() && !cpass.isEmpty()){

            try{
                if(pass.length()>=6){
                    if(pass.equals(cpass)){
                        int edad = Integer.parseInt(campedad);
                        AgregarUsuario(name,gmail,pass,edad,postulation);
                        startActivity(new Intent(this,SplashRegister.class));
                        finish();
                    }
                    else{
                        msm("Las contraseñas no coinciden");
                    }
                }
                else
                    msm("La contraseña debe tener minimo 6 caracteres");
            }
            catch (Exception e){
                msm(""+e);}
        }
        else
            msm("Complete todos los campos");
    }


    public void msm (String m){
        Toast.makeText(this,m,Toast.LENGTH_LONG).show();}
}