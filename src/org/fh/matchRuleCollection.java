package org.fh;

import java.util.ArrayList;

public interface matchRuleCollection extends matchRule
{
	ArrayList<matchRule> rules = new ArrayList<matchRule>();
	public void add(matchRule rule);
	public void remove(int index);
}
