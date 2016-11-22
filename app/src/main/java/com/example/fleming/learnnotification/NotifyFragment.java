package com.example.fleming.learnnotification;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RemoteViews;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * NotifyFragment
 * Created by Fleming on 2016/11/21.
 */

public class NotifyFragment extends Fragment implements NotifyKind {

    private static final int NOTIFICATION_0 = 0;
    private static final int NOTIFICATION_1 = 1;
    private static final int NOTIFICATION_2 = 2;
    private static final int NOTIFICATION_3 = 3;
    private static final int NOTIFICATION_4 = 4;
    @BindView(R.id.bt_normal_notify)
    Button btNormalNotify;
    @BindView(R.id.bt_bigimage_notify)
    Button btBigimageNotify;
    @BindView(R.id.bt_multiline_notify)
    Button btMultilineNotify;
    @BindView(R.id.bt_progress_notify)
    Button btProgressNotify;
    @BindView(R.id.bt_custom_notify)
    Button btCustomNotify;
    private NotificationManager mManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.notify_fragment, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @OnClick({R.id.bt_normal_notify, R.id.bt_bigimage_notify, R.id.bt_multiline_notify, R.id.bt_progress_notify, R.id.bt_custom_notify})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_normal_notify:
                sendNormalNotification();
                break;
            case R.id.bt_bigimage_notify:
                sendBigImageNotification();
                break;
            case R.id.bt_multiline_notify:
                sendMultiLineNotification();
                break;
            case R.id.bt_progress_notify:
                sendProgressNotification();
                break;
            case R.id.bt_custom_notify:
                sendCustomNotification();
                break;
        }
    }

    @Override
    public void sendNormalNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity());
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        mBuilder.setContentTitle("标题");
        mBuilder.setContentText("我是内容");
        mBuilder.setNumber(10);
        mBuilder.setTicker("好消息好消息");
        mManager.notify(NOTIFICATION_0, mBuilder.build());
    }

    @Override
    public void sendBigImageNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity());
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        mBuilder.setContentTitle("提示");
        mBuilder.setContentText("精彩大图");
        mBuilder.setTicker("大图来了...");
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle(mBuilder);
        bigPictureStyle.bigPicture(BitmapFactory.decodeResource(getResources(), R.mipmap.miss));
        mManager.notify(NOTIFICATION_1, mBuilder.build());
    }

    @Override
    public void sendMultiLineNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity());
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        mBuilder.setContentTitle("提示");
        mBuilder.setContentText("多行内容");
        mBuilder.setTicker("一大波信息来了...");
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle(mBuilder);
        inboxStyle.setBigContentTitle("内容是这样子滴");
        inboxStyle.addLine("111111111111111");
        inboxStyle.addLine("2222222222222222");
        inboxStyle.addLine("33333333333333");
        mManager.notify(NOTIFICATION_2, mBuilder.build());
    }

    @Override
    public void sendProgressNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity());
        new Thread(new ProgressRunable(mManager, mBuilder)).start();
    }

    @Override
    public void sendCustomNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity());
        mBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setTicker("显示自定义notification");
        RemoteViews views = new RemoteViews(getActivity().getPackageName(), R.layout.notification);
        mBuilder.setContent(views);
        mManager.notify(NOTIFICATION_4, mBuilder.build());
    }

    public class ProgressRunable implements Runnable {

        private NotificationManager manager;
        private NotificationCompat.Builder builder;

        public ProgressRunable(NotificationManager manager, NotificationCompat.Builder builder) {
            this.manager = manager;
            this.builder = builder;
        }

        @Override
        public void run() {
            builder.setContentTitle("下载文件");
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setTicker("文件开始下载");
            for (int i = 0; i <= 100; i+=5) {
                builder.setProgress(100, i, false);
                manager.notify(NOTIFICATION_3, builder.build());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            builder.setContentText("下载完成");
            builder.setProgress(0, 0, false);
            manager.notify(NOTIFICATION_3, builder.build());
        }
    }
}
