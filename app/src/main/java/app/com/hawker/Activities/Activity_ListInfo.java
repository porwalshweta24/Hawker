package app.com.hawker.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
import com.amazonaws.util.Base64;
import com.amazonaws.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import app.com.hawker.R;
import app.com.hawker.Utils.Utils;
import app.com.hawker.dao.Customer;
import app.com.hawker.dao.Hawker;
import app.com.hawker.dao.LineInfo;

import app.com.hawker.BuildConfig;

/**
 * Created by admin on 8/15/2016.
 */
public class Activity_ListInfo  extends AppCompatActivity implements View.OnClickListener {
	ArrayList<LineInfo> lineInfoArrayList;
	ArrayList<Customer> customerArrayList;
	TextView tv_title;
	ImageView iv_list_report;
	ImageView iv_logout;
	ImageView iv_back;
	ListView listView_info;
static int pos_customer=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.activity_list_info);
			init();
			getInfo();
			onCall();

		}catch (Exception e){
			e.printStackTrace();
		}
	}

	void init()
	{

		tv_title= (TextView) findViewById(R.id.tv_title);
		iv_list_report= (ImageView) findViewById(R.id.iv_list_report);
		iv_logout= (ImageView) findViewById(R.id.iv_logout);
		iv_back= (ImageView) findViewById(R.id.iv_back);
		listView_info=(ListView)findViewById(R.id.listView_info);
		tv_title.setText("List Info");
		iv_logout.setVisibility(View.GONE);
		iv_list_report.setVisibility(View.GONE);
		iv_back.setVisibility(View.VISIBLE);
	}
	void getInfo()
	{
		new AsyncForListInfo().execute();

	}

	public class AsyncForListInfo extends AsyncTask<String, Void, String> {
		ProgressDialog mProgressDialog;

		public void closeDialog() {
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

		protected void onPreExecute() {
			showDialog(Activity_ListInfo.this);
		}

		protected String doInBackground(String... params) {
			String Result = "";

			try {

				Result = callLineInfoListFunction();
			} catch (Exception e) {
				closeDialog();

			}

			return Result;

		}

		protected void onPostExecute(String Result) {
			closeDialog();

			setDataListInfo();
			System.out.println(Result);
		}
	}
	private String callLineInfoListFunction() {


		Hawker	hawker = new Hawker(((ApplicationClass) this.getApplicationContext()).getHawker());

		Gson gson = new Gson();
		AWSCredentials credentials = null;
		credentials = new BasicAWSCredentials(BuildConfig.ACCESS_KEY, BuildConfig.SECRET);
		AWSLambdaClient lambdaClient = new AWSLambdaClient(credentials);
		lambdaClient.setRegion(Region.getRegion(Regions.AP_NORTHEAST_1));

		try {
			InvokeRequest invokeRequest = new InvokeRequest();
			invokeRequest.setFunctionName("LineInfoList");
			HashMap<String, String> map1 = new HashMap<String, String>();
			map1.put("HawkerId", "" + hawker.getHawkerId());
			invokeRequest.setPayload(ByteBuffer.wrap(gson.toJson(map1).getBytes(StringUtils.UTF8)));
			InvokeResult invokeResult = lambdaClient.invoke(invokeRequest);

			if (invokeResult.getLogResult() != null) {
				System.out.println(" log: "
						+ new String(Base64.decode(invokeResult.getLogResult())));
			}

			if (invokeResult.getFunctionError() != null) {
				throw new LambdaFunctionException(invokeResult.getFunctionError(),
						new String(invokeResult.getPayload().array()));
			}

			if (invokeResult.getStatusCode() == HttpURLConnection.HTTP_NO_CONTENT) {
				return "";
			}
			lineInfoArrayList = new ArrayList<>();

			//            lineInfo = gson.fromJson(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(invokeResult.getPayload().array()))), ArrayList.class);
			ArrayList<LinkedTreeMap> subtree = (ArrayList<LinkedTreeMap>) gson.fromJson(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(invokeResult.getPayload().array()))), ArrayList.class);
			for (LinkedTreeMap map : subtree) {

				LineInfo review = new LineInfo((map.get("lineId").toString()),
						(map.get("lineNum").toString()),
						(map.get("hawkerId").toString()),
						map.get("lineNumDist").toString());

				lineInfoArrayList.add(review);
			}


		} catch (Exception e) {
			e.printStackTrace();
		}


		return "";

	}
	void setDataListInfo() {

		try {
			System.out.println("reviews:\t" + lineInfoArrayList);
			if (lineInfoArrayList != null) {
				((ApplicationClass) this.getApplicationContext()).setLineInfoArrayList(lineInfoArrayList);
				System.out.println("LineInfoList:\t" + lineInfoArrayList);
		getListViewData();

			} else {
				Utils.showToast(Activity_ListInfo.this, "No data to display or retry.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void getListViewData()
	{
		lineInfoArrayList = new  ArrayList<LineInfo>(((ApplicationClass) this.getApplicationContext()).getLineInfoArrayList());
		CustomAdapter adapter = new CustomAdapter(this, lineInfoArrayList, getResources());
		listView_info.setAdapter(adapter);

	}
	void onCall()
	{
		iv_back.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.iv_back){
			//ActivityCustomers
			ApplicationClass globalVariable = (ApplicationClass) getApplicationContext();
			if(globalVariable.isBackActivity()){
				globalVariable.setBackActivity(false);
				Intent intent = new Intent(this, ActivityDashBoard.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				finish();
			}else
				finish();
		}

	}
	private class CustomAdapter extends BaseAdapter {
		ViewHolder holder;

		/***********
		 * Declare Used Variables
		 *********/
		private Activity activity;
		private ArrayList<LineInfo> data;
		private LayoutInflater inflater = null;
		public Resources res;
		int i = 0;

		/*************
		 * CustomAdapter Constructor
		 *****************/
		public CustomAdapter(Activity a, ArrayList<LineInfo> d, Resources resLocal) {

			/********** Take passed values **********/
			activity = a;
			data = d;
			res = resLocal;

			/***********  Layout inflator to call external xml layout () ***********/
			inflater = (LayoutInflater) activity.
					getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		/********
		 * What is the size of Passed Arraylist Size
		 ************/
		public int getCount() {

			return data.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		/*********
		 * Create a holder Class to contain inflated xml file elements
		 *********/
		public class ViewHolder {

			public TextView tv_lineNum_detail;
			public LinearLayout linear_layout_listinfo;
		}

		/******
		 * Depends upon data size called for each row , Create each ListView row
		 *****/
		@SuppressLint("InflateParams")
		public View getView(final int position, View convertView, ViewGroup parent) {

			View vi = convertView;
			holder = new ViewHolder();
			LineInfo country = data.get(position);

			try {
				if (convertView == null) {

					/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
					vi = inflater.inflate(R.layout.inflate_row, null);

					/****** View Holder Object to contain tabitem.xml file elements ******/
					holder.tv_lineNum_detail = (TextView) vi.findViewById(R.id.tv_lineNum_detail);
					holder.linear_layout_listinfo=(LinearLayout) vi.findViewById(R.id.linear_layout_listinfo);
					holder.tv_lineNum_detail.setTag(country);
					vi.setTag(holder);
				} else
					holder = (ViewHolder) vi.getTag();


				holder.tv_lineNum_detail.setText(country.getLineNumDist());
				holder.linear_layout_listinfo.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						pos_customer=position;
						callAllFragments();
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
			return vi;
		}


	}
	void callAllFragments()
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
			showDialog(Activity_ListInfo.this);
		}

		protected String doInBackground(String... params) {
			String Result="";

			try{

				Result=	customerListForLineFunction(pos_customer);
			}catch(Exception e){
				closeDialog();
			}

			return Result;

		}
		protected void onPostExecute(String Result){
			closeDialog();
			setData(Result);
			System.out.println(Result);
		}
	}
	void setData(String jsonFormattedString)
	{


		try{
			Log.d("CustomerListFor", "CustomerListForHawkerAndLine");
			customerArrayList= new ArrayList<>();
			Gson gson = new Gson();

			ArrayList<LinkedTreeMap> subtree = (ArrayList<LinkedTreeMap>) gson.fromJson(jsonFormattedString,ArrayList.class);
			if(subtree.size()>0)
				for(LinkedTreeMap map1 : subtree){
					Customer review = new Customer();

					review.setCustomerId(map1.get("customerId").toString());
					review.setName(map1.get("name").toString());
					review.setCustomerCode(map1.get("customerCode").toString());
					try{
						review.setMobileNum(map1.get("mobileNum").toString());
					}catch(Exception e)
					{
						review.setMobileNum("");
					}
					review.setHawkerCode(map1.get("hawkerCode").toString());
					review.setLineNum(map1.get("lineNum").toString());
					review.setHouseSeq(map1.get("houseSeq").toString());
					try{
					review.setOldHouseNum(map1.get("oldHouseNum").toString());
					}catch(Exception e)
					{
						review.setOldHouseNum("");
					}
					try{
					review.setNewHouseNum(map1.get("newHouseNum").toString());
					}catch(Exception e)
					{
						review.setNewHouseNum("");
					}
					try{
					review.setAddrLine1(map1.get("addrLine1").toString());
					}catch(Exception e)
					{
						review.setAddrLine1("");
					}
					try{
					review.setAddrLine2(map1.get("addrLine2").toString());
					}catch(Exception e)
					{
						review.setAddrLine2("");
					}
					try{
					review.setLocality(map1.get("locality").toString());
					}catch(Exception e)
					{
						review.setLocality("");
					}
					try{
					
					review.setCity(map1.get("city").toString());
					}catch(Exception e)
					{
						review.setCity("");
					}
					try{
					review.setState(map1.get("state").toString());
					}catch(Exception e)
					{
						review.setState("");
					}
					try{
					
					review.setProfile1(map1.get("profile1").toString());
					}catch(Exception e)
					{
						review.setProfile1("");
					}
					try{
					review.setProfile2(map1.get("profile2").toString());
					}catch(Exception e)
					{
						review.setProfile2("");
					}
					try{
					review.setProfile3(map1.get("profile3").toString());
					}catch(Exception e)
					{
						review.setProfile3("");
					}
					try{
					review.setInitials(map1.get("initials").toString());
					}catch(Exception e)
					{
						review.setInitials("");
					}
					try{
					review.setEmployment(map1.get("employment").toString());
					}catch(Exception e)
					{
						review.setEmployment("");
					}
					try{
					review.setComments(map1.get("comments").toString());
					}catch(Exception e)
					{
						review.setComments("");
					}
					try{
					review.setBuildingStreet(map1.get("buildingStreet").toString());
					}catch(Exception e)
					{
						review.setBuildingStreet("");
					}
					review.setHawkerId(map1.get("hawkerId").toString());
					review.setLineId(map1.get("lineId").toString());
					review.setTotalDue(map1.get("totalDue").toString());
					customerArrayList.add(review);
				}

			if( customerArrayList.size()>0) {
				System.out.println("customerArrayList:\t" + customerArrayList);
				((ApplicationClass) this.getApplicationContext()).setCustomerArrayList(customerArrayList);
				Intent browserIntent = new Intent(Activity_ListInfo.this, ActivityCustomers.class);
				startActivity(browserIntent);
				
			}
			else
			{
				Utils.showToast(Activity_ListInfo.this, "Nothing to display.");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String customerListForLineFunction(int position) {

		Hawker hawker = new Hawker(((ApplicationClass) this.getApplicationContext()).getHawker());

		Gson gson = new Gson();
		AWSCredentials credentials = null;

		credentials = new BasicAWSCredentials(BuildConfig.ACCESS_KEY,BuildConfig. SECRET);
		AWSLambdaClient lambdaClient = new AWSLambdaClient(credentials);

		lambdaClient.setRegion(Region.getRegion(Regions.AP_NORTHEAST_1));
		try {
			InvokeRequest invokeRequest = new InvokeRequest();
			invokeRequest.setFunctionName("CustomerListForHawkerAndLine");
			HashMap<String,String> map = new HashMap<String,String>();
			//            map.put("hawkerId", hawker.getHawkerId().toString());
			map.put("LineId",  lineInfoArrayList.get(position).getLineId().toString());
			invokeRequest.setPayload(ByteBuffer.wrap(gson.toJson(map).getBytes(StringUtils.UTF8)));
			InvokeResult invokeResult = lambdaClient.invoke(invokeRequest);

			if (invokeResult.getLogResult() != null) {
				System.out.println(" log: "
						+ new String(Base64.decode(invokeResult.getLogResult())));
			}

			if (invokeResult.getFunctionError() != null) {
				throw new LambdaFunctionException(invokeResult.getFunctionError(),
						new String(invokeResult.getPayload().array()));
			}

			if (invokeResult.getStatusCode() == HttpURLConnection.HTTP_NO_CONTENT ) {
				return "";
			}
			String value = new String(invokeResult.getPayload().array(), "UTF-8");
			String jsonFormattedString = value.replaceAll("\\\\", "");
			jsonFormattedString=jsonFormattedString.replace("\"[", "[");
			jsonFormattedString=jsonFormattedString.replace("]\"", "]");    
			/* jsonFormattedString=jsonFormattedString.replace("\"{", "{");
            jsonFormattedString=jsonFormattedString.replace("}\"", "}"); */   

			System.out.println("CustomerListForHawkerAndLine:\t"+jsonFormattedString);
			return  jsonFormattedString;
			//			System.out.println("CustomerListForHawkerAndLine:\t"+gson.fromJson(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(invokeResult.getPayload().array()))), ArrayList.class));
			

		}catch (LambdaFunctionException e){
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return "";
	}
}
