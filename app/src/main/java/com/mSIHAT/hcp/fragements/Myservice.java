package com.mSIHAT.hcp.fragements;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.mSIHAT.hcp.Central;
import com.mSIHAT.hcp.R;
import com.mSIHAT.hcp.classes.HCPAuthentication;
import com.mSIHAT.hcp.model.ScheduleSlotHolder;
import com.mSIHAT.hcp.model.myScheduleSlotListAdapter2;
import com.mSIHAT.hcp.model.refineSchedule;
import com.mSIHAT.hcp.pageaddapter.ServiceListAdapter;
import com.mSIHAT.hcp.util.TimeSlotUtil;
import com.mSIHAT.hcp.webapi.RestScheduleService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Activities that contain this fragment must implement the
 * {@link Myservice.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Myservice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Myservice extends DialogFragment {
    Activity a;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RestScheduleService scheduleService = new RestScheduleService();
    ServiceListAdapter ci;
    LinearLayout noservicefound;
   List<String> stringList = new ArrayList<>();
    List<HCPAuthentication> serviceares;
    Integer itemposition = 0;
    ListView listvi;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Myservice() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Myservice.
     */
    // TODO: Rename and change types and number of parameters
    public static Myservice newInstance(String param1, String param2) {
        Myservice fragment = new Myservice();
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
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.alerttheme2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_myservice, container, false);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.alerttheme2);
        Toolbar tool = (Toolbar)view.findViewById(R.id.toolbarprofile);
        tool.setTitle("My service area(s)");
        tool.setNavigationIcon(R.drawable.ic_back);
noservicefound = (LinearLayout)view.findViewById(R.id.noserviceareafound);
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tool.setOverflowIcon(getResources().getDrawable(R.drawable.ic_add_white, getActivity().getTheme()));
        } else {
            tool.setOverflowIcon(getResources().getDrawable(R.drawable.ic_add_white));
        }
*/
        tool.inflateMenu(R.menu.menu_my_service);

        tool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tool.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_add_new_service:
                        Log.e("in addp","in addp");


                        final android.app.AlertDialog.Builder builder_gender = new android.app.AlertDialog.Builder(getActivity(),R.style.alerttheme);
                        builder_gender.setTitle("Select your service area");
                        final CharSequence gender[] = stringList.toArray(new String[0]);

                        builder_gender.setItems(gender, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // the user clicked on colors[which]

                                addservicearea(String.valueOf(gender[which]));
                            }
                        });
                        builder_gender.show();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        listvi = (ListView)view.findViewById(R.id.list_servicearea);


        final AlertDialog.Builder builder_long = new AlertDialog.Builder(getActivity());
        final CharSequence renewal[] = getResources().getTextArray(R.array.serlongclick);
        builder_long.setItems(renewal, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stopservicearea(ci.getItem(itemposition).detail);

            }


        });


