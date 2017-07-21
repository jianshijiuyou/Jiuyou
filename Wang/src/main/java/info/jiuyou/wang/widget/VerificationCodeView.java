package info.jiuyou.wang.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

import info.jiuyou.wang.R;
import info.jiuyou.wang.utils.DisplayUtils;

/**
 * ==========================================
 * <p>
 * 版   权 ：jianshijiuyou(c) 2017
 * <br/>
 * 作   者 ：wq
 * <br/>
 * 版   本 ：1.0
 * <br/>
 * 创建日期 ：2017/4/24 0024  10:35
 * <br/>
 * 描   述 ：
 * <br/>
 * 修订历史 ：
 * </p>
 * ==========================================
 */
public class VerificationCodeView extends View {
    //将图片划分成4*3个小格
    private static final int WIDTH=4;
    private static final int HEIGHT=3;
    //小格相交的总的点数
    private int COUNT=(WIDTH+1)*(HEIGHT+1);
    private float[] verts=new float[COUNT*2];
    private float[] origs=new float[COUNT*2];

    //黄背景颜色
    private int YELLOW_BG_COLOR = 0xfff9dec1;
    //蓝背景颜色
    private int BLUE_BG_COLOR = 0xffdcdef8;

    private RectF mBounds;//用于获取控件宽高
    private Rect textBound;//用于计算文本的宽高

    private Paint bgPaint;//背景画笔
    private Paint textPaint;
    private Paint linePaint;

    private String tempCode;//当前生成的验证码
    private int codeNum = 4;//验证码位数  4或6。。
    private Random mRandom;

    //控件总宽度
    private int mWidth;
    private int dWidth;//真正可以绘制code的区域宽
    //控件高度
    private int mHeight;
    private int dHeight;//真正可以绘制code的区域高
    private int horizontalOffset=0;//水平方向的偏移，决定画笔开始的偏移量
    private boolean isInited=false;//是否初始化完成

    private Bitmap mbitmap;
    private Bitmap codebitmap;
    /**
     * 绘制贝塞尔曲线的路径集合
     */
    private ArrayList<Path> mPaths = new ArrayList<Path>();
    private float offset=5;//扭曲偏移
    private String vCode;
    private boolean isNetCode=false;//是否是网络请求到验证码

    public VerificationCodeView(Context context) {
        this(context,null);
    }

