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
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.findai.xkk.ai_interviewer.Dao.Question_Data_Exe;
import com.findai.xkk.ai_interviewer.Http.Commiuncate_Server;
import com.findai.xkk.ai_interviewer.Utils.BitmapUtil;
import com.findai.xkk.ai_interviewer.domain.ApplicationRecord;
import com.findai.xkk.ai_interviewer.domain.Job;
import com.findai.xkk.ai_interviewer.domain.JobList;
import com.findai.xkk.ai_interviewer.domain.QuestionList;
import com.findai.xkk.ai_interviewer.domain.Search;
import com.findai.xkk.ai_interviewer.job_fragment.JobListView_Adapter;
import com.findai.xkk.ai_interviewer.job_fragment.Job_Index_maintop_Fragment;
import com.findai.xkk.ai_interviewer.job_fragment.Myinfo_Index_maintop_Fragment;
import com.findai.xkk.ai_interviewer.job_fragment.Toudi_Index_maintop_Fragment;
import com.oragee.banners.BannerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

public class SearchResultActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    Commiuncate_Server cs = new Commiuncate_Server();
    private List<Job> joblist = new ArrayList<>();
    private List<Map<String, Object>> list;
    GifImageView gifImageView;
    LinearLayout ll_toudi;
    TextView tv_toudi;
    private ListView lv_toudi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toudixiang_fragment);
        lv_toudi = findViewById(R.id.lv_toudi);
        ll_toudi = findViewById(R.id.ll_toudi);
        tv_toudi = findViewById(R.id.tv_toudi);
        lv_toudi.setOnItemClickListener(this);
        gifImageView = findViewById(R.id.img_toudi);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Search s = (Search) getIntent().getBundleExtra("jobidlist").getSerializable("jobidlist");
        String[] ss = s.getJobidlist();
        get_list(ss);
    }


    public void get_list(final String[] idlist) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    for (int i = 0; i <Math.min(50,idlist.length);i++) {

                        try {
                            String sid = idlist[i];
                            int id = Integer.parseInt(sid);
                            Job j = new Job();
                            j.setId_job(id);
                            j = cs.get_jobdetails(j);
                            System.out.println(j.getJobName());
//                        if (j1.getJobName().equals("")||j1.getJobName()==null){
//                            continue;
//                        }
                            j.setBitmap(BitmapUtil.Bitmap2Bytes(cs.get_bitmap_from_url(j.getCompanyLogo())));

                            joblist.add(j);
                        }catch (Exception ex){
                            continue;
                        }


                    }

                    list = getData(joblist);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //                            ll_toudi.setVisibility(View.VISIBLE);
                            lv_toudi.setAdapter(new JobListView_Adapter(getBaseContext(), list));
                            gifImageView.setVisibility(View.GONE);
                            tv_toudi.setVisibility(View.GONE);
                        }
                    });

                    } catch(Exception ex){
                        ex.printStackTrace();
                    }

                }

        }).start();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final int p = position;
        Bundle bundle = new Bundle();
//        joblist.get(position).setBitmap(null);
        bundle.putSerializable("job", joblist.get(position));

        Intent intent = new Intent(this, JobinfoActivity.class);
        intent.putExtra("job", bundle);
        startActivity(intent);

    }

    public List<Map<String, Object>> getData(List<Job> joblist) {

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (Job job : joblist) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", job.getBitmap());
            map.put("jobname", job.getJobName());
            map.put("jobdesc", job.getDegree() + "|" + job.getWorkPlace());
            list.add(map);
        }
        System.out.println(list.size());
//        Collections.shuffle(list);
        return list;
    }


}
