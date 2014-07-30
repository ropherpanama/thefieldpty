package com.otacm.thefieldpty.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.sample.R;
import com.otacm.thefieldpty.groups.GroupScores;

public class ExpandableScores extends BaseExpandableListAdapter {
	private Activity context;
	private String ligaName;
	private SparseArray<GroupScores> groups;

	public ExpandableScores(Activity context, SparseArray<GroupScores> groups) {
		this.context = context;
		this.groups = groups;
	}

	@Override
	public int getGroupCount() {
		return groups.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;// Uno porque solo despliega un hijo por nodo
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return groups.get(childPosition);
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

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		GroupScores group = groups.valueAt(groupPosition);
		ligaName = group.getLabelNameLiga();

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater
					.inflate(R.layout.score_group_view, null);
		}

		TextView textoEncabezado = (TextView) convertView
				.findViewById(R.id.textoEncabezado);
		textoEncabezado.setText(ligaName);

		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		GroupScores group = groups.valueAt(groupPosition);
		final String children = group.getTeamsMatch();
		LayoutInflater inflater = context.getLayoutInflater();

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.scores_child_view, null);
		}

		TextView textViewChildScore = (TextView) convertView
				.findViewById(R.id.textViewChildScore);
		textViewChildScore.setText(children);

		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}
