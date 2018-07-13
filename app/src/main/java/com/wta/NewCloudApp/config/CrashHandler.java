package com.wta.NewCloudApp.config;

import android.content.Context;

import com.jess.arms.utils.DataHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private Context context;
    private static final String LOG_NAME = "crash.log";

    public CrashHandler(Context context) {
        this.context = context;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        throwable.printStackTrace();
        new Thread() {
            @Override
            public void run() {
                writeToFile(throwable);
            }
        }.start();
    }

    private void writeToFile(Throwable t) {
        StringWriter sw = new StringWriter(256);
        PrintWriter pw = new PrintWriter(sw, false);
        t.printStackTrace(pw);
        pw.flush();
        StringBuilder sb = new StringBuilder();
        sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date(System.currentTimeMillis())))
                .append("\n")
                .append(sw.toString())
                .append("\n\n\n");
        File file = new File(DataHelper.getCacheFile(context), LOG_NAME);
        FileOutputStream fo = null;
        try {
            if (!file.exists()) file.createNewFile();
            fo = new FileOutputStream(file, true);
            fo.write(sb.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
            if (fo != null) {
                try {
                    fo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
