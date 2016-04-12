package app.survey.android.feedbackapp.util;

import android.content.Context;
import android.graphics.Typeface;

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
        if (FontManager.mInstance == null) {
            FontManager.mInstance = new FontManager();
        }
        return FontManager.mInstance;
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

    public static enum Fonts {
        TW_CENT_MT_BOLD,
        TW_CENT_MT_REGULAR
    }
}
