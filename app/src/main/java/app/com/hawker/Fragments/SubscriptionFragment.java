package app.com.hawker.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunctionException;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambdaClient;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.amazonaws.util.StringUtils;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.net.HttpURLConnection;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.com.hawker.Activities.ActivityCustomers;
import app.com.hawker.Activities.ApplicationClass;
import app.com.hawker.BuildConfig;
import app.com.hawker.R;
import app.com.hawker.Utils.Utils;
import app.com.hawker.dao.Subscription;


public class SubscriptionFragment extends Fragment {


	View vi ;
	ArrayList<Subscription> SubscriptionArrayList=new ArrayList<Subscription>();
	ListView listView_info_subscription;
	public static String customerId="0";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment

		vi = inflater.inflate(R.layout.fragment_item_list, container, false);

		listView_info_subscription=(ListView)vi.findViewById(R.id.listView_info_subscription);
		try{
			SubscriptionArrayList = new  ArrayList<Subscription>(((ApplicationClass) getActivity().getApplicationContext()).getSubscriptionArrayList());
			if(customerId.equals(ActivityCustomers.CustomerId)&&SubscriptionArrayList!=null)
			{
				if(SubscriptionArrayList.size()>0)
					listView_info_subscription.setAdapter(new MyCustomAdapter(getActivity(), SubscriptionArrayList));
				else
					Utils.showToast(getActivity(), "No data to display.");
			}
			else{
				callAllFragments("hello");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			callAllFragments("hello");

		}
		//setData();
		return vi;
	}
	void callAllFragments(final String customerId)
	{
		new SendJsonValue ().execute();
	}
	private String subsListForCustFunction(String customerId) {

		Gson gson = new Gson();
		AWSCredentials credentials = null;
		credentials = new BasicAWSCredentials(BuildConfig.ACCESS_KEY, BuildConfig.SECRET);
		AWSLambdaClient lambdaClient = new AWSLambdaClient(credentials);
		lambdaClient.setRegion(Region.getRegion(Regions.AP_NORTHEAST_1));
		try {
			InvokeRequest invokeRequest = new InvokeRequest();
			invokeRequest.setFunctionName("SubsListForCustomer");
			HashMap<String, String> map = new HashMap<String, String>();
			customerId= ActivityCustomers.CustomerId;

			map.put("CustomerId",ActivityCustomers.CustomerId);// "25425");
			invokeRequest.setPayload(ByteBuffer.wrap(gson.toJson(map).getBytes(StringUtils.UTF8)));
			InvokeResult invokeResult = lambdaClient.invoke(invokeRequest);

			if (invokeResult.getLogResult() != null) { 
				/* System.out.println(" log: " 
                        + new String(Base64.decode(invokeResult.getLogResult()))); */
			} 

			if (invokeResult.getFunctionError() != null) { 
				throw new LambdaFunctionException(invokeResult.getFunctionError(), 
						new String(invokeResult.getPayload().array())); 
			} 

			if (invokeResult.getStatusCode() == HttpURLConnection.HTTP_NO_CONTENT ) { 
				return ""; 
			} 
			String value = new String(invokeResult.getPayload().array(), "UTF-8");

			System.out.println(value);
			String jsonFormattedString = value.replaceAll("\\\\", "");
			jsonFormattedString=jsonFormattedString.replace("\"[", "[");
			jsonFormattedString=jsonFormattedString.replace("]\"", "]");
			jsonFormattedString=jsonFormattedString.replace("\"{", "{");
			jsonFormattedString=jsonFormattedString.replace("}\"", "}");

			JSONArray jsonArray=new JSONArray(jsonFormattedString);
			ArrayList<Subscription> subscriptionArrayList= new ArrayList<Subscription>();
			for(int i=0;i<jsonArray.length();i++)
			{
				subscriptionArrayList.add(gson.fromJson(jsonArray.get(i).toString(), Subscription.class));
			}
			this.SubscriptionArrayList=subscriptionArrayList;
			((ApplicationClass) getActivity().getApplicationContext()).setSubscriptionArrayList(subscriptionArrayList);
			return jsonFormattedString;

		}catch (LambdaFunctionException e){
			e.printStackTrace();
			return "";

		} catch (Exception e) {
			e.printStackTrace();
			return "";

		}
	}




