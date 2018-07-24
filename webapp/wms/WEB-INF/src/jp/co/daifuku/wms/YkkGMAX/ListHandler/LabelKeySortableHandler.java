package jp.co.daifuku.wms.YkkGMAX.ListHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import jp.co.daifuku.wms.YkkGMAX.Entities.LabelKeySortable;

public class LabelKeySortableHandler
{
	private static class labelKeyComparator implements Comparator
	{
		public int compare(Object o1, Object o2)
		{
			return ((LabelKeySortable) o1).getLabelKey().compareTo(((LabelKeySortable) o2).getLabelKey());		
		}
	}
	
	private static class LabelKeyEntity implements LabelKeySortable
	{
		public LabelKeyEntity(String labelKey)
		{
			this.labelKey = labelKey;
		}
		
		String labelKey = "";

		public String getLabelKey()
		{			
			return labelKey;
		}
	}
	
	private static Comparator comparator = new LabelKeySortableHandler.labelKeyComparator();
	
	public static void sort(ArrayList list)
	{
		Collections.sort(list, comparator);
	}
	
	public static void insert(ArrayList list, LabelKeySortable element)
	{
		if (element.getLabelKey() != null)
		{
			int index = Collections.binarySearch(list, element, comparator);
			if (index < 0)
			{
				list.add(Math.abs(index) - 1, element);
			}
		}
	}
	
	public static void remove(ArrayList list, LabelKeySortable element)
	{
		if (element.getLabelKey() != null)
		{
			int index = Collections.binarySearch(list, element, comparator);
			if (index >= 0)
			{
				list.remove(index);
			}
		}
	}
	
	public static void remove (ArrayList list, String labelKey)
	{
		if (labelKey != null)
		{
			LabelKeySortable element = new LabelKeySortableHandler.LabelKeyEntity(
					labelKey);
			remove(list, element);
		}
	}
	
	public static Object get(ArrayList list, String labelKey)
	{
		if (labelKey != null)
		{
			LabelKeySortable element = new LabelKeySortableHandler.LabelKeyEntity(labelKey);
			return list
					.get(Collections.binarySearch(list, element, comparator));
		}
		else
		{
			return null;
		}
	}
	
	public static boolean contain(ArrayList list, String labelKey)
	{
		if (labelKey != null)
		{
			LabelKeySortable element = new LabelKeySortableHandler.LabelKeyEntity(
					labelKey);
			return Collections.binarySearch(list, element, comparator) >= 0;
		}
		else
		{
			return false;
		}
	}
}
