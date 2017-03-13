package com.mSIHAT.hcp.fragements;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mSIHAT.hcp.R;
import com.mSIHAT.hcp.classes.LocationUpdate;
import com.mSIHAT.hcp.model.noficationmodelfromapi;
import com.mSIHAT.hcp.util.DateTimeFormatter;
import com.mSIHAT.hcp.webapi.RestPractitionerService;
import com.mSIHAT.hcp.webapi.RestUserService;

import java.text.ParseException;
import java.util.Date;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Notification_details.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Notification_details#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Notification_details extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RestPractitionerService restUserService = new RestPractitionerService();
ImageButton closeBtn,deleteBtn;
    // TODO: Rename and change types of parameters
    private noficationmodelfromapi mParam1;
    private Boolean mParam2;

    private OnFragmentInteractionListener mListener;

    public Notification_details() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment Notification_details.
     */
    // TODO: Rename and change types and number of parameters
    public static Notification_details newInstance(noficationmodelfromapi note,boolean state) {
        Log.e("notification p","i am herer");
        Notification_details fragment = new Notification_details();
        Log.e("notification p","i am herer 2");
        Bundle args = new Bundle();
        Log.e("notification p","i am herer 3");
        args.putSerializable(ARG_PARAM1, note);
        args.putBoolean(ARG_PARAM2, state);
        Log.e("notification p","i am herer 4");
        fragment.setArguments(args);
        Log.e("notification p","i am herer 5");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (noficationmodelfromapi)getArguments().getSerializable(ARG_PARAM1);
            mParam2 = getArguments().getBoolean(ARG_PARAM2);
        }
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.alerttheme2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_notification_details, container, false);


        Toolbar tool = (Toolbar)view.findViewById(R.id.toolnotificationdetails);

        // tool.setOverflowIcon(getResources().getDrawable(R.drawable.arrowdown, getActivity().getTheme()));

        tool.setNavigationIcon(R.drawable.ic_back);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tool.setOverflowIcon(getResources().getDrawable(R.drawable.ic_back, getActivity().getTheme()));
        } else {
            tool.setOverflowIcon(getResources().getDrawable(R.drawable.ic_back));
        }

        tool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDismiss();
            }
        });
        closeBtn = (ImageButton)view.findViewById(R.id.noteCloseBtn);
        deleteBtn = (ImageButton)view.findViewById(R.id.noteDeleteBtn);
        TextView appointmentheader = (TextView)view.findViewById(R.id.txt_appiontment_header) ;
        TextView appheader = (TextView)view.findViewById(R.id.appheader) ;
        TextView appdetails = (TextView)view.findViewById(R.id.appdetails) ;

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDismiss();
            }
        });


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("call for","request");
                final AlertDialog progressDialog = new SpotsDialog(getContext(), R.style.CustomDialog2);
// To dismiss the dialog
                progressDialog.show();

                Call<Boolean> call = restUserService.getService().deletenotification(mParam1.id);
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        int statusCode = response.code();
                        String msg = "sucess";

                        progressDialog.dismiss();
                        if (statusCode == 200) {
                            msg = "read notification";
                            Log.e("read notification ser", msg);
                            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, getActivity().getIntent());
                            closeDismiss();

                        } else {

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("Something went wrong, please try again")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }


                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        //   progress.dismiss();
                        Log.e("update location ser", t.toString());

                    }
                });
            }
        });
        DateTimeFormatter datevalue;
        try {
            datevalue = new DateTimeFormatter(mParam1.notification_date);
            appointmentheader.setText(datevalue.getTImevalue()+" "+datevalue.getdayvalue()+", "+datevalue.getDateWithMonth());
            tool.setTitle(datevalue.getTImevalue()+" "+datevalue.getdayvalue()+", "+datevalue.getDateWithMonth());
        } catch (ParseException e) {
            e.printStackTrace();
        }
appheader.setText(mParam1.header);
        appdetails.setText(mParam1.details);

        if(mParam2){
            Log.e("call for","request");

            Call<Boolean> call = restUserService.getService().readnotification(mParam1.id);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    int statusCode = response.code();
                    String msg = "sucess";


                    if (statusCode == 200) {
                        msg = "read notification";
                        Log.e("read notification ser", msg);

                    } else {
                        msg = "error again";
                        Log.e("update location ser", msg + statusCode);
                    }

                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    //   progress.dismiss();
                    Log.e("update location ser", t.toString());

                }
            });

        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

     public void closeDismiss(){
        dismiss();
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
