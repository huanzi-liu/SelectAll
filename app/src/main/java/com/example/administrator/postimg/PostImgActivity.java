package com.example.administrator.postimg;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.selectall.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PostImgActivity extends AppCompatActivity {

    @BindView(R.id.rv)
    RecyclerView rv;

    List<Bitmap> list = new ArrayList<>();

    private imgAdapter imgAdapter;

    String time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_img);
        ButterKnife.bind(this);

        imgAdapter = new imgAdapter();
        rv.setAdapter(imgAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplication());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(layoutManager);
    }

    @OnClick(R.id.tv_add)
    public void onViewClicked() {
        showDialogImg();
    }

    class imgHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv)
        ImageView iv;

        public imgHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class imgAdapter extends RecyclerView.Adapter<imgHolder> {

        @Override
        public imgHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplication()).inflate(R.layout.item_add_ing, parent, false);
            return new imgHolder(view);
        }

        @Override
        public void onBindViewHolder(imgHolder holder, int position) {
            holder.iv.setImageBitmap(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list != null ? list.size() : 0;
        }
    }
    public void showDialogImg() {
        View view = View.inflate(this, R.layout.item_dialog_img, null);

        TextView tvXiang = view.findViewById(R.id.tv_xiang);
        TextView tvPai = view.findViewById(R.id.tv_pai);

        final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();

        tvXiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, null)
                        .setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*"), 1);
                dialog.dismiss();
            }
        });

        tvPai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time = getCurrectTime();
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        .putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(new File(Environment.getExternalStorageDirectory(), time+"head.jpg"))), 2);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());// 裁剪图片
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory() + "/"+time+"head.jpg");
                    cropPhoto(Uri.fromFile(temp));// 裁剪图片
                }
                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    Bitmap head = extras.getParcelable("data");
                    if (head != null) {
                        /**
                         * 上传服务器代码
                         */
                        decodeBitmapToFile(head);// 保存在SD卡中
//                        rivHeads.setImageBitmap(head);// 用ImageView显示出来
                        list.add(head);
                        imgAdapter.notifyDataSetChanged();
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 调用系统的裁剪功能
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void decodeBitmapToFile(Bitmap bitmap) {

        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        File file = null;

        //存入本地
        try {
            String time = getCurrectTime();
            file = new File(Environment.getExternalStorageDirectory(), time+"head.jpg");
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

            fos.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getCurrectTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = dateFormat.format(new java.util.Date());

        return time;
    }
}
