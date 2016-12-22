package app.com.hawker.AppFonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


public class TextViewSemiBoldOpensans extends TextView {

    public TextViewSemiBoldOpensans(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextViewSemiBoldOpensans(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public TextViewSemiBoldOpensans(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "OpenSans_Semibold.ttf");
        super.setTypeface(tf);
    }
}