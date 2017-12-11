package com.example.administrator.payui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.selectall.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv)
    public void onViewClicked() {
        PayDetailFragment payDetailFragment = new PayDetailFragment();
        payDetailFragment.show(getSupportFragmentManager(),"s");
    }
}
