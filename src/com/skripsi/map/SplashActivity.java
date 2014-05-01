package com.skripsi.map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;


public class SplashActivity extends Activity{
	Boolean isInternetPresent = false;
	CheckConnection connection;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		connection = new CheckConnection(getApplicationContext());
		isInternetPresent = connection.isConnectingToInternet();
		
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (isInternetPresent) {
					Intent intent = new Intent(SplashActivity.this,HomeActivity.class);
					startActivity(intent);
					finish();
				
			}
		}, 3000);
		
	}	
	
}