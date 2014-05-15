package jj.leasecalcapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.util.Log;
import android.view.KeyEvent;

public class ActivityKiller implements DialogInterface.OnKeyListener
{
    private Activity activity;

    public ActivityKiller(Activity activity)
    {
        this.activity = activity;
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            Log.d(ActivityKiller.class.getName(), "KILLING ACTIVITY");
            dialog.dismiss();
            activity.finish();
            return true;
        }
        return false;
    }
}
