package com.mSIHAT.hcp;

import android.*;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mSIHAT.hcp.R;
import com.mSIHAT.hcp.classes.HCPAuthentication;
import com.mSIHAT.hcp.webapi.RestUserService;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int RESULT_LOAD_IMG = 1;
    private RestUserService restUserService = new RestUserService();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText username;
    EditText password;

    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Button login = (Button)view.findViewById(R.id.btnLogin);
        username = (EditText) view.findViewById(R.id.input_username);
        password = (EditText) view.findViewById(R.id.input_pwd);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getActivity(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED  || (ContextCompat.checkSelfPermission(getActivity(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            android.Manifest.permission.READ_EXTERNAL_STORAGE)) {

                        Log.e("permission","dsdsd");
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                    } else {

                        // No explanation needed, we can request the permission.

                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                                        android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.INTERNET,android.Manifest.permission.CALL_PHONE},
                                RESULT_LOAD_IMG);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                }else{


               // ((FirstPage)getActivity()).opengallery();
                final AlertDialog progressDialog = new SpotsDialog(getActivity(), R.style.CustomDialog4);
// To dismiss the dialog
                progressDialog.show();
                Call<HCPAuthentication> call = restUserService.getService().Authenticate(username.getText().toString(),password.getText().toString());
                call.enqueue(new Callback<HCPAuthentication>() {
                    @Override
                    public void onResponse(Call<HCPAuthentication> call, Response<HCPAuthentication> response) {
                        int statusCode = response.code();
                        HCPAuthentication userAppiontments = response.body();
                        String msg = "here";


                        if (statusCode == 200) {
                            Log.e("here","heer");
                            Log.e("userinformation",String.valueOf(userAppiontments.getDetail()));
if(userAppiontments.getDetail() > 0 ){

    Intent intent = new Intent(getActivity().getBaseContext(), Central.class);
    intent.putExtra("hcp_id",userAppiontments.getDetail());
    intent.putExtra("imageurl",userAppiontments.hcp_url);
    getActivity().startActivity(intent);
    getActivity().overridePendingTransition(R.anim.move, R.anim.moveout);

    progressDialog.hide();
}else{
    Log.e("here","heer2");
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setMessage("Authentication failed verify username and password")
            .setCancelable(false)
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                }
            });
    AlertDialog alert = builder.create();
    alert.show();
    progressDialog.hide();

}

                        }else{
                            Log.e("here","heer3");

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("Authentication failed Verify username and password")
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


                    }

                    @Override
                    public void onFailure(Call<HCPAuthentication> call, Throwable t) {
                        //   progress.dismiss();
                        progressDialog.hide();
                        Toast.makeText(getActivity(),"error 405 :"+t.toString(),Toast.LENGTH_LONG).show();

                    }
                });



            }

            }
        });
     /*   register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getBaseContext(), RegistrationActivity.class);
                getActivity().startActivity(intent);

                getActivity().overridePendingTransition(R.anim.move, R.anim.moveout);

            }
        });*/

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
