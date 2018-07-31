package com.android.server.am;

import android.content.pm.ApplicationInfo;
import com.android.internal.os.BatteryStatsImpl.Uid.Pkg.Serv;

class BackupRecord
{
    public static final int BACKUP_FULL = 1;
    public static final int BACKUP_NORMAL = 0;
    public static final int RESTORE = 2;
    public static final int RESTORE_FULL = 3;
    ProcessRecord app;
    final ApplicationInfo appInfo;
    final int backupMode;
    final BatteryStatsImpl.Uid.Pkg.Serv stats;
    String stringName;

    BackupRecord(BatteryStatsImpl.Uid.Pkg.Serv paramServ, ApplicationInfo paramApplicationInfo, int paramInt)
    {
        this.stats = paramServ;
        this.appInfo = paramApplicationInfo;
        this.backupMode = paramInt;
    }

    public String toString()
    {
        String str;
        if (this.stringName != null)
            str = this.stringName;
        while (true)
        {
            return str;
            StringBuilder localStringBuilder = new StringBuilder(128);
            localStringBuilder.append("BackupRecord{").append(Integer.toHexString(System.identityHashCode(this))).append(' ').append(this.appInfo.packageName).append(' ').append(this.appInfo.name).append(' ').append(this.appInfo.backupAgentName).append('}');
            str = localStringBuilder.toString();
            this.stringName = str;
        }
    }
}

/* Location:                     /home/lithium/miui/chameleon/2.11.16/services_dex2jar.jar
 * Qualified Name:         com.android.server.am.BackupRecord
 * JD-Core Version:        0.6.2
 */