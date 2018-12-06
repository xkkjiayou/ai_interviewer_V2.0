package com.findai.xkk.ai_interviewer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.findai.xkk.ai_interviewer.Dao.Question_Data_Exe;
import com.findai.xkk.ai_interviewer.Http.Commiuncate_Server;
import com.findai.xkk.ai_interviewer.domain.Question;
import com.findai.xkk.ai_interviewer.domain.QuestionList;
import com.findai.xkk.ai_interviewer.question_fragment.question_choose_fragment;
import com.findai.xkk.ai_interviewer.question_fragment.question_wenda_fragment;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.UUID;

public class InterviewMainActivity extends AppCompatActivity implements View.OnClickListener, question_choose_fragment.callbackQuestion_Choose_Fragment, question_wenda_fragment.callbackQuestion_WenDa_Fragment {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    String userid = "602409866";
    String img_base64 = "";
    QuestionList questionList = null;
    private Camera camera;
    private SurfaceHolder sh;
    private SurfaceView sv_camera;
    private question_choose_fragment qcfragment = new question_choose_fragment(this);
    private question_wenda_fragment qwdfragment = new question_wenda_fragment(this);
    private FragmentManager fm = getSupportFragmentManager();
    private FragmentTransaction ft = fm.beginTransaction();
    private Matrix matrix = new Matrix();
//    private SurfaceView sv_camera_face;
//    private SurfaceHolder sv_camera_face_holder;
    private Button btn_next;
    private boolean hasface = false;
    Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            if (hasface) {
                System.out.println("人脸已存储");
                Camera.Size size = camera.getParameters().getPreviewSize();
                try {
                    YuvImage image = new YuvImage(data, ImageFormat.NV21, size.width,
                            size.height, null);
                    if (image != null) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        image.compressToJpeg(new Rect(0, 0, size.width, size.height),
                                80, stream);
                        Bitmap bmp = BitmapFactory.decodeByteArray(
                                stream.toByteArray(), 0, stream.size());
                        stream.close();
                        bmp = adjustPhotoRotation(bmp,-90);
                        img_base64 = bitmapToBase64(bmp);
//                        System.out.println(img_base64);
                    }
                } catch (Exception ex) {
                    Log.e("Sys", "Error:" + ex.getMessage());
                }
            }
        }
    };
    private byte[] imgdata;
    private Question_Data_Exe question_data_exe;
    private Question current_question;
    private Button btn_former;
    private Iterator<Question> questionIterator;
    private TextView tv_has_face;

    public Bitmap adjustPhotoRotation(Bitmap bm, final int orientationDegree)
    {

        Matrix m = new Matrix();
        m.setRotate(orientationDegree, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);

        try {
            Bitmap bm1 = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);

            return bm1;

        } catch (OutOfMemoryError ex) {
        }
        return null;

    }
    /**
     * bitmap转为base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    //语音

    public static void followScreenOrientation(Context context, Camera camera) {
        final int orientation = context.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            camera.setDisplayOrientation(180);
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            camera.setDisplayOrientation(90);
        }
    }

    public static void prepareMatrix(Matrix matrix, boolean mirror, int displayOrientation,
                                     int viewWidth, int viewHeight) {
        // Need mirror for front camera.
        matrix.setScale(mirror ? -1 : 1, 1);
        // This is the value for android.hardware.Camera.setDisplayOrientation.
        matrix.postRotate(displayOrientation);
        // Camera driver coordinates range from (-1000, -1000) to (1000, 1000).
        // UI coordinates range from (0, 0) to (width, height).
        matrix.postScale(viewWidth / 2000f, viewHeight / 2000f);
        matrix.postTranslate(viewWidth / 2f, viewHeight / 2f);
    }

    @Override
    public int get_question_answer(int answer) {
//        System.out.println("ACTivity"+answer);
        current_question.setAnswer(answer + "");
        return answer;
    }

    @Override
    public String get_question_wenda_answer(String answer) {
//        System.out.println("-p-p-p-p-p-p");
        current_question.setAnswer(answer);
//        System.out.println("ACT_wenda:"+answer);
        return answer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        hasface = false;
        setContentView(R.layout.interview_main_activity);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("index_get_questionlist_bundle");
        questionList = (QuestionList) bundle.getSerializable("questionlist");
        questionIterator = get_question_iterator(questionList);
        tv_has_face = findViewById(R.id.tv_has_face);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        question_data_exe = new Question_Data_Exe(getBaseContext());
        init();
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
//    public native String stringFromJNI();
    private void init() {
        current_question = questionIterator.next();
        show_fragment(current_question);
//        System.out.println(Camera.getNumberOfCameras());
//        camera = Camera.open(1);
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int camIdx = 0; camIdx < Camera.getNumberOfCameras(); camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                //代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置
                try {
                    camera = Camera.open(camIdx);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

        sv_camera = findViewById(R.id.sv_camera);
//        sv_camera_face = findViewById(R.id.sv_camera_face);
        btn_next = findViewById(R.id.btn_next_question);
        btn_next.setOnClickListener(this);

        sh = sv_camera.getHolder();
//        sv_camera_face_holder = sv_camera_face.getHolder();
//        sv_camera_face.setZOrderOnTop(true);      // 这句不能少
//        sv_camera_face.getHolder().setFormat(PixelFormat.TRANSPARENT);
//        sv_camera_face.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        sv_camera_face_holder.setFormat(PixelFormat.TRANSLUCENT);
//        sv_camera_face_holder.addCallback();

        sh.addCallback(new SurfaceCallback());
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, 1);//1 can be another integer
        }


    }

    public Iterator<Question> get_question_iterator(QuestionList ql) {
        return ql.getQuestionList().iterator();
    }

    @Override
    public void onClick(View v) {
//        if(current_question.getAnswer()==null||current_question.getAnswer().trim().equals("")){
//            Toast.makeText(this,"请完成该题目",Toast.LENGTH_LONG).show();
//        }
        if (questionIterator.hasNext()) {
            switch (v.getId()) {
                case R.id.btn_next_question:
                    current_question = questionIterator.next();
                    System.out.println(current_question.getTitle());
                    show_fragment(current_question);
                    if (!questionIterator.hasNext()) {
                        btn_next.setText("完成面试");
                    }
                    break;

                default:
                    break;
            }
        } else {
            System.out.println(img_base64);
            questionList.setImgdata(img_base64);
            questionList.setUserid(userid);
            questionList.setReportid(UUID.randomUUID().toString().replace("-", "") + System.currentTimeMillis());
            Gson gson = new Gson();
            final String answer_json = gson.toJson(questionList);
//            System.out.println(json);
            final Commiuncate_Server cs = new Commiuncate_Server();
            try {
                //拿到报告返回结果，上传图像和结果
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
//                            String report_result="";
                            String report_result = cs.post_answer(answer_json);
                            System.out.println("ACt:" + report_result);
                            Intent intent = new Intent(getBaseContext(), SubmitReportActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("report_url", report_result);
                            intent.putExtra("report_url", bundle);
                            startActivity(intent);
                            finish();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                thread.start();


            } catch (Exception ex) {
                ex.printStackTrace();
            }
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("questionlist",questionList);
//            intent.putExtra("bundle_questionlist",bundle);
//            startActivity(intent);
        }
    }

    private void show_fragment(Question q) {
        Bundle bundle;
        ft = fm.beginTransaction();
        switch (q.type) {
            case 0:
                bundle = new Bundle();
                bundle.putSerializable("question", q);
                qcfragment = new question_choose_fragment(this);
                qcfragment.setArguments(bundle);
                ft.replace(R.id.fragment_question, qcfragment);
                break;
            case 1:
                bundle = new Bundle();
                bundle.putSerializable("question", q);
                qwdfragment.setArguments(bundle);
                ft.replace(R.id.fragment_question, qwdfragment);
                break;
            case 2:
                bundle = new Bundle();
                bundle.putSerializable("question", q);
                qwdfragment.setArguments(bundle);
                ft.replace(R.id.fragment_question, qwdfragment);
                break;
            default:
                break;
        }
        ft.commit();
    }

    private class SurfaceCallback implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            sh = holder;
            try {
                followScreenOrientation(getBaseContext(), camera);
                camera.startFaceDetection();
                camera.setFaceDetectionListener(new FaceDetectListener());

                final Camera.Parameters params = camera.getParameters();
                params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                params.setPictureSize(sv_camera.getWidth(), sv_camera.getHeight());
                params.setPreviewSize(sv_camera.getWidth(), sv_camera.getHeight());
                params.setSceneMode(Camera.Parameters.SCENE_MODE_BARCODE);
                params.setJpegQuality(50); // 设置照片质量

                camera.setPreviewCallback(previewCallback);
                camera.setPreviewDisplay(holder);
                camera.startPreview();
                Log.i("camera", "create");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Log.i("camera", "change");
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            try {
                camera.setPreviewCallback(null);
                camera.stopPreview();
                camera.release();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private class FaceDetectListener implements Camera.FaceDetectionListener {
        @Override
        public void onFaceDetection(Camera.Face[] faces, Camera camera) {
//            Log.i("face_num:",String.valueOf(faces.length));

//                Log.i("检测到了",face_rect.toString());
            try {
//                Canvas clear_canvas = sv_camera_face_holder.lockCanvas();
//                clear_canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
//                sv_camera_face_holder.unlockCanvasAndPost(clear_canvas);
                hasface = false;
//                Toast.makeText(getBaseContext(),"请让人脸入镜一次，用以完成人脸比对工作。",Toast.LENGTH_LONG).show();
                if (faces.length > 0) {
                    hasface = true;
                    tv_has_face.setText("人脸比对工作已完成\n您的精神面貌良好");
                    tv_has_face.setBackgroundColor(Color.parseColor("#50FF9933"));
//                    System.out.println("看到人脸啦");
//                    Rect face_rect = faces[0].rect;
//                    System.out.println(faces[0].id);
//                    Paint paint = new Paint();
//                    paint.setColor(Color.parseColor("#222222"));
//                    paint.setStrokeWidth(2f);
//                    paint.setAlpha(180);
//                    paint.setStyle(Paint.Style.STROKE);
//                    Canvas face_canvas = sv_camera_face_holder.lockCanvas();
//                    face_canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
//
//                    prepareMatrix(matrix, true, 90, sv_camera.getWidth(), sv_camera.getHeight());
//                    RectF rf = new RectF(face_rect.left,
//                            face_rect.top,
//                            face_rect.right,
//                            face_rect.bottom);
//                    matrix.mapRect(rf);
//
//                    face_rect = new Rect();
//                    rf.round(face_rect);
//
//                    face_canvas.drawRect(new Rect(face_rect.left + 15,
//                            face_rect.top,
//                            face_rect.right,
//                            face_rect.bottom), paint);//绘制矩形
//                    sv_camera_face_holder.unlockCanvasAndPost(face_canvas);
                }
            } catch (Exception ex) {
                Log.i("出错了", "2");
            }

        }
    }


}
