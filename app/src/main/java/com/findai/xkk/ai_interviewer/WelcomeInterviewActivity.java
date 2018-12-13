package com.findai.xkk.ai_interviewer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.findai.xkk.ai_interviewer.Dao.Question_Data_Exe;
import com.findai.xkk.ai_interviewer.Utils.ACache;
import com.findai.xkk.ai_interviewer.Utils.GlobalParams;
import com.findai.xkk.ai_interviewer.domain.QuestionList;
import com.findai.xkk.ai_interviewer.domain.User;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeInterviewActivity extends AppCompatActivity {

    private Question_Data_Exe question_data_exe;
    private QuestionList questionList = null;
    int iid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.welcome_waiting_index);
        User user = (User)ACache.get(this).getAsObject(GlobalParams.Para_USER);
        if(user == null){
            Toast.makeText(getBaseContext(),"您尚未登录，请进行登录",Toast.LENGTH_LONG).show();
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
                question_data_exe = new Question_Data_Exe(getBaseContext());
                try {
                    questionList = question_data_exe.Add_Question_To_DB(iid);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        thread.start();
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
        time.schedule(tk, 2000);

    }
}
