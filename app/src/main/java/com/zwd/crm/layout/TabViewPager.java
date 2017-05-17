package com.zwd.crm.layout;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zwd.crm.HomePage.affair.View.affairFragment;
import com.zwd.crm.HomePage.conference.Module.conferenceGet;
import com.zwd.crm.HomePage.conference.View.conferenceFragment;
import com.zwd.crm.HomePage.login.Module.loginGet;
import com.zwd.crm.HomePage.me.View.meFragment;
import com.zwd.crm.base.BaseAppManager;
import com.zwd.crm.internet.CustomCallBack;
import com.zwd.crm.internet.RemoteDataResult;
import com.zwd.crm.internet.RemoteOptionIml;
import com.zwd.crm.layout.Controller.MainViewpager;
import com.zwd.crm.HomePage.team.View.teamFragment;
import com.zwd.crm.R;
import com.zwd.crm.adapter.MyViewPagerAdapter;
import com.zwd.crm.base.BaseActivity;
import com.zwd.crm.base.BaseFragment;
import com.zwd.crm.layout.Controller.PopupMenu;
import com.zwd.crm.util.ToastUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Response;

public class TabViewPager extends BaseActivity implements ViewPager.OnPageChangeListener {
    @Bind(R.id.tab_viewpager)
    MainViewpager mainViewpager;
    @Bind(R.id.tab_bottom_click)
    RelativeLayout click;
    @Bind(R.id.iv_img)
    ImageView img;

    @Bind(R.id.tab_bottom_1)
    RelativeLayout tab_1;
    @Bind(R.id.tab_bottom_2)
    RelativeLayout tab_2;
    @Bind(R.id.tab_bottom_3)
    RelativeLayout tab_3;
    @Bind(R.id.tab_bottom_4)
    RelativeLayout tab_4;

    @Bind(R.id.img_1)
    ImageView imageView_1;
    @Bind(R.id.img_2)
    ImageView imageView_2;
    @Bind(R.id.img_3)
    ImageView imageView_3;
    @Bind(R.id.img_4)
    ImageView imageView_4;

    @Bind(R.id.text_1)
    TextView textView_1;
    @Bind(R.id.text_2)
    TextView textView_2;
    @Bind(R.id.text_3)
    TextView textView_3;
    @Bind(R.id.text_4)
    TextView textView_4;

    public loginGet login_Get;
    public String token="";

    private static final int TAB_1 = 0;
    private static final int TAB_2 = 1;
    private static final int TAB_3 = 2;
    private static final int TAB_4 = 3;
    private long exitTime = 0;
    private ArrayList<BaseFragment> fgms;
    public MyViewPagerAdapter adapter;

    protected int getContentView(){
        return R.layout.activity_tab_view_pager;
    }

    protected void initViews() {
        Bundle bundle = getIntent().getExtras();
        login_Get =(loginGet) bundle.getSerializable("login_get");
        token = bundle.getString("token");

        fgms = new ArrayList<>();

        BaseFragment mTab_01 = new conferenceFragment();
        BaseFragment mTab_02 = new affairFragment();
        BaseFragment mTab_03 = new teamFragment();
        BaseFragment mTab_04 = new meFragment();
        fgms.add(mTab_01);
        fgms.add(mTab_02);
        fgms.add(mTab_03);
        fgms.add(mTab_04);
        adapter = new MyViewPagerAdapter(getSupportFragmentManager(),fgms);
        mainViewpager.setAdapter(adapter);
        mainViewpager.setOnPageChangeListener(this);
        mainViewpager.setOffscreenPageLimit(4);
        selectPage(TAB_1);

    }

    /**
     *选中
     */

    private void selectPage(int page) {
        replaceFragments();
        switch (page) {
            case TAB_1:
                imageView_1.setImageResource(R.mipmap.conference_after);
                textView_1.setTextColor(this.getResources().getColor(R.color.dark));
                mainViewpager.setCurrentItem(page);
                break;
            case TAB_2:
                imageView_2.setImageResource(R.mipmap.affair_after);
                textView_2.setTextColor(this.getResources().getColor(R.color.dark));
                mainViewpager.setCurrentItem(page);
                break;
            case TAB_3:
                imageView_3.setImageResource(R.mipmap.team_after);
                textView_3.setTextColor(this.getResources().getColor(R.color.dark));
                mainViewpager.setCurrentItem(page);
                break;
            case TAB_4:
                imageView_4.setImageResource(R.mipmap.me_after);
                textView_4.setTextColor(this.getResources().getColor(R.color.dark));
                mainViewpager.setCurrentItem(page);
                break;
        }
    }
    /**
     * 充值图片样式 选中前
     */
    private void replaceFragments() {
        imageView_1.setImageResource(R.mipmap.conference_before);
        imageView_2.setImageResource(R.mipmap.affair_before);
        imageView_3.setImageResource(R.mipmap.team_before);
        imageView_4.setImageResource(R.mipmap.me_tabbefore);
        textView_1.setTextColor(this.getResources().getColor(R.color.tab_before));
        textView_2.setTextColor(this.getResources().getColor(R.color.tab_before));
        textView_3.setTextColor(this.getResources().getColor(R.color.tab_before));
        textView_4.setTextColor(this.getResources().getColor(R.color.tab_before));
    }
    @OnClick({R.id.tab_bottom_1,R.id.tab_bottom_2,R.id.tab_bottom_3,R.id.tab_bottom_4,R.id.tab_bottom_click})
    public void onPageClick(View v){
        switch (v.getId()){
            case R.id.tab_bottom_1:
                selectPage(TAB_1);
                break;
            case R.id.tab_bottom_2:
                selectPage(TAB_2);
                break;
            case R.id.tab_bottom_3:
                selectPage(TAB_3);
                break;
            case R.id.tab_bottom_4:
                selectPage(TAB_4);
                break;
            case R.id.tab_bottom_click:
                PopupMenu.getInstance()._show(this, img);
                break;
        }

    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    @Override
    public void onPageSelected(int position) {
        selectPage(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onBackPressed() {
        // 当popupWindow 正在展示的时候 按下返回键 关闭popupWindow 否则关闭activity
        if (PopupMenu.getInstance()._isShowing()) {
            PopupMenu.getInstance()._rlClickAction();
        } else {
            super.onBackPressed();
        }
    }


    /**
     * 按两次BACK键退出程序
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (PopupMenu.getInstance()._isShowing()) {
                PopupMenu.getInstance()._rlClickAction();
            } else {
                if (System.currentTimeMillis() - exitTime > 2000) {
                    ToastUtils.showShort(this, getString(R.string.text_one_more_click));
                    exitTime = System.currentTimeMillis();
                } else {
                    BaseAppManager.getInstance().clearAll();
                    System.exit(0);
                }
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
