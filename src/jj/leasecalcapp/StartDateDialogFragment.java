package jj.leasecalcapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
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
                Log.d(StartDateDialogFragment.class.toString(), 
                        String.format("dayOfMonth: %d, month: %d, year: %d", dayOfMonth, month, year));
                
                DialogFragment yearlyKilometersDialog = new YearlyKilometersDialogFragment();
                yearlyKilometersDialog.show(getFragmentManager(), FragmentTags.YEARLY_KILOMETERS_DIALOG);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.cancel, new CancelDialogAction(getActivity()));

        return builder.create();
    }

    private View getDatePicker()
    {
        datePicker = new DatePicker(getActivity());
        datePicker.setSpinnersShown(true);
        datePicker.setCalendarViewShown(false);
        return datePicker;
    }
}
