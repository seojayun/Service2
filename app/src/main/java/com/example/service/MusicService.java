package com.example.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

//<uses-permission android:name="android.permission.FOREGROUND_SERVICE"/>  manifests.xml에 추가

public class MusicService extends Service {
    MediaPlayer mp;   //4-2 mp 변수 선언

    public MusicService() {//생성자
    }

    @Override
    public void onCreate() {  //8. onCreate() 메소드 생성
        super.onCreate();
        startForegroundService();  //17.  startForegroundService(); 호출
    }

    @Override
    public void onDestroy() {  //6. onDestroy 메소드 생성
        mp.stop();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {   //4. onStartCommand 생성
        mp=MediaPlayer.create(this,R.raw.da);
        mp.setLooping(true);  //5. 음악이 끝나면 무한반복
        mp.start();
        return super.onStartCommand(intent, flags, startId);
    }



    void startForegroundService() {//9.startForegroundService 메소드 생성 후 xml 생성 (notipcation.xml) 후 이동

        Intent notiIntent = new Intent(this,MainActivity.class);  //10. 인텐트 생성
        PendingIntent pInrent = PendingIntent.getActivity(this,0,notiIntent,0);
        RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.notipcation);   //11.notlpcation.xml을 노티화면으로 사용하겠다는 의미  설정만한 상태이고 세팅은 하지 않음
        NotificationCompat.Builder builder ;

        if(Build.VERSION.SDK_INT>=26) {  //12.오레오 이상 버전이면 if구문 수행 미만이면 else 구문 수행
            String channelID = "musicServiceChannel";  //13.채널 변수 선언
            NotificationChannel channel = new NotificationChannel(channelID, "음악채널", NotificationManager.IMPORTANCE_DEFAULT); //14.(채널id/채널이름/중요도)
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
            builder = new NotificationCompat.Builder(this, channelID);
        }else {
            builder = new NotificationCompat.Builder(this);
        }
        //noti 전용 xml 설정
        builder.setSmallIcon(R.drawable.apple)
                .setContent(remoteViews)   //★  15. xml자체를 컨텐츠 한다는 의미(노티로사용)
                .setContentIntent(pInrent) ;  //16. 기타 옵션을 적용시키고 싶다면 아래로 내려가면서 쓰면 된다.


        startForeground(1,builder.build());//18. startForeground 실행
    }

}
