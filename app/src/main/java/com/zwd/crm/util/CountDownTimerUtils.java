package com.zwd.crm.util;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.zwd.crm.R;

/**
 * Created by asus-pc on 2017/5/9.
 */

public class CountDownTimerUtils extends CountDownTimer {
    private TextView mTextView;

    public CountDownTimerUtils(TextView mTextView,long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mTextView = mTextView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setClickable(false);//设置不可点击
        mTextView.setText("重新获取"+"("+millisUntilFinished /1000+")");//设置倒计时
        mTextView.setBackgroundResource(R.drawable.corner_gray_3dp_press);//设置按钮为灰色，不能点击

        SpannableString spannableString = new SpannableString(mTextView.getText().toString());//获取按钮上的文字
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.WHITE);

        spannableString.setSpan(foregroundColorSpan,0,2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);//字体设置颜色
        mTextView.setText(spannableString);
    }

    @Override
    public void onFinish() {
        mTextView.setText("重新获取");
        mTextView.setClickable(true);
        mTextView.setBackgroundResource(R.drawable.corner_gray_3dp_nomal);
    }


}
