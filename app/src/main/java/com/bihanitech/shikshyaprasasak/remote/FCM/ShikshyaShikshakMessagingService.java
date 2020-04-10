package com.bihanitech.shikshyaprasasak.remote.FCM;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;


import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.database.DatabaseHelper;
import com.bihanitech.shikshyaprasasak.ui.splashScreen.SplashScreenActivity;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.bihanitech.shikshyaprasasak.utility.sharedPreference.SharedPrefsHelper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class ShikshyaShikshakMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    SharedPrefsHelper sharedPrefsHelper;
    DatabaseHelper databaseHelper;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        //createNotification(remoteMessage);

        Log.v("ShikshyaMessage","Message is "+remoteMessage.getData().toString());

        sharedPrefsHelper = SharedPrefsHelper.getInstance(this);
        String content = remoteMessage.getData().get("msg");
        try{
            JSONObject obj = new JSONObject(content);


            String section = obj.getString("Section");
            String regNo =  obj.getString("Regno");

            switch (section) {

                case "Account":
                    sharedPrefsHelper.saveValue(Constant.ACCOUNT_UPTODATE,false);
                    break;
                case "Complain":
                    break;
                case "Notice":
                    sharedPrefsHelper.saveValue(Constant.NOTICE_UPTO_DATE,false);
                    break;
                case "Leave":
                    break;
                case "Event":
                    break;
                case "Calendar":
                    sharedPrefsHelper.saveValue(Constant.CALENDAR_UPTO_DATE,false);
                    break;
                case "SHIKSHYA":
                    sharedPrefsHelper.saveValue(Constant.APPLICATION_UPTO_DATE,false);
                    break;
                case "StudentUpdate":
                    sharedPrefsHelper.saveValue(Constant.STUDENT_UPTO_DATE,false);
                    break;
                case "SubjectUpdate":
                    sharedPrefsHelper.saveValue(Constant.SUBJECT_UPTO_DATE,false);
                    break;
                default:
                    // nothing
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        FirebaseCloudMessageFunction(remoteMessage.getData().get("title"), remoteMessage.getData().get("msg"));
        
    }


    private void createNotification(RemoteMessage remoteMessage){
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_id")
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.drawable.shikshya)
                .setAutoCancel(true);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(10051, notificationBuilder.build());
    }




    // Function to Generate Push Notification After Receiving Response from Server.
    private void FirebaseCloudMessageFunction(String messageTitle, String content) {

        String notTitle = "Shikshya Shikshak";
        String notSubTitle = "New Notification";
        Intent intent = new Intent(this, SplashScreenActivity.class);

        if(content.length()==0){
            notTitle = "Shikhsya Shikshak Notification";
            notSubTitle = "Notification from Shikhsya Shikshak App";
        }else{
            try {
                JSONObject obj = new JSONObject(content);
                notTitle = "Shikshya Shikshak";
                notSubTitle = obj.getString("Title");
                intent.putExtra(Constant.NOTIFICATION_ACTION,obj.getString("Section"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        Log.v(TAG,"The notification should have been generated");

        // Creating Intent.
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        int requestID = (int) System.currentTimeMillis();

        // Device vibrate pattern.
        long[] pattern = {500,500,500,500,500};

        // Adding FLAG_ACTIVITY_CLEAR_TOP to intent.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        // Creating Pending Intent object.
        PendingIntent pendingIntent = PendingIntent.getActivity(this,requestID , intent, PendingIntent.FLAG_CANCEL_CURRENT);




        // Creating URI to access the default Notification Ringtone.
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        // Converting drawable icon to bitmap for default notification ICON.
        Bitmap DefaultIconBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.shikshya);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            String id = "shikhsya_shikshak";
            // The user-visible name of the channel.
            CharSequence name = "Shikhsya Shikshak";
            // The user-visible description of the channel.
            String description = "Notifications from Shikhsya Shikshak";
            int importance = NotificationManager.IMPORTANCE_MAX;
            NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT);
            // Configure the notification channel.
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            // Sets the notification light color for notifications posted to this
            // channel, if the device supports this feature.
            mChannel.setLightColor(Color.RED);
            notificationManager.createNotificationChannel(mChannel);





            PendingIntent mPendingIntent = PendingIntent.getActivity(getApplicationContext(), 123, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(),"shikshya")
                    .setSmallIcon(R.drawable.shikshya) //your app icon
                    .setBadgeIconType(R.drawable.shikshya) //your app icon
                    .setChannelId(id)
                    .setContentTitle(notTitle)
                    .setAutoCancel(true).setContentIntent(pendingIntent)
                    .setNumber(1)
                    .setVibrate(pattern)
                    .setSound(defaultSoundUri)
                    .setColor(255)
                    .setContentText(notSubTitle)
                    .setWhen(System.currentTimeMillis());
            notificationManager.notify(10051, notificationBuilder.build());


        }

        else {


            // Building Notfication.
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)

                    // Adding Default Icon to Notification bar.
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.shikshya))
                    .setSmallIcon(R.drawable.shikshya)
                    // Setting up Title.
                    .setContentTitle(notTitle)

                    // Setting the default msg coming from server into Notification.
                    .setContentText(notSubTitle)

                    .setAutoCancel(true)
/*
                .setOngoing(true)
*/
                    .setVibrate(pattern)

                    .setSound(defaultSoundUri)

                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            notificationManager.notify(10051, builder.build());
        }


    }

   /* private void setUpdateDBFlag(String updateFlag) {

        sharedPrefsHelper = SharedPrefsHelper.getInstance(this);
        sharedPrefsHelper.saveValue(Constant.DATABASE_UPDATED,true);
        sharedPrefsHelper.saveValue(Constant.FLAG,true);
        sharedPrefsHelper.saveValue(Constant.UPDATE_FIRST,true);
        Log.v(TAG,sharedPrefsHelper.getValue(Constant.DATABASE_UPDATED,false)+"");
    }*/
}



