package com.lovecareworks.healthcarepersonnel.fragements;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.lovecareworks.healthcarepersonnel.Calendar.CalendarController;
import com.lovecareworks.healthcarepersonnel.Central;
import com.lovecareworks.healthcarepersonnel.R;
import com.lovecareworks.healthcarepersonnel.db.dbclasses.ScheduleTra;
import com.lovecareworks.healthcarepersonnel.dialogs.schedule_dialog;
import com.lovecareworks.healthcarepersonnel.model.Appiontments;
import com.lovecareworks.healthcarepersonnel.model.ScheduleSlotHolder;
import com.lovecareworks.healthcarepersonnel.model.myScheduleSlot;
import com.lovecareworks.healthcarepersonnel.model.myScheduleSlot2;
import com.lovecareworks.healthcarepersonnel.model.myScheduleSlotListAdapter;
import com.lovecareworks.healthcarepersonnel.model.myScheduleSlotListAdapter2;
import com.lovecareworks.healthcarepersonnel.model.refineSchedule;
import com.lovecareworks.healthcarepersonnel.util.TimeSlotUtil;
import com.lovecareworks.healthcarepersonnel.webapi.RestPractitionerService;
import com.lovecareworks.healthcarepersonnel.webapi.RestScheduleService;
import com.lovecareworks.healthcarepersonnel.webapi.RestUserService;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Schedule extends Fragment {

    int item_position = 0;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ProgressDialog progressDialog;
    Button btnToday;
int activeday = 0;
    private RestPractitionerService restPracService = new RestPractitionerService();
    myScheduleSlotListAdapter2 ci;
    private RestScheduleService scheduleService = new RestScheduleService();
    List<myScheduleSlot2> userAppiontments;
    LinearLayout calHeader;
    LinearLayout calHeader2,calHeader3;

    LinearLayout cal_mon_cont,cal_mon_det,cal_tue_cont,cal_tue_det,cal_wed_cont,cal_wed_det,cal_thu_cont,cal_thu_det,cal_fri_cont,cal_fri_det;
    LinearLayout cal_sat_cont,cal_sat_det,cal_sun_det,cal_sun_cont;

    SwipeRefreshLayout mySwipeRefreshLayout;

    TableLayout WeekTable,WeekTable2,WeekTable3,weekdetails,headerpanel;
    boolean showweekView = true;
    CalendarController calendarController;
   TextView displayweeknum ;
  TextView displayweek;

TextView hidepanel,showpanel;
   TextView displayweeknum2 ;
 TextView displayweek2 ;


   TextView displayweeknum3 ;
   TextView displayweek3 ;
   ImageView btnNext,btnPrevious;

    TextView mon_num , mon_month, tue_num , tue_month ,wed_num,wed_month ;
   TextView thu_num ,thu_month ,fri_num ,fri_month ;
 TextView sat_num , sat_month ,sun_num , sun_month ;


    TextView cal_mon_c , cal_tue_c , cal_wed_c, cal_thu_c ,cal_fri_c ,cal_sat_c ,cal_sun_c ;

    ListView listvi;
    Schedule thisfragement;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RestUserService restUserService = new RestUserService();
    private OnFragmentInteractionListener mListener;


    public Schedule() {
        // Required empty public constructor
    }


    public static Schedule newInstance(String param1, String param2) {
        Schedule fragment = new Schedule();
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
        View view = inflater.inflate(R.layout.myschedule2, container, false);
        Toolbar tool = (Toolbar)view.findViewById(R.id.toolbar);
        calendarController = new CalendarController();
        weekdetails = (TableLayout)view.findViewById(R.id.Cal_week_days_rows) ;

        FloatingActionButton floatingactBtn = (FloatingActionButton)view.findViewById(R.id.add_schedule);
         thisfragement = this;
         mon_num = (TextView)view.findViewById(R.id.mon_num);
         mon_month = (TextView)view.findViewById(R.id.mon_month);

         tue_num = (TextView)view.findViewById(R.id.tue_num);
         tue_month = (TextView)view.findViewById(R.id.tue_month);

        wed_num = (TextView)view.findViewById(R.id.wed_num);
        wed_month = (TextView)view.findViewById(R.id.wed_month);

         thu_num = (TextView)view.findViewById(R.id.thu_num);
         thu_month = (TextView)view.findViewById(R.id.thu_month);
        mySwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swiperefresh);
       fri_num = (TextView)view.findViewById(R.id.fri_num);
       fri_month = (TextView)view.findViewById(R.id.fri_month);

     sat_num = (TextView)view.findViewById(R.id.sat_num);
       sat_month = (TextView)view.findViewById(R.id.sat_month);
   sun_num = (TextView)view.findViewById(R.id.sun_num);
         sun_month = (TextView)view.findViewById(R.id.sun_month);

        cal_mon_c = (TextView)view.findViewById(R.id.Cal_mon_c);
        cal_tue_c = (TextView)view.findViewById(R.id.Cal_tue_c);
        cal_wed_c = (TextView)view.findViewById(R.id.Cal_wed_c);
        cal_thu_c = (TextView)view.findViewById(R.id.Cal_thu_c);
        cal_fri_c = (TextView)view.findViewById(R.id.Cal_fri_c);
        cal_sat_c = (TextView)view.findViewById(R.id.Cal_sat_c);
        cal_sun_c = (TextView)view.findViewById(R.id.Cal_sun_c);



        cal_mon_cont =  (LinearLayout) view.findViewById(R.id.Cal_mon_cont);
        cal_mon_det =  (LinearLayout) view.findViewById(R.id.Cal_mon_det);
        cal_tue_cont =  (LinearLayout) view.findViewById(R.id.Cal_tue_cont);
        cal_tue_det =  (LinearLayout) view.findViewById(R.id.Cal_tue_det);

        cal_wed_cont =  (LinearLayout) view.findViewById(R.id.Cal_wed_cont);
        cal_wed_det =  (LinearLayout) view.findViewById(R.id.Cal_wed_det);
        cal_thu_cont =  (LinearLayout) view.findViewById(R.id.Cal_thu_cont);
        cal_thu_det =  (LinearLayout) view.findViewById(R.id.Cal_thu_det);

        cal_fri_cont =  (LinearLayout) view.findViewById(R.id.Cal_fri_cont);
        cal_fri_det =  (LinearLayout) view.findViewById(R.id.Cal_fri_det);
        cal_sat_cont =  (LinearLayout) view.findViewById(R.id.Cal_sat_cont);
        cal_sat_det =  (LinearLayout) view.findViewById(R.id.Cal_sat_det);

        cal_sun_cont =  (LinearLayout) view.findViewById(R.id.Cal_sun_cont);
        cal_sun_det =  (LinearLayout) view.findViewById(R.id.Cal_sun_det);


               headerpanel = (TableLayout)view.findViewById(R.id. Cal_header_panel) ;

     displayweeknum = (TextView)view.findViewById(R.id.txtDisplayweekNum2);
   displayweek = (TextView)view.findViewById(R.id.txtDisplayweek2);
  displayweeknum2 = (TextView)view.findViewById(R.id.txtDisplayweekNum);
       displayweek2 = (TextView)view.findViewById(R.id.txtDisplayweek);
        displayweeknum3 = (TextView)view.findViewById(R.id.txtDisplayweekNum3);
      displayweek3 = (TextView)view.findViewById(R.id.txtDisplayweek3);
hidepanel = (TextView)view.findViewById(R.id.Cal_btn_hide_panel);
        showpanel = (TextView)view.findViewById(R.id.Cal_btn_show_panel);
        calHeader = (LinearLayout)view.findViewById(R.id.Cal_header);
        calHeader2 = (LinearLayout)view.findViewById(R.id.Cal_header_2);
        calHeader3 = (LinearLayout)view.findViewById(R.id.Cal_header_3);
      btnNext = (ImageView)view.findViewById(R.id.Cal_btn_next_week);
        btnPrevious = (ImageView)view.findViewById(R.id.Cal_btn_previous_week);
        hidepanel.setOnClickListener(showhide());
        showpanel.setOnClickListener(showhide());
        setWeekplaceHeader();

        calHeader2.setVisibility(View.GONE);

        setDayClick();
       // tool.setOverflowIcon(getResources().getDrawable(R.drawable.arrowdown, getActivity().getTheme()));
tool.setTitle("My Appointments");
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

        floatingactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                PublishScheduleFragment alertDialogFragment = new PublishScheduleFragment();
                alertDialogFragment.setTargetFragment(thisfragement,14414);
                alertDialogFragment.show(manager, "publishSchedule");
            }
        });

        tool.inflateMenu(R.menu.schedule_menu);
        tool.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.action_myschedule){

                    //i dont know why am writing this

                }
                return false;
            }
        });


        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        loadSchedule();
                    }
                }
        );



        mySwipeRefreshLayout.setColorSchemeColors(Color.parseColor("#8b1f6f"), Color.parseColor("#8b1f6f"), Color.parseColor("#8b1f6f"), Color.parseColor("#8b1f6f"));


        listvi = (ListView)view.findViewById(R.id.schedule_list);


        final AlertDialog.Builder builder_long = new AlertDialog.Builder(getActivity());
        final CharSequence renewal[] = getResources().getTextArray(R.array.reglongclick);
        builder_long.setItems(renewal, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
         deleteBlock(item_position);

            }


        });




        listvi.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                item_position = position;
                builder_long.show();
                return true;
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calHeader3.setVisibility(View.VISIBLE);

                calHeader3.animate()
                        .translationY(0).setDuration(300)
                        .withStartAction(new Runnable() {
                            @Override
                            public void run() {
                              //  WeekTable2.setVisibility(View.VISIBLE);
                                if(showweekView)
                                    calendarController.nextweek();
                                else
                                    calendarController.nextday();





/*
                                WeekTable2.animate().translationX(0).setDuration(300).setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
/*


                                        mon_num.setText(calendarController.daydetails[0].getDay());
                                        mon_month.setText(calendarController.daydetails[0].getMonth());

                                        tue_num.setText(calendarController.daydetails[1].getDay());
                                        tue_month.setText(calendarController.daydetails[1].getMonth());

                                        wed_num.setText(calendarController.daydetails[2].getDay());
                                        wed_month.setText(calendarController.daydetails[2].getMonth());


                                        thu_num.setText(calendarController.daydetails[3].getDay());
                                        thu_month.setText(calendarController.daydetails[3].getMonth());


                                        fri_num.setText(calendarController.daydetails[4].getDay());
                                        fri_month.setText(calendarController.daydetails[4].getMonth());

                                        sat_num.setText(calendarController.daydetails[5].getDay());
                                        sat_month.setText(calendarController.daydetails[5].getMonth());


                                        sun_num.setText(calendarController.daydetails[6].getDay());
                                        sun_month.setText(calendarController.daydetails[6].getMonth());

                                        WeekTable2.setVisibility(View.GONE);
                                        WeekTable2.animate()
                                                .translationX(-1*(WeekTable2.getWidth())).setDuration(0);

                                        mon_num2.setText(calendarController.Nxtdaydetails[0].getDay());
                                        mon_month2.setText(calendarController.Nxtdaydetails[0].getMonth());

                                        tue_num2.setText(calendarController.Nxtdaydetails[1].getDay());
                                        tue_month2.setText(calendarController.Nxtdaydetails[1].getMonth());

                                        wed_num2.setText(calendarController.Nxtdaydetails[2].getDay());
                                        wed_month2.setText(calendarController.Nxtdaydetails[2].getMonth());


                                        thu_num2.setText(calendarController.Nxtdaydetails[3].getDay());
                                        thu_month2.setText(calendarController.Nxtdaydetails[3].getMonth());


                                        fri_num2.setText(calendarController.Nxtdaydetails[4].getDay());
                                        fri_month2.setText(calendarController.Nxtdaydetails[4].getMonth());

                                        sat_num2.setText(calendarController.Nxtdaydetails[5].getDay());
                                        sat_month2.setText(calendarController.Nxtdaydetails[5].getMonth());


                                        sun_num2.setText(calendarController.Nxtdaydetails[6].getDay());
                                        sun_month2.setText(calendarController.Nxtdaydetails[6].getMonth());



                                    }

                                    @Override
                                    public void onAnimationStart(Animator animation) {
                                        super.onAnimationStart(animation);
                                    }
                                });

                                */
                            }
                        })
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);

                                if(!showweekView){

                                    displayweeknum.setText(calendarController.currentDay.getdateinstring());
                                    displayweek.setText(calendarController.currentDay.getDayinfull());

                                    displayweeknum3.setText(calendarController.nextDay.getdateinstring());
                                    displayweek3.setText(calendarController.nextDay.getDayinfull());


                                    displayweeknum2.setText(calendarController.previousDay.getdateinstring());
                                    displayweek2.setText(calendarController.previousDay.getDayinfull());

                                }else{

                                    displayweeknum.setText(calendarController.getDisplayWeekNum());
                                    displayweek.setText(calendarController.getDisplayWeekDis());

                                    displayweeknum2.setText(calendarController.getPreDisplayWeekNum());
                                    displayweek2.setText(calendarController.getPreDisplayWeekDis());


                                    displayweeknum3.setText(calendarController.getNxtDisplayWeekNum());
                                    displayweek3.setText(calendarController.getNxtDisplayWeekDis());
                                }


                                mon_num.setText(calendarController.daydetails[0].getDay());
                                mon_month.setText(calendarController.daydetails[0].getMonth());

                                tue_num.setText(calendarController.daydetails[1].getDay());
                                tue_month.setText(calendarController.daydetails[1].getMonth());

                                wed_num.setText(calendarController.daydetails[2].getDay());
                                wed_month.setText(calendarController.daydetails[2].getMonth());


                                thu_num.setText(calendarController.daydetails[3].getDay());
                                thu_month.setText(calendarController.daydetails[3].getMonth());


                                fri_num.setText(calendarController.daydetails[4].getDay());
                                fri_month.setText(calendarController.daydetails[4].getMonth());

                                sat_num.setText(calendarController.daydetails[5].getDay());
                                sat_month.setText(calendarController.daydetails[5].getMonth());


                                sun_num.setText(calendarController.daydetails[6].getDay());
                                sun_month.setText(calendarController.daydetails[6].getMonth());

                                calHeader3.setVisibility(View.GONE);
                                calHeader3.animate()
                                        .translationY(-1*(calHeader3.getHeight())).setDuration(0)
                                        .setListener(new AnimatorListenerAdapter() {
                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                super.onAnimationEnd(animation);
/*
                                                mon_num.setText(calendarController.daydetails[0].getDay());
                                                mon_month.setText(calendarController.daydetails[0].getMonth());

                                                tue_num.setText(calendarController.daydetails[1].getDay());
                                                tue_month.setText(calendarController.daydetails[1].getMonth());

                                                wed_num.setText(calendarController.daydetails[2].getDay());
                                                wed_month.setText(calendarController.daydetails[2].getMonth());


                                                thu_num.setText(calendarController.daydetails[3].getDay());
                                                thu_month.setText(calendarController.daydetails[3].getMonth());


                                                fri_num.setText(calendarController.daydetails[4].getDay());
                                                fri_month.setText(calendarController.daydetails[4].getMonth());

                                                sat_num.setText(calendarController.daydetails[5].getDay());
                                                sat_month.setText(calendarController.daydetails[5].getMonth());


                                                sun_num.setText(calendarController.daydetails[6].getDay());
                                                sun_month.setText(calendarController.daydetails[6].getMonth());

*/
                                                if(!showweekView){
                                                    displayweeknum3.setText(calendarController.nextDay.getdateinstring());
                                                    displayweek3.setText(calendarController.nextDay.getDayinfull());

                                                    try {
                                                        ci = new myScheduleSlotListAdapter2(getActivity(), refineSchedule.refine(userAppiontments,calendarController.currentDay.getDayDate()));
                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }
                                                    listvi.setAdapter(ci);

                                                    if(calendarController.getcurrentdayvalue() == 0){

                                                        loadSchedule();
                                                    }
                                                }else{

                                                    displayweeknum3.setText(calendarController.getNxtDisplayWeekNum());
                                                    displayweek3.setText(calendarController.getNxtDisplayWeekDis());
                                                    loadSchedule();

                                                }
                                            }
                                        });

                            }
                        });







            }
        });





        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calHeader2.setVisibility(View.VISIBLE);
                calHeader.animate()
                        .translationY(-1*(calHeader.getHeight())).setDuration(300)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);


                                if(showweekView)
                                calendarController.previousweek();
                                else
                                    calendarController.prevday();

                                displayweeknum.setText(calendarController.getDisplayWeekNum());
                                displayweek.setText(calendarController.getDisplayWeekDis());

                                calHeader.animate().translationY(0).setDuration(0)
                                        .setListener(new AnimatorListenerAdapter() {
                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                super.onAnimationEnd(animation);
                                                calHeader.setVisibility(View.VISIBLE);
                                                calHeader2.setVisibility(View.GONE);
                                                displayweeknum2.setText(calendarController.getPreDisplayWeekNum());
                                                displayweek2.setText(calendarController.getPreDisplayWeekDis());


                                                if(!showweekView){

                                                    displayweeknum.setText(calendarController.currentDay.getdateinstring());
                                                    displayweek.setText(calendarController.currentDay.getDayinfull());

                                                    displayweeknum3.setText(calendarController.nextDay.getdateinstring());
                                                    displayweek3.setText(calendarController.nextDay.getDayinfull());


                                                    displayweeknum2.setText(calendarController.previousDay.getdateinstring());
                                                    displayweek2.setText(calendarController.previousDay.getDayinfull());

                                                    try {
                                                        ci = new myScheduleSlotListAdapter2(getActivity(), refineSchedule.refine(userAppiontments,calendarController.currentDay.getDayDate()));
                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }
                                                    listvi.setAdapter(ci);


                                                    if(calendarController.getcurrentdayvalue() == 6){

                                                        loadSchedule();
                                                    }

                                                }else{

                                                    displayweeknum.setText(calendarController.getDisplayWeekNum());
                                                    displayweek.setText(calendarController.getDisplayWeekDis());

                                                    displayweeknum2.setText(calendarController.getPreDisplayWeekNum());
                                                    displayweek2.setText(calendarController.getPreDisplayWeekDis());


                                                    displayweeknum3.setText(calendarController.getNxtDisplayWeekNum());
                                                    displayweek3.setText(calendarController.getNxtDisplayWeekDis());

                                                    loadSchedule();
                                                }

                                                mon_num.setText(calendarController.daydetails[0].getDay());
                                                mon_month.setText(calendarController.daydetails[0].getMonth());

                                                tue_num.setText(calendarController.daydetails[1].getDay());
                                                tue_month.setText(calendarController.daydetails[1].getMonth());

                                                wed_num.setText(calendarController.daydetails[2].getDay());
                                                wed_month.setText(calendarController.daydetails[2].getMonth());


                                                thu_num.setText(calendarController.daydetails[3].getDay());
                                                thu_month.setText(calendarController.daydetails[3].getMonth());


                                                fri_num.setText(calendarController.daydetails[4].getDay());
                                                fri_month.setText(calendarController.daydetails[4].getMonth());

                                                sat_num.setText(calendarController.daydetails[5].getDay());
                                                sat_month.setText(calendarController.daydetails[5].getMonth());


                                                sun_num.setText(calendarController.daydetails[6].getDay());
                                                sun_month.setText(calendarController.daydetails[6].getMonth());


                                            }
                                        });
                            }

                            @Override
                            public void onAnimationStart(Animator animation) {
                                super.onAnimationStart(animation);
                                       /*
                                WeekTable.animate().translationX(-1*WeekTable.getWidth()).setDuration(300).setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {
                                        super.onAnimationStart(animation);
                                      //  WeekTable3.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);

                                        WeekTable.setVisibility(View.GONE);
                                        WeekTable.animate().translationX(0).setDuration(0).setListener(new AnimatorListenerAdapter() {
                                            @Override
                                            public void onAnimationEnd(Animator animation) {

                                            mon_num.setText(calendarController.daydetails[0].getDay());
                                                mon_month.setText(calendarController.daydetails[0].getMonth());

                                                tue_num.setText(calendarController.daydetails[1].getDay());
                                                tue_month.setText(calendarController.daydetails[1].getMonth());

                                                wed_num.setText(calendarController.daydetails[2].getDay());
                                                wed_month.setText(calendarController.daydetails[2].getMonth());


                                                thu_num.setText(calendarController.daydetails[3].getDay());
                                                thu_month.setText(calendarController.daydetails[3].getMonth());


                                                fri_num.setText(calendarController.daydetails[4].getDay());
                                                fri_month.setText(calendarController.daydetails[4].getMonth());

                                                sat_num.setText(calendarController.daydetails[5].getDay());
                                                sat_month.setText(calendarController.daydetails[5].getMonth());


                                                sun_num.setText(calendarController.daydetails[6].getDay());
                                                sun_month.setText(calendarController.daydetails[6].getMonth());

                                                mon_num3.setText(calendarController.Pvsdaydetails[0].getDay());
                                                mon_month3.setText(calendarController.Pvsdaydetails[0].getMonth());

                                                tue_num3.setText(calendarController.Pvsdaydetails[1].getDay());
                                                tue_month3.setText(calendarController.Pvsdaydetails[1].getMonth());

                                                wed_num3.setText(calendarController.Pvsdaydetails[2].getDay());
                                                wed_month3.setText(calendarController.Pvsdaydetails[2].getMonth());


                                                thu_num3.setText(calendarController.Pvsdaydetails[3].getDay());
                                                thu_month3.setText(calendarController.Pvsdaydetails[3].getMonth());


                                                fri_num3.setText(calendarController.Pvsdaydetails[4].getDay());
                                                fri_month3.setText(calendarController.Pvsdaydetails[4].getMonth());

                                                sat_num3.setText(calendarController.Pvsdaydetails[5].getDay());
                                                sat_month3.setText(calendarController.Pvsdaydetails[5].getMonth());


                                                sun_num3.setText(calendarController.Pvsdaydetails[6].getDay());
                                                sun_month3.setText(calendarController.Pvsdaydetails[6].getMonth());

                                                WeekTable.setVisibility(View.VISIBLE);
                                                WeekTable3.setVisibility(View.GONE);


                                            }
                                        });
                                    }
                                });*/
                            }
                        });









            }
        });
