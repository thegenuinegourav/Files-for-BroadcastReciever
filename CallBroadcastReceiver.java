package com.example.gsuri.sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

public class CallBroadcastReciever extends BroadcastReceiver {

    private WindowManager wm;
    private static LinearLayout ly1;
    private WindowManager.LayoutParams params1;

    @Override
    public void onReceive(Context context,Intent intent) {

        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        // Adds a view on top of the dialer app when it launches.
        if(state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
            wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            params1 = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_SYSTEM_ALERT |
                    WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSPARENT);

            params1.height = 75;
            params1.width = 512;
            params1.x = 265;
            params1.y = 400;
            params1.format = PixelFormat.TRANSLUCENT;

            ly1 = new LinearLayout(context);
            ly1.setBackgroundColor(Color.BLACK);
            ly1.setOrientation(LinearLayout.VERTICAL);

            wm.addView(ly1, params1);
        }

        // To remove the view once the dialer app is closed.
        if(intent.getAction().equals("android.intent.action.PHONE_STATE")){
            String state1 = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if(state1.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                if(ly1!=null)
                {
                    wm.removeView(ly1);
                    ly1 = null;
                }
            }
        }

    }
}

