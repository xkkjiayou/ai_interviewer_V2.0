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
import com.findai.xkk.ai_interviewer.Http.Commiuncate_Server;
import com.findai.xkk.ai_interviewer.Utils.ACache;
import com.findai.xkk.ai_interviewer.Utils.GlobalParams;
import com.findai.xkk.ai_interviewer.domain.Job;
import com.findai.xkk.ai_interviewer.domain.JobList;
import com.findai.xkk.ai_interviewer.domain.QuestionList;
import com.findai.xkk.ai_interviewer.domain.User;
import com.findai.xkk.ai_interviewer.job_fragment.JobListView_Adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WelcomeIndexActivity extends AppCompatActivity {

    //    private Question_Data_Exe question_data_exe;
//    private QuestionList questionList = null;
//    int iid;
    private JobList joblist = new JobList();

    final Commiuncate_Server cs = new Commiuncate_Server();
    boolean job_loaded_flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.loading_activity);
//        User user = (User)ACache.get(this).getAsObject(GlobalParams.Para_USER);
//        if(user == null){
//            Toast.makeText(getBaseContext(),"您尚未登录，请进行登录",Toast.LENGTH_LONG).show();
//            Intent intent = new Intent(getBaseContext(),LoginActivity.class);
//            startActivity(intent);
//            finish();
//            return;
//        }
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    joblist = cs.get_joblist(10);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("joblist", joblist);
                    Intent intent = new Intent(getBaseContext(), JobCenterActivity.class);
                    intent.putExtra("joblist", bundle);
                    startActivity(intent);
//                    data = getData();
//                    System.out.println(joblist.size()+"OK!!!!!!!!");
                    job_loaded_flag = true;
                    finish();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        thread1.start();
//        Thread thread = new Thread(new Runnable() {
//            boolean thread_flag=false;
//            @Override
//            public void run() {
//                while (true) {
//                    if(thread_flag){
//                        return;
//                    }
////                    System.out.println("又来了");
//                    getActivity().runOnUiThread(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            if(job_loaded_flag) {
////                            System.out.println("====3213=21=321=321=3=21=321=321=321=3=21=321");
//                                lv.setAdapter(new JobListView_Adapter(getContext(), data));
//                                fixListViewHeight(lv);
//                                job_loaded_flag = false;
//                                thread_flag = true;
//                                return;
//                            }
//                        }
//
//                    });
//                }
//            }
//        });
//        thread.start();


    }
}