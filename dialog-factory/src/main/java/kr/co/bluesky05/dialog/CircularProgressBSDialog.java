package kr.co.bluesky05.dialog;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import kr.co.bluesky05.bs_dialog_factory.R;


/**
 * 원형 Progress 를 띄운다
 */
public class CircularProgressBSDialog extends DialogFragment {
	public static final String CIRCULAR_PROGRESS_BUILDER = "CIRCULAR_PROGRESS_BUILDER";

	private CircularProgressBuilder builder;

	private ImageView imgCircularProgress;
	private TextView txtMessage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BlueSky05_DialogTheme);

		builder = getArguments().getParcelable(CIRCULAR_PROGRESS_BUILDER);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View rootView;
		if (builder.layoutResId == Builder.EMPTY) {
			rootView = inflater.inflate(R.layout.dialog_circular_progress_bs, container);
		} else {
			rootView = inflater.inflate(builder.layoutResId, container);
		}

		imgCircularProgress = (ImageView) rootView.findViewById(R.id.imgCircularProgress);
		if (imgCircularProgress != null) {
			if (builder.drawableResId == Builder.EMPTY) {
				imgCircularProgress.setImageResource(R.drawable.ico_loading_01);
			} else {
				imgCircularProgress.setImageResource(builder.drawableResId);
			}
			startProgressAnimation(imgCircularProgress);
		}
		txtMessage = (TextView) rootView.findViewById(R.id.txtMessage);
		if ( (txtMessage != null) && ( !TextUtils.isEmpty(builder.message))) {
			txtMessage.setText(builder.message);
		}

		return rootView;
	}

	private void startProgressAnimation(ImageView imageView) {
		RotateAnimation anim = new RotateAnimation(0.0f, 360.0f,
				Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
		anim.setInterpolator(new LinearInterpolator());
		anim.setRepeatCount(Animation.INFINITE);
		anim.setDuration(700);
		imageView.setAnimation(anim);
		imageView.startAnimation(anim);
	}

	@Override
	public void show(FragmentManager manager, String tag) {
		FragmentTransaction ft = manager.beginTransaction();
		ft.add(this, tag);
		ft.commitAllowingStateLoss();
	}



	public static class CircularProgressBuilder extends ProgressBuilder implements Parcelable {
		int drawableResId;

		public CircularProgressBuilder() {
			drawableResId = EMPTY;
		}

		public CircularProgressBuilder(Parcel in) {
			super();

			readFromParcel(in);
		}

		@Override
		public CircularProgressBuilder setCancelable(boolean cancelable) {
			this.cancelable = cancelable;
			return this;
		}

		/**
		 * progress image res id : imgCircularProgress
		 * message text res id : txtMessage
		 * @param layoutResId
		 * @return
		 */
		@Override
		public CircularProgressBuilder setLayoutResId(int layoutResId) {
			this.layoutResId = layoutResId;
			return this;
		}

		@Override
		public CircularProgressBuilder setMessage(String message) {
			this.message = message;
			return this;
		}

		public CircularProgressBuilder setDrawableResId(int drawableResId) {
			this.drawableResId = drawableResId;
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
			dest.writeInt(this.drawableResId);
			dest.writeString(this.message);
		}

		private void readFromParcel(Parcel in) {
			this.cancelable = (in.readByte() != 0);
			this.layoutResId = in.readInt();
			this.drawableResId = in.readInt();
			this.message = in.readString();
		}

		public static final Creator CREATOR = new Creator() {
			public CircularProgressBuilder createFromParcel(Parcel in) {
				return new CircularProgressBuilder(in);
			}

			public CircularProgressBuilder[] newArray(int size) {
				return new CircularProgressBuilder[size];
			}
		};

		@Override
		public CircularProgressBSDialog create() {
			Bundle bundle = new Bundle();
			bundle.putParcelable(CIRCULAR_PROGRESS_BUILDER, this);

			CircularProgressBSDialog dialog = new CircularProgressBSDialog();
			dialog.setCancelable(this.cancelable);
			dialog.setArguments(bundle);
			return dialog;
		}
	}
}
