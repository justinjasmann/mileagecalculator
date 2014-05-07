package jj.leasecalcapp;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class SettingsFragment extends PreferenceFragment
{
    private Button resetData;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        LinearLayout linearLayout = (LinearLayout) super.onCreateView(inflater, container, savedInstanceState);
        resetData = new Button(getActivity().getApplicationContext());
        resetData.setText(R.string.button_reset);
        resetData.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                PreferencesUtil.resetAll(getActivity().getApplicationContext());
                getActivity().finish();
            }
        });
        
        linearLayout.addView(resetData);
        return linearLayout;
    }
}
