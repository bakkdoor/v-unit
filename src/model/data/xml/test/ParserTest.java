package model.data.xml.test;

import java.util.Date;

import model.CurrentDate;
import model.exceptions.CurrentDateException;
import junit.framework.TestCase;

public class ParserTest extends TestCase
{

	protected void setUp() throws Exception
	{
		super.setUp();

		try
		{
			CurrentDate.set(new Date());
		}
		catch (CurrentDateException e2)
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}

	protected void tearDown() throws Exception
	{
		super.tearDown();
	}

}
