package com.example.gsuri.sample;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Vibrator;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telecom.Call;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HomeActivity extends Activity implements View.OnClickListener,
        View.OnLongClickListener {

    private EditText mPhoneNumberField;
    private Button mOneButton;
    private Button mTwoButton;
    private Button mThreeButton;
    private Button mFourButton;
    private Button mFiveButton;
    private Button mSixButton;
    private Button mSevenButton;
    private Button mEightButton;
    private Button mNineButton;
    private Button mZeroButton;
    private Button mStarButton;
    private Button mPoundButton;
    private Button mDialButton;
    private Button mDeleteButton;
    private Button mContacts;
    private Button mCallLogs;

    private static final int DURATION = 50; // Vibrate duration

    private Vibrator mVibrator; // Vibration (haptic feedback) for dialer key presses.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);

        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        initUI();
    }

    private void initUI() {
        initializeViews();
        addNumberFormatting();
        setClickListeners();
    }

    private void initializeViews() {
        mPhoneNumberField = (EditText) findViewById(R.id.phone_number);
        mPhoneNumberField.setInputType(android.text.InputType.TYPE_NULL);

        mOneButton = (Button) findViewById(R.id.one);
        mTwoButton = (Button) findViewById(R.id.two);
        mThreeButton = (Button) findViewById(R.id.three);
        mFourButton = (Button) findViewById(R.id.four);
        mFiveButton = (Button) findViewById(R.id.five);
        mSixButton = (Button) findViewById(R.id.six);
        mSevenButton = (Button) findViewById(R.id.seven);
        mEightButton = (Button) findViewById(R.id.eight);
        mNineButton = (Button) findViewById(R.id.nine);
        mZeroButton = (Button) findViewById(R.id.zero);
        mStarButton = (Button) findViewById(R.id.asterisk);
        mPoundButton = (Button) findViewById(R.id.hash);
        mDialButton = (Button) findViewById(R.id.dialButton);
        mDeleteButton = (Button) findViewById(R.id.deleteButton);
        mContacts = (Button) findViewById(R.id.contacts);
        mCallLogs = (Button) findViewById(R.id.logs);
    }

    private void addNumberFormatting() {
        mPhoneNumberField.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }

    private void setClickListeners() {
        mZeroButton.setOnClickListener(this);
        mZeroButton.setOnLongClickListener(this);

        mOneButton.setOnClickListener(this);
        mTwoButton.setOnClickListener(this);
        mThreeButton.setOnClickListener(this);
        mFourButton.setOnClickListener(this);
        mFiveButton.setOnClickListener(this);
        mSixButton.setOnClickListener(this);
        mSevenButton.setOnClickListener(this);
        mEightButton.setOnClickListener(this);
        mNineButton.setOnClickListener(this);
        mStarButton.setOnClickListener(this);
        mPoundButton.setOnClickListener(this);
        mDialButton.setOnClickListener(this);

        mDeleteButton.setOnClickListener(this);
        mDeleteButton.setOnLongClickListener(this);

        mContacts.setOnClickListener(this);
        mCallLogs.setOnClickListener(this);
    }

    private void keyPressed(int keyCode) {
        mVibrator.vibrate(DURATION);
        KeyEvent event = new KeyEvent(KeyEvent.ACTION_DOWN, keyCode);
        mPhoneNumberField.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.one: {
                keyPressed(KeyEvent.KEYCODE_1);
                return;
            }
            case R.id.two: {
                keyPressed(KeyEvent.KEYCODE_2);
                return;
            }
            case R.id.three: {
                keyPressed(KeyEvent.KEYCODE_3);
                return;
            }
            case R.id.four: {
                keyPressed(KeyEvent.KEYCODE_4);
                return;
            }
            case R.id.five: {
                keyPressed(KeyEvent.KEYCODE_5);
                return;
            }
            case R.id.six: {
                keyPressed(KeyEvent.KEYCODE_6);
                return;
            }
            case R.id.seven: {
                keyPressed(KeyEvent.KEYCODE_7);
                return;
            }
            case R.id.eight: {
                keyPressed(KeyEvent.KEYCODE_8);
                return;
            }
            case R.id.nine: {
                keyPressed(KeyEvent.KEYCODE_9);
                return;
            }
            case R.id.zero: {
                keyPressed(KeyEvent.KEYCODE_0);
                return;
            }
            case R.id.hash: {
                keyPressed(KeyEvent.KEYCODE_POUND);
                return;
            }
            case R.id.asterisk: {
                keyPressed(KeyEvent.KEYCODE_STAR);
                return;
            }
            case R.id.deleteButton: {
                keyPressed(KeyEvent.KEYCODE_DEL);
                return;
            }
            case R.id.dialButton: {
                dialNumber();
                return;
            }
            case R.id.contacts: {
                Intent i = new Intent(this,CallLogsActivity.class);
                i.putExtra("isContacts",true);
                startActivity(i);
                return;
            }
            case R.id.logs: {
                Intent i = new Intent(this,CallLogsActivity.class);
                i.putExtra("isContacts",false);
                startActivity(i);
                return;
            }

        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.deleteButton: {
                Editable digits = mPhoneNumberField.getText();
                digits.clear();
                return true;
            }
            case R.id.zero: {
                keyPressed(KeyEvent.KEYCODE_PLUS);
                return true;
            }
        }
        return false;
    }

    private void dialNumber() {
        String number = mPhoneNumberField.getText().toString();
        if (number.length() > 0  && number.length() == 10) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Please grant the permission of calling contacts", Toast.LENGTH_SHORT).show();
                return;
            }
            startActivity(new Intent(Intent.ACTION_CALL,
                    Uri.parse("tel:" + number)));
        }else
        {
            Toast.makeText(this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
        }

    }
}
