package com.example.user.profileupdate;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.JsonReader;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import static java.lang.System.out;

public class Profile1 extends AppCompatActivity {

    RadioGroup radio;
    RadioGroup gendergroup;
    RadioButton selectedbutton;
    ImageView imageView;
    String etname, etphone, etemail, etadhar, etgender;
    EditText sname, semail, sadhar;
    TextView sphone;
    Button UploadImageServer,b;
    ProgressDialog progressDialog;
    Bitmap bitmap;
    Boolean check = true;
    String ImagePath = "image_path";
    String name = "name";
    String phone = "phone";
    String email = "email";
    String adhar = "adhar";

    String name2;
    String email2;
    String adhar2;
    String image_path;
    String FinalData;

    String ServerUploadPath = "https://vamc.000webhostapp.com/profile1.php";
    String response="https://vamc.000webhostapp.com/2.php";
    List<Utils> UtilsList;
    RequestQueue rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        UtilsList = new ArrayList<>();
        rq = Volley.newRequestQueue(this);


        imageView = (ImageView) findViewById(R.id.imageView);
        sname = (EditText) findViewById(R.id.etname);
        sphone = (TextView) findViewById(R.id.etphone);
        semail = (EditText) findViewById(R.id.etmail);
        sadhar = (EditText) findViewById(R.id.etadhar);
        gendergroup = (RadioGroup) findViewById(R.id.gendergroup);
        b=(Button)findViewById(R.id.b);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profile1.this,Main3Activity.class);
                startActivity(intent);
            }
        });


//        sname.setText(name2);
//        semail.setText(email2);
//       sadhar.setText(adhar2);
        Bundle bundle = getIntent().getExtras();
        String s1 = bundle.getString("ename");

        String s2 = bundle.getString("pemail");

        String s3 = bundle.getString("padhar");

        String image_path = bundle.getString("image_path");
        if (image_path.equals("0")) {
            // Picasso.get().load().into(imageView);
        } else {
            Picasso.get().load(image_path).into(imageView);
        }

      // imageView.setImageResource(Integer.parseInt(image_path));


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
       final String tld = sharedPreferences.getString("id", "");
//        final String name = sharedPreferences.getString("name2", "");
//        final String email= sharedPreferences.getString("email2", "");
//        final String adhar = sharedPreferences.getString("adhar2", "");
//       final String image_path1 = sharedPreferences.getString("image_path", "");
        out.print(tld);
       sphone.setText(tld);
        sname.setText(s1);
        semail.setText(s2);
        sadhar.setText(s3);
 //      Picasso.get().load(image_path1).into(imageView);
//        gendergroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                int id=gendergroup.getCheckedRadioButtonId();
//                RadioButton radioButton=(RadioButton)findViewById(id);
//                 radioText= radioButton.getText().toString();
//            }
//        });

//
//                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("name2", name2);
//                    editor.putString("email2", email2);
//                    editor.putString("adhar2", adhar2);
//                      editor.putString("image_path", image_path);
//                    editor.commit();

//        JSONObject obj = null;
//        try {
//            obj = new JSONObject(FinalData);
//            // Log.d("My App", obj.toString());
//            Log.d("TAG", "onCreate: obj"+obj);
//
//            name2 = obj.getString("ename");
//            Log.d("phonetype value ", name2);
//
//            email2 = obj.getString("pemail");
//            Log.d("phonetype value ", email2);
//
//            adhar2 = obj.getString("padhar");
//            Log.d("phonetype value ", adhar2);
//
//            image_path = obj.getString("image_path");
//            Log.d("phonetype value ", image_path);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//



        UploadImageServer = (Button) findViewById(R.id.buttonUpload);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();

                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);

            }
        });

        UploadImageServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etname = sname.getText().toString();
                sname.setText(etname);

                Log.d("TAG", "onClick:e" + etname);
                sphone.setText(tld);
                etphone = sphone.getText().toString();
                etemail = semail.getText().toString();
                etadhar = sadhar.getText().toString();
               // a = radioText;
                Log.d("TAG", "onClick: name" + etname);

                if (etname.isEmpty()){
                    sname.setError("Enter Name");
                    return;
                }
              else if (!etemail.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
                    semail.setError("Invalid Email Address");
                }
                else if (etadhar.length() != 12) {
                    sadhar.setError("please enter valid aadhar number");


                }
