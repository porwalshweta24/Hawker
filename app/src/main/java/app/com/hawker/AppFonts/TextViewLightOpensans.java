package app.com.hawker.AppFonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


public class TextViewLightOpensans extends TextView {

    public TextViewLightOpensans(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextViewLightOpensans(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public TextViewLightOpensans(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "OpenSans_Light.ttf");
        super.setTypeface(tf);
    }
}
