package com.gmail.tamyewwah.mywallet;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

public class NewsActivity extends AppCompatActivity {

    private ViewPager nSlideViewPager;
    private LinearLayout nDotsLayout;

    private TextView[] nDots;

    private SliderAdapter sliderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        nSlideViewPager = (ViewPager)findViewById(R.id.slide_image);
        nDotsLayout = (LinearLayout)findViewById(R.id.dotsLayout);

        sliderAdapter = new SliderAdapter(this);

        nSlideViewPager.setAdapter(sliderAdapter);
    }

    public void addDotsIndicator()
    {
        nDots = new TextView[3];

        for(int i=0;i<nDots.length;i++)
        {

            nDots[i] = new TextView(this);
            nDots[i].setText(Html.fromHtml("&#8226:"));
            nDots[i].setTextSize(35);
//            nDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));

            nDotsLayout.addView(nDots[i]);
        }
    }

}
