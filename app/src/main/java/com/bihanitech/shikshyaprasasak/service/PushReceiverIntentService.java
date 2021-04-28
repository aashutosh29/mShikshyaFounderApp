package com.bihanitech.shikshyaprasasak.service;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.bihanitech.shikshyaprasasak.utility.Constant;


/**
 * Created by dilip on 12/25/17.
 */
public class PushReceiverIntentService extends IntentService {


    public PushReceiverIntentService() {
        super("PushReceiverIntentService");
    }

    private final Handler.Callback callback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            try {
                throw new IllegalAccessException("PUSH_RECEIVED NOT HANDLED!");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return true;
        }
    };

    public PushReceiverIntentService(String name) {
        super(name);
    }

/*
    private Handler.Callback callback = msg -> {
        throw new IllegalArgumentException("PUSH_RECEIVED NOT HANDLED!");
    };
*/



    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if(extras!=null) {

            Intent broadcast = new Intent();
            broadcast.putExtra("MESSAGE",extras.getString("MESSAGE"));
            broadcast.setAction(Constant.BROADCAST_NOTIFICATION);

            sendOrderedBroadcast(broadcast,null,null,new Handler(callback), Activity.RESULT_OK,null,extras);
        }
    }

   /* private void sendNotification(Notification notification) {
        sendNotification(notification, new Bundle());
    }

    private void sendNotification(Notification notification, Bundle extras) {
        Intent broadcast = new Intent();
        extras.putParcelable(Extras.NOTIFICATION, notification);
        broadcast.putExtras(extras);
        broadcast.setAction(CalendarContract.Events.BROADCAST_NOTIFICATION);

        sendOrderedBroadcast(broadcast, null, null, new Handler(callback), Activity.RESULT_OK, null, extras);
    }*/
}
