package com.qiangxi.ratingbarviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.qiangxi.RatingBarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RatingBarView ratingBarView = (RatingBarView) findViewById(R.id.ratingBarView);
        ratingBarView.setRatingCount(7);//设置RatingBarView总数
        ratingBarView.setSelectedCount(2);//设置RatingBarView选中数
        ratingBarView.setSelectedIconResId(R.drawable.icon_selected);//设置RatingBarView选中的图片id
        ratingBarView.setNormalIconResId(R.drawable.icon_normal);//设置RatingBarView正常图片id
        ratingBarView.setClickable(true);//设置RatingBarView是否可点击
        ratingBarView.setChildPadding(4);//设置RatingBarView的子view的padding
        ratingBarView.setChildMargin(3);//设置RatingBarView的子view左右之间的margin
        ratingBarView.setChildDimension(20);//设置RatingBarView的子view的宽高尺寸
        ratingBarView.setOnRatingBarClickListener(new RatingBarView.RatingBarViewClickListener() {
            @Override
            public void onRatingBarClick(LinearLayout parent, View childView, int position) {
                Log.e("tag", String.valueOf(childView instanceof ImageView) + "," + position);
            }
        });
    }
}
