package jj.leasecalcapp;

import android.content.Context;

public class FirstTimeHelper
{
    private FirstTimeHelper()
    {
    };

    public static boolean isFirstTime(Context context)
    {
        if (PreferencesUtil.doPreferencesExist(context, PreferencesUtil.PURCHASE_DATE_DAY_OF_MONTH_KEY,
                PreferencesUtil.PURCHASE_DATE_MONTH_KEY, PreferencesUtil.PURCHASE_DATE_YEAR_KEY,
                PreferencesUtil.YEARLY_MILEAGE_KEY))
        {
            return false;
        }
        return true;
    }
}
