package com.ricky.f.view;

/**
 * Created by Deak on 16/12/3.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;
import com.ricky.f.R;


/**
 * Created by opprime on 16-7-21.
 */
public class ClearEditText extends EditText {
    private Context mContext;
    private Bitmap mClearButton, mEyeButton, mOpenEyeButton, mCloseEyeButton;

    private boolean isPasswordBox;
    private Paint mPaint;

    private boolean mClearStatus;

    //初始化输入框右内边距
    private int mInitPaddingRight;
    //按钮的左右内边距，默认为3dp
    private int mClearButtonPadding = dp2px(3);
    private int mEyeButtonPadding = dp2px(3);


    public ClearEditText(Context context) {
        super(context);
        init(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 初始化
     */
    private void init(Context context, AttributeSet attributeSet) {
        this.mContext = context;

        addTextChangedListener(onMobileTextWatcher);

        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ClearEditText);
        isPasswordBox = typedArray.getBoolean(R.styleable.ClearEditText_isPasswordBox, false);
        if(isPasswordBox){
            //密码默认可见
            setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }

        int clearButton = typedArray.getResourceId(R.styleable.ClearEditText_clearButtonDrawable, R.drawable.ic_del);
        int openEyeButton = typedArray.getResourceId(R.styleable.ClearEditText_eyeOpenButtonDrawable, R.drawable.ic_open_eye);
        int closeEyeButton = typedArray.getResourceId(R.styleable.ClearEditText_eyeCloseButtonDrawable, R.drawable.ic_close_eye);
        typedArray.recycle();

        //按钮的图片
        mClearButton = ((BitmapDrawable) getDrawableCompat(clearButton)).getBitmap();
        mOpenEyeButton = ((BitmapDrawable) getDrawableCompat(openEyeButton)).getBitmap();
        mCloseEyeButton = ((BitmapDrawable) getDrawableCompat(closeEyeButton)).getBitmap();

        mEyeButton = mOpenEyeButton; //以开眼睛的图片计算

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mInitPaddingRight = getPaddingRight();
    }

    /**
     * 按钮状态管理
     * @param canvas onDraw的Canvas
     */
    private void buttonManager(Canvas canvas) {
        int clearButtonLeft = getMeasuredWidth() - mClearButton.getWidth()-mClearButtonPadding*2;
        int eyeButtonLeft = getMeasuredWidth() - mEyeButton.getWidth()-mEyeButtonPadding*2;

        int clearButtonTop =  (getMeasuredHeight() - mClearButton.getHeight())/2;
        int eyeButtonTop =  (getMeasuredHeight() - mEyeButton.getHeight())/2;

        if(isPasswordBox) {
            int offset = mEyeButton.getWidth() + mEyeButtonPadding*4;
            setPadding(getPaddingLeft(), getPaddingTop(), getMeasuredWidth() - clearButtonLeft+offset+mClearButton.getWidth(), getPaddingBottom());
            drawBitmap(canvas, mEyeButton, eyeButtonLeft, eyeButtonTop, true);
            drawBitmap(canvas, mClearButton, clearButtonLeft - offset, clearButtonTop, hasFocus() && getText().length()>0);
        } else {
            setPadding(getPaddingLeft(), getPaddingTop(),  getMeasuredWidth() - clearButtonLeft+mClearButton.getWidth(), getPaddingBottom());
            drawBitmap(canvas, mClearButton, clearButtonLeft, clearButtonTop, hasFocus() && getText().length()>0);
        }

    }

    /**
     * 绘制按钮图片
     * @param canvas onDraw的Canvas
     * @param left   图片位置
     */
    private void drawBitmap(Canvas canvas, Bitmap bitmap, int left, int top, boolean isShow) {
        left = isShow ? left : -bitmap.getWidth();
        canvas.drawBitmap(bitmap, left, top, mPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();

        buttonManager(canvas);

        canvas.restore();
    }

    private boolean isHidden=false;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if(isPasswordBox){
                    //密码输入框，处理两个点击
                    int leftEdge = getMeasuredWidth() - mClearButton.getWidth()-mClearButtonPadding*2 - mEyeButton.getWidth() - mEyeButtonPadding * 4;
                    if (event.getX() >= leftEdge) {
                        if(event.getX() < (leftEdge + getMeasuredWidth())/2.0f){
                            //表示点击清除
                            setError(null);
                            this.setText("");
                        } else {
                            if (isHidden) {
                                mEyeButton = mOpenEyeButton;
                                //设置EditText文本为可见的
                                setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            } else {
                                mEyeButton = mCloseEyeButton;
                                //设置EditText文本为隐藏的
                                setTransformationMethod(PasswordTransformationMethod.getInstance());
                            }
                            isHidden = !isHidden;
                            postInvalidate();
                            CharSequence charSequence = getText();
                            if (charSequence instanceof Spannable) {
                                Spannable spanText = (Spannable) charSequence;
                                Selection.setSelection(spanText, charSequence.length());
                            }

                        }
                    }
                } else {
                    int leftEdge = getMeasuredWidth() - mClearButton.getWidth()-mClearButtonPadding*2;
                    if(event.getX() >= leftEdge){
                        //表示点击清除
                        setError(null);
                        this.setText("");
                    }
                }
                break;
        }

        return super.onTouchEvent(event);
    }

    /**
     * 获取Drawable
     * @param resourseId  资源ID
     */
    private Drawable getDrawableCompat(int resourseId) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            return getResources().getDrawable(resourseId, mContext.getTheme());
        } else {
            return getResources().getDrawable(resourseId);
        }
    }

    public int dp2px(float dipValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private TextWatcher onMobileTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(onTextWatcherListner == null)return;
            onTextWatcherListner.onChange();
        }
    };

    private OnTextWatcherListner onTextWatcherListner;
    public interface OnTextWatcherListner{

        void onChange();
    }
    public void setOnTextWatcherListner(OnTextWatcherListner onTextWatcherListner) {
        this.onTextWatcherListner = onTextWatcherListner;
    }
}
