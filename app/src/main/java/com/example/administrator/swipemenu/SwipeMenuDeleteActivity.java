package com.example.administrator.swipemenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.SwipeMenuLayout;
import com.example.administrator.selectall.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SwipeMenuDeleteActivity extends AppCompatActivity {

    @BindView(R.id.rv_swipemenu)
    RecyclerView rvSwipemenu;

    List<String> list = new ArrayList<String>();

    private static final String TAG = "SwipeMenuDeleteActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_menu_delete);
        ButterKnife.bind(this);
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");

        swipemenuAdaper adaper = new swipemenuAdaper();
        rvSwipemenu.setAdapter(adaper);
        rvSwipemenu.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvSwipemenu.addItemDecoration(new DividerItemDecoration(getApplication(),LinearLayout.VERTICAL));

    }

    public class swipemenuHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv)
        TextView tv;
        @BindView(R.id.tv_update)
        TextView tvUpdate;
        @BindView(R.id.tv_delete)
        TextView tvDelete;

        public swipemenuHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class swipemenuAdaper extends RecyclerView.Adapter<swipemenuHolder> {

        @Override
        public swipemenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(getApplication()).inflate(R.layout.item_swipemenu, parent, false);
            return new swipemenuHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final swipemenuHolder holder, final int position) {

            ((SwipeMenuLayout) holder.itemView).setIos(false);

            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplication(), "tvOnclick" + position, Toast.LENGTH_SHORT).show();
                }
            });
            holder.tvUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplication(), "tvUpdate" + position, Toast.LENGTH_SHORT).show();
                    holder.tv.setText("222");
                }
            });
            holder.tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplication(), "tvDelete" + position, Toast.LENGTH_SHORT).show();
                    if (position >=0 && position<list.size()){
                        list.remove(position);
                        notifyItemRemoved(position);
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
}
