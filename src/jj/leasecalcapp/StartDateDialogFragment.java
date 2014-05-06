package jj.leasecalcapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

public class StartDateDialogFragment extends DialogFragment
{
    private DatePicker datePicker;

    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.startDateTitle);
        builder.setView(getDatePicker());
        builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                int dayOfMonth = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();

                addSelectedDateToPreferences(dayOfMonth, month, year);

                getFragmentManager().beginTransaction()
                        .add(new YearlyMileageDialogFragment(), FragmentTags.YEARLY_MILEAGE_DIALOG)
                        .commit();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.cancel, new CancelDialogAction());

        return builder.create();
    }

    private View getDatePicker()
    {
        datePicker = new DatePicker(getActivity());
        datePicker.setSpinnersShown(true);
        datePicker.setCalendarViewShown(false);
        if (PreferencesUtil.doPreferencesExist(getActivity(), 
                PreferencesUtil.PURCHASE_DATE_DAY_OF_MONTH_KEY, 
                PreferencesUtil.PURCHASE_DATE_MONTH_KEY, 
                PreferencesUtil.PURCHASE_DATE_YEAR_KEY))
        {
            SharedPreferences preferences = PreferencesUtil.getSharedPreferences(getActivity());
            int dayOfMonth = preferences.getInt(PreferencesUtil.PURCHASE_DATE_DAY_OF_MONTH_KEY, 1);
            int month = preferences.getInt(PreferencesUtil.PURCHASE_DATE_MONTH_KEY, 1);
            int year = preferences.getInt(PreferencesUtil.PURCHASE_DATE_YEAR_KEY, 1999);
            datePicker.updateDate(year, month, dayOfMonth);
        }
        return datePicker;
    }

    private void addSelectedDateToPreferences(int dayOfMonth, int month, int year)
    {
        SharedPreferences.Editor editor = PreferencesUtil.getSharedPreferencesEditor(getActivity());
        editor.putInt(PreferencesUtil.PURCHASE_DATE_DAY_OF_MONTH_KEY, dayOfMonth);
        editor.putInt(PreferencesUtil.PURCHASE_DATE_MONTH_KEY, month);
        editor.putInt(PreferencesUtil.PURCHASE_DATE_YEAR_KEY, year);
        editor.commit();
        Log.d(StartDateDialogFragment.class.toString(), 
                String.format("adding to prefs: '%s' %d '%s' %d '%s' %d", 
                        PreferencesUtil.PURCHASE_DATE_DAY_OF_MONTH_KEY, dayOfMonth, 
                        PreferencesUtil.PURCHASE_DATE_MONTH_KEY, month, 
                        PreferencesUtil.PURCHASE_DATE_YEAR_KEY, year));
    }
}
