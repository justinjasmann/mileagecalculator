package jj.leasecalcapp;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ResultsFragment extends Fragment
{
    public ResultsFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_results, container, false);
        
        SharedPreferences preferences = PreferencesUtil.getSharedPreferences(getActivity());
        int dayOfMonth = preferences.getInt(PreferencesUtil.DAY_OF_MONTH_KEY, 1);
        int month = preferences.getInt(PreferencesUtil.MONTH_KEY, 1);
        int year = preferences.getInt(PreferencesUtil.YEAR_KEY, 1999);
        int yearlyMileage = preferences.getInt(PreferencesUtil.YEARLY_MILEAGE_KEY, 0);
        
        Log.d(ResultsFragment.class.toString(), 
                String.format("dayofmonth: %d, month: %d, year: %d, yearlymileage: %d", dayOfMonth, month, year, yearlyMileage));
        
        return rootView;
    }
}
