package jj.leasecalcapp;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentManager.BackStackEntry;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.util.Log;
import android.view.KeyEvent;

public class BackStackPopper implements OnKeyListener
{
    private Activity activity;
    
    public BackStackPopper(Activity activity)
    {
        this.activity = activity;
    }
    
    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) 
        {
            FragmentManager fragmentManager = activity.getFragmentManager();
            Log.d(BackStackPopper.class.getName(), "ENTRY COUNT WHEN CLICKED : " + fragmentManager.getBackStackEntryCount());
            
            int previousIndex = fragmentManager.getBackStackEntryCount() - 2;
            Log.d(BackStackPopper.class.getName(), "PREVIOUS DIALOG INDEX: " + previousIndex);
            if (previousIndex > -1) 
            {
                BackStackEntry backStackEntry = fragmentManager.getBackStackEntryAt(previousIndex);
                String fragmentName = backStackEntry.getName();

                DialogFragment dialogFragment = null;
                String dialogFragmentTag = null;
                
                if (fragmentName.equals(FragmentTags.WELCOME_DIALOG)) 
                {
                    dialogFragment = new WelcomeDialogFragment();
                    dialogFragmentTag = FragmentTags.WELCOME_DIALOG;
                }
                else if (fragmentName.equals(FragmentTags.START_DATE_DIALOG))
                {
                    dialogFragment = new StartDateDialogFragment();
                    dialogFragmentTag = FragmentTags.START_DATE_DIALOG;
                }
                
                if (null != dialogFragment && null != dialogFragmentTag) 
                {
                    fragmentManager.popBackStack();
                    dialog.dismiss();
                    
                    fragmentManager.beginTransaction()
                        .add(dialogFragment, dialogFragmentTag)
                        .commit();
                    
                    return true;
                }
            }
        }
        return false;
    }
}
