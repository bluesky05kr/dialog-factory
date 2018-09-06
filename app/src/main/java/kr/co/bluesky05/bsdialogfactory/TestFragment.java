package kr.co.bluesky05.bsdialogfactory;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kr.co.bluesky05.dialog.BSDialogFactory;
import kr.co.bluesky05.dialog.OnBSClickListener;

/**
 *
 */
public class TestFragment extends Fragment implements OnBSClickListener {

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

		TextView rootView = new TextView(getActivity());
		rootView.setLayoutParams(layoutParams);
		rootView.setBackgroundResource(android.R.color.darker_gray);
		rootView.setGravity(Gravity.CENTER);

		rootView.setText("Fragment Click !");
		rootView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				BSDialogFactory
						.newDialogBuilder(BSDialogFactory.Type.OneBtn)
						.setRequestCode(100)
						.setMessage("fragment click !")
						.show(TestFragment.this);
			}
		});

		return rootView;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Log.i(BSDialogFactory.LOG_NAME, "TestFragment.onActivityResult() " +
				"- resultCode : " + requestCode + ", resultCode : " + resultCode);
	}

	@Override
	public void onBSClick(int requestCode, int type) {
		Log.i(BSDialogFactory.LOG_NAME, "TestFragment.onBSClick() " +
				"- requestCode : " + requestCode + ", type : " + type);
	}
}
