package com.mSIHAT.hcp.fragements;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mSIHAT.hcp.R;
import com.mSIHAT.hcp.Central;
import com.mSIHAT.hcp.db.DBAppRequest;
import com.mSIHAT.hcp.db.dbclasses.AppReqTra;
import com.mSIHAT.hcp.model.AppRequest;
import com.mSIHAT.hcp.pageaddapter.MyPageAdapter;
import com.mSIHAT.hcp.webapi.RestUserService;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AppRequestContain.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AppRequestContain#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppRequestContain extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RestUserService restUserService = new RestUserService();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ViewPager vpPager;
    DBAppRequest db ;
    List<Fragment> list;
    private OnFragmentInteractionListener mListener;
    FragmentPagerAdapter adapterViewPager;
    public AppRequestContain() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppRequestContain.
     */
    // TODO: Rename and change types and number of parameters
    public static AppRequestContain newInstance(String param1, String param2) {
        AppRequestContain fragment = new AppRequestContain();
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
       View view = inflater.inflate(R.layout.fragment_app_request_contain, container, false);

        Toolbar tool = (Toolbar)view.findViewById(R.id.toolbarrequest);

        // tool.setOverflowIcon(getResources().getDrawable(R.drawable.arrowdown, getActivity().getTheme()));
        tool.setTitle("Appointment Requests");
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

        vpPager = (ViewPager) view.findViewById(R.id.vpPager);
        getRequests(((Central)getActivity()).gethcp_id());





        return view;
    }


    public void getRequests(int hcp_id){
        final AlertDialog progressDialog = new SpotsDialog(getContext(), R.style.CustomDialog);
// To dismiss the dialog
        progressDialog.show();

        Call<List<AppRequest>> call = restUserService.getService().GetRequests(hcp_id);
        call.enqueue(new Callback<List<AppRequest>>() {
            @Override
            public void onResponse(Call<List<AppRequest>> call, Response<List<AppRequest>> response) {
                int statusCode = response.code();
                List<AppRequest> userAppiontments = response.body();
                String msg = "here";

                List<AppReqTra> transformedRequest = new ArrayList<>();

                if (statusCode == 200) {


                    if(userAppiontments.size() > 0){

                     for(int j = 0;j < userAppiontments.size();j++){
                         AppReqTra newappreq = new AppReqTra(userAppiontments.get(j));
                         transformedRequest.add(newappreq);

                     }
                        setappiontments(transformedRequest);
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
            public void onFailure(Call<List<AppRequest>> call, Throwable t) {
                //   progress.dismiss();
                progressDialog.hide();
                Toast.makeText(getActivity(),t.toString(),Toast.LENGTH_LONG).show();

                //   Log.e("dfdf", t.toString());
            }
        });



    }


    public void setappiontments(List<AppReqTra> appiontments){

        list = new ArrayList<Fragment>();
        for(int k =0;k<appiontments.size();k++){


            list.add(AppReqFragement.newInstance(appiontments.get(k),(k+1), String.valueOf((k+1))+"/"+String.valueOf((appiontments.size()))));
        }


        adapterViewPager = new MyPageAdapter(getActivity().getSupportFragmentManager(),list);

        vpPager.setAdapter(adapterViewPager);

    }


    public void removefragment(int position){

       list.remove(position) ;
        adapterViewPager = new MyPageAdapter(getActivity().getSupportFragmentManager(),list);

        vpPager.setAdapter(null);
        vpPager.setAdapter(adapterViewPager);


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
