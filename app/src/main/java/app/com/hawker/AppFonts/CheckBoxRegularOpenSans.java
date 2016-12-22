package app.com.hawker.AppFonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckBox;

public class CheckBoxRegularOpenSans extends CheckBox {
    public CheckBoxRegularOpenSans(Context context) {
        super(context);
        init();
    }

    public CheckBoxRegularOpenSans(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CheckBoxRegularOpenSans(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CheckBoxRegularOpenSans(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "OpenSans_Regular.ttf");
        super.setTypeface(tf);
    }
}
