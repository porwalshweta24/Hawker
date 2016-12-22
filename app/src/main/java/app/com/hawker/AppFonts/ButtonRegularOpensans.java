package app.com.hawker.AppFonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;


public class ButtonRegularOpensans extends Button {

    public ButtonRegularOpensans(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ButtonRegularOpensans(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public ButtonRegularOpensans(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "OpenSans_Regular.ttf");
        super.setTypeface(tf);
    }
}