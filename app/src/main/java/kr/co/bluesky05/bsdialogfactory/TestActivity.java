package kr.co.bluesky05.bsdialogfactory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import kr.co.bluesky05.dialog.BSDialogFactory;
import kr.co.bluesky05.dialog.OnBSClickListener;

/**
 *
 */
public class TestActivity extends AppCompatActivity implements OnBSClickListener {

	private void printLog(String message) {
		final String LOG_TAG = "BSDialogFactory";
		Log.i(LOG_TAG, message);
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		findViewById(R.id.btnProgressTestClick).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onTestProgressClick();
			}
		});
		findViewById(R.id.btnOneBtnTestClick).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onTestOneBtnClick();
			}
		});
		findViewById(R.id.btnTwoBtnTestClick).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onTestTwoBtnClick();
			}
		});
	}

	private void onTestProgressClick() {
		BSDialogFactory
				.newDialogBuilder(BSDialogFactory.Type.CircularProgress)
				.show(this);
	}

	private void onTestOneBtnClick() {
		BSDialogFactory
				.newDialogBuilder(BSDialogFactory.Type.OneBtn)
				.setRequestCode(98)
				.setMessage("activity click !")
				.show(this);
	}

	private void onTestTwoBtnClick() {
		BSDialogFactory
				.newDialogBuilder(BSDialogFactory.Type.TwoBtn)
				.setRequestCode(99)
				.setMessage("activity click !")
				.show(this);
	}

	@Override
	public void onBSClick(int requestCode, int type) {
		printLog("TestActivity.onBSClick() - requestCode : " + requestCode + ", type : " + type);
	}
}
