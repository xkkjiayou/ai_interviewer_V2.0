package com.findai.xkk.ai_interviewer.Http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.findai.xkk.ai_interviewer.Utils.BitmapUtil;
import com.findai.xkk.ai_interviewer.domain.ApplicationRecordWrapper;
import com.findai.xkk.ai_interviewer.domain.Job;
import com.findai.xkk.ai_interviewer.domain.JobList;
import com.findai.xkk.ai_interviewer.domain.JobWrapper;
import com.findai.xkk.ai_interviewer.domain.QuestionList;
import com.findai.xkk.ai_interviewer.domain.Resume;
import com.findai.xkk.ai_interviewer.domain.ResumeWrapper;
import com.findai.xkk.ai_interviewer.domain.Search;
import com.findai.xkk.ai_interviewer.domain.User;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Commiuncate_Server {

    static OkHttpClient client;

    static {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.connectTimeout(1, TimeUnit.MINUTES) // connect timeout
                .writeTimeout(1, TimeUnit.MINUTES) // write timeout
                .readTimeout(1, TimeUnit.MINUTES); // read timeout

        client = builder.build();
    }

    Gson gson = new Gson();
    String get_question_url = "http://115.159.59.188:5000/get_question_by_industry_id?iid=";
    String post_answer_url = "http://115.159.59.188:5000/post_answer";
    String post_login_url = "http://115.159.59.188:5000/login";
    String post_register_url = "http://115.159.59.188:5000/register";
    String post_resume_url = "http://115.159.59.188:5000/add_resume";
    String get_resume_by_uid_url = "http://115.159.59.188:5000/get_resume_by_uid?uid=";
    String get_jobdetails_by_jid_url = "http://115.159.59.188:5000/get_jobdetails?jobid=";
    String get_lastest_joblist_url = "http://115.159.59.188:5000/get_joblist?topk=";
    String get_index_load_bitmap_url = "http://115.159.59.188:5000/get_loading_image";
    String post_toudi_url = "http://115.159.59.188:5000/post_job_application";
    String check_toudi_url = "http://115.159.59.188:5000/check_job_application";
    String get_applicationrecordlist_by_uid_url = "http://115.159.59.188:5000/get_application_record_list_by_uid?uid=";
    String search_url = "http://findai.wang:9000/get_search_result/?searchcontent=";
    public QuestionList get_question_by_iid(int qid) throws Exception {
//        OkHttpClient client = new OkHttpClient();
        String qurl = get_question_url + qid;
        System.out.println(qurl);
        Request request = new Request.Builder().url(qurl).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String str = response.body().string();
//            System.out.println(str);
            return get_question_list(str);
        } else {
            throw new IOException("Unexpected code " + response);
        }
//        return get_question_list("");
    }

    public String get_my_report_by_id(int uid) throws IOException {
//        OkHttpClient client = new OkHttpClient();
        String qurl = get_question_url + "get_user_report_list/?userid=" + uid;
        Request request = new Request.Builder().url(qurl).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }


    private QuestionList get_question_list(String resp_json) throws Exception {

//        resp_json = "{\"questionList\":[\n" +
//                "\t\t\t\t{\"qid\":11,\"qtitle\":\"人工智能好不好\",\"question_choose_items\":[\"好的图塔糊涂哦\",\"不好\",\"凑合吧\"],\"answer\":\"\",\"type\":\"0\"}\t,{\"qid\":4,\"qtitle\":\"LSTM原理是什么样的，请简述\",\"question_choose_items\":[],\"answer\":\"\",\"type\":\"1\"},{\"qid\":5,\"qtitle\":\"计算机好不好\",\"question_choose_items\":[\"好\",\"不好\"],\"answer\":\"\",\"type\":\"0\"}\t\t\t\n" +
//                "\t\t\t\t\t\n" +
//                "\n" +
//                "\n" +
//                "\t\t\t\t]}";
        JSONObject json = new JSONObject(resp_json);
        System.out.println(resp_json);
        Gson gson = new Gson();
        QuestionList qlist = gson.fromJson(resp_json, QuestionList.class);
        return qlist;
    }

    public String post_answer(String answer) throws Exception {
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.connectTimeout(1, TimeUnit.MINUTES) // connect timeout
//                .writeTimeout(1, TimeUnit.MINUTES) // write timeout
//                .readTimeout(1, TimeUnit.MINUTES); // read timeout
//
//        OkHttpClient client = builder.build();
        String url = post_answer_url;
        System.out.println(url);

//        okHttpClient = builder.build();
//        client.conn(30, TimeUnit.SECONDS); // connect timeout
//        client.setReadTimeout(30, TimeUnit.SECONDS);    // socket timeout
        RequestBody requestbody = new FormBody.Builder()
                .add("user_answer", answer)
                .build();

        Request request = new Request.Builder()
                .url(post_answer_url)
                .post(requestbody)
                .build();
        System.out.println(request.toString());
        Response response;
        try {
            response = client.newCall(request).execute();
            String jsonString = response.body().string();
            Log.d("面试结果上传", jsonString);

//            Gson gson = new Gson();
            return jsonString;

        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }


    }

    public String post_login(User user) throws Exception {

//        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("user", gson.toJson(user))
//                .add("password",user.getPassword())
                .build();
        Request request = new Request.Builder().url(post_login_url).post(requestBody).build();
        Response response;
        try {
            response = client.newCall(request).execute();
            String jsonString = response.body().string();
            Log.i("登录", jsonString);
            return jsonString;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public String post_register(User user) throws Exception {
//        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("user", gson.toJson(user))
//                .add("password",user.getPassword())
                .build();
        Request request = new Request.Builder().url(post_register_url).post(requestBody).build();
        Response response;
        try {
            response = client.newCall(request).execute();
            String jsonString = response.body().string();
            Log.i("注册", jsonString);
            return jsonString;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }


    public String post_resume(Resume resume) throws Exception {
//        OkHttpClient client = new OkHttpClient();
        System.out.println("上传的简历：" + gson.toJson(resume));
        RequestBody requestBody = new FormBody.Builder()
                .add("resume", gson.toJson(resume))
//                .add("password",user.getPassword())
                .build();
        Request request = new Request.Builder().url(post_resume_url).post(requestBody).build();
        Response response;
        try {
            response = client.newCall(request).execute();
            String jsonString = response.body().string();
            Log.i("注册", jsonString);
            return jsonString;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }


    public Resume get_resume_by_uid(User user) throws Exception {

//        OkHttpClient client = new OkHttpClient();
        String qurl = get_resume_by_uid_url + user.getUid();
        System.out.println(qurl);
        Resume resume = new Resume();
        Request request = new Request.Builder().url(qurl).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String str = response.body().string();
//            System.out.println(str);
            ResumeWrapper rw = gson.fromJson(str, ResumeWrapper.class);
            if (rw.getStatus().equals("success")) {
                resume = rw.getResume();
                resume.setHasResume(true);
            } else {
                resume = new Resume();
                resume.setHasResume(false);
            }
            return resume;
        } else {

//            throw new IOException("Unexpected code " + response);
            return new Resume();

        }
    }


    public JobList get_joblist(int topk) throws Exception {

        String qurl = get_lastest_joblist_url + topk;
        System.out.println(qurl);
        List<Job> joblist = new ArrayList<>();
        Request request = new Request.Builder().url(qurl).build();
        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            String str = response.body().string();
//            System.out.println(str);
            JobList rw = gson.fromJson(str, JobList.class);
            if (rw.getStatus().equals("success")) {
                for (Job j : rw.getJobList()) {

                    j.setBitmap(BitmapUtil.Bitmap2Bytes(get_bitmap_from_url(j.getCompanyLogo())));
                }
            }

            return rw;
        }
        return new JobList();
    }

    public Job get_jobdetails(Job job) throws Exception {

//        OkHttpClient client = new OkHttpClient();
        String qurl = get_jobdetails_by_jid_url + job.getId_job();
        System.out.println(qurl);
        JobWrapper jobWrapper = new JobWrapper();
        Request request = new Request.Builder().url(qurl).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String str = response.body().string();
            System.out.println(str);
            JobWrapper rw = gson.fromJson(str, JobWrapper.class);
            if (rw.getStatus().equals("success")) {
                job = rw.getJob_details();
            }
        }

        return job;
    }

    public Bitmap get_bitmap_from_url(String url) throws Exception {
//        OkHttpClient client = new OkHttpClient();
        Request imgrequest = new Request.Builder()
                .url(url)
                .build();
        Response img_response = client.newCall(imgrequest).execute();
        InputStream inputStream = img_response.body().byteStream();//得到图片的流
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//        j.setBitmap(BitmapUtil.Bitmap2Bytes(bitmap));
        return bitmap;
    }


    public Bitmap get_index_load_bitmap_url() throws Exception {

//        OkHttpClient client = new OkHttpClient();
        String qurl = get_index_load_bitmap_url;
        System.out.println(qurl);
//        JobWrapper jobWrapper = new JobWrapper();
        Request request = new Request.Builder().url(qurl).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String str = response.body().string();
            return get_bitmap_from_url(str);
//            System.out.println(str);
//            JobWrapper rw  = gson.fromJson(str,JobWrapper.class);
//            if(rw.getStatus().equals("success")){
//                job = rw.getJob_details();
//            }
        }

        return null;
    }


    public String post_toudi(int uid, int jid) throws Exception {
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//
//        builder.connectTimeout(1, TimeUnit.MINUTES) // connect timeout
//                .writeTimeout(1, TimeUnit.MINUTES) // write timeout
//                .readTimeout(1, TimeUnit.MINUTES); // read timeout
//
//        OkHttpClient client = builder.build();
        String qurl = post_toudi_url + "?uid=" + uid + "&jid=" + jid + "";
        System.out.println(qurl);

        Request request = new Request.Builder().url(qurl).build();
//        System.out.println(request.toString());
        Response response;
        try {
            response = client.newCall(request).execute();
            String jsonString = response.body().string();
            Log.d("投递结果上传", jsonString);

//            Gson gson = new Gson();
            return jsonString;

        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }


    }


    public String check_toudi(int uid, int jid) throws Exception {
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//
//        builder.connectTimeout(1, TimeUnit.MINUTES) // connect timeout
//                .writeTimeout(1, TimeUnit.MINUTES) // write timeout
//                .readTimeout(1, TimeUnit.MINUTES); // read timeout
//
//        OkHttpClient client = builder.build();
        String qurl = check_toudi_url + "?uid=" + uid + "&jid=" + jid + "";
        System.out.println(qurl);

        Request request = new Request.Builder().url(qurl).build();
//        System.out.println(request.toString());
        Response response;
        try {
            response = client.newCall(request).execute();
            String jsonString = response.body().string();
            Log.d("投递结果上传", jsonString);

//            Gson gson = new Gson();
            return jsonString;

        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public ApplicationRecordWrapper get_applicationrecordlist_by_uid(int uid) {
        String qurl = get_applicationrecordlist_by_uid_url + uid;
        Request request = new Request.Builder().url(qurl).build();
        System.out.println(qurl);
        ApplicationRecordWrapper arw = new ApplicationRecordWrapper();
        try {
            Response response = client.newCall(request).execute();
            ;
            String jsonString = response.body().string();
            System.out.println(jsonString);
            System.out.println("=============3==============");
            arw = gson.fromJson(jsonString, ApplicationRecordWrapper.class);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return arw;
    }


    public Search get_search_result(String  content) {
        String qurl = search_url + content;
        Request request = new Request.Builder().url(qurl).build();
        System.out.println(qurl);
        Search s = new Search();
        try {
            Response response = client.newCall(request).execute();

            String jsonString = response.body().string();
            System.out.println(jsonString);
            System.out.println("=============3==============");
            s = gson.fromJson(jsonString, Search.class);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return s;
    }

}
