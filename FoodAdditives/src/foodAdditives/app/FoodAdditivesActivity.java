package foodAdditives.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.*;
import android.view.*;
import android.widget.*;

public class FoodAdditivesActivity extends Activity {
	
	private AdditivesRepository repository = new AdditivesRepository();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        // set action bar items
        ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
        actionBar.setTitle(R.string.app_name);
        actionBar.setHomeLogo(R.drawable.actionbar_home_logo);
        
        // add edittext to the actionbar
        LayoutInflater  inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        EditText editText = (EditText)inflater.inflate(R.layout.actionbar_edittext, null);
        actionBar.addActionView(editText);
        
        // set list adapter
        AdditivesAdapter adapter = new AdditivesAdapter(this, R.layout.additive_data_row, repository.all()); 
        ListView lv = (ListView)findViewById(R.id.elist);
        lv.setAdapter(adapter);
        
        // set text box listener
        EditText et = (EditText)findViewById(R.id.filter);
        et.addTextChangedListener(new TextWatcher()
        	{
	        	public void afterTextChanged(Editable s) {
	        		// get the position of the e number
	        		EditText et = (EditText)findViewById(R.id.filter);
	        		String eNumber = et.getText().toString();
	        		int position = repository.findFirstPostion(eNumber);
	        		if (position!=-1) {	        		
	        			// scroll to position
	        			ListView lv = (ListView)findViewById(R.id.elist);
	        			lv.setSelection(position);
	        		}
	            }
	         
	            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
	            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        	});
     
        
        // prevent virtual keyboard on text box filter
        et.setInputType(0);
    }
    
    public void ButtonClicked(View view) {
    	EditText text = (EditText)findViewById(R.id.filter);
    	
    	if (text.getText().length() < 4) {
    		// get the button's text to add
    		Button button = (Button)view;    		
    		text.getText().append(button.getText());
    	}
    }
    
    public void BackspaceButtonClicked(View view) {
		EditText text = (EditText)findViewById(R.id.filter);
    	
    	if (text.getText().length() > 0) {
    		text.getText().delete(text.getText().length()-1, text.getText().length());
    	}
    }
    
    public void ClearButtonClicked(View view) {
		EditText text = (EditText)findViewById(R.id.filter);
    	
    	if (text.getText().length() > 0) {
    		text.setText("");
    	}
    }
    
    
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aboutItem:   
            	Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("אודות תוספי מזון");
                builder.setIcon(R.drawable.logo);
                builder.setPositiveButton("אישור", null);
                
                LayoutInflater  inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.about_dialog, (ViewGroup) findViewById(R.layout.main));               
                builder.setView(view);                
                
                AlertDialog dialog = builder.create();
                dialog.show();
            	
                break;
            case R.id.exitItem: 
            	FoodAdditivesActivity.this.finish();
                break;
        }
        return true;
    }
    
    public static Intent createIntent(Context context) {
        Intent i = new Intent(context, FoodAdditivesActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return i;
    }
    
}