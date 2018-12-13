package com.findai.xkk.ai_interviewer.job_fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.findai.xkk.ai_interviewer.R;
import com.findai.xkk.ai_interviewer.Utils.BitmapUtil;

import java.util.List;
import java.util.Map;

public class JobListView_Adapter extends BaseAdapter {
    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;


    public JobListView_Adapter(Context context, List<Map<String, Object>> data) {


        this.context=context;
        this.data=data;
        this.layoutInflater=LayoutInflater.from(context);


    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public final class Zujian{
        public ImageView img_job_img;
        public TextView tv_job_name;
        public TextView tv_jbdesc;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Zujian zujian = null;

        if(convertView==null) {
            zujian = new Zujian();
            convertView = layoutInflater.inflate(R.layout.job_listview_item, null);
            zujian.tv_job_name = convertView.findViewById(R.id.tv_jobname);
            zujian.tv_jbdesc = convertView.findViewById(R.id.tv_jbdesc);
            zujian.img_job_img = convertView.findViewById(R.id.img_job_img);
            convertView.setTag(zujian);
        }else {
            zujian = (Zujian)convertView.getTag();
        }

        zujian.tv_jbdesc.setText(data.get(position).get("jobdesc").toString());
        zujian.tv_job_name.setText(data.get(position).get("jobname").toString());
        zujian.img_job_img.setImageBitmap(BitmapUtil.toRoundBitmap(BitmapUtil.Bytes2Bimap((byte[]) data.get(position).get("image"))));
        return convertView;

    }
}
