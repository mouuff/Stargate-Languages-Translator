package com.mou.stargatetranslator;

import com.google.ads.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.view.View.*;
import android.widget.AdapterView.*;
import android.widget.MediaController.*;
import android.media.*;
import android.net.Uri;
import android.content.res.*;
import android.content.Intent;
import android.content.*;
import android.provider.*;

public class Banner extends Activity {
	private AdView adView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Create the adView
		adView = new AdView(this, AdSize.BANNER, "a151b7590ca7869");

		// Lookup your LinearLayout assuming it's been given
		// the attribute android:id="@+id/mainLayout"
		LinearLayout layout = (LinearLayout)findViewById(R.id.mainLayout);

		// Add the adView to it
		layout.addView(adView);

		// Initiate a generic request to load it with an ad
		adView.loadAd(new AdRequest());
	}

	@Override
	public void onDestroy() {
		if (adView != null) {
			adView.destroy();
		}
		super.onDestroy();
	}
}
