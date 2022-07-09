package com.example.index.OpcionesMenu.EventosVoluntarios;

import androidx.lifecycle.ViewModelProvider;

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
import com.example.index.OpcionesMenu.EventosAdministrador.EditarEventos;
import com.example.index.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EventosVoluntario extends Fragment {

    private EventosVoluntarioViewModel mViewModel;

    public static EventosVoluntario newInstance() {
        return new EventosVoluntario();
    }

    private RecyclerView rv;
    private DatabaseReference db;
    private List<ClassEventos> elem;
    private AdaptadorEventosAdm listAdapter;
    private Activity act;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eventos_voluntario, container, false);
        act = getActivity();

        rv = view.findViewById(R.id.ListEventosVoluntarios); //Vinculo con la lista
        db = FirebaseDatabase.getInstance().getReference("Eventos"); //De donde extraer
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        elem = new ArrayList<>();
        listAdapter = new AdaptadorEventosAdm(elem, getContext()); //La funcion de adapter
        rv.setAdapter(listAdapter);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
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
    public void CapturarId(){
        listAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassEventos x = elem.get(rv.getChildAdapterPosition(v));
                Intent joinevento = new Intent(act, EventosUnirse.class);
                joinevento.putExtra("id",x.getCodex());
                joinevento.putExtra("titulo",x.getName());
                joinevento.putExtra("descripcion",x.getDescripcion());
                joinevento.putExtra("ubicacion",x.getUbicacion());
                joinevento.putExtra("fecha",x.getFecha());
                joinevento.putExtra("hora",x.getHora());
                startActivity(joinevento);
            }
        });
    }

}