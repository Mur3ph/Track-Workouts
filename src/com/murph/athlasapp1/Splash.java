package com.murph.athlasapp1;

import com.murph.athlasapp1.R;
import com.murph.athlasapp1.Splash;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

public class Splash extends Activity{
	
MediaPlayer m_ourSound;
	
	@Override
	protected void onCreate(Bundle myBundle) 
	{
		super.onCreate(myBundle);
		
		setContentView(R.layout.splash);
	    m_ourSound = MediaPlayer.create(Splash.this, R.raw.splash_sound);
		m_ourSound.start();
		Thread timer = new Thread()
		{
			public void run()
			{
				try
				{
					// Five seconds
					sleep(5000);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
				finally
				{
					Intent openMainActivity = new Intent("com.murph.athlasapp1.STARTINGPOINT");
					startActivity(openMainActivity);
				}
			}
		};
		
		timer.start();
		
	}

		@Override
		protected void onPause()
		{
			super.onPause();
			m_ourSound.release();
			finish();
		}

}
