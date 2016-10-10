package com.lovecareworks.healthcarepersonnel.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.lovecareworks.healthcarepersonnel.Central;
import com.lovecareworks.healthcarepersonnel.R;
import com.lovecareworks.healthcarepersonnel.db.dbclasses.ScheduleTra;
import com.lovecareworks.healthcarepersonnel.fragements.Home;
import com.lovecareworks.healthcarepersonnel.fragements.PublishScheduleFragment;
import com.lovecareworks.healthcarepersonnel.fragements.ScheduleListAdapter;
import com.lovecareworks.healthcarepersonnel.model.Appiontments;
import com.lovecareworks.healthcarepersonnel.model.ScheduleSlotHolder;
import com.lovecareworks.healthcarepersonnel.model.myScheduleSlot;
import com.lovecareworks.healthcarepersonnel.model.myScheduleSlotListAdapter;
import com.lovecareworks.healthcarepersonnel.util.TimeSlotUtil;
import com.lovecareworks.healthcarepersonnel.webapi.RestScheduleService;
import com.lovecareworks.healthcarepersonnel.webapi.RestUserService;
import com.lovecareworks.healthcarepersonnel.webapi.ScheduleService;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Activities that contain this fragment must implement the
 * {@link MySchedule.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MySchedule#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MySchedule extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
public ListView listvi;
    myScheduleSlotListAdapter ci;
    private RestScheduleService scheduleService = new RestScheduleService();
    private OnFragmentInteractionListener mListener;

    public MySchedule() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SetSchedule.
     */
    // TODO: Rename and change types and number of parameters
    public static MySchedule newInstance(String param1, String param2) {
        MySchedule fragment = new MySchedule();
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
         View view = inflater.inflate(R.layout.fragment_set_schedule, container, false);

        Toolbar tool = (Toolbar)view.findViewById(R.id.set_schedule_toolbar);
        FloatingActionButton floatingactBtn = (FloatingActionButton)view.findViewById(R.id.add_schedule);
        // tool.setOverflowIcon(getResources().getDrawable(R.drawable.arrowdown, getActivity().getTheme()));
        tool.setTitle("My Schedule");
        tool.setNavigationIcon(R.drawable.showslide);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tool.setOverflowIcon(getResources().getDrawable(R.drawable.menu_icon, getActivity().getTheme()));
        } else {
            tool.setOverflowIcon(getResources().getDrawable(R.drawable.menu_icon));
        }

        tool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Central)getActivity()).open();
            }
        });

        floatingactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               FragmentManager manager = getFragmentManager();
                PublishScheduleFragment alertDialogFragment = new PublishScheduleFragment();
                alertDialogFragment.show(manager, "publishSchedule");
            }
        });

        listvi = (ListView)view.findViewById(R.id.myschedule_list);


        listvi.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
              /*  Object o = listvi.getItemAtPosition(position);
                ScheduleTra appiontment = (ScheduleTra) o;
                FragmentManager fm = getActivity().getSupportFragmentManager();
                schedule_dialog dFragment =  schedule_dialog.newInstance(appiontment,2);
                // Show DialogFragment
                dFragment.show(fm, "Dialog Fragment");

                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framei, new AppiontmentInfo()).commit();

*/

            }
        });

        final AlertDialog progressDialog = new SpotsDialog(getContext(), R.style.CustomDialog);
// To dismiss the dialog
        progressDialog.show();
        Call<ScheduleSlotHolder> call = scheduleService.getService().GetSchedule2(((Central)getActivity()).gethcp_id(), TimeSlotUtil.getDateinString());
        call.enqueue(new Callback<ScheduleSlotHolder>() {
            @Override
            public void onResponse(Call<ScheduleSlotHolder> call, Response<ScheduleSlotHolder> response) {
                int statusCode = response.code();
Log.e("status",String.valueOf(statusCode));
                ScheduleSlotHolder responsefrom = response.body();


                String msg = "here";

                if (statusCode == 200) {

                    List<myScheduleSlot> userAppiontments = responsefrom.getScheduleList();



                    if(userAppiontments.size() > 0){




                        ci = new myScheduleSlotListAdapter(getActivity(), userAppiontments);
                        listvi.setAdapter(ci);

                        progressDialog.hide();
                    }else{


                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("No appiontment Found!")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framei, new Home()).commit();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                        progressDialog.hide();
                    }


                    //  progress.dismiss();
                }else{
                    Log.e("dfdf2", msg);
                    msg="error again";
                    progressDialog.hide();
                    Toast.makeText(getActivity(),"request failed gone"+String.valueOf(statusCode),Toast.LENGTH_LONG).show();

                }
                Log.e("dfdf1", msg);

            }

            @Override
            public void onFailure(Call<ScheduleSlotHolder> call, Throwable t) {
                //   progress.dismiss();
                progressDialog.hide();
                Toast.makeText(getActivity(),"request failed here",Toast.LENGTH_LONG).show();

                //   Log.e("dfdf", t.toString());
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


    public void getSchedule(){



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
}
