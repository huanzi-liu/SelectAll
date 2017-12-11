package com.example.administrator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.administrator.address.AddressActivity;
import com.example.administrator.groupedrecyclerview.StickyActivity;
import com.example.administrator.okhttp.OkHttpActivity;
import com.example.administrator.payui.PayActivity;
import com.example.administrator.postimg.PostImgActivity;
import com.example.administrator.selectall.MainActivity;
import com.example.administrator.selectall.R;
import com.example.administrator.swipemenu.LinearLayoutDelDemoActivity;
import com.example.administrator.swipemenu.SwipeMenuDeleteActivity;
import com.example.administrator.updateuserinfo.UpdateUserInfoActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.selectall, R.id.address, R.id.update,R.id.swipemenu,R.id.grouped,R.id.pay,R.id.postimg,R.id.okhttp})
    public void onViewClicked(View view) {

        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.selectall:
                startActivity(intent.setClass(getApplication(), MainActivity.class));
                break;
            case R.id.address:
                startActivity(intent.setClass(getApplication(), AddressActivity.class));
                break;
            case R.id.update:
                startActivity(intent.setClass(getApplication(), UpdateUserInfoActivity.class));
                break;
            case R.id.swipemenu:
                startActivity(intent.setClass(getApplication(), SwipeMenuDeleteActivity.class));
                break;
            case R.id.grouped:
                startActivity(intent.setClass(getApplication(), StickyActivity.class));
                break;
            case R.id.pay:
                startActivity(intent.setClass(getApplication(), PayActivity.class));
                break;
            case R.id.postimg:
                startActivity(intent.setClass(getApplication(), PostImgActivity.class));
                break;
            case R.id.okhttp:
                startActivity(intent.setClass(getApplication(), OkHttpActivity.class));
                break;
        }
    }
}
