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
                StartDateDialogFragment startDateDialogFragment = new StartDateDialogFragment();
                startDateDialogFragment.setCancelable(false);
                getFragmentManager().beginTransaction()
                        .add(startDateDialogFragment, FragmentTags.START_DATE_DIALOG)
                        .commit();
            }
        });

        Dialog dialog = dialogBuilder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(new ActivityKiller(getActivity()));
        return dialog;
    }
}
