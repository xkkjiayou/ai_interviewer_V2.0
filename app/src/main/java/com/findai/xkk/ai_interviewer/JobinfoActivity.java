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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.findai.xkk.ai_interviewer.Http.Commiuncate_Server;
import com.findai.xkk.ai_interviewer.Utils.ACache;
import com.findai.xkk.ai_interviewer.Utils.GlobalParams;
import com.findai.xkk.ai_interviewer.domain.Job;
import com.findai.xkk.ai_interviewer.domain.User;
import com.google.gson.Gson;
import com.sdsmdg.tastytoast.TastyToast;


public class JobinfoActivity extends AppCompatActivity{

    TextView tv_jobname;
    TextView tv_salary;
    TextView tv_degree_place;
    TextView tv_enddate;
    TextView tv_job_description;
    TextView tv_job_request;
    Job job = new Job();
    LinearLayout ll_job;
    Button btn_toudi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jobinfo_activity);
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

        tv_jobname = findViewById(R.id.tv_jobname);
        tv_salary = findViewById(R.id.tv_salary);
        tv_degree_place = findViewById(R.id.tv_degree_place);
        tv_enddate = findViewById(R.id.tv_enddate);
        tv_job_description = findViewById(R.id.tv_job_description);
        tv_job_request = findViewById(R.id.tv_job_request);
//        System.out.println(questionList.getQuestionList().get(0).answer);
        final Commiuncate_Server cs = new Commiuncate_Server();
        btn_toudi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final User user = (User)ACache.get(getBaseContext()).getAsObject(GlobalParams.Para_USER);
                if(user == null){
//                    Toast.makeText(getBaseContext(),"您尚未登录，请进行登录",Toast.LENGTH_LONG).show();
                    TastyToast.makeText(getApplicationContext(), "您尚未登录，请进行登录……", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
                    Intent intent = new Intent(getBaseContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
                TastyToast.makeText(getApplicationContext(), "投递中……", TastyToast.LENGTH_LONG, TastyToast.INFO);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String s = "{\"uid\":"+user.getUid()+",\"jid\":"+job.getId_job()+"}";
                        try{
                            final String result = cs.post_toudi(s);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(result.equals("error")){

                                        TastyToast.makeText(getApplicationContext(), "当前投递人数过多，投递失败，请稍后再试", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
                                    }else{

                                        TastyToast.makeText(getApplicationContext(), "投递成功", TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
                                    }
                                }
                            });
                        }catch (Exception ex){ex.printStackTrace();}
                    }
                });
                thread.start();
            }
        });
//        final Commiuncate_Server cs = new Commiuncate_Server();
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try{
                    final Job job_detail = cs.get_joblist(job);

//                    Looper.myQueue();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_jobname.setText(job_detail.getJobName());
                            tv_salary.setText(""+job_detail.getPayMin()+"-"+job_detail.getPayMax()+"RMB");
                            tv_degree_place.setText(""+job_detail.getDegree()+"|"+job_detail.getWorkPlace());
                            tv_enddate.setText(""+job_detail.getEndDate());
                            tv_job_description.setText(job_detail.getJobDescript());
                            tv_job_request.setText(job_detail.getJobRequest());
                        }
                    });

                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        thread.start();



        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

}
