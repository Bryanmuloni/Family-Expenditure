package com.bryanville.familyexpenditure;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Bryanville on 7/5/2018.
 */

public class NotificationUtils {
    private static final int NOTIFICATION_ID = 312;
    private static final int PENDING_INTENT_ID = 313;
    private static final String NOTIFICATION_CHANNEL_ID = "channel_id";

    private static PendingIntent contentIntent(Context context){
        Intent intent = new Intent(context,MainActivity.class);
        return PendingIntent.getActivity(context,
                PENDING_INTENT_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

    }

    public static void remindNewExpenditureAdded(Context context,String title,String content){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,context.getString(R.string.string_notification_channel),NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context,NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context,R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_shopping_basket_white_24dp)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(title+" added to daily expenditure list.")
                .setContentText(content)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }
        notificationManager.notify(NOTIFICATION_ID,notificationBuilder.build());
    }
    public static Bitmap largeIcon(Context context){
        Resources resources = context.getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(resources,R.drawable.ic_shopping_basket_black_24dp);
        return bitmap;
    }
}
