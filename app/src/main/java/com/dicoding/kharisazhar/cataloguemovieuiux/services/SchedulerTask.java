package com.dicoding.kharisazhar.cataloguemovieuiux.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dicoding.kharisazhar.cataloguemovieuiux.DetailMovieActivity;
import com.dicoding.kharisazhar.cataloguemovieuiux.R;
import com.dicoding.kharisazhar.cataloguemovieuiux.adapter.AdapterMovie;
import com.dicoding.kharisazhar.cataloguemovieuiux.connection.ApiServices;
import com.dicoding.kharisazhar.cataloguemovieuiux.connection.RetroConfig;
import com.dicoding.kharisazhar.cataloguemovieuiux.model.ModelListMovie;
import com.dicoding.kharisazhar.cataloguemovieuiux.model.Result;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchedulerTask extends JobService {

    public static final String TAG = SchedulerTask.class.getSimpleName();

    private String PARCEL_OBJECT = "parcel_object";
    String api_key = "da2c66905b58cbb6b972e167cd56310f";

    ArrayList<Result> mData = new ArrayList<>();

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "onStartJob() Executed");
        loadData(params);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "onStopJob() Executed");
        return true;
    }

    private void loadData(final JobParameters job){
        ApiServices api = RetroConfig.getApiServices();
        final Call<ModelListMovie> call = api.getUpComing(api_key,"en-US");
        call.enqueue(new Callback<ModelListMovie>() {
            @Override
            public void onResponse(Call<ModelListMovie> call, Response<ModelListMovie> response) {
                try {
                    mData = (ArrayList<Result>) response.body().getResults();
                    Result data = new Result();
//                progressBar.setVisibility(View.GONE);

                    String title = mData.get(0).getTitle();
                    int notifId = mData.get(0).getId();

                    showNotification(getApplicationContext(), title,
                            mData.get(0).getTitle(), notifId, data);
                    jobFinished(job, false);
                } catch (Exception e){
                    jobFinished(job, true);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ModelListMovie> call, Throwable t) {
                Toast.makeText(SchedulerTask.this, ""+t, Toast.LENGTH_SHORT).show();
                jobFinished(job, false);
            }
        });
    }

    private void showNotification(Context context, String title, String message, int notifId, Result film) {
        /*private void showNotification(Context context, String title, String message, int notifId) {*/
        Intent intent = new Intent(context, DetailMovieActivity.class);
        intent.putExtra(PARCEL_OBJECT, film);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_reply_black_24dp)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        notificationManagerCompat.notify(notifId, builder.build());
    }
}
