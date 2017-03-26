package com.mSIHAT.hcp.fragements;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import com.mSIHAT.hcp.Central;
import com.mSIHAT.hcp.R;
import com.mSIHAT.hcp.classes.Equipmentdetails;
import com.mSIHAT.hcp.model.EmploymentClass;
import com.mSIHAT.hcp.util.Date;
import com.mSIHAT.hcp.util.DateDialog;
import com.mSIHAT.hcp.webapi.RestScheduleService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyequipmentAU.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyequipmentAU#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyequipmentAU extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Equipmentdetails Currentitem;
    private RestScheduleService scheduleService = new RestScheduleService();
    // TODO: Rename and change types of parameters
    private ArrayList<Equipmentdetails> mParam1;
    private String mParam2;
    List<String> stringList = new ArrayList<>();
    private OnFragmentInteractionListener mListener;
    EditText input_prac_type,input_prac_price,input_prac_quotation;
    Button Cancel,Confirm;
    public MyequipmentAU() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MyequipmentAU.
     */
    // TODO: Rename and change types and number of parameters
    public static MyequipmentAU newInstance(ArrayList<Equipmentdetails> equipments) {
        MyequipmentAU fragment = new MyequipmentAU();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, equipments);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelableArrayList(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_myequipment_au, container, false);
        Cancel = (Button)view.findViewById(R.id.btn_eqp_cancel);
        Confirm = (Button)view.findViewById(R.id.btn_eqp_ok);
        input_prac_type = (EditText)view.findViewById(R.id.input_ins_name);
        input_prac_price  = (EditText)view.findViewById(R.id.inst1);
        input_prac_quotation  = (EditText)view.findViewById(R.id.inst2);
        getDialog().requestWindowFeature(STYLE_NO_TITLE);
        setCancelable(false);

        stringList = new ArrayList<String>();
        new Thread(new Runnable() {
            public void run() {
                Boolean check = false;
                for(int i =0;i<mParam1.size();i++){
                    check = false;
                 /*   for(int j = 0; j < responsefrom.size();j++){

                        if(responsefrom.get(j).hcp_url.equals(serviceares.get(i).hcp_url)){
                            check = true;
                            break;
                        }

                    }
                    */
                    //if(!check){
                        stringList.add(mParam1.get(i).name);
                   // }
//

                }
            }
        }).start();




        input_prac_type.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    final AlertDialog.Builder builder_renewal = new AlertDialog.Builder(getActivity(),R.style.alerttheme);
                    builder_renewal.setTitle("Select Equipment");



                    final CharSequence renewal[] = stringList.toArray(new String[0]);
                    builder_renewal.setItems(renewal, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // the user clicked on colors[which]
                            input_prac_type.setText(renewal[which]);
                            Currentitem = mParam1.get(which);
                            input_prac_price.setText("RM"+String.valueOf(mParam1.get(which).price));
                            Log.e("equipment select",Currentitem.name);
                        }


                    });

                    builder_renewal.show();
                }
            }
        });
        input_prac_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder_renewal = new AlertDialog.Builder(getActivity(),R.style.alerttheme);
                builder_renewal.setTitle("Select Equipment");



                final CharSequence renewal[] = stringList.toArray(new String[0]);
                builder_renewal.setItems(renewal, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // the user clicked on colors[which]
                        input_prac_type.setText(renewal[which]);
                        Currentitem = mParam1.get(which);
                        Log.e("equipment select",Currentitem.name);
                        input_prac_price.setText("RM"+String.valueOf(mParam1.get(which).price));


                    }


                });

                builder_renewal.show();
            }
        });

Cancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        dismiss();
    }
});

        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Float.parseFloat(input_prac_quotation.getText().toString()) > Currentitem.price) {


                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setMessage("Your quotation cannot be more than the recommeded price which is RM" + String.valueOf(Currentitem.price))
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .show();
                }else{
                    Log.e("in my ser", "in get service");
                    // ((FirstPage)getActivity()).opengallery();
                    final AlertDialog progressDialog = new SpotsDialog(getActivity(), R.style.CustomDialog4);
// To dismiss the dialog
                    progressDialog.show();

                    Call<Boolean> call = scheduleService.getService().setmyequipments(((Central)getActivity()).gethcp_id(),Currentitem.id,input_prac_quotation.getText().toString());
                    call.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            int statusCode = response.code();

                            String msg = "here";
                            progressDialog.dismiss();


                            if (statusCode == 200) {




                                dismiss();
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
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if (d!=null){
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;

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
}
