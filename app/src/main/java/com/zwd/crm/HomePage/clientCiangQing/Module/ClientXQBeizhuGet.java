package com.zwd.crm.HomePage.clientCiangQing.Module;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by asus-pc on 2017/5/6.
 */

public class ClientXQBeizhuGet {
    @SerializedName("BackupList")
    List<ClientXQBeizhuElement> BackupList;

    public List<ClientXQBeizhuElement> getBackupList() {
        return BackupList;
    }

    public void setBackupList(List<ClientXQBeizhuElement> backupList) {
        BackupList = backupList;
    }
}
