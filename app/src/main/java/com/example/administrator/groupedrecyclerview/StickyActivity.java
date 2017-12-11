package com.example.administrator.groupedrecyclerview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.example.administrator.entity.ChildEntity;
import com.example.administrator.entity.GroupEntity;
import com.example.administrator.selectall.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StickyActivity extends AppCompatActivity {

    @BindView(R.id.rv)
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky);
        ButterKnife.bind(this);

        stickyAdapter adapter = new stickyAdapter(this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

//    class stickyHeaderHolder extends BaseViewHolder {
//
//        @BindView(R.id.tv_address)
//        TextView tvAddress;
//        @BindView(R.id.tv_condition)
//        TextView tvCondition;
//
//        public stickyHeaderHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//        }
//    }
//
//    class stickyChiidHolder extends BaseViewHolder {
//
//        @BindView(R.id.iv_state)
//        ImageView ivState;
//        @BindView(R.id.iv_goods_img)
//        ImageView ivGoodsImg;
//        @BindView(R.id.tv_goods_name)
//        TextView tvGoodsName;
//        @BindView(R.id.tv_goods_color)
//        TextView tvGoodsColor;
//        @BindView(R.id.tv_money)
//        TextView tvMoney;
//        @BindView(R.id.tv_number)
//        TextView tvNumber;
//        @BindView(R.id.rl_num)
//        RelativeLayout rlNum;
//        @BindView(R.id.ll_card_goods)
//        LinearLayout llCardGoods;
//        @BindView(R.id.tv_delete)
//        TextView tvDelete;
//
//        public stickyChiidHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//        }
//    }

    class stickyAdapter extends GroupedRecyclerViewAdapter {

        private ArrayList<GroupEntity> mGroups;

        public stickyAdapter(Context context){
            super(context);
        }

        public stickyAdapter(Context context, ArrayList<GroupEntity> groups) {
            super(context);
            mGroups = groups;
        }

        @Override
        public int getGroupCount() {
//            return mGroups == null ? 0 : mGroups.size();
            return 3;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
//            ArrayList<ChildEntity> children = mGroups.get(groupPosition).getChildren();
//            return children == null ? 0 : children.size();
            return 2;
        }

        @Override
        public boolean hasHeader(int groupPosition) {
            return true;
        }

        @Override
        public boolean hasFooter(int groupPosition) {
            return false;
        }

        @Override
        public int getHeaderLayout(int viewType) {
            return R.layout.item_cart;
        }

        @Override
        public int getFooterLayout(int viewType) {
            return 0;
        }

        @Override
        public int getChildLayout(int viewType) {
            return R.layout.item_cart_goods;
        }

        @Override
        public void onBindHeaderViewHolder(BaseViewHolder holder, int groupPosition) {
//            View itemView = View.inflate(getApplication(), R.layout.item_cart_goods, null);
//            holder = new stickyHeaderHolder(itemView);

//            stickyHeaderHolder s = (stickyHeaderHolder) holder;
//            s.tvAddress.setText("text" + groupPosition);

//            holder.tvAddress.setText("text" + groupPosition);
            holder.setText(R.id.tv_address, "text" + groupPosition);
        }

        @Override
        public void onBindFooterViewHolder(BaseViewHolder holder, int groupPosition) {

        }

        @Override
        public void onBindChildViewHolder(BaseViewHolder holder, int groupPosition, int childPosition) {
//            stickyChiidHolder chiidHolder = (stickyChiidHolder) holder;
//            chiidHolder.tvGoodsName.setText("test"+groupPosition);
            holder.setText(R.id.tv_goods_name, "text" + groupPosition);
        }
    }
}
