package com.example.administrator.selectall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.address.AddressActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.tv_all)
    TextView tvAll;

    public static final int CHECK_STATE_UNCHECKED = 1;
    public static final int CHECK_STATE_CHECKED = 2;

    int checked = CHECK_STATE_CHECKED;

    private static final String TAG = "MainActivity";

    List<String> list = new ArrayList<>();
    List<String> list2 = new ArrayList<>();
    private rvAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");

        adapter = new rvAdapter();
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getApplication()));
        rv.addItemDecoration(new DividerItemDecoration(getApplication(), LinearLayout.VERTICAL));

        tvAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChecked(checked);

                Log.e(TAG, "onClick: 1111" + checked);
            }
        });

    }

    public void onChecked(int checke) {

        list2.clear();

        if (checke == CHECK_STATE_UNCHECKED) {
            tvAll.setSelected(false);
            checked = CHECK_STATE_CHECKED;
        } else {
            tvAll.setSelected(true);
            checked = CHECK_STATE_UNCHECKED;
            list2.addAll(list);
        }
        adapter.notifyDataSetChanged();
    }

    class rvHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv)
        ImageView imageView;
        @BindView(R.id.tv)
        TextView textView;

        public rvHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class rvAdapter extends RecyclerView.Adapter<rvHolder> {
        @Override
        public rvHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = View.inflate(getApplication(), R.layout.item_rv, null);

            return new rvHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final rvHolder holder, int position) {

            final String s = list.get(position);

            holder.textView.setText(list.get(position).toString());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                int boo = CHECK_STATE_CHECKED;

                @Override
                public void onClick(View v) {
                    if (boo == CHECK_STATE_CHECKED) {
                        //选中处理
                        if (!list2.contains(s)) {
                            list2.add(s);
                        }
                        holder.imageView.setSelected(true);
                        boo = CHECK_STATE_UNCHECKED;
                    } else {
                        //未选中处理
                        if (list2.contains(s)) {
                            list2.remove(s);
                        }
                        holder.imageView.setSelected(false);
                        boo = CHECK_STATE_CHECKED;
                    }
                    //判断列表数据是否全部选中
                    if (list2.size() == list.size()) {

                        tvAll.setSelected(true);
                    } else {

                        tvAll.setSelected(false);
                    }
//                    notifyDataSetChanged();
                }
            });
            if (list2.size() == list.size()) {
                holder.imageView.setSelected(true);
            } else {
                holder.imageView.setSelected(false);
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

}
