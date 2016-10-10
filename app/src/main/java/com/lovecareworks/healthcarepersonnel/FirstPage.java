package com.lovecareworks.healthcarepersonnel;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.lovecareworks.healthcarepersonnel.R;
import com.lovecareworks.healthcarepersonnel.fragements.Welcomepage;
import com.lovecareworks.healthcarepersonnel.webapi.RestUserService;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstPage extends AppCompatActivity {
Boolean login = false;

    private RestUserService restUserService = new RestUserService();

        private static int RESULT_LOAD_IMG = 1;
        String imgDecodableString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        getSupportActionBar().hide();
final AppCompatActivity act = this;
        act.getSupportFragmentManager().beginTransaction().addToBackStack("p1").
        setCustomAnimations(R.anim.move, R.anim.moveout).replace(R.id.firstpage, new Welcomepage(), "welcomepage").commit();
        showlogin();

    }
public void showlogin(){
login = true;
}


    public void hidelogin(){
        login = false;
    }



    @Override
    public void onBackPressed()
    {
if(login){
    hidelogin();
    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.move, R.anim.moveout).show(getSupportFragmentManager().findFragmentByTag("welcomepage"))
            .remove(getSupportFragmentManager().findFragmentByTag("login")).commit();

}else{

    this.finish();
}
    }



    public void opengallery(){

        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
// Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data


                Uri selectedImageURI = data.getData();
                Toast.makeText(this, getRealPathFromURI(selectedImageURI),
                        Toast.LENGTH_LONG).show();


                File file = new File(getRealPathFromURI(selectedImageURI));

                uploadFile(file);
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
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

    private void uploadFile(File file) {


        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("picture", file.getName(), requestFile);

        // add another part within the multipart request
        String descriptionString = "hcp images";
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


}
