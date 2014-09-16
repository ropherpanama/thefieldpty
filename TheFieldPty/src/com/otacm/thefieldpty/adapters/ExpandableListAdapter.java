package com.otacm.thefieldpty.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.otacm.thefieldpty.FirstTabChildActivity;
import com.otacm.thefieldpty.R;
import com.otacm.thefieldpty.groups.GroupLigas;
import com.otacm.thefieldpty.utils.AppUtils;

@SuppressLint("InflateParams")
public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private Activity context;
	private String nombreLiga;
	private SparseArray<GroupLigas> groups;
	private TextView textViewChildItem;

	public ExpandableListAdapter(Activity context, SparseArray<GroupLigas> groups) {
		this.context = context;
		this.groups = groups;
	}

	@Override
	public int getGroupCount() {
		return groups.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return groups.get(groupPosition).children.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return groups.get(groupPosition).children.get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	/**
	 * Realiza el setup de los nodos padres (Ligas)
	 */
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		GroupLigas group = (GroupLigas) getGroup(groupPosition);
		nombreLiga = group.getString();

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.ligas_group_view, null);
		}

		TextView textoNombreLiga = (TextView) convertView.findViewById(R.id.textoNombreLiga);
		textoNombreLiga.setText(nombreLiga);
		textoNombreLiga.setTypeface(AppUtils.normalFont(context)); 

		return convertView;
	}

	/**
	 * Realiza el setup de los hijos de cada item (categorias)
	 */
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		final String children = (String) getChild(groupPosition, childPosition);
		final GroupLigas group = (GroupLigas) getGroup(groupPosition);
		LayoutInflater inflater = context.getLayoutInflater();

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.categorias_child_view, null);
		}

		textViewChildItem = (TextView) convertView.findViewById(R.id.textViewChildItem);
		textViewChildItem.setText(children);
		textViewChildItem.setTypeface(AppUtils.normalFont(context)); 

		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { 
				Intent i = new Intent(context, FirstTabChildActivity.class);
				// Se envia el padre y el hijo para filtrar en la siguiente
				// actividad
				i.putExtra("parent", group.getString());
				i.putExtra("child", children);
				context.startActivity(i);
			}
		});

		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}