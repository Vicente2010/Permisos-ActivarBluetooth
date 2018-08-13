package com.example.o_betanzos.activarbluetooth;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int CODIGO_SOLICITAR_PERMISO = 1;
    private static final int CODIGO_SOLICITUD_HABILITAR_BLUETOOTH = 0;
    private Context context;
    private Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        activity = this;
    }

    public void habilitarBluetooth(View v){
        solicitarPermiso();

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null){
            Toast.makeText(MainActivity.this, "Tú dispositivo no tiene Bluetooth",Toast.LENGTH_SHORT).show();
        }else{
        
            if (!mBluetoothAdapter.isEnabled()){
                Intent habilitarBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(habilitarBluetoothIntent,CODIGO_SOLICITUD_HABILITAR_BLUETOOTH);
            }
        }
    }

    public boolean checarStatusPermiso(){
        int resultado = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH);
        if (resultado== PackageManager.PERMISSION_GRANTED){
            return true;
        }else {
            return false;
        }
    }

    public void solicitarPermiso(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.BLUETOOTH)){
            Toast.makeText(MainActivity.this, "El permiso ya fue otorgado, si deseas desactivarlo puedes ir a los ajustes de la applicación",Toast.LENGTH_SHORT).show();
        } else{
            ActivityCompat.requestPermissions(activity,new String[] {Manifest.permission.BLUETOOTH},CODIGO_SOLICITAR_PERMISO);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case CODIGO_SOLICITAR_PERMISO:
                if (checarStatusPermiso()){
                    Toast.makeText(MainActivity.this,"Ya está activo el permiso para Bluetooth", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this,"No está activo el permiso para Bluetooth", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
