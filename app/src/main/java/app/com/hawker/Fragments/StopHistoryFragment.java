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
import app.com.hawker.dao.StopHistory;


public class StopHistoryFragment extends Fragment {

	ArrayList<StopHistory> stopHistories= new ArrayList<StopHistory>();
	ListView listView_info_subscription;
	View mView;
	public static String customerId="0";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		mView = inflater.inflate(R.layout.fragment_item_list, container, false);
		listView_info_subscription=(ListView)mView.findViewById(R.id.listView_info_subscription);
		try{
			stopHistories = new  ArrayList<StopHistory>(((ApplicationClass) getActivity().getApplicationContext()).getStopHistoryArrayList());
			if(customerId.equals(ActivityCustomers.CustomerId)&&stopHistories!=null)
			{
				if(stopHistories.size()>0)
					listView_info_subscription.setAdapter(new MyCustomAdapter(getActivity(), stopHistories));
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
		
		return mView;
	}
	void callAllFragments(final String customerId)
	{

		new SendJsonValue ().execute();

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

				Result=stpHistoryistForCustFunction("");

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
	private String stpHistoryistForCustFunction(String customerId) {

		Gson gson = new Gson();
		AWSCredentials credentials = null;
		credentials = new BasicAWSCredentials(BuildConfig.ACCESS_KEY, BuildConfig.SECRET);
		AWSLambdaClient lambdaClient = new AWSLambdaClient(credentials);
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("CustomerId", ActivityCustomers.CustomerId);//"6167");
		customerId= ActivityCustomers.CustomerId;
		lambdaClient.setRegion(Region.getRegion(Regions.AP_NORTHEAST_1));
		try {
			InvokeRequest invokeRequest = new InvokeRequest();
			invokeRequest.setFunctionName("StopHistoryListForCust");
			invokeRequest.setPayload(ByteBuffer.wrap(gson.toJson(map).getBytes(StringUtils.UTF8)));
			InvokeResult invokeResult = lambdaClient.invoke(invokeRequest);

			if (invokeResult.getLogResult() != null) {
				/*System.out.println(" log: "
        			+ new String(Base64.decode(invokeResult.getLogResult())));*/
			}

			if (invokeResult.getFunctionError() != null) {
				throw new LambdaFunctionException(invokeResult.getFunctionError(),
						new String(invokeResult.getPayload().array()));
			}
			if (invokeResult.getStatusCode() == HttpURLConnection.HTTP_NO_CONTENT ) {
				return "";
			}
			String value = new String(invokeResult.getPayload().array(), "UTF-8");

			System.out.println("StopHistoryListForCust:\t"+value);
			String jsonFormattedString = value.replaceAll("\\\\", "");
			jsonFormattedString=jsonFormattedString.replace("\"[", "[");
			jsonFormattedString=jsonFormattedString.replace("]\"", "]");
			jsonFormattedString=jsonFormattedString.replace("\"{", "{");
			jsonFormattedString=jsonFormattedString.replace("}\"", "}");

			JSONArray jsonArray=new JSONArray(jsonFormattedString);
			ArrayList<StopHistory> stopHistories= new ArrayList<StopHistory>();
			for(int i=0;i<jsonArray.length();i++)
			{
				stopHistories.add(gson.fromJson(jsonArray.get(i).toString(), StopHistory.class));
			}
			System.out.println(stopHistories);
			this.stopHistories =stopHistories;
			((ApplicationClass) getActivity().getApplicationContext()).setStopHistoryArrayList(stopHistories);
			return  jsonFormattedString;
		}catch (LambdaFunctionException e){
			e.printStackTrace();
			return  "";
		} catch (Exception e) {
			e.printStackTrace();
			return  "";

		}
	}
	void setData()
	{
		try {
			//			stopHistories.addAll(((ApplicationClass) getActivity().getApplicationContext()).getStopHistoryArrayList());
			//listView_info_subscription.removeAllViews();
			//stopHistories = new  ArrayList<StopHistory>(((ApplicationClass) getActivity().getApplicationContext()).getStopHistoryArrayList());
			LayoutInflater inflater = getActivity().getLayoutInflater();
			ViewGroup header = (ViewGroup)inflater.inflate(R.layout.table_row_child_stophistory, null, false);
			listView_info_subscription.addHeaderView(header, null, false);
			listView_info_subscription.setAdapter(new MyCustomAdapter(getActivity(), stopHistories));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public class MyCustomAdapter extends ArrayAdapter<StopHistory> {

		public class ViewHolder {

			public TextView tv_stopHistoryId,tv_SubId,tv_code,tv_name,tv_subType;
			public TextView tv_DOW,tv_stopDate,tv_resumeDate,tv_amount,tv_freq;

		}

		public MyCustomAdapter(Context context, List<StopHistory> objects) {
			super(context, R.layout.table_row_stophistory, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View vi;
			ViewHolder holder;
			StopHistory customer = getItem(position);
			if(convertView == null) {
				vi = LayoutInflater.from(getContext()).inflate(R.layout.table_row_stophistory, parent, false);
				holder = new ViewHolder();
				vi.setTag(holder);
				
				holder.tv_stopHistoryId=(TextView)vi.findViewById(R.id.tv_stopHistoryId);
				holder.tv_SubId=(TextView)vi.findViewById(R.id.tv_SubId);
				holder.tv_code=(TextView)vi.findViewById(R.id.tv_code);
				holder.tv_name=(TextView)vi.findViewById(R.id.tv_name);
				holder.tv_subType=(TextView)vi.findViewById(R.id.tv_subType);
				holder.tv_DOW=(TextView)vi.findViewById(R.id.tv_DOW);
				holder.tv_stopDate=(TextView)vi.findViewById(R.id.tv_stopDate);
				holder.tv_resumeDate=(TextView)vi.findViewById(R.id.tv_resumeDate);
				holder.tv_amount=(TextView)vi.findViewById(R.id.tv_amount);
				holder.tv_freq=(TextView)vi.findViewById(R.id.tv_freq);
			} else {
				vi = convertView;
				holder = (ViewHolder) vi.getTag();
			}

			/****** View Holder Object to contain tabitem.xml file elements ******/
		
			try{
				holder.tv_stopHistoryId.setText(""+customer.getStopHistoryId());
				holder.tv_SubId.setText(""+customer.getSubscriptionId());
				holder.tv_name.setText(""+customer.getCustomerName());
				holder.tv_code.setText(""+customer.getHawkerCode());
				holder.tv_freq.setText(""+customer.getSubscriptionFreq());
				holder.tv_subType.setText(""+customer.getSubscriptionType());
				holder.tv_DOW.setText(""+customer.getSubscriptionDOW());
				holder.tv_stopDate.setText(""+customer.getStopDate());
				holder.tv_resumeDate.setText(""+customer.getResumeDate());
				holder.tv_amount.setText(""+customer.getAmount());
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			vi.setTag(holder);
			return vi;
		}
	}

}