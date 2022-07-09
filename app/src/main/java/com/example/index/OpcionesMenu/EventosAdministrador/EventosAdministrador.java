package com.example.index.OpcionesMenu.EventosAdministrador;
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
import android.widget.Button;

import com.example.index.Clases.AdaptadorEventosAdm;
import com.example.index.Clases.ClassEventos;
import com.example.index.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EventosAdministrador extends Fragment {

    private EventosViewModel mViewModel;

    public static EventosAdministrador newInstance() {
        return new EventosAdministrador();
    }

    private RecyclerView rv;
    private DatabaseReference db;
    private List<ClassEventos> elem;
    private AdaptadorEventosAdm listAdapter;
    private Activity act;
    private Button btnagregar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eventos_administrador, container, false);

        btnagregar = (Button) view.findViewById(R.id.btnagregareventos);
        act = getActivity();

        rv = view.findViewById(R.id.ListEventosAdministrador); //Vinculo con la lista
        db = FirebaseDatabase.getInstance().getReference("Eventos"); //De donde extraer
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        elem = new ArrayList<>();
        listAdapter = new AdaptadorEventosAdm(elem,getContext());
        rv.setAdapter(listAdapter);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
                    ClassEventos eventos = data.getValue(ClassEventos.class);
                    elem.add(eventos);
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

    public void onStart(){
        super.onStart();
        btnagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(act,CrearEventos.class));
            }
        });
    }


    public void CapturarId(){
        listAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassEventos x = elem.get(rv.getChildAdapterPosition(v));
                Intent boxevento = new Intent(act, EditarEventos.class);
                boxevento.putExtra("idevento",x.getCodex());
                boxevento.putExtra("name",x.getName());
                boxevento.putExtra("descripcion",x.getDescripcion());
                boxevento.putExtra("ubicacion",x.getUbicacion());
                boxevento.putExtra("fecha",x.getFecha());
                boxevento.putExtra("hora",x.getHora());
                startActivity(boxevento);
            }
        });
    }
}