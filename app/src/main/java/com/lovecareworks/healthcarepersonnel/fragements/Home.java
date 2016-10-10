package com.lovecareworks.healthcarepersonnel.fragements;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.lovecareworks.healthcarepersonnel.Central;
import com.lovecareworks.healthcarepersonnel.R;
import com.lovecareworks.healthcarepersonnel.classes.LocationUpdate;
import com.lovecareworks.healthcarepersonnel.db.dbclasses.ScheduleTra;
import com.lovecareworks.healthcarepersonnel.model.Appiontments;
import com.lovecareworks.healthcarepersonnel.webapi.FileUploadServic;
import com.lovecareworks.healthcarepersonnel.webapi.RestUserService;
import com.lovecareworks.healthcarepersonnel.webapi.ServiceGenerator;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dmax.dialog.SpotsDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Home.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,com.google.android.gms.location.LocationListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RestUserService restUserService = new RestUserService();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView txtnoapp, txtDistance, txtTraveltime;
    LinearLayout homedetails, description, call, nav;
    ArrayList<ScheduleTra> transformedRequest;
    List<Appiontments> userAppiontments;
    private static final String TAG = "Home";

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    private Location currentLocation;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;

    private LocationRequest mLocationRequest;

    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 5000; // 10 sec
    private static int FATEST_INTERVAL = 1000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters


    TextView TxtClient, TxtPatient, TxtDate, TxtTime;

    private OnFragmentInteractionListener mListener;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Toolbar tool = (Toolbar) view.findViewById(R.id.toolbar1);
        txtnoapp = (TextView) view.findViewById(R.id.txtnoapp);
        transformedRequest = null;

            // Building the GoogleApi client
            buildGoogleApiClient();



        TxtClient = (TextView) view.findViewById(R.id.txtClient);
        TxtPatient = (TextView) view.findViewById(R.id.txtPatient);
        TxtDate = (TextView) view.findViewById(R.id.txtDate);
        TxtTime = (TextView) view.findViewById(R.id.txtTime);
        txtTraveltime = (TextView) view.findViewById(R.id.txtTraveltime);
        txtDistance = (TextView) view.findViewById(R.id.txtDistance);

        homedetails = (LinearLayout) view.findViewById(R.id.homedetails);
        // tool.setOverflowIcon(getResources().getDrawable(R.drawable.arrowdown, getActivity().getTheme()));
        tool.setTitle("Home");
        tool.setNavigationIcon(R.drawable.showslide);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tool.setOverflowIcon(getResources().getDrawable(R.drawable.menu_icon, getActivity().getTheme()));
        } else {
            tool.setOverflowIcon(getResources().getDrawable(R.drawable.menu_icon));
        }

        tool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Central) getActivity()).open();
            }
        });


        final AlertDialog progressDialog = new SpotsDialog(getContext(), R.style.CustomDialog);
