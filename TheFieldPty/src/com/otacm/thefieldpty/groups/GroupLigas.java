package com.otacm.thefieldpty.groups;


import java.util.ArrayList;
import java.util.List;

/**
* Esta clase representa el conjunto de padres e hijos del primer tab (Expandable list)
* Ligas y categorias
* @author rospena
*
*/
public class GroupLigas {
public String string;
public final List<String> children = new ArrayList<String>();

public GroupLigas(String string) {
this.string = string;
}

public String getString() {
return string;
}

public void setString(String string) {
this.string = string;
}

public List<String> getChildren() {
return children;
}
}