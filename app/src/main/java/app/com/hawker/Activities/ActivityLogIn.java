package app.com.hawker.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
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

import org.json.JSONArray;

import java.net.HttpURLConnection;
import java.nio.ByteBuffer;
import java.util.HashMap;

import app.com.hawker.BuildConfig;
import app.com.hawker.Models.MySharedpreference;
import app.com.hawker.R;
import app.com.hawker.Utils.Utils;
import app.com.hawker.dao.Hawker;


/**
 * Created by admin on 8/6/2016.
 */
public class ActivityLogIn extends AppCompatActivity {
	static ProgressDialog mProgressDialog;
	Hawker hawker;
	public MySharedpreference mySharedpreference;

	RelativeLayout login_user_rel;
	Button login_btnlogin;
	EditText login_et_mobilenumber, login_et_password;
	TextView login_fpassword, login_reglink;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initViews();
	}

	private void initViews() {

		mySharedpreference = new MySharedpreference(this);
		login_btnlogin = (Button) findViewById(R.id.login_btnlogin);
		login_et_mobilenumber = (EditText) findViewById(R.id.login_et_mobilenumber);
		login_et_password = (EditText) findViewById(R.id.login_et_password);
		login_fpassword = (TextView) findViewById(R.id.login_fpassword);
		login_reglink = (TextView) findViewById(R.id.login_reglink);

		login_btnlogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
					if(isValidate())
						callAllFragments();
					else
						Utils.showToast(ActivityLogIn.this, "Please enter credentials.");
			}
		});
	}
	void callAllFragments()
	{

		new SendJsonValue ().execute();

	}
boolean isValidate()
{
	try{
		if(login_et_mobilenumber.getText().toString().equals("")){ 
			Utils.showToast(ActivityLogIn.this, "Please enter mobile number.");

			return false;}
		if(login_et_password.getText().toString().equals("")){
			Utils.showToast(ActivityLogIn.this, "Please enter password.");

			return false;}
       
	}catch(Exception e)
	{
		e.printStackTrace();
	}
	return true;
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
			showDialog(ActivityLogIn.this);
		}

		protected String doInBackground(String... params) {
			String Result="";

			try{

				Result=callLoginFunction();


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

		@SuppressLint("NewApi")
		private String callLoginFunction() {
			if (android.os.Build.VERSION.SDK_INT > 9) {
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
			}

			Gson gson = new Gson();
			AWSCredentials credentials = null;
			credentials = new BasicAWSCredentials(BuildConfig.ACCESS_KEY, BuildConfig.SECRET);
			AWSLambdaClient lambdaClient = new AWSLambdaClient(credentials);
			lambdaClient.setRegion(Region.getRegion(Regions.AP_NORTHEAST_1));
			try {
				InvokeRequest invokeRequest = new InvokeRequest();
				invokeRequest.setFunctionName("Login");
				HashMap<String, String> map = new HashMap<String, String>();
				String mobile="9841155736";
				String password="narayanan";
				map.put("Mobile", login_et_mobilenumber.getText().toString());
	            map.put("Password",login_et_password.getText().toString());
				/*map.put("Mobile", mobile);
				map.put("Password", password);*/
				invokeRequest.setPayload(ByteBuffer.wrap(gson.toJson(map).getBytes(StringUtils.UTF8)));
				InvokeResult invokeResult = lambdaClient.invoke(invokeRequest);

				if (invokeResult.getLogResult() != null) {
					System.out.println(" log: "
							+ new String(Base64.decode(invokeResult.getLogResult())));
				}

				if (invokeResult.getFunctionError() != null) {
					throw new LambdaFunctionException(invokeResult.getFunctionError(), new String(invokeResult.getPayload().array()));
				}

				if (invokeResult.getStatusCode() == HttpURLConnection.HTTP_NO_CONTENT) {
					return "";
				}

				boolean isSuccess=false;
				String value = new String(invokeResult.getPayload().array(), "UTF-8");
				String jsonFormattedString = value.replaceAll("\\\\", "");
				jsonFormattedString=jsonFormattedString.replace("\"{", "{");
				jsonFormattedString=jsonFormattedString.replace("}\"", "}");

				/* 
	            //jsonFormattedString=jsonFormattedString.replaceFirst("\"", "");
	            if(jsonFormattedString.lastIndexOf("\"")>=0)
	            StringBuilder b= new StringBuilder(jsonFormattedString);
	            b.replace(jsonFormattedString.lastIndexOf("\""),jsonFormattedString.lastIndexOf("\"")+1, "");*/
				JSONArray jsonArray=new JSONArray(jsonFormattedString);
				String Success= jsonArray.get(0).toString();
				if(Success.equals("Success"))
				{	hawker = gson.fromJson( jsonArray.get(1).toString(), Hawker.class);

				mySharedpreference.saveHawkerInfo(mobile, 
						password, gson.toJson(hawker));
				//        	System.out.println("jsonArray" + jsonArray);
				}

				closeDialog();
				return Success;
			} catch (Exception e) {
				closeDialog();
				Utils.showToast(ActivityLogIn.this, "Please retry.");

				e.printStackTrace();
			}
			return "";
		}

	}

	void setData()
	{
		try{
			if(hawker!=null) {

				((ApplicationClass) ActivityLogIn.this.getApplicationContext()).setHawker(hawker);
				System.out.println("Login" + hawker);
				Intent browserIntent = new Intent(ActivityLogIn.this, ActivityDashBoard.class);
				startActivity(browserIntent);
				finish();
			}
			else
			{
				Utils.showToast(ActivityLogIn.this, "Please confirm number or password.");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}

