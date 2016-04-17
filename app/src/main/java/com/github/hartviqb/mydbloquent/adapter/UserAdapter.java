package com.github.hartviqb.mydbloquent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.hartviqb.mydbloquent.R;
import com.github.hartviqb.mydbloquent.data.UserData;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by hartviq <apiq404@gmail.com> on 4/17/16.
 */
public class UserAdapter extends BaseAdapter {
    private List<UserData> userDatas;
    private Context context;
    private LayoutInflater inflater;

    public UserAdapter(Context context,List<UserData> userDatas){
        this.context = context;
        this.userDatas = userDatas;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return userDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return userDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.listview_element, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        UserData data = userDatas.get(position);
        viewHolder.txtId.setText(data.getId().toString());
        viewHolder.txtName.setText(data.getName());
        viewHolder.txtPhone.setText(data.getPhone().toString());
        return convertView;
    }

    private class ViewHolder{
        TextView txtId, txtName, txtPhone;
        public ViewHolder(View item){
            txtId = (TextView) item.findViewById(R.id.listview_element_txt_id);
            txtName = (TextView) item.findViewById(R.id.listview_element_txt_name);
            txtPhone = (TextView) item.findViewById(R.id.listview_element_txt_phone);
        }
    }
}
