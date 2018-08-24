package com.dicoding.kharisazhar.cataloguemovieuiux.preference;

import android.app.AlarmManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.dicoding.kharisazhar.cataloguemovieuiux.R;
import com.dicoding.kharisazhar.cataloguemovieuiux.services.SchedulerTask;
import com.dicoding.kharisazhar.cataloguemovieuiux.util.AlarmReceiver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindString;
import butterknife.ButterKnife;

public class MyPreferenceFragment extends PreferenceFragment
        implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {

    @BindString(R.string.key_reminder_daily)
    String reminder_daily;

    @BindString(R.string.key_reminder_upcoming)
    String reminder_upcoming;

    @BindString(R.string.key_language)
    String setting_locale;

    private String mDaily = "07:00";
    private String mNowMovie = "08:00";

    private AlarmReceiver alarmReceiver = new AlarmReceiver();

    private int jobId = 10;

    private Context mContext;

    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        ButterKnife.bind(this, getActivity());

        findPreference(reminder_daily).setOnPreferenceChangeListener(this);
        findPreference(reminder_upcoming).setOnPreferenceChangeListener(this);
        findPreference(setting_locale).setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        String key = preference.getKey();
        boolean isOn = (boolean) o;

        if (key.equals(reminder_daily)) {
            if (isOn) {
                alarmReceiver.setRepeatingAlarm(getActivity(), alarmReceiver.TYPE_REPEATING,
                        mDaily, getString(R.string.daily_reminder_notif));
            } else {
                alarmReceiver.cancelAlarm(getActivity(), alarmReceiver.TYPE_REPEATING);
            }

            Toast.makeText(mContext, getString(R.string.daily_reminder_notif)
                    + " " + (isOn ? getString(R.string.notif_actived) : getString(R.string.notif_deactived)), Toast.LENGTH_SHORT).show();
            return true;
        }

        if (key.equals(reminder_upcoming)) {
            if (isOn) {
                String dateNow = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                alarmReceiver.setOneTimeAlarm(getActivity(), alarmReceiver.TYPE_ONE_TIME, dateNow,
                        mNowMovie, getString(R.string.daily_reminder_upcoming));
            } else cancelJob();

            Toast.makeText(mContext, getString(R.string.daily_reminder_upcoming)
                    + " " + (isOn ? getString(R.string.notif_actived) : getString(R.string.notif_deactived)), Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();

        if (key.equals(setting_locale)) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
            return true;
        }

        return false;
    }

    public void startJob() {
        ComponentName mServiceComponent = new ComponentName(mContext, SchedulerTask.class);

        JobInfo.Builder builder = new JobInfo.Builder(jobId, mServiceComponent);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        builder.setRequiresDeviceIdle(false);
        builder.setRequiresCharging(false);
        builder.setPeriodic(AlarmManager.INTERVAL_DAY);

        JobScheduler jobScheduler = (JobScheduler) mContext.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());
    }

    private void cancelJob() {
        JobScheduler tm = (JobScheduler) mContext.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.cancel(jobId);
    }
}
