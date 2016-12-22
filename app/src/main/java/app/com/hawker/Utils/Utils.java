package app.com.hawker.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.view.Gravity;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {



	public static boolean isOnline(Context context) {
		ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		return conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected();
	}

	public static void showAlertBox(Context context, String msg, DialogInterface.OnClickListener okListener) {
		new AlertDialog.Builder(context).setTitle(null).setMessage(msg).setPositiveButton("OK", okListener).show().setCancelable(false);
	}

	public static void showToast(Context context, String message) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.BOTTOM, 0, 0);
		toast.show();
	}

	// get current time
	public static String getCurrentTime(Calendar calendar) {
		SimpleDateFormat df = new SimpleDateFormat("dd MMM HH:mm ");
		String formattedDate = df.format(calendar.getTime());
		return formattedDate;
	}

	public static String getDeviceId(Context context) {
		return Settings.Secure.getString(context.getContentResolver(),
				Settings.Secure.ANDROID_ID);
	}

}
