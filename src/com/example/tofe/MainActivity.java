package com.example.tofe;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
	private static GameBoard gb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}

		gb = new GameBoard();
		gb.toString();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements OnClickListener {

		// TextView tv1;
		Button btn1, btn2, btn3, btn4;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			iniView(rootView);
			// tv1 = (TextView) rootView.findViewById(R.id.tv1);
			// tv1.setOnTouchListener(new OnTouchListener() {
			//
			// @Override
			// public boolean onTouch(View v, MotionEvent event) {
			// Log.w("liuy", event.getAction() + "");
			// return true;
			// }
			// });
			return rootView;
		}

		private void iniView(final View v) {
			btn1 = (Button) v.findViewById(R.id.btn1);
			btn1.setOnClickListener(this);
			btn2 = (Button) v.findViewById(R.id.btn2);
			btn2.setOnClickListener(this);
			btn3 = (Button) v.findViewById(R.id.btn3);
			btn3.setOnClickListener(this);
			btn4 = (Button) v.findViewById(R.id.btn4);
			btn4.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn1:
				gb.pullUp();
				gb.toString();
				break;
			case R.id.btn2:
				gb.pullDown();
				gb.toString();
				break;
			case R.id.btn3:
				gb.pullLeft();
				gb.toString();
				break;
			case R.id.btn4:
				gb.pullRight();
				gb.toString();
				break;
			}

		}

	}

}