    public VerificationCodeView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public VerificationCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
        initView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取宽和高的SpecMode和SpecSize
        int wSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        //分别判断宽高是否设置为wrap_content
        if (wSpecMode == MeasureSpec.AT_MOST && hSpecMode == MeasureSpec.AT_MOST) {
            //宽高都为wrap_content,直接指定为400
            setMeasuredDimension(mWidth, mWidth);

        } else if (wSpecMode == MeasureSpec.AT_MOST) {
            //只有宽为wrap_content,宽直接指定为400，高为获取的SpecSize
            setMeasuredDimension(mWidth, hSpecSize);

        } else if (hSpecMode == MeasureSpec.AT_MOST) {
            //只有高为wrap_content,高直接指定为400，宽为获取的SpecSize
            setMeasuredDimension(wSpecSize, mWidth);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBounds=new RectF(getLeft(),getTop(),getRight(),getBottom());
        mWidth=dWidth= (int) (mBounds.right-mBounds.left);
        mHeight=dHeight= (int) (mBounds.bottom-mBounds.top);
        if (mWidth*1.0/mHeight>5.0/2){
            dWidth= (int) (mHeight*5.0/2);
        } else if (mWidth*1.0/mHeight<5.0/2) {
            dHeight= (int) (mWidth/5.0*2);
        }
        horizontalOffset=(mWidth-dWidth)/2;
        isInited=true;
        createCodeBitmap();
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.VerificationCodeView);
        isNetCode = typedArray.getBoolean(R.styleable.VerificationCodeView_isNetCode,false);
    }

    /**
     * 初始化
     */
    private void initView() {
        mRandom=new Random();

        bgPaint=new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(YELLOW_BG_COLOR);

        linePaint=new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(5);
        linePaint.setStrokeCap(Paint.Cap.ROUND);

        textPaint=new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(DisplayUtils.spToPx(getContext(),30));
        textPaint.setShadowLayer(5,3,3,0xFF999999);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setTextScaleX(0.8F);
        textPaint.setColor(Color.GREEN);

        textBound=new Rect();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(codebitmap,0,0,null);
    }

    /**
     * 生成验证码图片
     */
    private void createCodeBitmap() {
        if (!isInited)
            return;
        mPaths.clear();
        // 生成干扰线坐标
        for(int i=0;i<2;i++){
            Path path = new Path();
            int startX = mRandom.nextInt(mWidth/3)+10;
            int startY = mRandom.nextInt(mHeight/3)+10;
            int endX = mRandom.nextInt(mWidth/2)+mWidth/2-10;
            int endY = mRandom.nextInt(mHeight/2)+mHeight/2-10;
            path.moveTo(startX,startY);
            path.quadTo(Math.abs(endX-startX)/2, Math.abs(endY-startY)/2,endX,endY);
            mPaths.add(path);
        }
        mbitmap = Bitmap.createBitmap(mWidth,mHeight, Bitmap.Config.ARGB_8888);
        codebitmap = Bitmap.createBitmap(mWidth,mHeight, Bitmap.Config.ARGB_8888);
        Canvas myCanvas=new Canvas(mbitmap);
        Canvas canvas=new Canvas(codebitmap);
        tempCode=getCharAndNumr();
        //画背景
        myCanvas.drawColor(YELLOW_BG_COLOR);
        if (tempCode!=null&&tempCode.length()>0){
            textPaint.setTextSize(dWidth*0.3f);
            textPaint.getTextBounds(tempCode,0,codeNum,textBound);
            float charLength=(textBound.width())/codeNum;
            for (int i=0;i<codeNum;i++){
                int offsetDegree=mRandom.nextInt(15);
                // 这里只会产生0和1，如果是1那么正旋转正角度，否则旋转负角度
                offsetDegree = mRandom.nextInt(2) == 1?offsetDegree:-offsetDegree;
                myCanvas.save();
                myCanvas.rotate(offsetDegree,mWidth/2,mHeight/2);
                // 给画笔设置随机颜色，+20是为了去除一些边界值
                textPaint.setARGB(255, mRandom.nextInt(200) + 20, mRandom.nextInt(200) + 20, mRandom.nextInt(200) + 20);
                myCanvas.drawText(String.valueOf(tempCode.charAt(i)), i * charLength * 1.6f+20+horizontalOffset, mHeight * 2 / 3f, textPaint);
                myCanvas.restore();
            }
        }
        int index=0;
        float bitmapwidth= mbitmap.getWidth();
        float bitmapheight= mbitmap.getHeight();
        for(int i=0;i<HEIGHT+1;i++){
            float fy=bitmapheight/HEIGHT*i;
            for(int j=0;j<WIDTH+1;j++){
                float fx=bitmapwidth/WIDTH*j;
                //偶数位记录x坐标  奇数位记录Y坐标
                origs[index*2+0]=verts[index*2+0]=fx;
                origs[index*2+1]=verts[index*2+1]=fy;
                index++;
            }
        }
        //设置变形点，这些点将会影响变形的效果
        offset=bitmapheight/HEIGHT/3;
        verts[12]=verts[12]-offset;
        verts[13]=verts[13]+offset;
        verts[16]=verts[16]+offset;
        verts[17]=verts[17]-offset;
        verts[24]=verts[24]+offset;
        verts[25]=verts[25]+offset;

        // 对验证码图片进行扭曲变形
        canvas.drawBitmapMesh(mbitmap, WIDTH, HEIGHT, verts, 0, null, 0, null);
        // 产生干扰效果2 -- 干扰线
        for(Path path : mPaths){
            linePaint.setARGB(255, mRandom.nextInt(200) + 20, mRandom.nextInt(200) + 20, mRandom.nextInt(200) + 20);
            canvas.drawPath(path, linePaint);
        }
    }

    /**
     * java生成随机数字和字母组合
     * @return 随机验证码
     */
    public String getCharAndNumr() {
        if (isNetCode){
            return vCode;
        }else {
            String val = "";
            Random random = new Random();
            for (int i = 0; i < codeNum; i++) {
                // 输出字母还是数字
                String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
                // 字符串
                if ("char".equalsIgnoreCase(charOrNum)) {
                    // 取得大写字母还是小写字母
                    int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                    val += (char) (choice + random.nextInt(26));
                } else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
                    val += String.valueOf(random.nextInt(10));
                }
            }
            vCode=val;
            return val;
        }
    }

    /**
     * refresh verification Code
     */
    public void refreshCode(){
        createCodeBitmap();
        invalidate();
    }

    /**
     * get verification code
     * @return verification code
     */
    public String getvCode() {
        return vCode;
    }

    /**
     * set verification code, code get from net work
     * @return verification code
     */
    public void setvCode(String code) {
        this.vCode=code;
        refreshCode();
    }
}