	void setData()
	{
		try{
			//listView_info_subscription.removeAllViews();
			//			((ApplicationClass) getActivity().getApplicationContext()).setSubscriptionArrayList(subscriptionArrayList);
			//SubscriptionArrayList = new  ArrayList<Subscription>(((ApplicationClass) getActivity().getApplicationContext()).getSubscriptionArrayList());
			LayoutInflater inflater = getActivity().getLayoutInflater();
			ViewGroup header = (ViewGroup)inflater.inflate(R.layout.table_row_child_subscription, null, false);
			listView_info_subscription.addHeaderView(header, null, false);
			listView_info_subscription.setAdapter(new MyCustomAdapter(getActivity(), SubscriptionArrayList));

		}catch(Exception e)
		{
			e.printStackTrace();

		}
	}
	public class SendJsonValue extends AsyncTask<String, Void, String>{
		ProgressDialog mProgressDialog;
		public  void closeDialog() {
			if (mProgressDialog != null)
				mProgressDialog.dismiss();
		}
		public void showDialog(Context mContex) {
			if (Utils.isOnline(mContex)) {
				try {
					mProgressDialog = new ProgressDialog(mContex);
					mProgressDialog.setMessage("Loading...");
					mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
					mProgressDialog.setCancelable(false);
					mProgressDialog.setCanceledOnTouchOutside(false);
					mProgressDialog.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				Utils.showAlertBox(mContex, "No Internet Connection", null);
			}
		}
		@Override

		protected void onPreExecute(){
			showDialog(getActivity());
		}

		protected String doInBackground(String... params) {
			String Result="";

			try{

				Result=subsListForCustFunction("");


			}catch(Exception e){
				closeDialog();

			}

			return Result;

		}
		protected void onPostExecute(String Result){
			closeDialog();

			setData();
			System.out.println(Result);


		}

	}

	/*	public class MyCustomAdapter extends ArrayAdapter<Subscription> {

		public class ViewHolder {

			public TextView tv_subsid,tv_prod_id,tv_prod_name,tv_productType,tv_subsType,
			tv_freq,tv_subs_price,tv_subs_addToBill,tv_subs_frequnecy,tv_subs_dow,tv_subs_startDate
			,tv_subs_EndDate,tv_subs_stopDate,tv_subs_resumeDate,tv_subs_amount;
			public LinearLayout rootLayout_subscription;

		}

		public MyCustomAdapter(Context context, List<Subscription> objects) {
			super(context, R.layout.inflate_subscription, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View vi;
			ViewHolder holder;

			if(convertView == null) {
				vi = LayoutInflater.from(getContext()).inflate(R.layout.inflate_subscription, parent, false);
				holder = new ViewHolder();
				vi.setTag(holder);
			} else {
				vi = convertView;
				holder = (ViewHolder) vi.getTag();
			}

	 *//****** View Holder Object to contain tabitem.xml file elements ******//*
			Subscription country = getItem(position);
			holder.rootLayout_subscription=(LinearLayout) vi.findViewById(R.id.rootLayout_subscription);

			holder.tv_subsid = (TextView) vi.findViewById(R.id.tv_subsid);
			holder.tv_prod_id = (TextView) vi.findViewById(R.id.tv_prod_id);
			holder.tv_prod_name = (TextView) vi.findViewById(R.id.tv_prod_name);
			holder.tv_productType = (TextView) vi.findViewById(R.id.tv_productType);
			holder.tv_subsType = (TextView) vi.findViewById(R.id.tv_subsType);
			holder.tv_freq = (TextView) vi.findViewById(R.id.tv_freq);


			holder.tv_subsid.setText(""+country.getSubscriptionId());
			holder.tv_prod_id.setText(""+country.getProductId());
			holder.tv_prod_name.setText(""+country.getProductName());
			holder.tv_productType.setText(""+country.getProductType());
			holder.tv_subsType.setText(""+country.getSubscriptionType());
			holder.tv_freq.setText(""+country.getFrequency());

			holder.rootLayout_subscription.setTag(position);
			holder.rootLayout_subscription.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					int position= (int) v.getTag();

					BottomSheetDialogFragment bottomSheetDialogFragment=
							new SubscriptionBottomLayoutFragment(getItem(position));
					bottomSheetDialogFragment.show(getActivity().getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
				}
			});
			return vi;
		}
	}
	  */
	public class MyCustomAdapter extends ArrayAdapter<Subscription> {

