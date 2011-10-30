/**
 *
 */
package org.suites;

import oz.runner.annotations.Group;
import oz.runner.annotations.Priority;
import project.utils.Connection;
import app.Severity;



/**
 * @author oleksii.zozulenko
 */

@SuppressWarnings("unchecked")
public abstract class AbstractSuite extends AbstractFunctionalTestCase implements TestSuite,
		 Comparable
{


	protected String _timeout;

	protected String _baseUrl;

	protected Connection _oConn;


	public AbstractSuite()
	{
		super();
	}

	public AbstractSuite(Connection conn)
	{
		synchronized (this)
		{

			this._oConn = conn;
		}
	}



	protected abstract void initContainers();

	protected void initFields()
	{
		_baseUrl = _oConn.getApplicationUrl();
		_timeout = _oConn.getApplicationTimeout();
	}

	public int compareTo(Object arg0)
	{

		AbstractFunctionalTestCase other = (AbstractFunctionalTestCase) arg0;

		Priority pr1 = this.getClass().getAnnotation(Priority.class);
		Priority pr2 = arg0.getClass().getAnnotation(Priority.class);

		if (pr1 == null && pr2 == null)
		{
			return compareByGroupPriority(arg0);
		}
		else if (pr1 == null && pr2 != null)
		{
			Group grp1 = this.getClass().getAnnotation(Group.class);

			if (grp1 != null)
			{
				Severity sever = grp1.priority();

				return comparePriority(sever, pr2.value());
			}
			else
			{
				return -pr2.value().ordinal();
			}
		}
		else if (pr1 != null && pr2 == null)
		{
			Group grp2 = other.getClass().getAnnotation(Group.class);

			if (grp2 != null)
			{
				return this.comparePriority(pr1.value(), grp2.priority());
			}
			else
			{
				return pr1.value().ordinal();
			}
		}
		else
		{
			return comparePriority(pr1.value(), pr2.value());
		}

	}

	private int comparePriority(Severity pr1, Severity pr2)
	{
		if (pr1 == null && pr2 != null)
		{
			return -pr2.ordinal();
		}
		else if (pr1 == null && pr2 == null)
		{
			return 0;
		}
		else if (pr1 != null && pr2 == null)
		{
			return pr1.ordinal();
		}
		else
		{
			int prio1 = pr1.ordinal();
			int prio2 = pr2.ordinal();

			return ordinalCompare(prio1, prio2);
		}
	}

	private int ordinalCompare(int o1, int o2)
	{
		if (o1 < o2)
		{
			return -Math.max(o1, o2);
		}
		else if (o1 == o2)
		{
			return 0;
		}
		else
		{
			return Math.max(o1, o2);
		}
	}

	private int compareByGroupPriority(Object arg0)
	{
		AbstractFunctionalTestCase other = (AbstractFunctionalTestCase) arg0;

		Group grp1 = this.getClass().getAnnotation(Group.class);
		Group grp2 = other.getClass().getAnnotation(Group.class);

		if (grp1 == null && grp2 != null)
		{
			return this.comparePriority(null, grp2.priority());
		}
		else if (grp1 == null && grp2 == null)
		{
			return 0;
		}
		else if (grp1 != null && grp2 == null)
		{
			return this.comparePriority(grp1.priority(), null);
		}
		else
		{
			Severity pr1 = grp1.priority();
			Severity pr2 = grp2.priority();

			return this.comparePriority(pr1, pr2);

		}
	}

}
