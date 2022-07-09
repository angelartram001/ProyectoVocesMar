package com.example.index.OpcionesMenu.Perfil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.index.Login;
import com.example.index.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Perfil extends Fragment {

    private EditText opname,opgmail,oppass,opcon;
    private FirebaseAuth auth;
    private DatabaseReference dbref;
    private FirebaseUser firebaseUser;
    private AuthCredential credential;

    private Activity cont;
    private Button btn1,btn2,btn3;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        cont = getActivity();
        auth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        dbref = FirebaseDatabase.getInstance().getReference();
        opname = (EditText) view.findViewById(R.id.frnombre);
        opgmail = (EditText) view.findViewById(R.id.frgmail);
        oppass = (EditText) view.findViewById(R.id.frpass);
        opcon = (EditText) view.findViewById(R.id.frmot);
        btn2 = (Button) view.findViewById(R.id.fbtnsave);
        opgmail.setEnabled(false);
        ConfigDefault(view);
        DataPerfil();
        return view;
    }

    public void onStart(){
        super.onStart();

        //Editar Perfil
        btn1 = (Button) cont.findViewById(R.id.fbtnedit);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opname.setEnabled(true);
                oppass.setEnabled(true);
                opcon.setEnabled(true);
                btn2.setVisibility(View.VISIBLE);
                opname.setBackgroundResource(R.color.sombra);
                oppass.setBackgroundResource(R.color.sombra);
                opcon.setBackgroundResource(R.color.sombra);
            }
        });
        //Guardar cambios
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String boxname = opname.getText().toString();
                String boxpass = oppass.getText().toString();
                String boxmot = opcon.getText().toString();

                String ID = auth.getCurrentUser().getUid();
                Map<String,Object> user = new HashMap<>();
                user.put("name",boxname);
                user.put("password",boxpass);
                user.put("postulacion",boxmot);

                if(!boxname.isEmpty() && !boxpass.isEmpty()){
                    if(boxpass.length()>=6){
                        credential = EmailAuthProvider.getCredential(opgmail.getText().toString(),oppass.getText().toString());
                        firebaseUser.reauthenticate(credential);
                        firebaseUser.updatePassword(boxpass);
                        dbref.child("Usuarios").child(ID).updateChildren(user);
                        ConfigDefault(v);
                        msm("Los cambios han sido registrados");
                    }
                    else msm("La contraseña debe tener minimo 6 caracteres");
                }
                else
                    msm("Los datos ingresados no pueden estar vacios");
            }
        });
        //Cerrar seccion Perfil Fragmento
        btn3 =  (Button) cont.findViewById(R.id.fbtncerrar);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(cont, Login.class));
                cont.finish();
            }
        });
    }

    public void DataPerfil(){

        String ID = auth.getCurrentUser().getUid();
        dbref.child("Usuarios").child(ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                if(snapshot.exists()){
                    String name = snapshot.child("name").getValue().toString();
                    String gmail = snapshot.child("gmail").getValue().toString();
                    String pass = snapshot.child("password").getValue().toString();
                    String motivo = snapshot.child("postulacion").getValue().toString();

                    opname.setText(name);
                    opgmail.setText(gmail);
                    oppass.setText(pass);
                    opcon.setText(motivo);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void ConfigDefault(View v){

        opname.setEnabled(false);
        oppass.setEnabled(false);
        opcon.setEnabled(false);
        btn2.setVisibility(View.INVISIBLE);
        opname.setBackgroundResource(R.color.editperf);
        oppass.setBackgroundResource(R.color.editperf);
        opcon.setBackgroundResource(R.color.editperf);
    }

    public void msm (String m){
        Toast.makeText(getContext(),m,Toast.LENGTH_LONG).show();}
}
