package com.wenchao.member;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wenchao.annotations.BindPath;

@BindPath("member/member")
public class MemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
    }
}
