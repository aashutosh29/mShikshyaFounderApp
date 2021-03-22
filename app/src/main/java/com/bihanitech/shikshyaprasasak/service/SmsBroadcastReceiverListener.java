package com.bihanitech.shikshyaprasasak.service;

public interface SmsBroadcastReceiverListener {
    void onSuccess(String message);

    void onFailure();

}
