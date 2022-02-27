package com.example.testproject;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import android.content.Intent;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class BackgroundWorker extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    BackgroundWorker(Context ctx){
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {

        String type = params[0];
        String login_URL = "https://2207dbwebsite.000webhostapp.com/Login.php";
        String register_URL = "https://2207dbwebsite.000webhostapp.com/Register.php";
        String addcard_URL = "https://2207dbwebsite.000webhostapp.com/AddCard.php";

        if (type.equals("login")){
            try {
                String email = params[1];
                String password = params[2];
                URL url = new URL(login_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                //CODE RUNS UNTIL HERE FOR SIGNIN-----------------------------------------
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                //TO replace email and pass below
                String post_data = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!=null){
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println();
            }
        } else if (type.equals("register")) {
            try {
                String rname = params[1];
                String remail = params[2];
                String rpassword = params[3];
                String rdob = params[4];

                URL url = new URL(register_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                //CODE RUNS UNTIL HERE FOR SIGNUP-----------------------------------------
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("rname", "UTF-8") + "=" + URLEncoder.encode(rname, "UTF-8") + "&"
                        + URLEncoder.encode("remail", "UTF-8") + "=" + URLEncoder.encode(remail, "UTF-8") + "&"
                        + URLEncoder.encode("rpassword", "UTF-8") + "=" + URLEncoder.encode(rpassword, "UTF-8") + "&"
                        + URLEncoder.encode("rdob", "UTF-8") + "=" + URLEncoder.encode(rdob, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println();
            }
        }else if (type.equals("addcard")){
            try {
                String cardname = params[1];
                String cardnumber = params[2];
                String cardcvv = params[3];
                String cardexpirydate = params[4];

                URL url = new URL(addcard_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                //---------------------------------------------------
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("cardname","UTF-8")+"="+URLEncoder.encode(cardname,"UTF-8")+"&"
                        +URLEncoder.encode("cardnumber","UTF-8")+"="+URLEncoder.encode(cardnumber,"UTF-8")+"&"
                        +URLEncoder.encode("cardcvv","UTF-8")+"="+URLEncoder.encode(cardcvv,"UTF-8")+"&"
                        +URLEncoder.encode("cardexpirydate","UTF-8")+"="+URLEncoder.encode(cardexpirydate,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!=null){
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println();
            }

        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
    }
    @Override
    protected void onPostExecute(String result) {
        if(result.toString().equals("Login successful"))
        {   alertDialog.setMessage("Login successful");
            alertDialog.show();
            Intent intent = new Intent (context, AddCard.class);
            context.startActivity(intent);
        }
        else if(result.toString().equals("Login not successful"))
        {
            alertDialog.setMessage("Login not successful");
            alertDialog.show();
        }
        else if(result.toString().equals("Registration successful"))
        {
            alertDialog.setMessage("Registration successful");
            alertDialog.show();
            Intent intent = new Intent (context, AddCard.class);
            context.startActivity(intent);
        }
        else if(result.toString().equals("Failed to register"))
        {
            alertDialog.setMessage("Failed to register, please try again");
            alertDialog.show();
        }
    }
    /*@Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }*/
}

