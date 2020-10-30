package ca.on.conestogac.schmidt.rockpaperscissors;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {
    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }

    @Override
    public void onCreate() {


        final Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {

            @Override
           public void run() {
                SendNotification();
            }
        },  10000);

       // Toast.makeText(this, "Service on create called", Toast.LENGTH_SHORT).show();
        super.onCreate();
    }

    public void SendNotification(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("startFromNotification", "true");
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP  | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        int notificationId = 1;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "GameAlert")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("The game is Waiting for you")
                .setContentText("Keep Playing! You know you want to")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

        notificationManager.notify(notificationId, builder.build());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
