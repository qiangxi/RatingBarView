package com.qiangxi;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.qiangxi.exception.IllegalNumberException;

/**
 * @author qiangxi
 *         组合控件,imageView+LinearLayout实现RatingBar
 */
public class RatingBarView extends LinearLayout implements View.OnClickListener {
    private static final int DEFAULT_CHILD_DIMENSION = 25;//默认imageView的大小,宽高相等
    private static final int DEFAULT_CHILD_PADDING = 0;//默认imageView的内部padding,可以用来改变图片的大小
    private static final int DEFAULT_CHILD_MARGIN = 5;//imageView之间的距离
    private static final int DEFAULT_TOTAL_COUNT = 5;//默认Rating数量
    private static final int DEFAULT_SELECTED_COUNT = 1;//默认被选中的Rating数量
    private static final int DEFAULT_NORMAL_ICON_RES_ID = R.drawable.icon_rating_normal;//默认图片资源id
    private static final int DEFAULT_SELECTED_ICON_RES_ID = R.drawable.icon_rating_selected;//选中图片资源id
    private static final int DEFAULT_TAG_ID = R.id.RatingBarViewTagId;//TAG ID

    private int mTotalCount = DEFAULT_TOTAL_COUNT;
    private int mSelectedCount = DEFAULT_SELECTED_COUNT;
    private int mNormalIconResId = DEFAULT_NORMAL_ICON_RES_ID;
    private int mSelectedIconResId = DEFAULT_SELECTED_ICON_RES_ID;
    private int childPadding = DEFAULT_CHILD_PADDING;
    private int childMargin = DEFAULT_CHILD_MARGIN;
    private int mChildDimension = DEFAULT_CHILD_DIMENSION;
    private boolean mClickable;//子view是否可点击

    private RatingBarViewClickListener mListener;
    private int mPosition;//点击的位置

    public RatingBarView(Context context) {
        this(context, null);
    }

