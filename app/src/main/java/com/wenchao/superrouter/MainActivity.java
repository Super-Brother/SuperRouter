package com.wenchao.superrouter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wenchao.router.ARouter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goLogin(View view) {
        ARouter.getInstance().jumpActivity("login/login", null);
    }

    public void goMember(View view) {
        ARouter.getInstance().jumpActivity("member/member", null);
    }
}
