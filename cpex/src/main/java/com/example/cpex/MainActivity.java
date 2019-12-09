package com.example.cpex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
//콘텐트 프로바이더 구현

public class MainActivity extends AppCompatActivity {
    Button btnCall;
    EditText edtResult;

    //1.manifests.xml 에서 <uses-permission android:name="android.permission.READ_CALL_LOG"/> 퍼미션 추가

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_CALL_LOG},MODE_PRIVATE);  //2. 한번 더 퍼미션 물어봐야한다.

        btnCall = (Button)findViewById(R.id.btnCall);
        edtResult = (EditText)findViewById(R.id.edtResult);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtResult.setText(getCallHistory());  //4. 메소드에 있는 자료를 세팅시킨다.(db)
            }
        });




    }

    @SuppressLint("MissingPermission")   //7.다시한번 퍼미션 확인함
    public String getCallHistory () {  // 3. getCallHistory () 메소드 생성
        String callSet[] = new String[] {CallLog.Calls.DATE,CallLog.Calls.TYPE,CallLog.Calls.NUMBER, CallLog.Calls.DURATION};
        //5.통화할 날짜,수신발신종류,통화한번호,통화시간(초)  가 들어가는 배열 선언
        Cursor cursor;
        cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI,callSet,null,null,null); //6.커서 선언해서 배열에 있는 주소를 가져온다.
        if(cursor==null) {
            return "통화기록 없음";
        }else {
            StringBuffer callBuffer = new StringBuffer();
            callBuffer.append("날짜             구분          전화번호            통화시간\n----------------------------------------------------------------\n");
            cursor.moveToFirst();  //7.제일 앞으로 가져다 놓음


            do {
                long callDate = cursor.getLong(0);  //8.필드 위치가 0번째
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  //9. 연도 월 일   소문자mm은 분
                String date_str = dateFormat.format(new Date(callDate)); //10. 연도 월 일 있는 데이터 포멧방식을 변경
                callBuffer.append(date_str+"  ");
                if(cursor.getInt(1)==CallLog.Calls.INCOMING_TYPE){
                    callBuffer.append("수신    ");
                }else {
                    callBuffer.append("발신    ");
                }
                callBuffer.append(cursor.getString(2));
                callBuffer.append("         "+cursor.getString(3)+"초\n");

            }while (cursor.moveToNext());
            cursor.close();
            return callBuffer.toString();

//            while (cursor.moveToNext()) {  //위의 do while 구문과 같은 의미다.
//                long callDate = cursor.getLong(0);  //8.필드 위치가 0번째
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  //9. 연도 월 일   소문자mm은 분
//                String date_str = dateFormat.format(new Date(callDate)); //10. 연도 월 일 있는 데이터 포멧방식을 변경
//                callBuffer.append(date_str+"  ");
//                if(cursor.getInt(1)==CallLog.Calls.INCOMING_TYPE){
//                    callBuffer.append("수신    ");
//                }else {
//                    callBuffer.append("발신    ");
//                }
//                callBuffer.append(cursor.getString(2));
//                callBuffer.append("         "+cursor.getString(3)+"초\n");
//
//            }  cursor.close();
//            return callBuffer.toString();

        }
    }
}
