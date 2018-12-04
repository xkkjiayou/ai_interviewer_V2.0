package com.findai.xkk.ai_interviewer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;

import com.findai.xkk.ai_interviewer.Dao.Question_Data_Exe;
import com.findai.xkk.ai_interviewer.domain.QuestionList;
import com.findai.xkk.ai_interviewer.resume_fragment.Resume_Basicinfo_Fragment;
import com.findai.xkk.ai_interviewer.resume_fragment.Resume_Education_Fragment;
import com.findai.xkk.ai_interviewer.resume_fragment.Resume_Myevaluate_Fragment;
import com.findai.xkk.ai_interviewer.resume_fragment.Resume_Workexperience_Fragment;

import java.util.Timer;
import java.util.TimerTask;

public class WriteResumeActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    RadioGroup rg_resume_navigation;
    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();
    Object Current_Fragment;
    Resume_Basicinfo_Fragment resume_basicinfo_fragment;
    Resume_Myevaluate_Fragment resume_myevaluate_fragment;
    Resume_Workexperience_Fragment resume_workexperience_fragment;
    Resume_Education_Fragment resume_education_fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.write_resume_activity);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        rg_resume_navigation = findViewById(R.id.rg_resume_navigation);
        rg_resume_navigation.setOnCheckedChangeListener(this);

        resume_basicinfo_fragment = new Resume_Basicinfo_Fragment();
//        resume_myevaluate_fragment = new Resume_Myevaluate_Fragment();
//        resume_workexperience_fragment = new Resume_Workexperience_Fragment();
//        resume_education_fragment = new Resume_Education_Fragment();
        ft = fm.beginTransaction();
        resume_basicinfo_fragment = new Resume_Basicinfo_Fragment();
        Current_Fragment = resume_basicinfo_fragment;
        ft.replace(R.id.framelayout_resume,(Fragment)Current_Fragment);
        ft.commit();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        ft = fm.beginTransaction();
        switch (checkedId){
            case R.id.rd_resume_basicinfo:
                ft.hide((Fragment) Current_Fragment);
                Current_Fragment = resume_basicinfo_fragment;
//                ft.replace(R.id.framelayout_resume,resume_basicinfo_fragment);
                if (Current_Fragment==null){
                    Current_Fragment =resume_basicinfo_fragment = new Resume_Basicinfo_Fragment();
                    ft.add(R.id.framelayout_resume, (Fragment)Current_Fragment);
                }else {
//                    ft.add(R.id.framelayout_resume, (Fragment)Current_Fragment);
                    ft.show((Fragment)Current_Fragment);
                }
                break;
            case R.id.rd_myevaluate_basicinfo:
                ft.hide((Fragment) Current_Fragment);
                Current_Fragment = resume_myevaluate_fragment;
//                ft.replace(R.id.framelayout_resume,resume_myevaluate_fragment);

                if (Current_Fragment==null){
                    Current_Fragment =resume_myevaluate_fragment = new Resume_Myevaluate_Fragment();
                    ft.add(R.id.framelayout_resume, (Fragment)Current_Fragment);
                }else {
//                    ft.add(R.id.framelayout_resume, (Fragment)Current_Fragment);
                    ft.show((Fragment)Current_Fragment);
                }
                break;
            case R.id.rd_workexperience_basicinfo:
                ft.hide((Fragment) Current_Fragment);
                Current_Fragment = resume_workexperience_fragment;
//                ft.replace(R.id.framelayout_resume,resume_workexperience_fragment);

                if (Current_Fragment==null){
                    Current_Fragment =resume_workexperience_fragment = new Resume_Workexperience_Fragment();
                    ft.add(R.id.framelayout_resume, (Fragment)Current_Fragment);
                }else {
//                    ft.add(R.id.framelayout_resume, (Fragment)Current_Fragment);
                    ft.show((Fragment)Current_Fragment);
                }
                break;
            case R.id.rd_education_basicinfo:
                ft.hide((Fragment) Current_Fragment);
                Current_Fragment = resume_education_fragment;
//                ft.replace(R.id.framelayout_resume,resume_education_fragment);

                if (Current_Fragment==null){
                    Current_Fragment =resume_education_fragment = new Resume_Education_Fragment();
                    ft.add(R.id.framelayout_resume, (Fragment)Current_Fragment);
                }else {
//                    ft.add(R.id.framelayout_resume, (Fragment)Current_Fragment);
                    ft.show((Fragment)Current_Fragment);
                }
                break;
        }
//        ft.show((Fragment)Current_Fragment);
//        ft.addToBackStack(null);
        ft.commit();
    }

//    FragmentManager fm = getSupportFragmentManager();
//    FragmentTransaction ft = fm.beginTransaction();
//ft.hide(firstStepFragment);
//if (secondStepFragment==null){
//        ft.add(R.id.fl_content, secondStepFragment);
//    }else {
//        ft.show(secondStepFragment);
//    }
}
