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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
    private EditText currentMileage;
    private View mileageDifferenceLine;
    private TextView mileageDifference;
    private Button resetSummation;

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
        currentMileage = (EditText) rootView.findViewById(R.id.current_mileage);
        mileageDifferenceLine = (View) rootView.findViewById(R.id.mileage_difference_line);
        mileageDifference = (TextView) rootView.findViewById(R.id.mileage_difference);
        resetSummation = (Button) rootView.findViewById(R.id.reset_summation);

        currentMileage.setHintTextColor(getResources().getColor(R.color.light_grey));
        currentMileage.setOnFocusChangeListener(new OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean hasFocus)
            {
                if (view.getId() == R.id.current_mileage)
                {
                    currentMileage.setHint(null);
                }
            }
        });
        currentMileage.setOnKeyListener(new OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER)
                {
                    int targetMileageInt = Integer.valueOf(targetMileage.getText().toString());
                    int currentMileageInt = Integer.valueOf(currentMileage.getText().toString());
                    int difference = targetMileageInt - currentMileageInt;
                    if (difference < 0)
                    {
                        mileageDifference.setTextColor(getResources().getColor(R.color.red));
                    }
                    else if (difference > 0)
                    {
                        mileageDifference.setTextColor(getResources().getColor(R.color.green));
                    }

                    mileageDifferenceLine.setVisibility(View.VISIBLE);
                    mileageDifference.setText(String.valueOf(Math.abs(difference)));
                    return true;
                }
                return false;
            }
        });

        resetSummation.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                resetSummation();
            }
        });

        if (!FirstTimeHelper.isFirstTime(getActivity()))
        {
            SharedPreferences preferences = PreferencesUtil.getSharedPreferences(getActivity());
            int purchaseDateDayOfMonth = preferences.getInt(PreferencesUtil.PURCHASE_DATE_DAY_OF_MONTH_KEY, 1);
            int purchaseDateMonth = preferences.getInt(PreferencesUtil.PURCHASE_DATE_MONTH_KEY, 1);
            int purchaseDateYear = preferences.getInt(PreferencesUtil.PURCHASE_DATE_YEAR_KEY, 1999);
            int yearlyMileage = preferences.getInt(PreferencesUtil.YEARLY_MILEAGE_KEY, 0);
            Results results = getResults(purchaseDateDayOfMonth, purchaseDateMonth, purchaseDateYear, yearlyMileage);
            updateUi(results);
        }

        return rootView;
    }

    private void resetSummation()
    {
        mileageDifference.setText(null);
        mileageDifferenceLine.setVisibility(View.INVISIBLE);
        currentMileage.setText(null);
        currentMileage.requestFocus();
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
        DateTime purchaseDateTime = new DateTime(year, month + 1, dayOfMonth, 11, 0);
        DateTime currentDateTime = new DateTime();

        int daysDelta = Days.daysBetween(purchaseDateTime, currentDateTime).getDays();
        int weeksDelta = Weeks.weeksBetween(purchaseDateTime, currentDateTime).getWeeks();
        int monthsDelta = Months.monthsBetween(purchaseDateTime, currentDateTime).getMonths();
        int yearsDelta = Years.yearsBetween(purchaseDateTime, currentDateTime).getYears();
        int targetMileageInt = getTargetMileage(yearlyMileage, daysDelta);

        logDetails(purchaseDateTime, currentDateTime, daysDelta, weeksDelta, monthsDelta, yearsDelta, targetMileageInt);
        return new Results(purchaseDateTime, currentDateTime, daysDelta, weeksDelta, monthsDelta, yearsDelta,
                targetMileageInt);
    }

    private int getTargetMileage(int yearlyMileage, int daysDelta)
    {
        double mileagePerDay = yearlyMileage / 365.0;
        return (int) (mileagePerDay * daysDelta);
    }

    private void logDetails(DateTime purchaseDateTime, DateTime currentDateTime, int daysDelta, int weeksDelta,
            int monthsDelta, int yearsDelta, int targetMileage)
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
