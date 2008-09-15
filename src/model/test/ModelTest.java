package model.test;

import java.util.Date;

import junit.framework.TestCase;

import model.CurrentDate;
import model.data.DataBase;

/**
 * ModelTest.java
 * @author Christopher Bertels (chbertel@uos.de)
 * @date 15.09.2008
 * 
 * Oberklasse aller UnitTests für Klassen im model Package.
 */
public abstract class ModelTest extends TestCase
{
	protected void setUp() throws Exception
	{
		super.setUp();

		if (!CurrentDate.isSet())
		{
			CurrentDate.set(new Date());
		}
		
		DataBase.loadTestData();
	}
}
