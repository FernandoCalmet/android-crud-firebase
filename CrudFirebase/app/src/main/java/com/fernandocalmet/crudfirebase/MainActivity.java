package com.fernandocalmet.crudfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.fernandocalmet.crudfirebase.model.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    EditText userFirstName, userLastName, userEmail, userPassword;
    ListView listViewUsers;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private List<User> users = new ArrayList<User>();
    ArrayAdapter<User> adapterUsers;
    User userSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userFirstName = findViewById(R.id.txtFirstName);
        userLastName = findViewById(R.id.txtLastName);
        userEmail = findViewById(R.id.txtEmail);
        userPassword = findViewById(R.id.txtPassword);
        listViewUsers = findViewById(R.id.lvUserData);

        initFirebase();
        listUserData();

        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                userSelected = (User) adapterView.getItemAtPosition(i);
                userFirstName.setText(userSelected.getFirstName());
                userLastName.setText(userSelected.getLastName());
                userEmail.setText(userSelected.getEmail());
                userPassword.setText(userSelected.getPassword());
            }
        });
    }

    private void listUserData() {
        databaseReference.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for(DataSnapshot data : snapshot.getChildren()){
                    User user = data.getValue(User.class);
                    users.add(user);
                    adapterUsers = new ArrayAdapter<User>(MainActivity.this, android.R.layout.simple_list_item_1, users);
                    listViewUsers.setAdapter(adapterUsers);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        /*firebaseDatabase.setPersistenceEnabled(true);*/ //using just without fragments
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        String firstName = userFirstName.getText().toString();
        String lastName = userLastName.getText().toString();
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();

        switch(item.getItemId()){
            case R.id.icon_add:
                if(firstName.equals("") || lastName.equals("") || email.equals("") || password.equals("")){
                    validateData();
                } else{
                    User user = new User();
                    user.setUid(UUID.randomUUID().toString());
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setEmail(email);
                    user.setPassword(password);
                    databaseReference.child("User").child(user.getUid()).setValue(user);
                    Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
                    cleanData();
                }
                break;
            case R.id.icon_edit:
                User userEdited = new User();
                userEdited.setUid(userSelected.getUid());
                userEdited.setFirstName(userFirstName.getText().toString().trim());
                userEdited.setLastName(userLastName.getText().toString().trim());
                userEdited.setEmail(userEmail.getText().toString().trim());
                userEdited.setPassword(userPassword.getText().toString().trim());
                databaseReference.child("User").child(userEdited.getUid()).setValue(userEdited);
                Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
                cleanData();
                break;
            case R.id.icon_delete:
                User userDeleted = new User();
                userDeleted.setUid(userSelected.getUid());
                databaseReference.child("User").child(userDeleted.getUid()).removeValue();
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                cleanData();
                break;
            default: break;
        }
        return true;
    }

    private void cleanData() {
        userFirstName.setText("");
        userLastName.setText("");
        userEmail.setText("");
        userPassword.setText("");
    }

    private void validateData() {
        String firstName = userFirstName.getText().toString();
        String lastName = userLastName.getText().toString();
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();

        if(firstName.equals("")){
            userFirstName.setError("Required");
        } else if(lastName.equals("")){
            userLastName.setError("Required");
        } else if(email.equals("")){
            userEmail.setError("Required");
        } else if(password.equals("")){
            userPassword.setError("Required");
        }
    }
}