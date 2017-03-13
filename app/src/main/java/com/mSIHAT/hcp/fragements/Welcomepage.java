package com.mSIHAT.hcp.fragements;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mSIHAT.hcp.R;
import com.mSIHAT.hcp.FirstPage;
import com.mSIHAT.hcp.LoginFragment;
import com.mSIHAT.hcp.RegistrationActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Welcomepage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Welcomepage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Welcomepage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Welcomepage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Welcomepage.
     */
    // TODO: Rename and change types and number of parameters
    public static Welcomepage newInstance(String param1, String param2) {
        Welcomepage fragment = new Welcomepage();
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
        View view = inflater.inflate(R.layout.fragment_welcomepage, container, false);
        TextView signup = (TextView)view.findViewById(R.id.textView36);
        Button signBtn = (Button)view.findViewById(R.id.signBtnr);
        Button gettingBtn = (Button)view.findViewById(R.id.gettingbr);
        gettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FirstPage)getActivity()).showlogin();
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.move, R.anim.moveout).hide(getActivity().getSupportFragmentManager().findFragmentByTag("welcomepage")).
                        add(R.id.firstpage, new GettingStartedFragment(), "gettingstarted").commit();
            }
        });
        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((FirstPage)getActivity()).opengallery();
               ((FirstPage)getActivity()).showlogin();
              getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.move, R.anim.moveout).hide(getActivity().getSupportFragmentManager().findFragmentByTag("welcomepage")).
                        add(R.id.firstpage, new LoginFragment(), "login").commit();




            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getBaseContext(), RegistrationActivity.class);
                getActivity().startActivity(intent);

                getActivity().overridePendingTransition(R.anim.move, R.anim.moveout);
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
