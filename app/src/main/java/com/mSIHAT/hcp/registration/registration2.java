package com.mSIHAT.hcp.registration;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.plus.PlusOneButton;
import com.mSIHAT.hcp.R;
import com.mSIHAT.hcp.classes.certificationbody;
import com.mSIHAT.hcp.classes.errorMessage;
import com.mSIHAT.hcp.util.DateDialog;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link com.mSIHAT.hcp.registration.registration2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link com.mSIHAT.hcp.registration.registration2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class registration2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // The request code must be 0 or greater.
    private static final int PLUS_ONE_REQUEST_CODE = 0;
    // The URL to +1.  Must be a valid URL.
    private final String PLUS_ONE_URL = "http://developer.android.com";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private PlusOneButton mPlusOneButton;
    EditText input_exp_date,input_reg_body,input_reg_num,input_nric;
    EditText input_prac_type,input_renewal,input_pract,input_renewalperiod,input_language;
    TextView edu_list,emp_list;
    Fragment registration2;

    private OnFragmentInteractionListener mListener;

    public registration2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment registration2.
     */
    // TODO: Rename and change types and number of parameters
    public static com.mSIHAT.hcp.registration.registration2 newInstance(String param1, String param2) {
        com.mSIHAT.hcp.registration.registration2 fragment = new registration2();
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
        View view = inflater.inflate(R.layout.fragment_registration2, container, false);
        input_exp_date = (EditText)view.findViewById(R.id.input_exp_date);
        input_prac_type = (EditText)view.findViewById(R.id.input_prac_type);
        input_renewal = (EditText)view.findViewById(R.id.input_ren_per);
        registration2 = this;
        input_reg_body = (EditText)view.findViewById(R.id.input_reg_body_name);
        input_reg_num = (EditText)view.findViewById(R.id.input_reg_number);
        input_nric = (EditText)view.findViewById(R.id.input_nric);
        input_pract = (EditText)view.findViewById(R.id.input_emp_position);
        input_renewalperiod = (EditText)view.findViewById(R.id.input_ren_per);
        input_language = (EditText)view.findViewById(R.id.input_language);

        edu_list = (TextView)view.findViewById(R.id.textView12);
        emp_list = (TextView)view.findViewById(R.id.textView14);
        final AlertDialog.Builder builder_prac_type = new AlertDialog.Builder(getActivity(),R.style.alerttheme);
        builder_prac_type.setTitle("Select your practitioner type");
        final CharSequence prac[] = getResources().getTextArray(R.array.prac);
        builder_prac_type.setItems(prac, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on colors[which]
                input_prac_type.setText(prac[which]);

            }


        });

        edu_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FragmentManager manager = getActivity().getSupportFragmentManager();
                EducationalDialog alertDialogFragment = new EducationalDialog();
                alertDialogFragment.setTargetFragment(registration2,1);
                alertDialogFragment.show(manager, "ListEducation");
            }
        });

        emp_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FragmentManager manager = getActivity().getSupportFragmentManager();
                EmploymentLister alertDialogFragment = new EmploymentLister();
                alertDialogFragment.setTargetFragment(registration2,2);
                alertDialogFragment.show(manager, "ListEmployment");
            }
        });

        final AlertDialog.Builder builder_renewal = new AlertDialog.Builder(getActivity(),R.style.alerttheme);
        builder_renewal.setTitle("Select renewal type offered by the body");
        final CharSequence renewal[] = getResources().getTextArray(R.array.renewal_period);
        builder_renewal.setItems(renewal, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on colors[which]
                input_renewal.setText(renewal[which]);

            }


        });
        input_prac_type.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    builder_prac_type.show();
                }
            }
        });
        input_prac_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder_prac_type.show();
            }
        });

        input_renewal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    builder_renewal.show();
                }
            }
        });
        input_renewal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder_renewal.show();
            }
        });

        setDate(input_exp_date,view);
        return view;
    }

    public void setDate(final EditText input_dob,View view){
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
    public void onResume() {
        super.onResume();

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

    public errorMessage validateinput(){
        errorMessage.clear();
        if(input_prac_type.getText().toString().equals(""))new errorMessage("Practitioner type is empty");
        if(input_language.getText().toString().equals(""))new errorMessage("Language is empty");
        return new errorMessage();
    }



    public certificationbody getCertificationData(){
        return new certificationbody(input_reg_body.getText().toString(),input_reg_num.getText().toString(),input_nric.getText().toString(),input_prac_type.getText().toString(),
                input_renewal.getText().toString(),input_renewalperiod.getText().toString(),input_language.getText().toString());
    }



}
