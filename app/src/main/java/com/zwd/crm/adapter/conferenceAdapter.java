package com.zwd.crm.adapter;

import android.content.Context;

import com.zwd.crm.HomePage.conference.Controller.conferenceItemDelegate;
import com.zwd.crm.HomePage.conference.Module.conferenceGet;

import java.util.List;

/**
 * Created by asus-pc on 2017/3/7.
 */

public class conferenceAdapter extends MultiItemTypeAdapter<conferenceGet>{
    public conferenceAdapter(Context context, List<conferenceGet> datas) {
        super(context, datas);
        addItemViewDelegate(new conferenceItemDelegate());
    }
}
