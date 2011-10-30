/**
 *
 */
package project.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author oleksiizozulenko
 */
public class ConnectionListHelper
{
	public static ConcurrentMap<Connection, List<Connection>> getDuplicatedConfigs(
			Collection<Connection> connections)
	{
		ConcurrentMap<Connection, List<Connection>> duplicates = new ConcurrentHashMap<Connection, List<Connection>>();
		
		Set<Connection> hasDuplicate = new HashSet<Connection>();
		
		// Get keys for map
		for (Connection conn : connections)
		{
			String duplicateLabel = conn.getDuplicateLabel();
			
			if (duplicateLabel == null)
			{
				hasDuplicate.add(conn);
			}
			else if (duplicateLabel.equalsIgnoreCase(""))
			{
				hasDuplicate.add(conn);
			}
		}
		
		// Add values for map
		
		for (Connection keyConn : hasDuplicate)
		{
			List<Connection> isDuplicate = new ArrayList<Connection>();
			for (Connection conn : connections)
			{
				String connLabel = keyConn.getLabel();
				String dupLabel = conn.getDuplicateLabel();
				
				if (connLabel != null && dupLabel != null)
				{
					if (connLabel.equalsIgnoreCase(dupLabel))
					{
						isDuplicate.add(conn);
					}
				}
				
			}
			
			duplicates.put(keyConn, isDuplicate);
		}
		
		return duplicates;
	}
}
