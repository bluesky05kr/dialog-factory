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
 *     하단 버튼이 한개
 *     {@link OneBtnBuilder}
 *
 *     {@link OneBtnBuilder#requestCode} :  의 인자 값
 * </pre>
 */
public class OneBtnBSDialog extends DialogFragment {
	public static final String ONE_BTN_BUILDER = "ONE_BTN_BUILDER";

	private OneBtnBuilder builder;

	private OnBSClickListener onBSClickListener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BlueSky05_DialogTheme);

		builder = getArguments().getParcelable(ONE_BTN_BUILDER);
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
			rootView = inflater.inflate(R.layout.dialog_one_button_bs, container);
		} else {
			rootView = inflater.inflate(builder.layoutResId, container);
		}
		TextView txtMessage = (TextView) rootView.findViewById(R.id.txtMessage);
		if (txtMessage != null) {
			txtMessage.setText(builder.message);
		}
		TextView txtButton = (TextView) rootView.findViewById(R.id.txtButton);
		if (txtButton != null) {
			if ( ! TextUtils.isEmpty(builder.btnText) ) {
				txtButton.setText(builder.btnText);
			}
			txtButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Fragment fragment = getTargetFragment();
					if (fragment != null) {
						if (fragment instanceof OnBSClickListener) {
							((OnBSClickListener) fragment).onBSClick(builder.requestCode, OnBSClickListener.ONE);
						} else {
							fragment.onActivityResult(builder.requestCode, -1, null);
						}
					} else if (onBSClickListener != null) {
						onBSClickListener.onBSClick(builder.requestCode, OnBSClickListener.ONE);
					}

					dismiss();
				}
			});
		}

		return rootView;
	}



	public static class OneBtnBuilder extends BtnBuilder implements Parcelable {
		String btnText;

		public OneBtnBuilder() {
			super();
		}

		public OneBtnBuilder(Parcel in) {
			super();

			readFromParcel(in);
		}

		@Override
		public OneBtnBuilder setCancelable(boolean cancelable) {
			this.cancelable = cancelable;
			return this;
		}

		/**
		 * message text res id : txtMessage
		 * button text res id : txtButton
		 * @param layoutResId
		 * @return
		 */
		@Override
		public OneBtnBuilder setLayoutResId(int layoutResId) {
			this.layoutResId = layoutResId;
			return this;
		}

		@Override
		public OneBtnBuilder setRequestCode(int requestCode) {
			this.requestCode = requestCode;
			return this;
		}

		@Override
		public OneBtnBuilder setMessage(String message) {
			this.message = message;
			return this;
		}

		public OneBtnBuilder setBtnText(String btnText) {
			this.btnText = btnText;
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
			dest.writeString(this.btnText);
		}

		private void readFromParcel(Parcel in) {
			this.cancelable = (in.readByte() != 0);
			this.layoutResId = in.readInt();
			this.requestCode = in.readInt();
			this.message = in.readString();
			this.btnText = in.readString();
		}

		public static final Creator CREATOR = new Creator() {
			public OneBtnBuilder createFromParcel(Parcel in) {
				return new OneBtnBuilder(in);
			}

			public OneBtnBuilder[] newArray(int size) {
				return new OneBtnBuilder[size];
			}
		};

		public OneBtnBSDialog create() {
			Bundle bundle = new Bundle();
			bundle.putParcelable(ONE_BTN_BUILDER, this);

			OneBtnBSDialog dialog = new OneBtnBSDialog();
			dialog.setCancelable(this.cancelable);
			dialog.setArguments(bundle);
			return dialog;
		}
	}
}
