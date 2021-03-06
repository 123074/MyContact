package com.example.user.mycontact;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddContactActivity extends AppCompatActivity implements DatabaseUpdatedListener{
    private int MENU_UPDATE = Menu.FIRST;
    private Boolean UPDATE = false;
    EditText editText_name, editText_phone, editText_email;
    Contact contact;
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        databaseHelper = new DatabaseHelper(AddContactActivity.this);
        databaseHelper.databaseUpdatedListener = this;
        editText_name = (EditText) findViewById(R.id.edit_name);
        editText_phone = (EditText) findViewById(R.id.edit_phone);
        editText_email = (EditText) findViewById(R.id.edit_email);
        if (getIntent().getSerializableExtra("contact") != null) {
            contact = (Contact) getIntent().getSerializableExtra("contact");
            editText_name.setText(contact.getName());
            editText_phone.setText(contact.getPhone());
            editText_email.setText(contact.getEmail());
            UPDATE = true;
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuItem item = menu.add(0, MENU_UPDATE, Menu.NONE, "Send").setIcon(R.drawable.ic_done_white_24dp);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        String name = editText_name.getText().toString();
        String phone = editText_phone.getText().toString();
        String email = editText_email.getText().toString();
        //noinspection SimplifiableIfStatement
        if (id == MENU_UPDATE) {

            if (UPDATE) {
                Contact c = new Contact(contact.get_id(), name, phone, email);
                Log.d("data", name + " " + phone + " " + email);
                UPDATE = false;
                databaseHelper.updateContact(c);
            } else {
                Contact c = new Contact(name, phone, email);
                Log.d("data", name + " " + phone + " " + email);
                databaseHelper.addContact(c);
            }
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            intent.putExtra("restart", true);
//            setResult(RESULT_OK, intent);
//            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void setDatabaseSuccess(String name, String phone, String email) {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    @Override
    public void setDatabaseError(String failureMessage) {
        Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();
    }
}
