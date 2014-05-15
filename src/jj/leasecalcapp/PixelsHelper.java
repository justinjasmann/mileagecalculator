package jj.leasecalcapp;

import android.content.Context;
import android.content.res.Resources;

public class PixelsHelper
{
    private PixelsHelper() {};
    
    public static int dpToPixels(Context context, int dp)
    {
        Resources resources = context.getResources();
        float density = resources.getDisplayMetrics().density;
        return (int) (dp * density);
    }
}
