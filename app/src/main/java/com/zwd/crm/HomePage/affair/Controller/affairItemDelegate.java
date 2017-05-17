package com.zwd.crm.HomePage.affair.Controller;

import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zwd.crm.HomePage.affair.Module.affairGet;
import com.zwd.crm.HomePage.affair.Module.affairNoticeSignPost;
import com.zwd.crm.HomePage.affair.View.affairFragment;
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

public class affairItemDelegate implements ItemViewDelegate<affairGet> {
    int flag=0;
    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.item_affair_item_delegate;
    }

    @Override
    public boolean isForViewType(affairGet item, int position)
    {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, final affairGet party, final int position)
    {
        String status="";
        if(party.getAnswerstatus()==0){
            status="标记完成";
        }else if(party.getAnswerstatus()==1){
            status="已完成";
        }

        int sure = party.getConfirmcount();
        int all = party.getReferencecount();
        if(flag==1){
            sure=sure+1;
            all=all+1;
        }
        if (status=="已完成"){
            holder.setTextColorRes(R.id.item_affair_confirm,R.color.title);
            holder.setBackgroundRes(R.id.item_affair_confirm, R.drawable.conference_unselect);
        } else {
            holder.setTextColorRes(R.id.item_affair_confirm,R.color.nomoltext);
            holder.setBackgroundRes(R.id.item_affair_confirm, R.drawable.conference_select);
            holder.setOnClickListener(R.id.item_affair_confirm, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btn_noticeSign(v,position,party);

                }


            });


        }
        CircleImageView imageView = (CircleImageView) holder.getView(R.id.item_affair_img);
        if(party.getHeading()!=null){
            Glide.with(BaseAppManager.getInstance().getForwardActivity())
                    .load("http://opoecp2mn.bkt.clouddn.com/"+party.getHeading())
                    .into(imageView);
        }
        holder.setText(R.id.item_affair_name,party.getName());
        if (party.getDate()!=null){
            holder.setText(R.id.item_affair_time,party.getDate().replace("T"," "));
        }
        holder.setText(R.id.item_affair_text,party.getContent());
        if(party.getNoticeenddate()!=null){
            holder.setText(R.id.item_affair_end,party.getNoticeenddate().replace("T"," "));
        }

        holder.setText(R.id.item_affair_num,String.valueOf(party.getAnswercount())+"条回复");
        holder.setText(R.id.item_affair_inform,party.getNoticetype());
        holder.setText(R.id.item_affair_edt,String.valueOf(sure)+"人确认"+"/"+"共"+String.valueOf(all)+"人");
        holder.setText(R.id.item_affair_confirm,status);

    }
    /**
     * 标记完成的接口
     **/
    public void btn_noticeSign(final View v,int position,final affairGet party){
        String baseUrl="http://139.224.164.183:8088/";
        String url="Notice_ReturnNoticeSign.aspx";
        final TabViewPager tabViewPager =(TabViewPager) BaseAppManager.getInstance().getForwardActivity();
        affairFragment fragment = (affairFragment) tabViewPager.adapter.getItem(1);
        affairNoticeSignPost post = new affairNoticeSignPost();
        post.setId(fragment.affairGetList.get(position).getId());
        post.setNoticeusersstatus(1);
        post.setUserid(fragment.userid);
        RemoteOptionIml remoteOptionIml = new RemoteOptionIml();
        remoteOptionIml.Notice_ReturnNoticeSign(post, baseUrl, url, new CustomCallBack<RemoteDataResult>() {
            @Override
            public void onSuccess(Response<RemoteDataResult> response) {
                ToastUtils.showShort(tabViewPager,response.body().getResultMessage());
                flag =response.body().getResultCode();
                TextView txt = (TextView) v.findViewById(R.id.item_affair_confirm);
                txt.setText("已完成");
                txt.setTextColor(v.getResources().getColor(R.color.title));
                txt.setBackground(v.getResources().getDrawable(R.drawable.conference_unselect));
                txt.setClickable(false);

            }

            @Override
            public void onFail(String message) {
                ToastUtils.showShort(tabViewPager,message);

            }
        });
    }
}
