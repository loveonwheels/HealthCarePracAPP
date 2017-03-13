package com.mSIHAT.hcp.dialogs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import com.mSIHAT.hcp.R;
import com.mSIHAT.hcp.model.TimeSlots;
import com.mSIHAT.hcp.model.myScheduleSlot;
import com.mSIHAT.hcp.registration.EducationalAdder;
import com.mSIHAT.hcp.util.DateDialog;
import com.mSIHAT.hcp.util.TimeSlotUtil;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScheduleAddFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScheduleAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleAddFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
   TimeSlots starttime;
    TimeSlots endtime;
    private static final int RequestCode1 = 14401;
    private static final int RequestCode2 = 14402;

    boolean change = false;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RelativeLayout smain;
    myScheduleSlot schedule;
Button btnSingleDate,btnMultiDate,cancel,ok,Delete,Edit;
    TextInputLayout Lay_date,Lay_startdate,Lay_enddate;
    EditText input_sch_date,input_start_time,input_end_time,input_sch_start_date,input_sch_end_date;
    private OnFragmentInteractionListener mListener;
    TableLayout seditbtn,ssavelay,soklay;
    Boolean singleSelection = true;
    Fragment thisfragement;
    Button close,save,canedit;
    public ScheduleAddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleAddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleAddFragment newInstance(String param1, String param2) {
        ScheduleAddFragment fragment = new ScheduleAddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment




        View view = inflater.inflate(R.layout.scheduleadder, container);
        getDialog().requestWindowFeature(STYLE_NO_TITLE);
        setCancelable(false);
 thisfragement = this;
        btnMultiDate = (Button)view.findViewById(R.id.btnMultiDate);
        btnSingleDate = (Button)view.findViewById(R.id.btnSingleDate);
        smain = (RelativeLayout)view.findViewById(R.id.smainlay);
        cancel = (Button)view.findViewById(R.id.btn_sch_cancel);
        ok = (Button)view.findViewById(R.id.btn_sch_ok);
  close = (Button)view.findViewById(R.id.btnCloses);
        Delete = (Button)view.findViewById(R.id.btnDeletes);
        Edit = (Button)view.findViewById(R.id.btnEdits);
        Lay_date = (TextInputLayout) view.findViewById(R.id.input_layout_sch_date);
        Lay_startdate = (TextInputLayout)view.findViewById(R.id.input_layout_sch_startdate);
        Lay_enddate = (TextInputLayout)view.findViewById(R.id.input_layout_sch_end_date);
        input_sch_date = (EditText)view.findViewById(R.id.input_sch_date) ;
        input_start_time = (EditText) view.findViewById(R.id.input_start_time);
        input_end_time = (EditText) view.findViewById(R.id.input_sch_end);
        seditbtn = (TableLayout)view.findViewById(R.id.seditbtns);
        soklay = (TableLayout)view.findViewById(R.id.saddlay);
        ssavelay = (TableLayout)view.findViewById(R.id.sedtsave);
        input_sch_start_date = (EditText) view.findViewById(R.id.input_sch_start_date);
        input_sch_end_date = (EditText) view.findViewById(R.id.input_sch_end_date);
canedit = (Button)view.findViewById(R.id.btnCans);
        save = (Button)view.findViewById(R.id.btnSaves);




        if (getArguments() != null) {
            schedule = getArguments().getParcelable("schedule");
            input_sch_date.setEnabled(false);
            input_start_time.setEnabled(false);
            input_end_time.setEnabled(false);
            input_sch_start_date.setEnabled(false);
            input_sch_end_date.setEnabled(false);
            soklay.setVisibility(View.GONE);
            seditbtn.setVisibility(View.VISIBLE);
            ssavelay.setVisibility(View.GONE);

            if(schedule.getScheduleType() == 0){
                input_sch_date.setText(schedule.getSlotDate());

                input_start_time.setText(TimeSlotUtil.getTimeStringValue(schedule.getStartSlot()));
                input_end_time.setText(TimeSlotUtil.getTimeStringValue(schedule.getEndSlot()));

                starttime = new TimeSlots(schedule.getStartSlot(),true);
                endtime = new TimeSlots(schedule.getEndSlot(),true);


            }else{
                multidatecall();
                input_sch_start_date.setText(schedule.getStartDate());
                input_sch_end_date.setText(schedule.getEndDate());
                input_start_time.setText(TimeSlotUtil.getTimeStringValue(schedule.getStartSlot()));
                input_end_time.setText(TimeSlotUtil.getTimeStringValue(schedule.getEndSlot()));
                starttime = new TimeSlots(schedule.getStartSlot(),true);
                endtime = new TimeSlots(schedule.getEndSlot(),true);
            }


        }

        canedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(change){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setCancelable(false);
                    builder.setTitle("Confirm action");
                    builder.setMessage("Are you sure you want to cancel ?, changings made will not be saved");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog

                            dialog.dismiss();
                            closeDismiss();
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
                }else {
                    dismiss();
                }
            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(singleSelection){

                    Intent intent = new Intent();


                    myScheduleSlot slot = new myScheduleSlot(input_sch_date.getText().toString(),starttime.getTimevalue(), endtime.getTimevalue(), 0);

                    intent.putExtra("scheduleslot", slot);
                    intent.putExtra("scheduleAction",1);
                    getTargetFragment().onActivityResult(getTargetRequestCode(),14404,intent);
                    getDialog().dismiss();

                }else{

                    Intent intent = new Intent();
                    intent.putExtra("scheduleslot", new myScheduleSlot(starttime.getTimevalue(), endtime.getTimevalue(),1, input_sch_start_date.getText().toString(),input_sch_end_date.getText().toString()));
                    intent.putExtra("scheduleAction",1);
                    getTargetFragment().onActivityResult(getTargetRequestCode(),14404,intent);
                    getDialog().dismiss();

                }
            }
        });
