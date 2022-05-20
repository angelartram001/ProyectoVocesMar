package com.example.index;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.index.Clases.Usuarios;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SolicitudConvocatoria extends AppCompatActivity {

    EditText rname,rlast,rgmail,rpass,rcpass;
    Usuarios user = null;

    private FirebaseAuth auth;
    private DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_convocatoria);

        rname = (EditText) findViewById(R.id.regname);
        rlast = (EditText) findViewById(R.id.reglast);
        rgmail = (EditText) findViewById(R.id.reggmail);
        rpass = (EditText) findViewById(R.id.regpass);
        rcpass = (EditText) findViewById(R.id.regcpass);

        auth = FirebaseAuth.getInstance();
        dbReference = FirebaseDatabase.getInstance().getReference();

    }

    public void RegistrarConvocatoria(View view){

        ValidarInfo();
    }

    public void AgregarUsuario(String xnom,String xape,String xgma,String xpas){

        auth.createUserWithEmailAndPassword(xgma,xpas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    user = new Usuarios();
                    user.setName(xnom);
                    user.setLastname(xape);
                    user.setGmail(xgma);
                    user.setPassword(xpas);
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
        String last = rlast.getText().toString();
        String gmail = rgmail.getText().toString();
        String pass = rpass.getText().toString();
        String cpass = rcpass.getText().toString();

        if(!name.isEmpty() && !last.isEmpty() && !gmail.isEmpty()
                && !pass.isEmpty() && !cpass.isEmpty()){

            try{
                if(pass.equals(cpass)){
                    AgregarUsuario(name,last,gmail,pass);
                    Intent next = new Intent(this,MainActivity.class);
                    startActivity(next);
                    msm("Su convocatoria a sido presentada exitosamente");
                }
                else{
                    msm("Las contraseñas no coinciden");
                }
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