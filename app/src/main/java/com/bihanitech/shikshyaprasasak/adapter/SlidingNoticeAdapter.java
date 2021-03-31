package com.bihanitech.shikshyaprasasak.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.model.Notice;

import java.util.List;

public class SlidingNoticeAdapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater;
    private final List<Notice> noticeList;

    public SlidingNoticeAdapter(Context context, List<Notice> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View textLayout = inflater.inflate(R.layout.notification_layout, view, false);

        assert textLayout != null;
        final TextView tvMainTitle = textLayout
                .findViewById(R.id.tvMainTitle);
        final TextView tvMainContent = textLayout.findViewById(R.id.tvDetail1);

        final TextView tvDate = textLayout.findViewById(R.id.tvDateOfIssue);

        tvMainTitle.setText(Html.fromHtml(noticeList.get(position).getTitle()));
        tvMainContent.setText(Html.fromHtml(noticeList.get(position).getContent()));
        tvDate.setText(noticeList.get(position).getDate().split(" ")[0]);


        view.addView(textLayout, 0);

        return textLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
