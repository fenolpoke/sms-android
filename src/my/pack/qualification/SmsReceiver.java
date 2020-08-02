package my.pack.qualification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();

		SmsMessage[] msgs = null;

		Log.e("before bundle", bundle+""+msgs);
		
		if (bundle != null) {
			Object[] pdus = (Object[]) bundle.get("pdus");
			msgs = new SmsMessage[pdus.length];

			Log.e("inside bundle before loop", "msg:"+msgs[0]);

			for (int i=0; i < pdus.length; i++) {
				msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				
				Log.e("loop "+i, "Message from "+ msgs[i].getOriginatingAddress() + ": "+ msgs[i].getMessageBody().toString());
				Toast.makeText(context, "Message from "+ msgs[i].getOriginatingAddress() + ": "+ msgs[i].getMessageBody().toString(), Toast.LENGTH_SHORT).show();

			}

		}

		Log.e("after bundle", bundle+""+msgs);
	}
}