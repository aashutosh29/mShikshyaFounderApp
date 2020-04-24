package com.bihanitech.shikshyaprasasak.ui.homeActivity.homeFragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.noticeActivity.NoticeActivity;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.noticeActivity.noticeDetailAcitivity.NoticeDetailActivity;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.bihanitech.shikshyaprasasak.utility.sharedPreference.SharedPrefsHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends Fragment {

    @BindView(R.id.tvTitle1)
    TextView tvTitle1;


    @BindView(R.id.tvDetail1)
    TextView tvDetail1;


    @BindView(R.id.cvNotice1)
    ConstraintLayout cvNotice1;


    @BindView(R.id.btMore)
    TextView btMore;


    @BindView(R.id.clNoNotice)
    ConstraintLayout clNoNotice;

    SharedPrefsHelper sharedPrefsHelper;

    String orgNotice1 = "",orgNotice2 = "";

    public HomeFragment(){

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        ButterKnife.bind(this,view);
        sharedPrefsHelper = SharedPrefsHelper.getInstance(getContext());
        setUpNoticeList();
        return view;
    }

    private void setUpNoticeList() {
        int count = sharedPrefsHelper.getValue(Constant.NOTICE_COUNT,0);
        if(count==0){
            cvNotice1.setVisibility(View.VISIBLE);
            //cvNotice2.setVisibility(View.VISIBLE);
            //vBar.setVisibility(View.GONE);
            btMore.setVisibility(View.VISIBLE);
            clNoNotice.setVisibility(View.GONE);
        }else{

            clNoNotice.setVisibility(View.GONE);
            btMore.setVisibility(View.VISIBLE);
            if(count > 0){
                cvNotice1.setVisibility(View.VISIBLE);
                tvTitle1.setText(sharedPrefsHelper.getValue(Constant.NOTICE_TITLE1,""));
                String noticeDetail1 = sharedPrefsHelper.getValue(Constant.NOTICE_DETAIL1,"");
                orgNotice1 = noticeDetail1;
                if(noticeDetail1.length()>=80){
                    noticeDetail1 = noticeDetail1.substring(0,80)+"...";
                }
                tvDetail1.setText(noticeDetail1);

                //tvDate1.setText(sharedPrefsHelper.getValue(Constant.P_DATE1,""));
                // tvDate1.setText(convertDate(sharedPrefsHelper.getValue(Constant.P_DATE1,"")));
            }

            if(count>1){
                //vBar.setVisibility(View.VISIBLE);
                //cvNotice2.setVisibility(View.VISIBLE);
                //tvTitle2.setText(sharedPrefsHelper.getValue(Constant.NOTICE_TITLE2,""));
                String noticeDetail2 = sharedPrefsHelper.getValue(Constant.NOTICE_DETAIL2,"");
                orgNotice2 = noticeDetail2;
                if(noticeDetail2.length()>=80){
                    noticeDetail2 = noticeDetail2.substring(0,80)+"...";
                }
                // tvDetail2.setText(noticeDetail2);

                /*                tvDate2.setText(sharedPrefsHelper.getValue(Constant.P_DATE2,""));
*/
                //tvDate2.setText(convertDate(sharedPrefsHelper.getValue(Constant.P_DATE2,"")));

            }

            if(count == 1){

            }
        }

    }


  /*  @OnClick(R.id.tvNotices)
    public void tvNoticesClicked(){
        sendToNoticeList();
    }*/

    @OnClick(R.id.btMore)
    public void btMoreClicked(){
        sendToNoticeList();
    }


    private void sendToNoticeList(){
        startActivity(new Intent(getActivity(), NoticeActivity.class));
    }


  /*  @OnClick(R.id.cvContacts)
    public void cvContactsClicked(){startActivity(new Intent(getActivity(), ContactActivity.class));}*/



    @OnClick(R.id.cvNotice1)
    public void notice1Clickded(){
        sendToNoticeDetail(1);
    }

  /*  @OnClick(R.id.cvNotice2)
    public void notice2Clicked(){
        sendToNoticeDetail(2);
    }*/

    private void sendToNoticeDetail(int i) {
        Intent intent = new Intent(getActivity(), NoticeDetailActivity.class);

        if(i == 1) {
            intent.putExtra(Constant.NOTICE_TITLE, tvTitle1.getText().toString());
            intent.putExtra(Constant.NOTICE_DETAIL, orgNotice1);
            //intent.putExtra(Constant.NOTICE_DATE, tvDate1.getText().toString());
        }else{
            // intent.putExtra(Constant.NOTICE_TITLE, tvTitle2.getText().toString());
            intent.putExtra(Constant.NOTICE_DETAIL, orgNotice2);
            //intent.putExtra(Constant.NOTICE_DATE, tvDate2.getText().toString());

        }

        startActivity(intent);
    }


    public String convertDate(String sdate) {
/*

        String[] data = sdate.split(" ");
        String[] dat = data[0].split("-");
        String apple = dat[1] + "/" + dat[2] + "/" + dat[0];
        String newDate = apple + " " + data[1].substring(0, 8) + " " + data[1].substring(8);

*/

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        Date nDate = new Date();
        try {

            nDate = sdf.parse(sdate);
        } catch (ParseException e) {

        }

        Long newDateTimeLong = nDate.getTime();


        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                newDateTimeLong,
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE);

        return timeAgo.toString();


    }



}
