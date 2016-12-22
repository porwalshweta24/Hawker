package app.com.hawker.Models;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MySharedpreference {
	public static String USER_MOBILE = "Mobile";
    public static String USER_PWD = "Password";
    public static String OBJ_HAWKER = "Hawker";
    private Context context;

    private static final String APP_SHARED_PREFS = MySharedpreference.class
            .getSimpleName(); // Name of the file -.xml
    private SharedPreferences _sharedPrefs;
    private Editor _prefsEditor;

    public MySharedpreference(Context ctx) {
        this.context = ctx;
        this._sharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS,
                Context.MODE_PRIVATE);
        this._prefsEditor = _sharedPrefs.edit();
    }

    public void saveHawkerInfo(final String mobile, final String password, final String hawkerObj) {
        _prefsEditor.putString(USER_MOBILE, mobile);
        _prefsEditor.putString(USER_PWD, password);
        _prefsEditor.putString(OBJ_HAWKER, hawkerObj);
        _prefsEditor.commit();

    }
    public void clearAll()
    {
    	 _prefsEditor.remove(USER_MOBILE);
    	 _prefsEditor.remove(USER_PWD);
    	 _prefsEditor.remove(OBJ_HAWKER);
    	 _prefsEditor.commit();
    }
    
    public String getUserMobile(){
        return _sharedPrefs.getString(USER_MOBILE, null);
    }
    public String getUserPassword(){
        return _sharedPrefs.getString(USER_PWD, null);
    }

    public String getHawkerObj(){
        return _sharedPrefs.getString(OBJ_HAWKER, null);
    }



    public void logout() {
        this._prefsEditor.clear().commit();
    }

    
    
}
