package jp.co.daifuku.wms.YkkGMAX.ListHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import jp.co.daifuku.wms.YkkGMAX.Entities.SystemIdSortable;

public class SystemIdSortableHandler
{
	private static class systemIdComparator implements Comparator
	{
		public int compare(Object o1, Object o2)
		{
			return ((SystemIdSortable) o1).getSystemId().compareTo(((SystemIdSortable) o2).getSystemId());		
		}

	}
	
	private static class SystemIdEntity implements SystemIdSortable
	{
		public SystemIdEntity(String systemId)
		{
			this.systemId = systemId;
		}
		
		String systemId = "";

		public String getSystemId()
		{			
			return systemId;
		}
	}
	
	private static Comparator comparator = new SystemIdSortableHandler.systemIdComparator();
	
	public static void sort(ArrayList list)
	{
		Collections.sort(list, comparator);
	}
	
	public static void insert(ArrayList list, SystemIdSortable element)
	{
		if (element.getSystemId() != null)
		{
			int index = Collections.binarySearch(list, element, comparator);
			if (index < 0)
			{
				list.add(Math.abs(index) - 1, element);
			}
		}
	}
	
	public static void remove(ArrayList list, SystemIdSortable element)
	{
		if (element.getSystemId() != null)
		{
			int index = Collections.binarySearch(list, element, comparator);
			if (index >= 0)
			{
				list.remove(index);
			}
		}
	}
	
	public static void remove (ArrayList list, String systemId)
	{
		if (systemId != null)
		{
			SystemIdSortable element = new SystemIdSortableHandler.SystemIdEntity(
					systemId);
			remove(list, element);
		}
	}
	
	public static Object get(ArrayList list, String systemId)
	{
		if (systemId != null)
		{
			SystemIdSortable element = new SystemIdSortableHandler.SystemIdEntity(
					systemId);
			return list
					.get(Collections.binarySearch(list, element, comparator));
		}
		else
		{
			return null;
		}
	}
	
	public static boolean contain(ArrayList list, String systemId)
	{
		if (systemId != null)
		{
			SystemIdSortable element = new SystemIdSortableHandler.SystemIdEntity(
					systemId);
			return Collections.binarySearch(list, element, comparator) >= 0;
		}
		else
		{
			return false;
		}
	}
}
