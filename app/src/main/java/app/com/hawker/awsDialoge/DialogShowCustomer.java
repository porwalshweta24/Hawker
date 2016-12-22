package app.com.hawker.awsDialoge;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import app.com.hawker.R;
import app.com.hawker.dao.Customer;

/**
 * Created by admin on 8/16/2016.
 */
public class DialogShowCustomer extends Dialog {

    TextView tv_cust_id_detail;
    TextView tv_cust_name_detail;
    TextView tv_cust_mobile_detail;
    TextView tv_cust_employment_detail;
    ImageView iv_dialog_cancel;

    private Activity activity;
    ArrayList<Customer> customerArrayList=new ArrayList<>();
    int position;
    public DialogShowCustomer(Activity activity, ArrayList<Customer> customerArrayList,int position) {
        super(activity);
        this.activity = activity;
        this.customerArrayList=customerArrayList;
        this.position=position;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_customer);
            init();
            getClick();
            getInfo();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    void init()
    {
        tv_cust_id_detail=(TextView) findViewById(R.id.tv_cust_id_detail);
        tv_cust_name_detail=(TextView) findViewById(R.id.tv_cust_name_detail);
        tv_cust_mobile_detail=(TextView) findViewById(R.id.tv_cust_mobile_detail);
        tv_cust_mobile_detail=(TextView) findViewById(R.id.tv_cust_mobile_detail);
        iv_dialog_cancel=(ImageView)findViewById(R.id.iv_dialog_cancel);

    }
    void getInfo()
    {
        try{
            tv_cust_id_detail.setText(customerArrayList.get(position).getCustomerId());
            tv_cust_name_detail.setText(customerArrayList.get(position).getName());
            tv_cust_mobile_detail.setText(customerArrayList.get(position).getMobileNum());
            tv_cust_employment_detail.setText(customerArrayList.get(position).getEmployment());

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    void getClick()
    {
        iv_dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
