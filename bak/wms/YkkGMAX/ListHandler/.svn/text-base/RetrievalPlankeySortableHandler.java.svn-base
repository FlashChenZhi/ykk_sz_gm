package jp.co.daifuku.wms.YkkGMAX.ListHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import jp.co.daifuku.wms.YkkGMAX.Entities.RetrievalPlankeySortable;

public class RetrievalPlankeySortableHandler
{
	private static class RetrievalPlankeyComparator implements Comparator
	{
		public int compare(Object o1, Object o2)
		{
			return ((RetrievalPlankeySortable) o1).getRetrievalPlankey().compareTo(((RetrievalPlankeySortable) o2).getRetrievalPlankey());		
		}
	}
	
	private static class RetrievalPlankeyEntity implements RetrievalPlankeySortable
	{
		public RetrievalPlankeyEntity(String RetrievalPlankey)
		{
			this.RetrievalPlankey = RetrievalPlankey;
		}
		
		String RetrievalPlankey = "";

		public String getRetrievalPlankey()
		{			
			return RetrievalPlankey;
		}
	}
	
	private static Comparator comparator = new RetrievalPlankeySortableHandler.RetrievalPlankeyComparator();
	
	public static void sort(ArrayList list)
	{
		Collections.sort(list, comparator);
	}
	
	public static void insert(ArrayList list, RetrievalPlankeySortable element)
	{
		if (element.getRetrievalPlankey() != null)
		{
			int index = Collections.binarySearch(list, element, comparator);
			if (index < 0)
			{
				list.add(Math.abs(index) - 1, element);
			}
		}
	}
	
	public static void remove(ArrayList list, RetrievalPlankeySortable element)
	{
		if (element.getRetrievalPlankey() != null)
		{
			int index = Collections.binarySearch(list, element, comparator);
			if (index >= 0)
			{
				list.remove(index);
			}
		}
	}
	
	public static void remove (ArrayList list, String RetrievalPlankey)
	{
		if (RetrievalPlankey != null)
		{
			RetrievalPlankeySortable element = new RetrievalPlankeySortableHandler.RetrievalPlankeyEntity(
					RetrievalPlankey);
			remove(list, element);
		}
	}
	
	public static Object get(ArrayList list, String RetrievalPlankey)
	{
		if (RetrievalPlankey != null)
		{
			RetrievalPlankeySortable element = new RetrievalPlankeySortableHandler.RetrievalPlankeyEntity(
					RetrievalPlankey);
			return list
					.get(Collections.binarySearch(list, element, comparator));
		}
		else
		{
			return null;
		}
	}
	
	public static boolean contain(ArrayList list, String RetrievalPlankey)
	{
		if (RetrievalPlankey != null)
		{
			RetrievalPlankeySortable element = new RetrievalPlankeySortableHandler.RetrievalPlankeyEntity(
					RetrievalPlankey);
			return Collections.binarySearch(list, element, comparator) >= 0;
		}
		else
		{
			return false;
		}
	}
}
