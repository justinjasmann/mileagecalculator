package jj.leasecalcapp;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

public class CancelDialogAction implements DialogInterface.OnClickListener
{
    private Context context;
    
    public CancelDialogAction(Context context)
    {
        this.context = context;
    }
    
    @Override
    public void onClick(DialogInterface dialog, int which)
    {
        Toast.makeText(context, "'Cancel' was selected", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }
}
