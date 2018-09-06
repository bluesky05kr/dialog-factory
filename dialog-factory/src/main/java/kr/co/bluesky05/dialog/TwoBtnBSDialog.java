package kr.co.bluesky05.dialog;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kr.co.bluesky05.bs_dialog_factory.R;


/**
 * 공통 팝업
 *
 * <pre>
 *     하단 버튼이 두개
 *     {@link TwoBtnBuilder}
 *
 *     {@link TwoBtnBuilder#requestCode} :  의 인자 값
 * </pre>
 */
public class TwoBtnBSDialog extends DialogFragment {
	public static final String TWO_BTN_BUILDER = "TWO_BTN_BUILDER";

	private TwoBtnBuilder builder;

	private OnBSClickListener onBSClickListener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BlueSky05_DialogTheme);

		builder = getArguments().getParcelable(TWO_BTN_BUILDER);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		if (activity instanceof OnBSClickListener) {
			onBSClickListener = (OnBSClickListener) activity;
		}
	}


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View rootView;
		if (builder.layoutResId == Builder.EMPTY) {
			rootView = inflater.inflate(R.layout.dialog_two_button_bs, container);
		} else {
			rootView = inflater.inflate(builder.layoutResId, container);
		}
		TextView txtMessage = (TextView) rootView.findViewById(R.id.txtMessage);
		if (txtMessage != null) {
			txtMessage.setText(builder.message);
		}

		TextView txtLeftButton = (TextView) rootView.findViewById(R.id.txtLeftButton);
		if (txtLeftButton != null) {
			if ( ! TextUtils.isEmpty(builder.lBtnText) ) {
				txtLeftButton.setText(builder.lBtnText);
			}
			txtLeftButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Fragment fragment = getTargetFragment();
					if (fragment != null) {
						if (fragment instanceof OnBSClickListener) {
							((OnBSClickListener) fragment).onBSClick(
									builder.requestCode, OnBSClickListener.LEFT);
						} else {
							fragment.onActivityResult(builder.requestCode, -1, null);
						}
					} else if (onBSClickListener != null) {
						onBSClickListener.onBSClick(builder.requestCode, OnBSClickListener.LEFT);
					}
					dismiss();
				}
			});
		}

		TextView txtRightButton = (TextView) rootView.findViewById(R.id.txtRightButton);
		if (txtRightButton != null) {
			if ( ! TextUtils.isEmpty(builder.rBtnText) ) {
				txtRightButton.setText(builder.rBtnText);
			}
			txtRightButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Fragment fragment = getTargetFragment();
					if (fragment != null) {
						if (fragment instanceof OnBSClickListener) {
							((OnBSClickListener) fragment).onBSClick(builder.requestCode, OnBSClickListener.RIGHT);
						} else {
							fragment.onActivityResult(builder.requestCode, -1, null);
						}
					} else if (onBSClickListener != null) {
						onBSClickListener.onBSClick(builder.requestCode, OnBSClickListener.RIGHT);
					}
					dismiss();
				}
			});
		}

		return rootView;
	}



	public static class TwoBtnBuilder extends BtnBuilder implements Parcelable {
		String lBtnText;
		String rBtnText;

		public TwoBtnBuilder() {
			super();
		}

		public TwoBtnBuilder(Parcel in) {
			super();

			readFromParcel(in);
		}

		@Override
		public TwoBtnBuilder setCancelable(boolean cancelable) {
			this.cancelable = cancelable;
			return this;
		}

		/**
		 * message text res id : txtMessage
		 * left button text res id : txtLeftButton
		 * right button text res id : txtRightButton
		 * @param layoutResId
		 * @return
		 */
		@Override
		public TwoBtnBuilder setLayoutResId(int layoutResId) {
			this.layoutResId = layoutResId;
			return this;
		}

		@Override
		public TwoBtnBuilder setRequestCode(int requestCode) {
			this.requestCode = requestCode;
			return this;
		}

		@Override
		public TwoBtnBuilder setMessage(String message) {
			this.message = message;
			return this;
		}

		public TwoBtnBuilder setLBtnText(String lBtnText) {
			this.lBtnText = lBtnText;
			return this;
		}

		public TwoBtnBuilder setRBtnText(String rBtnText) {
			this.rBtnText = rBtnText;
			return this;
		}

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeByte((byte) (this.cancelable ? 1 : 0));
			dest.writeInt(this.layoutResId);
			dest.writeInt(this.requestCode);
			dest.writeString(this.message);
			dest.writeString(this.lBtnText);
			dest.writeString(this.rBtnText);
		}

		private void readFromParcel(Parcel in) {
			this.cancelable = (in.readByte() != 0);
			this.layoutResId = in.readInt();
			this.requestCode = in.readInt();
			this.message = in.readString();
			this.lBtnText = in.readString();
			this.rBtnText = in.readString();
		}

		public static final Creator CREATOR = new Creator() {
			public TwoBtnBuilder createFromParcel(Parcel in) {
				return new TwoBtnBuilder(in);
			}

			public TwoBtnBuilder[] newArray(int size) {
				return new TwoBtnBuilder[size];
			}
		};

		public TwoBtnBSDialog create() {
			Bundle bundle = new Bundle();
			bundle.putParcelable(TWO_BTN_BUILDER, this);

			TwoBtnBSDialog dialog = new TwoBtnBSDialog();
			dialog.setCancelable(this.cancelable);
			dialog.setArguments(bundle);
			return dialog;
		}
	}
}
