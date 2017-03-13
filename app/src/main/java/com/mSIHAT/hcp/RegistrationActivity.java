package com.mSIHAT.hcp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mSIHAT.hcp.R;
import com.mSIHAT.hcp.classes.HCP;
import com.mSIHAT.hcp.classes.HCPtest;
import com.mSIHAT.hcp.classes.basicinfomation;
import com.mSIHAT.hcp.classes.certificationbody;
import com.mSIHAT.hcp.model.EducationItem;
import com.mSIHAT.hcp.model.EmploymentClass;
import com.mSIHAT.hcp.registration.registration1;
import com.mSIHAT.hcp.registration.registration2;
import com.mSIHAT.hcp.registration.registration3;
import com.mSIHAT.hcp.webapi.RestUserService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    Button signup, cancel;
    HCP registeringPersonnel;
    int location = 0;
    AppCompatActivity activity;
    basicinfomation BasicInformation;
    certificationbody CertificationBody;
    List<EducationItem> edulist = new ArrayList<>();
    List<EmploymentClass> emplist = new ArrayList<>();
    RadioGroup radiogroup;
    private RestUserService restUserService = new RestUserService();
    private void replaceFragment (String backStateName){

        this.getSupportFragmentManager().popBackStackImmediate (backStateName, 0);


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/TrebuchetMS.ttf");
        signup = (Button) findViewById(R.id.btn_signup_confirm);
        cancel = (Button) findViewById(R.id.btn_signup_cancel);

        activity = this;
        SpannableString s = new SpannableString("Register");
        s.setSpan(face, 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

//        getSupportActionBar().setTitle(s);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(location == 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setMessage("Are you sure if u want to cancel your registration, data will not be kept after canclling")
                            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            })
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    finish();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();

                }else if(location == 1){
                    location = 0;
                  /*  replaceFragment("r");
                    location = 0;
                    cancel.setText("Cancel");
                   cancel.setTextColor(Color.parseColor("#FFFFFF"));
                    cancel.setBackgroundColor(Color.parseColor("#710043"));
*/

                    cancel.setText("Cancel");
                    cancel.setTextColor(Color.parseColor("#FFFFFF"));
                    cancel.setBackgroundColor(Color.parseColor("#710043"));
                    if (activity.getSupportFragmentManager().findFragmentByTag ( "registration1" ) == null) { // No fragment in backStack with same tag..
                        activity.getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.slide_in_left, 0)
                                .add(R.id.registrationcontainer, new registration2(), "registration2")
                                .addToBackStack("r1")
                                .commit();
                        Log.e("not found","not found");
                    }
                    else {

                        activity.getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("registration2")).show (activity.getSupportFragmentManager().findFragmentByTag ( "registration1" ) ).commit ();
                    }


                }else if(location == 2){
                    Log.e("found","found");
                   // replaceFragment("r1");
                    location = 1;
                    signup.setText("Next");
                    signup.setTextColor(Color.parseColor("#710043"));
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        signup.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonborder));
                    } else {
                        ;

                        signup.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttonborder));
                    }
                    activity.getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("registration3")).show (activity.getSupportFragmentManager().findFragmentByTag ( "registration2" ) ).commit ();

                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (signup.getText().toString() == "Submit") {

                    final registration3 fragment3 = (registration3) getSupportFragmentManager().findFragmentByTag("registration3");

                    if (fragment3.imagePicked()) {
                        if (fragment3.getCheckButton()) {
                            registeringPersonnel = createHcp();
                            //   Toast.makeText(getApplication(), "here is data" + registeringPersonnel.CertificationBody.getLanguage(), Toast.LENGTH_LONG).show();
                  /*  FragmentManager manager = getFragmentManager();
                    completedReg alertDialogFragment = new completedReg();
                    alertDialogFragment.show(manager, "completedreg");
                    */

                            final AlertDialog progressDialog = new SpotsDialog(activity, R.style.CustomDialog3);
// To dismiss the dialog
                            progressDialog.show();
                            Log.e("registration", registeringPersonnel.getEducationBackgroundList().get(0).getUniversityName());
                            Call<HCPtest> call = restUserService.getService().Register(registeringPersonnel);
                            call.enqueue(new Callback<HCPtest>() {
                                @Override
                                public void onResponse(Call<HCPtest> call, Response<HCPtest> response) {
                                    int statusCode = response.code();
                                    HCPtest userid = response.body();
                                    String msg = "here";


                                    if (statusCode == 200) {

                                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                                        builder.setMessage("Your registration has been sucessful, an email has been sent to you. Do check for more information..  WElCOME ON BOARD MSIHAT")
                                                .setCancelable(false)
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        activity.finish();
                                                    }
                                                });
                                        AlertDialog alert = builder.create();
                                        alert.show();

                                        uploadFile(fragment3.getfile(), Integer.parseInt(userid.getDetail()), progressDialog);


                                    } else {


                                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                                        builder.setMessage("Registration failed contact the admin or try again")
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
                                public void onFailure(Call<HCPtest> call, Throwable t) {
                                    //   progress.dismiss();
                                    progressDialog.hide();
                                    Toast.makeText(activity, t.toString(), Toast.LENGTH_LONG).show();

                                }
                            });
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                            builder.setMessage("Please Accept our terms and condition to continue")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setMessage("Please click the image box to select a profile image")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }


                    //get registration 3 data;


                } else {
                    if (location == 0) {

                        //get registration 1 data;
                        registration1 fragment = (registration1) getSupportFragmentManager().findFragmentByTag("registration1");

                        if (fragment.validateinput().isSatus()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity); //<---------------------
                            builder.setMessage(fragment.validateinput().getMessage())
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                            return;
                        }
                        BasicInformation = fragment.getData();


                        location = 1;

                    /*   if(!activity.getSupportFragmentManager().popBackStackImmediate ("r1", 0)){
                           Log.e("not found","not found");
                           activity.getSupportFragmentManager().beginTransaction()
                                   .setCustomAnimations(R.anim.slide_in_left, 0)
                                   .add(R.id.registrationcontainer, new registration2(), "registration2")
                                   .addToBackStack("r1")
                                   .commit();
                       }
                       */
                        if (activity.getSupportFragmentManager().findFragmentByTag ( "registration2" ) == null) { // No fragment in backStack with same tag..
                            activity.getSupportFragmentManager().beginTransaction()
                                    .setCustomAnimations(R.anim.slide_in_left, 0)
                                    .add(R.id.registrationcontainer, new registration2(), "registration2")
                                    .addToBackStack("r1")
                                    .commit();
                            Log.e("not found","not found");
                        }
                        else {
                            Log.e("found","found");
                            activity.getSupportFragmentManager().beginTransaction().show ( activity.getSupportFragmentManager().findFragmentByTag ( "registration2" ) ).commit ();
                        }

                        cancel.setText("Previous");
                        cancel.setTextColor(Color.parseColor("#710043"));
                        final int sdk = android.os.Build.VERSION.SDK_INT;
                        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            cancel.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonborder));
                        } else {
                            ;

                            cancel.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttonborder));
                        }
                    } else {

                        //get registration 2 data;
                        registration2 fragmentreg2 = (registration2) getSupportFragmentManager().findFragmentByTag("registration2");

                        if (fragmentreg2.validateinput().isSatus()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity); //<---------------------
                            builder.setMessage(fragmentreg2.validateinput().getMessage())
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                            return;
                        }
                        if (emplist.size() <= 0) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity); //<---------------------
                            builder.setMessage("Please Enter Employment history")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                            return;
                        }
                        if (edulist.size() <= 0) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity); //<---------------------
                            builder.setMessage("Please Enter Educational history")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                            return;
                        }


                        CertificationBody = fragmentreg2.getCertificationData();
