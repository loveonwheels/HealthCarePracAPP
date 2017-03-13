package com.mSIHAT.hcp.fragements;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mSIHAT.hcp.Central;
import com.mSIHAT.hcp.R;
import com.mSIHAT.hcp.db.dbclasses.ScheduleTra;
import com.mSIHAT.hcp.model.Appiontments;
import com.mSIHAT.hcp.model.noficationmodel;
import com.mSIHAT.hcp.model.noficationmodelfromapi;
import com.mSIHAT.hcp.pageaddapter.NotificationListAdapter;
import com.mSIHAT.hcp.registration.EducationalAdder;
import com.mSIHAT.hcp.webapi.RestUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link notificationhub.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link notificationhub#newInstance} factory method to
 * create an instance of this fragment.
 */
public class notificationhub extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final int DIALOG_FRAGMENT = 1;
    noficationmodelfromapi activenote ;
    int activeposition = 0;
    private RestUserService restUserService = new RestUserService();
    ListView listvi;
    Fragment me;
    NotificationListAdapter ci;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public notificationhub() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment notificationhub.
     */
    // TODO: Rename and change types and number of parameters
    public static notificationhub newInstance(String param1, String param2) {
        notificationhub fragment = new notificationhub();
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
       View view = inflater.inflate(R.layout.fragment_notificationhub, container, false);
        me = this;
        Toolbar tool = (Toolbar)view.findViewById(R.id.toolbarnotificationhub);
        listvi = (ListView)view.findViewById(R.id.notificationslist);
        tool.setTitle("Notifications");
        tool.setNavigationIcon(R.drawable.showslide);

        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tool.setOverflowIcon(getResources().getDrawable(R.drawable.menu_icon, getActivity().getTheme()));
        } else {
            tool.setOverflowIcon(getResources().getDrawable(R.drawable.menu_icon));
        }
        */
        tool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Central)getActivity()).open();
            }
        });
        //tool.inflateMenu(R.menu.notification_menu);

listvi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentManager manager = getFragmentManager();
        Log.e("notification p",String.valueOf(position));
        Log.e("notification p",ci.getItem(position).notification_date);
        activenote = ci.getItem(position);
        activeposition = position;
        if(ci.getItem(position).status == 1){
            ci.getItem(position).status = 2;
            ci.notifyDataSetChanged();
            listvi.invalidateViews();
            listvi.refreshDrawableState();
            Notification_details alertDialogFragment = Notification_details.newInstance(ci.getItem(position),true);
            alertDialogFragment.setTargetFragment(me, DIALOG_FRAGMENT);

            alertDialogFragment.show(manager, "notificationdetails");
        }else{
            Notification_details alertDialogFragment = Notification_details.newInstance(ci.getItem(position),false);
            alertDialogFragment.setTargetFragment(me, DIALOG_FRAGMENT);
            alertDialogFragment.show(manager, "notificationdetails");
        }



    }
});

        final AlertDialog progressDialog = new SpotsDialog(getContext(), R.style.CustomDialog);
// To dismiss the dialog
        progressDialog.show();
        Call<List<noficationmodelfromapi>> call = restUserService.getService().GetHcpNotification(((Central)getActivity()).gethcp_id(),1);
        call.enqueue(new Callback<List<noficationmodelfromapi>>() {
            @Override
            public void onResponse(Call<List<noficationmodelfromapi>> call, Response<List<noficationmodelfromapi>> response) {
                int statusCode = response.code();
                List<noficationmodelfromapi> userAppiontments = response.body();
                String msg = "here";

                ArrayList<noficationmodel> transformedRequest = new ArrayList<>();

                if (statusCode == 200) {


                    Log.e("dfdf3", msg);
                    if(userAppiontments.size() > 0){



                      /*  for(int j = 0;j < userAppiontments.size();j++){
                            ScheduleTra newappreq = new ScheduleTra(userAppiontments.get(j));
                            transformedRequest.add(newappreq);
                        }
*/
                       ci = new NotificationListAdapter(getActivity(), userAppiontments);
                        listvi.setAdapter(ci);

                        progressDialog.hide();
                    }else{


                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("You have no notification")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

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

                Toast.makeText(getActivity(),"request failed fd"+ String.valueOf(statusCode),Toast.LENGTH_LONG).show();
                Log.e("dfdf1", msg);

            }

            @Override
            public void onFailure(Call<List<noficationmodelfromapi>> call, Throwable t) {
                //   progress.dismiss();
                progressDialog.hide();
                Toast.makeText(getActivity(),"request failed again ",Toast.LENGTH_LONG).show();

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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case DIALOG_FRAGMENT:

                if (resultCode == Activity.RESULT_OK) {
                    // After Ok code.
                    ci.removeItem(activeposition);
                    ci.notifyDataSetChanged();
                    listvi.invalidateViews();
                    listvi.refreshDrawableState();
                } else if (resultCode == Activity.RESULT_CANCELED){
                    // After Cancel code.
                }

                break;
        }
    }
}

