package com.example.batterystat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


//BroadcastReceiver 에 대한 설명


public class MainActivity extends AppCompatActivity {

    TextView tvBattery;
    ImageView ivBattery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvBattery = (TextView)findViewById(R.id.tvBattery);
        ivBattery = (ImageView)findViewById(R.id.ivBattery);

    }
    BroadcastReceiver br = new BroadcastReceiver() { //1. BroadcastReceiver 인스턴스 생성
        @Override
        public void onReceive(Context context, Intent intent) { //(장소,intent)
            String action = intent.getAction(); //2.변수 선언
            if(action.equals(Intent.ACTION_BATTERY_CHANGED))  {//3.베터리 상태가 바뀌면 발생하면 if문 수행
                int reamin = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);  //4.베터리의 남은량을 정수값을로 받음
                tvBattery.setText("현재 충전량"+reamin+"%\n");
                if(reamin>=95) {
                    ivBattery.setImageResource(R.drawable.betteryfull);
                }else if(reamin>85) {
                    ivBattery.setImageResource(R.drawable.bettery90);
                }else if(reamin>75) {
                    ivBattery.setImageResource(R.drawable.bettery70);
                }else if(reamin>45) {
                    ivBattery.setImageResource(R.drawable.bettery40);
                }else if(reamin>15) {
                    ivBattery.setImageResource(R.drawable.bettery10);
                }else {
                    ivBattery.setImageResource(R.drawable.bettery0);
                }
                int plug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,0);  //5.플러그에 대한 정보(플러그가 꽂였나 안꽃였나
                switch (plug) {
                    case 0:
                        tvBattery.append("충전기 연결 안됨 충전기를 연결하시오.");    //6. .append();  는 settext 이후에 추가해서 기술해주는 메소드
                        break;
                    case BatteryManager.BATTERY_PLUGGED_AC :  //일반충전기
                        tvBattery.append("충전기 충전중... ");    //6. .append();  는 settext 이후에 추가해서 기술해주는 메소드
                        break;
                    case BatteryManager.BATTERY_PLUGGED_USB :  //USB잭 충전
                        tvBattery.append("USB 연결됨... ");
                        break;


                }
                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS,0);
                switch (status) {

                    case BatteryManager.BATTERY_STATUS_CHARGING :
                        tvBattery.append("베터리 충전 중임");
                        break;
                    case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                        tvBattery.append("베터리 충전중이 아니오니 충전잭을 꼽으시오.");
                        break;
                    case BatteryManager.BATTERY_STATUS_FULL : //베터리 100%충전이면
                        tvBattery.append("베터리 충전 완료 ");
                        break;
//                    case BatteryManager.BATTERY_STATUS_DISCHARGING : //usb 100%충전이면
//                        tvBattery.append("베터리 방전됨 ");
//                        break;

                }
            }
        }
    };

    @Override
    protected void onResume() {  //7. onResume() 생성  - 엑티비티 생명주기에 의해 자주 사용하는 메소드이다. (엑티비티가 여러개 있을 경우는 특히)  왜냐면 온크리에이트 메소드가 실행되기전에 실행하기 때문
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(br,filter);  //생성
    }

    @Override
    protected void onPause() {  //8. onPause() 생성 - 다른엑티비티 만날때
        super.onPause();
        unregisterReceiver(br);  //헤제
    }
}
