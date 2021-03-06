package com.mSIHAT.hcp.fragements;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mSIHAT.hcp.Central;
import com.mSIHAT.hcp.R;
import com.mSIHAT.hcp.classes.hcpinfo;
import com.mSIHAT.hcp.model.hcp_infoDate;
import com.mSIHAT.hcp.webapi.RestUserService;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.ParseException;

import dmax.dialog.SpotsDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link profile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static int RESULT_LOAD_IMG = 1;
    File file;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public EditText EDTprofileName,EDTprofilePhone,EDTprofileEmail,EDTprofileAdd,EDTprofileApp;
    TextView EDTprofileExpDate,EDTprofileDays;
    CircularImageView profileimage;
    private RestUserService restUserService = new RestUserService();
    private OnFragmentInteractionListener mListener;
    hcpinfo userinfo;
    public profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profile.
     */
    // TODO: Rename and change types and number of parameters
    public static profile newInstance(String param1, String param2) {
        profile fragment = new profile();
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
       View view = inflater.inflate(R.layout.fragment_profile, container, false);
        Toolbar tool = (Toolbar)view.findViewById(R.id.toolbarprofile);
        EDTprofileName = (EditText)view.findViewById(R.id.profileName);
        EDTprofilePhone= (EditText)view.findViewById(R.id.profilePhone);
                EDTprofileEmail= (EditText)view.findViewById(R.id.profileEmail);
        EDTprofileAdd= (EditText)view.findViewById(R.id.profileAddress);
                EDTprofileApp= (EditText)view.findViewById(R.id.profileApp);
        EDTprofileExpDate= (TextView)view.findViewById(R.id.profile_ConDate);
                EDTprofileDays= (TextView)view.findViewById(R.id.profile_RemDays);
profileimage = (CircularImageView)view.findViewById(R.id.profileimage);

        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengallery();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Your profile page (alpha testing)")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framei, new Home()).commit();
                    }
                });
        AlertDialog alert = builder.create();
     //   alert.show();
        // tool.setOverflowIcon(getResources().getDrawable(R.drawable.arrowdown, getActivity().getTheme()));
        tool.setTitle("Profile");
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


        // ((FirstPage)getActivity()).opengallery();
        final AlertDialog progressDialog = new SpotsDialog(getActivity(), R.style.CustomDialog4);
// To dismiss the dialog
        progressDialog.show();
        Call<hcpinfo> call = restUserService.getService().GetInformation(((Central)getActivity()).gethcp_id());
        call.enqueue(new Callback<hcpinfo>() {
            @Override
            public void onResponse(Call<hcpinfo> call, Response<hcpinfo> response) {
                int statusCode = response.code();
                userinfo = response.body();
                String msg = "here";


                if (statusCode == 200) {
                    hcp_infoDate contract = new hcp_infoDate();

                    try {
                         contract = new hcp_infoDate(userinfo.contractdate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    EDTprofileName.setText(userinfo.getName());
                    EDTprofilePhone.setText(userinfo.getPhonenumber());
                    EDTprofileEmail.setText(userinfo.getEmail());
                    EDTprofileAdd.setText(userinfo.getAddress());
                    EDTprofileApp.setText(userinfo.getApplication());
                    EDTprofileExpDate.setText(contract.getExpDate());
                    EDTprofileDays.setText(contract.getContractremaindays());
Log.e("profile image url",userinfo.getImageurl());
                    Picasso.with(getActivity())
                            .load(userinfo.getImageurl())
                            .fit().centerCrop()
                            .networkPolicy(NetworkPolicy.NO_CACHE)
                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                            .into(profileimage);

                    progressDialog.hide();
                }else{


                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("error couldnt get your information")
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
            public void onFailure(Call<hcpinfo> call, Throwable t) {
                //   progress.dismiss();
                progressDialog.hide();
                Toast.makeText(getActivity(),t.toString(),Toast.LENGTH_LONG).show();

            }
        });

        return view;
    }
    public void opengallery(){

        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
// Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == getActivity().RESULT_OK
                    && null != data) {
                // Get the Image from data


                Uri selectedImageURI = data.getData();
                //   Toast.makeText(this, getRealPathFromURI(selectedImageURI),
                //   Toast.LENGTH_LONG).show();


                file = new File(getRealPathFromURI(selectedImageURI));

                //uploadFile(file);

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor =     getActivity().getContentResolver().query(selectedImage,filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();


                profileimage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                uploadFile(file,userinfo.getAppid());
                /*
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView)findViewById(R.id.imgView);
                // Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));

                        */

            } else {
                Toast.makeText(getActivity(), "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

    private void uploadFile(File file,int hcpid) {

Log.e("application image",String.valueOf(hcpid));
        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData(String.valueOf(hcpid), file.getName(), requestFile);

        // add another part within the multipart request
        String descriptionString = "hcpimages";
        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), descriptionString);


        Call<String> call = restUserService.getService().uploadregisters(body,description);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                int statusCode = response.code();

                String msg = "here";

                if (statusCode == 200) {
                    Log.e("success","sucesss");
                    //  progress.dismiss();
                }else{
                    Log.e("dfdf2", msg);
                    msg="error again";

                }
                Log.e("dfdf1", msg);

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //   progress.dismiss();
                Log.e("dfdf1", "error");
                //   Log.e("dfdf", t.toString());
            }
        });


    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }


    public File getfile(){

        return file;
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
