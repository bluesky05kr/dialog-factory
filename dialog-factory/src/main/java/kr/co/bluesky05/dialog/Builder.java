package kr.co.bluesky05.dialog;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.util.Log;


/**
 *
 * @param <T>
 * @param <T2>
 */
public abstract class Builder<T extends Builder, T2 extends DialogFragment> {
	protected final String TAG = "bluesky05_DialogManager";

	public static final int EMPTY = -1;
	/**
	 * true : dialog 를 취소 할 수 있다
	 * false : dialog 를 취소 할 수 없다
	 */
	boolean cancelable;

	/**
	 * 결과 시에 전달 될 code
	 *
	 * @see OnBSClickListener#onBSClick(int, int)
	 */
	int requestCode;

	int layoutResId;

	/**
	 * diloag message
	 */
	String message;

	public Builder() {
		this.cancelable = true;

		this.requestCode = EMPTY;
		this.layoutResId = EMPTY;
	}

	abstract public T setCancelable(boolean cancelable);

	abstract public T setLayoutResId(int layoutResId);

	abstract public T setMessage(String message);



	/**
	 * DialogFragment 를 생성한다
	 * @return
	 */
	abstract public T2 create();

	/**
	 *
	 * @param activity DialogFragment 를 생성 후 화면에 표시한다
	 * @return
	 */
	public T2 show(Activity activity) {
		T2 dialogFragment = create();
		try {
			dialogFragment.show(activity.getFragmentManager(), TAG);
		} catch (Exception ex) {
			Log.e(BSDialogFactory.LOG_NAME, ex.toString(), ex);
		}
		return dialogFragment;
	}

	/**
	 *
	 * @param fragment DialogFragment 를 생성 후 화면에 표시한다
	 * @return
	 */
	public T2 show(Fragment fragment) {
		T2 dialogFragment = create();
		try {
			dialogFragment.setTargetFragment(fragment, this.requestCode);
			dialogFragment.show(fragment.getFragmentManager(), TAG);
		} catch (Exception ex) {
			Log.e(BSDialogFactory.LOG_NAME, ex.toString(), ex);
		}
		return dialogFragment;
	}
}
