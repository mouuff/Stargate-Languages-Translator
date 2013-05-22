package com.mou.stargatetranslator;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.graphics.*;
import android.view.View.*;
import android.content.*;
import android.graphics.*;
import android.provider.*;
import java.net.*;
import java.io.*;
import android.text.*;
import android.net.*;

public class MainActivity extends Activity
{
    /** Called when the activity is first created. */
	@Override
	private Bitmap getTransparentBitmapCopy(Bitmap source)
	{
		int width =  source.getWidth();
		int height = source.getHeight();
		Bitmap copy = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		int[] pixels = new int[width * height];
		source.getPixels(pixels, 0, width, 0, 0, width, height);
		copy.setPixels(pixels, 0, width, 0, 0, width, height);
		return copy;
	}
	final Context context = this;
	int choice = 0;
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		final TextView output = (TextView) findViewById(R.id.output);
		final TextView hello = (TextView) findViewById(R.id.hello);
		final EditText input = (EditText) findViewById(R.id.input);
		
		Button convert = (Button) findViewById(R.id.convert);
		Button showAlphabet = (Button) findViewById(R.id.showAlphabet);
		Button changeLangage = (Button) findViewById(R.id.changeLangage);
		
		Typeface Ancient = Typeface.createFromAsset(getAssets(),"fonts/anquietas.ttf");
		Typeface Wraith = Typeface.createFromAsset(getAssets(),"fonts/wraith.ttf");
		Typeface Asgard = Typeface.createFromAsset(getAssets(),"fonts/asgard.ttf");
		Typeface Goauld1 = Typeface.createFromAsset(getAssets(),"fonts/goa_uld1.ttf");
		Typeface Furling = Typeface.createFromAsset(getAssets(),"fonts/Furling.ttf");
		Typeface Nox = Typeface.createFromAsset(getAssets(),"fonts/Nox.ttf");
		
		final Typeface[] Languages= {Ancient,Asgard,Wraith,Goauld1, Furling, Nox};
		final String[] LanguagesNames = {"Ancient","Asgard","Wraith","Goa'uld (decorative)", "Furling","Nox"};
		final int[] LanguagesSize = {35, 23, 45, 27,33,25};
		
		hello.setTextSize(15);
		output.setTextSize(25);
		
		output.setTypeface(Ancient);
		
		input.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s){
				output.setText(input.getText().toString());
			}
			public void beforeTextChanged(CharSequence s,int start, int count, int after){}
			public void onTextChanged(CharSequence s,int start, int count, int after){}
		});
		
		changeLangage.setOnClickListener(new OnClickListener(){
			public void onClick(View p1){
				new AlertDialog.Builder(context)
				.setSingleChoiceItems(LanguagesNames,choice,null)
				.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton){
						dialog.dismiss();
						int pos = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
						choice = pos;
						output.setTypeface(Languages[choice]);
						output.setTextSize(LanguagesSize[choice]);
						Toast.makeText(getApplicationContext(),LanguagesNames[choice],Toast.LENGTH_SHORT).show();
					}
				}).show();
			}
		});
		
		convert.setOnClickListener(new OnClickListener(){
			public void onClick(View p1){
				TextView glyphs = output;
				glyphs.setDrawingCacheEnabled(true);
				glyphs.destroyDrawingCache();
				Bitmap image = getTransparentBitmapCopy(glyphs.getDrawingCache());
				MediaStore.Images.Media.insertImage(getContentResolver(),image,input.getText().toString(),"Ancient");
				
				Toast.makeText(getApplicationContext(),"Saved glyphs to your gallery ready to share!",Toast.LENGTH_LONG).show();
			}
		});
		
		showAlphabet.setOnClickListener(new OnClickListener() {
			public void onClick(View p1){
				final Dialog dialog = new Dialog(context);
				dialog.setContentView(R.layout.alphabet);
				ImageView image = (ImageView) dialog.findViewById(R.id.image);
				if (choice == 0){
					dialog.setTitle("Ancient Alphabet");
					image.setImageResource(R.drawable.ancientglyphs);
				}
				else if (choice == 1){
					dialog.setTitle("Asgard Alphabet");
					image.setImageResource(R.drawable.asgardalphabet);
				}
				else if (choice == 2){
					dialog.setTitle("Wraith Alphabet");
					image.setImageResource(R.drawable.wraith);
				}
				else if (choice == 3){
					dialog.setTitle("One of Goa'uld's Alphabet");
					image.setImageResource(R.drawable.goa_uld1);
				}
				else if (choice == 4){
					dialog.setTitle("Furling Alphabet");
					image.setImageResource(R.drawable.furling);
				}
				else{
					dialog.setTitle("Nox Alphabet");
					image.setImageResource(R.drawable.nox);
				}
				Button ok = (Button) dialog.findViewById(R.id.dimiss);
				
				ok.setTypeface(output.getTypeface());
				ok.setTextSize(LanguagesSize[choice]);
				
				ok.setOnClickListener(new OnClickListener() {
					public void onClick(View v){
						dialog.dismiss();
					}
				});
				dialog.show();
			}
		});
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.layout.menu, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case R.id.emailme:
			    Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("message/rfc822");
				i.putExtra(Intent.EXTRA_EMAIL, new String[]{"arnaudalies.py@gmail.com"});
				i.putExtra(Intent.EXTRA_SUBJECT,"Feedback of Stargate langages translator");
				try{
					startActivity(Intent.createChooser(i,"Send mail..."));
				}
				catch (android.content.ActivityNotFoundException e){
					Toast.makeText(getApplicationContext(),"no email client found...", Toast.LENGTH_SHORT).show();
				}
				return true;
			
			case R.id.rateit:
			    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.mou.stargatetranslator")));
				return true;
			
			default:
			    return false;
		}
	}
}
