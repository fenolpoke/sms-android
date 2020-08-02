package my.pack.qualification;

import my.pack.qualification.R.color;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class HomeActivity extends ActionBarActivity implements OnClickListener {

	RelativeLayout layout;
	int toggle = 0;
	Activity activity = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		layout = ((RelativeLayout) findViewById(R.id.homeLayout));

		Log.e("on cursorc", "before linear");
		LinearLayout linear = (LinearLayout) findViewById(R.id.usernameLinearLayout);

		DatabaseHelper dbHelper = new DatabaseHelper(activity);
		SQLiteDatabase db = dbHelper.getWritableDatabase();


		Cursor cu = db.query("users", null,null, null, null, null, null);
		while(cu.moveToNext())
		{
			TextView text = new TextView(this);
			text.setText(cu.getString(2));
			linear.addView(text);
		}
		cu.close();
		db.close();
		dbHelper.close();

		Log.e("on cursorc", "after linear");

		Log.e("on add action listener", "before listen");

		((Button) findViewById(R.id.smsButton)).setOnClickListener(this);
		((ToggleButton) findViewById(R.id.backgroundToggleButton)).setOnClickListener(this);

		Log.e("on add action listener", "after listen");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == findViewById(R.id.smsButton)){

			if(((EditText)findViewById(R.id.phoneEditText)).getText().toString().trim().equalsIgnoreCase("")){
				Toast.makeText(this, "Input the phone number!", Toast.LENGTH_SHORT).show();
			}else if(((EditText)findViewById(R.id.messageEditText)).getText().toString().trim().equalsIgnoreCase("")){
				Toast.makeText(this, "Input the message!", Toast.LENGTH_SHORT).show();
			}else{

				String SMS_SENT = "SMS_SENT";
				String SMS_DELIVERED = "SMS_DELIVERED";

				PendingIntent sentPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT), 0);
				PendingIntent deliveredPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);

				// For when the SMS has been sent
				registerReceiver(new BroadcastReceiver() {
					@Override
					public void onReceive(Context context, Intent intent) {
						switch (getResultCode()) {
						case Activity.RESULT_OK:
							Toast.makeText(context, "SMS sent successfully", Toast.LENGTH_SHORT).show();
							break;
						case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
							Toast.makeText(context, "Generic failure cause", Toast.LENGTH_SHORT).show();
							break;
						case SmsManager.RESULT_ERROR_NO_SERVICE:
							Toast.makeText(context, "Service is currently unavailable", Toast.LENGTH_SHORT).show();
							break;
						case SmsManager.RESULT_ERROR_NULL_PDU:
							Toast.makeText(context, "No pdu provided", Toast.LENGTH_SHORT).show();
							break;
						case SmsManager.RESULT_ERROR_RADIO_OFF:
							Toast.makeText(context, "Radio was explicitly turned off", Toast.LENGTH_SHORT).show();
							break;
						}
					}

				}, new IntentFilter(SMS_SENT));

				// For when the SMS has been delivered
				registerReceiver(new BroadcastReceiver() {
					@Override
					public void onReceive(Context context, Intent intent) {
						switch (getResultCode()) {
						case Activity.RESULT_OK:
							Toast.makeText(getBaseContext(), "SMS delivered", Toast.LENGTH_SHORT).show();
							break;
						case Activity.RESULT_CANCELED:
							Toast.makeText(getBaseContext(), "SMS not delivered", Toast.LENGTH_SHORT).show();
							break;
						}
					}
				}, new IntentFilter(SMS_DELIVERED));

				// Get the default instance of SmsManager
				SmsManager smsManager = SmsManager.getDefault();
				// Send a text based SMS
				smsManager.sendTextMessage(((EditText)findViewById(R.id.phoneEditText)).getText().toString(), "5554 dammit", 
						((EditText)findViewById(R.id.messageEditText)).getText().toString(), sentPendingIntent, deliveredPendingIntent);
			}
		}else if(v == findViewById(R.id.backgroundToggleButton)){

			Toast.makeText(this, toggle%2 == 0? "Color: cadetblue" : "Color: alice", Toast.LENGTH_SHORT).show();

			int now = toggle%2 == 0? R.color.CadetBlue : R.color.AliceBlue;

			layout.setBackgroundColor(now);

			toggle = (toggle+1) % 2;
		}
	}
}

