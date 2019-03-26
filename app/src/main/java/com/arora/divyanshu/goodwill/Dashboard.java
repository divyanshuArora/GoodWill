package com.arora.divyanshu.goodwill;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.arora.divyanshu.goodwill.Utility.Response;
import com.arora.divyanshu.goodwill.Utility.Server;
import com.ceylonlabs.imageviewpopup.ImagePopup;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    EditText pickdate, ename, emobile, info, amtDash;
    Calendar myCalendar = Calendar.getInstance();
    FloatingActionButton glry;
    emp_nameAdapter adapter;
    Spinner selectname, paymentType;
    ArrayList<emp_name> employee_name;

    public static String emp_id, emp_name;

    TextView username, currentAcc;
    Button insertRecord;
    Bitmap bitmap_pro;
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    private ImageView img;
        String image_str;
    SessionManager sessionManager;

    private Uri imageUri, outputUri,uri;
    String selectedPath;
    private static final String IMAGE_DIRECTORY_NAME = "";
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getCmpname();
 //   getCurrentAcc();


        selectname = (Spinner) findViewById(R.id.select_emp_name);
        paymentType = (Spinner) findViewById(R.id.paymentType);
        ename = (EditText) findViewById(R.id.e_name);
        emobile = (EditText) findViewById(R.id.e_mobile);
        info = (EditText) findViewById(R.id.info);
        amtDash = (EditText) findViewById(R.id.amntDash);

        List<String> paymentTyp = new ArrayList<String>();

        paymentTyp.add("Select Payment Type");
        paymentTyp.add("By Current");
        paymentTyp.add("By Saving");
        paymentTyp.add("By Paytm");
        paymentTyp.add("By Phonepe");
        paymentTyp.add("By Raj Kishore Gupta");
        paymentTyp.add("By Ajay Kumar");
        paymentTyp.add("By  Credit Card");
        paymentTyp.add("By  Cash Deposit");
        paymentTyp.add("By  Cash In Hand");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, paymentTyp);
        paymentType.setAdapter(dataAdapter);


        insertRecord = (Button) findViewById(R.id.insertRec);
        insertRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });


        selectname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                emp_name = employee_name.get(position).emp_name.toString();
                emp_id = employee_name.get(position).emp_id.toString();

                String newE = "New Employee";

                //   Log.e("pos",""+pos);


                if (emp_name.matches(newE)) {
                    ename.setVisibility(View.VISIBLE);
                    emobile.setVisibility(View.VISIBLE);
                } else {
                    ename.setVisibility(View.GONE);
                    emobile.setVisibility(View.GONE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        img = (ImageView) findViewById(R.id.selectedImg);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDialog("Slip"    ,img.setImageBitmap(img););
            }
        });


        glry = (FloatingActionButton) findViewById(R.id.glry);
        glry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("click","on click");
        chooseProfilePicFromGallery(01);
            }
        });


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();
            }

        };


        pickdate = (EditText) findViewById(R.id.pickdate);
        pickdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              DatePickerDialog datePickerDialog=  new DatePickerDialog(Dashboard.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
              datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
              datePickerDialog.show();



            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sessionManager = new SessionManager(getApplicationContext());
        username = navigationView.getHeaderView(0).findViewById(R.id.username);
        username.setText("" + sessionManager.getString("user_name").toUpperCase());


        currentAcc = navigationView.getHeaderView(0).findViewById(R.id.Current_Account);


    }


    private Uri getOutputMediaFileUri() {
        if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            return Uri.fromFile(getOutputMediaFile());
        }
        else
            return FileProvider.getUriForFile(getApplicationContext(), getPackageName()+".fileprovider", getOutputMediaFile());

    }


    private File getOutputMediaFile() {

        // External sdcard location
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.e(IMAGE_DIRECTORY_NAME, "Oops! Failed create " + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");

        Log.e("result","media==========="+mediaFile);
        return mediaFile;
    }//get output media file

    private void  chooseProfilePicFromGallery(int code) {

        Log.e("gall","enter");

        if (ActivityCompat.checkSelfPermission(Dashboard.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(Dashboard.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
            return;
        }//if

        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, code);
        Log.e("result", "start gal===========");
    }//choose dp

    private String uploadDp(Bitmap bitmap) {

        // Toast.makeText(getActivity(), "upload", Toast.LENGTH_SHORT).show();
          image_str = "";

        try {
            bitmap = Bitmap.createScaledBitmap(bitmap, 720, 720, true);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
            image_str = Base64.encodeToString(bos.toByteArray(), Base64.DEFAULT);

            //  String data=image_str.replace("\n","\\n");
            // update(image_str);
            // updateProfilePhoto(image_str,bitmap);
        } catch (Exception e) {
            e.printStackTrace();
            //return;
        }

        return image_str;
    }//upload dp

    public void insertData() {

        final String selectEmp1 = emp_id;
        final String ename1 = ename.getText().toString();
        String emobile1 = emobile.getText().toString();
        String info1 = info.getText().toString();
        String amntDash1 = amtDash.getText().toString();
        String date1 = pickdate.getText().toString();
        String payType1 = paymentType.getSelectedItem().toString();




        if (ename.getVisibility()==View.VISIBLE)
        {
            if (ename1.isEmpty()) {
                ename.setError("Enter New Employee Name");
                return;
            }

        }











        if (selectEmp1.matches("null"))
        {
            Toast.makeText(this, "Select Employee Name", Toast.LENGTH_SHORT).show();
            return;
        }



        if (selectEmp1.isEmpty()) {
            selectname.performClick();
            return;
        }




        if (amntDash1.isEmpty()) {
            amtDash.setError("Enter Amount");
            return;
        }

        if (amntDash1.length()<=0) {
            amtDash.setError("Enter Correct Amount");
            return;
        }

        if (payType1.isEmpty()) {
            paymentType.performClick();
            return;
        } else {


            final ProgressDialog pDialog = new ProgressDialog(Dashboard.this);
            pDialog.setMessage("Loading...");
            pDialog.show();
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject();


                String uname1 = sessionManager.getString("user_name");

                jsonObject.put("employee_id", selectEmp1);
                jsonObject.put("user_name", uname1);
                jsonObject.put("new_employee_name", ename1);
                jsonObject.put("employee_mobile", emobile1);
                jsonObject.put("payment_type", payType1);
                jsonObject.put("payment_date", date1);
                jsonObject.put("description", info1);
                jsonObject.put("amount", amntDash1);
                jsonObject.put("slip", image_str );


                Log.e("on request", "  " + Server.URL + "submit_payment" + jsonObject.toString());


                Server.fetchPost(getApplicationContext(), Server.URL + "submit_payment", jsonObject, "", new Server.OnResponseListener() {
                    @Override
                    public void onJSONResponse(Response response, String code) {
                        pDialog.dismiss();


                        try {

                            if (response.data.getString("error").equalsIgnoreCase("0")) {

                                // JSONObject jsonObject1 =response.data;

                                Toast.makeText(Dashboard.this, "" + response.data.getString("message"), Toast.LENGTH_SHORT).show();


                                ename.setText(" ");
                                pickdate.setText(" ");
                                emobile.setText(" ");
                                info.setText(" ");
                                amtDash.setText(" ");





                            } else if (response.data.getString("error").equalsIgnoreCase("1")) {
                                Toast.makeText(Dashboard.this, "" + response.data.getString("message"), Toast.LENGTH_SHORT).show();
                            } else if (response.data.getString("error").equalsIgnoreCase("2")) {
                                Toast.makeText(Dashboard.this, "" + response.data.getString("message"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Dashboard.this, "" + response.data.getString("message"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {

            }


        }

    }


    private void updateLabel() {

        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


        pickdate.setText(sdf.format(myCalendar.getTime()));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        if (requestCode == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, 01);
            Log.e("result", "start gal===========");

        }//if
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                System.out.println("Image Path : " + selectedImagePath);
                img.setImageURI(selectedImageUri);

            }
        }
        if (data != null && resultCode == Activity.RESULT_OK && requestCode == 01) {

            outputUri = getOutputMediaFileUri();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                Log.e(data+"width = " + bitmap.getWidth(), "height = " + bitmap.getHeight()+outputUri);

                // if (bitmap.getWidth() > 1000 || bitmap.getHeight() > 1000) {
                // bitmap = resizeImageForImageView(bitmap);

                imageUri = getOutputMediaFileUri();
                Log.e("image uri","on imageuri==========="+imageUri);

                File file = new File(imageUri.getPath());
                OutputStream outStream = null;

                try {
                    outStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 60, outStream);
                    outStream.flush();
                    outStream.close();

                    if (bitmap != null)
                    {
                        //alertPhoto();
                         uploadDp(bitmap);
                    }
                    bitmap = null;


                    //Crop.of(imageUri, outputUri).asSquare().start(this);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }

                // }


            } catch (Exception ex) {
                ex.printStackTrace();
            }//try
        }//Gallery
    }


    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    //reducing image start


    public static class ImageUtils {

        public static ImageUtils mInstant;

        public static ImageUtils getInstant() {
            if (mInstant == null) {
                mInstant = new ImageUtils();
            }
            return mInstant;
        }

        public Bitmap getCompressedBitmap(String imagePath) {
            float maxHeight = 1920.0f;
            float maxWidth = 1080.0f;
            Bitmap scaledBitmap = null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            Bitmap bmp = BitmapFactory.decodeFile(imagePath, options);

            int actualHeight = options.outHeight;
            int actualWidth = options.outWidth;
            float imgRatio = (float) actualWidth / (float) actualHeight;
            float maxRatio = maxWidth / maxHeight;

            if (actualHeight > maxHeight || actualWidth > maxWidth) {
                if (imgRatio < maxRatio) {
                    imgRatio = maxHeight / actualHeight;
                    actualWidth = (int) (imgRatio * actualWidth);
                    actualHeight = (int) maxHeight;
                } else if (imgRatio > maxRatio) {
                    imgRatio = maxWidth / actualWidth;
                    actualHeight = (int) (imgRatio * actualHeight);
                    actualWidth = (int) maxWidth;
                } else {
                    actualHeight = (int) maxHeight;
                    actualWidth = (int) maxWidth;

                }
            }

            options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
            options.inJustDecodeBounds = false;
            options.inDither = false;
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inTempStorage = new byte[16 * 1024];

            try {
                bmp = BitmapFactory.decodeFile(imagePath, options);
            } catch (OutOfMemoryError exception) {
                exception.printStackTrace();

            }
            try {
                scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
            } catch (OutOfMemoryError exception) {
                exception.printStackTrace();
            }

            float ratioX = actualWidth / (float) options.outWidth;
            float ratioY = actualHeight / (float) options.outHeight;
            float middleX = actualWidth / 2.0f;
            float middleY = actualHeight / 2.0f;

            Matrix scaleMatrix = new Matrix();
            scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

            Canvas canvas = new Canvas(scaledBitmap);
            canvas.setMatrix(scaleMatrix);
            canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

            ExifInterface exif = null;
            try {
                exif = new ExifInterface(imagePath);
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
                Matrix matrix = new Matrix();
                if (orientation == 6) {
                    matrix.postRotate(90);
                } else if (orientation == 3) {
                    matrix.postRotate(180);
                } else if (orientation == 8) {
                    matrix.postRotate(270);
                }
                scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 85, out);

            byte[] byteArray = out.toByteArray();

            Bitmap updatedBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

            return updatedBitmap;
        }


        //reducing image closed


        private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;

            if (height > reqHeight || width > reqWidth) {
                final int heightRatio = Math.round((float) height / (float) reqHeight);
                final int widthRatio = Math.round((float) width / (float) reqWidth);
                inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
            }
            final float totalPixels = width * height;
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
            return inSampleSize;
        }
    }


    public void getCurrentAcc() {

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        String url = "http://guddukumar.com/account/api/current_account.php";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("response", response.toString());
                        pDialog.hide();
                        try {


                            JSONObject obj = new JSONObject();

                            CurrentBal cb = new CurrentBal();



                            String cBal = response.getString("message");


                            Log.e("message", cBal);




                           // employee_name.add(user);
                            currentAcc.setText("Balance "+cBal+"/-");




                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("onError", "Error: " + error.getMessage());
                // hide the progress dialog
                pDialog.hide();
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, "json req");
    }




//        private void getJsonData1(JSONArray array)
//
//        {
//
//            //    JSONArray array=response.getJsonArray("data");
//            employee_name = new ArrayList<>();
//
//            try {
//
//                for (int i = 0; i < array.length(); i++) {
//                    JSONObject obj = array.getJSONObject(i);
//                    emp_name user = new emp_name();
//
//                    String id = obj.getString("employee_id");
//                    String employee = obj.getString("employee_name");
//
//
//                    Log.e("employee_name", employee);
//
//                    user.setEmp_id(id);
//                    user.setEmp_name(employee);
//
//
//                    employee_name.add(user);
//
//                }
//
//                adapter = new emp_nameAdapter(Dashboard.this, employee_name);
//                selectname.setAdapter(adapter);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
















    private void getCmpname() {


        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        String url = "http://guddukumar.com/account/api/employee_list.php";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("response", response.toString());
                        pDialog.hide();
                        try {


                                JSONArray array = response.getJSONArray("list");

                                getJsonData(array);




                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("onError", "Error: " + error.getMessage());
                // hide the progress dialog
                pDialog.hide();
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, "json req");
    }


    private void getJsonData(JSONArray array)

    {

        //    JSONArray array=response.getJsonArray("data");
        employee_name = new ArrayList<>();

        try {

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                emp_name user = new emp_name();

                String id = obj.getString("employee_id");
                String employee = obj.getString("employee_name");


                Log.e("employee_name", employee);

                user.setEmp_id(id);
                user.setEmp_name(employee);


                employee_name.add(user);

            }

            adapter = new emp_nameAdapter(Dashboard.this, employee_name);
            selectname.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }







    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {

            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        getCurrentAcc();
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.new_emp)
        {
            Intent details = new Intent(getApplicationContext(),Add_Employee.class);
            startActivity(details);

        }
        else if (id == R.id.payment_Details)
        {

            Intent details = new Intent(getApplicationContext(),Payment_Details.class);
            startActivity(details);

        }

        else if (id == R.id.receive_pay)
        {

            Intent details = new Intent(getApplicationContext(),Received_Payment.class);
            startActivity(details);

        }

        else if (id == R.id.history)
        {
            Intent history = new Intent(getApplicationContext(),History.class);
            startActivity(history);

        }

        else if (id == R.id.logout)
        {
            sessionManager.clear();
            Intent details = new Intent(getApplicationContext(),Login.class);
            startActivity(details);
            finish();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;






        }

}
