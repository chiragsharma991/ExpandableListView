package info.androidhive.expandablelistview;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity implements Groupclick {

	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	boolean [] child_checked;
	private ArrayList<String> top250;


	private ArrayList<String> finalChildSelected;
	private ArrayList<String> finalGroupSelected;
	private CheckBox child_check_button;
	private CheckBox group_check_button;
	private boolean[] group_checked;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// get the listview
		expListView = (ExpandableListView) findViewById(R.id.lvExp);
		final EditText edit=(EditText)findViewById(R.id.search);

		edit.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				String text=edit.getText().toString().toLowerCase(Locale.getDefault());
				listAdapter.filter(text);

			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		});

		// preparing list data
		prepareListData();

		listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

		// This is for interface calling, you have to pass context whenever you wans to trigger from it ??

		listAdapter.Listner(this);
		expListView.setAdapter(listAdapter);
		
		

		// Listview Group click listener
		expListView.setOnGroupClickListener(new OnGroupClickListener() {


			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				Log.e("onGroupClick:", "worked");


				parent.expandGroup(groupPosition);
				return false;
			}
		});

		expListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Log.e("onGroupClick:", "position"+i);

			}
		});





		// Listview on child click listener
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(
						getApplicationContext(),
						listDataHeader.get(groupPosition)
								+ " : "
								+ listDataChild.get(
										listDataHeader.get(groupPosition)).get(
										childPosition), Toast.LENGTH_SHORT)
						.show();

				return false;
			}
		});
	}


	

	/*
	 * Preparing the list data
	 */
	private void prepareListData()
	{
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();
		

		// Adding child data
		listDataHeader.add("Top 250");
		listDataHeader.add("Now Showing");
		listDataHeader.add("Coming Soon..");

		// Adding child data
		top250 = new ArrayList<String>();
		top250.add("The Shawshank Redemption");
		top250.add("The Godfather");
		top250.add("The Godfather: Part II");
		top250.add("Pulp Fiction");
		top250.add("The Good, the Bad and the Ugly");
		top250.add("The Dark Knight");
		top250.add("12 Angry Men");
		//Log.e("tag","top250 size"+top250.size());

		List<String> nowShowing = new ArrayList<String>();
		nowShowing.add("The Conjuring");
		nowShowing.add("Despicable Me 2");
		nowShowing.add("Turbo");
		nowShowing.add("Grown Ups 2");
		nowShowing.add("Red 2");
		nowShowing.add("The Wolverine");
		//Log.e("tag","now showing size"+nowShowing.size());

		List<String> comingSoon = new ArrayList<String>();
		comingSoon.add("2 Guns");
		comingSoon.add("The Smurfs 2");
		comingSoon.add("The Spectacular Now");
		comingSoon.add("The Canyons");
		comingSoon.add("Europa Report");
		//Log.e("tag","coming size"+comingSoon.size());

		listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
		listDataChild.put(listDataHeader.get(1), nowShowing);
		listDataChild.put(listDataHeader.get(2), comingSoon);
		Log.e("tag","listdatasize "+listDataChild.get(listDataHeader.get(0)).size());


	}


	//Interface for expand list view and colapse list view

	@Override
	public void expandGroupEvent(int groupPosition, boolean isExpanded) {
		Log.e("interfacePosition", String.valueOf(groupPosition));

		if(isExpanded)

			expListView.collapseGroup(groupPosition);
		else
			expListView.expandGroup(groupPosition);


	}
}
