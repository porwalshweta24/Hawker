package app.com.hawker.awsDialoge;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.com.hawker.R;
import app.com.hawker.dao.Billing;

public class DialogShowDatesList extends Dialog {

    private Activity activity;
    
    ArrayList<Billing> billingList;
    
    private ImageView imgCloseImagePopUp;
    ListView listView_info_dates;
    OnMyDialogResult mDialogResult; // the callback

    
    public DialogShowDatesList(Activity activity, ArrayList<Billing> billingList) {
        super(activity);
        this.activity = activity;
        this.billingList = billingList;
       
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_dateinfo);

        initView();

    }

    private void initView() {
    	listView_info_dates = (ListView) findViewById(R.id.listView_info_dates);
    	imgCloseImagePopUp = (ImageView) findViewById(R.id.imgCloseImagePopUp);
    	listView_info_dates.setAdapter(new MyCustomAdapter(activity, billingList));

    	imgCloseImagePopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
	public class MyCustomAdapter extends ArrayAdapter<Billing> {

		public class ViewHolder {

			public TextView tv_lineNum;


		}

		public MyCustomAdapter(Context context, List<Billing> objects) {
			super(context, R.layout.inflate_header, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View vi;
			ViewHolder holder;

			if(convertView == null) {
				vi = LayoutInflater.from(getContext()).inflate(R.layout.inflate_header, parent, false);
				holder = new ViewHolder();
				vi.setTag(holder);
			} else {
				vi = convertView;
				holder = (ViewHolder) vi.getTag();
			}
			try{
				/****** View Holder Object to contain tabitem.xml file elements ******/
				Billing country = getItem(position);

				holder.tv_lineNum=(TextView) vi.findViewById(R.id.tv_lineNum);
				
				holder.tv_lineNum.setText(""+country.getInvoiceDate());
				


				holder.tv_lineNum.setTag(position);
				holder.tv_lineNum.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						int position= (int) v.getTag();
				
						 mDialogResult.finish(getItem(position));
						 dismiss();

					}
				});
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			return vi;
		}
	}
	public void setDialogResult(OnMyDialogResult dialogResult) {
        mDialogResult = dialogResult;
    }

    public interface OnMyDialogResult {
        void finish(Billing billingData);
    }
}