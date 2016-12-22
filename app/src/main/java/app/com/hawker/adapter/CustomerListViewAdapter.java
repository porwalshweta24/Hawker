package app.com.hawker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import app.com.hawker.R;
import app.com.hawker.dao.Customer;

public class CustomerListViewAdapter extends ArrayAdapter<Customer>{
	Context context;
	ArrayList<Customer> orderListVOs;
	private static LayoutInflater inflater = null;

	int layoutResourceId;  
	public CustomerListViewAdapter(Context context, int layoutResourceId, ArrayList<Customer> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.orderListVOs = data;
	}
	/*public CustomerListViewAdapter(Activity context, ArrayList<Customer> orderListVOs) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.orderListVOs = orderListVOs;

	}*/

	/*@Override
	public int getCount() {
		return orderListVOs.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}*/

	public class ViewHolder {
		public TextView house_seq_details,cust_code_details,name_details,mobile_num_details,t_due_details
		,h_no_details,flat_details,line1_details,line2_details,line3_details,City_details,State_details;
		public LinearLayout linear_layout_customer;
	}

	@Override
	public View getView(final int position, View view, ViewGroup parent) {

		ViewHolder holder;
		Customer country = orderListVOs.get(position);

		try {
			if (view == null) {
				inflater = (LayoutInflater) context.
						getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.table_row_child_customer, null);
				holder = new ViewHolder();

				/****** View Holder Object to contain tabitem.xml file elements ******/
				holder.house_seq_details = (TextView) view.findViewById(R.id.house_seq_details);
				holder.cust_code_details = (TextView) view.findViewById(R.id.cust_code_details);
				holder.name_details = (TextView) view.findViewById(R.id.name_details);
				holder.mobile_num_details = (TextView) view.findViewById(R.id.mobile_num_details);
				holder.t_due_details = (TextView) view.findViewById(R.id.t_due_details);
				holder.h_no_details = (TextView) view.findViewById(R.id.h_no_details);
				holder.flat_details = (TextView) view.findViewById(R.id.flat_details);
				holder.line1_details = (TextView) view.findViewById(R.id.line1_details);
				holder.line2_details = (TextView) view.findViewById(R.id.line2_details);
				holder.line3_details = (TextView) view.findViewById(R.id.line3_details);
				holder.City_details = (TextView) view.findViewById(R.id.City_details);
				holder.State_details = (TextView) view.findViewById(R.id.State_details);
				holder.linear_layout_customer=(LinearLayout) view.findViewById(R.id.linear_layout_customer);
				//holder.linear_layout_customer.setTag(country.getCustomerId());
				
				
				view.setTag(holder);
			}else
				holder = (ViewHolder) view.getTag();
			holder.house_seq_details.setText(""+country.getHouseSeq().split("\\.")[0]);
			holder.cust_code_details.setText(country.getCustomerCode().split("\\.")[0]);
			holder.name_details.setText(country.getName().split("\\.")[0]);
			holder.mobile_num_details.setText(country.getMobileNum());
			holder.t_due_details.setText(country.getTotalDue().split("\\.")[0]); 
			holder.h_no_details.setText(country.getNewHouseNum().split("\\.")[0]);
			holder.flat_details.setText(country.getBuildingStreet());
			holder.line1_details.setText(country.getAddrLine1());
			holder.line2_details.setText(country.getAddrLine2());
			holder.line3_details.setText(country.getLocality());
			holder.City_details.setText(country.getCity());
			holder.State_details.setText(country.getState());

			/*holder.linear_layout_customer.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					try{
//						String custId=v.getTag().toString();
						int pos=(int)(v.getTag());	
						Customer country =orderListVOs.get(pos);
						Intent browserIntent = new Intent(context, ActivityCustomerSubs.class);
						browserIntent.putExtra("CUSTOMER_ID", country.getCustomerId());
						context.startActivity(browserIntent);

					}catch(Exception e){
						e.printStackTrace();
					}
				}
			});*/
		}catch (Exception e){
			e.printStackTrace();
		}
		return view;
	}


}
