package foodAdditives.app;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import android.app.*;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.*;
import android.widget.*;

public class AdditivesAdapter extends ArrayAdapter<Additive> {

	private ArrayList<Additive> additiveItems;
	private LinkedHashMap<String, Bitmap> bitmapCache = new LinkedHashMap<String, Bitmap>();
	private Activity context;
	
	public AdditivesAdapter(Activity context, int textViewResourceId, ArrayList<Additive> additiveItems) {
		super(context, textViewResourceId, additiveItems);
		this.context = context;
		this.additiveItems = additiveItems;
		
		bitmapCache.put("Forbidden", BitmapFactory.decodeResource(context.getResources(), R.drawable.forbidden));
		bitmapCache.put("Suspicious", BitmapFactory.decodeResource(context.getResources(), R.drawable.warning));
		bitmapCache.put("Safe", BitmapFactory.decodeResource(context.getResources(), R.drawable.safe));
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.additive_data_row, parent);
		         
        Additive additive = additiveItems.get(position);
        if (additive != null) {
        	// e number
        	//TextView eTextView = (TextView) view.findViewById(R.id.enumber_text_view);
            //eTextView.setText("E" + additive.eNumber);

            // name_text_view
            //TextView nameTextView = (TextView) view.findViewById(R.id.name_text_view);
            //nameTextView.setText(additive.name);
            
            // category
            //TextView ratingTextView = (TextView) view.findViewById(R.id.category_text_view);
            //ratingTextView.setText(additive.category);

            //comment_text_view
            //TextView commentTextView = (TextView) view.findViewById(R.id.comment_text_view);
            //commentTextView.setText(additive.comment);
            
            // thumb image
		    //ImageView imageView = (ImageView) view.findViewById(R.id.safety_icon);
		    	    
		    //if (additive.safety==Safety.Safe)
		    	//imageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.safe));
		    //else if (additive.safety==Safety.Suspicious)
		    	//imageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.warning));
		    //else if (additive.safety==Safety.Forbidden)
		    	//imageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.forbidden));
		}
        return view;
	}
}
