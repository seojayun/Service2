package com.example.vibrateex;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RadioGroup rGroup1;
    RadioButton rdo1,rdo2,rdo3,rdo4;
    Button btnOk;
    TextView tvResult;
    MediaPlayer mp  ;

    //수업내용   소리 = 폰에 내장되있는 소리 내는법  case R.id.rdo1 :   //raw 폴더에 음악파일 넣고 수행 (app모듈에 있음)
    //수업내용   진동 =   case R.id.rdo4 : 일정시간 한번 진동울리게하기    case R.id.rdo3 : 배열을 선언하여 순서나 시간 마음대로 진동울리게하기.
    //1.res 폴더 하위로 raw 폴더 생성
    // <uses-permission android:name="android.permission.VIBRATE"/> androidManifest.xml에 등록  --> 진동효과를 내기 위해서는 퍼미션을 줘야 가능하다.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rGroup1 = (RadioGroup)findViewById(R.id.rGroup1);
        rdo1 = (RadioButton)findViewById(R.id.rdo1);
        rdo2= (RadioButton)findViewById(R.id.rdo2);
        rdo3= (RadioButton)findViewById(R.id.rdo3);
        rdo4= (RadioButton)findViewById(R.id.rdo4);
        btnOk = (Button)findViewById(R.id.btnOk);
        tvResult = (TextView)findViewById(R.id.tvResult);
            mp = MediaPlayer.create(this,R.raw.mpplayer);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (rGroup1.getCheckedRadioButtonId()) {//라디오 버튼을 눌렀을 때 수행되도록
                    case R.id.rdo1 :
                        Uri noti = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);   //2.음악파일이 없을 때 써주면, 폰의 기본 상태소리를 내주는 역할을 한다. TYPE_NOTIFICATION  <-폰 자체에서 나는 소리 종류중 하나임
                        Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(),noti);  //2.음악파일이 없을 때 써주면, 폰의 기본 상태소리를 내주는 역할을 한다.
                        ringtone.play();   //2.음악파일이 없을 때 써주면, 폰의 기본 상태소리를 내주는 역할을 한다.(플레이한다.)
                        tvResult.setText("오답입니다.");
                        break;
                    case R.id.rdo2 :
                           mp.start();
                           tvResult.setText("정답입니다.\n ㅋㅋㅋㅋㅋㅋ");
                        break;
                    case R.id.rdo3 :
                        Vibrator vibrator1 = (Vibrator)getSystemService(VIBRATOR_SERVICE);
                                                  //0.5초쉬고 1초진동 0.4초 쉬고 2초 진동
                        vibrator1.vibrate(new long[] {500,1000,400,2000},-1);  // repeat 을 0으로 해놓으면 진동이 무제한 으로 울린다.     // vibrator1.cancel(); 진동을 무한대로 했을 때 멈추는 수행명령어  vibrator1.cancel();

                        tvResult.setText("오답입니다.");
                        break;
                    case R.id.rdo4 :
                        Vibrator vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);  //진동을 하게 하기위한 변수 선언후
                        vibrator.vibrate(1000); //1초동안 진동이 나도록 설정
                        tvResult.setText("오답입니다.");
                        break;
                        default:
                            toestMake("선택해줘");
                            break;
                }
            }
        });
    }


    //토스트 메소드
    public void toestMake (String msg) {
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }

}
