package app.com.hawker.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import app.com.hawker.Models.MySharedpreference;
import app.com.hawker.R;
import app.com.hawker.Utils.Utils;
import app.com.hawker.dao.Customer;
import app.com.hawker.dao.Hawker;
import app.com.hawker.dao.LineInfo;

import app.com.hawker.BuildConfig;import app.com.hawker.BuildConfig;
/**
 * Created by admin on 8/6/2016.
 */
public class ActivityDashBoard extends AbstractBaseActivity implements View.OnClickListener {
    static Hawker hawker;
    ArrayList<LineInfo> lineInfoArrayList;
    ArrayList<Customer> customerArrayList;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_dashboard;
    }

    TextView tv_hawkerId;
    TextView tv_hawkerName;
    TextView tv_agencyName;
    TextView tv_mobile;
    TextView tv_address;
    TextView tv_title;
    ImageView iv_list_report;
    ImageView iv_logout;
    static int exitCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
//            setContentView(R.layout.activity_dashboard);
            init();
            getInfo();
            onCall();
            checkDataIfExist();
           callEmploymentValuesList();
           /* callEmploymentValuesList();
            callProfileValuesList();
            callPointNamesList();
            callDurationValuesList();
            callPaymentTypeValues();
            callSubscriptionTypeValues();*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void checkDataIfExist() {
        try {
            /*boolean is_exist=false;
            try{
				lineInfoArrayList = new  ArrayList<LineInfo>(((ApplicationClass) this.getApplicationContext()).getLineInfoArrayList());
				if(lineInfoArrayList!=null)
					is_exist=true;
			}catch(Exception e){
				e.printStackTrace();
			}
			if(is_exist)
			{
				Intent browserIntent = new Intent(ActivityDashBoard.this, Activity_ListInfo.class);
				startActivity(browserIntent);
			}*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void init() {
        tv_hawkerId = (TextView) findViewById(R.id.tv_hawkerId);
        tv_hawkerName = (TextView) findViewById(R.id.tv_hawkerName);
        tv_agencyName = (TextView) findViewById(R.id.tv_agencyName);
        tv_mobile = (TextView) findViewById(R.id.tv_mobile);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_list_report = (ImageView) findViewById(R.id.iv_list_report);
        iv_logout = (ImageView) findViewById(R.id.iv_logout);
        tv_title.setText("NewsHawker");
    }

    void getInfo() {
        try {

            hawker = new Hawker(((ApplicationClass) this.getApplicationContext()).getHawker());
            tv_hawkerId.setText(String.valueOf(hawker.getHawkerId()));
            tv_hawkerName.setText(hawker.getName() != null ? hawker.getName() : "");
            tv_agencyName.setText(hawker.getAgencyName().equalsIgnoreCase(null) ? hawker.getAgencyName() : "");//hawker.getAgencyName());
            tv_mobile.setText(hawker.getMobileNum() != null ? hawker.getMobileNum() : "");
            tv_address.setText(hawker.getNewHouseNum() != null ? hawker.getNewHouseNum() : "" + " " + hawker.getAddrLine1() != null ? hawker.getAddrLine1() : ""
                    + " " + hawker.getAddrLine2() != null ? hawker.getAddrLine2() : "" + " " + hawker.getCity() != null ? hawker.getCity() : ""
                    + " " + hawker.getState() != null ? hawker.getState() : "");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void onCall() {
        iv_list_report.setOnClickListener(this);
        iv_logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.iv_list_report:
                    ApplicationClass globalVariable = (ApplicationClass) getApplicationContext();
                    globalVariable.setBackActivity(false);
                    Intent intent = new Intent(ActivityDashBoard.this, Activity_ListInfo.class);
                    startActivity(intent);
//                    callAllFragments();

                    break;
                case R.id.iv_logout:
                    MySharedpreference mySharedpreference;
                    mySharedpreference = new MySharedpreference(this);
                    mySharedpreference.clearAll();

                    Intent browserIntent = new Intent(ActivityDashBoard.this, ActivityLogIn.class);
                    startActivity(browserIntent);
                    finish();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @Override
    public void onBackPressed() {

        CountDownTimer timer;
        exitCount++;

        if (exitCount == 1) {
            Toast.makeText(ActivityDashBoard.this, "Press again to exit",
                    Toast.LENGTH_SHORT).show();
        }
        if (exitCount == 2) {

            super.onBackPressed();
        } else {

            timer = new CountDownTimer(2000, 100) {

                @Override
                public void onTick(long millisUntilFinished) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub
                    if (exitCount != 2) {

                    }
                    exitCount = 0;

                }
            };
            timer.start();

        }
    }



    void callEmploymentValuesList() {
        new AsyncForListInfoEmploymentValuesList().execute();

    }

    void callProfileValuesList() {
        new AsyncForListInfoProfileValuesList().execute();

    }

    void callPointNamesList() {
        new AsyncForListInfoPointNamesList().execute();

    }

    void callDurationValuesList() {
        new AsyncForListInfoDurationValuesList().execute();

    }

    void callPaymentTypeValues() {
        new AsyncForListInfoPaymentTypeValues().execute();

    }

    void callSubscriptionTypeValues() {
        new AsyncForListInfoSubscriptionTypeValues().execute();

    }

    public class AsyncForListInfoEmploymentValuesList extends AsyncTask<String, Void, String> {
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
            showDialog(ActivityDashBoard.this);
        }

        protected String doInBackground(String... params) {
            String Result = "";

            try {
                Result=callEmploymentValuesListRequest();
            } catch (Exception e) {
                closeDialog();

            }

            return Result;

        }

        protected void onPostExecute(String Result) {
            closeDialog();

            System.out.println(Result);
        }
    }

    public class AsyncForListInfoSubscriptionTypeValues extends AsyncTask<String, Void, String> {
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
            showDialog(ActivityDashBoard.this);
        }

        protected String doInBackground(String... params) {
            String Result = "";

            try {
                Result=callSubscriptionTypeValuesRequest();

            } catch (Exception e) {
                closeDialog();

            }

            return Result;

        }

        protected void onPostExecute(String Result) {
            closeDialog();

            System.out.println(Result);
        }
    }

    public class AsyncForListInfoPaymentTypeValues extends AsyncTask<String, Void, String> {
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
            showDialog(ActivityDashBoard.this);
        }

        protected String doInBackground(String... params) {
            String Result = "";

            try {
                Result=callPaymentTypeValuesRequest();

            } catch (Exception e) {
                closeDialog();

            }

            return Result;

        }

        protected void onPostExecute(String Result) {
            closeDialog();

            System.out.println(Result);
        }
    }

    public class AsyncForListInfoDurationValuesList extends AsyncTask<String, Void, String> {
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
            showDialog(ActivityDashBoard.this);
        }

        protected String doInBackground(String... params) {
            String Result = "";

            try {
                Result=callDurationValuesListRequest();

            } catch (Exception e) {
                closeDialog();

            }

            return Result;

        }

        protected void onPostExecute(String Result) {
            closeDialog();

            System.out.println(Result);
        }
    }

    public class AsyncForListInfoPointNamesList extends AsyncTask<String, Void, String> {
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
            showDialog(ActivityDashBoard.this);
        }

        protected String doInBackground(String... params) {
            String Result = "";

            try {
                Result=callPointNamesListRequest();

            } catch (Exception e) {
                closeDialog();

            }

            return Result;

        }

        protected void onPostExecute(String Result) {
            closeDialog();

            System.out.println(Result);
        }
    }

    public class AsyncForListInfoProfileValuesList extends AsyncTask<String, Void, String> {
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
            showDialog(ActivityDashBoard.this);
        }

        protected String doInBackground(String... params) {
            String Result = "";

            try {
                Result=callProfileValuesListRequest();

            } catch (Exception e) {
                closeDialog();

            }

            return Result;

        }

        protected void onPostExecute(String Result) {
            closeDialog();

            System.out.println(Result);
        }
    }


    @SuppressWarnings({"rawtypes", "unchecked"})
    private String callEmploymentValuesListRequest() {
        AWSCredentials credentials = null;
        credentials = new BasicAWSCredentials(BuildConfig.ACCESS_KEY, BuildConfig.SECRET);
        AWSLambdaClient lambdaClient = new AWSLambdaClient(credentials);
        lambdaClient.setRegion(Region.getRegion(Regions.AP_NORTHEAST_1));

        try {
            InvokeRequest invokeRequest = new InvokeRequest();
            invokeRequest.setFunctionName("ConsolidatedSetupData");
            invokeRequest.setPayload(ByteBuffer.wrap(new Gson().toJson("").getBytes(StringUtils.UTF8)));
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
            HashMap<String,Object> objectHashMap = new HashMap<>();
           //
            objectHashMap = new Gson().fromJson(new BufferedReader(new InputStreamReader
                    (new ByteArrayInputStream(invokeResult.getPayload().array()))), HashMap.class);
            paymentValuesArr=new ArrayList<>();
            employmentValuesArr=new ArrayList<>();
            profileValuesArr=new ArrayList<>();
            durationValuesArr=new ArrayList<>();

            paymentValuesArr = ((ArrayList<String>) objectHashMap.get("paymentValues"));
            employmentValuesArr = ((ArrayList<String>) objectHashMap.get("employmentValues"));
            profileValuesArr = ((ArrayList<String>) objectHashMap.get("profileValues"));
            durationValuesArr = ((ArrayList<String>) objectHashMap.get("durationValues"));

            ((ApplicationClass) this.getApplicationContext()).setPaymentValuesArr(paymentValuesArr);
            ((ApplicationClass) this.getApplicationContext()).setEmploymentValuesArr(employmentValuesArr);
            ((ApplicationClass) this.getApplicationContext()).setProfileValuesArr(profileValuesArr);
            ((ApplicationClass) this.getApplicationContext()).setDurationValuesArr(durationValuesArr);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return "";

    }
    @SuppressWarnings({"rawtypes", "unchecked"})
    private String callProfileValuesListRequest() {
        AWSCredentials credentials = null;
        credentials = new BasicAWSCredentials(BuildConfig.ACCESS_KEY, BuildConfig.SECRET);
        AWSLambdaClient lambdaClient = new AWSLambdaClient(credentials);
        lambdaClient.setRegion(Region.getRegion(Regions.AP_NORTHEAST_1));

        try {
            InvokeRequest invokeRequest = new InvokeRequest();
            invokeRequest.setFunctionName("ProfileValuesList");
            invokeRequest.setPayload(ByteBuffer.wrap(new Gson().toJson("").getBytes(StringUtils.UTF8)));
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
            ArrayList<String> ProfileValuesListArr = new ArrayList<>();
            ProfileValuesListArr = new Gson().fromJson(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(invokeResult.getPayload().array()))), ArrayList.class);
            Utils.showToast(this, "ProfileValuesListArr done");


        } catch (Exception e) {
            e.printStackTrace();
        }


        return "";

    }
    @SuppressWarnings({"rawtypes", "unchecked"})
    private String callPointNamesListRequest() {
        AWSCredentials credentials = null;
        credentials = new BasicAWSCredentials(BuildConfig.ACCESS_KEY, BuildConfig.SECRET);
        AWSLambdaClient lambdaClient = new AWSLambdaClient(credentials);
        lambdaClient.setRegion(Region.getRegion(Regions.AP_NORTHEAST_1));

        try {
            InvokeRequest invokeRequest = new InvokeRequest();
            invokeRequest.setFunctionName("PointNamesList");
            invokeRequest.setPayload(ByteBuffer.wrap(new Gson().toJson("").getBytes(StringUtils.UTF8)));
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
            ArrayList<String> PointNamesListArr = new ArrayList<>();
            PointNamesListArr = new Gson().fromJson(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(invokeResult.getPayload().array()))), ArrayList.class);
            Utils.showToast(this, "PointNamesListArr done");


        } catch (Exception e) {
            e.printStackTrace();
        }


        return "";

    }
    @SuppressWarnings({"rawtypes", "unchecked"})
    private String callDurationValuesListRequest() {
        AWSCredentials credentials = null;
        credentials = new BasicAWSCredentials(BuildConfig.ACCESS_KEY, BuildConfig.SECRET);
        AWSLambdaClient lambdaClient = new AWSLambdaClient(credentials);
        lambdaClient.setRegion(Region.getRegion(Regions.AP_NORTHEAST_1));

        try {
            InvokeRequest invokeRequest = new InvokeRequest();
            invokeRequest.setFunctionName("DurationValuesList");
            invokeRequest.setPayload(ByteBuffer.wrap(new Gson().toJson("").getBytes(StringUtils.UTF8)));
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
            ArrayList<String> DurationValuesListArr = new ArrayList<>();
            DurationValuesListArr = new Gson().fromJson(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(invokeResult.getPayload().array()))), ArrayList.class);
            Utils.showToast(this, "DurationValuesListArr done");


        } catch (Exception e) {
            e.printStackTrace();
        }


        return "";

    }
    @SuppressWarnings({"rawtypes", "unchecked"})
    private String callPaymentTypeValuesRequest() {
        AWSCredentials credentials = null;
        credentials = new BasicAWSCredentials(BuildConfig.ACCESS_KEY, BuildConfig.SECRET);
        AWSLambdaClient lambdaClient = new AWSLambdaClient(credentials);
        lambdaClient.setRegion(Region.getRegion(Regions.AP_NORTHEAST_1));

        try {
            InvokeRequest invokeRequest = new InvokeRequest();
            invokeRequest.setFunctionName("PaymentTypeValues");
            invokeRequest.setPayload(ByteBuffer.wrap(new Gson().toJson("").getBytes(StringUtils.UTF8)));
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
            ArrayList<String> PaymentTypeValuesArr = new ArrayList<>();
            PaymentTypeValuesArr = new Gson().fromJson(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(invokeResult.getPayload().array()))), ArrayList.class);
            Utils.showToast(this, "PaymentTypeValuesArr done");


        } catch (Exception e) {
            e.printStackTrace();
        }


        return "";

    }
    @SuppressWarnings({"rawtypes", "unchecked"})
    private String callSubscriptionTypeValuesRequest() {
        AWSCredentials credentials = null;
        credentials = new BasicAWSCredentials(BuildConfig.ACCESS_KEY, BuildConfig.SECRET);
        AWSLambdaClient lambdaClient = new AWSLambdaClient(credentials);
        lambdaClient.setRegion(Region.getRegion(Regions.AP_NORTHEAST_1));

        try {
            InvokeRequest invokeRequest = new InvokeRequest();
            invokeRequest.setFunctionName("SubscriptionTypeValues");
            invokeRequest.setPayload(ByteBuffer.wrap(new Gson().toJson("").getBytes(StringUtils.UTF8)));
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
            ArrayList<String> SubscriptionTypeValuesArr = new ArrayList<>();
            SubscriptionTypeValuesArr = new Gson().fromJson(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(invokeResult.getPayload().array()))), ArrayList.class);
            Utils.showToast(this, "SubscriptionTypeValuesArr done");


        } catch (Exception e) {
            e.printStackTrace();
        }


        return "";

    }

}
