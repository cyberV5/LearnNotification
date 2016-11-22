package com.example.fleming.learnnotification;

/**
 * NotifyKind
 * Created by Fleming on 2016/11/21.
 */

public interface NotifyKind {

    void sendNormalNotification();

    void sendBigImageNotification();

    void sendMultiLineNotification();

    void sendProgressNotification();

    void sendCustomNotification();
}
