package com.example.administrator.address;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.administrator.selectall.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.AddressPicker;

public class AddressActivity extends AppCompatActivity {

    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_address)
    public void onViewClicked() {

        ArrayList<Province> data = new ArrayList<>();
        String json = AssetsUtils.readText(this, "city.json");
        data.addAll(JSON.parseArray(json, Province.class));
        AddressPicker addressPicker = new AddressPicker(this, data);
        addressPicker.setSelectedItem("北京", "北京", "东城");
        addressPicker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
            @Override
            public void onAddressPicked(Province province, City city, County county) {
                String areaName = province.getAreaName();
                String areaName1 = city.getAreaName();
                String areaName2 = county.getAreaName();

                StringBuffer sb = new StringBuffer();
                sb.append(areaName);
                sb.append(areaName1);
                sb.append(areaName2);
                String pcaAddress = sb.toString();
                tvAddress.setText(pcaAddress);
                //获取省级行政区列表
//                requestProvinces(areaName, areaName1, areaName2);

            }

        });
        addressPicker.show();

    }

}
