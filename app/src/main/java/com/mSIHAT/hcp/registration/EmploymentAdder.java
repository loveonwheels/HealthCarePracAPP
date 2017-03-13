package com.mSIHAT.hcp.registration;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
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

import com.mSIHAT.hcp.R;
import com.mSIHAT.hcp.model.EmploymentClass;
import com.mSIHAT.hcp.util.Date;
import com.mSIHAT.hcp.util.DateDialog;

import java.text.ParseException;
import java.util.Calendar;

/**
 * Created by ghost on 13/1/16.
 */
public class EmploymentAdder extends DialogFragment {
    EditText empN,position;
    EditText startDate,endDate;
    Button ok,cancel;
    Button btnClose,btnSave,btnCan,btnDelete;
    private DatePicker dpResult;


    private Date startD ;
    private Date endD;

    private int year;
    private int month;
    private int day;

    private int mYear;
    private int mMonth;
    private int mDay;
    TableLayout editbtn,savelay,oklay;
    Button btnEdit;
    private int pos;
    Boolean showEdit;
    Boolean change = false;
    RelativeLayout main;
    static final int DATE_DIALOG_ID = 999;

    Bundle mArgs;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArgs = getArguments();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.employmentadder, container);
        getDialog().requestWindowFeature(STYLE_NO_TITLE);
        setCancelable(false);
        dpResult = new DatePicker(getActivity());
        startDate = (EditText)view.findViewById(R.id.input_emp_start_date);
        endDate = (EditText)view.findViewById(R.id.input_emp_to_date);
        main = (RelativeLayout)view.findViewById(R.id.mainlayb);

empN = (EditText)view.findViewById(R.id.input_emp_name);
        position  = (EditText)view.findViewById(R.id.input_emp_position);

        editbtn = (TableLayout)view.findViewById(R.id.tableLayout6);
        oklay = (TableLayout)view.findViewById(R.id.tableLayout4);
        savelay = (TableLayout)view.findViewById(R.id.tableLayout5);

        ok = (Button) view.findViewById(R.id.button17);
        cancel = (Button) view.findViewById(R.id.button18);
        btnEdit= (Button) view.findViewById(R.id.button6b);

        btnClose = (Button)view.findViewById(R.id.button8b);
        btnSave = (Button)view.findViewById(R.id.button4b);
        btnCan = (Button)view.findViewById(R.id.button5b);
        btnDelete = (Button)view.findViewById(R.id.button7b);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();

            }
        });

        setDate(startDate, view);

        setDate(endDate,view);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if((empN.getText().toString().equals("") )|| (startDate.getText().toString().equals("") ) ||  endDate.getText().toString().equals("") || position.getText().toString().equals(""))
                {
                    Toast.makeText(getActivity(),"Please fill every section",Toast.LENGTH_LONG).show();
                }else{
                    Fragment prev = getFragmentManager().findFragmentByTag("ListEmployment");
                    if (prev != null) {

                        EmploymentLister df = (EmploymentLister) prev;
                        df.attachInfo(new EmploymentClass(empN.getText().toString(), startDate.getText().toString(), endDate.getText().toString(), position.getText().toString()));
                    }
                    dismiss();
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();

            }
        });



    /*    universityName = (EditText) view.findViewById(R.id.editText9);
        award = (EditText) view.findViewById(R.id.editText11);

       universityName.requestFocus();


        */

        if(mArgs != null){


            startDate.setText(mArgs.getString("startDate"));
            endDate.setText(mArgs.getString("endDate"));
            empN.setText(mArgs.getString("empName"));
            position.setText(mArgs.getString("empPos"));
            pos = mArgs.getInt("position");
            showEdit = mArgs.getBoolean("showEdit");

            if(showEdit){
                oklay.setVisibility(View.GONE);
                editbtn.setVisibility(View.GONE);
                savelay.setVisibility(View.VISIBLE);
            }else{
                startDate.setEnabled(false);
                endDate.setEnabled(false);
                empN.setEnabled(false);
                position.setEnabled(false);
                oklay.setVisibility(View.GONE);
                editbtn.setVisibility(View.VISIBLE);
            }


            startDate.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    changeCheck();
                    return false;
                }
            });

            endDate.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    changeCheck();
                    return false;
                }
            });

            empN.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    changeCheck();
                    return false;
                }
            });
            empN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeCheck();
                }
            });

            position.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    changeCheck();
                    return false;
                }
            });

            setCancelable(true);
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                change = false;

                startDate.setEnabled(true);
                endDate.setEnabled(true);
                empN.setEnabled(true);
                position.setEnabled(true);
                setCancelable(false);
                editbtn.animate()
                        .translationX(-main.getWidth())
                        .setDuration(400)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                editbtn.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationStart(Animator animation){
                                super.onAnimationStart(animation);

                                savelay.animate()
                                        .translationX(0)
                                        .setDuration(400)
                                        .setListener(new AnimatorListenerAdapter() {
                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                super.onAnimationEnd(animation);
                                                savelay.setVisibility(View.VISIBLE);
                                            }

                                            @Override
                                            public void onAnimationStart(Animator animation) {
                                                super.onAnimationStart(animation);
                                                savelay.setVisibility(View.VISIBLE);
                                            }
                                        });
                            }
                        });


            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment prev = getFragmentManager().findFragmentByTag("ListEmployment");
                if (prev != null) {

                    EmploymentLister df = (EmploymentLister) prev;
                    try {
                        startD = new Date(startDate.getText().toString());
                        endD = new Date(endDate.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    df.saveInfo(empN.getText().toString(), startD, endD, position.getText().toString(),pos);
                }
                dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Confirm action");
                builder.setMessage("Are you sure you want to delete this record ?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog

                        Fragment prev = getFragmentManager().findFragmentByTag("ListEmployment");
                        if (prev != null) {

                            EmploymentLister df = (EmploymentLister) prev;

                            df.deleteInfo(pos);
                        }
                        dialog.dismiss();
                        closeDismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        btnCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(change){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setCancelable(false);
                    builder.setTitle("Confirm action");
                    builder.setMessage("Are you sure you want to cancel ?, changings made will not be saved");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog

                            dialog.dismiss();
                            closeDismiss();
                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // Do nothing
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }else {
                    dismiss();
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

    public void changeCheck(){
        if(change == false){
            change = true;
        }
    }
    // display current date
    public void setCurrentDateOnView() {


        // dpResult = (DatePicker) findViewById(R.id.dpResult);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
      /*  tvDisplayDate.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" "));
*/
        // set current date into datepicker
        dpResult.init(year, month, day, null);

    }

    public void closeDismiss(){
        dismiss();
    }

    public void addListenerOnButton() {


        startDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                startDate.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);


                            }
                        }, mYear, mMonth, mDay);
                dpd.show();
            }

        });

        endDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                endDate.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);


                            }
                        }, mYear, mMonth, mDay);
                dpd.show();
            }
        });

    }


    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(getActivity(), datePickerListener, year, month,
                        day);
        }
        return null;
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
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into textview
            startDate.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));

            // set selected date into datepicker also
            dpResult.init(year, month, day, null);

        }
    };


}
