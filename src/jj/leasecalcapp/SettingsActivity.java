package jj.leasecalcapp;

import android.app.Activity;
import android.os.Bundle;

public class SettingsActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
            .add(android.R.id.content, new SettingsFragment())
            .commit();
    }
}
