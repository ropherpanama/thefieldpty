package com.otacm.thefieldpty;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class DetalleFavoritoActivity extends ActionBarActivity {

	private int favId = 0;
	private TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle_favorito);
		favId = getIntent().getIntExtra("favId", -1);
		textView = (TextView) findViewById(R.id.texViewDetalleFav);
		textView.setText("Id de equipo encontrado " + favId); 
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
