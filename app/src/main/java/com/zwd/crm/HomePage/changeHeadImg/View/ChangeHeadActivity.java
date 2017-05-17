package com.zwd.crm.HomePage.changeHeadImg.View;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.zwd.crm.HomePage.changeHeadImg.Module.changeHeadingPost;
import com.zwd.crm.R;
import com.zwd.crm.base.BaseActivity;
import com.zwd.crm.base.BaseApplication;
import com.zwd.crm.internet.CustomCallBack;
import com.zwd.crm.internet.RemoteDataResult;
import com.zwd.crm.internet.RemoteOptionIml;
import com.zwd.crm.layout.layout_action_bar;
import com.zwd.crm.util.LogUtils;
import com.zwd.crm.util.ToastUtils;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Response;

public class ChangeHeadActivity extends BaseActivity {
    @Bind(R.id.change_head_img_action_bar)
    layout_action_bar actionBar;
    @Bind(R.id.iv_change_img_head_img)
    ImageView ivHeadImg;
    @Bind(R.id.btn_take_photo)
    Button btnTakePhoto;
    @Bind(R.id.btn_upload_from_rom)
    Button btnUploadFromRom;

    private int userId;
    private String token="";
    private String baseUrl="http://139.224.164.183:8088/";
    private String url="Users_EditHeadImg.aspx";


    private static final int TAKE_PHOTO=1;
    //调用系统相册-选择图片
    private static final int IMAGE = 2;
    private Bitmap bitmap;
    private String fileName;


    protected int getContentView(){
        return R.layout.activity_change_head;
    }

    protected void initViews() {
        initActionBar(actionBar);
        Bundle bundle = getIntent().getExtras();
        userId = bundle.getInt("userid");
        token = bundle.getString("token");
        ToastUtils.showShort(this,String.valueOf(userId));

    }

    private void initActionBar(layout_action_bar actionBar) {
        actionBar.setTitle("修改头像");
        actionBar.setPriviousClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =getIntent();
                intent.putExtra("filename",fileName);
                ChangeHeadActivity.this.setResult(0,intent);
                ChangeHeadActivity.this.finish();
            }
        });
        actionBar.setImage(R.mipmap.finish);
        actionBar.setAddClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    uploadImageToQiniu(fileName,token);
            }
        });
    }

    /**
     * 七牛上传
     * @param filePath
     * @param token
     * @return
     */
    public void uploadImageToQiniu(String filePath, String token) {
        UploadManager uploadManager = new UploadManager();
        // 设置图片名字
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String key = "icon_" + sdf.format(new Date())+"CRM";
        uploadManager.put(filePath, key, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject res) {
                Log.d("error:", info.toString());

                // info.error中包含了错误信息，可打印调试
                // 上传成功后将key值上传到自己的服务器
                if(key!=null){
                    Log.d("headkey",key);
                    btn_changeHead(key);
                }

            }
        }, null);
    }

    /**
     * 调接口
     *
     */
    public void btn_changeHead(String outkey){
        if(outkey!=null){
            changeHeadingPost post = new changeHeadingPost();
            post.setId(userId);post.setUrl(outkey);
            RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
            remoteOptionIml.Users_EditHeadImg(post, baseUrl, url, new CustomCallBack<RemoteDataResult>() {
                @Override
                public void onSuccess(Response<RemoteDataResult> response) {

                    ToastUtils.showShort(ChangeHeadActivity.this,response.body().getResultMessage());
                }
                @Override
                public void onFail(String message) {
                    Log.d("falure:",message);
                }
            });
        }

    }
    
    @OnClick({R.id.btn_take_photo,R.id.btn_upload_from_rom})
    public void click(View v){
        switch (v.getId()){
            case R.id.btn_take_photo:
                startActivityForResult(new Intent("android.media.action.IMAGE_CAPTURE"), TAKE_PHOTO);
                break;
            case R.id.btn_upload_from_rom:
                //调用相册
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent intent) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String sdStatus = Environment.getExternalStorageState();
                    if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
                        Log.i("TestFile",
                                "SD card is not avaiable/writeable right now.");
                    }
                    new DateFormat();
                    //以时间作为文件名
                    String name = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
                    Bundle bundle = intent.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data"); //获取相机返回的数据，转换为Bitmap格式

                    FileOutputStream b = null;
                    File file = new File(BaseApplication.SD_LOCAL + "/Image");
                    boolean mkdirs = file.mkdirs();
                    Log.w(TAG_LOG, String.valueOf(mkdirs));
                    fileName = BaseApplication.SD_LOCAL + "/Image/" + name;

                    try {
                        b = new FileOutputStream(fileName);
                        if (bitmap != null) {
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);
                        } else {
                            LogUtils.e(TAG_LOG, "onActivityResult", "bitmap is null!");
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            assert b != null;
                            b.flush();
                            b.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    ivHeadImg.setImageBitmap(bitmap);
                    break;
                }
                    break;
                    case 2:
                        if (resultCode == RESULT_OK && intent != null) {
                            Uri uri = intent.getData();
                            Cursor cursor = getContentResolver().query(uri, null,
                                    null, null, null);
                            assert cursor != null;
                            cursor.moveToFirst();
                            ContentResolver contentResolver = getContentResolver();
                            try {
                                bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri));
                                ivHeadImg.setImageBitmap(bitmap);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            String imgPath = cursor.getString(1); // 图片文件路径
                            cursor.close();
                            fileName = imgPath;
                        }
                        break;

                }
        }
    /**
     *键盘返回
     */
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent =getIntent();
            intent.putExtra("filename",fileName);
            ChangeHeadActivity.this.setResult(0,intent);
            ChangeHeadActivity.this.finish();
        }
        return true;
    }


}