// To dismiss the dialog
        progressDialog.show();
        Call<List<Appiontments>> call = restUserService.getService().GetCurrent(((Central) getActivity()).gethcp_id());
        call.enqueue(new Callback<List<Appiontments>>() {
            @Override
            public void onResponse(Call<List<Appiontments>> call, Response<List<Appiontments>> response) {
                int statusCode = response.code();
                userAppiontments = response.body();
                String msg = "here";



                if (statusCode == 200) {


                    if (userAppiontments.size() > 0) {
                        transformedRequest = new ArrayList<>();
                        for (int j = 0; j < userAppiontments.size(); j++) {
                            ScheduleTra newappreq = new ScheduleTra(userAppiontments.get(j));
                            transformedRequest.add(newappreq);
                        }


                        TxtClient.setText(transformedRequest.get(0).sponsor_fullname);
                        TxtPatient.setText(transformedRequest.get(0).patient_fullname);
                        TxtDate.setText( transformedRequest.get(0).appiontment_date);
                        TxtTime.setText(transformedRequest.get(0).appiontment_time);
                        txtnoapp.setVisibility(View.GONE);
                        homedetails.setVisibility(View.VISIBLE);
                        progressDialog.hide();
                    } else {

                        transformedRequest = null;
                        homedetails.setVisibility(View.GONE);
                        txtnoapp.setVisibility(View.VISIBLE);

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("No appiontment Found!")
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
                } else {
                    Log.e("dfdf2", msg);
                    msg = "error again";
                    transformedRequest = null;
                    progressDialog.hide();
                    homedetails.setVisibility(View.GONE);
                    txtnoapp.setVisibility(View.VISIBLE);

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("No appiontment Found!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                    progressDialog.hide();

                    Toast.makeText(getActivity(), "request failed", Toast.LENGTH_LONG).show();

                }
                Log.e("dfdf1", msg);

            }


            @Override
            public void onFailure(Call<List<Appiontments>> call, Throwable t) {
                //   progress.dismiss();
                progressDialog.hide();
                Toast.makeText(getActivity(), "request failed", Toast.LENGTH_LONG).show();

                //   Log.e("dfdf", t.toString());
            }
        });


        LinearLayout open = (LinearLayout) view.findViewById(R.id.btn_call);

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   ;*/
                opencall(transformedRequest.get(0).sponsor_phonenumber);
            }
        });

        ImageButton btn_open = (ImageButton) view.findViewById(R.id.img_btn_call);
        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opencall(transformedRequest.get(0).sponsor_phonenumber);
            }
        });


        LinearLayout openwaze = (LinearLayout) view.findViewById(R.id.btn_nav);
        openwaze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   ;*/
                openwaze(transformedRequest.get(0).patient_address);
            }
        });

        ImageButton btn_open_waze = (ImageButton) view.findViewById(R.id.img_btn_nav);
        btn_open_waze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openwaze(transformedRequest.get(0).patient_address);
            }
        });
        return view;
    }

    public void opencall(String phone_number) {
        Intent intent = new Intent(Intent.ACTION_CALL);

        intent.setData(Uri.parse("tel:" + phone_number));
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        getActivity().startActivity(intent);
    }

    public void openwaze(String add) {
        try {
            String url = "waze://?q=" + add;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent =
                    new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze"));
            getActivity().startActivity(intent);
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    public void gettime(String address) {
if(transformedRequest != null && userAppiontments.size()>0) {
    System.currentTimeMillis();
    ServiceGenerator service = new ServiceGenerator();
    FileUploadServic servicePone = service.createService(FileUploadServic.class);
    Call<ResponseBody> call = servicePone.getdistance(address, transformedRequest.get(0).patient_address, "AIzaSyAzv1UFN6WfXNa8DJIIhox8Z9n0u_R008Q");

    Log.e("sdsd224545", call.request().url().toString());
    call.enqueue(new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            int statusCode = response.code();
            Log.e("sdsd", response.message());
            String msg = "sucess";
            Log.e("sdsd", String.valueOf(statusCode));


            if (statusCode == 200) {
                try {
                    JSONObject jsonRes = new JSONObject(response.body().string());
                    Log.e("sdsd", jsonRes.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONObject("duration").get("text").toString());
                    Calendar now = Calendar.getInstance();
                    String[] timedate = jsonRes.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONObject("duration").get("text").toString().trim().split(" ");

                    if(timedate[1].trim().equals("mins")){

                        now.add(Calendar.MINUTE,Integer.parseInt(timedate[0]));
                    }else if(timedate[1] == "hours"){

                        now.add(Calendar.MINUTE,Integer.parseInt(timedate[0])*60 +Integer.parseInt(timedate[2])) ;

                    }

                    SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy hh:mm aa");



                    txtTraveltime.setText( jsonRes.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONObject("duration").get("text").toString() + "[Estimate]");
                    txtDistance.setText( jsonRes.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONObject("distance").get("text").toString()  + " | ");

                } catch (Exception e) {

                    Log.e("sdsd", e.getMessage());
                }

            } else {
                msg = "error again";
                try {
                    Log.e("sdsd", response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            //   progress.dismiss();
            Log.e("sdsd", t.getMessage());

        }
    });




}
    }

    private String getAddressFromLatLng(Location latLng) {
        Geocoder geocoder = new Geocoder(getActivity());

        String address = "";
        try {
            address = geocoder
                    .getFromLocation(latLng.getLatitude(), latLng.getLongitude(), 1)
                    .get(0).getAddressLine(0);
        } catch (IOException e) {
        }

        return address;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();


    }


    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
            @Override
    public void onConnected(@Nullable Bundle bundle) {



        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        currentLocation = LocationServices
                .FusedLocationApi
                .getLastLocation(mGoogleApiClient);
        if(currentLocation != null){
            displayLocation();

        }else{









            Log.e("nolocation", "cannt find location");
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(UPDATE_INTERVAL);
            mLocationRequest.setFastestInterval(FATEST_INTERVAL);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
            com.google.android.gms.location.LocationRequest xx = new com.google.android.gms.location.LocationRequest();

            xx.setInterval(UPDATE_INTERVAL);
            xx.setFastestInterval(FATEST_INTERVAL);
            xx.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            xx.setSmallestDisplacement(DISPLACEMENT);
            com.google.android.gms.location.LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, xx, this);
    /*mCurrentLocation = LocationServices
            .FusedLocationApi
            .getLastLocation(mGoogleApiClient);
    initCamera(mCurrentLocation);*/
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    private void displayLocation() {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        currentLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if (currentLocation != null) {
            double latitude = currentLocation.getLatitude();
            double longitude = currentLocation.getLongitude();
            Log.e("location found", latitude + ", " + longitude);
            gettime(getAddressFromLatLng(currentLocation));


            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(UPDATE_INTERVAL);
            mLocationRequest.setFastestInterval(FATEST_INTERVAL);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
            com.google.android.gms.location.LocationRequest xx = new com.google.android.gms.location.LocationRequest();

            xx.setInterval(UPDATE_INTERVAL);
            xx.setFastestInterval(FATEST_INTERVAL);
            xx.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            xx.setSmallestDisplacement(DISPLACEMENT);
            com.google.android.gms.location.LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, xx, this);
        //    lblLocation.setText(latitude + ", " + longitude);

        } else {
            Log.e("nolocation", "cannt find location");
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(UPDATE_INTERVAL);
            mLocationRequest.setFastestInterval(FATEST_INTERVAL);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
            com.google.android.gms.location.LocationRequest xx = new com.google.android.gms.location.LocationRequest();

            xx.setInterval(UPDATE_INTERVAL);
            xx.setFastestInterval(FATEST_INTERVAL);
            xx.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            xx.setSmallestDisplacement(DISPLACEMENT);
            com.google.android.gms.location.LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, xx,this);
            //lblLocation.setText("(Couldn't get the location. Make sure location is enabled on the device)");
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        ((Central)getActivity()).updatelocation(new LocationUpdate(((Central) getActivity()).gethcp_id(),new Date(),location.getLatitude(),location.getLongitude()));
        gettime(getAddressFromLatLng(location));
    }

public void updateLocation(){
    ((Central)getActivity()).updatelocation(new LocationUpdate(((Central) getActivity()).gethcp_id(),new Date(),101.32,122.0));

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
