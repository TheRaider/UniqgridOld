package com.energy.uniqgrid;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Reports extends Fragment {

    RecyclerView rvReports;
    ArrayList<String> reportTitles = new ArrayList();
    ArrayList<String> reportDates = new ArrayList();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View customView = inflater.inflate(R.layout.fragment_reports, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Reports");

        setHasOptionsMenu(true);

        rvReports = (RecyclerView) customView.findViewById(R.id.rvReports);

        ReportsAdapter reportsAdapter = new ReportsAdapter();
        rvReports.setAdapter(reportsAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvReports.setLayoutManager(linearLayoutManager);


        return  customView;
    }

    public void loadData(){
       for (int i=0;i<10;i++){
           reportTitles.add("Energy Assesment Report");
       }
    }




    public class ReportsAdapter extends  RecyclerView.Adapter<ReportsAdapter.ReportHolder>{

        public ReportsAdapter() {
        }

        public class ReportHolder extends RecyclerView.ViewHolder{
            TextView tvReportTitle,tvReportDate;
            ImageView ivReportDownload;
            public ReportHolder(View itemView) {
                super(itemView);
                tvReportTitle = (TextView) itemView.findViewById(R.id.tvReportTitle);
                tvReportDate = (TextView) itemView.findViewById(R.id.tvReportDate);
                ivReportDownload = (ImageView) itemView.findViewById(R.id.ivReportDownload);

            }
        }
        @Override
        public ReportsAdapter.ReportHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_reports,parent,false);
            return new ReportHolder(view);
        }

        @Override
        public void onBindViewHolder(ReportsAdapter.ReportHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }
}
