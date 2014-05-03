package jj.leasecalcapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class YearlyKilometersDialogFragment extends DialogFragment
{
    private TextView textView;
    
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        builder.setTitle(R.string.yearlyKilometersTitle);
        builder.setView(getTextView());
        builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(getActivity(), "'Okay' was clicked", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.cancel, new CancelDialogAction(getActivity()));
        
        return builder.create();
    }
    
    private View getTextView()
    {
        textView = new TextView(getActivity());
        textView.setHint(R.string.yearlyKilometersHint);
        return textView;
    }
}
