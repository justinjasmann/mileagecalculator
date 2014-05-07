package jj.leasecalcapp;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null)
        {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            if (!PreferencesUtil.doPreferencesExist(this, PreferencesUtil.PURCHASE_DATE_DAY_OF_MONTH_KEY,
                    PreferencesUtil.PURCHASE_DATE_MONTH_KEY, PreferencesUtil.PURCHASE_DATE_YEAR_KEY,
                    PreferencesUtil.YEARLY_MILEAGE_KEY))
            {
                transaction.add(new WelcomeDialogFragment(), FragmentTags.WELCOME_DIALOG);
            }
            else
            {
                transaction.add(R.id.container, new ResultsFragment(), FragmentTags.RESULTS_FRAGMENT);
            }
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
