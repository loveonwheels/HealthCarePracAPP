package com.mSIHAT.hcp.dialogs;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mSIHAT.hcp.R;
import com.mSIHAT.hcp.db.dbclasses.ScheduleTra;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link schedule_dialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link schedule_dialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class schedule_dialog extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ScheduleTra appiontment;
    int viewcase;
    ImageView pro;

TextView patName;
TextView cliName;
TextView Add;
TextView Gender;
TextView Relationship;
TextView schDate;
TextView schTime;
TextView ailDes;
TextView addDes;
TextView serDes;
TextView btnReq;
TextView btnCan;
    
    private OnFragmentInteractionListener mListener;

    public schedule_dialog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment schedule_dialog.
     */
    // TODO: Rename and change types and number of parameters
    public static schedule_dialog newInstance(ScheduleTra schedule, int viewcase) {
        schedule_dialog fragment = new schedule_dialog();
        Bundle args = new Bundle();
        args.putSerializable("appiontment", schedule);
        args.putInt("viewcase", viewcase);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            appiontment = (ScheduleTra)getArguments().getSerializable("appiontment");
        }

        setStyle(DialogFragment.STYLE_NO_TITLE,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);

        return dialog;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setStyle(DialogFragment.STYLE_NO_TITLE,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
      View view = inflater.inflate(R.layout.fragment_schedule_dialog, container, false);
        RelativeLayout btnClose = (RelativeLayout)view.findViewById(R.id.btnCloseDialog);

        patName = (TextView) view.findViewById(R.id.sch_dia_pat_name);
        cliName = (TextView) view.findViewById(R.id.sch_dia_cli_name);
        Add = (TextView) view.findViewById(R.id.sch_dia_add);
        Gender = (TextView) view.findViewById(R.id.sch_dia_gender);
        Relationship= (TextView) view.findViewById(R.id.sch_dia_rel);
        schDate = (TextView) view.findViewById(R.id.sch_dia_date);
        schTime = (TextView) view.findViewById(R.id.sch_dia_time);
        ailDes = (TextView) view.findViewById(R.id.sch_dia_ail);
        addDes = (TextView) view.findViewById(R.id.sch_dia_info);
        serDes = (TextView) view.findViewById(R.id.sch_dia_service_des);
        btnReq = (Button)view.findViewById(R.id.btnRequest);
        btnCan = (Button)view.findViewById(R.id.btnCanel);

        patName.setText(appiontment.patient_fullname);
        cliName.setText(appiontment.sponsor_fullname);
        Add.setText(appiontment.patient_address);
        Gender.setText(appiontment.patient_gender);
        Relationship.setText(appiontment.patient_relationship);
        schDate.setText(appiontment.appiontment_date);
        schTime.setText(appiontment.appiontment_time);
        ailDes.setText(appiontment.ailmentdescription);
        addDes.setText(appiontment.ailmentdescription);
        serDes.setText(appiontment.servicedescription);


            btnReq.setVisibility(View.VISIBLE);
            btnCan.setVisibility(View.VISIBLE);



        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    Bitmap textImg(final String text, int R, int G, int B) throws IOException {
        Bitmap bitmap = Bitmap.createBitmap(112, 120, Bitmap.Config.ARGB_8888);

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(45);
        bitmap.eraseColor(Color.rgb(R, G, B));
        paint.setTextAlign(Paint.Align.RIGHT);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText(text, 80, 80, paint);
        return bitmap;
    }
}
