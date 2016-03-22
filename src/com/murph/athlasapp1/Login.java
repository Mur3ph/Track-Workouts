package com.murph.athlasapp1;

import com.murph.db.DatabaseController;
import com.murph.db.DatabaseHandler;
import com.murph.objects.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener 
{
	DatabaseController m_MyDatabaseController;
	EditText m_Username;
	EditText m_Password;
	Button m_Login;
	TextView m_registerScreen;
	TextView m_Welcome;

	User u = null;
	DatabaseHandler m_MyDBHandler = null;

	@Override
	protected void onCreate(Bundle myBundle) 
	{
		super.onCreate(myBundle);

		setContentView(R.layout.login);

		m_Welcome = (TextView) findViewById(R.id.txtWelcome);

		m_registerScreen = (TextView) findViewById(R.id.link_to_register);
		m_registerScreen.setOnClickListener(this);

		m_Login = (Button) findViewById(R.id.login);
		m_Login.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) 
	{

		switch (v.getId()) {
		case R.id.link_to_register:
			Intent i = new Intent(getBaseContext(), Register.class);
			startActivity(i);
			break;

		case R.id.login:

			m_Username = (EditText) findViewById(R.id.edtLoginUsername);
			m_Password = (EditText) findViewById(R.id.edtLoginPassword);

			String username = m_Username.getText().toString();
			String password = m_Password.getText().toString();

			if (username.equals("") || username == null) {
				Toast.makeText(getApplicationContext(),
						"Please enter User details!", Toast.LENGTH_SHORT)
						.show();
			} else if (password.equals("") || password == null) {
				Toast.makeText(getApplicationContext(),
						"Please enter User details!", Toast.LENGTH_SHORT)
						.show();
			} else {
				
				m_MyDatabaseController = new DatabaseController(this);
				m_MyDatabaseController.open();
				User u = m_MyDatabaseController.findUser(username, password);

				if (u != null) {
//					System.out.println("Login Success!");
						Intent ii = new Intent(Login.this, MainActivity.class);
						ii.putExtra("username", username);
						startActivity(ii);
				} else { 
//					System.out.println("Login Failure!");
					Toast.makeText(getApplicationContext(),
							"Not a member, innit!!", Toast.LENGTH_SHORT).show();
				}
			}
			break;
		}

	} // End of onClick method.

	public void onDestroy() 
	{
		super.onDestroy();
		m_MyDBHandler.close();
	}
	
	public int multiply(int x, int y) 
	{
	    // the following is just an example
	    if (x > 999) {
	      throw new IllegalArgumentException("X should be less than 1000");
	    }
	    return x / y;
	  }

}
