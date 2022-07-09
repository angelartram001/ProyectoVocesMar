package com.example.index;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.index.databinding.ActivityMenuUserBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuUser extends AppCompatActivity {

    private TextView campname,gmail;
    private View hnav;
    private FirebaseAuth auth;
    private DatabaseReference dbref;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMenuUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuUserBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMenuUser.toolbar);

        auth = FirebaseAuth.getInstance();
        dbref = FirebaseDatabase.getInstance().getReference();

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        ModificarHeader(navigationView);
        DataPerfil(navigationView);

        //Quitar la sombra por defecto
        navigationView.setItemIconTintList(null);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.perfil,R.id.eventosAdministrador,R.id.eventosVoluntario,R.id.convocatorias,R.id.allUser)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu_user);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu_user);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //Modificar el texto del Header
    public void ModificarHeader(NavigationView x){

        hnav = x.getHeaderView(0);
        campname=(TextView)hnav.findViewById(R.id.navname);
        gmail=(TextView)hnav.findViewById(R.id.navcorreo);
    }

    public void DataPerfil(NavigationView x){

        String ID = auth.getCurrentUser().getUid();
        dbref.child("Usuarios").child(ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                if(snapshot.exists()){
                    String fname = snapshot.child("name").getValue().toString();
                    String fgmail = snapshot.child("gmail").getValue().toString();
                    String fpermisos = snapshot.child("permisos").getValue().toString();
                    campname.setText(fname);
                    gmail.setText(fgmail);
                    if(fpermisos.equals("false")){
                        OpcionDenegada(x);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    //Ocultar opciones segun el tipo de usuario
    public void OpcionDenegada(NavigationView x){

        Menu menu = x.getMenu();
        MenuItem vis1 = menu.findItem(R.id.convocatorias);
        MenuItem vis2 = menu.findItem(R.id.eventosAdministrador);
        MenuItem vis3 = menu.findItem(R.id.allUser);
        vis1.setVisible(false);
        vis2.setVisible(false);
        vis3.setVisible(false);
    }
}