package com.sutpc.floatmenutest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.yw.game.floatmenu.FloatLogoMenu;
import com.yw.game.floatmenu.FloatMenuView;

/**
 * 坐标系
 * ------------------------> X
 * ┇
 * ┇
 * ┇
 * ┇
 * Y
 *  demo介绍：
 *  imgLayoutMethod ： 使用layout方法移动view，并加入了view点击事件检测
 *  imgTranslationMethod： 使用Translation方法移动view，
 * 参考文章：
 * 获取view的各种位置：https://blog.csdn.net/l25000/article/details/49499585
 * MotionEvent 介绍：https://blog.csdn.net/vansbelove/article/details/78416791
 * View拖拽技巧：https://blog.csdn.net/qq_17810899/article/details/96829458
 * OnTouch 和OnClick会存在事件冲突 https://blog.csdn.net/u014043113/article/details/74778414
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    int lastX =0;
    int lastY =0;

    int lastX2 = 0;
    int lastY2 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imgLayoutMethod = findViewById(R.id.img);
        imgLayoutMethod.setOnTouchListener((v, event) -> {
            // v.getLeft ..  获取的是view相对于父布局的位置
            int left = v.getLeft();
            int top = v.getTop();
            int right = v.getRight();
            int bottom = v.getBottom();

            //event.getX   event.getY  此方法获取的是手指相对于view的左上角的x,y值
            int x = (int) event.getX();
            int y = (int) event.getY();
            switch(event.getAction()) {
                //第一个 手指 初次接触到屏幕 时触发
                case MotionEvent.ACTION_DOWN:
                    //记录初次接触屏幕时的 x y 坐标（手指相对于view的左上角的值）
                    lastX = x;
                    lastY = y;
                    break;
                 //手指 在屏幕上滑动 时触发，会多次触发
                case MotionEvent.ACTION_MOVE:
                   int offsetX = x - lastX;
                   int offsetY = y - lastY;
                   //限制只能上下滑动
                   v.layout(left,top + offsetY,right,bottom +offsetY);
                   //任意滑动
                    //v.layout(left + offsetX , top + offsetY, right + offsetX , bottom + offsetY);
                    break;
                 //最后一个 手指 离开屏幕 时触发。
                case MotionEvent.ACTION_UP:
                    //注：在拖动过按钮后，如果其他view刷新导致重绘，会让按钮重回原点，所以需要更改布局参数
                    int absOffsetX = Math.abs( x - lastX) ;
                    int absOffsetY = Math.abs( y- lastY);
                    int thresholdOffsetPx = ScreenUtils.dp2px(getApplicationContext(),5);
                    if (absOffsetX < thresholdOffsetPx && absOffsetY < thresholdOffsetPx ){
                        Log.i(TAG, "img 移动小于5dp ");
                        //如果上下左右移动都小于 5dp ， 则认为是点击事件
                        //注OnTouch 和OnClick会存在事件冲突，根据AS提示，如下
                        //onTouch lambda should call View#performClick when a click is detected
                        v.performClick();
                    }

                    break;
                default:
                    break;
            }
            return true;
        });
        imgLayoutMethod.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(),"点击了第一张img",Toast.LENGTH_LONG).show();
            Log.i(TAG, "onClick: 点击了第一张img");
        });


        ImageView imgTranslationMethod = findViewById(R.id.img2);
        imgTranslationMethod.setOnTouchListener((v,event)->{
            //使用 TranslationX()、TranslationY()方法移动view
            //event.getX   event.getY  此方法获取的是手指相对于view的左上角的x,y值
            int x = (int) event.getX();
            int y = (int) event.getY();
            switch(event.getAction()) {
                //第一个 手指 初次接触到屏幕 时触发
                case MotionEvent.ACTION_DOWN:
                    //记录初次接触屏幕时的 x y 坐标（手指相对于view的左上角的值）
                    lastX2 = x;
                    lastY2 = y;
                    break;
                //手指 在屏幕上滑动 时触发，会多次触发
                case MotionEvent.ACTION_MOVE:
                    int offsetX = x - lastX2;
                    int offsetY = y - lastY2;
                    //任意滑动
                    //v.layout(left + offsetX , top + offsetY, right + offsetX , bottom + offsetY);
                    v.setTranslationX(v.getTranslationX() + offsetX);
                    v.setTranslationY(v.getTranslationY() + offsetY);
                    break;
                //最后一个 手指 离开屏幕 时触发。
                case MotionEvent.ACTION_UP:
                    //注：在拖动过按钮后，如果其他view刷新导致重绘，会让按钮重回原点，所以需要更改布局参数
                    int absOffsetX = Math.abs( x - lastX2) ;
                    int absOffsetY = Math.abs( y- lastY2);
                    int thresholdOffsetPx = ScreenUtils.dp2px(getApplicationContext(),5);
                    if (absOffsetX < thresholdOffsetPx && absOffsetY < thresholdOffsetPx ){
                        Log.i(TAG, "img2 移动小于5dp ");
                        //如果上下左右移动都小于 5dp ， 则认为是点击事件
                        //注OnTouch 和OnClick会存在事件冲突，根据AS提示，如下
                        //onTouch lambda should call View#performClick when a click is detected
                        v.performClick();
                    }
                    break;
            }
            return true;
        } );

    }
}