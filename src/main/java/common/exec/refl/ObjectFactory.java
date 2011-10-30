package common.exec.refl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ObjectFactory
{
	@SuppressWarnings("unchecked")
	private Class _oClass = null;
	
	@SuppressWarnings("unchecked")
	private Constructor _oConstructor = null;
	
	private Method _oMethod = null;
	
	private Object _oObject = null;
	@SuppressWarnings("unchecked")
	private Class[] _loConstrParams;
	private Object[] _loConstrValues;
	
	
	@SuppressWarnings("unchecked")
	public ObjectFactory(Class[] oParams, Object[] oValues)
	{
		this._loConstrParams = oParams;
		this._loConstrValues = oValues;
	}
	
	@SuppressWarnings("unchecked")
	public Object callObject(String sClassName)
			throws InvocationTargetException, NoSuchMethodException,
			InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		try
		{
			
			_oClass = Class.forName(sClassName);
			_oConstructor = _oClass.getDeclaredConstructor(this._loConstrParams);
			
			_oObject = _oConstructor.newInstance(this._loConstrValues);
		}
		catch (SecurityException se)
		{
			throw se;
		}
		catch (NoSuchMethodException nsme)
		{
			throw nsme;
		}
		catch (IllegalArgumentException iae)
		{
			throw iae;
		}
		catch (InstantiationException ie)
		{
			throw ie;
		}
		catch (IllegalAccessException iacce)
		{
			throw iacce;
		}
		catch (InvocationTargetException ite)
		{
			throw ite;
		}
		catch (ClassNotFoundException cnfe)
		{
			//TODO: add skipping steps if class not found
			throw cnfe;
		}
		
		return _oObject;
	}
	
	@SuppressWarnings("unchecked")
	public Object callMethod(String sMethodName, Class[] oParams,
			Object[] oValues) throws InvocationTargetException,
			NoSuchMethodException, IllegalAccessException
	{
		Object oReturn = null;
		try
		{
			_oMethod = _oClass.getMethod(sMethodName, oParams);
			oReturn = _oMethod.invoke(_oObject, oValues);
		}
		catch (SecurityException e)
		{
			throw e;
		}
		catch (NoSuchMethodException e)
		{
			throw e;
		}
		catch (IllegalArgumentException e)
		{
			throw e;
		}
		catch (IllegalAccessException e)
		{
			throw e;
		}
		catch (InvocationTargetException e)
		{
			throw e;
		}
		
		return oReturn;
	}
}
