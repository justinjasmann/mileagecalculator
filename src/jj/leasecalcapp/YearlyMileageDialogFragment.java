package jj.leasecalcapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class YearlyMileageDialogFragment extends DialogFragment
{
    private EditText editText;

    @Override
    public void onStart()
    {
        super.onStart();
        final AlertDialog alertDialog = (AlertDialog) getDialog();
        if (alertDialog != null)
        {
            Button positiveButton = (Button) alertDialog.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    String yearlyMileageAsString = editText.getText().toString();
                    if (!yearlyMileageAsString.isEmpty())
                    {
                        addYearlyMileageToPreferences(yearlyMileageAsString);
                        
                        getFragmentManager().beginTransaction()
                                .add(R.id.container, new ResultsFragment(), FragmentTags.RESULTS_FRAGMENT)
                                .commit();
                        
                        /*
                         * because we're overriding the default 'okay' functionality, 'dismiss' isn't called by default
                         */
                        alertDialog.dismiss();
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Yearly mileage cannot be empty.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        editText = getEditText();
        
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.yearlyMileageTitle);
        builder.setView(editText);
        builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                // intentionally left empty so we receive an 'okay' button
            }
        });

        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        dialog.setOnKeyListener(new BackStackPopper(getActivity()));
        return dialog;
    }

    private EditText getEditText()
    {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        EditText editText = (EditText) inflater.inflate(R.layout.yearlymileage_edittext, null);
        editText.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        if (!FirstTimeHelper.isFirstTime(getActivity()))
        {
            SharedPreferences preferences = PreferencesUtil.getSharedPreferences(getActivity());
            int yearlyMileage = preferences.getInt(PreferencesUtil.YEARLY_MILEAGE_KEY, 0);
            editText.setText(String.valueOf(yearlyMileage));
        }
        return editText;
    }

    private void addYearlyMileageToPreferences(String yearlyMileageAsString)
    {
        int yearlyMileage = Integer.parseInt(yearlyMileageAsString);
        SharedPreferences.Editor editor = PreferencesUtil.getSharedPreferencesEditor(getActivity());
        editor.putInt(PreferencesUtil.YEARLY_MILEAGE_KEY, yearlyMileage);
        editor.commit();
        Log.d(YearlyMileageDialogFragment.class.toString(),
                String.format("adding to prefs: '%s' %d", PreferencesUtil.YEARLY_MILEAGE_KEY, yearlyMileage));
    }
}
