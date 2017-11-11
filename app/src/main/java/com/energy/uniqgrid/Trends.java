package com.energy.uniqgrid;


import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import info.hoang8f.android.segmented.SegmentedGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Trends extends Fragment {


    ViewPager vpPowerMeter,vpEnergyMeter;
    SegmentedGroup sgPowerMeter,sgEnergyMeter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View customView = inflater.inflate(R.layout.fragment_trends, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Trends");

        setHasOptionsMenu(true);


        vpPowerMeter = (ViewPager) customView.findViewById(R.id.vpPowerMeter);
        vpEnergyMeter = (ViewPager) customView.findViewById(R.id.vpEnergyMeter);
        sgPowerMeter = (SegmentedGroup) customView.findViewById(R.id.sgPowerMeter);
        sgEnergyMeter = (SegmentedGroup) customView.findViewById(R.id.sgEnergyMeter);

        setUpViewPagers();

        sgPowerMeter.check(R.id.rbPowerRealtime);
        sgEnergyMeter.check(R.id.rbEnergyToday);


        sgPowerMeter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rbPowerRealtime:
                        vpPowerMeter.setCurrentItem(0);
                        break;
                    case R.id.rbPowerToday:
                        vpPowerMeter.setCurrentItem(1);
                        break;
                    default:
                        break;
                }
            }
        });

        vpPowerMeter.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    sgPowerMeter.check(R.id.rbPowerRealtime);
                }else if(position==1){
                    sgPowerMeter.check(R.id.rbPowerToday);
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        sgEnergyMeter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rbEnergyToday:
                        vpPowerMeter.setCurrentItem(0);
                        break;
                    case R.id.rbEnergyDaily:
                        vpEnergyMeter.setCurrentItem(1);
                        break;
                    case R.id.rbEnergyMonthly:
                        vpEnergyMeter.setCurrentItem(2);
                        break;
                    default:
                        break;
                }
            }
        });
        vpEnergyMeter.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    sgEnergyMeter.check(R.id.rbEnergyToday);
                }else if(position==1){
                    sgEnergyMeter.check(R.id.rbEnergyDaily);
                }else if(position==2){
                    sgEnergyMeter.check(R.id.rbEnergyMonthly);
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




        return  customView;
    }



    public  void setUpViewPagers(){
        StatsPagerAdapter statsPagerAdapter1 = new StatsPagerAdapter(getChildFragmentManager());
        statsPagerAdapter1.addFragment(new RealTimePowerFragment(),"RealTime Power");
        statsPagerAdapter1.addFragment(new TodayPowerFragment(),"Today Power");
        vpPowerMeter.setAdapter(statsPagerAdapter1);

        if(sgPowerMeter.getCheckedRadioButtonId() == R.id.rbPowerRealtime ){
            vpPowerMeter.setCurrentItem(0);
        }else{
            vpPowerMeter.setCurrentItem(1);
        }


        StatsPagerAdapter statsPagerAdapter2 = new StatsPagerAdapter(getChildFragmentManager());
        statsPagerAdapter2.addFragment(new EnergyTodayFragment(),"Energy Today");
        statsPagerAdapter2.addFragment(new EnergyDailyFragment(),"Energy Daily");
        statsPagerAdapter2.addFragment(new EnergyMonthlyFragment(),"Energy Monthly");
        vpEnergyMeter.setAdapter(statsPagerAdapter2);

        if(sgEnergyMeter.getCheckedRadioButtonId() == R.id.rbEnergyToday ){
            vpEnergyMeter.setCurrentItem(0);
        }else if(sgEnergyMeter.getCheckedRadioButtonId() == R.id.rbEnergyDaily ){
            vpEnergyMeter.setCurrentItem(1);
        }else{
            vpEnergyMeter.setCurrentItem(2);
        }


    }


    class StatsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public StatsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }



}
