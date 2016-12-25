package info.androidhive.expandablelistview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private Context _context;
	private List<String> _listDataHeader; // header titles
	// child data in format of header title, child title
	private HashMap<String, List<String>> _listDataChild;
	private final Set<Pair<Long, Long>> mCheckedItems = new HashSet<Pair<Long, Long>>();
	Button button;
	Groupclick groupclick;
	boolean groupButtonCheck=true;



	public ExpandableListAdapter(Context context, List<String> listDataHeader,
			HashMap<String, List<String>> listChildData) {
		this._context = context;
		this._listDataHeader = listDataHeader;
		this._listDataChild = listChildData;
		Log.e("tag","list data size"+listChildData.get(listDataHeader.get(0)).size());

	}

	// It will take context from main mathod and can be trigger in main activity only.

	public void Listner(Groupclick context)
	{
		this.groupclick=context;


	}

	//child list view>>>>

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.get(childPosititon);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		Log.e("tag","enter in getchildView");

		// get child name-- to add all list as a layoutinflator...

		final String childText = (String) getChild(groupPosition, childPosition);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_item, null);
		}

		TextView txtListChild = (TextView) convertView
				.findViewById(R.id.lblListItem);
		CheckBox cb = (CheckBox) convertView.findViewById(R.id.child_check);

		//  child tag store Integar and Long value and store like this {1,2,3}; and new will not call in final method

		final Pair<Long, Long> Childtag = new Pair<Long, Long>(
				getGroupId(groupPosition),
				getChildId(groupPosition, childPosition));
		// every time set tag on child ckeck boxes...
		cb.setTag(Childtag);

		// check if contion is true then check box has been On like-{125}={127}
		cb.setChecked(mCheckedItems.contains(Childtag));

		// set OnClickListener to handle checked switches
		cb.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				final CheckBox cb = (CheckBox) v;
				final Pair<Long, Long> tagFlag = (Pair<Long, Long>) v.getTag();

				//this is check and uncheked method to remove and add flag ...
				if (cb.isChecked()) {
					mCheckedItems.add(tagFlag);
				} else {
					mCheckedItems.remove(tagFlag);

				}







			}
		});

		// inflator calling...
		txtListChild.setText(childText);
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.size();
	}




	//Strting grouop>>>>>

	@Override
	public Object getGroup(int groupPosition) {
		return this._listDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this._listDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(final int groupPosition, final boolean isExpanded,
							 View convertView, ViewGroup parent) {
		Log.e("tag","enter in GroupView");
		String headerTitle = (String) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_group, null);
		}

		TextView lblListHeader = (TextView) convertView
				.findViewById(R.id.lblListHeader);
		Button button=(Button)convertView.findViewById(R.id.checkedAll);

		button.setOnClickListener(new View.OnClickListener() {
			@Override

			public void onClick(View view) {

				//check all child and unchecheck child

				if(groupButtonCheck){
				//check value if
				mCheckedItems.clear();
                    for (int j = 0; j <getChildrenCount(groupPosition); j++)
                    {
                        Integer integerI=new Integer(groupPosition);
                        Integer integerJ=new Integer(j);
                        Long intI=integerI.longValue();
                        Long intJ=integerJ.longValue();
                        final Pair<Long, Long>GroupChecked = new Pair<Long, Long>(intI,intJ);
						mCheckedItems.add(GroupChecked);
						notifyDataSetChanged();
						groupButtonCheck=false;
                    }

				}
				else
				{
					mCheckedItems.clear();
					notifyDataSetChanged();
					groupButtonCheck=true;


				}


            }
		});

		lblListHeader.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				groupclick.expandGroupEvent(groupPosition,isExpanded);

			}
		});


		lblListHeader.setTypeface(null, Typeface.BOLD);
		lblListHeader.setText(headerTitle);

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
