package com.example.index.OpcionesMenu.EventosAdministrador;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.index.Clases.ClassEventos;
import com.example.index.R;
import com.example.index.Splash.SplashEventoCreado;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Random;

public class CrearEventos extends AppCompatActivity {

    private ImageView foto;
    private EditText nombre,descripcion,ubicacion,fecha,hora;
    private String xnombre,xdescripcion,xubicacion,xfecha,xhora;
    private ImageButton btncalendar,btndia;
    private Button btnimagen,btncreareven;
    private ClassEventos event=null;
    private DatabaseReference eventoReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_eventos);

        eventoReference = FirebaseDatabase.getInstance().getReference();

        nombre = (EditText) findViewById(R.id.addeven_name);
        descripcion = (EditText) findViewById(R.id.addeven_desc);
        ubicacion = (EditText) findViewById(R.id.addeven_ubic);
        foto = (ImageView) findViewById(R.id.addevenimg);
        fecha = (EditText) findViewById(R.id.addeven_day);
        hora = (EditText) findViewById(R.id.addeven_hour);
        btncalendar = (ImageButton) findViewById(R.id.addbtnday);
        btndia = (ImageButton) findViewById(R.id.addbtnhour);
        btnimagen = (Button) findViewById(R.id.addbtnfoto);
        btncreareven = (Button) findViewById(R.id.addbtncrearevento);

    }

    public void onStart(){
        fecha.setEnabled(false);
        hora.setEnabled(false);
        super.onStart();

        //Capturar fecha
        btncalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int edia = c.get(Calendar.DAY_OF_MONTH);
                int emes = c.get(Calendar.MONTH);
                int eaño = c.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(CrearEventos.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        fecha.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                },edia,emes,eaño);
                datePickerDialog.show();
            }
        });

        //Captura hora
        btndia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int ehora = c.get(Calendar.HOUR);
                int eminutos = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(CrearEventos.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if(minute >=0 && minute <10){
                            String num = "0"+minute;
                            hora.setText(hourOfDay+":"+num);
                        }
                        else
                            hora.setText(hourOfDay+":"+minute);
                    }
                },ehora,eminutos,false);
                timePickerDialog.show();
            }
        });

        //Registrar evento
        btncreareven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                xnombre = nombre.getText().toString();
                xdescripcion = descripcion.getText().toString();
                xubicacion = ubicacion.getText().toString();
                xfecha = fecha.getText().toString();
                xhora = hora.getText().toString();

                if(!xnombre.isEmpty()&&!xdescripcion.isEmpty()&&!xubicacion.isEmpty()
                        &&!xfecha.isEmpty()&&!xhora.isEmpty()){

                    String xid = CodigoRandom();
                    event = new ClassEventos();
                    event.setCodex(xid);
                    event.setName(xnombre);
                    event.setDescripcion(xdescripcion);
                    event.setUbicacion(xubicacion);
                    event.setFecha(xfecha);
                    event.setHora(xhora);

                    eventoReference.child("Eventos").child(xid).setValue(event);
                    startActivity(new Intent(CrearEventos.this, SplashEventoCreado.class));
                    finish();
                }
                else
                    msm("Complete todos los campos");
            }
        });
    }
    public void msm (String m){
        Toast.makeText(this,m,Toast.LENGTH_LONG).show();}

    public String CodigoRandom(){
        //Intento de generar un codigo aleatorio
        Random box = new Random();
        int ranbox = box.nextInt(101);
        String code = xnombre.substring(0,2)+(""+ranbox)+xhora.substring(0,2)
                +xubicacion.substring(0,2)+xdescripcion.substring(0,2) +(""+ranbox)+xfecha.substring(0,1);
        return code;
    }
}