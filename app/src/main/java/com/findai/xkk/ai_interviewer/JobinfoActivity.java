package com.findai.xkk.ai_interviewer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.findai.xkk.ai_interviewer.Http.Commiuncate_Server;
import com.findai.xkk.ai_interviewer.Utils.ACache;
import com.findai.xkk.ai_interviewer.Utils.BitmapUtil;
import com.findai.xkk.ai_interviewer.Utils.GlobalParams;
import com.findai.xkk.ai_interviewer.domain.ApplicationWrapper;
import com.findai.xkk.ai_interviewer.domain.Job;
import com.findai.xkk.ai_interviewer.domain.User;
import com.google.gson.Gson;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.sdsmdg.tastytoast.TastyToast;


public class JobinfoActivity extends AppCompatActivity{

    TextView tv_jobname;
    TextView tv_salary;
    TextView tv_degree_place;
    TextView tv_enddate;
    TextView tv_job_description;
    TextView tv_job_request;
    Job job = new Job();
    Commiuncate_Server cs = new Commiuncate_Server();
    LinearLayout ll_job;
    Button btn_toudi;
    ApplicationWrapper aw = new ApplicationWrapper();
    User user;
    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("进入resume刷新");
        if(is_login()==false){
            btn_toudi.setText("投递");
//            return;
        }else {
            get_job_application_status_for_resume();
            TastyToast.makeText(getApplicationContext(), "Loading……", TastyToast.LENGTH_SHORT, TastyToast.INFO);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jobinfo_activity);
        ShineButton shineButton = (ShineButton) findViewById(R.id.sbtn_shoucang);
        shineButton.init(this);
        btn_toudi = findViewById(R.id.btn_toudi);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        Intent intent = getIntent();
        job = (Job) intent.getBundleExtra("job").getSerializable("job");
        ImageView img_job_img = findViewById(R.id.img_job_img);
        img_job_img.setImageBitmap(BitmapUtil.Bytes2Bimap(job.getBitmap()));
        tv_jobname = findViewById(R.id.tv_jobname);
        tv_salary = findViewById(R.id.tv_salary);
        tv_degree_place = findViewById(R.id.tv_degree_place);
        tv_enddate = findViewById(R.id.tv_enddate);
        tv_job_description = findViewById(R.id.tv_job_description);
        tv_job_request = findViewById(R.id.tv_job_request);
//        System.out.println(questionList.getQuestionList().get(0).answer);
        btn_toudi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(is_login()==false){
                    return;
                }
                get_job_application_status();
                TastyToast.makeText(getApplicationContext(), "投递中……", TastyToast.LENGTH_SHORT, TastyToast.INFO);

            }
        });
