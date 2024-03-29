package com.bihanitech.shikshyaprasasak.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;


public class SmsBroadcastReceiver extends BroadcastReceiver {
    public static final String TAG = com.bihanitech.shikshyaprasasak.service.SmsBroadcastReceiver.class.getSimpleName();
    public SmsBroadcastReceiverListener smsBroadcastReceiverListener;

    public SmsBroadcastReceiver(SmsBroadcastReceiverListener smsBroadcastReceiverListener) {
        this.smsBroadcastReceiverListener = smsBroadcastReceiverListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
            Bundle extras = intent.getExtras();
            Status status = (Status) extras.get(SmsRetriever.EXTRA_STATUS);


            switch (status.getStatusCode()) {
                case CommonStatusCodes.SUCCESS:
                    // Get SMS message contents
                    String message = (String) extras.get(SmsRetriever.EXTRA_SMS_MESSAGE);
                    Log.v(TAG + "sms_sample_test", message);
                    if (message.contains("is your Shikshya OTP code.")) {
                        smsBroadcastReceiverListener.onSuccess(message);
                    }

                    break;
                case CommonStatusCodes.TIMEOUT:
                    smsBroadcastReceiverListener.onFailure();
                    break;

            }
        }
    }
}

