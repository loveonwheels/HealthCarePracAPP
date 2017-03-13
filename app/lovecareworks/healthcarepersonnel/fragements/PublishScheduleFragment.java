package com.mSIHAT.hcp.fragements;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.mSIHAT.hcp.Central;
import com.mSIHAT.hcp.R;
import com.mSIHAT.hcp.classes.HCPAuthentication;
import com.mSIHAT.hcp.dialogs.ScheduleAddFragment;
import com.mSIHAT.hcp.dialogs.SlotDialogFragment;
import com.mSIHAT.hcp.model.EducationItem;
import com.mSIHAT.hcp.model.EducationalListAdapter;
import com.mSIHAT.hcp.model.ScheduleSlotHolder;
import com.mSIHAT.hcp.model.ScheduleSlotHolder2;
import com.mSIHAT.hcp.model.TimeSlots;
import com.mSIHAT.hcp.model.myScheduleSlot;
import com.mSIHAT.hcp.model.myScheduleSlotListAdapter;
import com.mSIHAT.hcp.registration.EducationalAdder;
import com.mSIHAT.hcp.registration.EmploymentAdder;
import com.mSIHAT.hcp.webapi.RestScheduleService;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**

 * Activities that contain this fragment must implement the
 * {@link PublishScheduleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PublishScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PublishScheduleFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    myScheduleSlotListAdapter cts;
    RestScheduleService restScheduleService = new RestScheduleService();
    List<myScheduleSlot> results =  new ArrayList<>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button addSchedule,closeSchedule,publishSchedule;
    Fragment thisfragement;
    ListView listvi;
    int item_position = 0;
    private OnFragmentInteractionListener mListener;

    public PublishScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PublishScheduleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PublishScheduleFragment newInstance(String param1, String param2) {
        PublishScheduleFragment fragment = new PublishScheduleFragment();
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

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.alerttheme2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_publish_schedule, container, false);
        setCancelable(false);

        thisfragement = this;
        Toolbar tool = (Toolbar)view.findViewById(R.id.publish_schedule_toolbar);
        addSchedule = (Button)view.findViewById(R.id.btnAddSchedule);
        closeSchedule = (Button)view.findViewById(R.id.btnCloseSchedule);
        publishSchedule= (Button)view.findViewById(R.id.btnPublishSchedule);
        listvi = (ListView)view.findViewById(R.id.publishlistview);
        // tool.setOverflowIcon(getResources().getDrawable(R.drawable.arrowdown, getActivity().getTheme()));
        tool.setTitle("");
        tool.setNavigationIcon(R.drawable.ic_back);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tool.setOverflowIcon(getResources().getDrawable(R.drawable.menu_icon, getActivity().getTheme()));
        } else {
            tool.setOverflowIcon(getResources().getDrawable(R.drawable.menu_icon));
        }


        publishSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishScheduleValues();
            }
        });
        final AlertDialog.Builder builder_long = new AlertDialog.Builder(getActivity(),R.style.alertthemelongclick);
        final CharSequence renewal[] = getResources().getTextArray(R.array.reglongclick);
        builder_long.setItems(renewal, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                   Bundle args = new Bundle();
                    args.putParcelable("schedule",results.get(item_position));
                    FragmentManager manager = getFragmentManager();
                    ScheduleAddFragment alertDialogFragment = new ScheduleAddFragment();
                    alertDialogFragment.setArguments(args);
                    alertDialogFragment.setTargetFragment(thisfragement,14404);
                    alertDialogFragment.show(manager, "schedulefragment");

                }else if(which == 1){

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setTitle("Confirm action");
                    builder.setMessage("Are you sure you want to delete this record ?");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog


                            deleteschedule(item_position);

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

        listvi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle args = new Bundle();
                args.putParcelable("schedule",results.get(item_position));
                FragmentManager manager = getFragmentManager();
                ScheduleAddFragment alertDialogFragment = new ScheduleAddFragment();
                alertDialogFragment.setArguments(args);
                alertDialogFragment.setTargetFragment(thisfragement,14404);
                alertDialogFragment.show(manager, "schedulefragment");

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

        tool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
closeSchedule.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        dismiss();
    }
});

        addSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                FragmentManager manager = getFragmentManager();
                ScheduleAddFragment alertDialogFragment = new ScheduleAddFragment();

                alertDialogFragment.setTargetFragment(thisfragement,14403);
                alertDialogFragment.show(manager, "schedulefragment");
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

        if(requestCode == 14403){

            myScheduleSlot schedule = data.getParcelableExtra("scheduleslot");

            results.add(schedule);
            cts = new myScheduleSlotListAdapter(getActivity(), results);
            listvi.setAdapter(cts);

            Toast.makeText(getContext(),String.valueOf(schedule.getScheduleType()),Toast.LENGTH_LONG).show();


        }else if(requestCode == 14404){
            if(data.getIntExtra("scheduleAction",0) == 0){
                deleteschedule(item_position);
            }else{

                myScheduleSlot schedule = data.getParcelableExtra("scheduleslot");
                results.set(item_position,schedule);
                cts = new myScheduleSlotListAdapter(getActivity(), results);
                listvi.setAdapter(cts);
            }
        }
    }


    public void deleteschedule(int position){

        results.remove(position);
        cts = new myScheduleSlotListAdapter(getActivity(), results);
        listvi.setAdapter(cts);
    }


    public void publishScheduleValues(){
        ScheduleSlotHolder2 schedulesList = new ScheduleSlotHolder2();
        schedulesList.setScheduleList(results);
        schedulesList.setId(((Central)getActivity()).gethcp_id());

        Log.e("schedules",schedulesList.getScheduleList().get(0).getSlotDate());
        final AlertDialog progressDialog = new SpotsDialog(getActivity(), R.style.CustomDialogPublishing);
// To dismiss the dialog
        progressDialog.show();

        Call<Boolean> call = restScheduleService.getService().SetSchedule(schedulesList);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                int statusCode = response.code();
               Boolean successful = response.body();
                String msg = "here";


                if (statusCode == 200) {

if(successful){

    Intent intent = new Intent();
    intent.putExtra("scheduleAction", 0);
    getTargetFragment().onActivityResult(getTargetRequestCode(),14414,intent);
    dismiss();
    progressDialog.hide();

}

                }else{


                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Publishing failed")
                            .setCancelable(true)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                    progressDialog.hide();
                }


                //  progress.dismiss();


            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                //   progress.dismiss();
                progressDialog.hide();
                Toast.makeText(getActivity(),t.toString(),Toast.LENGTH_LONG).show();

            }
        });


    }
}
