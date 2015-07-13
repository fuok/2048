package com.example.tofe;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private static GameBoard gb;
	final static int GAME_START = 0;
	final static int PULL_UP = 1;
	final static int PULL_DOWN = 2;
	final static int PULL_LEFT = 3;
	final static int PULL_RIGHT = 4;
	public final static int GAME_OVER = 9;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}
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
			PlaceholderFragment.mHandler.sendEmptyMessage(GAME_START);
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
			PlaceholderFragment.refreshGrid();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		PlaceholderFragment.refreshGrid();
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
			PlaceholderFragment.mHandler.sendEmptyMessage(GAME_START);
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
	public static class PlaceholderFragment extends Fragment {
		// �趨��ĳһ�����ϻ����������100��λ���ٶȴ���100��λΪ���Ʒ������
		private final int FLING_MIN_DISTANCE = 100;
		private final int FLING_MIN_VELOCITY = 100;
		// ��ÿ�λ�������ӽ���������ʱ������һ������
		private static int[] newPosion = new int[] { -1, -1 };

		private static View rView;
		static RelativeLayout[] items;
		{
			RelativeLayout item_layout_1 = null, item_layout_2 = null, item_layout_3 = null, item_layout_4 = null, item_layout_5 = null, item_layout_6 = null, item_layout_7 = null, item_layout_8 = null, item_layout_9 = null, item_layout_10 = null, item_layout_11 = null, item_layout_12 = null, item_layout_13 = null, item_layout_14 = null, item_layout_15 = null, item_layout_16 = null;
			items = new RelativeLayout[] { item_layout_1, item_layout_2, item_layout_3, item_layout_4, item_layout_5, item_layout_6, item_layout_7, item_layout_8, item_layout_9, item_layout_10,
					item_layout_11, item_layout_12, item_layout_13, item_layout_14, item_layout_15, item_layout_16 };
		}

		private static PlaceholderFragment instance;

		public static PlaceholderFragment getInstance() {
			return instance;
		}

		public PlaceholderFragment() {
			instance = this;
		}

		@SuppressLint("HandlerLeak")
		public static Handler mHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case GAME_START:
					gb = new GameBoard();
					gb.randomAdd(2);
					gb.toString();
					break;
				case PULL_UP:
					if (gb.pullUp()) {
						newPosion = gb.randomAdd();
						gb.toString();
					}
					break;
				case PULL_DOWN:
					if (gb.pullDown()) {
						newPosion = gb.randomAdd();
						gb.toString();
					}
					break;
				case PULL_LEFT:
					if (gb.pullLeft()) {
						newPosion = gb.randomAdd();
						gb.toString();
					}
					break;
				case PULL_RIGHT:
					if (gb.pullRight()) {
						newPosion = gb.randomAdd();
						gb.toString();
					}
					break;
				case GAME_OVER:// ��Ϸ�����������Ŀǰ���޷��жϣ�TODO
					Toast.makeText(getInstance().getActivity(), "��Ϸ����", Toast.LENGTH_SHORT).show();
					return;
				}
				refreshGrid();
			}

		};

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			rView = rootView;
			iniView(rootView);
			return rootView;
		}

		private void iniView(final View v) {
			// //////////////
			// ��ȡÿ����item���
			int COL_NUMBER = 4;// 4��
			Display currDisplay = getActivity().getWindowManager().getDefaultDisplay();
			Point size = new Point();
			currDisplay.getSize(size);
			int COL_WIDTH = (size.x / COL_NUMBER) - 12;// ����
			// //
			GridLayout grid_container = (GridLayout) v.findViewById(R.id.grid_container);
			grid_container.setColumnCount(COL_NUMBER);
			grid_container.setRowCount(COL_NUMBER);

			for (int i = 0; i < items.length; i++) {// 4*4
				GridLayout.Spec rowSpec = GridLayout.spec(i / COL_NUMBER);// �Ǻ�3
				GridLayout.Spec columnSpec = GridLayout.spec(i % COL_NUMBER);
				GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);

				params.setGravity(Gravity.CENTER);
				params.height = COL_WIDTH;// LayoutParams.WRAP_CONTENT;
				params.width = COL_WIDTH;

				items[i] = (RelativeLayout) getActivity().getLayoutInflater().inflate(R.layout.item_grid, null);
				items[i].setId(10000 + i);
				grid_container.addView(items[i], params);
			}

			// �������ƶ���
			v.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					return mGestureDetector.onTouchEvent(event);
				}
			});
			v.setFocusable(true);
			v.setClickable(true);
			v.setLongClickable(true);

		}

		GestureDetector mGestureDetector = new GestureDetector(getActivity(), new OnGestureListener() {

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
				// Log.w("liuy", "X����ٶȣ�" + velocityX + ",Y����ٶȣ�" + velocityY);
				if (e1.getY() - e2.getY() > FLING_MIN_DISTANCE && Math.abs(velocityY) > Math.abs(velocityX) && Math.abs(velocityY) > FLING_MIN_VELOCITY) {
					// Log.w("liuy", "����");
					mHandler.sendEmptyMessage(PULL_UP);
				} else if (e2.getY() - e1.getY() > FLING_MIN_DISTANCE && Math.abs(velocityY) > Math.abs(velocityX) && Math.abs(velocityY) > FLING_MIN_VELOCITY) {
					// Log.w("liuy", "����");
					mHandler.sendEmptyMessage(PULL_DOWN);
				} else if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > Math.abs(velocityY) && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
					// Log.w("liuy", "����");
					mHandler.sendEmptyMessage(PULL_LEFT);
				} else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > Math.abs(velocityY) && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
					// Log.w("liuy", "����");
					mHandler.sendEmptyMessage(PULL_RIGHT);
				}
				return false;
			}

			@Override
			public boolean onDown(MotionEvent e) {
				return false;
			}
		}, mHandler);

		private static void refreshGrid() {
			if (rView != null && gb != null) {
				int[][] dimensionArray = gb.getDArray();
				for (int i = 0, n = 0; i < dimensionArray.length; i++) {
					for (int j = 0; j < dimensionArray[i].length; j++) {
						RelativeLayout item_layout = items[n];// ��ǰ�汣���View[]ֱ�ӻ�ȡ��������ȡ
						n++;
						TextView tv_item = (TextView) item_layout.findViewById(R.id.tv_item);
						tv_item.setText(String.valueOf(dimensionArray[i][j]));
						tv_item.setBackgroundResource(Utils.getColor(dimensionArray[i][j]));
						if (dimensionArray[i][j] == -1) {
							tv_item.setVisibility(View.INVISIBLE);
						} else {
							if (newPosion[0] == i && newPosion[1] == j) {
								newPosion[0] = -1;
								newPosion[1] = -1;// ��ʱ���
								Animation animation = AnimationUtils.loadAnimation(getInstance().getActivity(), android.R.anim.fade_in);
								tv_item.startAnimation(animation);
							}
							tv_item.setVisibility(View.VISIBLE);
						}
					}
				}
				// ��ʾ����
				TextView tv_sccore = (TextView) rView.findViewById(R.id.tv_sccore);
				tv_sccore.setText(gb.getSccore());
			}
		}

	}

}
