package jj.leasecalcapp;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.Weeks;
import org.joda.time.Years;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ResultsFragment extends Fragment
{
    private TextView purchaseDate;
    private TextView currentDate;
    private TextView timeDiffInDays;
    private TextView timeDiffInWeeks;
    private TextView timeDiffInMonths;
    private TextView timeDiffInYears;
    private TextView targetMileage;

    public ResultsFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_results, container, false);

        purchaseDate = (TextView) rootView.findViewById(R.id.purchaseDate);
        currentDate = (TextView) rootView.findViewById(R.id.currentDate);
        timeDiffInDays = (TextView) rootView.findViewById(R.id.timeDiffInDays);
        timeDiffInWeeks = (TextView) rootView.findViewById(R.id.timeDiffInWeeks);
        timeDiffInMonths = (TextView) rootView.findViewById(R.id.timeDiffInMonths);
        timeDiffInYears = (TextView) rootView.findViewById(R.id.timeDiffInYears);
        targetMileage = (TextView) rootView.findViewById(R.id.target_mileage);

        SharedPreferences preferences = PreferencesUtil.getSharedPreferences(getActivity());
        int purchaseDateDayOfMonth = preferences.getInt(PreferencesUtil.PURCHASE_DATE_DAY_OF_MONTH_KEY, 1);
        int purchaseDateMonth = preferences.getInt(PreferencesUtil.PURCHASE_DATE_MONTH_KEY, 1);
        int purchaseDateYear = preferences.getInt(PreferencesUtil.PURCHASE_DATE_YEAR_KEY, 1999);
        int yearlyMileage = preferences.getInt(PreferencesUtil.YEARLY_MILEAGE_KEY, 0);

        Results results = getResults(purchaseDateDayOfMonth, purchaseDateMonth, purchaseDateYear, yearlyMileage);
        updateUi(results);
        
        return rootView;
    }
    
    private void updateUi(Results results)
    {
        purchaseDate.setText(results.getPurchaseDate());
        currentDate.setText(results.getCurrentDate());
        timeDiffInDays.setText(String.valueOf(results.getDaysDelta()));
        timeDiffInWeeks.setText(String.valueOf(results.getWeeksDelta()));
        timeDiffInMonths.setText(String.valueOf(results.getMonthsDelta()));
        timeDiffInYears.setText(String.valueOf(results.getYearsDelta()));
        targetMileage.setText(String.valueOf(results.getTargetMileage()));
    }

    private Results getResults(int dayOfMonth, int month, int year, int yearlyMileage)
    {
        DateTime purchaseDateTime =
                new DateTime(year, month + 1, dayOfMonth, 11, 0);
        DateTime currentDateTime = new DateTime();

        int daysDelta = Days.daysBetween(purchaseDateTime, currentDateTime).getDays();
        int weeksDelta = Weeks.weeksBetween(purchaseDateTime, currentDateTime).getWeeks();
        int monthsDelta = Months.monthsBetween(purchaseDateTime, currentDateTime).getMonths();
        int yearsDelta = Years.yearsBetween(purchaseDateTime, currentDateTime).getYears();
        int targetMileageInt = getTargetMileage(yearlyMileage, daysDelta);

        logDetails(purchaseDateTime, currentDateTime, daysDelta, weeksDelta, monthsDelta, yearsDelta, targetMileageInt);
        return new Results(purchaseDateTime, currentDateTime, daysDelta, weeksDelta, monthsDelta, yearsDelta, targetMileageInt);
    }

    private int getTargetMileage(int yearlyMileage, int daysDelta)
    {
        double mileagePerDay = yearlyMileage / 365.0;
        return (int) (mileagePerDay * daysDelta);
    }

    private void logDetails(
            DateTime purchaseDateTime, DateTime currentDateTime,
            int daysDelta, int weeksDelta, int monthsDelta, int yearsDelta, int targetMileage)
    {
        String tag = ResultsFragment.class.toString();
        Log.d(tag, purchaseDateTime.toLocalDate().toString());
        Log.d(tag, currentDateTime.toLocalDate().toString());
        Log.d(tag, "timediffindays: " + daysDelta);
        Log.d(tag, "timediffinweeks: " + weeksDelta);
        Log.d(tag, "timediffinmonths: " + monthsDelta);
        Log.d(tag, "timediffinyears: " + yearsDelta);
        Log.d(tag, "targetmileage: " + targetMileage);
    }
}
