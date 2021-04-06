package com.bihanitech.shikshyaprasasak.curveGraph;

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

        String data = e.getData().toString();
        String dataNew = data.substring(1, data.length() - 1);
        String[] list = dataNew.split(", ");
       /* char[] ch = new char[40];
        char[] newCh = new char[40];
        for (int i = 0; i < ch.length; i++) {
            ch[i] = data.charAt(i);
        }
        for (int i = 1; i < ch.length - 1; i++) {
            ch[i] = newCh[i];
        }*/

        Log.d(TAG, "refreshContent: " + dataNew);
        //tvContent.setText("" + highlight.getStackIndex() + e.getData());
        super.refreshContent(e, highlight);
        tvContent.setText(list[highlight.getStackIndex()]);

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

