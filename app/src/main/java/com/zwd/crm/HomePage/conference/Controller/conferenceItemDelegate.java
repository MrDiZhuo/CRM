package com.zwd.crm.HomePage.conference.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zwd.crm.HomePage.conference.Module.conferenceGet;
import com.zwd.crm.R;
import com.zwd.crm.interfaze.ItemViewDelegate;
import com.zwd.crm.widget.ViewHolder;

public class conferenceItemDelegate implements ItemViewDelegate<conferenceGet> {

    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.item_conference_item_delegate;
    }

    @Override
    public boolean isForViewType(conferenceGet item, int position)
    {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, conferenceGet party, int position)
    {
        String state = "";
            if(party.getExhibitionstatus()==0){
                state="初试";
            }else if(party.getExhibitionstatus()==1){
                state="结束";
            }else if(party.getExhibitionstatus()==2){
                state="进行中";
            }else if(party.getExhibitionstatus()==3){
                state="其它";
            }


        holder.setText(R.id.item_conference_title,party.getExhibitionname());
        holder.setText(R.id.item_conference_state,state);
    }

}
