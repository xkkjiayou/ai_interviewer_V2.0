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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.findai.xkk.ai_interviewer.Http.Commiuncate_Server;
import com.findai.xkk.ai_interviewer.JobinfoActivity;
import com.findai.xkk.ai_interviewer.R;
import com.findai.xkk.ai_interviewer.WelcomeInterviewActivity;
import com.findai.xkk.ai_interviewer.domain.Job;
import com.findai.xkk.ai_interviewer.domain.JobList;
import com.oragee.banners.BannerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressLint("ValidFragment")
public class Job_Index_maintop_Fragment extends Fragment implements View.OnClickListener, ListView.OnItemClickListener {
    final Commiuncate_Server cs = new Commiuncate_Server();
    BannerView bannerView;

    callbackQuestion_Choose_Fragment callbackQuestion_choose_fragment = null;
    boolean job_loaded_flag = false;
    private Button btn_kj;
    private List<Job> joblist = new ArrayList<>();
    private LinearLayout ll_job;
    private ListView lv;
    private List<Map<String, Object>> data;
    private int[] imgs = {R.mipmap.ad4, R.mipmap.ad6, R.mipmap.ad8, R.mipmap.ad1, R.mipmap.ad3};
    private List<View> viewList;

    public Job_Index_maintop_Fragment() {
    }

    public Job_Index_maintop_Fragment(callbackQuestion_Choose_Fragment callbackQuestionChooseFragment) {
        this.callbackQuestion_choose_fragment = callbackQuestionChooseFragment;

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

    @Override
    public void onClick(View v) {
        Bundle bundle;
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_kj_interview:
                bundle = new Bundle();
                bundle.putInt("iid", 1);
                intent = new Intent(getContext(), WelcomeInterviewActivity.class);
                intent.putExtra("iid", bundle);
                startActivity(intent);
                break;
//            case R.id.ll_job:
//                bundle = new Bundle();
//                bundle.putInt("jid",1);
//                intent = new Intent(getContext(),JobinfoActivity.class);
//                intent.putExtra("jid",bundle);
//                startActivity(intent);
//                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.job_center_maintop_activity, container, false);
        lv = view.findViewById(R.id.lv_joblist);
        Bundle bundle = getArguments();
        joblist = ((JobList) bundle.getSerializable("joblist")).getJobList();
        data = getData();
        lv.setAdapter(new JobListView_Adapter(getContext(), data));
        fixListViewHeight(lv);

//                                fixListViewHeight(lv);
//
//        Thread thread1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try{
//                    joblist = cs.get_joblist(10);
//                    data = getData();
////                    System.out.println(joblist.size()+"OK!!!!!!!!");
//                    job_loaded_flag = true;
//                }catch (Exception ex){
//                    ex.printStackTrace();
//                }
//            }
//        });
//        thread1.start();
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
//        System.out.println(data.size()+"----------------===");

        viewList = new ArrayList<View>();
        lv.setOnItemClickListener(this);
        for (int i = 0; i < imgs.length; i++) {
            ImageView image = new ImageView(getContext());
            image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //设置显示格式
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            image.setImageResource(imgs[i]);
            viewList.add(image);
        }
        bannerView = (BannerView) view.findViewById(R.id.banner_ad);
        bannerView.startLoop(true);
        bannerView.setLoopInterval(3000);
        bannerView.setViewList(viewList);

        btn_kj = view.findViewById(R.id.btn_kj_interview);
        btn_kj.setOnClickListener(this);
//        ll_job = view.findViewById(R.id.ll_job);
//        ll_job.setOnClickListener(this);

        lv.setFocusable(false);
        ((ScrollView) view.findViewById(R.id.sv_job_lastest)).scrollTo(0, 20);

        return view;

    }

    public List<Map<String, Object>> getData() {

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

    public void fixListViewHeight(ListView listView) {

        // 如果没有设置数据适配器，则ListView没有子项，返回。

        JobListView_Adapter listAdapter = (JobListView_Adapter) listView.getAdapter();

        int totalHeight = 0;

        if (listAdapter == null) {

            return;

        }

        for (int index = 0, len = listAdapter.getCount(); index < len; index++) {

            View listViewItem = listAdapter.getView(index, null, listView);

            // 计算子项View 的宽高

            listViewItem.measure(0, 0);

            // 计算所有子项的高度和

            totalHeight += listViewItem.getMeasuredHeight();

        }


        ViewGroup.LayoutParams params = listView.getLayoutParams();

        // listView.getDividerHeight()获取子项间分隔符的高度

        // params.height设置ListView完全显示需要的高度

        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);


    }


    public interface callbackQuestion_Choose_Fragment {
        public int get_question_answer(int answer);
    }


}
