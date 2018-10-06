package com.fahim.sunshine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.annotation.RequiresApi;
import android.view.MenuItem;

@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private SharedPreferences preferences;

    private SwitchPreference geolocationEnabledPreference;
    private EditTextPreference manualLocationPreference;

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.app_preferences);

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        geolocationEnabledPreference = (SwitchPreference) findPreference(getString(R.string.pref_geolocation_enabled));
        manualLocationPreference = (EditTextPreference) findPreference(getString(R.string.pref_manual_location));

        bindPreferenceSummaryToValue(manualLocationPreference);
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_temperature_unit)));

        PreferenceManager.getDefaultSharedPreferences(getActivity()).registerOnSharedPreferenceChangeListener(this);

        onSharedPreferenceChanged(null, null);

        if(!preferences.getBoolean(getString(R.string.pref_needs_setup), false)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(getString(R.string.pref_needs_setup), false);
            editor.apply();
        }

        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            startActivity(new Intent(getActivity(), MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (geolocationEnabledPreference.isChecked()) {
            manualLocationPreference.setEnabled(false);
        } else {
            manualLocationPreference.setEnabled(true);
        }
    }

    private void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(this);
        onPreferenceChange(preference, preferences.getString(preference.getKey(), null));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        String stringValue = value.toString();

        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int index = listPreference.findIndexOfValue(stringValue);

            preference.setSummary(index >= 0 ? listPreference.getEntries()[index] : null);

        } else if (preference instanceof EditTextPreference) {
            preference.setSummary(stringValue);
        }

        return true;
    }
}
