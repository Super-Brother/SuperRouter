package com.wenchao.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wenchao.annotations.BindPath;
import com.wenchao.router.ARouter;

/**
 * @author wenchao
 */
@BindPath("login/login")
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void jumpMember(View view) {
        ARouter.getInstance().jumpActivity("member/member", null);
    }
}
