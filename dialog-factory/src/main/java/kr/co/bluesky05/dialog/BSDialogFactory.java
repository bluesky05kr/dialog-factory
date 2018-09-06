package kr.co.bluesky05.dialog;

/**
 * android api 11 (3.0) 이상부터 사용 가능합니다
 */
public class BSDialogFactory {
	public static String LOG_NAME = "BSDialogFactory";

	public static final class Type {
		/**
		 * 원형 dialog
		 */
		public static final Class<CircularProgressBSDialog.CircularProgressBuilder> CircularProgress = CircularProgressBSDialog.CircularProgressBuilder.class;
		/**
		 * 하단에 버튼 1개짜리 dialog
		 * <p/>
		 * implements {@link OnBSClickListener} 를 구현하여 click event 를 받을 수 있다
		 */
		public static final Class<OneBtnBSDialog.OneBtnBuilder> OneBtn = OneBtnBSDialog.OneBtnBuilder.class;
		/**
		 * 하단에 버튼 2개짜리 dialog
		 * <p/>
		 * implements {@link OnBSClickListener} 를 구현하여 click event 를 받을 수 있다
		 */
		public static final Class<TwoBtnBSDialog.TwoBtnBuilder> TwoBtn = TwoBtnBSDialog.TwoBtnBuilder.class;
	}

	public static <T extends Builder>T newDialogBuilder(Class<T> type) {
		if (type.getSimpleName().equals(CircularProgressBSDialog.CircularProgressBuilder.class.getSimpleName())) {
			return type.cast(new CircularProgressBSDialog.CircularProgressBuilder());
		} else if (type.getSimpleName().equals(OneBtnBSDialog.OneBtnBuilder.class.getSimpleName())) {
			return type.cast(new OneBtnBSDialog.OneBtnBuilder());
		} else if (type.getSimpleName().equals(TwoBtnBSDialog.TwoBtnBuilder.class.getSimpleName())) {
			return type.cast(new TwoBtnBSDialog.TwoBtnBuilder());
		}
		return null;
	}
}
