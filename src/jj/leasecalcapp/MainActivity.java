package jj.leasecalcapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity
{
    private static final int SETTINGS_ACTIVITY_REQUEST = 6459;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        
        if (savedInstanceState == null)
        {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            if (FirstTimeHelper.isFirstTime(this))
            {
                WelcomeDialogFragment welcomeDialogFragment = new WelcomeDialogFragment();
                welcomeDialogFragment.setCancelable(false);
                transaction.add(new WelcomeDialogFragment(), FragmentTags.WELCOME_DIALOG);
            }
            else
            {
                transaction.add(R.id.container, new ResultsFragment(), FragmentTags.RESULTS_FRAGMENT);
            }
            transaction.addToBackStack(FragmentTags.WELCOME_DIALOG);
            transaction.commit();
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivityForResult(intent, SETTINGS_ACTIVITY_REQUEST);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == SETTINGS_ACTIVITY_REQUEST && resultCode == RESULT_OK)
        {
            FragmentManager fragmentManager = getFragmentManager();
            Fragment resultsFragment = fragmentManager.findFragmentByTag(FragmentTags.RESULTS_FRAGMENT);
            if (null != resultsFragment)
            {
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(resultsFragment);
                
                WelcomeDialogFragment welcomeDialogFragment = new WelcomeDialogFragment();
                welcomeDialogFragment.setCancelable(false);
                fragmentTransaction.add(welcomeDialogFragment, FragmentTags.WELCOME_DIALOG);
                fragmentTransaction.addToBackStack(FragmentTags.WELCOME_DIALOG);
                fragmentTransaction.commit();
            }
        }
    }
}
