package kr.co.bluesky05.dialog;

/**
 * {@link OneBtnBSDialog}, {@link TwoBtnBSDialog} 의 버튼을 클릭 시 호출 될 Listener
 */
public interface OnBSClickListener {
	/**
	 * {@link OneBtnBSDialog} 클릭 시 호출
	 */
	int ONE = 0;
	/**
	 * {@link TwoBtnBSDialog} 의 왼쪽 버튼 클릭 시 호출
	 */
	int LEFT = ONE + 1;
	/**
	 * {@link TwoBtnBSDialog} 의 오른쪽 버튼 클릭 시 호출
	 */
	int RIGHT = LEFT + 1;

	/**
	 *
	 * @param requestCode 호출 시 전달 한 requestCode
	 * @param type 버튼의 타입 {@link #ONE}, {@link #LEFT}, {@link #RIGHT}
	 */
	void onBSClick(int requestCode, int type);
}
