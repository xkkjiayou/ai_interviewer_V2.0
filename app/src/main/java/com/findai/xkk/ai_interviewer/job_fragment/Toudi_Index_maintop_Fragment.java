package com.findai.xkk.ai_interviewer.job_fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.findai.xkk.ai_interviewer.Http.Commiuncate_Server;
import com.findai.xkk.ai_interviewer.JobinfoActivity;
import com.findai.xkk.ai_interviewer.R;
import com.findai.xkk.ai_interviewer.Utils.ACache;
import com.findai.xkk.ai_interviewer.Utils.BitmapUtil;
import com.findai.xkk.ai_interviewer.Utils.GlobalParams;
import com.findai.xkk.ai_interviewer.domain.ApplicationRecord;
import com.findai.xkk.ai_interviewer.domain.ApplicationRecordWrapper;
import com.findai.xkk.ai_interviewer.domain.Job;
import com.findai.xkk.ai_interviewer.domain.User;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

@SuppressLint("ValidFragment")
public class Toudi_Index_maintop_Fragment extends Fragment implements AdapterView.OnItemClickListener {
    Commiuncate_Server cs = new Commiuncate_Server();
    LinearLayout ll_toudi;
    TextView tv_toudi;
    private ListView lv_toudi;
    GifImageView gifImageView;
    ApplicationRecordWrapper arw;
    User user;
    private List<Map<String, Object>> list;
    private List<Job> joblist = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.toudixiang_fragment, container, false);
        lv_toudi = view.findViewById(R.id.lv_toudi);
        ll_toudi = view.findViewById(R.id.ll_toudi);
        tv_toudi = view.findViewById(R.id.tv_toudi);
        gifImageView = view.findViewById(R.id.img_toudi);
//        ll_toudi.setVisibility(View.GONE);
        lv_toudi.setOnItemClickListener(this);
        tv_toudi.setText("加载中~");
        user = (User) ACache.get(getContext()).getAsObject(GlobalParams.Para_USER);
        if (user == null) {
            TastyToast.makeText(getContext(), "您尚未登录，无法获取哦", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
//            Intent intent = new Intent(getContext(), LoginActivity.class);
//            startActivity(intent);
            return view;
        } else {
            get_list(user.getUid());
        }
        return view;
    }

    public void get_list(final int uid) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    arw = cs.get_applicationrecordlist_by_uid(uid);
                    if (arw.getStatus().equals("error")) {
                        return;
                    } else {
                        if(arw.getApplication_record_list().size()==0){
                            tv_toudi.setText("海量职位等你投递~");
                            gifImageView.setVisibility(View.GONE);
                            return;
                        }
                        for (ApplicationRecord a : arw.getApplication_record_list()) {
                            Job j = new Job();
                            j.setId_job(a.getJobid());
                            j = cs.get_jobdetails(j);
                            j.setBitmap(BitmapUtil.Bitmap2Bytes(cs.get_bitmap_from_url(j.getCompanyLogo())));
                            String Application_status = "已完成投递";
                            if (a.isNeed_interview() == false)
                                Application_status = "已完成投递";
                            else if (a.isIs_interview() == false)
                                Application_status = "尚未完成面试";
//                            Application_status
                            j.setApplication_status(Application_status);
                            joblist.add(j);
                            list = getData(joblist);
                        }
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            ll_toudi.setVisibility(View.VISIBLE);
                            lv_toudi.setAdapter(new JobListView_Adapter(getContext(), list));
                            gifImageView.setVisibility(View.GONE);
                            tv_toudi.setVisibility(View.GONE);
                        }
                    });
                } catch (Exception ex) {
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

        Intent intent = new Intent(getContext(), JobinfoActivity.class);
        intent.putExtra("job", bundle);
        startActivity(intent);

    }

    public List<Map<String, Object>> getData(List<Job> joblist) {

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (Job job : joblist) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", job.getBitmap());
            map.put("jobname", job.getJobName());
            map.put("jobdesc", job.getApplication_status());
            list.add(map);
        }
        System.out.println(list.size());
//        Collections.shuffle(list);
        return list;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (user != null)
            get_list(user.getUid());
    }
}
