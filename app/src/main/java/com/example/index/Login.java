package com.example.index;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private EditText xgmail,xpass;
    private FirebaseAuth auth;
    private DatabaseReference dbref;
    private String activo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        xgmail = (EditText) findViewById(R.id.logingmail);
        xpass = (EditText) findViewById(R.id.loginpass);

        auth = FirebaseAuth.getInstance();
        dbref = FirebaseDatabase.getInstance().getReference();
    }

    public void StartSeccion (View view){

        String gmail = xgmail.getText().toString();
        String pass = xpass.getText().toString();

        if(!gmail.isEmpty() && !pass.isEmpty()){
            SignIn(gmail,pass);
            }
            else
                msm("Los campos se encuentran vacios");
        }

    public void msm (String m){
        Toast.makeText(this,m,Toast.LENGTH_LONG).show();}

    /*Boton de registrar usuario
    public void PresentarConvocatoria(View view){
        Intent next = new Intent(this,SolicitudConvocatoria.class);
        startActivity(next);
    }*/
    //Iniciar seccion
    public void SignIn(String g,String p){
        auth.signInWithEmailAndPassword(g,p)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Condiciones();
                        } else {
                            msm("Los datos ingresados no son los correctos");
                        }
                    }
                });
    }
    //Identificar si la cuenta esta activada
    public void Condiciones(){

        String ID = auth.getCurrentUser().getUid();
        dbref.child("Usuarios").child(ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                if(snapshot.exists()){
                    activo = snapshot.child("active").getValue().toString();
                    if(activo.equals("true")){
                        startActivity(new Intent(Login.this,MenuUser.class));
                    }
                    else{
                        msm("Su convocatoria aun no ha sido confirmada");
                        auth.signOut();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
