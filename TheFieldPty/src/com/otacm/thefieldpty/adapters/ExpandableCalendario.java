package com.otacm.thefieldpty.adapters;

import com.example.sample.R;
import com.otacm.thefieldpty.groups.GroupCalendario;

import android.app.Activity;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandableCalendario extends BaseExpandableListAdapter {
	private Activity context;
	private String encabezado;
	private SparseArray<com.otacm.thefieldpty.groups.GroupCalendario> groups;
	private TextView textViewChildCalendario;

	public ExpandableCalendario(Activity context,
			SparseArray<com.otacm.thefieldpty.groups.GroupCalendario> groups) {
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

	/**
	 * Realiza el setup de los nodos padres (Ligas)
	 */
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// GroupCalendario group = (GroupCalendario) getGroup(groupPosition);
		com.otacm.thefieldpty.groups.GroupCalendario group = groups.valueAt(groupPosition);
		encabezado = group.getLabel();

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.calendario_group_view,
					null);
		}

		TextView textoEncabezado = (TextView) convertView
				.findViewById(R.id.textoEncabezado);
		textoEncabezado.setText(encabezado);

		return convertView;
	}

	/**
	 * Realiza el setup de los hijos de cada item (categorias)
	 */
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// GroupCalendario group = (GroupCalendario) getGroup(groupPosition);
		GroupCalendario group = groups.valueAt(groupPosition);
		final String children = group.getDetallePartido();
		LayoutInflater inflater = context.getLayoutInflater();

		if (convertView == null) {
			convertView = inflater
					.inflate(R.layout.calendario_child_view, null);
		}

		textViewChildCalendario = (TextView) convertView
				.findViewById(R.id.textViewChildCalendario);
		textViewChildCalendario.setText(children);

		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}
