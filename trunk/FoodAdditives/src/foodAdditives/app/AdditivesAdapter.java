package foodAdditives.app;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import android.app.*;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.*;
import android.widget.*;

public class AdditivesAdapter extends ArrayAdapter<Additive> {

	private List<Additive> additiveItems;
	private LinkedHashMap<String, Bitmap> bitmapCache = new LinkedHashMap<String, Bitmap>();
	private Activity context;
	
	private final Object mLock = new Object();
	private AdditivesFilter newFilter;
	private ArrayList<Additive> mOriginalValues;
	
	public AdditivesAdapter(Activity context, int textViewResourceId, List<Additive> additiveItems) {
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
        View view = vi.inflate(R.layout.additive_data_row, null);
		         
        Additive additive = additiveItems.get(position);
        if (additive != null) {
        	// e number
        	TextView eTextView = (TextView) view.findViewById(R.id.enumber_text_view);
            eTextView.setText("E" + additive.eNumber);

            // name_text_view
            TextView nameTextView = (TextView) view.findViewById(R.id.name_text_view);
            nameTextView.setText(additive.name);
                        
            // thumb image
		    ImageView imageView = (ImageView) view.findViewById(R.id.safety_icon);
		    	    
		    if (additive.safety==Safety.Safe)
		    	imageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.safe));
		    else if (additive.safety==Safety.Suspicious)
		    	imageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.warning));
		    else if (additive.safety==Safety.Forbidden)
		    	imageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.forbidden));
		}
        return view;
	}
	
	
	@Override
    public Filter getFilter() {
        if(newFilter == null) 
        	newFilter = new AdditivesFilter();
        return newFilter;
	}


	@Override
	public int getCount() {
		return additiveItems.size();
	}


	@Override
	public Additive getItem(int position) {
		return additiveItems.get(position);
	}
	
	private class AdditivesFilter extends Filter {
		@Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
        	additiveItems = (List<Additive>)results.values;
            notifyDataSetChanged();
        }
		
		@Override
		protected FilterResults performFiltering(CharSequence prefix) {
			FilterResults results = new FilterResults();
			
			if (mOriginalValues == null) {
				synchronized (mLock) {
					mOriginalValues = new ArrayList<Additive>(additiveItems);
				}
			}

			if (prefix == null || prefix.length() == 0) {
				synchronized (mLock) {
					ArrayList<Additive> list = new ArrayList<Additive>(mOriginalValues);
					results.values = list;
					results.count = list.size();
				}
			} else {
				List<Additive> filteredList = new LinkedList<Additive>();
				String filterTerm = prefix.toString();
	
				for(int i=0; i<additiveItems.size(); i++) {
					Additive additive = additiveItems.get(i);
	
					if (additive.eNumber.startsWith(filterTerm))
						filteredList.add(additive);
				}
	
				
				results.count = filteredList.size();
				results.values = filteredList;
			}
			return results;
		}
	}
	
}
