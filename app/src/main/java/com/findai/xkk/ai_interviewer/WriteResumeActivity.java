package com.findai.xkk.ai_interviewer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.findai.xkk.ai_interviewer.Http.Commiuncate_Server;
import com.findai.xkk.ai_interviewer.Utils.ACache;
import com.findai.xkk.ai_interviewer.Utils.GlobalParams;
import com.findai.xkk.ai_interviewer.domain.EducationExperience;
import com.findai.xkk.ai_interviewer.domain.Resume;
import com.findai.xkk.ai_interviewer.domain.User;
import com.findai.xkk.ai_interviewer.domain.WorkExperience;
import com.findai.xkk.ai_interviewer.resume_fragment.ResumeInterface;
import com.findai.xkk.ai_interviewer.resume_fragment.Resume_Basicinfo_Fragment;
import com.findai.xkk.ai_interviewer.resume_fragment.Resume_Education_Fragment;
import com.findai.xkk.ai_interviewer.resume_fragment.Resume_Myevaluate_Fragment;
import com.findai.xkk.ai_interviewer.resume_fragment.Resume_Workexperience_Fragment;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.List;

public class WriteResumeActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, ResumeInterface {

    Bundle bundle;
    Resume resume = null;
    RadioGroup rg_resume_navigation;
    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();
    Object Current_Fragment;
    Resume_Basicinfo_Fragment resume_basicinfo_fragment;
    Resume_Myevaluate_Fragment resume_myevaluate_fragment;
    Resume_Workexperience_Fragment resume_workexperience_fragment;
    Resume_Education_Fragment resume_education_fragment;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.write_resume_activity);
        init();

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

    }

    public void init() {

        user = (User) ACache.get(getBaseContext()).getAsObject(GlobalParams.Para_USER);
        if (user == null) {
            TastyToast.makeText(getApplicationContext(), "您尚未登录，请进行登录", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        final Commiuncate_Server cs = new Commiuncate_Server();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    resume = cs.get_resume_by_uid(user);
                } catch (Exception ex) {

                    resume = new Resume();
                    ex.printStackTrace();
                }
//            return;
            }
        });
        thread.start();
        while (resume == null) {
            System.out.println("等待简历加载");
        }

        if (resume.getEduexperience().size() == 0) {
            List<EducationExperience> l = new ArrayList<>();
            l.add(new EducationExperience());
            resume.setEduexperience(l);
        }
        if (resume.getWorkexperience().size() == 0) {
            List<WorkExperience> l = new ArrayList<>();
            l.add(new WorkExperience());
            resume.setWorkexperience(l);
        }
        resume.setUid(user.getUid());
//            Toast.makeText().show();
        System.out.println("==============");
        System.out.println(resume.getEduexperience().get(0).getMajor());

        System.out.println("==============");
        bundle = new Bundle();
        bundle.putSerializable("resume", resume);
        ft = fm.beginTransaction();
