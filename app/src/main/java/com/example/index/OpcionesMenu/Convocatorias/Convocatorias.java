package com.example.index.OpcionesMenu.Convocatorias;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.index.Clases.AdaptadorUsuarios;
import com.example.index.Clases.Usuarios;
import com.example.index.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Convocatorias extends Fragment {

    private ConvocatoriasViewModel mViewModel;

    public static Convocatorias newInstance() {
        return new Convocatorias();
    }

    private RecyclerView rv;
    private DatabaseReference db;
    private List<Usuarios> elem;
    private AdaptadorUsuarios listAdapter;
    private Activity act;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_convocatorias, container, false);

        act = getActivity();

        rv = view.findViewById(R.id.ListConvocatorias); //Vinculo con la lista
        db = FirebaseDatabase.getInstance().getReference("Usuarios"); //De donde extraer
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        elem = new ArrayList<>();
        listAdapter = new AdaptadorUsuarios(elem,getContext());
        rv.setAdapter(listAdapter);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
                    Usuarios user = data.getValue(Usuarios.class);
                    if(user.isActive()==false)
                        elem.add(user);
                        CapturarId();
                }
                listAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return view;
    }

    public void CapturarId(){
        listAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuarios x = elem.get(rv.getChildAdapterPosition(v));
                Intent box = new Intent(act,DescripConvocatoria.class);
                box.putExtra("id",x.getId());
                box.putExtra("name",x.getName());
                box.putExtra("gmail",x.getGmail());
                box.putExtra("edad",x.getEdad());
                box.putExtra("coment",x.getPostulacion());
                startActivity(box);
            }
        });
    }
}