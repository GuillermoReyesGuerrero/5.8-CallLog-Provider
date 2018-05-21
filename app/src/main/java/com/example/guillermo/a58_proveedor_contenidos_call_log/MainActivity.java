package com.example.guillermo.a58_proveedor_contenidos_call_log;

import android.database.Cursor;
import android.provider.CallLog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView listcall;
    List<ItemLog> calllog=new ArrayList<ItemLog>();
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //textView = (TextView) findViewById(R.id.txtcalllog);
        listcall = findViewById(R.id.listacalllog);



        Cursor mCursor=managedQuery(CallLog.Calls.CONTENT_URI,null,null,null,null);
        int number=mCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int date=mCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration=mCursor.getColumnIndex(CallLog.Calls.DURATION);
        int type=mCursor.getColumnIndex(CallLog.Calls.TYPE);
        StringBuilder sb = new StringBuilder();
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
            sb.append(" Numero telefonico: "+phnumber);
            sb.append(System.getProperty("line.separator"));
            sb.append(" Duracion de llamada: "+callduration);
            sb.append(System.getProperty("line.separator"));
            sb.append(" Tipo de llamada: "+callTypeStr);
            sb.append(System.getProperty("line.separator"));
            sb.append(" Date de llamada: "+calldate);
            sb.append(System.getProperty("line.separator"));
            sb.append(" ----------------------- ");
            sb.append(System.getProperty("line.separator"));

            calllog.add(new ItemLog(sb.toString()));
        }

        //textView.setText(sb.toString());


        Adapter adapter=new Adapter(calllog);
        listcall.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        listcall.setLayoutManager(layoutManager);

    }
}
