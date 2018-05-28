package com.example.guillermo.a58_proveedor_contenidos_call_log;

import android.database.Cursor;
import android.provider.CallLog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView listcall;
    List<ItemLog> calllog=new ArrayList<ItemLog>();
    TextView textView;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //textView = (TextView) findViewById(R.id.txtcalllog);
        listcall = findViewById(R.id.listacalllog);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("call-backup");


        Cursor mCursor=managedQuery(CallLog.Calls.CONTENT_URI,null,null,null,null);
        int number=mCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int date=mCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration=mCursor.getColumnIndex(CallLog.Calls.DURATION);
        int type=mCursor.getColumnIndex(CallLog.Calls.TYPE);
        StringBuilder sb = new StringBuilder();
        String cad="";
        while(mCursor.moveToNext()){
            String phnumber=mCursor.getString(number);
            String calldate=mCursor.getString(date);
            String callduration=mCursor.getString(duration);
            String calltype=mCursor.getString(type);
            String callTypeStr="";
            switch (Integer.parseInt(calltype)){
                case CallLog.Calls.OUTGOING_TYPE:
                    callTypeStr="Llamada";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    callTypeStr="Llamada Recibida";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    callTypeStr="Llamada Perdida";
                    break;
            }
           /* sb.append(" Numero telefonico: "+phnumber);
            sb.append(System.getProperty("line.separator"));
            sb.append(" Duracion de llamada: "+callduration);
            sb.append(System.getProperty("line.separator"));
            sb.append(" Tipo de llamada: "+callTypeStr);
            sb.append(System.getProperty("line.separator"));
            sb.append(" Date de llamada: "+calldate);
            sb.append(System.getProperty("line.separator"));
            sb.append(" ----------------------- ");
            sb.append(System.getProperty("line.separator"));*/

           Date fec = new Date(Long.parseLong(calldate));
            cad="Numero: "+phnumber+"\n"+"Duracion(s): "+callduration+"\n"+"Tipo: "+callTypeStr+"\n"+"Fecha: "+fec.toString()+"\n";

            calllog.add(new ItemLog(cad));
        }

        //textView.setText(sb.toString());


        Adapter adapter=new Adapter(calllog);
        listcall.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        listcall.setLayoutManager(layoutManager);

        hilos();
        Toast.makeText(getApplicationContext(),"Se esta respaldando la informacion..",Toast.LENGTH_LONG).show();


    }

    private void UnSegundo() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void hilos() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                UnSegundo();
                myRef.setValue(calllog);
            }
        }).start();
    }
}
