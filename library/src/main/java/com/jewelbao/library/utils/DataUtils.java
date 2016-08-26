package com.jewelbao.library.utils;

import android.content.Context;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kevin on 2015/10/13 0013.
 * 数据相关工具
 */
public class DataUtils {

    /**
     * 密码正则
     */
    public static final String passwordPattern = "^[A-Za-z0-9_]{6,15}$";
    /**
     * 手机号正则
     */
    public static final String mobilePattern = "^1[0-9]{10}$";

    /**
     * 正则判断
     *
     * @param input   需要判断的字符串
     * @param pattern 正则表达式
     * @return Boolean
     */
    public static boolean Regex(String input, String pattern) {
        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(input);
        return matcher.matches();
    }

    /**
     * 保存文件
     *
     * @param filename 文件名称
     * @param content  文件内容
     */
    public static void save(Context context, String filename, String content) throws Exception {

		/*
         * 1、根据上下文对象能快速得到一个文件输出流对象；
		 * 2、私有操作模式：创建出来的文件只能被本应用访问，其他应用无法访问该文件：Context.MODE_PRIVATE；
		 * 另外采用私有操作模式创建的文件，写入的内容会覆盖原文件的内容。
		 * 3、openFileOutput()方法的第一个参数用于指定文件名称，不能包含路径分隔符"/"，如果文件不存在，
		 * Android会自动创建它，创建的文件保存在/data/data/<package
		 * name>/files目录，如/data/data/org.example.files/files.
		 */
        FileOutputStream outStream = context.openFileOutput(filename,
                Context.MODE_PRIVATE);
        // 把字符串传化为二进制数据写入到文件中
        outStream.write(content.getBytes());
        // 然后关掉这个流
        outStream.close();

    }

    /**
     * 使用追加模式保存文件
     *
     * @param filename
     * @param content
     * @throws Exception
     */
    public static void saveAppend(Context context, String filename, String content) throws Exception {

		/*
         * 1、根据上下文对象能快速得到一个文件输出流对象；
		 * 2、追加操作模式：创建出来的文件只能被本应用访问，其他应用无法访问该文件：Context.MODE_PRIVATE；
		 * 另外采用追加操作模式创建的文件，先会检查文件是否存在，存在就往文件追加内容，否则就创建新文件。
		 * 3、openFileOutput()方法的第一个参数用于指定文件名称，不能包含路径分隔符"/"，如果文件不存在，
		 * Android会自动创建它，创建的文件保存在/data/data/<package
		 * name>/files目录，如/data/data/org.example.files/files.
		 */
        FileOutputStream outStream = context.openFileOutput(filename,
                Context.MODE_APPEND);
        // 把字符串传化为二进制数据写入到文件中
        outStream.write(content.getBytes());
        // 然后关掉这个流
        outStream.close();

    }

    /**
     * 读取文件内容
     *
     * @param filename 文件名称
     * @return 文件内容
     * @throws Exception
     */
    public static String read(Context context, String filename) throws Exception {
        /*
		 * 1、从上下文对象中得到一个文件输入流对像，context.openFileInput(filename)得到文件输入流对象； 2、
		 */
        FileInputStream inStream = context.openFileInput(filename);
        // 把每次读到的数据都存放在内存中
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        // 定义数组大小
        byte[] buffer = new byte[1024];
        int len = 0;
        // 读取这个输入流数组,判断数据是否读完
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        // 从内存中获取得到的数据
        byte[] data = outStream.toByteArray();
        // 转化为字符
        return new String(data);
    }

    /**
     * 保存文本至文件数据中
     *
     * @param text 需要保存的文本
     */
    public static void saveHistory(Context context, String fileName, String text) {
        try {
            List<String> temp = getHistory(context, fileName);
            if (!temp.isEmpty()) { // 判断文件是否存在数据
                if (!temp.contains(text)) { // 判断当前号码是否已保存在文件中,不存在则保存
                    if (temp.size() >= 10) {
                        String history = getHistoryString(context, fileName);
                        if (!TextUtils.isEmpty(history)) {
                            save(context, fileName, history.substring(history.indexOf(",")+1) + "," + text);
                        }
                    } else {
                        saveAppend(context, fileName, ","
                                + text);
                    }
                }
            } else { // 如果文件不存在数据,以追加文本的形式保存数据
                saveAppend(context, fileName, text);
//                SharedPrefUtil.saveBoolean(Global.KEY_HASHISTORY, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文本记录转化为List输出
     *
     * @return
     */
    public static List<String> getHistory(Context context, String fileName) {
        List<String> phones = new ArrayList<>();

        String history = getHistoryString(context, fileName);
        if (!TextUtils.isEmpty(history))
            phones = java.util.Arrays.asList(history.split(","));

        return phones;
    }

    // 读取历史记录
    private static String getHistoryString(Context context, String fileName) {
        String historyPhone = null;
//        if (SharedPrefUtil.getBoolean(Global.KEY_HASHISTORY, false)) {
            try {
                historyPhone = read(context, fileName);
            } catch (Exception e) {
                return null;
            }
//        }
        return historyPhone;
    }

    /**
     * 清除内容
     * @return
     */
    public static boolean clearHistory(Context context, String fileName) {
        try {
            save(context, fileName, "");
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 格式化数据大小
     * @param size 传入的大小
     * @return  格式化单位返回格式化之后的值
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                          .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                          .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                          .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }
}
