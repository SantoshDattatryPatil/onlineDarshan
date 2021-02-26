package com.e.onlinedarshan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class DarshanActivity extends AppCompatActivity {
    WebView abm;
    String GodView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_darshan);
       /* ProgressDialog d = ProgressDialog.show(DarshanActivity.this, "",
                "Loading. Please wait...", true);*/
        ConnectivityManager connectivityManager= (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo==null || !networkInfo.isConnected() || !networkInfo.isAvailable())
        {
            Dialog dialog=new Dialog(this);
            dialog.setContentView(R.layout.internetconnection);
            dialog.setCancelable(false);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().getAttributes().windowAnimations= android.R.style.Animation_Dialog;

            Button button=dialog.findViewById(R.id.btn);
            button.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              recreate();                              }
                                      }
            );

        }
      else{
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        abm=findViewById(R.id.darshan);
        WebSettings webSettings=abm.getSettings();
        webSettings.setJavaScriptEnabled(true);

        GodView=getIntent().getStringExtra("title");

                abm.loadUrl(GodView);
    }}
}