    public RatingBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RatingBarView);
        int indexCount = a.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.RatingBarView_totalCount) {
                mTotalCount = a.getInt(attr, DEFAULT_TOTAL_COUNT);
                checkIsReasonableNumber(mTotalCount);
            } else if (attr == R.styleable.RatingBarView_selectedCount) {
                mSelectedCount = a.getInt(attr, DEFAULT_SELECTED_COUNT);
                checkIsReasonableNumber(mSelectedCount);
                checkSelectedCount();
            } else if (attr == R.styleable.RatingBarView_selectedDrawable) {
                mSelectedIconResId = a.getResourceId(attr, DEFAULT_SELECTED_ICON_RES_ID);
            } else if (attr == R.styleable.RatingBarView_normalDrawable) {
                mNormalIconResId = a.getResourceId(attr, DEFAULT_NORMAL_ICON_RES_ID);
            } else if (attr == R.styleable.RatingBarView_clickable) {
                mClickable = a.getBoolean(attr, false);
            } else if (attr == R.styleable.RatingBarView_childMargin) {
                childMargin = a.getDimensionPixelSize(attr, DEFAULT_CHILD_MARGIN);
                checkIsReasonableNumber(childMargin);
            } else if (attr == R.styleable.RatingBarView_childPadding) {
                childPadding = a.getDimensionPixelSize(attr, DEFAULT_CHILD_PADDING);
                checkIsReasonableNumber(childPadding);
            } else if (attr == R.styleable.RatingBarView_childDimension) {
                mChildDimension = a.getDimensionPixelSize(attr, DEFAULT_CHILD_DIMENSION);
                checkIsReasonableNumber(mChildDimension);
            }
        }
        a.recycle();
        initLayout();
    }

    /**
     * 检查数字或尺寸是否合理
     */
    private void checkIsReasonableNumber(int numberOrDimension) {
        if (numberOrDimension < 0) {
            throw new IllegalNumberException("任何数字或尺寸不可小于0");
        }
    }

    private void checkSelectedCount() {
        if (mSelectedCount > mTotalCount) {
            mSelectedCount = mTotalCount;
        }
    }

    private void initLayout() {
        if (getChildCount() != 0) {
            removeAllViews();
        }
        final LayoutParams lp = new LayoutParams(dpToPx(mChildDimension), dpToPx(mChildDimension));
        lp.gravity = Gravity.CENTER_VERTICAL;
        lp.rightMargin = dpToPx(childMargin);
        for (int position = 0; position < mTotalCount; position++) {
            final ImageView imageView = new ImageView(getContext());
            imageView.setLayoutParams(lp);
            imageView.setTag(DEFAULT_TAG_ID, position);
            imageView.setPadding(dpToPx(childPadding), dpToPx(childPadding), dpToPx(childPadding), dpToPx(childPadding));
            imageView.setOnClickListener(this);
            if (position < mSelectedCount) {
                imageView.setImageResource(mSelectedIconResId);
            } else {
                imageView.setImageResource(mNormalIconResId);
            }
            addView(imageView);
        }
    }

    @Override
    public void onClick(View v) {
        if (mClickable) {
            int position = (int) v.getTag(DEFAULT_TAG_ID);
            if (mListener != null) {
                mListener.onRatingBarClick(this, v, position + 1);
            }
            updateLayoutIfNeeded(position);
        }
    }

    private void updateLayoutIfNeeded(int position) {
        int childCount = getChildCount();
        if (childCount == 0) {
            return;
        }
        //当前点击的position与上次点击的position相同,则return,节省性能
        if (mPosition == position) {
            return;
        }
        mPosition = position;
        updateLayout(childCount, position);
    }

    private void updateLayout(int childCount, int position) {
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView == null) {
                break;
            }
            final ImageView imageView = (ImageView) childView;
            if (i <= position) {
                imageView.setImageResource(mSelectedIconResId);
            } else {
                imageView.setImageResource(mNormalIconResId);
            }
        }
    }

    /**
     * 设置子view数量
     */
    public void setRatingCount(int count) {
        mTotalCount = count;
        checkIsReasonableNumber(mTotalCount);
        initLayout();
    }

    /**
     * 设置未选中图片资源id
     */
    public void setNormalIconResId(@DrawableRes int normalIconResId) {
        mNormalIconResId = normalIconResId;
        updateLayout(getChildCount(), mPosition);
    }

    /**
     * 设置选中图片资源id
     */
    public void setSelectedIconResId(@DrawableRes int selectedIconResId) {
        mSelectedIconResId = selectedIconResId;
        updateLayout(getChildCount(), mPosition);
    }

    /**
     * 设置子view的padding
     */
    public void setChildPadding(int childPadding) {
        this.childPadding = childPadding;
        checkIsReasonableNumber(childPadding);
        initLayout();
    }

    /**
     * 设置子view之间的左右margin
     */
    public void setChildMargin(int childMargin) {
        this.childMargin = childMargin;
        checkIsReasonableNumber(childMargin);
        initLayout();
    }

    /**
     * 设置每个子view的宽高，宽=高
     */
    public void setChildDimension(int childDimension) {
        mChildDimension = childDimension;
        checkIsReasonableNumber(mChildDimension);
        initLayout();
    }

    /**
     * 设置选中的数量
     */
    public void setSelectedCount(int selectedCount) {
        mSelectedCount = selectedCount;
        checkIsReasonableNumber(mChildDimension);
        checkSelectedCount();
        updateLayout(getChildCount(), selectedCount);
    }

    /**
     * 设置RatingBarView是否可点击
     */
    @Override
    public void setClickable(boolean clickable) {
        mClickable = clickable;
    }

    /**
     * 为RatingBarView设置点击监听
     */
    public void setOnRatingBarClickListener(RatingBarViewClickListener listener) {
        mListener = listener;
    }

    public interface RatingBarViewClickListener {
        /**
         * 当RatingBarView设置为可点击的且被点击时触发
         *
         * @param parent    RatingBarView本身，
         * @param childView 子view，即ImageView
         * @param position  点击的子view的位置
         */
        void onRatingBarClick(LinearLayout parent, View childView, int position);
    }

    private int dpToPx(int dp) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) ((dp * scale) + 0.5f);
    }
}
