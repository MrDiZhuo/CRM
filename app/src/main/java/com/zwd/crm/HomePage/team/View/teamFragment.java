package com.zwd.crm.HomePage.team.View;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zwd.crm.HomePage.group.View.groupFragment;
import com.zwd.crm.HomePage.inform.View.informFragment;
import com.zwd.crm.R;
import com.zwd.crm.base.BaseFragment;
import com.zwd.crm.layout.TabViewPager;

import butterknife.Bind;
import butterknife.OnClick;


public class teamFragment extends BaseFragment {
    @Bind(R.id.friend_message)
    Button message;
    @Bind(R.id.friend_group)
    Button group;
    private informFragment messageFrag;
    private groupFragment groupFrag;
    public static final int MESSAGE_FRAGMENT_TYPE = 1;
    public static final int CALL_FRAGMENT_TYPE = 2;
    public int currentFragmentType = -1;



    protected int getContentViewID() {
        return R.layout.fragment_team;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set tag
        TAG_LOG = this.getClass().getSimpleName();



        mContext = getActivity();
        FragmentManager fragmentManager = getChildFragmentManager();
        if (savedInstanceState != null) {
            int type = savedInstanceState.getInt("currentFragmentType");
            messageFrag = (informFragment) fragmentManager.findFragmentByTag("message");
            groupFrag = (groupFragment)fragmentManager.findFragmentByTag("group");
            if(type > 0)
                loadFragment(type);
        } else {
            FragmentTransaction transaction = fragmentManager
                    .beginTransaction();
            Fragment mainFragment = fragmentManager.findFragmentByTag("message");
            if (mainFragment != null) {
                transaction.replace(R.id.friend_content, mainFragment);
                transaction.commit();
            } else {
                loadFragment(MESSAGE_FRAGMENT_TYPE);
            }
        }
    }

    @Override
    protected void initViews(View rootView) {

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("lastFragmentTag", currentFragmentType);
    }
    private void switchFragment(int type) {
        switch (type) {
            case MESSAGE_FRAGMENT_TYPE:
                loadFragment(MESSAGE_FRAGMENT_TYPE);
                break;
            case CALL_FRAGMENT_TYPE:
                loadFragment(CALL_FRAGMENT_TYPE);
                break;
        }

    }
    private void loadFragment(int type) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (type == CALL_FRAGMENT_TYPE) {
            if (groupFrag == null) {
                groupFrag = new groupFragment();

                transaction.add(R.id.friend_content, groupFrag, "zhishi");
            } else {
                transaction.show(groupFrag);
            }
            if (messageFrag != null) {
                transaction.hide(messageFrag);
            }
            currentFragmentType = MESSAGE_FRAGMENT_TYPE;
        } else {
            if (messageFrag == null) {
                messageFrag = new informFragment();
                transaction.add(R.id.friend_content, messageFrag, "wenda");
            } else {
                transaction.show(messageFrag);
            }
            if (groupFrag != null) {
                transaction.hide(groupFrag);
            }
            currentFragmentType = CALL_FRAGMENT_TYPE;
        }
        transaction.commitAllowingStateLoss();
    }

    @OnClick({R.id.friend_message,R.id.friend_group})
    public void click(View v){
        switch (v.getId()){
            case R.id.friend_message:
                message.setTextColor(getResources().getColor(R.color.dark));
                group.setTextColor(getResources().getColor(R.color.title));
                message
                        .setBackgroundResource(R.drawable.frid_left_press);
                group
                        .setBackgroundResource(R.drawable.frid_right_unpress);
                switchFragment(MESSAGE_FRAGMENT_TYPE);
                break;
            case R.id.friend_group:
                message.setTextColor(getResources().getColor(R.color.title));
                group.setTextColor(getResources().getColor(R.color.dark));
                message
                        .setBackgroundResource(R.drawable.frid_left_unpress);
                group
                        .setBackgroundResource(R.drawable.frid_right_press);
                switchFragment(CALL_FRAGMENT_TYPE);
                break;
        }
    }

}
