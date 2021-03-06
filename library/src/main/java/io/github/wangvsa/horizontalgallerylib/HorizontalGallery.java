package io.github.wangvsa.horizontalgallerylib;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangchen on 6/22/17.
 *
 * Only two methods are exposed to the user.
 * 1. setHorizontalGalleryAdapter(HorizontalGalleryAdapter adapter)
 * 2. addImage(String uri)
 */
public class HorizontalGallery extends HorizontalScrollView implements View.OnClickListener {

    private static final String TAG = "horizontal_gallery";


    private LinearLayout mContainer;


    // 子元素的宽度
    private int mChildWidth, mChildHeight;

    // 当前最后一张图片的index
    private int mCurrentIndex;

    // 当前第一张图片的下标
    private int mFristIndex;

    // 当前第一个View
    private View mFirstView;

    // 数据适配器
    private HorizontalGalleryAdapter mAdapter;

    // 每屏幕最多显示的个数
    private int mCountOneScreen;

    // 屏幕的宽度
    private int mScreenWitdh;


    // 保存View与位置的键值对
    private Map<View, Integer> mViewPos = new HashMap<View, Integer>();


    public HorizontalGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.horizontal_gallery, this, true);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWitdh = outMetrics.widthPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mContainer = (LinearLayout) findViewById(R.id.horizontal_gallery_content);
    }

    // 加载下一张图片
    protected void loadNextImg() {
        // 数组边界值计算
        if (mCurrentIndex == mAdapter.getCount() - 1) {
            return;
        }
        //移除第一张图片，且将水平滚动位置置0
        scrollTo(0, 0);
        mViewPos.remove(mContainer.getChildAt(0));
        mContainer.removeViewAt(0);

        //获取下一张图片，并且设置onclick事件，且加入容器中
        View view = mAdapter.getView(++mCurrentIndex, null, mContainer);
        view.setOnClickListener(this);

        View removeButton = view.findViewById(R.id.horizontal_gallery_item_remove);
        removeButton.setOnClickListener(this);

        mContainer.addView(view);
        mViewPos.put(view, mCurrentIndex);

        //当前第一张图片小标
        mFristIndex++;
        //如果设置了滚动监听则触发
        if (mListener != null) {
            notifyCurrentImgChanged();
        }

    }
    /**
     * 加载前一张图片
     */
    protected void loadPreImg() {
        //如果当前已经是第一张，则返回
        if (mFristIndex == 0)
            return;
        //获得当前应该显示为第一张图片的下标
        int index = mCurrentIndex - mCountOneScreen;
        if (index >= 0) {
//          mContainer = (LinearLayout) getChildAt(0);
            //移除最后一张
            int oldViewPos = mContainer.getChildCount() - 1;
            mViewPos.remove(mContainer.getChildAt(oldViewPos));
            mContainer.removeViewAt(oldViewPos);

            //将此View放入第一个位置
            View view = mAdapter.getView(index, null, mContainer);
            mViewPos.put(view, index);
            mContainer.addView(view, 0);
            view.setOnClickListener(this);

            View removeButton = view.findViewById(R.id.horizontal_gallery_item_remove);
            removeButton.setOnClickListener(this);

            //水平滚动位置向左移动view的宽度个像素
            scrollTo(mChildWidth, 0);
            //当前位置--，当前第一个显示的下标--
            mCurrentIndex--;
            mFristIndex--;
            //回调
            if (mListener != null)
            {
                notifyCurrentImgChanged();

            }
        }
    }

    /**
     * 滑动时的回调
     */
    public void notifyCurrentImgChanged() {
        //先清除所有的背景色，点击时会设置为蓝色
        for (int i = 0; i < mContainer.getChildCount(); i++) {
            mContainer.getChildAt(i).setBackgroundColor(Color.WHITE);
        }

        mListener.onCurrentImgChanged(mFristIndex, mContainer.getChildAt(0));

    }

    /**
     * 初始化数据，设置数据适配器
     *
     * @param mAdapter
     */
    public void setHorizontalGalleryAdapter(HorizontalGalleryAdapter mAdapter) {
        this.mAdapter = mAdapter;
        mContainer = (LinearLayout) findViewById(R.id.horizontal_gallery_content);
        // 获得适配器中第一个View
        final View view = mAdapter.getView(0, null, mContainer);
        mContainer.addView(view);

        // 强制计算当前View的宽和高
        if (mChildWidth == 0 && mChildHeight == 0) {
            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            view.measure(w, h);
            mChildWidth = view.getMeasuredWidth();
            mChildHeight = view.getMeasuredHeight();
            // 计算每次加载多少个View
            mCountOneScreen = mScreenWitdh / mChildWidth + 2;
        }
        //初始化第一屏幕的元素
        initFirstScreenChildren(mCountOneScreen);
    }

    /**
     * 加载第一屏的View
     *
     * @param mCountOneScreen
     */
    private void initFirstScreenChildren(int mCountOneScreen) {
        mContainer = (LinearLayout) findViewById(R.id.horizontal_gallery_content);
        mContainer.removeAllViews();
        mViewPos.clear();

        for (int i = 0; i < mCountOneScreen && i < mAdapter.getCount(); i++) {
            View view = mAdapter.getView(i, null, mContainer);
            view.setOnClickListener(this);

            View removeButton = view.findViewById(R.id.horizontal_gallery_item_remove);
            removeButton.setOnClickListener(this);

            mContainer.addView(view);
            mViewPos.put(view, i);
            mCurrentIndex = i;
        }

        if (mListener != null) {
            notifyCurrentImgChanged();
        }
    }


    public void addImage(String uri) {
        mAdapter.add(uri);
        setHorizontalGalleryAdapter(mAdapter);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int scrollX = getScrollX();
                // 如果当前scrollX为view的宽度，加载下一张，移除第一张
                if (scrollX >= mChildWidth) {
                    loadNextImg();
                }
                // 如果当前scrollX = 0， 往前设置一张，移除最后一张
                if (scrollX == 0) {
                    loadPreImg();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.horizontal_gallery_item_remove) {  // remove picture
            int pos = mViewPos.get(v.getParent());
            String uri = (String)mAdapter.getItem(pos);
            mAdapter.remove(pos);
            setHorizontalGalleryAdapter(mAdapter);

            if(mOnItemRemovedListener != null) {
                mOnItemRemovedListener.onItemRemovedListener(pos, uri);
            }
            return;
        }


        if (mOnClickListener != null) {
            for (int i = 0; i < mContainer.getChildCount(); i++) {
                mContainer.getChildAt(i).setBackgroundColor(Color.WHITE);
            }
            mOnClickListener.onClick(v, mViewPos.get(v));
        }
    }





    public interface CurrentImageChangeListener {
        void onCurrentImgChanged(int position, View viewIndicator);
    }
    private CurrentImageChangeListener mListener;
    public void setCurrentImageChangeListener(CurrentImageChangeListener listener) {
        this.mListener = listener;
    }




    public interface OnItemClickListener {
        void onClick(View view, int pos);
    }
    private OnItemClickListener mOnClickListener;
    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }


    public interface OnItemRemovedListener {
        void onItemRemovedListener(int pos, String uri);     // return the removed image's uri
    }
    private OnItemRemovedListener mOnItemRemovedListener;
    public void setOnItemRemovedListener(OnItemRemovedListener onItemRemovedListener) {
        this.mOnItemRemovedListener = onItemRemovedListener;
    }



}
