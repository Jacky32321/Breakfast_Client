package com.example.jacky.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.ParcelUuid;
import android.support.v4.app.NotificationCompat;

import com.example.jacky.Common.Commons;

import com.example.jacky.Database.Database;
import com.example.jacky.Model.Request;
import com.example.jacky.OrderStatus;
import com.google.android.gms.common.internal.service.Common;
import com.example.jacky.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListenOrder extends Service implements ChildEventListener {

    FirebaseDatabase db;
    DatabaseReference requests;

    public ListenOrder() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        db = FirebaseDatabase.getInstance();
        requests = db.getReference("confirm");
    }

    @Override
    public int onStartCommand(Intent intend, int flags, int startId){
        requests.addChildEventListener(this);
        return super.onStartCommand(intend, flags, startId);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s){}

    public void onChildChanged(DataSnapshot dataSnapshot, String s){
        Request request = dataSnapshot.getValue(Request.class);
        String key = dataSnapshot.getKey();
        System.out.println("##############################1: " + dataSnapshot.getKey());

        requests = db.getReference("Request");
        //submit to firebase
        requests.child(key).setValue(request);

        showNotification(dataSnapshot.getKey(),request);


    }

    private void showNotification(String key, Request request){
        Intent intent = new Intent(getBaseContext(), OrderStatus.class);
        intent.putExtra("userPhone", request.getPhone());
        System.out.println("##############################2: " + request.getPhone());

        //狀態通知
        PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());

        builder.setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL)
                                    .setWhen(System.currentTimeMillis())
                                    .setTicker("EDMTDev")
                                    .setContentInfo("Your order was updated")
                                    .setContentText("Order #" + key + "was update status to " + Commons.convertCodeToStatus(request.getStatus()))
                                    .setContentIntent(contentIntent)
                                    .setContentInfo("Info")
                                    .setSmallIcon(R.mipmap.ic_launcher);

        NotificationManager notificationManager = (NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot){}

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s){}

    @Override
    public void onCancelled(DatabaseError databaseError){}
}
