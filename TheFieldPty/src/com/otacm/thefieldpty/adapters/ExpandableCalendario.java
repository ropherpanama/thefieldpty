package com.otacm.thefieldpty.adapters;

import com.example.sample.R;
import com.otacm.thefieldpty.groups.GroupCalendario;
import com.otacm.thefieldpty.utils.AppUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

@SuppressLint({ "DefaultLocale", "InflateParams" })
public class ExpandableCalendario extends BaseExpandableListAdapter {
	private Activity context;
//	private String encabezado;
	private SparseArray<com.otacm.thefieldpty.groups.GroupCalendario> groups;
	private TextView textViewChildCalendario;
	private Typeface font;

	public ExpandableCalendario(Activity context, SparseArray<com.otacm.thefieldpty.groups.GroupCalendario> groups) {
		this.context = context;
		this.groups = groups;
		font = AppUtils.normalFont(context);
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
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		// GroupCalendario group = (GroupCalendario) getGroup(groupPosition);
		com.otacm.thefieldpty.groups.GroupCalendario group = groups.valueAt(groupPosition);
//		encabezado = group.getLabel();

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.calendario_group_view, null);
		}

		int id_drawable_1 = AppUtils.getDrawableByName(context, group.getEquipo1().trim().toLowerCase().replace(" ", ""));
		int id_drawable_2 = AppUtils.getDrawableByName(context, group.getEquipo2().trim().toLowerCase().replace(" ", ""));
		
		TextView textoEquipo1 = (TextView) convertView.findViewById(R.id.textoEquipo1);
		textoEquipo1.setText(group.getEquipo1());
		textoEquipo1.setTypeface(font);
		
		TextView textoEquipo2 = (TextView) convertView.findViewById(R.id.textoEquipo2);
		textoEquipo2.setText(group.getEquipo2());
		textoEquipo2.setTypeface(font); 
		
		if(id_drawable_1 == 0)
			textoEquipo1.setCompoundDrawablesWithIntrinsicBounds(AppUtils.getDrawableByName(context, "default_logo"),0,0,0);
		else
			textoEquipo1.setCompoundDrawablesWithIntrinsicBounds(id_drawable_1, 0, 0, 0); 
		
		if(id_drawable_2 == 0)
			textoEquipo2.setCompoundDrawablesWithIntrinsicBounds(AppUtils.getDrawableByName(context, "default_logo"),0,0,0);
		else
			textoEquipo2.setCompoundDrawablesWithIntrinsicBounds(id_drawable_2, 0, 0, 0); 

		return convertView;
	}

	/**
	 * Realiza el setup de los hijos de cada item (categorias)
	 */
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		// GroupCalendario group = (GroupCalendario) getGroup(groupPosition);
		GroupCalendario group = groups.valueAt(groupPosition);
		final String children = group.getDetallePartido();
		LayoutInflater inflater = context.getLayoutInflater();

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.calendario_child_view, null);
		}

		textViewChildCalendario = (TextView) convertView.findViewById(R.id.textViewChildCalendario);
		textViewChildCalendario.setText(children);

		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}
