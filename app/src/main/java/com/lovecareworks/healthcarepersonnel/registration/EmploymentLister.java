
package com.lovecareworks.healthcarepersonnel.registration;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.lovecareworks.healthcarepersonnel.R;
import com.lovecareworks.healthcarepersonnel.RegistrationActivity;
import com.lovecareworks.healthcarepersonnel.model.EducationalListAdapter;
import com.lovecareworks.healthcarepersonnel.model.EmploymentClass;
import com.lovecareworks.healthcarepersonnel.model.EmploymentListAdapter;
import com.lovecareworks.healthcarepersonnel.util.Date;

import java.util.ArrayList;
import java.util.List;

/**
     * Created by ghost on 13/1/16.
     */
    public class EmploymentLister extends DialogFragment {
        EditText universityName,award;
        TextView startDate,endDate,notice;
        Button add,ok,cancel;
    EmploymentListAdapter cts;
    List<EmploymentClass> results;
    ListView listvi;
    int item_position;
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.alerttheme2);


    }

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
             view = inflater.inflate(R.layout.employmentlist, container);
            listvi = (ListView)view.findViewById(R.id.listView);
            results = new ArrayList<EmploymentClass>();

            getDialog().requestWindowFeature(STYLE_NO_TITLE);
            setCancelable(false);
notice = (TextView)view.findViewById(R.id.emptyNoteb);
        //   Boolean value =  ((RegistrationPage5) getActivity()).Educationentered();

     messagecheck();

            ok = (Button) view.findViewById(R.id.button3b);
            cancel = (Button) view.findViewById(R.id.button2b);
            add = (Button) view.findViewById(R.id.buttonb);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((RegistrationActivity) getActivity()).AddEmp(results);
                    dismiss();


                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dismiss();

                }
            });

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager manager = getFragmentManager();
                    EmploymentAdder alertDialogFragment = new EmploymentAdder();
                    alertDialogFragment.show(manager, "employmentadder");



                }
            });


        final AlertDialog.Builder builder_long = new AlertDialog.Builder(getActivity(),R.style.alerttheme);
        final CharSequence renewal[] = getResources().getTextArray(R.array.reglongclick);
        builder_long.setItems(renewal, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Bundle args = new Bundle();
                    args.putInt("position", item_position);
                    args.putBoolean("showEdit", true);
                    args.putString("empName", results.get(item_position).getEmployer());
                    args.putString("startDate", results.get(item_position).getStartD());
                    args.putString("endDate", results.get(item_position).getToD());
                    args.putString("empPos", results.get(item_position).getPosition());
                    FragmentManager manager = getFragmentManager();
                    EmploymentAdder alertDialogFragment = new EmploymentAdder();
                    alertDialogFragment.setArguments(args);
                    alertDialogFragment.show(manager, "employmentadder");

                } else if (which == 1) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setTitle("Confirm action");
                    builder.setMessage("Are you sure you want to delete this record ?");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog


                            deleteInfo(item_position);

                            dialog.dismiss();

                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // Do nothing
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }

            }


        });

        listvi.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item_position = position;
                builder_long.show();
                return true;
            }
        });

        listvi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle args = new Bundle();
                args.putInt("position", position);
                args.putBoolean("showEdit", false);

                args.putString("empName", results.get(position).getEmployer());
                args.putString("startDate", results.get(position).getStartD());
                args.putString("endDate", results.get(position).getToD());
                args.putString("empPos", results.get(position).getPosition());
                FragmentManager manager = getFragmentManager();
                EmploymentAdder alertDialogFragment = new EmploymentAdder();
                alertDialogFragment.setArguments(args);
                alertDialogFragment.show(manager, "employmentadder");

            }
        });

/*
            universityName = (EditText) view.findViewById(R.id.editText9);
            award = (EditText) view.findViewById(R.id.editText11);

            universityName.requestFocus();


            */

        results = ((RegistrationActivity)getActivity()).getEmploymentList();
        refreshlist();
            return view;
        }

        @Override
        public void onStart() {
            super.onStart();
            Dialog d = getDialog();
            if (d!=null){
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                d.getWindow().setLayout(width, height);
            }
        }



        public void attachInfo(EmploymentClass newsData){


                results.add(newsData);
             cts = new EmploymentListAdapter(getActivity(), results);
            listvi.setAdapter(cts);

            messagecheck();
        }


    public void messagecheck(){
        if(results.size() != 0){
                notice.setVisibility(view.GONE);

        }else{
            notice.setVisibility(view.VISIBLE);
        }

    }

    public void deleteInfo(int pos){

        results.remove(pos);
        cts = new EmploymentListAdapter(getActivity(), results);
        listvi.setAdapter(cts);

        messagecheck();
    }


    public void saveInfo(String empName,Date startD,Date endD,String position,int pos){
        EmploymentClass newsData = new EmploymentClass();
        newsData.setEmployer(empName);
        newsData.setPosition(position);
        newsData.setStartD(startD.getDay() + "/" + startD.getMonth() + "/" + startD.getYear());
        if(endD != null){
            newsData.setToD(endD.getDay() + "/" + endD.getMonth() + "/" + endD.getYear());
        }else{
            newsData.setToD("Present");
        }

        results.set(pos, newsData);





        cts = new EmploymentListAdapter(getActivity(), results);
        listvi.setAdapter(cts);

        messagecheck();
    }

    public void refreshlist(){

        cts = new EmploymentListAdapter(getActivity(), results);
        listvi.setAdapter(cts);
        messagecheck();
    }
    }


