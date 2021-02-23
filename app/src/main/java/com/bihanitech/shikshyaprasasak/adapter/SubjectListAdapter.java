package com.bihanitech.shikshyaprasasak.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

 class SubjectListAdapter extends BaseAdapter {

    // override other abstract methods here

     @Override
     public int getCount() {
         return 0;
     }

     @Override
     public Object getItem(int position) {
         return null;
     }

     @Override
     public long getItemId(int position) {
         return 0;
     }

     @Override
     public View getView(int position, View convertView, ViewGroup parent) {
         return null;
     }

    /* @Override
    public View getView(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(R.layout.item_exam_result_subject, container, false);
        }

        ((TextView) convertView.findViewById(android.R.id.text1))
                .setText(getItem(position));
        return convertView;
    }*/
}