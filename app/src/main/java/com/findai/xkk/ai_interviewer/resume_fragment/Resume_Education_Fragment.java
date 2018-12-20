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
import com.findai.xkk.ai_interviewer.domain.EducationExperience;
import com.findai.xkk.ai_interviewer.domain.Resume;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class Resume_Education_Fragment extends Fragment {

    Button btn_next_resume;
    EditText et_university;
    EditText et_major;
    EditText et_degree;
    EditText et_edudescription;
    EditText et_eduyear_start;
    EditText et_eduyear_end;

    Resume resume = new Resume();
    ResumeInterface ri;


    public Resume_Education_Fragment(ResumeInterface cb) {
        this.ri = cb;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_education_resume_fragment, container, false);
        Bundle bundle = getArguments();
        resume = (Resume) bundle.getSerializable("resume");
        et_university = view.findViewById(R.id.et_university);
        et_major = view.findViewById(R.id.et_major);
        et_degree = view.findViewById(R.id.et_degree);
        et_edudescription = view.findViewById(R.id.et_edudescription);
        et_eduyear_start = view.findViewById(R.id.et_eduyear_start);
        et_eduyear_end = view.findViewById(R.id.et_eduyear_end);

//        btn_next_resume = view.findViewById(R.id.btn_next_resume);
//        btn_next_resume.setOnClickListener(this);


        et_university.setText(resume.getEduexperience().get(0).getUniversity());
        et_major.setText(resume.getEduexperience().get(0).getMajor());
        et_degree.setText(resume.getEduexperience().get(0).getDegree());
        et_edudescription.setText(resume.getEduexperience().get(0).getEdudescription());
        et_eduyear_start.setText(resume.getEduexperience().get(0).getEdu_year_start());
        et_eduyear_end.setText(resume.getEduexperience().get(0).getEdu_year_end());


        return view;

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        String set_university = et_university.getText().toString();
        String set_major = et_major.getText().toString();
        String set_degree = et_degree.getText().toString();
        String set_edudescription = et_edudescription.getText().toString();
        String set_eduyear_start = et_eduyear_start.getText().toString();
        String set_eduyear_end = et_eduyear_end.getText().toString();

        EducationExperience ee = new EducationExperience();
        List<EducationExperience> list = new ArrayList<>();
        ee.setDegree(set_degree);
        ee.setEdudescription(set_edudescription);
        ee.setMajor(set_major);
        ee.setUniversity(set_university);
        ee.setEdu_year_start(set_eduyear_start);
        ee.setEdu_year_end(set_eduyear_end);

        list.add(ee);
        String[] ss = new String[]{set_university, set_major, set_degree, set_edudescription};
        if (set_university == null || set_university.equals("")
                || set_major == null || set_major.equals("")
                || set_degree == null || set_degree.equals("")
                || set_edudescription == null || set_edudescription.equals("")
                ) {
            Toast.makeText(getContext(), "请填写内容哦", Toast.LENGTH_LONG).show();
            return;
        }
        ri.resume_eduexperience_interface_impl(list);

    }

//    @Override
//    public void onClick(View v) {
//        String set_university = et_university.getText().toString();
//        String set_major = et_major.getText().toString();
//        String set_degree = et_degree.getText().toString();
//        String set_edudescription = et_edudescription.getText().toString();
//        String set_eduyear_start = et_eduyear_start.getText().toString();
//        String set_eduyear_end = et_eduyear_end.getText().toString();
//
//        EducationExperience ee = new EducationExperience();
//        List<EducationExperience> list = new ArrayList<>();
//        ee.setDegree(set_degree);
//        ee.setEdudescription(set_edudescription);
//        ee.setMajor(set_major);
//        ee.setUniversity(set_university);
//        ee.setEdu_year_start(set_eduyear_start);
//        ee.setEdu_year_end(set_eduyear_end);
//
//        list.add(ee);
//        String[] ss = new String[]{set_university,set_major,set_degree,set_edudescription};
//        if(set_university==null||set_university.equals("")
//                ||set_major==null||set_major.equals("")
//                ||set_degree==null||set_degree.equals("")
//                ||set_edudescription==null||set_edudescription.equals("")
//                ){
//            Toast.makeText(getContext(),"请填写内容哦",Toast.LENGTH_LONG).show();
//            return;
//        }
//        ri.resume_eduexperience_interface_impl(list);
//    }

}
