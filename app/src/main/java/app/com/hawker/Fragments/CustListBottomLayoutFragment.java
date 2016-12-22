package app.com.hawker.Fragments;


import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.TextView;

import app.com.hawker.R;
import app.com.hawker.dao.Customer;

public class CustListBottomLayoutFragment extends BottomSheetDialogFragment{
	Customer customer;

	public CustListBottomLayoutFragment(Customer country) {
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
		View coView= View.inflate(getContext(), R.layout.dialog_modal, null);
		TextView tv_house_seq,tv_customerCode,tv_name,tv_mobile,tv_t_due;
		TextView tv_state,tv_city,tv_line3,tv_line2,tv_line1,tv_flat,tv_H_no,tv_cust_id;
		tv_cust_id=(TextView)coView.findViewById(R.id.tv_cust_id);
		tv_house_seq=(TextView)coView.findViewById(R.id.tv_house_seq);
		tv_customerCode=(TextView)coView.findViewById(R.id.tv_customerCode);
		tv_name=(TextView)coView.findViewById(R.id.tv_name);
		tv_mobile=(TextView)coView.findViewById(R.id.tv_mobile);
		tv_t_due=(TextView)coView.findViewById(R.id.tv_t_due);

		tv_state=(TextView)coView.findViewById(R.id.tv_state);
		tv_city=(TextView)coView.findViewById(R.id.tv_city);
		tv_line3=(TextView)coView.findViewById(R.id.tv_line3);
		tv_line2=(TextView)coView.findViewById(R.id.tv_line2);
		tv_line1=(TextView)coView.findViewById(R.id.tv_line1);
		tv_flat=(TextView)coView.findViewById(R.id.tv_flat);
		tv_H_no=(TextView)coView.findViewById(R.id.tv_H_no);
		try{
			tv_house_seq.setText(""+customer.getHouseSeq());
			tv_customerCode.setText(""+customer.getCustomerCode());
			tv_name.setText(""+customer.getName());
			tv_mobile.setText(""+customer.getMobileNum());
			tv_t_due.setText(""+customer.getTotalDue());
			tv_state.setText(""+customer.getState());
			tv_city.setText(""+customer.getCity());
			tv_line3.setText(""+customer.getNewHouseNum());
			tv_line2.setText(""+customer.getAddrLine2());
			tv_line1.setText(""+customer.getAddrLine2());
			tv_flat.setText(""+customer.getBuildingStreet());
			tv_H_no.setText(""+customer.getOldHouseNum());
			tv_cust_id.setText(""+customer.getCustomerId());
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
