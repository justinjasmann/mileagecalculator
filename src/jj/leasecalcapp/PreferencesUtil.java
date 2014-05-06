package jj.leasecalcapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class PreferencesUtil
{
    private static final String SHARED_PREFERENCES_NAME = "leasecalcapp";

    public static final String PURCHASE_DATE_DAY_OF_MONTH_KEY = "dayOfMonth";
    public static final String PURCHASE_DATE_MONTH_KEY = "month";
    public static final String PURCHASE_DATE_YEAR_KEY = "year";
    public static final String YEARLY_MILEAGE_KEY = "yearlyMileage";
    
    private PreferencesUtil()
    {
    }

    public static SharedPreferences getSharedPreferences(Context context)
    {
        return context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getSharedPreferencesEditor(Context context)
    {
        return getSharedPreferences(context).edit();
    }

    public static boolean doPreferencesExist(Context context, String... keys)
    {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        for (String key : keys)
        {
            if (!preferences.contains(key))
            {
                Log.d(PreferencesUtil.class.toString(), String.format("preference %s does not exist: ", key));
                return false;
            }
        }
        return true;
    }
}
