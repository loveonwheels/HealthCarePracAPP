package com.mSIHAT.hcp.registration;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mSIHAT.hcp.R;
import com.mSIHAT.hcp.classes.basicinfomation;
import com.mSIHAT.hcp.classes.errorMessage;
import com.mSIHAT.hcp.util.CountryDetails;
import com.mSIHAT.hcp.util.DateDialog;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link registration1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link registration1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class registration1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String errormessage = "";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText fullname;
    EditText input_nationality;
    EditText input_dob;
    EditText input_marital_status;
    EditText input_gender;
    EditText email;
    EditText phonenum;
    EditText secphonenum;
    EditText address;
    EditText secaddress;
    EditText service_area;
    EditText nric;

    private OnFragmentInteractionListener mListener;

    public registration1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment regristration1.
     */
    // TODO: Rename and change types and number of parameters
    public static registration1 newInstance(String param1, String param2) {
        registration1 fragment = new registration1();
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
        View view =  inflater.inflate(R.layout.fragment_regristration1, container, false);
        input_nationality= (EditText)view.findViewById(R.id.input_nationality);
        input_dob = (EditText)view.findViewById(R.id.dob);
        input_marital_status = (EditText)view.findViewById(R.id.input_marital_status);
        input_gender = (EditText)view.findViewById(R.id.input_gender);
        fullname = (EditText)view.findViewById(R.id.input_name);
        email = (EditText)view.findViewById(R.id.input_email);
        service_area = (EditText)view.findViewById(R.id.input_servicearea);
        phonenum = (EditText)view.findViewById(R.id.input_number1);
        secphonenum = (EditText)view.findViewById(R.id.input_number2);
        address = (EditText)view.findViewById(R.id.input_address1);
        secaddress = (EditText)view.findViewById(R.id.input_address2);
        nric = (EditText)view.findViewById(R.id.input_nric2);

        final AlertDialog.Builder builder_gender = new AlertDialog.Builder(getActivity(),R.style.alerttheme);
        builder_gender.setTitle("Select your gender");
        final CharSequence gender[] = getResources().getTextArray(R.array.gender);
        builder_gender.setItems(gender, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on colors[which]
                input_gender.setText(gender[which]);

            }
        });


        final AlertDialog.Builder builder_marital = new AlertDialog.Builder(getActivity(),R.style.alerttheme);
        builder_marital.setTitle("Select your marital status");
        final CharSequence marital[] = getResources().getTextArray(R.array.marital_status);
        builder_marital.setItems(marital, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on colors[which]
                input_marital_status.setText(marital[which]);

            }
        });



        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.alerttheme);
        builder.setTitle("Select your nationality");
        final CharSequence race[] = CountryDetails.getCountry();
        builder.setItems(race, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on colors[which]
                input_nationality.setText(race[which]);

            }
        });


        final AlertDialog.Builder builder_service_area = new AlertDialog.Builder(getActivity(),R.style.alerttheme);
        builder_service_area.setTitle("Preferred service area");
        final CharSequence service_area_cont[] = getResources().getTextArray(R.array.service_area);
        builder_service_area.setItems(service_area_cont, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on colors[which]
                service_area.setText(service_area_cont[which]);

            }
        });

        service_area.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    builder_service_area.show();
                }
            }
        });
        service_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder_service_area.show();
            }
        });


        input_nationality.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    builder.show();
                }
            }
        });
        input_nationality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.show();
            }
        });

        input_marital_status.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    builder_marital.show();
                }
            }
        });
        input_marital_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder_marital.show();
            }
        });

        input_gender.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    builder_gender.show();
                }
            }
        });
        input_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder_gender.show();
            }
        });

        setDate(input_dob,view);
        return view;
    }



    public void setDate(final EditText input_dob,View view){
        input_dob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    DateDialog.getDate3(getActivity().getFragmentManager(), input_dob);
                }
            }
        });
        input_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DateDialog.getDate(getActivity().getFragmentManager(),input_dob);

            }
        });
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


    public errorMessage validateinput(){
        errorMessage.clear();
        if(fullname.getText().toString().equals(""))new errorMessage("Full Name is empty");
        if(nric.getText().toString().equals(""))new errorMessage("NRIC is empty");
        if(input_nationality.getText().toString().equals(""))new errorMessage("Nationality is empty");
        if(email.getText().toString().equals(""))new errorMessage("Email is empty");
        if(phonenum.getText().toString().equals(""))new errorMessage("Phone number is empty");
        if(input_dob.getText().toString().equals(""))new errorMessage("Date of Birth is empty");
        if(input_marital_status.getText().toString().equals(""))new errorMessage("Martial Status is empty");
        if(input_gender.getText().toString().equals(""))new errorMessage("Gender is empty");
        if(address.getText().toString().equals(""))new errorMessage("Address is empty");
        if(service_area.getText().toString().equals(""))new errorMessage("Service area is empty");
        if(!email.getText().toString().equals("")){
            if(!isValidEmailAddress(email.getText().toString()))new errorMessage("Incorrect email format");
        }
        return new errorMessage();
    }

    public  boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }


    public basicinfomation getData(){


        return new basicinfomation(fullname.getText().toString(),email.getText().toString(),input_nationality.getText().toString(),
                phonenum.getText().toString(),input_dob.getText().toString(),input_marital_status.getText().toString(),
                input_gender.getText().toString(), secphonenum.getText().toString(),address.getText().toString(),
                secaddress.getText().toString(),service_area.getText().toString(),nric.getText().toString()) ;


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
