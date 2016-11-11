package com.tnt_development.nativeexpressadssampleapp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;
import com.tnt_development.nativeexpressadssampleapp.Adapters.ObjectAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    private Queue adQueue = new LinkedList();
    private LinkedList queue = new LinkedList();
    private ArrayList<Object> items = new ArrayList<>();
    ObjectAdapter adapter;
    MainActivity context;
    private mainThread thread = null;
    int count = 0;
    private int adPlaceAfter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData(1);

        context = (MainActivity) this;
        listView = (ListView) findViewById(R.id.lvTest);

        adapter = new ObjectAdapter(context);

        listView.setAdapter(adapter);

        if(thread == null) {
            thread = new mainThread();
            thread.start();
        }

        loadAd();

    }

    private void loadData(int j) {
        object o = null;

        for(int i = j; i < 100; i++)
        {
            o = new object("name " + i, i);
            queue.add(o);
        }
    }

    private void loadAd()
    {
        NativeExpressAdView adView = new NativeExpressAdView(context);
        AdSize adSize = new AdSize(AdSize.FULL_WIDTH, 150);
        adView.setAdSize(adSize);
        adView.setAdUnitId(getString(R.string.ad_unit_id));
        AbsListView.LayoutParams layoutParams =  new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                adSize.getHeightInPixels(context));

        adView.setLayoutParams(layoutParams);

        AdRequest request = new AdRequest.Builder().addTestDevice("F121686E1D2D817CB9440F5C3492D3D7").build();
        adView.loadAd(request);
        adQueue.add(adView);
    }


    public class mainThread extends Thread {

        private boolean run = true;

        @Override
        public void run() {
            super.run();

            while(run) {


                while (queue.size() > 0 && run) {

                    count++;

                    if(queue.size() <= 3)
                    {
                        loadData(((object)queue.getLast()).getId() +1);
                    }

                    if(count > adPlaceAfter - 1) {

                        count = 0;

                        if (adQueue.size() > 0) {

                            items.add(0, adQueue.remove());
                        }

//                        adLoader.loadAd(new AdRequest.Builder().build());

                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loadAd();
                            }
                        });

                    }else {
                        try {
                            items.add(0, queue.remove());
                        }catch (Exception e)
                        {

                        }
                    }

                    if (items.size() > 50) {
                        items.remove(items.size() - 1);
                    }


                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.setList(items);
                        }
                    });

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void cancel()
        {
            run = false;
        }

        public boolean active()
        {
            return run;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(thread != null && thread.isAlive() && thread.active())
            thread.cancel();

    }

    @Override
    public void onResume() {
        super.onResume();
        if(listView != null && listView.getFirstVisiblePosition() == 0 && thread!= null && !thread.active())
        {
            thread = new mainThread();
            thread.start();
        }
    }
}
