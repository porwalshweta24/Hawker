package app.com.hawker.Fragments;


import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.TextView;

import app.com.hawker.R;
import app.com.hawker.dao.StopHistory;

public class StopHistoryBottomLayoutFragment extends BottomSheetDialogFragment{
	StopHistory customer;

	public StopHistoryBottomLayoutFragment(StopHistory country) {
		customer=country;
	}
	private BottomSheetBehavior.BottomSheetCallback mBottomSheetCallback= new BottomSheetBehavior.BottomSheetCallback() {

		@Override
		public void onStateChanged(@NonNull View arg0, int newState) {
			// TODO Auto-generated method stub
			if(newState == BottomSheetBehavior.STATE_HIDDEN)
				dismiss();
		}

		@Override
		public void onSlide(@NonNull View arg0, float arg1) {
			// TODO Auto-generated method stub

		}
	};

	@Override
	public void setupDialog(Dialog dialog, int style) {
		// TODO Auto-generated method stub
		super.setupDialog(dialog, style);
		View coView= View.inflate(getContext(), R.layout.dialog_stophistory, null);
		TextView tv_stopHistoryId,tv_SubId,tv_code,tv_name,tv_subType;
		TextView tv_DOW,tv_stopDate,tv_resumeDate,tv_amount,tv_freq;
		tv_stopHistoryId=(TextView)coView.findViewById(R.id.tv_stopHistoryId);
		tv_SubId=(TextView)coView.findViewById(R.id.tv_SubId);
		tv_code=(TextView)coView.findViewById(R.id.tv_code);
		tv_name=(TextView)coView.findViewById(R.id.tv_name);
		tv_subType=(TextView)coView.findViewById(R.id.tv_subType);
		tv_DOW=(TextView)coView.findViewById(R.id.tv_DOW);
		tv_stopDate=(TextView)coView.findViewById(R.id.tv_stopDate);
		tv_resumeDate=(TextView)coView.findViewById(R.id.tv_resumeDate);
		tv_amount=(TextView)coView.findViewById(R.id.tv_amount);
		tv_freq=(TextView)coView.findViewById(R.id.tv_freq);

	try{
			tv_stopHistoryId.setText(""+customer.getStopHistoryId());
			tv_SubId.setText(""+customer.getSubscriptionId());
			tv_name.setText(""+customer.getCustomerName());
			tv_code.setText(""+customer.getHawkerCode());
			tv_freq.setText(""+customer.getSubscriptionFreq());

			tv_subType	.setText(""+customer.getSubscriptionType());
			tv_DOW.setText(""+customer.getSubscriptionDOW());
			tv_stopDate.setText(""+customer.getStopDate());
			tv_resumeDate.setText(""+customer.getResumeDate());
			tv_amount.setText(""+customer.getAmount());
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		dialog.setContentView(coView);
		CoordinatorLayout.LayoutParams params=(CoordinatorLayout.LayoutParams)((View)coView.getParent()).getLayoutParams();
		CoordinatorLayout.Behavior behavior= params.getBehavior();
		if(behavior!= null && behavior instanceof BottomSheetBehavior)
		{
			((BottomSheetBehavior)behavior).setBottomSheetCallback(mBottomSheetCallback);
		}
	}
}