//              else if(bitmap!=null){
//                    Toast.makeText(Profile1.this,"Capture Licence", Toast.LENGTH_SHORT).show();
//                }
                else{


                    try {
                        ImageUploadToServerFunction();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
//
//                Intent intent = new Intent(Profile1.this, ProfileRetrieve.class);
//                startActivity(intent);

//                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
//                byte[] bytes=byteArrayOutputStream.toByteArray();
//
//                intent.putExtra("ImagePath",bytes);


            }
        });

    }

    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);

        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {

            Uri uri = I.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
            bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            imageView.setImageBitmap(bitmap);

        }
    }

    public void ImageUploadToServerFunction() throws IOException {


        ByteArrayOutputStream byteArrayOutputStreamObject;

        byteArrayOutputStreamObject = new ByteArrayOutputStream();

     //  bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);
//        Bitmap original = BitmapFactory.decodeStream(getAssets().open("1024x768.jpg"));
//
//        original.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);
//        Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(byteArrayOutputStreamObject.toByteArray()));
//
//        Log.e("Original   dimensions", original.getWidth()+" "+original.getHeight());
//        Log.e("Compressed dimensions", decoded.getWidth()+" "+decoded.getHeight());

            byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();

            final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);


            class AsyncTaskUploadClass extends AsyncTask<Void, Void, String> {


                @Override
                protected void onPreExecute() {

                    super.onPreExecute();

                    progressDialog = ProgressDialog.show(Profile1.this, "Image is Uploading", "Please Wait", false, false);
                }

                @Override
                protected void onPostExecute(String string1) {

                    super.onPostExecute(string1);

                    // Dismiss the progress dialog after done uploading.
                    progressDialog.dismiss();

                    // Printing uploading success message coming from server on android app.
                    Toast.makeText(Profile1.this, string1, Toast.LENGTH_LONG).show();

                    // Setting image as transparent after done uploading.
                    imageView.setImageResource(android.R.color.transparent);


                }

                @Override
                protected String doInBackground(Void... params) {


                    ImageProcessClass imageProcessClass = new ImageProcessClass();

                    HashMap<String, String> HashMapParams = new HashMap<String, String>();

                    HashMapParams.put(name, etname);
                    HashMapParams.put(phone, etphone);
                    HashMapParams.put(email, etemail);
                    HashMapParams.put(adhar, etadhar);

                    HashMapParams.put(ImagePath, ConvertImage);

                     FinalData = imageProcessClass.ImageHttpRequest(ServerUploadPath, HashMapParams);
                    Log.d("TAG", "doInBackground: finalData" + FinalData);


//                    JSONObject obj = null;
//                    try {
//                        obj = new JSONObject(FinalData);
//                        // Log.d("My App", obj.toString());
//                        Log.d("TAG", "onCreate: obj"+obj);
//
//                        name2 = obj.getString("ename");
//                        Log.d("phonetype value ", name2);
//
//                        email2 = obj.getString("pemail");
//                        Log.d("phonetype value ", email2);
//
//                        adhar2 = obj.getString("padhar");
//                        Log.d("phonetype value ", adhar2);
//
//                        image_path = obj.getString("image_path");
//                        Log.d("phonetype value ", image_path);
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }


                    return FinalData;


                }

            }
            AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();

            AsyncTaskUploadClassOBJ.execute();
        }



    public class ImageProcessClass {

        public String ImageHttpRequest(String requestURL, HashMap<String, String> PData) {

            StringBuilder stringBuilder = new StringBuilder();

            try {

                URL url;
                HttpURLConnection httpURLConnectionObject;
                OutputStream OutPutStream;
                BufferedWriter bufferedWriterObject;
                BufferedReader bufferedReaderObject;
                int RC;

                url = new URL(requestURL);

                httpURLConnectionObject = (HttpURLConnection) url.openConnection();

                httpURLConnectionObject.setReadTimeout(19000);

                httpURLConnectionObject.setConnectTimeout(19000);

                httpURLConnectionObject.setRequestMethod("POST");

                httpURLConnectionObject.setDoInput(true);

                httpURLConnectionObject.setDoOutput(true);

                OutPutStream = httpURLConnectionObject.getOutputStream();

                bufferedWriterObject = new BufferedWriter(

                        new OutputStreamWriter(OutPutStream, "UTF-8"));

                bufferedWriterObject.write(bufferedWriterDataFN(PData));

                bufferedWriterObject.flush();

                bufferedWriterObject.close();

                OutPutStream.close();

                RC = httpURLConnectionObject.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReaderObject = new BufferedReader(new InputStreamReader(httpURLConnectionObject.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReaderObject.readLine()) != null) {

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException, UnsupportedEncodingException {

            StringBuilder stringBuilderObject;

            stringBuilderObject = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {

                if (check)

                    check = false;
                else stringBuilderObject.append("&");

                stringBuilderObject.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

                stringBuilderObject.append("=");

                stringBuilderObject.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }

            return stringBuilderObject.toString();
        }
    }
}


