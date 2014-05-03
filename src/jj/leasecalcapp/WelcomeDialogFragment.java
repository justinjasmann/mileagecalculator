package jj.leasecalcapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class WelcomeDialogFragment extends DialogFragment
{
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle(R.string.welcomeTitle);
        dialogBuilder.setMessage(R.string.welcomeText);
        dialogBuilder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                DialogFragment datePickerDialog = new StartDateDialogFragment();
                datePickerDialog.show(getFragmentManager(), FragmentTags.START_DATE_DIALOG);
                dialog.dismiss();
            }
        });
        dialogBuilder.setNegativeButton(R.string.cancel, new CancelDialogAction(getActivity()));
        return dialogBuilder.create();
    }
}
