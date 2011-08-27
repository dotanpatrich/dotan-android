package foodAdditives.app;

import foodAdditives.app.ActionBar.Action;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

public class FoodAdditivesActivity extends Activity implements OnClickListener {
	
	private AdditivesRepository repository = new AdditivesRepository();
	private EditText filterText;
	private AdditivesAdapter adapter;
	private ImageView filterButton;
	private boolean isTextFilter = false;
	private TableLayout buttonBar;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        buttonBar = (TableLayout)findViewById(R.id.table);
        
        // set action bar items
        ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
        actionBar.setTitle(R.string.app_name);
        actionBar.setHomeLogo(R.drawable.actionbar_home_logo);
        
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
              
        // add button to switch between numbers and text search
        filterButton = (ImageView)inflater.inflate(R.layout.actionbar_filterbutton, null);
        filterButton.setOnClickListener(this);
        actionBar.addActionView(filterButton);

        // add edittext to the actionbar
        filterText = (EditText)inflater.inflate(R.layout.actionbar_edittext, null);
        actionBar.addActionView(filterText);       
        
        // set list adapter
        adapter = new AdditivesAdapter(this, R.layout.additive_data_row, repository.all()); 
        ListView listView = (ListView)findViewById(R.id.elist);
        listView.setAdapter(adapter);
        
        // set text box listener
        filterText.addTextChangedListener(new TextWatcher()
        	{
	        	public void afterTextChanged(Editable s) {}         
	            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
	            public void onTextChanged(CharSequence s, int start, int before, int count) {
	            	
	            	AdditivesAdapter.AdditivesFilter filter = (AdditivesAdapter.AdditivesFilter)adapter.getFilter();
	            	filter.setTextMode(isTextFilter);
        			filter.filter(s);
	            }
        	});
     
        
        // prevent virtual keyboard on text box filter
        filterText.setInputType(0);
        
        // set empty view for the list
        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);
        
        // add click listener to list view items
        listView.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        			Additive additive = (Additive)adapter.getItem(position);
        			Intent intent = new Intent(view.getContext(), AdditiveDetailsActivity.class);
        			intent.putExtra("additive", additive);
        			FoodAdditivesActivity.this.startActivityForResult(intent, 1);
                }
			});
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     super.onActivityResult(requestCode, resultCode, data);
     if (resultCode == 42)
    	 finish();
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
    

	@Override
	public void onClick(View arg0) {
		// handle click on the filter button
		if (isTextFilter) {
			filterButton.setImageResource(R.drawable.filter_numberic);
			isTextFilter = false;
			
			// show numeric button bar and close keyboard
			buttonBar.setVisibility(View.VISIBLE);
			filterText.setInputType(0);
			filterText.setMaxEms(4);
			filterText.setText("");
			
			// hide keyboard
			InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(filterText.getApplicationWindowToken(), 0);
			
			
		} else { 
			filterButton.setImageResource(R.drawable.filter_text);
			isTextFilter = true;
			
			// clear numeric button bar and open keyboard
			buttonBar.setVisibility(View.GONE);
			filterText.setInputType(InputType.TYPE_CLASS_TEXT);
			filterText.setMaxEms(0);
			filterText.setText("");
			filterText.requestFocusFromTouch();
			
			// show keyboard
			InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(filterText, 0);
			
		}
	}
    
}