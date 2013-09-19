package com.example.locationfinder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;

public class MainActivity extends Activity {

	TextView textView;
	GpsService gps;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView = (TextView) findViewById(R.id.AddressText);
		StringBuilder strbuilder = new StringBuilder("Address:\n");
		Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
		gps = new GpsService(MainActivity.this);
		if (gps.canGetLocation()) {
			try {
				if (geocoder.isPresent()) {
					Toast.makeText(
							getApplicationContext(),
							"Your Location is - \nAdress: " + gps.getLatitude()
									+ gps.getLongitude(), Toast.LENGTH_LONG)
							.show();
					List<Address> address = geocoder.getFromLocation(
							gps.getLatitude(), gps.getLongitude(), 1);			
					if (address != null && address.size() > 0) {
						Address returnadress = address.get(0);
						for (int i = 0; i < returnadress
								.getMaxAddressLineIndex(); i++) {							
							strbuilder.append(returnadress.getAddressLine(i))
									.append("\n");
						}

					} else {
						strbuilder.append("Addres Not Found \n");
					}

					textView.setText(strbuilder.toString());
					
				} else {
					Toast.makeText(getApplicationContext(),
							"Geocode Not Present ", Toast.LENGTH_LONG).show();
				}
			} catch (IOException io) {
				System.out.println("Exception" + io.getMessage());
			}
		}

	}
	
	public class MyReceiver extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context context, Intent intent) {
			
		String adress = intent.getStringExtra("Address");
		
		Toast.makeText(context, "Address ::", Toast.LENGTH_LONG).show();
		}
		
	}

}