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
import com.lovecareworks.healthcarepersonnel.model.EducationItem;
import com.lovecareworks.healthcarepersonnel.model.EducationalListAdapter;
import com.lovecareworks.healthcarepersonnel.util.Date;

import java.util.ArrayList;
import java.util.List;


public class EducationalDialog extends DialogFragment {
    EditText universityName,award;
    TextView startDate,endDate,notice;
    Button add,ok,cancel;
    EducationalListAdapter cts;
  List<EducationItem> results;
    int item_position;
    ListView listvi;
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.alerttheme2);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.educationgetter, container);
        listvi = (ListView)view.findViewById(R.id.listView);
        results = new ArrayList<EducationItem>();

        getDialog().setTitle("Educational History");
        setCancelable(false);


        results = ((RegistrationActivity)getActivity()).getEducationList();

        listvi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle args = new Bundle();
                args.putInt("position", position);
                args.putBoolean("showEdit",false);
                args.putString("uniName", results.get(position).getUniversityName());
                args.putString("startDate", results.get(position).getStartDate());
                args.putString("endDate", results.get(position).getEndDate());
                args.putString("award", results.get(position).getAwardReceived());
                FragmentManager manager = getFragmentManager();
                EducationalAdder alertDialogFragment = new EducationalAdder();
                alertDialogFragment.setArguments(args);
                alertDialogFragment.show(manager, "educationadder");

            }
        });

        final AlertDialog.Builder builder_long = new AlertDialog.Builder(getActivity(),R.style.alerttheme);
        final CharSequence renewal[] = getResources().getTextArray(R.array.reglongclick);
        builder_long.setItems(renewal, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               if(which == 0){
                   Bundle args = new Bundle();
                   args.putInt("position", item_position);
                   args.putBoolean("showEdit",true);
                   args.putString("uniName", results.get(item_position).getUniversityName());
                   args.putString("startDate", results.get(item_position).getStartDate());
                   args.putString("endDate", results.get(item_position).getEndDate());
                   args.putString("award", results.get(item_position).getAwardReceived());
                   FragmentManager manager = getFragmentManager();
                   EducationalAdder alertDialogFragment = new EducationalAdder();
                   alertDialogFragment.setArguments(args);
                   alertDialogFragment.show(manager, "educationadder");

               }else if(which == 1){

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

        setCancelable(false);
        notice = (TextView)view.findViewById(R.id.emptyNote);
       // Boolean value =  ((RegistrationPage5) getActivity()).Educationentered();

        messagecheck();
        refreshlist();
        ok = (Button) view.findViewById(R.id.btn_sch_ok);
        cancel = (Button) view.findViewById(R.id.btn_sch_cancel);
        add = (Button) view.findViewById(R.id.button3);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              ((RegistrationActivity) getActivity()).AddEdu(results);
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
                EducationalAdder alertDialogFragment = new EducationalAdder();
                alertDialogFragment.show(manager, "educationadder");

            }
        });



/*
            universityName = (EditText) view.findViewById(R.id.editText9);
            award = (EditText) view.findViewById(R.id.editText11);

            universityName.requestFocus();


            */
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



    public void attachInfo(String collegeName,Date startD,Date endD,String award){
        EducationItem newsData = new EducationItem();
        newsData.setUniversityName(collegeName);
        newsData.setAwardReceived(award);
        newsData.setStartDate(startD.getDay() + "/" + (Integer.parseInt(startD.getMonth())+1) + "/" + startD.getYear());
        newsData.setEndDate(endD.getDay() + "/" + (Integer.parseInt(endD.getMonth())+1) + "/" + endD.getYear());

        results.add(newsData);




        cts = new EducationalListAdapter(getActivity(), results);
        listvi.setAdapter(cts);

        messagecheck();
    }

    public void refreshlist(){

        cts = new EducationalListAdapter(getActivity(), results);
        listvi.setAdapter(cts);

        messagecheck();
    }
    public void deleteInfo(int pos){

        results.remove(pos);
        cts = new EducationalListAdapter(getActivity(), results);
        listvi.setAdapter(cts);

        messagecheck();
    }

    public void saveInfo(String collegeName,Date startD,Date endD,String award,int pos){
        EducationItem newsData = new EducationItem();
        newsData.setUniversityName(collegeName);
        newsData.setAwardReceived(award);
        newsData.setStartDate(startD.getDay() + "/" + startD.getMonth()+1 + "/" + startD.getYear());
        newsData.setEndDate(endD.getDay() + "/" + endD.getMonth()+1 + "/" + endD.getYear());
        results.set(pos, newsData);





        cts = new EducationalListAdapter(getActivity(), results);
        listvi.setAdapter(cts);

        messagecheck();
    }





    public void messagecheck(){
        if(results.size() != 0){
            notice.setVisibility(view.GONE);
        }else{
            notice.setVisibility(View.VISIBLE);

        }

    }



}
