package com.otacm.thefieldpty.groups;


public class GroupScores {
private String labelNameLiga;
private String teamsMatch;

public String getLabelNameLiga() {
return labelNameLiga;
}

public void setLabelNameLiga(String labelNameLiga) {
this.labelNameLiga = labelNameLiga;
}

public String getTeamsMatch() {
return teamsMatch;
}

public void setTeamsMatch(String teamsMatch) {
this.teamsMatch = teamsMatch;
}

@Override
public int hashCode() {
String str = getLabelNameLiga()+getTeamsMatch();
return str.hashCode();
}
}