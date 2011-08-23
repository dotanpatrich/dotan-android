package foodAdditives.app;

import foodAdditives.app.ActionBar.Action;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
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
			return R.drawable.ic_dialog_menu_generic;
		}
        
		public void performAction(View view) {
			AdditiveDetailsActivity.this.onBackPressed();
        }
        
	 }
	
}
