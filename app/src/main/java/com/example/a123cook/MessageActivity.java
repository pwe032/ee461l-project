package com.example.a123cook;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The MessageActivity class allows the user to send messages and start conversations
 * By: Gaurav Nagar
 * Date: 4/29/17
 */
public class MessageActivity extends MainActivity {

    private FirebaseListAdapter<Message> adapter;
    private Firebase database;
    private Firebase messageDatabaseRef;
    private String sender;
    private EditText message;
    private ListView messageList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);
        Firebase.setAndroidContext(this);

        message = (EditText) findViewById(R.id.message_text);
        messageList = (ListView) findViewById(R.id.listView);

        database = new Firebase("https://cook-6d9e0.firebaseio.com/");
        messageDatabaseRef = database.child("messages");
        System.out.println("LOG: CREATED messages database.");

        adapter = new FirebaseListAdapter<Message>(this, Message.class, R.layout.message, messageDatabaseRef) {
            @Override
            protected void populateView(View view, Message message) {
                ((TextView)view.findViewById(R.id.username_text_view)).setText(message.getSenderName());
                ((TextView)view.findViewById(R.id.message_text_view)).setText(message.getMessage());
            }
        };

        messageList.setAdapter(adapter);
    }

    public void onSendButtonClick(View view){
        String senderID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final String sendMessage = message.getEditableText().toString();//message to be sent
        if (!sendMessage.isEmpty()) {
            database.child("users").child(senderID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    String senderName = (String) ((Map<String, Object>) snapshot.getValue()).get("name");
                    String senderEmail = (String) ((Map<String, Object>) snapshot.getValue()).get("email");
                    String senderID = (String) ((Map<String, Object>) snapshot.getValue()).get("userID");
                    System.out.println(((Map<String, Object>) snapshot.getValue()).get("attemptedRecipes"));
                    User user = new User(senderID, senderEmail, senderName, null, null); //fix this
                    messageDatabaseRef.push().setValue(new Message(sendMessage, user));
                }

                @Override
                public void onCancelled(FirebaseError error) {
                }
            });

            message.setText("");
        }
    }

}
