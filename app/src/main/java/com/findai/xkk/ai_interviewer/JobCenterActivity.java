package com.findai.xkk.ai_interviewer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.findai.xkk.ai_interviewer.Dao.Question_Data_Exe;
import com.findai.xkk.ai_interviewer.domain.QuestionList;
import com.findai.xkk.ai_interviewer.job_fragment.Job_Index_maintop_Fragment;
import com.findai.xkk.ai_interviewer.job_fragment.Myinfo_Index_maintop_Fragment;
import com.findai.xkk.ai_interviewer.job_fragment.Toudi_Index_maintop_Fragment;
import com.oragee.banners.BannerView;

public class JobCenterActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        System.out.println(checkedId);
        ft = fm.beginTransaction();
        switch (checkedId){
            case R.id.rd_job_center:
                job_index_maintop_fragment = new Job_Index_maintop_Fragment();
                ft.replace(R.id.framelayout_job_maintop,job_index_maintop_fragment);
                break;
            case R.id.rd_myinfo:
                myinfo_index_maintop_fragment = new Myinfo_Index_maintop_Fragment();
                ft.replace(R.id.framelayout_job_maintop,myinfo_index_maintop_fragment);
                break;
            case R.id.rd_toudi:
                toudi_index_maintop_fragment = new Toudi_Index_maintop_Fragment();
                ft.replace(R.id.framelayout_job_maintop,toudi_index_maintop_fragment);
                break;
        }
        ft.commit();
    }

    private Question_Data_Exe question_data_exe;
    private QuestionList questionList = null;
    private Job_Index_maintop_Fragment job_index_maintop_fragment;
    private Myinfo_Index_maintop_Fragment myinfo_index_maintop_fragment;
    private Toudi_Index_maintop_Fragment toudi_index_maintop_fragment;
    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();

    BannerView bannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_center_activity);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
//        btn_kj = findViewById(R.id.btn_kj_interview);
//        btn_kj.setOnClickListener(this);
        showfragment();
        RadioGroup rg_navigation_bottom = findViewById(R.id.rg_navigation_bottom);

        rg_navigation_bottom.setOnCheckedChangeListener(this);
//
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                question_data_exe = new Question_Data_Exe(getBaseContext());
//                try {
//                    questionList = question_data_exe.Add_Question_To_DB(1);
//                    Thread.sleep(2000);
//
//                    Intent intent = new Intent(getBaseContext(), InterviewMainActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("questionlist", questionList);
//                    intent.putExtra("index_get_questionlist_bundle", bundle);
//                    startActivity(intent);
//                    finish();
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
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


    }
    private void showfragment(){
//        ft = fm.beginTransaction();
        job_index_maintop_fragment = new Job_Index_maintop_Fragment();
        ft.replace(R.id.framelayout_job_maintop,job_index_maintop_fragment);
        ft.commit();

    }
}
