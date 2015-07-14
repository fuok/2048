package com.example.tofe;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private GameBoard gb;
	private final int GAME_START = 0;
	private final int PULL_UP = 1;
	private final int PULL_DOWN = 2;
	private final int PULL_LEFT = 3;
	private final int PULL_RIGHT = 4;
	private final int GAME_OVER = 9;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		iniView();
	}

	@Override
	protected void onStart() {
		super.onStart();
		SharedPreferences preferences = getSharedPreferences("2048", MODE_PRIVATE);
		boolean isFirst = preferences.getBoolean("isFirst", true);
		if (isFirst) {
			Editor editor = preferences.edit();
			editor.putBoolean("isFirst", false);
			editor.commit();
			mHandler.sendEmptyMessage(GAME_START);
		} else {
			gb = new GameBoard();
			int[][] dimensionArray = gb.getDArray();
			for (int i = 0, n = 0; i < dimensionArray.length; i++) {
				for (int j = 0; j < dimensionArray[i].length; j++) {
					int num = preferences.getInt(String.valueOf(n), -1);
					dimensionArray[i][j] = num;
					n++;
				}
			}
			refreshGrid();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		SharedPreferences preferences = getSharedPreferences("2048", MODE_PRIVATE);
		Editor editor = preferences.edit();
		int[][] dimensionArray = gb.getDArray();
		for (int i = 0, n = 0; i < dimensionArray.length; i++) {
			for (int j = 0; j < dimensionArray[i].length; j++) {
				editor.putInt(String.valueOf(n), dimensionArray[i][j]);
				n++;
			}
		}
		editor.commit();
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
			mHandler.sendEmptyMessage(GAME_START);
			break;
		case R.id.action_settings:

			break;
		default:
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// 设定在某一方向上滑动距离大于100单位、速度大于100单位，视为手势方向成立
	private final int FLING_MIN_DISTANCE = 100;
	private final int FLING_MIN_VELOCITY = 100;
	// 当每次滑动后添加进来新数字时，保存一下坐标
	private int[] newPosion = new int[] { -1, -1 };

	private RelativeLayout[] items;

	{
		RelativeLayout item_layout_1 = null, item_layout_2 = null, item_layout_3 = null, item_layout_4 = null, item_layout_5 = null, item_layout_6 = null, item_layout_7 = null, item_layout_8 = null, item_layout_9 = null, item_layout_10 = null, item_layout_11 = null, item_layout_12 = null, item_layout_13 = null, item_layout_14 = null, item_layout_15 = null, item_layout_16 = null;
		items = new RelativeLayout[] { item_layout_1, item_layout_2, item_layout_3, item_layout_4, item_layout_5, item_layout_6, item_layout_7, item_layout_8, item_layout_9, item_layout_10,
				item_layout_11, item_layout_12, item_layout_13, item_layout_14, item_layout_15, item_layout_16 };
	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case GAME_START:
				gb = new GameBoard();
				gb.randomAdd(2);
				// gb.toString();
				break;
			case PULL_UP:
				if (gb.pullUp()) {
					newPosion = gb.randomAdd();
					// gb.toString();
				}
				break;
			case PULL_DOWN:
				if (gb.pullDown()) {
					newPosion = gb.randomAdd();
					// gb.toString();
				}
				break;
			case PULL_LEFT:
				if (gb.pullLeft()) {
					newPosion = gb.randomAdd();
					// gb.toString();
				}
				break;
			case PULL_RIGHT:
				if (gb.pullRight()) {
					newPosion = gb.randomAdd();
					// gb.toString();
				}
				break;
			case GAME_OVER:// 游戏结束，但这个目前还无法判断，TODO
				Toast.makeText(MainActivity.this, "游戏结束", Toast.LENGTH_SHORT).show();
				return;
			}
			refreshGrid();
		}

	};

	private void iniView() {
		// 获取每个子item宽度
		int COL_NUMBER = 4;// 4列
		Display currDisplay = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		currDisplay.getSize(size);
		int COL_WIDTH = (size.x / COL_NUMBER) - 12;// 两侧空出的部分
		// //
		GridLayout grid_container = (GridLayout) findViewById(R.id.grid_container);
		grid_container.setColumnCount(COL_NUMBER);
		grid_container.setRowCount(COL_NUMBER);

		for (int i = 0; i < items.length; i++) {// 4*4
			GridLayout.Spec rowSpec = GridLayout.spec(i / COL_NUMBER);
			GridLayout.Spec columnSpec = GridLayout.spec(i % COL_NUMBER);
			GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);

			params.setGravity(Gravity.CENTER);
			params.height = COL_WIDTH;// LayoutParams.WRAP_CONTENT;
			params.width = COL_WIDTH;

			items[i] = (RelativeLayout) getLayoutInflater().inflate(R.layout.item_grid, null);
			items[i].setId(10000 + i);
			grid_container.addView(items[i], params);
		}

		// 定义手势动作
		grid_container.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return mGestureDetector.onTouchEvent(event);
			}
		});
		grid_container.setFocusable(true);
		grid_container.setClickable(true);
		grid_container.setLongClickable(true);

	}

	@SuppressWarnings("deprecation")
	GestureDetector mGestureDetector = new GestureDetector(new OnGestureListener() {

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {

		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {

		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			// Log.w("liuy", "X轴加速度：" + velocityX + ",Y轴加速度：" + velocityY);
			if (e1.getY() - e2.getY() > FLING_MIN_DISTANCE && Math.abs(velocityY) > Math.abs(velocityX) && Math.abs(velocityY) > FLING_MIN_VELOCITY) {
				// Log.w("liuy", "向上");
				mHandler.sendEmptyMessage(PULL_UP);
			} else if (e2.getY() - e1.getY() > FLING_MIN_DISTANCE && Math.abs(velocityY) > Math.abs(velocityX) && Math.abs(velocityY) > FLING_MIN_VELOCITY) {
				// Log.w("liuy", "向下");
				mHandler.sendEmptyMessage(PULL_DOWN);
			} else if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > Math.abs(velocityY) && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
				// Log.w("liuy", "向左");
				mHandler.sendEmptyMessage(PULL_LEFT);
			} else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > Math.abs(velocityY) && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
				// Log.w("liuy", "向右");
				mHandler.sendEmptyMessage(PULL_RIGHT);
			}
			return false;
		}

		@Override
		public boolean onDown(MotionEvent e) {
			return false;
		}
	});

	private void refreshGrid() {
		if (gb != null) {
			int[][] dimensionArray = gb.getDArray();
			for (int i = 0, n = 0; i < dimensionArray.length; i++) {
				for (int j = 0; j < dimensionArray[i].length; j++) {
					RelativeLayout item_layout = items[n];// 从前面保存的View[]直接获取，不必再取
					n++;
					TextView tv_item = (TextView) item_layout.findViewById(R.id.tv_item);
					if (dimensionArray[i][j] == -1) {
						tv_item.setVisibility(View.INVISIBLE);
					} else {
						tv_item.setText(String.valueOf(dimensionArray[i][j]));
						tv_item.setBackgroundResource(Utils.getColor(dimensionArray[i][j]));
						if (newPosion[0] == i && newPosion[1] == j) {
							newPosion[0] = -1;
							newPosion[1] = -1;// 及时清空
							Animation animation = AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_in);
							tv_item.startAnimation(animation);
						}
						tv_item.setVisibility(View.VISIBLE);
					}
				}
			}
			// 显示分数
			TextView tv_sccore = (TextView) findViewById(R.id.tv_sccore);
			tv_sccore.setText(gb.getSccore());
		}
	}

}
