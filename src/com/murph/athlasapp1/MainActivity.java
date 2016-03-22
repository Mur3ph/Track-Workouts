package com.murph.athlasapp1;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity 
{

	private String username;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button btnExercise = (Button) findViewById(R.id.exercise);
		Button btnProfile = (Button) findViewById(R.id.profile);
		Button btnSocial = (Button) findViewById(R.id.social);
		
		TextView uname = (TextView) findViewById(R.id.txtAthletesUname);
		Intent i = getIntent();
	    username = i.getExtras().getString("username");
		uname.setText(username);
		 
		//Access to exercise work out of application
		btnExercise.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v) 
			{
                // Switching to work out screen
                Intent i = new Intent(getApplicationContext(), Exercise1.class);
                i.putExtra("username", username);
                startActivity(i);
            }
	});
		
		
		//Access to user profile view of application
				btnProfile.setOnClickListener(new View.OnClickListener()
				{
					public void onClick(View v) 
					{
		                // Switching to Profile screen
		                Intent i = new Intent(getApplicationContext(), Profile1.class);
		                i.putExtra("username", username);
		                startActivity(i);
		            }
			});
				
				//Access to user profile view of application
				btnSocial.setOnClickListener(new View.OnClickListener()
				{
					public void onClick(View v) 
					{
		                // Switching to Profile screen
		                Intent i = new Intent(getApplicationContext(), Social1.class);
		                i.putExtra("username", username);
		                startActivity(i);
		            }
			});
		
	}

	// Getting data from another activity!
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
//	{
//		// TODO Auto-generated method stub
//		super.onActivityResult(requestCode, resultCode, data);
//		
//		if(resultCode == RESULT_OK)
//		{
//			Bundle extras = data.getExtras();
//			s = (String) extras.get("data");
//			uname.setText(s);
//		}
//		
//	}

	

}
