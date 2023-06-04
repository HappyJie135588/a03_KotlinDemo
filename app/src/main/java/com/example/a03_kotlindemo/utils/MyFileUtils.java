package com.example.a03_kotlindemo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 文件流读写工具类，包括获取指定路径与图文读取
 */
public class MyFileUtils {
    private static final String TAG = "MyFileUtils";

    /**
     * 外部存储的公共空间,卸载不会被删除
     *
     * @param path     自定义路径
     * @param fileName 自定义文件名
     * @return 绝对路径
     */
    public static String getOuterPublicPath(@Nullable String path, String fileName) {
        ///storage/emulated/0/Download/hj/cq/mtrace.trace
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (!TextUtils.isEmpty(path)) {
            dir = new File(dir, path);
        }
        return createPath(dir, fileName);
    }

    /**
     * 外部存储的私有空间，卸载会被删除
     *
     * @param context  上下文
     * @param path     自定义路径
     * @param fileName 自定义文件名
     * @return 绝对路径
     */
    public static String getOuterPrivatePath(Context context, @Nullable String path, String fileName) {
        ///storage/emulated/0/Android/data/com.example.a03_kotlindemo/files/Download/hj/cq/mtrace.trace
        File dir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (!TextUtils.isEmpty(path)) {
            dir = new File(dir, path);
        }
        return createPath(dir, fileName);
    }

    /**
     * 内部存储私有空间
     *
     * @param context  上下文
     * @param path     自定义路径
     * @param fileName 自定义文件
     * @return 绝对路径
     */
    public static String getInnerPrivatePath(Context context, @Nullable String path, String fileName) {
        ///data/user/0/com.example.a03_kotlindemo/files/hj/cq/mtrace.trace打印的是这个目录
        ///data/data/com.example.a03_kotlindemo/files/hj/cq/mtrace.trace实际查看是这个目录
        File dir = context.getFilesDir();
        if (!TextUtils.isEmpty(path)) {
            dir = new File(dir, path);
        }
        return createPath(dir, fileName);
    }

    /**
     * 检测文件是否存在，不存在就先创建文件
     *
     * @param path
     * @param fileName
     * @return
     */
    private static String createPath(File path, String fileName) {
        if (!path.exists()) {
            boolean mkdirs = path.mkdirs();
            Log.d(TAG, "createPath: 创建目录" + mkdirs);
        } else {
            Log.d(TAG, "createPath: 目录存在" + path);
        }
        File file = new File(path, fileName);
        if (!file.exists()) {
            try {
                Log.d(TAG, "createPath: " + file);
                boolean newFile = file.createNewFile();
                Log.d(TAG, "createPath: 创建文件" + newFile);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "createPath: 创建文件失败");
            }
        } else {
            Log.d(TAG, "createPath: 文件存在" + file);
        }
        return file.getPath();
    }

    /**
     * 保存字符
     *
     * @param path
     * @param str
     */
    public static void saveText(String path, String str) {
        BufferedWriter bos = null;
        try {
            bos = new BufferedWriter(new FileWriter(path));
            bos.write(str);
            Log.d(TAG, "saveText: 写入成功：" + str);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "saveText: 写入失败：" + str);
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读取字符
     *
     * @param path
     * @return
     */
    public static String readText(String path) {
        BufferedReader bis = null;
        StringBuilder sb = new StringBuilder();
        try {
            bis = new BufferedReader(new FileReader(path));
            String line = null;
            while ((line = bis.readLine()) != null) {
                sb.append(line + "\n");
            }
            Log.d(TAG, "readText: 读取成功：" + sb);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    /**
     * 保存图片
     *
     * @param path
     * @param bitmap
     */
    public static void saveImage(String path, Bitmap bitmap) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            Log.d(TAG, "saveImage: 保存成功：" + path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读取图片
     *
     * @param path
     * @return
     */
    public static Bitmap readImage(String path) {
        Bitmap bitmap = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }
}
