package com.findai.xkk.ai_interviewer.resume_fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.findai.xkk.ai_interviewer.R;
import com.findai.xkk.ai_interviewer.domain.Resume;
import com.findai.xkk.ai_interviewer.domain.WorkExperience;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class Resume_Workexperience_Fragment extends Fragment {


    Button btn_next_resume;
    EditText et_companyname;
    EditText et_jobname;
    EditText et_workyear_start;
    EditText et_workyear_end;
    EditText et_jobdescription;
    Resume resume = new Resume();
    ResumeInterface ri;


    public Resume_Workexperience_Fragment(ResumeInterface cb) {
        this.ri = cb;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_workexperience_resume_fragment, container, false);
        Bundle bundle = getArguments();
        resume = (Resume) bundle.getSerializable("resume");


        et_companyname = view.findViewById(R.id.et_companyname);
        et_jobname = view.findViewById(R.id.et_jobname);
        et_workyear_start = view.findViewById(R.id.et_workyear_start);
        et_workyear_end = view.findViewById(R.id.et_workyear_end);
        et_jobdescription = view.findViewById(R.id.et_jobdescription);

//        btn_next_resume = view.findViewById(R.id.btn_next_resume);
//        btn_next_resume.setOnClickListener(this);


        et_companyname.setText(resume.getWorkexperience().get(0).getCompanyname());
        et_jobname.setText(resume.getWorkexperience().get(0).getJobname());
        et_workyear_start.setText(resume.getWorkexperience().get(0).getWorkyear_start());
        et_workyear_end.setText(resume.getWorkexperience().get(0).getWorkyear_end());
        et_jobdescription.setText(resume.getWorkexperience().get(0).getJobdescription());

        return view;

    }

//    @Override
//    public void onClick(View v) {
//        String set_companyname = et_companyname.getText().toString();
//        String set_jobname = et_jobname.getText().toString();
//        String set_workyear_start = et_workyear_start.getText().toString();
//        String set_workyear_end = et_workyear_end.getText().toString();
//        String set_jobdescription = et_jobdescription.getText().toString();
//        WorkExperience we = new WorkExperience();
//        List<WorkExperience> list = new ArrayList<>();
//        we.setCompanyname(set_companyname);
//        we.setJobdescription(set_jobdescription);
//        we.setJobname(set_jobname);
//        we.setWorkyear_start(set_workyear_start);
//        we.setWorkyear_end(set_workyear_end);
//        list.add(we);
//
//
////        String[] ss = new String[]{set_companyname,set_jobname,set_workyear,set_jobdescription};
//        if(set_companyname==null||set_companyname.equals("")
//                ||set_jobname==null||set_jobname.equals("")
//                ||set_workyear_start==null||set_workyear_start.equals("")
//                ||set_workyear_end==null||set_workyear_end.equals("")
//                ||set_jobdescription==null||set_jobdescription.equals("")
//                ){
//            Toast.makeText(getContext(),"请填写内容哦",Toast.LENGTH_LONG).show();
//            return;
//        }
//        ri.resume_workexperience_interface_impl(list);
//    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        String set_companyname = et_companyname.getText().toString();
        String set_jobname = et_jobname.getText().toString();
        String set_workyear_start = et_workyear_start.getText().toString();
        String set_workyear_end = et_workyear_end.getText().toString();
        String set_jobdescription = et_jobdescription.getText().toString();
        WorkExperience we = new WorkExperience();
        List<WorkExperience> list = new ArrayList<>();
        we.setCompanyname(set_companyname);
        we.setJobdescription(set_jobdescription);
        we.setJobname(set_jobname);
        we.setWorkyear_start(set_workyear_start);
        we.setWorkyear_end(set_workyear_end);
        list.add(we);


//        String[] ss = new String[]{set_companyname,set_jobname,set_workyear,set_jobdescription};
        if (set_companyname == null || set_companyname.equals("")
                || set_jobname == null || set_jobname.equals("")
                || set_workyear_start == null || set_workyear_start.equals("")
                || set_workyear_end == null || set_workyear_end.equals("")
                || set_jobdescription == null || set_jobdescription.equals("")
                ) {
            Toast.makeText(getContext(), "请填写内容哦", Toast.LENGTH_LONG).show();
            return;
        }
        ri.resume_workexperience_interface_impl(list);
    }
}
