package com.findai.xkk.ai_interviewer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.findai.xkk.ai_interviewer.Dao.Question_Data_Exe;
import com.findai.xkk.ai_interviewer.Http.Commiuncate_Server;
import com.findai.xkk.ai_interviewer.Utils.ACache;
import com.findai.xkk.ai_interviewer.Utils.GlobalParams;
import com.findai.xkk.ai_interviewer.domain.QuestionList;
import com.findai.xkk.ai_interviewer.domain.Resume;
import com.findai.xkk.ai_interviewer.domain.User;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeInterviewActivity extends AppCompatActivity {

    private Question_Data_Exe question_data_exe;
    private QuestionList questionList = null;
    int iid;
    Resume resume;
    Commiuncate_Server cs = new Commiuncate_Server();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.welcome_waiting_index);
        final User user = (User)ACache.get(this).getAsObject(GlobalParams.Para_USER);
        if(user == null){
//            Toast.makeText(getBaseContext(),"您尚未登录，请进行登录",Toast.LENGTH_LONG).show();
            TastyToast.makeText(getApplicationContext(), "您尚未登录，请进行登录", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            Intent intent = new Intent(getBaseContext(),LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        iid = 1;
        System.out.println("用户点击题库iid"+iid);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
            try {
                resume = cs.get_resume_by_uid(user);
            }catch (Exception ex) {
                resume = new Resume();
                ex.printStackTrace();
            }
//            return;
            if (!resume.isHasResume()){
                Looper.prepare();
                System.out.println("卧槽，竟然没有简历");
                TastyToast.makeText(getApplicationContext(), "请先完善简历再进行面试，AI面试官会多角度对您进行考核", TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
                Looper.loop();
                finish();
                return;
            }else{
                Looper.prepare();
                System.out.println("卧槽，竟然有简历");
                TastyToast.makeText(getApplicationContext(), "AI面试官：简历已阅读完毕", TastyToast.LENGTH_SHORT, TastyToast.DEFAULT).show();
                question_data_exe = new Question_Data_Exe(getBaseContext());
                try {
                    System.out.println("卧槽，准备获取问题了");
                    questionList = question_data_exe.Add_Question_To_DB(iid);
                    TastyToast.makeText(getApplicationContext(), "AI面试官：AI面试管已抵达会议室", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                Looper.loop();
                return;
            }

            }
        });
        thread.start();





//        final Commiuncate_Server cs = new Commiuncate_Server();
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
//        thread.start();

//        while(questionList == null)
//        {
//
//        }
//        try {
//
//            Thread.sleep(20000);
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
        enterHome();

    }

    private void enterHome() {
        Timer time = new Timer();
        TimerTask tk = new TimerTask() {
            Intent intent = new Intent(getBaseContext(), ViewReportWeb.class);

            @Override
            public void run() {
                // TODO Auto-generated method stub

                while (questionList!=null){
                    Intent intent = new Intent(getBaseContext(), InterviewMainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("questionlist", questionList);
                    intent.putExtra("index_get_questionlist_bundle", bundle);
                    startActivity(intent);
                    finish();
                    return;
                }

//                finish();
            }
        };
        time.schedule(tk, 3500);

    }
}
