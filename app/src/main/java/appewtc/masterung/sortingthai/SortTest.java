package appewtc.masterung.sortingthai;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class SortTest extends Activity {

    DrawingView dv ;
    private Paint mPaint;

    //รูปภาพตัวอักษร A-Z
    private int[] ints = new int[]{R.drawable.a0,
            R.drawable.a1,R.drawable.a2,R.drawable.a3,
            R.drawable.a4,R.drawable.index5,R.drawable.index6,
            R.drawable.index7,R.drawable.index8,R.drawable.index9,
            R.drawable.index10,R.drawable.index11,R.drawable.index12,
            R.drawable.index13,R.drawable.index14,R.drawable.index15,
            R.drawable.index16,R.drawable.index17,R.drawable.index18,
            R.drawable.index19,R.drawable.index20,R.drawable.index21,
            R.drawable.index22,R.drawable.index23,R.drawable.index24,
            R.drawable.index25,R.drawable.index26,R.drawable.index27,
            R.drawable.index28,R.drawable.index29,R.drawable.index30,
            R.drawable.index31,R.drawable.index32,R.drawable.index33,
            R.drawable.index34,R.drawable.index35,R.drawable.index36,
            R.drawable.index37,R.drawable.index38,R.drawable.index39,
            R.drawable.index40,R.drawable.index41,R.drawable.index42,
            R.drawable.index43,};

    //ค่าindexที่รับมาจาก ListView
    private int anInt;

    //ไฟล์ mp3 ที่เป็นเสียงอักษร
    private int[] spackInts = new int[]{R.raw.a0, R.raw.a1,
            R.raw.a2, R.raw.a3, R.raw.a4, R.raw.a5,
            R.raw.a6, R.raw.a7, R.raw.index8, R.raw.index9,
            R.raw.index10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dv = new DrawingView(this);
        setContentView(dv);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLUE);    // Color of Pen เปลี่ยนสี
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(50);   // Size of Pen เปลี่ยนขนาด
        anInt = getIntent().getIntExtra("Index", 0);



    }   // Main Method

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        Log.d("SortV1", "You Click Back");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.doremon48);
        builder.setTitle("What do you want ?");
        builder.setMessage("Please Click Button");
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //Restart Activity
                Intent intent = getIntent();
                finish();
                startActivity(intent);

                dialogInterface.dismiss();
            }
        });
        builder.setNeutralButton("Speak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //Sound Effect
                MediaPlayer mediaPlayer = MediaPlayer.create(getBaseContext(),
                        spackInts[anInt]);
                mediaPlayer.start();
                //Clear Media
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayer.release();
                    }
                });

                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        builder.show();

    }   // onBackPressed

    public class DrawingView extends View {

        public int width;
        public  int height;
        private Bitmap mBitmap;
        private Canvas mCanvas;
        private Path mPath;
        private Paint   mBitmapPaint;
        Context context;
        private Paint circlePaint;
        private Path circlePath;

        public DrawingView(Context c) {
            super(c);
            context=c;
            mPath = new Path();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
            circlePaint = new Paint();
            circlePath = new Path();
            circlePaint.setAntiAlias(true);
            circlePaint.setColor(Color.RED);
            circlePaint.setStyle(Paint.Style.STROKE);
            circlePaint.setStrokeJoin(Paint.Join.MITER);
            circlePaint.setStrokeWidth(4f); //ขนาดของเส้นวงกลม ปลายปากกา

        }   // Constructor DrawingView

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
        }   // onSizeChanged

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            int intWidth = canvas.getWidth();
            int intHeight = canvas.getHeight();
            Log.d("7febV1", "intWidth ==> " + intWidth);
            Log.d("7febV1", "intHeight ==> " + intHeight);

            Bitmap aBitmap = BitmapFactory.decodeResource(getResources(), ints[anInt]);
            Bitmap indexConvas = Bitmap.createScaledBitmap(aBitmap, intWidth, intHeight, true);


            canvas.drawBitmap(indexConvas, 0, 0, mBitmapPaint);

            canvas.drawBitmap( mBitmap, 0, 0, mBitmapPaint);
            canvas.drawPath( mPath,  mPaint);
            canvas.drawPath( circlePath,  circlePaint);



        }   // onDraw

        private float mX, mY;
        private static final float TOUCH_TOLERANCE = 4;

        private void touch_start(float x, float y) {
            mPath.reset();
            mPath.moveTo(x, y);
            mX = x;
            mY = y;
        }   // touch_start

        private void touch_move(float x, float y) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
                mX = x;
                mY = y;

                circlePath.reset();
                circlePath.addCircle(mX, mY, 20, Path.Direction.CW);
            }
        }   // touch_move

        private void touch_up() {
            mPath.lineTo(mX, mY);
            circlePath.reset();
            // commit the path to our offscreen
            mCanvas.drawPath(mPath,  mPaint);
            // kill this so we don't double draw
            mPath.reset();
        }   // touch_up

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touch_start(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch_move(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up();
                    invalidate();
                    break;
            }
            return true;
        }   // onTouchEvent

    }   // DrawingView Class

}   // Main Class