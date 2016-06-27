/*
 *  Copyright © 2015-2016. by ceph3us
 *  All Rights Reserved.
 *  www.ceph3us.pl
 */

package pl.ceph3us.longclickonmenuitem;

import android.app.Activity;
import android.graphics.Color;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by ceph3us on 27.06.16.
 */
public class LongClick extends Activity {

    @IdRes  private static final int TOOLBAR_ID = 12312;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** create main view */
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.setBackgroundColor(Color.BLACK);

        /** create toolbar */
        final Toolbar toolbar = new Toolbar(this);
        toolbar.setId(TOOLBAR_ID);
        toolbar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
        toolbar.setTitle("Toolbar");
        toolbar.setTitleTextColor(Color.RED);
        toolbar.setBackgroundColor(Color.BLUE);

        /** add toolbar to main view */
        linearLayout.addView(toolbar);

        /** get toolbar menu && inflate */
        toolbar.inflateMenu(R.menu.main_men);
        Menu menu = toolbar.getMenu();

        /** find item and get action view */
        final MenuItem item = menu.findItem(R.id.item);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        final int itemId = item.getItemId();
        View actionView = item.getActionView();

        /** define view on long click listener */
        final View.OnLongClickListener toolbarItemLongClicked = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                toolbar.setTitle("Toolbar LongClicked");
                return true;
            }
        };

        /**
         *  check upon action view  - should be null as view is not laid out
         *  if so add demand to observe layout changes to set  listener when action view of an item will laid out
         * */
        if(actionView==null) toolbar.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                if(view.getId() == toolbar.getId()) {
                    View itemView = view.findViewById(itemId);
                    if(itemView!=null) {
                        itemView.setOnLongClickListener(toolbarItemLongClicked);
                        view.removeOnLayoutChangeListener(this);
                    }
                }
            }
        }); else actionView.setOnLongClickListener(toolbarItemLongClicked);


        /**  we delay execution ov view rendering as we want to show layout tree observer usage */
        setContentView(linearLayout);

    }

}
