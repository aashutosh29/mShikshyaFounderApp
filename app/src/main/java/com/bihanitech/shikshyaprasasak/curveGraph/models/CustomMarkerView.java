package com.bihanitech.shikshyaprasasak.curveGraph.models;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.bihanitech.shikshyaprasasak.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bihanitech.shikshyaprasasak.ui.homeActivity.homeFragment.HomeFragmentPresenter.TAG;

public class CustomMarkerView extends MarkerView {

    @BindView(R.id.tvContent)
    TextView tvContent;

    @BindView(R.id.tvTotal)
    TextView tvTotal;


    private MPPointF mOffset;


    public CustomMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        ButterKnife.bind(this);
        // this markerview only displays a textview
        //tvContent = (TextView) findViewById(R.id.tvContent);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        Log.d(TAG, "Aashutosh: " + highlight.getStackIndex() + " " + highlight.getDataSetIndex() + " " + highlight.getDataIndex() + " " + highlight.getY() + " " + e.getData());
        String data = e.getData().toString();
        Log.d(TAG, "refreshContent: " + e);
        String dataNew = data.substring(1, data.length() - 1);
        String[] list = dataNew.split(", ");
        String[] scoreAndTotal = list[highlight.getStackIndex()].split("@");
        Log.d(TAG, "refreshContent: " + dataNew);
        //tvContent.setText("" + highlight.getStackIndex() + e.getData());

        //tvContent.setText(list[highlight.getStackIndex()]);
        tvContent.setText(scoreAndTotal[0]);
        tvTotal.setText(scoreAndTotal[1]);
        super.refreshContent(e, highlight);
        // set the entry-value as the display text
    }

    @Override
    public MPPointF getOffset() {

        if (mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = new MPPointF(-(getWidth() / 2), -getHeight());
        }

        return mOffset;
    }
}