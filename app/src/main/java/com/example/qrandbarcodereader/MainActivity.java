package com.example.qrandbarcodereader;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.datamatrix.encoder.SymbolShapeHint;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
Button btnscan;
public  String link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnscan=(Button)findViewById(R.id.btnscan);

        btnscan.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        scanCode();

    }

    private void scanCode(){
        IntentIntegrator integrator=new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning Code");
        integrator.initiateScan();


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent Data){
        final IntentResult result=IntentIntegrator.parseActivityResult(requestCode,resultCode,Data);
        if(result!=null){
            if(result.getContents()!=null) {
                AlertDialog.Builder builder=new AlertDialog.Builder(this,R.style.dia);
                builder.setMessage(result.getContents());
                 link=result.toString();









                builder.setTitle("Scanning Result");
                builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        scanCode();

                    }
                }).setNegativeButton("Go to Link", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try{
                            click(result.getContents());
                            finish();

                        }catch (Exception e) {


                        }
                    }
                });

                AlertDialog dialog=builder.create();
                dialog.show();


                


            }
            else{
                Toast.makeText(this, "No Results", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onActivityResult(requestCode,resultCode,Data);
        }

    }
    public void click(String url){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}