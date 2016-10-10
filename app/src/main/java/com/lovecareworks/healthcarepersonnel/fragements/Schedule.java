package com.lovecareworks.healthcarepersonnel.fragements;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.lovecareworks.healthcarepersonnel.Central;
import com.lovecareworks.healthcarepersonnel.R;
import com.lovecareworks.healthcarepersonnel.db.dbclasses.ScheduleTra;
import com.lovecareworks.healthcarepersonnel.dialogs.MySchedule;
import com.lovecareworks.healthcarepersonnel.dialogs.schedule_dialog;
import com.lovecareworks.healthcarepersonnel.model.Appiontments;
import com.lovecareworks.healthcarepersonnel.webapi.RestUserService;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Schedule.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Schedule#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Schedule extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button btnToday;
    // Get a reference for the week view in the layout.

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RestUserService restUserService = new RestUserService();
    private OnFragmentInteractionListener mListener;
    ListView listvi;
    ScheduleListAdapter ci;
    public Schedule() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Schedule.
     */
    // TODO: Rename and change types and number of parameters
    public static Schedule newInstance(String param1, String param2) {
        Schedule fragment = new Schedule();
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
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        Toolbar tool = (Toolbar)view.findViewById(R.id.toolbar);

       // tool.setOverflowIcon(getResources().getDrawable(R.drawable.arrowdown, getActivity().getTheme()));
tool.setTitle("My Appointments");
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

        tool.inflateMenu(R.menu.schedule_menu);
        tool.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.action_myschedule){




                }
                return false;
            }
        });


        listvi = (ListView)view.findViewById(R.id.schedule_list);


        listvi.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listvi.getItemAtPosition(position);
                ScheduleTra appiontment = (ScheduleTra) o;
                FragmentManager fm = getActivity().getSupportFragmentManager();
                schedule_dialog dFragment =  schedule_dialog.newInstance(appiontment,1);
                // Show DialogFragment
                dFragment.show(fm, "Dialog Fragment");

                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framei, new AppiontmentInfo()).commit();



            }
        });



        final AlertDialog progressDialog = new SpotsDialog(getContext(), R.style.CustomDialog);
// To dismiss the dialog
        progressDialog.show();
        Call<List<Appiontments>> call = restUserService.getService().GetSchedule( ((Central)getActivity()).gethcp_id());
        call.enqueue(new Callback<List<Appiontments>>() {
            @Override
            public void onResponse(Call<List<Appiontments>> call, Response<List<Appiontments>> response) {
                int statusCode = response.code();
                List<Appiontments> userAppiontments = response.body();
                String msg = "here";

                ArrayList<ScheduleTra> transformedRequest = new ArrayList<>();

                if (statusCode == 200) {



                    if(userAppiontments.size() > 0){

                        for(int j = 0;j < userAppiontments.size();j++){
                            ScheduleTra newappreq = new ScheduleTra(userAppiontments.get(j));
                            transformedRequest.add(newappreq);
                        }

                        ci = new ScheduleListAdapter(getActivity(), transformedRequest);
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
                    Toast.makeText(getActivity(),"request failed",Toast.LENGTH_LONG).show();

                }
                Log.e("dfdf1", msg);

            }



            @Override
            public void onFailure(Call<List<Appiontments>> call, Throwable t) {
                //   progress.dismiss();
                progressDialog.hide();
                Toast.makeText(getActivity(),"request failed",Toast.LENGTH_LONG).show();

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