listvi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        itemposition = position;

        builder_long.show();

    }
});


        getserviceareas();
        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);



        if (context instanceof Activity){
            a=(Activity) context;
        }

    }


    public void addservicearea(String servicearea){

        Log.e("in my ser", "in get service");
        Call<Boolean> call = scheduleService.getService().addservicearea(servicearea,((Central)getActivity()).gethcp_id());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                int statusCode = response.code();
                Boolean responsefrom = response.body();
                String msg = "here";

                if (statusCode == 200) {

                    if(responsefrom){
                        getmyservicearea();

                    }else{

                        Toast.makeText(getActivity(),"Service could not be stopped, try again.",Toast.LENGTH_LONG).show();

                    }


                    //  progress.dismiss();
                }else{
                    Log.e("dfdf2", String.valueOf(statusCode));
                    msg="error again";
                    Toast.makeText(getActivity(),"request failed gone"+String.valueOf(statusCode),Toast.LENGTH_LONG).show();

                }
                Log.e("dfdf1", msg);

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                //   progress.dismiss();
                Toast.makeText(getActivity(),"request failed here gone",Toast.LENGTH_LONG).show();
                //   Log.e("dfdf", t.toString());
            }
        });

    }





    public void stopservicearea(int servicearea_id){

        Log.e("in my ser", "in get service");
        Call<Boolean> call = scheduleService.getService().stopservicearea(servicearea_id,((Central)getActivity()).gethcp_id());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                int statusCode = response.code();
                Boolean responsefrom = response.body();
                String msg = "here";

                if (statusCode == 200) {

                    if(responsefrom){
                        getmyservicearea();

                    }else{

                        Toast.makeText(getActivity(),"Service could not be stopped, try again.",Toast.LENGTH_LONG).show();

                    }


                    //  progress.dismiss();
                }else{
                    Log.e("dfdf2", String.valueOf(statusCode));
                    msg="error again";
                    Toast.makeText(getActivity(),"request failed gone"+String.valueOf(statusCode),Toast.LENGTH_LONG).show();

                }
                Log.e("dfdf1", msg);

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                //   progress.dismiss();
                Toast.makeText(getActivity(),"request failed here gone",Toast.LENGTH_LONG).show();
                //   Log.e("dfdf", t.toString());
            }
        });

    }


    public void getserviceareas(){

        Log.e("in my ser", "in get service");
        Call<List<HCPAuthentication>> call = scheduleService.getService().Getservicearea();
        call.enqueue(new Callback<List<HCPAuthentication>>() {
            @Override
            public void onResponse(Call<List<HCPAuthentication>> call, Response<List<HCPAuthentication>> response) {
                int statusCode = response.code();
                serviceares = response.body();
                String msg = "here";

                if (statusCode == 200) {

                    getmyservicearea();


                    //  progress.dismiss();
                }else{
                    Log.e("dfdf2", String.valueOf(statusCode));
                    msg="error again";
                    Toast.makeText(getActivity(),"request failed gone"+String.valueOf(statusCode),Toast.LENGTH_LONG).show();

                }
                Log.e("dfdf1", msg);

            }

            @Override
            public void onFailure(Call<List<HCPAuthentication>> call, Throwable t) {
                //   progress.dismiss();
                Toast.makeText(getActivity(),"request failed here gone",Toast.LENGTH_LONG).show();
                //   Log.e("dfdf", t.toString());
            }
        });

    }

    public void getmyservicearea(){

        if(scheduleService == null){
            scheduleService = new RestScheduleService();
        }
        Log.e("in my ser", "in get service");
        Call<List<HCPAuthentication>> call = scheduleService.getService().Getmyservicearea(((Central)getActivity()).gethcp_id());
        call.enqueue(new Callback<List<HCPAuthentication>>() {
            @Override
            public void onResponse(Call<List<HCPAuthentication>> call, Response<List<HCPAuthentication>> response) {
                int statusCode = response.code();
                final List<HCPAuthentication> responsefrom = response.body();
                String msg = "here";

                if (statusCode == 200) {

                    if(responsefrom.size() > 0){
                        noservicefound.setVisibility(View.GONE);
                        ci = new ServiceListAdapter(a,responsefrom);

                    listvi.setAdapter(ci);
                        stringList = new ArrayList<String>();
                        new Thread(new Runnable() {
                            public void run() {
                                Boolean check = false;
                              for(int i =0;i<serviceares.size();i++){
                                  check = false;
                                  for(int j = 0; j < responsefrom.size();j++){

                                      if(responsefrom.get(j).hcp_url.equals(serviceares.get(i).hcp_url)){
                                          check = true;
                                          break;
                                      }

                                  }
                                  if(!check){
                                      stringList.add(serviceares.get(i).hcp_url);
                                  }


                              }
                            }
                        }).start();

                        Log.e("check", "get the list");

                    }else{
                        noservicefound.setVisibility(View.VISIBLE);
                        Log.e("check", "less than ero");

                    }


                    //  progress.dismiss();
                }else{
                    Log.e("dfdf2", String.valueOf(statusCode));
                    msg="error again";
                    Toast.makeText(getActivity(),"request failed gone"+String.valueOf(statusCode),Toast.LENGTH_LONG).show();

                }
                Log.e("dfdf1", msg);

            }

            @Override
            public void onFailure(Call<List<HCPAuthentication>> call, Throwable t) {
                //   progress.dismiss();
                Toast.makeText(getActivity(),"request failed here gone",Toast.LENGTH_LONG).show();
                //   Log.e("dfdf", t.toString());
            }
        });

    }

}
