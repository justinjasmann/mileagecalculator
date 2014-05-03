package jj.leasecalcapp;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivityFragment extends Fragment
{
    private Button button;
    
    public MainActivityFragment()
    {
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        
        button = (Button) rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DialogFragment dialog = new IntroDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString("title", "Welcome!");
                dialog.setArguments(bundle);
                dialog.show(getFragmentManager(), "dialogFragment");
            }
        });
        
        return rootView;
    }
}
