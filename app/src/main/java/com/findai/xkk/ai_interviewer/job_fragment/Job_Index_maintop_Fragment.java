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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.findai.xkk.ai_interviewer.JobinfoActivity;
import com.findai.xkk.ai_interviewer.R;
import com.findai.xkk.ai_interviewer.WelcomeIndexActivity;
import com.findai.xkk.ai_interviewer.domain.Question;
import com.oragee.banners.BannerView;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class Job_Index_maintop_Fragment extends Fragment implements View.OnClickListener{

    BannerView bannerView;
    @Override
    public void onClick(View v) {
        Bundle bundle;
        Intent intent;
        switch (v.getId()){
            case R.id.btn_kj_interview:
                bundle = new Bundle();
                bundle.putInt("iid",1);
                intent = new Intent(getContext(),WelcomeIndexActivity.class);
                intent.putExtra("iid",bundle);
                startActivity(intent);
                break;
            case R.id.ll_job:
                bundle = new Bundle();
                bundle.putInt("jid",1);
                intent = new Intent(getContext(),JobinfoActivity.class);
                intent.putExtra("jid",bundle);
                startActivity(intent);
                break;
        }
    }

    callbackQuestion_Choose_Fragment callbackQuestion_choose_fragment = null;
    private Button btn_kj;
    private LinearLayout ll_job;
    public Job_Index_maintop_Fragment() {
    }

    public Job_Index_maintop_Fragment(callbackQuestion_Choose_Fragment callbackQuestionChooseFragment) {
        this.callbackQuestion_choose_fragment = callbackQuestionChooseFragment;

    }
    private int[] imgs = {R.mipmap.ad1,R.mipmap.ad3,R.mipmap.ad4,R.mipmap.ad6,R.mipmap.ad7,R.mipmap.ad8};
    private List<View> viewList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.job_center_maintop_activity, container, false);


        viewList = new ArrayList<View>();
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

        bannerView.setViewList(viewList);
//        RadioGroup radioGroup = view.findViewById(R.id.tv_question_radio);
//        Bundle bundle = getArguments();
//        Question q = (Question) bundle.getSerializable("question");
//        TextView qtitle = (TextView) view.findViewById(R.id.tv_question_title);
//        qtitle.setText(q.getTitle());
//        int i = 0;
//        for (String qchoose_item : q.getQuestion_choose_items()) {
//            RadioButton rb = new RadioButton(getContext());
//            rb.setId(i);
////            rb.setb
//            rb.setText(qchoose_item);
//            i++;
//            radioGroup.addView(rb);
//        }
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
////                System.out.println("------------------");
////                String answer = ((RadioButton)group.getChildAt(checkedId)).getText().toString();
////                System.out.println(answer);
//                callbackQuestion_choose_fragment.get_question_answer(checkedId);
//            }
//        });
        btn_kj = view.findViewById(R.id.btn_kj_interview);
        btn_kj.setOnClickListener(this);
        ll_job = view.findViewById(R.id.ll_job);
        ll_job.setOnClickListener(this);
        return view;

    }

    public interface callbackQuestion_Choose_Fragment {
        public int get_question_answer(int answer);
    }

}
