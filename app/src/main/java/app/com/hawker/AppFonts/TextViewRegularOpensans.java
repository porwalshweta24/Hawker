package app.com.hawker.AppFonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


public class TextViewRegularOpensans extends TextView {

    public TextViewRegularOpensans(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextViewRegularOpensans(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public TextViewRegularOpensans(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "OpenSans_Regular.ttf");
        super.setTypeface(tf);
    }
}