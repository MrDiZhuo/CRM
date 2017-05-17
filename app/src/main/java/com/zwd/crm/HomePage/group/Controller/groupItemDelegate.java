package com.zwd.crm.HomePage.group.Controller;

import com.zwd.crm.HomePage.inform.Module.subjectdepartmentElement;
import com.zwd.crm.R;
import com.zwd.crm.base.BaseAppManager;
import com.zwd.crm.interfaze.ItemViewDelegate;
import com.zwd.crm.internet.CustomCallBack;
import com.zwd.crm.internet.RemoteDataResult;
import com.zwd.crm.internet.RemoteOptionIml;
import com.zwd.crm.layout.TabViewPager;
import com.zwd.crm.util.ToastUtils;
import com.zwd.crm.widget.ViewHolder;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;

/**
 * Created by asus-pc on 2017/3/7.
 */

public class groupItemDelegate implements ItemViewDelegate<subjectdepartmentElement> {

    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.layout_team_group;
    }

    @Override
    public boolean isForViewType(subjectdepartmentElement item, int position)
    {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, final subjectdepartmentElement party, final int position)
    {
       holder.setText(R.id.layout_team_group_text,party.getDepartmentname());
    }
}