//        resume_basicinfo_fragment = new Resume_Basicinfo_Fragment(this);
//        resume_myevaluate_fragment = new Resume_Myevaluate_Fragment();
//        resume_workexperience_fragment = new Resume_Workexperience_Fragment();
//        resume_education_fragment = new Resume_Education_Fragment();
        resume_basicinfo_fragment = new Resume_Basicinfo_Fragment(this);
        Current_Fragment = resume_basicinfo_fragment;
        ((Resume_Basicinfo_Fragment) Current_Fragment).setArguments(bundle);
        ft.replace(R.id.framelayout_resume, (Fragment) Current_Fragment);
        ft.commit();


    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        ft = fm.beginTransaction();
        switch (checkedId) {
            case R.id.rd_resume_basicinfo:
                ft.hide((Fragment) Current_Fragment);
                Current_Fragment = resume_basicinfo_fragment;
//                ft.replace(R.id.framelayout_resume,resume_basicinfo_fragment);
                if (Current_Fragment == null) {

                    Current_Fragment = resume_basicinfo_fragment = new Resume_Basicinfo_Fragment(this);
                    ((Resume_Basicinfo_Fragment) Current_Fragment).setArguments(bundle);
//                    Current_Fragment);
                    ft.add(R.id.framelayout_resume, (Fragment) Current_Fragment);
                } else {
//                    ft.add(R.id.framelayout_resume, (Fragment)Current_Fragment);
                    ft.show((Fragment) Current_Fragment);
                }
                break;


            case R.id.rd_myevaluate_basicinfo:
                ft.hide((Fragment) Current_Fragment);
                Current_Fragment = resume_myevaluate_fragment;
//                ft.replace(R.id.framelayout_resume,resume_myevaluate_fragment);

                if (Current_Fragment == null) {
                    Current_Fragment = resume_myevaluate_fragment = new Resume_Myevaluate_Fragment(this);
                    ((Resume_Myevaluate_Fragment) Current_Fragment).setArguments(bundle);
                    ft.add(R.id.framelayout_resume, (Fragment) Current_Fragment);
                } else {
//                    ft.add(R.id.framelayout_resume, (Fragment)Current_Fragment);
                    ft.show((Fragment) Current_Fragment);
                }
                break;
            case R.id.rd_workexperience_basicinfo:
                ft.hide((Fragment) Current_Fragment);
                Current_Fragment = resume_workexperience_fragment;
//                ft.replace(R.id.framelayout_resume,resume_workexperience_fragment);

                if (Current_Fragment == null) {
                    Current_Fragment = resume_workexperience_fragment = new Resume_Workexperience_Fragment(this);
                    ((Resume_Workexperience_Fragment) Current_Fragment).setArguments(bundle);
                    ft.add(R.id.framelayout_resume, (Fragment) Current_Fragment);
                } else {
//                    ft.add(R.id.framelayout_resume, (Fragment)Current_Fragment);
                    ft.show((Fragment) Current_Fragment);
                }
                break;
            case R.id.rd_education_basicinfo:
                ft.hide((Fragment) Current_Fragment);
                Current_Fragment = resume_education_fragment;
//                ft.replace(R.id.framelayout_resume,resume_education_fragment);

                if (Current_Fragment == null) {
                    Current_Fragment = resume_education_fragment = new Resume_Education_Fragment(this);
                    ((Resume_Education_Fragment) Current_Fragment).setArguments(bundle);
                    ft.add(R.id.framelayout_resume, (Fragment) Current_Fragment);
                } else {
//                    ft.add(R.id.framelayout_resume, (Fragment)Current_Fragment);
                    ft.show((Fragment) Current_Fragment);
                }
                break;
        }
//        ft.show((Fragment)Current_Fragment);
//        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public String resume_evalution_interface_impl(String str) {
        resume.setSelfdescription(str);
//        Gson gson = new Gson();

        final Commiuncate_Server cs = new Commiuncate_Server();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                try {
                    System.out.println("简历要开始post请求了");
                    resume.setUid(user.getUid());
                    System.out.println("简历编辑者:" + resume.getUid());
                    String json = cs.post_resume(resume);
                    if (json.equals("success")) {
                        Toast.makeText(getBaseContext(), "简历上传成功", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(getBaseContext(), "简历上传失败，请检查错误", Toast.LENGTH_LONG).show();

                    }
                    System.out.println(json);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        thread.start();

        return null;
    }

    @Override
    public String resume_basicinfo_interface_impl(String[] str) {

//        String[] ss = new String[]{set_birthday,set_ideal,set_email,set_tele,set_gender,set_nativeplace,set_name};
        resume.setBirthday(str[0]);
        resume.setIdeal(str[1]);
        resume.setEmail(str[2]);
        resume.setTele(str[3]);
        resume.setGender(str[4]);
        resume.setNativeplace(str[5]);
        resume.setName(str[6]);

        return null;

    }

    @Override
    public String resume_workexperience_interface_impl(List<WorkExperience> list) {
        resume.setWorkexperience(list);
        return null;
    }

    @Override
    public String resume_eduexperience_interface_impl(List<EducationExperience> list) {
        resume.setEduexperience(list);
        return null;
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