Edit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        input_sch_date.setEnabled(true);
        input_start_time.setEnabled(true);
        input_end_time.setEnabled(true);
        input_sch_start_date.setEnabled(true);
        input_sch_end_date.setEnabled(true);

        seditbtn.animate()
                .translationX(-smain.getWidth())
                .setDuration(400)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        seditbtn.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationStart(Animator animation){
                        super.onAnimationStart(animation);

                        ssavelay.animate()
                                .translationX(0)
                                .setDuration(400)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        ssavelay.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onAnimationStart(Animator animation) {
                                        super.onAnimationStart(animation);
                                        ssavelay.setVisibility(View.VISIBLE);
                                    }
                                });
                    }
                });
    }
});
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("am here","Sdsds");
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Confirm action");
                builder.setMessage("Are you sure you want to delete this record ?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog


                        requestDelete();

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
        });


        setDate(input_sch_date,view);
        setDate(input_sch_start_date,view);
        setDate(input_sch_end_date,view);

        input_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


if(starttime != null){

    Log.e("start time"," is not null");
   int j = starttime.getTimevalue() + 1;
    int[] disabledtime = new int[j];

    for(int q = 0;q<j;q++){
        disabledtime[q]= q;
    }

    FragmentManager manager = getFragmentManager();
    SlotDialogFragment slotDialogFragment = SlotDialogFragment.newInstance(RequestCode2,0,23,disabledtime);
    slotDialogFragment.setTargetFragment(thisfragement,RequestCode2);
    slotDialogFragment.show(manager, "timeslots");

}else{
    Log.e("start time"," is null");
    FragmentManager manager = getFragmentManager();
    SlotDialogFragment slotDialogFragment = SlotDialogFragment.newInstance(RequestCode2,0,23);
    slotDialogFragment.setTargetFragment(thisfragement,RequestCode2);
    slotDialogFragment.show(manager, "timeslots");

}



            }
        });


        input_end_time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    if(starttime != null){

                        Log.e("start time"," is not null");
                        int j = starttime.getTimevalue() + 1;
                        int[] disabledtime = new int[j];

                        for(int q = 0;q<j;q++){
                            disabledtime[q]= q;
                        }

                        FragmentManager manager = getFragmentManager();
                        SlotDialogFragment slotDialogFragment = SlotDialogFragment.newInstance(RequestCode2,0,23,disabledtime);
                        slotDialogFragment.setTargetFragment(thisfragement,RequestCode2);
                        slotDialogFragment.show(manager, "timeslots");

                    }else{
                        Log.e("start time"," is null");
                        FragmentManager manager = getFragmentManager();
                        SlotDialogFragment slotDialogFragment = SlotDialogFragment.newInstance(RequestCode2,0,23);
                        slotDialogFragment.setTargetFragment(thisfragement,RequestCode2);
                        slotDialogFragment.show(manager, "timeslots");

                    }
                }
            }
        });

        input_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                FragmentManager manager = getFragmentManager();
                SlotDialogFragment slotDialogFragment = SlotDialogFragment.newInstance(RequestCode1,0,23);
                slotDialogFragment.setTargetFragment(thisfragement,RequestCode1);
                slotDialogFragment.show(manager, "timeslots");
            }
        });


        input_start_time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    FragmentManager manager = getFragmentManager();





                    SlotDialogFragment slotDialogFragment = SlotDialogFragment.newInstance(RequestCode1,0,23);
                    slotDialogFragment.setTargetFragment(thisfragement,RequestCode1);
                    slotDialogFragment.show(manager, "timeslots");
                }
            }
        });
        btnMultiDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               multidatecall();
            }
        });

        btnSingleDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleSelection = true;
                Lay_startdate.setVisibility(View.GONE);
                Lay_enddate.setVisibility(View.GONE);
                Lay_date.setVisibility(View.VISIBLE);

                btnMultiDate.setTextColor(Color.parseColor("#7b0a5e"));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    btnSingleDate.setBackgroundColor(Color.parseColor("#a81783"));
                    btnMultiDate.setBackground(getResources().getDrawable(R.drawable.buttonborder, getActivity().getTheme()));


                } else {
                    btnSingleDate.setBackgroundColor(Color.parseColor("#a81783"));
                    btnMultiDate.setBackground(getResources().getDrawable(R.drawable.buttonborder));

                }

                btnSingleDate.setTextColor(Color.parseColor("#ffffff"));

            }
        });



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(singleSelection){

                    Intent intent = new Intent();

                    myScheduleSlot slot = new myScheduleSlot(input_sch_date.getText().toString(),starttime.getTimevalue(), endtime.getTimevalue(), 0);

                    intent.putExtra("scheduleslot", slot);
                    getTargetFragment().onActivityResult(getTargetRequestCode(),14403,intent);
                    getDialog().dismiss();

                }else{

                    Intent intent = new Intent();
                    intent.putExtra("scheduleslot", new myScheduleSlot(starttime.getTimevalue(), endtime.getTimevalue(),1, input_sch_start_date.getText().toString(),input_sch_end_date.getText().toString()));
                    getTargetFragment().onActivityResult(getTargetRequestCode(),14403,intent);
                    getDialog().dismiss();

                }
            }
        });
        input_sch_date.clearFocus();
        input_start_time.clearFocus();
        input_end_time.clearFocus();
        ok.requestFocus();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    public void setDate(final EditText input_dob, View view){
        input_dob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    DateDialog.getDate2(getActivity().getFragmentManager(), input_dob);
                }
            }
        });
        input_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DateDialog.getDate2(getActivity().getFragmentManager(),input_dob);

            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if (d!=null){
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            d.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //do what ever you want here, and get the result from intent like below
       // String myData = data.getStringExtra("listdata");
       // Toast.makeText(getActivity(),data.getStringExtra("listdata"), Toast.LENGTH_SHORT).show();

        if(requestCode == RequestCode1){
            change = true;
            starttime = new TimeSlots(data.getIntExtra("timevalue",0),true);
            input_start_time.setText(starttime.getTimeStringvalue());

            if(endtime == null){
                endtime = new TimeSlots(data.getIntExtra("timevalue",0)+1,true);

                if(endtime.getTimevalue()>23){

                    endtime = new TimeSlots(endtime.timevalue-24,true);
                }

                input_end_time.setText(endtime.getTimeStringvalue());

            }



        }else if(requestCode == RequestCode2){

            change = true;
           endtime = new TimeSlots(data.getIntExtra("timevalue",0),true);
            input_end_time.setText(endtime.getTimeStringvalue());


            if(starttime == null){

                starttime = new TimeSlots(data.getIntExtra("timevalue",0)-1,true);

                input_start_time.setText(starttime.getTimeStringvalue());
            }

        }
    }


    public void multidatecall(){


        singleSelection = false;
        final int version = Build.VERSION.SDK_INT;

        Lay_startdate.setVisibility(View.VISIBLE);
        Lay_enddate.setVisibility(View.VISIBLE);
        Lay_date.setVisibility(View.GONE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btnMultiDate.setBackgroundColor(Color.parseColor("#a81783"));
            btnSingleDate.setBackground(getResources().getDrawable(R.drawable.buttonborder, getActivity().getTheme()));


        } else {
            btnMultiDate.setBackgroundColor(Color.parseColor("#a81783"));
            btnSingleDate.setBackground(getResources().getDrawable(R.drawable.buttonborder));

        }

        btnMultiDate.setTextColor(Color.parseColor("#ffffff"));
        btnSingleDate.setTextColor(Color.parseColor("#7b0a5e"));
    }

public void closeDismiss(){

    getDialog().dismiss();
}
    public void requestDelete(){

        Intent intent = new Intent();

        intent.putExtra("scheduleAction", 0);
        getTargetFragment().onActivityResult(getTargetRequestCode(),14404,intent);
        getDialog().dismiss();
    }
}
