package app.survey.android.feedbackapp.util;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.survey.android.feedbackapp.R;

public class FontManager {
    private static final String TAG = FontManager.class.getSimpleName();

    private static Typeface twCenMtBold;
    private static Typeface twCenMtRegular;
    // singleton instance
    private static FontManager mInstance;

    protected FontManager() {
        // enforce singleton
        super();
    }

    public static synchronized FontManager getInstance() {
        if (mInstance == null) {
            mInstance = new FontManager();
        }
        return mInstance;
    }

    public static synchronized Typeface getFont(Fonts fontType, Context context) {
        switch (fontType) {
            case TW_CENT_MT_BOLD:
                if (twCenMtBold == null) {
                    twCenMtBold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.tw_cen_mt_bold));
                }
                return twCenMtBold;
            case TW_CENT_MT_REGULAR:
                if (twCenMtRegular == null) {
                    twCenMtRegular = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.tw_cen_mt_regular));
                }
                return twCenMtRegular;
            default:
                return null;
        }
    }

    public static void replaceFonts(ViewGroup viewTree, Typeface typeface) {
        View child;
        for (int i = 0; i < viewTree.getChildCount(); ++i) {
            child = viewTree.getChildAt(i);
            if (child instanceof ViewGroup) {
                replaceFonts((ViewGroup) child, typeface);
            } else if (child instanceof TextView) {
                ((TextView) child).setTypeface(typeface);
            }
        }
    }

    public enum Fonts {
        TW_CENT_MT_BOLD,
        TW_CENT_MT_REGULAR
    }
}
