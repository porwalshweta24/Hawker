package app.com.hawker.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import com.amazonaws.util.StringUtils;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.net.HttpURLConnection;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.com.hawker.R;
import app.com.hawker.Utils.Utils;
import app.com.hawker.dao.BillingLine;
import app.com.hawker.BuildConfig;
public class ActivityBillingList  extends AppCompatActivity  implements View.OnClickListener{
	TextView tv_title;
	ImageView iv_list_report;
	ImageView iv_logout;
	ImageView iv_back;
	ListView listView_info;
	ArrayList<BillingLine> billingsLineArrayList= new ArrayList<BillingLine>();
 
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

		tv_title.setText("Billing List");
		iv_logout.setVisibility(View.GONE);
		iv_list_report.setVisibility(View.GONE);
		iv_back.setVisibility(View.VISIBLE);
	}
	void onCall()
	{
		iv_back.setOnClickListener(this);
	}
	void getInfo()
	{
		try{
			callAllFragmentsBillInvoiceNum("hello");
		}catch(Exception e)
		{
			e.printStackTrace();


		}
	}
	private String billingLineListForBillFunction(String billInvoiceNum) {

		Gson gson = new Gson();
		AWSCredentials credentials = null;
		credentials = new BasicAWSCredentials(BuildConfig.ACCESS_KEY, BuildConfig.SECRET);
		AWSLambdaClient lambdaClient = new AWSLambdaClient(credentials);
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("BillInvoiceNum", "25549");//BillingFragment.bBillInvoiceNum);//
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
			((ApplicationClass) this.getApplicationContext()).setBillingLinesArrayList(billingLinesArrayList);
			System.out.println(((ApplicationClass) this.getApplicationContext()).getBillingLinesArrayList());
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
			showDialog(ActivityBillingList.this);
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
			(((ApplicationClass) this.getApplicationContext()).getBillingLinesArrayList());
			listView_info.setAdapter(new MyCustomAdapterBillInvoiceNum(this, billingsLineArrayList));

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
	
	
	
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.iv_back){
			finish();
		}

	}
}
