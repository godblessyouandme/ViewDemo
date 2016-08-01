package com.chuanzhong.viewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ToggerView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myView = (ToggerView) findViewById(R.id.myView);
       /* //设置滑块和背景图片
        myView.setImagee(R.drawable.slide_background,R.drawable.slide_icon);
        //赊着状态
        myView.setState(true);
*/
        myView.setOnChangedStatLinstener(new ToggerView.OnChangedStatLinstener() {
            @Override
            public void onChangedStat(boolean state) {

                Toast.makeText(getApplication(), "" + state, Toast.LENGTH_SHORT).show();
            }
        });


    }
}
