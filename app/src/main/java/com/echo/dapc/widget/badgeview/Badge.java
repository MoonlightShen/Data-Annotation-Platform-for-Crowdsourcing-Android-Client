package com.echo.dapc.widget.badgeview;

import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.view.View;

public interface Badge {

    /**
     * 设置标记的数目
     *
     * @param badgeNum
     * @return
     */
    Badge setBadgeNumber(int badgeNum);

    int getBadgeNumber();

    /**
     * 设置标记文字
     *
     * @param badgeText
     * @return
     */
    Badge setBadgeText(String badgeText);

    String getBadgeText();

    /**
     * 设置是否是精准模式[非精准模式为99+]
     *
     * @param isExact
     * @return
     */
    Badge setExactMode(boolean isExact);

    boolean isExactMode();

    /**
     * 设置是否有阴影
     *
     * @param showShadow
     * @return
     */
    Badge setShowShadow(boolean showShadow);

    boolean isShowShadow();

    /**
     * 设置标记背景颜色
     *
     * @param color
     * @return
     */
    Badge setBadgeBackgroundColor(int color);

    /**
     * 设置边框的样式
     *
     * @param color     边框的颜色
     * @param width     边框的宽度
     * @param isDpValue
     * @return
     */
    Badge stroke(int color, float width, boolean isDpValue);

    int getBadgeBackgroundColor();

    /**
     * 设置标记背景
     *
     * @param drawable
     * @return
     */
    Badge setBadgeBackground(Drawable drawable);

    /**
     * 设置标记背景
     *
     * @param drawable
     * @param clip
     * @return
     */
    Badge setBadgeBackground(Drawable drawable, boolean clip);

    Drawable getBadgeBackground();

    /**
     * 设置标记文字颜色
     *
     * @param color
     * @return
     */
    Badge setBadgeTextColor(int color);

    int getBadgeTextColor();

    /**
     * 设置标记文字
     *
     * @param size
     * @param isSpValue
     * @return
     */
    Badge setBadgeTextSize(float size, boolean isSpValue);

    float getBadgeTextSize(boolean isSpValue);

    Badge setBadgePadding(float padding, boolean isDpValue);

    float getBadgePadding(boolean isDpValue);

    boolean isDraggable();

    /**
     * 设置对齐方式
     *
     * @param gravity
     * @return
     */
    Badge setBadgeGravity(int gravity);

    int getBadgeGravity();

    Badge setGravityOffset(float offset, boolean isDpValue);

    Badge setGravityOffset(float offsetX, float offsetY, boolean isDpValue);

    float getGravityOffsetX(boolean isDpValue);

    float getGravityOffsetY(boolean isDpValue);

    /**
     * 设置拖拽监听,不设置的话，无法拖拽
     *
     * @param l
     * @return
     */
    Badge setOnDragStateChangedListener(OnDragStateChangedListener l);

    PointF getDragCenter();

    /**
     * 绑定控件
     *
     * @param view
     * @return
     */
    Badge bindTarget(View view);

    View getTargetView();

    void hide(boolean animate);

    /**
     * 拖拽状态监听
     */
    interface OnDragStateChangedListener {
        /**
         * 开始拖拽
         */
        int STATE_START = 1;
        /**
         * 正在拖拽
         */
        int STATE_DRAGGING = 2;
        /**
         * 拖拽出区域
         */
        int STATE_DRAGGING_OUT_OF_RANGE = 3;
        /**
         * 拖拽取消
         */
        int STATE_CANCELED = 4;
        /**
         * 拖拽成功
         */
        int STATE_SUCCEED = 5;

        void onDragStateChanged(int dragState, Badge badge, View targetView);
    }
}
