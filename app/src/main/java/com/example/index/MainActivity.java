package com.example.index;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private EditText xgmail,xpass;
    private FirebaseAuth auth;
    private DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xgmail = (EditText) findViewById(R.id.logingmail);
        xpass = (EditText) findViewById(R.id.loginpass);

        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();
        dbref = FirebaseDatabase.getInstance().getReference();

        FirebaseApp.initializeApp(this);
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

    public void PresentarConvocatoria(View view){
        Intent next = new Intent(this,SolicitudConvocatoria.class);
        startActivity(next);
    }
    public void msm (String m){
        Toast.makeText(this,m,Toast.LENGTH_LONG).show();}

    public void SignIn(String g,String p){
        auth.signInWithEmailAndPassword(g,p)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(MainActivity.this,PerfilUser.class));
                        } else {
                            msm("Los datos ingresados no son los correctos");
                        }
                    }
                });
    }

    @Override   //Mantener seccion abierta
    protected void onStart(){
        super.onStart();
        if(auth.getCurrentUser()!=null)
            startActivity(new Intent(MainActivity.this,PerfilUser.class));
    }
}
