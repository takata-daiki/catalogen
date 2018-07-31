package com.htc.toolkit.gallery.layout;


import com.htc.toolkit.gallery.layout.fixed.Overview;
import com.htc.toolkit.gallery.layout.fixed.Class;
import com.htc.toolkit.gallery.layout.fixed.Sample;
import com.htc.toolkit.gallery.layout.fixed.Sample_xml;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class Fixed extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // main xml  ?? ??, tab? 
        //setContentView(R.layout.main);
        final TabHost tabHost = getTabHost();

        tabHost.addTab(tabHost.newTabSpec("tab1")
                .setIndicator("Overview")
                .setContent(new Intent(this, Overview.class)));

        tabHost.addTab(tabHost.newTabSpec("tab2")
                .setIndicator("Class")
                .setContent(new Intent(this, Class.class)));
        
        // This tab sets the intent flag so that it is recreated each time
        // the tab is clicked.
        tabHost.addTab(tabHost.newTabSpec("tab3")
                .setIndicator("Sample")
                .setContent(new Intent(this, Sample.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
        tabHost.addTab(tabHost.newTabSpec("tab4")
                .setIndicator("Sample")
                .setContent(new Intent(this, Sample_xml.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
    }
}