location = 2;
                        activity.getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.slide_in_left, 0)
                                .add(R.id.registrationcontainer, new registration3(), "registration3")
                                .addToBackStack("r2")
                                .hide(getSupportFragmentManager().findFragmentByTag("registration2"))
                                .hide(getSupportFragmentManager().findFragmentByTag("registration1"))
                                .commit();

                        signup.setText("Submit");
                        signup.setTextColor(Color.parseColor("#FFFFFF"));
                        signup.setBackgroundColor(Color.parseColor("#710043"));
                        cancel.setTextColor(Color.parseColor("#710043"));
                        final int sdk = android.os.Build.VERSION.SDK_INT;
                        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            cancel.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonborder));
                        } else {
                            ;


                            cancel.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttonborder));
                        }
                    }
                }


            }
        });

        this.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.push_down_out, R.anim.slide_in_left).replace(R.id.registrationcontainer, new registration1(), "registration1").addToBackStack("r").commit();

        //this.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.push_down_out, R.anim.slide_in_left).replace(R.id.registrationcontainer, new registration3(), "registration1").addToBackStack("r").commit();

    }

    public void AddEdu(List<EducationItem> result) {
        edulist = result;

    }
    @Override
    public void onBackPressed()
    {
       finish();
    }

    private void uploadFile(File file, int hcpid, final AlertDialog progressDialog) {


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


        Call<String> call = restUserService.getService().uploadregisters(body, description);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                int statusCode = response.code();

                String msg = "here";

                if (statusCode == 200) {
                    Log.e("success", "sucesss");
                    progressDialog.hide();
                    //  progress.dismiss();
                } else {
                    Log.e("dfdf2", msg);
                    msg = "error again";

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
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
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

    public void AddEmp(List<EmploymentClass> result) {
        emplist = result;

    }

    public HCP createHcp() {
        HCP hcp = new HCP();
        hcp.setBasicInformation(BasicInformation);
        hcp.setCertificationBody(CertificationBody);
        hcp.setEducationBackgroundList(edulist);


        hcp.setEmploymentHistoryList(emplist);
        return hcp;
    }

    public List<EducationItem> getEducationList() {
        return edulist;
    }

    public List<EmploymentClass> getEmploymentList() {
        return emplist;
    }
}