/*

        final AlertDialog progressDialog = new SpotsDialog(getContext(), R.style.CustomDialog);
// To dismiss the dialog
        progressDialog.show();
        Call<List<Appiontments>> call = restUserService.getService().GetSchedule( ((Central)getActivity()).gethcp_id());
        call.enqueue(new Callback<List<Appiontments>>() {
            @Override
            public void onResponse(Call<List<Appiontments>> call, Response<List<Appiontments>> response) {
                int statusCode = response.code();
                List<Appiontments> userAppiontments = response.body();
                String msg = "here";

                ArrayList<ScheduleTra> transformedRequest = new ArrayList<>();

                if (statusCode == 200) {



                    if(userAppiontments.size() > 0){

                        for(int j = 0;j < userAppiontments.size();j++){
                            ScheduleTra newappreq = new ScheduleTra(userAppiontments.get(j));
                            transformedRequest.add(newappreq);
                        }

                        ci = new ScheduleListAdapter(getActivity(), transformedRequest);
                        listvi.setAdapter(ci);
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
            public void onFailure(Call<List<Appiontments>> call, Throwable t) {
                //   progress.dismiss();
                progressDialog.hide();
                Toast.makeText(getActivity(),"request failed",Toast.LENGTH_LONG).show();

                //   Log.e("dfdf", t.toString());
            }
        });




*/

        setWeekplaceHeader();
        settoday();
        loadSchedule();
        return view;
    }




    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }




    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // TODO
            calHeader3.animate()
                    .translationY(-1*(calHeader3.getHeight())).setDuration(0);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void setWeekplaceHeader(){
        displayweeknum.setText(calendarController.getDisplayWeekNum());
        displayweek.setText(calendarController.getDisplayWeekDis());
        displayweeknum2.setText(calendarController.getPreDisplayWeekNum());
        displayweek2.setText(calendarController.getPreDisplayWeekDis());
        displayweeknum3.setText(calendarController.getNxtDisplayWeekNum());
        displayweek3.setText(calendarController.getNxtDisplayWeekDis());

        mon_num.setText(calendarController.daydetails[0].getDay());
        mon_month.setText(calendarController.daydetails[0].getMonth());

        tue_num.setText(calendarController.daydetails[1].getDay());
        tue_month.setText(calendarController.daydetails[1].getMonth());

        wed_num.setText(calendarController.daydetails[2].getDay());
        wed_month.setText(calendarController.daydetails[2].getMonth());


        thu_num.setText(calendarController.daydetails[3].getDay());
        thu_month.setText(calendarController.daydetails[3].getMonth());


        fri_num.setText(calendarController.daydetails[4].getDay());
        fri_month.setText(calendarController.daydetails[4].getMonth());

        sat_num.setText(calendarController.daydetails[5].getDay());
        sat_month.setText(calendarController.daydetails[5].getMonth());


        sun_num.setText(calendarController.daydetails[6].getDay());
        sun_month.setText(calendarController.daydetails[6].getMonth());
    }

    public void setDayplaceHeader(){
        displayweeknum.setText(calendarController.currentDay.getdateinstring());
        displayweek.setText(calendarController.currentDay.getDayinfull());
        displayweeknum3.setText(calendarController.nextDay.getdateinstring());
        displayweek3.setText(calendarController.nextDay.getDayinfull());
        displayweeknum2.setText(calendarController.previousDay.getdateinstring());
        displayweek2.setText(calendarController.previousDay.getDayinfull());



    }


    public void setDayClick(){
        cal_mon_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMonAct(true);
            }
        });

        cal_mon_det.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMonAct(true);
            }
        });

        cal_tue_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTueAct(true);
            }
        });

        cal_tue_det.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTueAct(true);
            }
        });

        //wednesday
        cal_wed_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWedAct(true);
            }
        });

        cal_wed_det.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWedAct(true);
            }
        });


        //thursday

        cal_thu_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setThuAct(true);
            }
        });

        cal_thu_det.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setThuAct(true);
            }
        });

        //friday
        cal_fri_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFriAct(true);
            }
        });

        cal_fri_det.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFriAct(true);
            }
        });

        //saturday
        cal_sat_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSatAct(true);
            }
        });

        cal_sat_det.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSatAct(true);
            }
        });

        //sunday

        cal_sun_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSunAct(true);
            }
        });

        cal_sun_det.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSunAct(true);
            }
        });

    }

    public void setMonAct(Boolean state){


        if(state){
            activeday = 0;
            cal_mon_cont.setBackgroundColor(Color.parseColor("#ff009688"));
            cal_mon_det.setBackgroundColor(Color.parseColor("#ff009688"));
            cal_mon_c.setTextColor(Color.WHITE);
            cal_mon_c.setTypeface(null, Typeface.BOLD_ITALIC);
            mon_num.setTextColor(Color.WHITE);
            mon_num.setTypeface(null, Typeface.BOLD_ITALIC);
            mon_month.setTextColor(Color.WHITE);
            mon_month.setTypeface(null, Typeface.BOLD_ITALIC);

            setTueAct(false);
            setWedAct(false);
            setThuAct(false);
            setFriAct(false);
            setSatAct(false);
            setSunAct(false);
            calendarController.SetCuurentDay(activeday);
            try {
                ci = new myScheduleSlotListAdapter2(getActivity(), refineSchedule.refine(userAppiontments,calendarController.currentDay.getDayDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            listvi.setAdapter(ci);
        }else{

            cal_mon_cont.setBackgroundColor(Color.parseColor("#00009688"));
            cal_mon_det.setBackgroundColor(Color.parseColor("#00009688"));
            cal_mon_c.setTextColor(Color.parseColor("#d7cece"));
            cal_mon_c.setTypeface(Typeface.DEFAULT,Typeface.ITALIC);
            mon_num.setTextColor(Color.parseColor("#d7cece"));
            mon_num.setTypeface(Typeface.DEFAULT,Typeface.ITALIC);
            mon_month.setTextColor(Color.parseColor("#d7cece"));
            mon_month.setTypeface(Typeface.DEFAULT,Typeface.ITALIC);

        }

    }

    public void setTueAct(Boolean state){


        if(state){
            activeday = 1;
            cal_tue_cont.setBackgroundColor(Color.parseColor("#ff009688"));
            cal_tue_det.setBackgroundColor(Color.parseColor("#ff009688"));
            cal_tue_c.setTextColor(Color.WHITE);
            cal_tue_c.setTypeface(null,Typeface.BOLD_ITALIC);
            tue_num.setTextColor(Color.WHITE);
            tue_num.setTypeface(null, Typeface.BOLD_ITALIC);
            tue_month.setTextColor(Color.WHITE);
            tue_month.setTypeface(null, Typeface.BOLD_ITALIC);

            setMonAct(false);
            setWedAct(false);
            setThuAct(false);
            setFriAct(false);
            setSatAct(false);
            setSunAct(false);
            calendarController.SetCuurentDay(activeday);
            try {
                ci = new myScheduleSlotListAdapter2(getActivity(), refineSchedule.refine(userAppiontments,calendarController.currentDay.getDayDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            listvi.setAdapter(ci);
        }else{

            cal_tue_cont.setBackgroundColor(Color.parseColor("#00009688"));
            cal_tue_det.setBackgroundColor(Color.parseColor("#00009688"));
            cal_tue_c.setTextColor(Color.parseColor("#d7cece"));
            cal_tue_c.setTypeface(Typeface.DEFAULT,Typeface.ITALIC);
            tue_num.setTextColor(Color.parseColor("#d7cece"));
            tue_num.setTypeface(Typeface.DEFAULT,Typeface.ITALIC);
            tue_month.setTextColor(Color.parseColor("#d7cece"));
            tue_month.setTypeface(Typeface.DEFAULT,Typeface.ITALIC);

        }

    }

    public void setWedAct(Boolean state){


        if(state){
            activeday = 2;

            cal_wed_cont.setBackgroundColor(Color.parseColor("#ff009688"));
            cal_wed_det.setBackgroundColor(Color.parseColor("#ff009688"));
            cal_wed_c.setTextColor(Color.WHITE);
            cal_wed_c.setTypeface(null,Typeface.BOLD_ITALIC);
            wed_num.setTextColor(Color.WHITE);
            wed_num.setTypeface(null, Typeface.BOLD_ITALIC);
            wed_month.setTextColor(Color.WHITE);
            wed_month.setTypeface(null, Typeface.BOLD_ITALIC);

            setMonAct(false);
            setTueAct(false);
            setThuAct(false);
            setFriAct(false);
            setSatAct(false);
            setSunAct(false);
            calendarController.SetCuurentDay(activeday);
            try {
                ci = new myScheduleSlotListAdapter2(getActivity(), refineSchedule.refine(userAppiontments,calendarController.currentDay.getDayDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            listvi.setAdapter(ci);
        }else{

            cal_wed_cont.setBackgroundColor(Color.parseColor("#00009688"));
            cal_wed_det.setBackgroundColor(Color.parseColor("#00009688"));
            cal_wed_c.setTextColor(Color.parseColor("#d7cece"));
            cal_wed_c.setTypeface(Typeface.DEFAULT,Typeface.ITALIC);
            wed_num.setTextColor(Color.parseColor("#d7cece"));
            wed_num.setTypeface(Typeface.DEFAULT,Typeface.ITALIC);
            wed_month.setTextColor(Color.parseColor("#d7cece"));
            wed_month.setTypeface(Typeface.DEFAULT,Typeface.ITALIC);

        }

    }

    public void setThuAct(Boolean state){


        if(state){
            activeday = 3;
            cal_thu_cont.setBackgroundColor(Color.parseColor("#ff009688"));
            cal_thu_det.setBackgroundColor(Color.parseColor("#ff009688"));
            cal_thu_c.setTextColor(Color.WHITE);
            cal_thu_c.setTypeface(null,Typeface.BOLD_ITALIC);
            thu_num.setTextColor(Color.WHITE);
            thu_num.setTypeface(null, Typeface.BOLD_ITALIC);
            thu_month.setTextColor(Color.WHITE);
            thu_month.setTypeface(null, Typeface.BOLD_ITALIC);

            setMonAct(false);
            setTueAct(false);
            setWedAct(false);
            setFriAct(false);
            setSatAct(false);
            setSunAct(false);
            calendarController.SetCuurentDay(activeday);
            try {
                ci = new myScheduleSlotListAdapter2(getActivity(), refineSchedule.refine(userAppiontments,calendarController.currentDay.getDayDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            listvi.setAdapter(ci);
        }else{

            cal_thu_cont.setBackgroundColor(Color.parseColor("#00009688"));
            cal_thu_det.setBackgroundColor(Color.parseColor("#00009688"));
            cal_thu_c.setTextColor(Color.parseColor("#d7cece"));
            cal_thu_c.setTypeface(Typeface.DEFAULT,Typeface.ITALIC);
            thu_num.setTextColor(Color.parseColor("#d7cece"));
            thu_num.setTypeface(Typeface.DEFAULT,Typeface.ITALIC);
            thu_month.setTextColor(Color.parseColor("#d7cece"));
            thu_month.setTypeface(Typeface.DEFAULT,Typeface.ITALIC);

        }

    }

    public void setFriAct(Boolean state){


        if(state){

            activeday = 4;

            cal_fri_cont.setBackgroundColor(Color.parseColor("#ff009688"));
            cal_fri_det.setBackgroundColor(Color.parseColor("#ff009688"));
            cal_fri_c.setTextColor(Color.WHITE);
            cal_fri_c.setTypeface(null,Typeface.BOLD_ITALIC);
            fri_num.setTextColor(Color.WHITE);
            fri_num.setTypeface(null, Typeface.BOLD_ITALIC);
            fri_month.setTextColor(Color.WHITE);
            fri_month.setTypeface(null, Typeface.BOLD_ITALIC);

            setMonAct(false);
            setTueAct(false);
            setWedAct(false);
            setThuAct(false);
            setSatAct(false);
            setSunAct(false);

            calendarController.SetCuurentDay(activeday);
            try {
                ci = new myScheduleSlotListAdapter2(getActivity(), refineSchedule.refine(userAppiontments,calendarController.currentDay.getDayDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            listvi.setAdapter(ci);
        }else{

            cal_fri_cont.setBackgroundColor(Color.parseColor("#00009688"));
            cal_fri_det.setBackgroundColor(Color.parseColor("#00009688"));
            cal_fri_c.setTextColor(Color.parseColor("#d7cece"));
            cal_fri_c.setTypeface(Typeface.DEFAULT,Typeface.ITALIC);
            fri_num.setTextColor(Color.parseColor("#d7cece"));
            fri_num.setTypeface(Typeface.DEFAULT,Typeface.ITALIC);
            fri_month.setTextColor(Color.parseColor("#d7cece"));
            fri_month.setTypeface(Typeface.DEFAULT,Typeface.ITALIC);

        }

    }

    public void setSatAct(Boolean state){


        if(state){
            activeday = 5;
            cal_sat_cont.setBackgroundColor(Color.parseColor("#ff009688"));
            cal_sat_det.setBackgroundColor(Color.parseColor("#ff009688"));
            cal_sat_c.setTextColor(Color.WHITE);
            cal_sat_c.setTypeface(null,Typeface.BOLD_ITALIC);
            sat_num.setTextColor(Color.WHITE);
            sat_num.setTypeface(null, Typeface.BOLD_ITALIC);
            sat_month.setTextColor(Color.WHITE);
            sat_month.setTypeface(null, Typeface.BOLD_ITALIC);

            setMonAct(false);
            setTueAct(false);
            setWedAct(false);
            setThuAct(false);
            setFriAct(false);
            setSunAct(false);

            calendarController.SetCuurentDay(activeday);

            try {
                ci = new myScheduleSlotListAdapter2(getActivity(), refineSchedule.refine(userAppiontments,calendarController.currentDay.getDayDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            listvi.setAdapter(ci);

        }else{

            cal_sat_cont.setBackgroundColor(Color.parseColor("#00009688"));
            cal_sat_det.setBackgroundColor(Color.parseColor("#00009688"));
            cal_sat_c.setTextColor(Color.parseColor("#d7cece"));
            cal_sat_c.setTypeface(Typeface.DEFAULT,Typeface.ITALIC);
            sat_num.setTextColor(Color.parseColor("#d7cece"));
            sat_num.setTypeface(Typeface.DEFAULT,Typeface.ITALIC);
            sat_month.setTextColor(Color.parseColor("#d7cece"));
            sat_month.setTypeface(Typeface.DEFAULT,Typeface.ITALIC);

        }

    }

    public void setSunAct(Boolean state){


        if(state){

            activeday = 6;
            cal_sun_cont.setBackgroundColor(Color.parseColor("#ff009688"));
            cal_sun_det.setBackgroundColor(Color.parseColor("#ff009688"));
            cal_sun_c.setTextColor(Color.WHITE);
            cal_sun_c.setTypeface(null,Typeface.BOLD_ITALIC);
            sun_num.setTextColor(Color.WHITE);
            sun_num.setTypeface(null, Typeface.BOLD_ITALIC);
            sun_month.setTextColor(Color.WHITE);
            sun_month.setTypeface(null, Typeface.BOLD_ITALIC);

            setMonAct(false);
            setTueAct(false);
            setWedAct(false);
            setThuAct(false);
            setFriAct(false);
            setSatAct(false);

            calendarController.SetCuurentDay(activeday);
            try {
                ci = new myScheduleSlotListAdapter2(getActivity(), refineSchedule.refine(userAppiontments,calendarController.currentDay.getDayDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            listvi.setAdapter(ci);
        }else{

            cal_sun_cont.setBackgroundColor(Color.parseColor("#00009688"));
            cal_sun_det.setBackgroundColor(Color.parseColor("#00009688"));
            cal_sun_c.setTextColor(Color.parseColor("#d7cece"));
            cal_sun_c.setTypeface(Typeface.DEFAULT,Typeface.ITALIC);
            sun_num.setTextColor(Color.parseColor("#d7cece"));
            sun_num.setTypeface(Typeface.DEFAULT,Typeface.ITALIC);
            sun_month.setTextColor(Color.parseColor("#d7cece"));
            sun_month.setTypeface(Typeface.DEFAULT,Typeface.ITALIC);

        }

    }


    public void settoday(){
      int i = calendarController.getcurrentdayvalue();
       setMonAct(false);
       if(i == 0){
           setMonAct(true);
       }else if(i == 1){
           setTueAct(true);
       }else if(i == 2){
           setWedAct(true);
       }else if(i == 3){
           setThuAct(true);
       }else if(i == 4){
           setFriAct(true);
       }else if(i == 5){
           setSatAct(true);
       }else{
           setSunAct(true);
       }

    }
    public View.OnClickListener showhide(){
        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settoday();
                if(showweekView){
                    showweekView = false;
                    setDayplaceHeader();
                    weekdetails.animate().translationX(-1*(weekdetails.getWidth())).setDuration(300).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            weekdetails.setVisibility(View.GONE);
                        }
                    });
                    showpanel.setVisibility(View.VISIBLE);
                    hidepanel.setVisibility(View.GONE);

                }else{
                    weekdetails.setVisibility(View.VISIBLE);
                    showweekView = true;
                    setWeekplaceHeader();
                    hidepanel.setVisibility(View.VISIBLE);
                    showpanel.setVisibility(View.GONE);
                    weekdetails.animate().translationX(0).setDuration(300).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);

                        }
                    });

                }

            }
        };

                return click;
    }


    public void  loadSchedule(){

        Log.e("start Date",TimeSlotUtil.convertDateToString(calendarController.daydetails[0].getDayDate()));
        Log.e("end Date",TimeSlotUtil.convertDateToString(calendarController.daydetails[6].getDayDate()));
        Call<ScheduleSlotHolder> call = scheduleService.getService().GetSchedule2(((Central)getActivity()).gethcp_id(),TimeSlotUtil.convertDateToString(calendarController.daydetails[0].getDayDate()),TimeSlotUtil.convertDateToString(calendarController.daydetails[6].getDayDate()));
        call.enqueue(new Callback<ScheduleSlotHolder>() {
            @Override
            public void onResponse(Call<ScheduleSlotHolder> call, Response<ScheduleSlotHolder> response) {
                int statusCode = response.code();
                ScheduleSlotHolder responsefrom = response.body();


                String msg = "here";

                if (statusCode == 200) {

                userAppiontments = responsefrom.getScheduleList();



                    if(userAppiontments.size() > 0){


                        Log.e("status",String.valueOf(userAppiontments.size()));

                        try {
                            ci = new myScheduleSlotListAdapter2(getActivity(), refineSchedule.refine(userAppiontments,calendarController.currentDay.getDayDate()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        listvi.setAdapter(ci);

                    }else{
                        Log.e("check", "less than ero");

                    }


                    //  progress.dismiss();
                }else{
                    Log.e("dfdf2", String.valueOf(statusCode));
                    msg="error again";
                    Toast.makeText(getActivity(),"request failed gone"+String.valueOf(statusCode),Toast.LENGTH_LONG).show();

                }
                Log.e("dfdf1", msg);
                mySwipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<ScheduleSlotHolder> call, Throwable t) {
                //   progress.dismiss();
                Toast.makeText(getActivity(),"request failed here gone",Toast.LENGTH_LONG).show();
                mySwipeRefreshLayout.setRefreshing(false);
                //   Log.e("dfdf", t.toString());
            }
        });

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
;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //do what ever you want here, and get the result from intent like below
        // String myData = data.getStringExtra("listdata");
        // Toast.makeText(getActivity(),data.getStringExtra("listdata"), Toast.LENGTH_SHORT).show();

        if(requestCode == 14414){

            loadSchedule();

        }
    }

    public void deleteBlock(int pos){

        progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Deleting");
        progressDialog.show();

        Log.e("slot number",userAppiontments.get(pos).StartDate);
        deleteSlot(Integer.parseInt(userAppiontments.get(pos).StartDate));

    }

    private void deleteSlot(int slotId){

        Call<Boolean> call = restPracService.getService().DeleteemptySlot(slotId);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                int statusCode = response.code();


                if (statusCode == 200) {


                    loadSchedule();


                    progressDialog.dismiss();
                }else{



                    progressDialog.dismiss();

                }


            }



            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                //   progress.dismiss();
                progressDialog.dismiss();
                Toast.makeText(getActivity(),"delete failed" + t.getMessage(),Toast.LENGTH_LONG).show();

                //   Log.e("dfdf", t.toString());
            }
        });
     /*   availablePractitioners
                .flatMapIterable(pracs -> pracs)
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {

                    if(practitioners.size() > 0) {
                        Log.e("here17","here17");
                        practitioners_list.setAdapter(new AvailablePractitionersListAdapter(this.getActivity(), practitioners));
                        practitioners_list.setOnItemClickListener(this);
                    }else{

                        Log.e("here18","here18");
                    }
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                }, throwable -> {
                    Log.e("here16","here16");
                    Toast.makeText(AvailablePractitionersFragment.this.getContext(),
                           throwable.toString(), Toast.LENGTH_SHORT).show();
                    throwable.printStackTrace();
                    Log.e("here161", throwable.toString());
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                });
                */
    }
}
