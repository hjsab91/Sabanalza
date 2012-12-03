package SQLite.act01;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import android.database.Cursor;
import android.util.Log;
//import java.io.IOException;

@SuppressLint("ShowToast")
public class MainActivity extends Activity {


	public String data_name;
	public String data_email;
	public String data_number;
	public long pos;
	public Cursor cur;

	@Override
	public void onCreate(Bundle savedInstanceState) {

	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	
	final DBAdapter db = new DBAdapter(this);
	
	final EditText editName = (EditText)findViewById(R.id.nameTxt);
	final EditText editEmail = (EditText)findViewById(R.id.emailTxt);
	final EditText editNumber = (EditText)findViewById(R.id.numberTxt);

	Button btnAdd = (Button)findViewById(R.id.addBtn);
	Button btnDelete = (Button)findViewById(R.id.deleteBtn);
	Button btnNext = (Button)findViewById(R.id.nextBtn);
	Button btnPrevious = (Button)findViewById(R.id.previousBtn);
    Button btnEdit = (Button)findViewById(R.id.editBtn);
    
	db.open();
	cur = db.getAllContacts();
	
	btnNext.setOnClickListener(new View.OnClickListener() 
  {
	public void onClick(View v) 
	{	
		if((cur.getCount() > 0) && (cur.isLast() != true))
	
		{
		cur.moveToNext();
		pos = cur.getLong(0);
		data_name = cur.getString(1);
		data_email = cur.getString(2);
		data_number = cur.getString(3);
		editName.setText(data_name);
		editEmail.setText(data_email);
		editNumber.setText(data_number);
		}
		
		else	
		{
		  Toast.makeText(getBaseContext(), "End of Data!!", Toast.LENGTH_SHORT);
		  String tag = null;
		  Log.d(tag, "End of data in here : 10004");
		  cur = db.getAllContacts();
		  cur.moveToFirst();
		  data_name = cur.getString(1);
		  data_email = cur.getString(2);
		  data_number = cur.getString(3);
		  editName.setText(data_name);
		  editEmail.setText(data_email);
		  editNumber.setText(data_number);
		 }

	}
});
	
	
	btnPrevious.setOnClickListener(new View.OnClickListener() {
		
	public void onClick(View v) {
		if((cur.getCount() < 0) && (cur.isFirst() != true))
			
		{
		cur.moveToPrevious();
		pos = cur.getLong(0);
		data_name = cur.getString(1);
		data_email = cur.getString(2);
		data_number = cur.getString(3);
		editName.setText(data_name);
		editEmail.setText(data_email);
		editNumber.setText(data_number);
		}
		
		else	
		{
		  Toast.makeText(getBaseContext(), "End of Data!!", Toast.LENGTH_SHORT);
		  String tag = null;
		  Log.d(tag, "End of data in here : 10004");
		  cur = db.getAllContacts();
		  cur.moveToFirst();
		  data_name = cur.getString(1);
		  data_email = cur.getString(2);
		  data_number = cur.getString(3);
		  editName.setText(data_name);
		  editEmail.setText(data_email);
		  editNumber.setText(data_number);
		 }

	}
	

	});
	
	btnAdd.setOnClickListener(new View.OnClickListener() {
		
	private long temp_id;

	public void onClick(View v) 
	{
		data_name = editName.getText().toString();
		data_email = editEmail.getText().toString();
		data_number = editNumber.getText().toString();
		
		if((data_name != "") && (data_email != "") && (data_number != ""))
		{
			temp_id = db.insertContact(data_name, data_email, data_number);
			Toast.makeText(getBaseContext(), "Contact added successfullly", Toast.LENGTH_SHORT).show();
			cur = db.getAllContacts();
			editName.setText("");
			editEmail.setText("");
			editNumber.setText("");
		}
		
		else
		{ Toast.makeText(getBaseContext(), "You have to  enter all the fields", Toast.LENGTH_SHORT).show();}
	
	  }
  });

	btnDelete.setOnClickListener(new View.OnClickListener() {
	
	 public void onClick(View v) {
		
	 if(db.deleteContact(pos))
	 {
		Toast.makeText(getBaseContext(), "Information Removed Successfully !!", Toast.LENGTH_SHORT).show();
		editName.setText("");
		editEmail.setText("");
		editNumber.setText("");
	 }
	 
	 }
	});
	
	btnEdit.setOnClickListener(new View.OnClickListener() {
		
		 public void onClick(View v) {
			String tempname, tempmail, tempnum;
			 
			data_name = tempname = cur.getString(1);
			data_email = tempmail =  cur.getString(2);
			data_number = tempnum = cur.getString(3);
			editName.setText(data_name);
			editEmail.setText(data_email);
			editNumber.setText(data_number);
			
		
		 long id = db.getID(tempname);	
		 if(db.updateContact(id, tempname, tempmail, tempnum))
		 {
			Toast.makeText(getBaseContext(), "Updated Successfully !!", Toast.LENGTH_SHORT).show();
		 }
		 
		 }
		});
	
   }
 }

	
	



 