package kr.co.bluesky05.dialog;

/**
 *
 */
abstract class BtnBuilder<T extends Builder> extends Builder {

	/**
	 *
	 * @param requestCode
	 * @see OnBSClickListener
	 * @return 클릭 시 전달 될 값
	 */
	abstract public T setRequestCode(int requestCode);
}
