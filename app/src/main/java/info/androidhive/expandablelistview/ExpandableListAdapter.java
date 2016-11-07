package info.androidhive.expandablelistview;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private Context _context;
	private List<String> _listDataHeader; // header titles
	// child data in format of header title, child title
	private HashMap<String, List<String>> _listDataChild;
	private final Set<Pair<Long, Long>> mCheckedItems = new HashSet<Pair<Long, Long>>();
	Button button;
	Groupclick groupclick;



	public ExpandableListAdapter(Context context, List<String> listDataHeader,
			HashMap<String, List<String>> listChildData) {
		this._context = context;
		this._listDataHeader = listDataHeader;
		this._listDataChild = listChildData;
		Log.e("tag","list data size"+listChildData.get(listDataHeader.get(0)).size());

	}
	public void Listner(Groupclick context)
	{
		this.groupclick=context;


	}

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

		final String childText = (String) getChild(groupPosition, childPosition);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_item, null);
		}

		TextView txtListChild = (TextView) convertView
				.findViewById(R.id.lblListItem);
		CheckBox cb = (CheckBox) convertView.findViewById(R.id.child_check);

		// two tags are different

		final Pair<Long, Long> tag = new Pair<Long, Long>(
				getGroupId(groupPosition),
				getChildId(groupPosition, childPosition));
		cb.setTag(tag);
		// set checked if groupId/childId in checked items
	/*	if(button.getTag().equals("GroupSelect"))
		{
			Log.e("tag","enter in group select");
			mCheckedItems.add(tag);
		}*/
			cb.setChecked(mCheckedItems.contains(tag));

		// set OnClickListener to handle checked switches
		cb.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				final CheckBox cb = (CheckBox) v;
				final Pair<Long, Long> tag = (Pair<Long, Long>) v.getTag();
				//button.setTag("ChildSelect");
				if (cb.isChecked()) {
					mCheckedItems.add(tag);
				} else {
					mCheckedItems.remove(tag);
				}
			}
		});

		txtListChild.setText(childText);
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.size();
	}

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
				Log.e("group btn clicked","log");
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
