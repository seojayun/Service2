package com.example.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // -raw 폴더를 만들고 음악집어넣기
    // - Service 클래스 생성

    Button btnMusicStart,btnMusicStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMusicStart = (Button)findViewById(R.id.btnMusicStart);
        btnMusicStop = (Button)findViewById(R.id.btnMusicStop);
        final Intent intent;  //1.인텐트 변수 선언
        intent = new Intent(this,MusicService.class);

        btnMusicStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=26) { //오레오 버전 이상이면 수행 미만이면 else구문
                    startForegroundService(intent); //7. 서비스가 종료되었기 때문에, ForegroundService 실행    ->MusicService 클래스 이동
                }else {
                    startService(intent);  //2. MusrcService 호출
                }

            }
        });
        btnMusicStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(intent); //3. 서비스 종료   ->MusicService 클래스 이동
            }
        });

    }
}
