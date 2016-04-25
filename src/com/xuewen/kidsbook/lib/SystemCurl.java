package com.xuewen.kidsbook.lib;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by root on 16-3-4.
 */
public class SystemCurl {
    private static String INPUT_STREAM = "INPUTSTREAM";
    private static String ERROR_STREAM = "ERRORSTREAM";
    /*
     * 标准流与错误流读取线程
     */
    private static class ReadThread extends Thread {
        private InputStream is;
        private String[] resultArr;
        private String type;
        private Object mutex;

        public ReadThread(InputStream is, String type, String[] resultArr, Object mutex) {
            this.is = is;
            this.type = type;
            this.resultArr = resultArr;
            this.mutex = mutex;
        }

        public void run() {
            synchronized (mutex) {
                try {
                    int readInt = is.read();
                    ArrayList result = new ArrayList();

                    /*
                     * 这里读取时我们不要使用字符流与缓冲流，发现执行某些命令时会阻塞，不
                     * 知道是什么原因。所有这里使用了最原始的流来操作，就不会出现问题。
                     */
                    while (readInt != -1) {
                        result.add(Byte.valueOf(String.valueOf((byte) readInt)));
                        readInt = is.read();
                    }

                    byte[] byteArr = new byte[result.size()];
                    for (int i = 0; i < result.size(); i++) {
                        byteArr[i] = ((Byte) result.get(i)).byteValue();
                    }
                    if (ERROR_STREAM.equals(this.type)) {
                        resultArr[1] = new String(byteArr);
                    } else {
                        resultArr[0] = new String(byteArr);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String[] visit(String url) throws IOException, InterruptedException {
        String curl_cmd = "/usr/bin/curl " + url;
        System.out.println("command is: " + curl_cmd);
        Process process = Runtime.getRuntime().exec(curl_cmd);

        String result[] = new String[2];
        Object mutexInstream = new Object();
        Object mutexErrorstream = new Object();

        new ReadThread(process.getInputStream(), INPUT_STREAM, result, mutexInstream).start();
        new ReadThread(process.getErrorStream(), ERROR_STREAM, result, mutexErrorstream).start();

        Thread.sleep(20);

        synchronized (mutexInstream) {
            synchronized (mutexErrorstream) {
                /*
                 * 导致当前线程等待，如果必要，一直要等到由该 Process 对象表示的进程已经终止
                 * 。如果已终止该子进程，此方法立即返回。如果没有终止该子进程，调用的线程将被
                 * 阻塞，直到退出子进程。
                 * process.waitFor()目的是等待子进程完成后再往下执行，不过这里好像没有什么
                 * 太大的作用除了用来判断返回的状态码外，因为如果程序进到这里表示子线程已执行完
                 * 毕，process子进程理所当然的也已执行完毕，如果子进程process未执行完，我想
                 * 读流的操作肯定会阻塞的。
                 *
                 * 另外，使用process.waitFor()要注的是一定不要在数据流读取前使用，否则线程
                 * 也会挂起，导致该现象的原因可能是该命令的输内容出比较多，而运行窗口的输出缓冲
                 * 区不够大，最后没不能写缓冲引起，所以这里先使用了两个单独的线程去读，这样不管
                 * 数据量有多大，都不会阻塞了。
                 */
                if (process.waitFor() != 0) {
                    result[0] = null;
                } else
                    result[1] = null;
            }
        }

        return result;
    }

    public String[] visit(String url, Map<String, String> params) throws IOException, InterruptedException {
        String tempurl = url;

        if (params.size() > 0) {
            if (!url.contains("?")) {
                tempurl = tempurl + "?";
            }

            Iterator it = params.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                if (tempurl.endsWith("?")) {
                    tempurl = tempurl + key + "=" + value;
                } else
                    tempurl = tempurl + "&" + key + "=" + value;
            }
        }
        System.out.println("URL Is : " + tempurl);
        String [] result = this.visit(tempurl);

        /*
        System.out.println("result is : " + result[0]);
        System.out.println("result is : " + result[1]);
        */

        return result;
    }

    public static void main(String [] args) {
        Map<String, String> detail_query_param = new HashMap<>();
        detail_query_param.put("time_code", "29f8fe56feb60dd9cc5e91d8ac93284e");
        detail_query_param.put("is_abtest", "0");
        detail_query_param.put("img_size", "b");
        detail_query_param.put("pid", "22791395");
        detail_query_param.put("client_version", "6.0.9");
        detail_query_param.put("action", "get_product");
        detail_query_param.put("union_id", "537-100114");
        detail_query_param.put("timestamp", "1455547650");
        detail_query_param.put("udid", "87e5b86170039e1338e8a78c6cd4c066");
        detail_query_param.put("expand", "1,2,3,4,5,6");
        detail_query_param.put("user_client", "android");
        detail_query_param.put("permanent_id", "20160215210106878724230811076647536");

        SystemCurl curl = new SystemCurl();
        try {
            curl.visit("http://mapi.dangdang.com/index.php", detail_query_param);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
