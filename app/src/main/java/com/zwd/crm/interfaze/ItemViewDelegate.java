package com.zwd.crm.interfaze;


import com.zwd.crm.HomePage.client.Controller.ClientItemDelegate;
import com.zwd.crm.widget.ViewHolder;

/**
 * Created by zhy on 16/6/22.
 */
public interface ItemViewDelegate<T>
{

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder holder, T t, int position);


}
