package jj.leasecalcapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;

public class YearlyMileageDialogFragment extends DialogFragment
{
    private EditText editText;

    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.yearlyKilometersTitle);
        builder.setView(getEditText());
        builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragment = fragmentManager.findFragmentByTag(FragmentTags.MAIN_ACTIVITY_FRAGMENT);

                /* fix so null pointers don't occur */
                addYearlyMileageToPreferences(editText.getText().toString());

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.remove(fragment);
                transaction.add(R.id.container, new ResultsFragment());
                transaction.commit();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.cancel, new CancelDialogAction(getActivity()));

        return builder.create();
    }

    private EditText getEditText()
    {
        editText = new EditText(getActivity());
        editText.setLayoutParams(getEditTextLayoutParams());
        editText.setGravity(Gravity.CENTER);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        editText.setBackground(null);
        editText.setTextSize(TypedValue.DENSITY_DEFAULT, 55);
        if (PreferencesUtil.doPreferencesExist(getActivity(), PreferencesUtil.YEARLY_MILEAGE_KEY)) 
        {
            SharedPreferences preferences = PreferencesUtil.getSharedPreferences(getActivity());
            int yearlyMileage = preferences.getInt(PreferencesUtil.YEARLY_MILEAGE_KEY, 0);
            editText.setText(String.valueOf(yearlyMileage));
        }
        return editText;
    }

    private LinearLayout.LayoutParams getEditTextLayoutParams()
    {
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        int marginInPixels = dpToPixels(5);
        layoutParams.setMargins(marginInPixels, marginInPixels, marginInPixels, marginInPixels);
        return layoutParams;
    }

    private int dpToPixels(int dp)
    {
        Resources resources = getActivity().getResources();
        float density = resources.getDisplayMetrics().density;
        return (int) (dp * density);
    }

    private void addYearlyMileageToPreferences(String yearlyMileageAsString)
    {
        int yearlyMileage = Integer.parseInt(yearlyMileageAsString);
        SharedPreferences.Editor editor = PreferencesUtil.getSharedPreferencesEditor(getActivity());
        editor.putInt(PreferencesUtil.YEARLY_MILEAGE_KEY, yearlyMileage);
        editor.commit();
        Log.d(YearlyMileageDialogFragment.class.toString(),
                String.format("adding yearly mileage to prefs: yearlyMileage %d", yearlyMileage));
    }
}
