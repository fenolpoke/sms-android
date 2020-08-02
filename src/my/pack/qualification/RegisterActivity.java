package my.pack.qualification;

import android.support.v7.app.ActionBarActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends ActionBarActivity {

	RegisterActivity activity = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		((Button) findViewById(R.id.registerButton)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				if(((EditText)findViewById(R.id.nameEditText)).getText().toString().trim().equals("")){
					Toast.makeText(activity, "Name must be filled!", Toast.LENGTH_LONG).show();
				}else if(((EditText)findViewById(R.id.usernameEditText)).getText().toString().trim().equals("")){
					Toast.makeText(activity, "Username must be filled!", Toast.LENGTH_LONG).show();
				}else if(((EditText)findViewById(R.id.emailEditText)).getText().toString().trim().equals("")){
					Toast.makeText(activity, "Email must be filled!", Toast.LENGTH_LONG).show();
				}else if(!isEmailValid(((EditText)findViewById(R.id.emailEditText)).getText().toString())){
					Toast.makeText(activity, "Email not valid!", Toast.LENGTH_LONG).show();
				}else if(((EditText)findViewById(R.id.passwordEditText)).getText().toString().trim().equals("")){
					Toast.makeText(activity, "Password must be filled!", Toast.LENGTH_LONG).show();
				}else if(!((EditText)findViewById(R.id.passwordEditText)).getText().toString().trim().equals(
						((EditText)findViewById(R.id.confirmPasswordEditText)).getText().toString())){
					Toast.makeText(activity, "Password is not confirmed!", Toast.LENGTH_LONG).show();
				}else{

					Toast.makeText(activity, "Inserting to database...", Toast.LENGTH_SHORT).show();
					
			        DatabaseHelper dbHelper = new DatabaseHelper(activity);
			        SQLiteDatabase db = dbHelper.getWritableDatabase();
			        ContentValues value = new ContentValues();
			        value.put("name", ((EditText)findViewById(R.id.nameEditText)).getText().toString());
			        value.put("username", ((EditText)findViewById(R.id.usernameEditText)).getText().toString());
			        value.put("email", ((EditText)findViewById(R.id.emailEditText)).getText().toString());
			        value.put("password", ((EditText)findViewById(R.id.passwordEditText)).getText().toString());
			        db.insert("users", null, value);
			        db.close();
			        dbHelper.close();

					Toast.makeText(activity, "Success insert!", Toast.LENGTH_SHORT).show();
					
					startActivity(new Intent(activity, HomeActivity.class));
					finish();
					Log.e("error", "after intent");
				}							
			}
		});
	}
	
	private boolean isEmailValid(String email) {
		// TODO: Replace this with your own logic
		return email.contains("@") && email.contains(".") && email.indexOf(".") > email.indexOf("@") - 1;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
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
}
