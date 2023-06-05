package com.example.a03_kotlindemo.androidsimple;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a03_kotlindemo.R;
import com.example.a03_kotlindemo.databinding.ActivityAndroidSimpleBinding;
import com.example.a03_kotlindemo.utils.MyFileUtils;

public class AndroidSimpleActivity extends AppCompatActivity {
    private ActivityAndroidSimpleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAndroidSimpleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initFile();
    }

    private final static String PATH = "hj/cq";
    private final static String TEXTFILENAME = "1124.txt";
    private final static String BITMAPFILENAME = "test_bitmap.jpeg";

    private void initFile() {
//        String textPath = MyFileUtils.getOuterPublicPath(PATH, FILENAME);
        String textPath = MyFileUtils.getOuterPrivatePath(this, PATH, TEXTFILENAME);
//        String textPath = MyFileUtils.getInnerPrivatePath(this, PATH, FILENAME);
        String bitmapPath = MyFileUtils.getOuterPrivatePath(this, PATH, BITMAPFILENAME);
        binding.btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //测试StrictMode严格模式
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //保存文本
                        MyFileUtils.saveText(textPath, "文本读取测试:\n" + System.currentTimeMillis());
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test1);
                        //保存图片
                        MyFileUtils.saveImage(bitmapPath, bitmap);
                    }
                }).start();
            }
        });
        binding.btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //读取文本
                String text = MyFileUtils.readText(textPath);
                binding.tvReadTest.setText(text);
                //读取图片采用普通字节流读取
                Bitmap bitmap = MyFileUtils.readImage(bitmapPath);
                binding.ivReadTest.setImageBitmap(bitmap);
                //读取图片采用BitmapFactory读取，底层也是字节流
//                Bitmap bitmap = BitmapFactory.decodeFile(bitmapPath);
//                binding.ivReadTest.setImageBitmap(bitmap);
                //读取图片采用Uri读取
//                binding.ivReadTest.setImageURI(Uri.parse(bitmapPath));
            }
        });
    }
}