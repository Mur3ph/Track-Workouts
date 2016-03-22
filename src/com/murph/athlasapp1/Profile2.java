package com.murph.athlasapp1;

import com.murph.db.DatabaseController;
import com.murph.objects.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Profile2 extends Activity implements OnClickListener
{

	// Lots to do to tidy this section up.
	
	EditText m_Name;
	EditText m_Age;
	EditText m_Gender;
	EditText m_About;
	EditText m_City;
	EditText m_Country;
	
	Button m_Save;
	
	User u;
	DatabaseController m_MyDbController;
	String m_Uname;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile2_edit);
		
		m_Save = (Button) findViewById(R.id.btnProfileSave);
		m_Save.setOnClickListener(this);
		
		 m_Name = (EditText) findViewById(R.id.edtProfileEditName);
		 m_Age = (EditText) findViewById(R.id.edtProfileEditAge);
		 m_Gender = (EditText) findViewById(R.id.edtProfileEditGender);
		 m_About = (EditText) findViewById(R.id.edtProfileEditAbout);
		 m_City = (EditText) findViewById(R.id.edtProfileEditCity);
		 m_Country = (EditText) findViewById(R.id.edtProfileEditCountry);
		 
		 	Intent i = getIntent();
			m_Uname = i.getExtras().getString("username");
			m_Name.setText(m_Uname);
			
			m_MyDbController = new DatabaseController(this);
			m_MyDbController.open();
			u = m_MyDbController.findUser1(m_Uname);
			
				m_Age.setText(u.getAge());
				m_Gender.setText(u.getSex());
				m_About.setText(u.getAbout());
				m_City.setText(u.getCounty());
				m_Country.setText(u.getCountry());
			
	}
	
	@Override
	public void onClick(View v) 
	{
		
		switch(v.getId()){

		 case R.id.btnProfileSave:
			 
			    String name = m_Name.getText().toString();
				String about = m_About.getText().toString();
				String gender = m_Gender.getText().toString();
				String age = m_Age.getText().toString();
				String city = m_City.getText().toString();
				String country = m_Country.getText().toString();
				
				if (m_Uname.length() > 0 && name.length() > 0 && about.length() > 1
						&& gender.length() > 0 && age.length() > 0) {
					
					DatabaseController dbC = new DatabaseController(this);
					
					dbC.open();
					
					dbC.updateUser(name, about, gender, age, city, country, m_Uname, u.getUserId(), u.getPassword());

					Toast.makeText(Profile2.this, "User details Updated, yahoo!",
							Toast.LENGTH_LONG).show();
					
					dbC.close();
					
//					 m_Name.setText("");
//					 m_About.setText("");
//					 m_Gender.setText("");
//					 m_Age.setText("");
//					 m_City.setText("");
//					 m_Country.setText("");
					
				}
				else
				{
					
					Toast.makeText(Profile2.this, "You must fill out all fields",
							Toast.LENGTH_LONG).show();
					
				}
				
				
				
		 break;
		}
		
	}

}
