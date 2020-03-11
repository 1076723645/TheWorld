package com.smallcat.theworld.minipay;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import com.smallcat.theworld.R;
import com.smallcat.theworld.utils.SystemFitKt;


/**
 * Created by hui on 2017/9/8.
 */

public class ZhiActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTitleTv, mSummeryTv, mTip;
    private static final int ZHI_WAY_WECHAT = 0;//weixin
    private int mZhiWay;
    private ViewGroup mQaView, mZhiBg;
    private ImageView mQaImage;

    /*******config***********/
    private String wechatTip, aliTip;
    @DrawableRes
    private int wechatQaImage, aliQaImage;
    private String aliZhiKey;//支付宝支付码，可从支付二维码中获取

    /*******config***********/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemFitKt.fitSystemAllScroll(this);
        setContentView(R.layout.zhi_activity);
        initView();
        initData();
    }

    private void initView() {
        mTitleTv = findViewById(R.id.zhi_title);
        mSummeryTv = findViewById(R.id.zhi_summery);
        mQaView = findViewById(R.id.qa_layout);
        mZhiBg = findViewById(R.id.zhi_bg);
        mQaImage = findViewById(R.id.qa_image_view);
        mTip = findViewById(R.id.tip);
        mZhiBg.setOnClickListener(this);
    }

    private void initData() {
        Config config = (Config) getIntent().getSerializableExtra(MiniPayUtils.EXTRA_KEY_PAY_CONFIG);
        this.wechatQaImage = config.getWechatQaImage();
        this.aliQaImage = config.getAliQaImage();
        this.wechatTip = config.getWechatTip();
        this.aliTip = config.getAliTip();
        this.aliZhiKey = config.getAliZhiKey();

        if (!checkLegal()) {
            throw new IllegalStateException("MiniPay Config illegal!!!");
        } else {
            if (TextUtils.isEmpty(wechatTip)) wechatTip = getString(R.string.wei_zhi_tip);
            if (TextUtils.isEmpty(aliTip)) aliTip = getString(R.string.ali_zhi_tip);

            mZhiBg.setBackgroundResource(R.drawable.common_bg);
            mTitleTv.setText(R.string.wei_zhi_title);
            mSummeryTv.setText(wechatTip);
            mQaImage.setImageResource(wechatQaImage);
        }

        ObjectAnimator animator = ObjectAnimator.ofFloat(mTip, "alpha", 0, 0.66f, 1.0f, 0);
        animator.setDuration(2666);
        animator.setRepeatCount(6);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.start();
    }


    private boolean checkLegal() {
        return wechatQaImage != 0 && aliQaImage != 0 && !TextUtils.isEmpty(aliZhiKey);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.zhi_btn) {
            if (mZhiWay == ZHI_WAY_WECHAT) {
                WeZhi.startWeZhi(this, mQaView);
            } else {
                AliZhi.startAlipayClient(this, aliZhiKey);
            }
        } else if (v == mZhiBg) {
            if (mZhiWay == ZHI_WAY_WECHAT) {
                mZhiBg.setBackgroundResource(R.color.common_blue);
                mTitleTv.setText(R.string.ali_zhi_title);
                mSummeryTv.setText(aliTip);
                mQaImage.setImageResource(aliQaImage);
            } else {
                mZhiBg.setBackgroundResource(R.drawable.common_bg);
                mTitleTv.setText(R.string.wei_zhi_title);
                mSummeryTv.setText(wechatTip);
                mQaImage.setImageResource(wechatQaImage);
            }
            mZhiWay = ++mZhiWay % 2;
        }

    }
}
