package pjh.mjc.Project_GIMAL_2017081066;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.HashMap;
//커스텀 어댑터
public class PostListAdapter extends BaseAdapter {
    Context context;

    HashMap<Integer, String> title = new HashMap<Integer, String>();
    HashMap<Integer, String> date = new HashMap<Integer, String>();

    public PostListAdapter(Context context, HashMap<Integer, String> title, HashMap<Integer, String> date) {
        this.context = context;
        this.title = title;
        this.date = date;
    }
    @Override
    public int getCount() {
        return title.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.post_list_component, parent, false);

        TextView title_tv;
        TextView date_tv;

        title_tv = convertView.findViewById(R.id.post_list_component_title);
        date_tv = convertView.findViewById(R.id.post_list_component_date);

        //HashMap의 각 값들을 넣기.
        int minKey = Collections.min(title.keySet());
        try {
            title_tv.setText(title.get(position + minKey).toString());
            date_tv.setText(date.get(position + minKey).toString());
        } catch (Exception e){
            while(title.get(position + minKey) == null) {
                minKey += 1;
                title_tv.setText(title.get(position + minKey).toString());
                date_tv.setText(date.get(position + minKey).toString());
            }
        }
        return convertView;
    }
}