		public class ViewHolder {

			public TextView   
			tv_subs_status,tv_subs_paymentType,tv_subs_type,tv_subs_productType,tv_subs_prod_code,tv_subs_id,
			tv_subs_frequnecy,
			tv_subs_price,tv_subs_addToBill,
			tv_subs_dow,tv_subs_startDate
			,tv_subs_EndDate,
			tv_subs_stopDate,tv_subs_resumeDate,tv_subs_amount;
			public LinearLayout rootLayout_subscription;

		}

		public MyCustomAdapter(Context context, List<Subscription> objects) {
			super(context, R.layout.table_row_subscription, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View vi;
			ViewHolder holder;
			Subscription country = getItem(position);
			if(convertView == null) {
				vi = LayoutInflater.from(getContext()).inflate(R.layout.table_row_subscription, parent, false);
				holder = new ViewHolder();
				vi.setTag(holder);
				holder.tv_subs_status = (TextView) vi.findViewById(R.id.tv_subs_status);
				holder.tv_subs_paymentType = (TextView) vi.findViewById(R.id.tv_subs_paymentType);
				holder.tv_subs_type = (TextView) vi.findViewById(R.id.tv_subs_type);
				holder.tv_subs_productType = (TextView) vi.findViewById(R.id.tv_subs_productType);
				holder.tv_subs_prod_code = (TextView) vi.findViewById(R.id.tv_subs_prod_code);
				holder.tv_subs_id = (TextView) vi.findViewById(R.id.tv_subs_id);
				holder.tv_subs_price = (TextView) vi.findViewById(R.id.tv_subs_price);
				holder.tv_subs_addToBill = (TextView) vi.findViewById(R.id.tv_subs_addToBill);
				holder.tv_subs_frequnecy = (TextView) vi.findViewById(R.id.tv_subs_frequnecy);
				holder.tv_subs_dow = (TextView) vi.findViewById(R.id.tv_subs_dow);
				holder.tv_subs_startDate = (TextView) vi.findViewById(R.id.tv_subs_startDate);
				holder.tv_subs_EndDate = (TextView) vi.findViewById(R.id.tv_subs_EndDate);
				holder.tv_subs_stopDate = (TextView) vi.findViewById(R.id.tv_subs_stopDate);
				holder.tv_subs_resumeDate = (TextView) vi.findViewById(R.id.tv_subs_resumeDate);
				holder.tv_subs_amount = (TextView) vi.findViewById(R.id.tv_subs_amount);
				vi.setTag(holder);
	
			} else {
				vi = convertView;
				holder = (ViewHolder) vi.getTag();
			}

			/****** View Holder Object to contain tabitem.xml file elements ******/
			holder.tv_subs_status.setText(""+country.getStatus());
			holder.tv_subs_paymentType.setText(""+country.getPaymentType());
			holder.tv_subs_type.setText(""+country.getSubscriptionType());
			holder.tv_subs_productType.setText(""+country.getProductType());
			holder.tv_subs_prod_code.setText(""+country.getProductCode());
			holder.tv_subs_id.setText(""+country.getSubscriptionId());
			holder.tv_subs_price.setText(""+country.getCost());
			holder.tv_subs_addToBill.setText(""+country.getAddToBill());
			holder.tv_subs_frequnecy.setText(""+country.getFrequency());
			holder.tv_subs_dow.setText(""+country.getDow());
			holder.tv_subs_startDate.setText(""+country.getStartDate());
			holder.tv_subs_EndDate.setText(""+country.getPausedDate());
			holder.tv_subs_stopDate.setText(""+country.getStopDate());
			holder.tv_subs_resumeDate.setText(""+country.getResumeDate());
			holder.tv_subs_amount.setText(""+country.getServiceCharge());
			return vi;
		}
	}

}