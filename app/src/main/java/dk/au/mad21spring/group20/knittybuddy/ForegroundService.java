package dk.au.mad21spring.group20.knittybuddy;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dk.au.mad21spring.group20.knittybuddy.model.Project;
import dk.au.mad21spring.group20.knittybuddy.repository.Repository;

//Lecture 5
public class ForegroundService extends Service {

    //attributes
    private static final String SERVICE = "ForegroundService";
    private static final String CHANNEL = "serviceChannel";
    private static final int NOTIFICATION_ID = 75;
    public static final String extraKeyBroadcastResult = "EXTRA_KEY_BROADCAST_RESULT";
    private boolean serviceStarted = false;

    ExecutorService executor;
    Repository repository;


    //constructor
    public ForegroundService() { }

    //onCreate is called when service is first called
    @Override
    public void onCreate(){
        super.onCreate();
        Log.d(SERVICE, "Foreground service created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(SERVICE, "Foreground service started");

        //Version check
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL, "Foreground Service", NotificationManager.IMPORTANCE_LOW);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }

        //build the notification
        Notification notification = new NotificationCompat.Builder(this, CHANNEL)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Will be updated with random projects for inspiration")
                .setSmallIcon(R.drawable.notification_icon_foreground)
                .setTicker("")
                .build();
        
        //call to startForeground will promote this Service to a foreground service (needs manifest permission)
        //also require the notification to be set, so that user can always see that Service is running in the background
        startForeground(NOTIFICATION_ID, notification);

        //this method starts recursive foreground work
        startForegroundRecursion();


        //returning START_STICKY will make the Service restart again eventually if it gets killed off (e.g. due to resources)
        return START_STICKY;
    }

    private void startForegroundRecursion()
    {
        Log.d(SERVICE, "Foreground recursion [1] started");
        if (!serviceStarted)
        {
            serviceStarted = true;
            foregroundRecursion();
        }
    }

    //method runs recursively (calls itself in the end) as long as started==true
    private void foregroundRecursion() {
        Log.d(SERVICE, "Foreground recursion [2] started");
        //lazy creation of ExecutorService running as a single threaded executor
        //this executor will allow us to do work off the main thread
        if(executor == null) {
            executor = Executors.newSingleThreadExecutor();
        }

        if (repository == null) {
            repository = Repository.getRepositoryInstance();
        }

        //submit a new Runnable (implement onRun() ) to the executor - code will run on other thread
        executor.submit(new Runnable() {
            @Override
            public void run() {
                Log.d(SERVICE, "Foreground started RUN");
                try
                {

                    Thread.sleep(20000);
                    Project rndProject = repository.getRandomProject();
                    Log.d(SERVICE, "Random project");


                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    //Version check
                    if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
                        NotificationChannel channel = new NotificationChannel(CHANNEL, "Foreground Service", NotificationManager.IMPORTANCE_DEFAULT);
                        notificationManager.createNotificationChannel(channel);
                    }

                    //build the notification
                    Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL)
                            .setContentTitle(getString(R.string.notiPublishedProjects))
                            .setContentText(rndProject.getName() + getString(R.string.notiBy) + rndProject.getUserId())
                            .setSmallIcon(R.drawable.notification_icon_foreground)
                            .setTicker("")
                            .build();

                    //NotificationManagerCompat nmc = NotificationManagerCompat.from(getApplicationContext());
                    notificationManager.notify(70, notification);
                    Log.d("ForegroundService","run: " + rndProject.getName() + "was shown as a notification");

                } catch (Exception e) { //Interrupted
                    e.printStackTrace();
                    Log.e(SERVICE, "Error", e);
                }
                //the recursive bit - if started still true, call self again
                if(serviceStarted) {
                    foregroundRecursion();
                }
            }
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //if service is destroyed
    @Override
    public void onDestroy()
    {
        serviceStarted = false;
        super.onDestroy();
    }
}
