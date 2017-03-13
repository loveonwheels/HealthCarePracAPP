package com.mSIHAT.hcp.fragements;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.mSIHAT.hcp.Central;
import com.mSIHAT.hcp.R;
import com.mSIHAT.hcp.db.dbclasses.AppReqTra;
import com.mSIHAT.hcp.util.reutilfunction;
import com.mSIHAT.hcp.webapi.RestUserService;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AppReqFragement.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AppReqFragement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppReqFragement extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RestUserService restUserService = new RestUserService();
Button accept , reject;
    CircularImageView proI;
    ImageView pro;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
public AppReqTra app;
    private OnFragmentInteractionListener mListener;
public TextView Patientname,PatientAddress,PatientAge,PatientGender,PatientAppiontmentHeader,PatientAppiontmentDate,PatientAppiontmentTime,PatientAilmentDes,PatientAddiontionalInfo,PatientServiceType ;

    public AppReqFragement(){

    }




    // TODO: Rename and change types and number of parameters
    public static AppReqFragement newInstance(AppReqTra appointment,int page, String title) {

        AppReqFragement fragmentFirst = new AppReqFragement();
            Bundle args = new Bundle();
            args.putInt("someInt", page);
            args.putString("someTitle", title);
        args.putSerializable("class",appointment);
            fragmentFirst.setArguments(args);
            return fragmentFirst;
        }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            app = (AppReqTra) getArguments().getSerializable("class");
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       View  view = inflater.inflate(R.layout.fragment_app_req_fragement, container, false);

        pro= (CircularImageView)view.findViewById(R.id.CImg);
        try {

            Toast.makeText(getActivity(),reutilfunction.convertname_to_intial(app.getPatient_fullname()),Toast.LENGTH_LONG).show();
            pro.setImageBitmap(textImg(reutilfunction.convertname_to_intial(app.getPatient_fullname()),39,189,190));
        } catch (IOException e) {
            e.printStackTrace();
        }

        accept = (Button)view.findViewById(R.id.button27);
        reject = (Button)view.findViewById(R.id.button26);

       Patientname = (TextView)view.findViewById(R.id.txt_patient_name);
        Patientname.setText(app.getPatient_fullname());

        PatientAddress = (TextView)view.findViewById(R.id.txt_patient_address);
        PatientAddress.setText(app.getPatient_address());

        PatientAge = (TextView)view.findViewById(R.id.txt_patient_age);
        PatientAge.setText(app.getPatient_age());

        PatientGender = (TextView)view.findViewById(R.id.txt_patient_gender);
        PatientGender.setText(reutilfunction.convertgender(app.getPatient_gender()));

        PatientAppiontmentHeader = (TextView)view.findViewById(R.id.txt_appiontment_header);
        PatientAppiontmentHeader.setText(app.getAppiontment_date());


        PatientAppiontmentDate = (TextView)view.findViewById(R.id.txt_appointment_date);
        PatientAppiontmentDate.setText(app.getAppiontment_date());


        PatientAppiontmentTime = (TextView)view.findViewById(R.id.txt_appointment_time);
        PatientAppiontmentTime.setText(app.getAppiontment_time());

        PatientAilmentDes = (TextView)view.findViewById(R.id.txt_patient_ailmentdes);
        PatientAilmentDes.setText(app.getAilmentdescription());


        PatientAddiontionalInfo = (TextView)view.findViewById(R.id.txt_patient_add);
        PatientAddiontionalInfo.setText(app.getAddiontionalinformation());

        PatientServiceType = (TextView)view.findViewById(R.id.txt_service_type);
        PatientServiceType.setText(app.getServicedescription());
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialog = new SpotsDialog(getContext(), R.style.CustomDialog2);
// To dismiss the dialog
                dialog.show();

                Call<Boolean> call = restUserService.getService().Appointmentrejected(app.getAppiontmentid());
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        int statusCode = response.code();
                        Boolean userAppiontments = response.body();
                        String msg = "here";

                        List<AppReqTra> transformedRequest = new ArrayList<>();

                        if (statusCode == 200) {




                            ((Central)getActivity()).removefragment();
                            dialog.hide();

                            //  progress.dismiss();
                        }else{
                            Log.e("dfdf2", msg);
                            msg="error again";
                            dialog.hide();
                            Toast.makeText(getActivity(),"request failed herer",Toast.LENGTH_LONG).show();

                        }
                        Log.e("dfdf1", msg);

                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        //   progress.dismiss();
                        dialog.hide();
                        Toast.makeText(getActivity(),"request failed",Toast.LENGTH_LONG).show();

                        //   Log.e("dfdf", t.toString());
                    }
                });
            }
        });


        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog dialog = new SpotsDialog(getContext(), R.style.CustomDialog2);
// To dismiss the dialog
               dialog.show();

                Call<Boolean> call = restUserService.getService().Appointmentaccepted(app.getAppiontmentid());
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        int statusCode = response.code();
                       Boolean userAppiontments = response.body();
                        String msg = "here";

                        List<AppReqTra> transformedRequest = new ArrayList<>();

                        if (statusCode == 200) {




                            ((Central)getActivity()).removefragment();
                            dialog.hide();

                            //  progress.dismiss();
                        }else{
                            Log.e("dfdf2", msg);
                            msg="error again";
                           dialog.hide();
                            Toast.makeText(getActivity(),"request failed herer",Toast.LENGTH_LONG).show();

                        }
                        Log.e("dfdf1", msg);

                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        //   progress.dismiss();
                        dialog.hide();
                        Toast.makeText(getActivity(),"request failed",Toast.LENGTH_LONG).show();

                        //   Log.e("dfdf", t.toString());
                    }
                });
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

    Bitmap textImg(final String text,int R,int G,int B) throws IOException {
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
