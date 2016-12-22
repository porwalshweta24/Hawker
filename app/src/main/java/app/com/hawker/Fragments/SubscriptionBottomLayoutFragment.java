package app.com.hawker.Fragments;


import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.com.hawker.R;
import app.com.hawker.dao.Subscription;

public class SubscriptionBottomLayoutFragment extends BottomSheetDialogFragment{
	Subscription subscription;

	public SubscriptionBottomLayoutFragment(Subscription country) {
		subscription=country;
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
		View vi= View.inflate(getContext(), R.layout.dialog_subscription, null);
		TextView   
		tv_subs_status,tv_subs_paymentType,tv_subs_type,tv_subs_productType,tv_subs_prod_code,tv_subs_id,
		tv_subs_frequnecy,
		tv_subs_price,tv_subs_addToBill,
		tv_subs_dow,tv_subs_startDate
		,tv_subs_EndDate,
		tv_subs_stopDate,tv_subs_resumeDate,tv_subs_amount;
		LinearLayout rootLayout_subscription;
		
		tv_subs_status = (TextView) vi.findViewById(R.id.tv_subs_status);
		tv_subs_paymentType = (TextView) vi.findViewById(R.id.tv_subs_paymentType);
		tv_subs_type = (TextView) vi.findViewById(R.id.tv_subs_type);
		tv_subs_productType = (TextView) vi.findViewById(R.id.tv_subs_productType);
		tv_subs_prod_code = (TextView) vi.findViewById(R.id.tv_subs_prod_code);
		tv_subs_id = (TextView) vi.findViewById(R.id.tv_subs_id);
		
		tv_subs_price = (TextView) vi.findViewById(R.id.tv_subs_price);
		tv_subs_addToBill = (TextView) vi.findViewById(R.id.tv_subs_addToBill);
		tv_subs_frequnecy = (TextView) vi.findViewById(R.id.tv_subs_frequnecy);
		tv_subs_dow = (TextView) vi.findViewById(R.id.tv_subs_dow);
		tv_subs_startDate = (TextView) vi.findViewById(R.id.tv_subs_startDate);
		tv_subs_EndDate = (TextView) vi.findViewById(R.id.tv_subs_EndDate);
		tv_subs_stopDate = (TextView) vi.findViewById(R.id.tv_subs_stopDate);
		tv_subs_resumeDate = (TextView) vi.findViewById(R.id.tv_subs_resumeDate);
		tv_subs_amount = (TextView) vi.findViewById(R.id.tv_subs_amount);
		rootLayout_subscription = (LinearLayout) vi.findViewById(R.id.rootLayout_subscription);


		try{

			tv_subs_status.setText(""+subscription.getStatus());
			tv_subs_paymentType.setText(""+subscription.getPaymentType());
			tv_subs_type.setText(""+subscription.getSubscriptionType());
			tv_subs_productType.setText(""+subscription.getProductType());
			tv_subs_prod_code.setText(""+subscription.getProductCode());
			tv_subs_id.setText(""+subscription.getSubscriptionId());
			tv_subs_price.setText(""+subscription.getCost());
			tv_subs_addToBill.setText(""+subscription.getAddToBill());
			tv_subs_frequnecy.setText(""+subscription.getFrequency());
			tv_subs_dow.setText(""+subscription.getDow());
			tv_subs_startDate.setText(""+subscription.getStartDate());
			tv_subs_EndDate.setText(""+subscription.getPausedDate());
			tv_subs_stopDate.setText(""+subscription.getStopDate());
			tv_subs_resumeDate.setText(""+subscription.getResumeDate());
			tv_subs_amount.setText(""+subscription.getServiceCharge());
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		dialog.setContentView(vi);
		CoordinatorLayout.LayoutParams params=(CoordinatorLayout.LayoutParams)
				((View)vi.getParent()).getLayoutParams();
		CoordinatorLayout.Behavior behavior= params.getBehavior();
		if(behavior!= null && behavior instanceof BottomSheetBehavior)
		{
			((BottomSheetBehavior)behavior).setBottomSheetCallback(mBottomSheetCallback);
		}
	}
}
