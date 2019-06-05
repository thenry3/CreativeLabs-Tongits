package com.example.tongits;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class instructions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        ViewPager viewPager = findViewById(R.id.viewpager);
        PagerAdapter myPagerAdapter = new FragmentPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager, true);

    }

    public void onInstructionsReturn (View view){
        finish();
    }

    private class FragmentPageAdapter extends FragmentStatePagerAdapter {

        int numTabs = 5;

        public FragmentPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    fragScreen1 tab1 = new fragScreen1();
                    return tab1;
                case 1:
                    fragScreen2 tab2 = new fragScreen2();
                    return tab2;
                case 2:
                    fragScreen3 tab3 = new fragScreen3();
                    return tab3;
                case 3:
                    fragScreen4 tab4 = new fragScreen4();
                    return tab4;
                case 4:
                    fragScreen5 tab5 = new fragScreen5();
                    return tab5;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return numTabs;
        }
    }

}
