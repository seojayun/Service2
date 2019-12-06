package com.example.notificationex;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//노티를 배워본다.
  //노티는 오레오 이상부터는 채널을 만들어서 시스템에서 사용하는 채널보다 낮게 만든다.
public class MainActivity extends AppCompatActivity {
    Button btnNoti;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnNoti = (Button)findViewById(R.id.btnNoti);


        final NotificationManager notiManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE); //1.화면에 .NOTIFICATION_SERVICE를 가져와 변수  notiManager 에 담는다. 노티를 사용한다고 정의만 내리고 생성은 안함


        if(Build.VERSION.SDK_INT>=26) {  //2 .Build.VERSION.SDK_INT>=26 (26=오레오) 이상부터 수행하는 명령어  --->오레오 이상버전부터는 채널을 만들어서 담아줘야 한다.
            //3.여러가지 채널의 이름 및 기능 설정
            final NotificationChannel notificationChannel = new NotificationChannel("noti","notiChannel",NotificationManager.IMPORTANCE_DEFAULT);   //5. id/이름/중요도(순서)


//            AudioAttributes audioAttributes = new AudioAttributes.Builder()
//                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//                    .setUsage(AudioAttributes.USAGE_ALARM)
//                    .build();

            notificationChannel.setDescription("채널");   //(팝업이름)
            notificationChannel.enableLights(true); ///채널에 무언가 오면  불이 들어오게 하는 기능
            notificationChannel.setLightColor(Color.BLUE);///채널에 무언가 오면  불의 색상 설정
            notificationChannel.enableVibration(true); //채널에 무언가 오면 진동
            notificationChannel.setVibrationPattern(new long[] {100,200,100,200});  //0.1초쉬고, 0.2초진동 0.1초쉬고 0.2초진동
//            notificationChannel.setSound();  //소리를 낸다.
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE); // 4. 화면에 보이게 해주는 기능
            notiManager.createNotificationChannel(notificationChannel);  //6. 채널 생성
        }

        final PendingIntent pIntent = PendingIntent.getActivity(MainActivity.this,0,new Intent(getApplicationContext(),MapsActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);
        //14.노티를 불러올때쓰는 엑티비티/노티를 불러올 엑티비티 순서로 적어주면 된다.   (노티에서 엑티비티 호출하는 방법)
        btnNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),R.drawable.screamicon); //11-1 .setLargeIcon() 바로 가져오지 못하고 변수 선언 후 대입해야한다.
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this,"noti") //오레오 이상버전은 채널id를줘야 한다. 주지않으면 아예 뜨지 않는다.
                           .setSmallIcon(R.drawable.screamicon) //8. .setSmallIcon()처음 상단에 보이는 아이콘
                            .setContentTitle("공지사항")  //9.노티를 보고 내렸을때 생기는 제목
                            .setContentText("등 뒤 조심해라 칼로 확!")  //10.노티를 보고 내렸을 때 생기는 내용
                            .setLargeIcon(largeIcon)   //11..setLargeIcon() 은 내타이틀과 내용이 보이는 부분에 생기는 아이콘
                            .setDefaults(Notification.DEFAULT_SOUND)
                            .setAutoCancel(true)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)  //12.노티의 중요도       .PRIORITY_DEFAULT 기본
                            .setContentIntent(pIntent)  ; //13. 노티를 눌렀을 때 실행할 엑티비티 를 정해주는 명령

                notiManager.notify(0,mBuilder.build()); //15.노티를만들겠다고 선언

            }
        });
    }
}
