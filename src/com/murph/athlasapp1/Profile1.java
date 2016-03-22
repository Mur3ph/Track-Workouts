package com.murph.athlasapp1;

import com.murph.db.DatabaseController;
import com.murph.objects.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Profile1 extends Activity implements OnClickListener
{

	//TODO I want to set up a content provider so the user can upload or change 
	// profile pictures from their phone gallery.
	
	DatabaseController m_MyDbController;
	String m_Fullname;
	TextView m_Edit;
	User u;
	ImageView m_ProfileImageV;
	
	Button m_Routes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile1_home);
		
	    m_Edit = (TextView) findViewById(R.id.txtProfileEditBtn);
		m_Edit.setOnClickListener(this);
		m_Routes = (Button) findViewById(R.id.btnCheckWorkoutRoutes);
		m_Routes.setOnClickListener(this);
		
		TextView name = (TextView) findViewById(R.id.txtProfileName);
		TextView age2 = (TextView) findViewById(R.id.txtProfileAge1);
		TextView age = (TextView) findViewById(R.id.txtProfileAge);
		TextView gender = (TextView) findViewById(R.id.txtProfileSex);
		TextView about = (TextView) findViewById(R.id.txtProfileAbout);
		TextView city = (TextView) findViewById(R.id.txtProfileCity);
		TextView country = (TextView) findViewById(R.id.txtProfileCountry);
		
//		profilePic = (ImageView) findViewById(R.id.profile_image);
//		profilePic.setImageResource(R.drawable.mise);
		
		Intent i = getIntent();
		m_Fullname = i.getExtras().getString("username");
		name.setText(m_Fullname);
		
		m_MyDbController = new DatabaseController(this);
		m_MyDbController.open();
		u = m_MyDbController.findUser1(m_Fullname);
		
			age.setText(u.getAge());
			gender.setText(u.getSex());
			about.setText(u.getAbout());
			age2.setText(u.getAge()); 
			city.setText(u.getCounty());
			country.setText(u.getCountry());
		
		
	}

	@Override
	public void onClick(View v) 
	{
		
		switch (v.getId()) {
		case R.id.txtProfileEditBtn:
			Intent i = new Intent(getBaseContext(), Profile2.class);
			i.putExtra("username", m_Fullname);
			startActivity(i);
			break;
		case R.id.btnCheckWorkoutRoutes:
			Intent ii = new Intent(getBaseContext(), ListAllRoutes.class);
			startActivity(ii);
			break;
		}
		
	}
	
}
