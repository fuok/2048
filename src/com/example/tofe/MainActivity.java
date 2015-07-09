package com.example.tofe;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
		gb.randomAdd(2);
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
		// if (id == R.id.action_settings) {
		// return true;
		// }
		switch (id) {
		case R.id.action_restart:
			Toast.makeText(getApplicationContext(), "haha", Toast.LENGTH_SHORT).show();
			gb = new GameBoard();
			gb.randomAdd(2);
			gb.toString();
			break;
		case R.id.action_settings:

			break;
		default:
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements OnClickListener {

		// TextView tv1;
		Button btn1, btn2, btn3, btn4, btn5;

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
			btn5 = (Button) v.findViewById(R.id.btn5);
			btn5.setOnClickListener(this);

			// //////////////
			// 获取每个子item宽度
			int COL_NUMBER = 4;// 4列
			Display currDisplay = getActivity().getWindowManager().getDefaultDisplay();
			Point size = new Point();
			currDisplay.getSize(size);
			int COL_WIDTH = (size.x / COL_NUMBER) - 12;// 两侧
			// //
			GridLayout grid_container = (GridLayout) v.findViewById(R.id.grid_container);
			grid_container.setColumnCount(COL_NUMBER);
			grid_container.setRowCount(COL_NUMBER);

			for (int i = 0; i < 16; i++) {// 4*4
				GridLayout.Spec rowSpec = GridLayout.spec(i / COL_NUMBER);// 呵呵3
				GridLayout.Spec columnSpec = GridLayout.spec(i % COL_NUMBER);
				GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);

				params.setGravity(Gravity.CENTER);
				params.height = COL_WIDTH;// LayoutParams.WRAP_CONTENT;
				params.width = COL_WIDTH;

				RelativeLayout item_layout = (RelativeLayout) getActivity().getLayoutInflater().inflate(R.layout.item_grid, null);
				grid_container.addView(item_layout, params);
			}
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn1:
				if (gb.pullUp()) {
					gb.randomAdd(1);
					gb.toString();
				}
				break;
			case R.id.btn2:
				if (gb.pullDown()) {
					gb.randomAdd(1);
					gb.toString();
				}
				break;
			case R.id.btn3:
				if (gb.pullLeft()) {
					gb.randomAdd(1);
					gb.toString();
				}
				break;
			case R.id.btn4:
				if (gb.pullRight()) {
					gb.randomAdd(1);
					gb.toString();
				}
				break;
			case R.id.btn5:
				gb.randomAdd(1);
				gb.toString();
				break;
			}

		}

	}

}
