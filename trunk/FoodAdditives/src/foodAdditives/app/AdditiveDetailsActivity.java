package foodAdditives.app;

import foodAdditives.app.ActionBar.Action;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;

public class AdditiveDetailsActivity extends Activity {

	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.additive_details);
	    
	    // set action bar items
        ActionBar actionBar = (ActionBar) findViewById(R.id.detailsactionbar);
        actionBar.setTitle(R.string.app_name);
        actionBar.setHomeLogo(R.drawable.actionbar_home_logo);
	    
        // add back button to action bar
        actionBar.addAction(new BackAction());
        
        // set additive details
        TextView eView = (TextView)findViewById(R.id.additive_enumber);
        TextView nameView = (TextView)findViewById(R.id.additive_name);
        TextView categoryView = (TextView)findViewById(R.id.additive_category);
        TextView commentsView = (TextView)findViewById(R.id.additive_comments);
        ImageView iconView = (ImageView)findViewById(R.id.additive_icon);
        
        Additive additive = (Additive)getIntent().getExtras().get("additive");
        eView.setText("מספר: E" + additive.eNumber);
        nameView.setText("שם: " + additive.name);
        categoryView.setText("קטגוריה: " + additive.category);
        commentsView.setText("הערות: " + additive.comment);
        
        if (additive.safety == Safety.Safe)
        	iconView.setImageResource(R.drawable.safe);
        else if (additive.safety == Safety.Suspicious)
        	iconView.setImageResource(R.drawable.warning);
        else if (additive.safety == Safety.Forbidden)
        	iconView.setImageResource(R.drawable.forbidden);
        
	 }
	 
	 private class BackAction implements Action {
		public int getDrawable() {
			return R.drawable.ic_menu_revert;
		}
        
		public void performAction(View view) {
			AdditiveDetailsActivity.this.onBackPressed();
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
	            	AdditiveDetailsActivity.this.setResult(42);
	            	AdditiveDetailsActivity.this.finish();
	                break;
	        }
	        return true;
	    }
	
}
