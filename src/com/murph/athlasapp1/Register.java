package com.murph.athlasapp1;
import java.util.List;

import com.murph.db.DatabaseController;
import com.murph.db.DatabaseHandler;
import com.murph.objects.User;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends FragmentActivity implements OnClickListener, OnItemSelectedListener
{
	
	 private DatabaseController m_MyDatabaseController;
	 private Button m_Register;
	 private EditText m_Fullname;
	 private EditText m_Username;
	 private EditText m_Password;
	 private TextView m_LoginScreen;

	 protected DatabaseHandler m_MyDBHandler = new DatabaseHandler(Register.this); 
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.register);
        
        //Assignment of UI fields to the variables
        m_Register = (Button)findViewById(R.id.btnRegister);
        m_Register.setOnClickListener(this);

        m_Fullname = (EditText)findViewById(R.id.reg_fullname);

        m_Username = (EditText)findViewById(R.id.reg_username);
        m_Password = (EditText)findViewById(R.id.reg_password);
 
        m_LoginScreen = (TextView) findViewById(R.id.link_to_login);
        m_LoginScreen.setOnClickListener(this);
		 
    }
	
	

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) 
	{
		
	}

	@Override
	public void onClick(View arg0) 
	{
		 switch(arg0.getId())
		 {

		 case R.id.link_to_login:
			 Intent i = new Intent(getApplicationContext(), Login.class);
			 startActivity(i);
			 //finish();
		 break;

		 case R.id.btnRegister:
			 Intent i_register = new Intent(Register.this, Login.class);

			    String fname = m_Fullname.getText().toString();
				String uname = m_Username.getText().toString();
				String pword = m_Password.getText().toString();
			
				// If all fields have data
				if (fname.length() > 0 && uname.length() > 0 && pword.length() > 0) {

					//Makes sure value entered for the worth field is between 0 and 100
					//int percentage = Integer.parseInt(worth);

						m_MyDatabaseController = new DatabaseController(this);
						m_MyDatabaseController.open();

						List<User> users = m_MyDatabaseController.getAllUsers();

						// Use the SimpleCursorAdapter to show the
						// elements in a ListView
						ArrayAdapter<User> adapter = new ArrayAdapter<User>(
								this, android.R.layout.simple_list_item_1,
								users);

						User u;

						u = m_MyDatabaseController.createUser(fname, uname, pword);
//
						adapter.add(u);
						adapter.notifyDataSetChanged();
						startActivity(i_register);

						m_Fullname.setText("");
						m_Username.setText("");
						m_Password.setText("");

						Toast.makeText(Register.this, "User details Added",
								Toast.LENGTH_LONG).show();
				
				} else {
					Toast.makeText(Register.this, "You must fill out all fields",
							Toast.LENGTH_LONG).show();
				}

				break;
			 
		 }
		 
	} // End of onClick()
	
	
	
	 public void onDestroy()
	 {
		 super.onDestroy();
		 m_MyDBHandler.close();
	 }


}