//        final Commiuncate_Server cs = new Commiuncate_Server();
        new Thread(new Runnable() {

            @Override
            public void run() {
                try{
                    final Job job_detail = cs.get_jobdetails(job);

//                    Looper.myQueue();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_jobname.setText(job_detail.getJobName());
                            tv_salary.setText(""+job_detail.getPayMin()+"-"+job_detail.getPayMax()+"RMB");
                            tv_degree_place.setText(""+job_detail.getDegree()+"|"+job_detail.getWorkPlace());
                            tv_enddate.setText("截止日期："+job_detail.getEndDate());
                            tv_job_description.setText(job_detail.getJobDescript());
                            tv_job_request.setText(job_detail.getJobRequest());
                        }
                    });

                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }).start();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    public void get_job_application_status(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
//                        String s = "{\"uid\":"+user.getUid()+",\"jid\":"+job.getId_job()+"}";
                try{
                    final String result = cs.post_toudi(user.getUid(),job.getId_job());
                    Gson gson = new Gson();
                    aw = gson.fromJson(result,ApplicationWrapper.class);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(aw.getStatus().equals("error")){

                                TastyToast.makeText(getApplicationContext(), "您已投递，请勿重复投递", TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();
                                btn_toudi.setText("已完成投递");
                                return;
                            }else{
//                                        if(aw.getResult().isDeliver_resume()==true) {
//                                            TastyToast.makeText(getApplicationContext(), "您已投递，请勿重复投递", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
////                                        }else{
//                                        }else{
//                                            TastyToast.makeText(getApplicationContext(), "投递成功", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
//
//                                        }
                                if(aw.getResult().isHave_resume()==false){
                                    TastyToast.makeText(getApplicationContext(), "您还没有创建简历哦，请前去完善简历", TastyToast.LENGTH_SHORT, TastyToast.INFO).show();
                                    Intent intent1 = new Intent(getBaseContext(),Resume_Warehouse_Activity.class);
                                    startActivity(intent1);
//                                    finish();
                                    return;
//                                                btn_toudi.setText("已投递");
                                }
                                if(aw.getResult().isNeed_interview()==false){
                                    TastyToast.makeText(getApplicationContext(), "您已投递成功，该企业无需面试", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
                                    btn_toudi.setText("已完成投递");
                                    return;
                                }else{
                                    if(aw.getResult().isIs_interview()==true){
                                        TastyToast.makeText(getApplicationContext(), "投递成功，您的简历与面试报告均发送至企业", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
                                        btn_toudi.setText("已完成投递");
                                    }else {
                                        TastyToast.makeText(getApplicationContext(), "该企业要求面试，请先参与面试", TastyToast.LENGTH_SHORT, TastyToast.INFO).show();
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("iid",1);
                                        Intent intent = new Intent(getBaseContext(),WelcomeInterviewActivity.class);
                                        intent.putExtra("iid",bundle);
                                        startActivity(intent);
//                                        finish();
                                        return;
                                    }
                                }
                            }
//                                        TastyToast.makeText(getApplicationContext(), "投递成功", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
                        }
//                                }
                    });
                }catch (Exception ex){ex.printStackTrace();}
            }
        });
        thread.start();
    }

    public boolean is_login(){
        user = (User)ACache.get(getBaseContext()).getAsObject(GlobalParams.Para_USER);
        if(user == null){
//                    Toast.makeText(getBaseContext(),"您尚未登录，请进行登录",Toast.LENGTH_SHORT).show();
            TastyToast.makeText(getApplicationContext(), "您尚未登录，请进行登录……", TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();
            Intent intent = new Intent(getBaseContext(),LoginActivity.class);
            startActivity(intent);
            finish();
            return false;
        }
        return true;
    }


    public void get_job_application_status_for_resume(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
//                        String s = "{\"uid\":"+user.getUid()+",\"jid\":"+job.getId_job()+"}";
                try{
                    final String result = cs.check_toudi(user.getUid(),job.getId_job());
                    Gson gson = new Gson();
                    aw = gson.fromJson(result,ApplicationWrapper.class);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(aw == null){

                                return;
                            }
                            if(aw.getStatus().equals("error")){

                                btn_toudi.setText("已完成投递");
                                return;
//                                TastyToast.makeText(getApplicationContext(), "当前投递人数过多，投递失败，请稍后再试", TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();
                            }else{
                                if(aw.getResult().isDeliver_resume()==true) {
//                                    TastyToast.makeText(getApplicationContext(), "您已投递，请勿重复投递", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
                                    //
                                    if (aw.getResult().isHave_resume() == false) {
//                                        TastyToast.makeText(getApplicationContext(), "您还没有创建简历哦，请前去完善简历", TastyToast.LENGTH_SHORT, TastyToast.INFO).show();
                                        btn_toudi.setText("还差一步,创建简历");
//                                        Intent intent1 = new Intent(getBaseContext(), Resume_Warehouse_Activity.class);
//                                        startActivity(intent1);
//                                        finish();
                                        return;
                                        //                                                btn_toudi.setText("已投递");
                                    }
                                    if (aw.getResult().isNeed_interview() == false) {
//                                        TastyToast.makeText(getApplicationContext(), "您已投递成功，该企业无需面试", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
                                        btn_toudi.setText("投递成功");
                                        return;
                                    } else {
                                        if (aw.getResult().isIs_interview() == true) {
//                                            TastyToast.makeText(getApplicationContext(), "投递成功，您的简历与面试报告均发送至企业", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();

                                            btn_toudi.setText("投递成功");
                                            return;
                                        } else {
//                                            TastyToast.makeText(getApplicationContext(), "该企业要求面试，请先参与面试", TastyToast.LENGTH_SHORT, TastyToast.INFO).show();
//                                            Bundle bundle = new Bundle();
//                                            bundle.putInt("iid", 1);
//                                            Intent intent = new Intent(getBaseContext(), WelcomeInterviewActivity.class);
//                                            intent.putExtra("iid", bundle);
//                                            startActivity(intent);
//                                            finish();
                                            btn_toudi.setText("还差一步，完成AI面试");
                                            return;
                                        }
                                    }
                                }
                            }
//                                        TastyToast.makeText(getApplicationContext(), "投递成功", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
                        }
//                                }
                    });
                }catch (Exception ex){ex.printStackTrace();}
            }
        });
        thread.start();
    }

}
