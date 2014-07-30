package com.otacm.thefieldpty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.sample.R;

public class Splashctivity extends Activity {
	private static final int SEGUNDOS = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_splashctivity);

		Handler handler = new Handler();
		handler.postDelayed(initApp(), SEGUNDOS);

	}

	private Runnable initApp() {
		Runnable res = new Runnable() {
			public void run() {
				Intent intent = new Intent(Splashctivity.this, TabActivity.class);
				startActivity(intent);
				finish();
			}
		};
		return res;
	}
}