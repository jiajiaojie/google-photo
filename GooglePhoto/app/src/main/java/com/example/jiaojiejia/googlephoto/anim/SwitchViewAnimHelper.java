package com.example.jiaojiejia.googlephoto.anim;

import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * 视图切换动画
 * Created by jiaojie.jia on 2017/3/21.
 */

public class SwitchViewAnimHelper {

    /** 显示动画（缩放）*/
    private ObjectAnimator mShowScaleX;
    private ObjectAnimator mShowScaleY;
    /** 显示动画集合 */
    private AnimatorSet mShowSet;

    public static SwitchViewAnimHelper instance;

    private SwitchViewAnimHelper() {
        mShowSet = new AnimatorSet();
    }

    public static SwitchViewAnimHelper getInstance() {
        if(instance == null) {
            synchronized (SwitchViewAnimHelper.class) {
                if(instance == null) {
                    instance = new SwitchViewAnimHelper();
                }
            }
        }
        return instance;
    }

    /**
     * 放大动画
     * @param showView
     */
    public void toLargeView(final View showView) {
        mShowScaleX = ObjectAnimator.ofFloat(showView, "scaleX", 0.95f, 1.0f);
        mShowScaleY = ObjectAnimator.ofFloat(showView, "scaleY", 0.95f, 1.0f);

        mShowSet.play(mShowScaleX).with(mShowScaleY);
        mShowSet.setDuration(250);
        mShowSet.setInterpolator(new DecelerateInterpolator());

        mShowSet.start();
    }

    /**
     * 缩小动画
     * @param showView
     */
    public void toSmallView(final View showView) {
        mShowScaleX = ObjectAnimator.ofFloat(showView, "scaleX", 1.05f, 1.0f);
        mShowScaleY = ObjectAnimator.ofFloat(showView, "scaleY", 1.05f, 1.0f);

        mShowSet.play(mShowScaleX).with(mShowScaleY);
        mShowSet.setDuration(250);
        mShowSet.setInterpolator(new DecelerateInterpolator());

        mShowSet.start();
    }

    /**
     * 当前是否有动画正在执行
     * @return
     */
    public boolean isAnimRunning() {
        return (mShowScaleX != null && mShowScaleY != null && mShowSet != null)
                && (mShowScaleX.isRunning() || mShowScaleY.isRunning());
    }
}
