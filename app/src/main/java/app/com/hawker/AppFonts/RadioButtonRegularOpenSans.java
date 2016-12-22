package app.com.hawker.AppFonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RadioButton;


public class RadioButtonRegularOpenSans extends RadioButton {
    public RadioButtonRegularOpenSans(Context context) {
        super(context);
        init();
    }

    public RadioButtonRegularOpenSans(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RadioButtonRegularOpenSans(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public RadioButtonRegularOpenSans(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "OpenSans_Regular.ttf");
        super.setTypeface(tf);
    }
}
