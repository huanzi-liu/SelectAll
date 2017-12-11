package com.example.administrator.updateuserinfo;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.selectall.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.OptionPicker;

public class UpdateUserInfoActivity extends AppCompatActivity {

    @BindView(R.id.riv_heads)
    RoundedImageView rivHeads;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_signature)
    TextView tvSignature;

    private Bitmap head;// 头像Bitmap

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.rl_heads, R.id.rl_name, R.id.rl_sex, R.id.rl_age, R.id.rl_signature})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_heads:
                showDialog();
                break;
            case R.id.rl_name:
                showDialogEdit(1);
                break;
            case R.id.rl_sex:
                OptionPicker optionPicker = new OptionPicker(this, new String[]{"男", "女"});
                optionPicker.setOffset(1);
                optionPicker.setSelectedIndex(0);
                optionPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(int index, String item) {
                        tvSex.setText(item);
                    }

                });
                optionPicker.show();
                break;
            case R.id.rl_age:
                showDialogEdit(2);
                break;
            case R.id.rl_signature:
                showDialogEdit(3);
                break;
        }
    }

    public void showDialog() {
        View view = View.inflate(this, R.layout.item_dialog, null);

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
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        .putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg"))), 2);
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
                    File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));// 裁剪图片
                }
                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        /**
                         * 上传服务器代码
                         */
                        decodeBitmapToFile(head);// 保存在SD卡中
                        rivHeads.setImageBitmap(head);// 用ImageView显示出来
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
            file = new File(Environment.getExternalStorageDirectory(), "head.jpg");
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

    public void showDialogEdit(final int i){

        View view = View.inflate(this, R.layout.item_dialog_edit, null);

        TextView textView = view.findViewById(R.id.tv_name);
        final EditText editText = view.findViewById(R.id.et_text);
        TextView tvNo = view.findViewById(R.id.tv_no);
        TextView tvYes = view.findViewById(R.id.tv_yes);

        if (i == 1){
            textView.setText("修改昵称");
        }else if(i == 2){
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            textView.setText("修改年龄");
        }else{
            textView.setText("修改个性签名");
        }

        final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();

        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvYes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String s = editText.getText().toString();
                if (i == 1){
                    tvName.setText(s);
                }else if(i == 2){
                    tvAge.setText(s);
                }else{
                    tvSignature.setText(s);
                }
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}
