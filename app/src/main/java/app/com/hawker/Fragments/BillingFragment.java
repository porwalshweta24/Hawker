package app.com.hawker.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

import app.com.hawker.Activities.ActivityBillingList;
import app.com.hawker.Activities.ActivityCustomers;
import app.com.hawker.Activities.ApplicationClass;
import app.com.hawker.BuildConfig;
import app.com.hawker.R;
import app.com.hawker.Utils.Utils;
import app.com.hawker.awsDialoge.DialogShowDatesList;
import app.com.hawker.dao.Billing;
import app.com.hawker.dao.BillingLine;


public class BillingFragment extends Fragment implements View.OnClickListener{
	public static String bBillInvoiceNum="";
	ArrayList<Billing> billingsArrayList= new ArrayList<Billing>();
	TextView textview_selectDate;
	View mView;
	public static String customerId= "0";
	ArrayList<BillingLine> billingsLineArrayList= new ArrayList<BillingLine>();
	ListView listView_billing;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		mView = inflater.inflate(R.layout.inflate_billing_by_date, container, false);
		textview_selectDate=(TextView)mView.findViewById(R.id.textview_selectDate);
		listView_billing=(ListView) mView.findViewById(R.id.listView_billing);
		textview_selectDate.setOnClickListener(this);

		try{
			billingsArrayList= new  ArrayList<Billing>(((ApplicationClass) getActivity().getApplicationContext()).getBillingsArrayList());
			System.out.println(billingsArrayList);
			if(customerId.equals(ActivityCustomers.CustomerId)&&billingsArrayList!=null)
			{
				if(billingsArrayList.size()>0)
					textview_selectDate.setText(billingsArrayList.get(0).getInvoiceDate());
				//					listView_info_subscription.setAdapter(new MyCustomAdapter(getActivity(), billingsArrayList));
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

				Result=billingListForCustFunction("");

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
	void setData()
	{
		try {

			billingsArrayList= new  ArrayList<Billing>(((ApplicationClass) getActivity().getApplicationContext()).getBillingsArrayList());
			textview_selectDate.setText(billingsArrayList.get(0).getInvoiceDate());
			bBillInvoiceNum=billingsArrayList.get(0).getBillInvoiceNum();
			callAllFragmentsBillInvoiceNum(bBillInvoiceNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String billingListForCustFunction(String customerId) {

		Gson gson = new Gson();
		AWSCredentials credentials = null;
		credentials = new BasicAWSCredentials(BuildConfig.ACCESS_KEY, BuildConfig.SECRET);
		AWSLambdaClient lambdaClient = new AWSLambdaClient(credentials);
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("CustomerId", ActivityCustomers.CustomerId);;//);"6167");
		customerId=ActivityCustomers.CustomerId;
		lambdaClient.setRegion(Region.getRegion(Regions.AP_NORTHEAST_1));
		try {
			InvokeRequest invokeRequest = new InvokeRequest();
			invokeRequest.setFunctionName("BillingListForCust");
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

			System.out.println("BillingListForCust:\t"+ value);
			String jsonFormattedString = value.replaceAll("\\\\", "");
			jsonFormattedString=jsonFormattedString.replace("\"[", "[");
			jsonFormattedString=jsonFormattedString.replace("]\"", "]");
			jsonFormattedString=jsonFormattedString.replace("\"{", "{");
			jsonFormattedString=jsonFormattedString.replace("}\"", "}");
			JSONArray jsonArray=new JSONArray(jsonFormattedString);
			ArrayList<Billing> billingsArrayList= new ArrayList<Billing>();
			for(int i=0;i<jsonArray.length();i++)
			{
				billingsArrayList.add(gson.fromJson(jsonArray.get(i).toString(), Billing.class));
			}
			System.out.println(billingsArrayList);
			((ApplicationClass) getActivity().getApplicationContext()).setBillingsArrayList(billingsArrayList);
			System.out.println(((ApplicationClass) getActivity().getApplicationContext()).getBillingsArrayList());
			return jsonFormattedString;
		}catch (LambdaFunctionException e){
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return "";
	}
	public class MyCustomAdapter extends ArrayAdapter<Billing> {

		public class ViewHolder {

			public TextView tv_billInvoiceNum,tv_customerId,tv_invoiceDate
			,tv_pdfURL,tv_due,tv_month,tv_bill_invoice_num;
			public LinearLayout rootLayout_billing;


		}

		public MyCustomAdapter(Context context, List<Billing> objects) {
			super(context, R.layout.inflate_billing, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View vi;
			ViewHolder holder;

			if(convertView == null) {
				vi = LayoutInflater.from(getContext()).inflate(R.layout.inflate_billing, parent, false);
				holder = new ViewHolder();
				vi.setTag(holder);
			} else {
				vi = convertView;
				holder = (ViewHolder) vi.getTag();
			}
			try{
				/****** View Holder Object to contain tabitem.xml file elements ******/
				Billing country = getItem(position);

				holder.tv_billInvoiceNum=(TextView) vi.findViewById(R.id.tv_billInvoiceNum);
				holder.tv_customerId=(TextView) vi.findViewById(R.id.tv_customerId);
				holder.tv_invoiceDate=(TextView) vi.findViewById(R.id.tv_invoiceDate);
				holder.tv_pdfURL = (TextView) vi.findViewById(R.id.tv_pdfURL);
				holder.tv_due = (TextView) vi.findViewById(R.id.tv_due);
				holder.tv_month = (TextView) vi.findViewById(R.id.tv_month);
				holder.rootLayout_billing=(LinearLayout) vi.findViewById(R.id.rootLayout_billing);
				holder.tv_customerId.setText(""+country.getCustomerId());
				holder.tv_invoiceDate.setText(""+country.getInvoiceDate());
				holder.tv_pdfURL.setText(""+country.getPdfURL());
				holder.tv_due.setText(""+country.getDue());
				holder.tv_billInvoiceNum.setText(""+country.getBillInvoiceNum());
				holder.tv_month.setText(""+country.getMonth());


				holder.rootLayout_billing.setTag(position);
				holder.rootLayout_billing.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						int position= (int) v.getTag();
						bBillInvoiceNum=getItem(position).getBillInvoiceNum();
						Intent browserIntent = new Intent(getActivity(), ActivityBillingList.class);
						startActivity(browserIntent);

					}
				});
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			return vi;
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==textview_selectDate){
			DialogShowDatesList dialog= new DialogShowDatesList(getActivity(),billingsArrayList);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			dialog.show();
			dialog.setDialogResult(new DialogShowDatesList.OnMyDialogResult() {
				public void finish(Billing result) {
					bBillInvoiceNum=result.getBillInvoiceNum();
					textview_selectDate.setText(result.getInvoiceDate());
					callAllFragmentsBillInvoiceNum(bBillInvoiceNum);
				}
			});

		}
	}

	private String billingLineListForBillFunction(String billInvoiceNum) {

		Gson gson = new Gson();
		AWSCredentials credentials = null;
		credentials = new BasicAWSCredentials(BuildConfig.ACCESS_KEY, BuildConfig.SECRET);
		AWSLambdaClient lambdaClient = new AWSLambdaClient(credentials);
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("BillInvoiceNum",bBillInvoiceNum);// "25549");//BillingFragment.bBillInvoiceNum);//
		lambdaClient.setRegion(Region.getRegion(Regions.AP_NORTHEAST_1));
		try {
			InvokeRequest invokeRequest = new InvokeRequest();
			invokeRequest.setFunctionName("BillingLineListForBill");
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

			System.out.println("BillingLineListForBill\t"+value);
			String jsonFormattedString = value.replaceAll("\\\\", "");
			jsonFormattedString=jsonFormattedString.replace("\"[", "[");
			jsonFormattedString=jsonFormattedString.replace("]\"", "]");
			jsonFormattedString=jsonFormattedString.replace("\"{", "{");
			jsonFormattedString=jsonFormattedString.replace("}\"", "}");

			JSONArray jsonArray=new JSONArray(jsonFormattedString);
			ArrayList<BillingLine> billingLinesArrayList= new ArrayList<BillingLine>();
			for(int i=0;i<jsonArray.length();i++)
			{
				billingLinesArrayList.add(gson.fromJson(jsonArray.get(i).toString(), BillingLine.class));
			}
			System.out.println(billingLinesArrayList);
			((ApplicationClass) getActivity().getApplicationContext()).setBillingLinesArrayList(billingLinesArrayList);
			System.out.println(((ApplicationClass) getActivity().getApplicationContext()).getBillingLinesArrayList());
			return jsonFormattedString;
		}catch (LambdaFunctionException e){
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return "";
	}
	void callAllFragmentsBillInvoiceNum(final String customerId)
	{

		new SendJsonValueBillInvoiceNum ().execute();

	}
	public class SendJsonValueBillInvoiceNum extends AsyncTask<String, Void, String>{
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
				Result=billingLineListForBillFunction("");
			}catch(Exception e){
				closeDialog();
			}
			return Result;

		}
		protected void onPostExecute(String Result){
			closeDialog();
			setDataBillInvoiceNum();
			System.out.println(Result);


		}

	}
	void setDataBillInvoiceNum()
	{
		try {

			billingsLineArrayList= new  ArrayList<BillingLine>
			(((ApplicationClass) getActivity().getApplicationContext()).getBillingLinesArrayList());
			listView_billing.setAdapter(new MyCustomAdapterBillInvoiceNum(getActivity(), billingsLineArrayList));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public class MyCustomAdapterBillInvoiceNum extends ArrayAdapter<BillingLine> {

		public class ViewHolder {

			public TextView tv_billLineId,tv_bill_invoice_num,tv_product
			,tv_amount,tv_teaExpenses,tv_lineNum;
			public LinearLayout rootLayout_billing_list;
		}

		public MyCustomAdapterBillInvoiceNum(Context context, List<BillingLine> objects) {
			super(context, R.layout.inflate_billing_list, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View vi;
			ViewHolder holder;

			if(convertView == null) {
				vi = LayoutInflater.from(getContext()).inflate(R.layout.inflate_billing_list, parent, false);
				holder = new ViewHolder();
				vi.setTag(holder);
			} else {
				vi = convertView;
				holder = (ViewHolder) vi.getTag();
			}
			try{
				/****** View Holder Object to contain tabitem.xml file elements ******/
				BillingLine country = getItem(position);

				holder.tv_billLineId=(TextView) vi.findViewById(R.id.tv_billLineId);
				holder.tv_bill_invoice_num=(TextView) vi.findViewById(R.id.tv_bill_invoice_num);
				holder.tv_product=(TextView) vi.findViewById(R.id.tv_product);
				holder.tv_amount = (TextView) vi.findViewById(R.id.tv_amount);
				holder.tv_teaExpenses = (TextView) vi.findViewById(R.id.tv_teaExpenses);
				holder.tv_lineNum = (TextView) vi.findViewById(R.id.tv_lineNum);
				holder.rootLayout_billing_list=(LinearLayout) vi.findViewById(R.id.rootLayout_billing_list);
				holder.tv_bill_invoice_num.setText(""+country.getBillInvoiceNum());
				holder.tv_product.setText(""+country.getProduct());
				holder.tv_amount.setText(""+country.getAmount());
				holder.tv_teaExpenses.setText(""+country.getTeaExpenses());
				holder.tv_billLineId.setText(""+country.getBillLineId());
				holder.tv_lineNum.setText(""+country.getLineNum());
			}catch(Exception e)
			{
				e.printStackTrace();
			}

			return vi;
		}
	}


}