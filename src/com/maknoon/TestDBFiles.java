package com.maknoon;

import java.io.*;
import java.sql.*;
import java.util.*;

// Testing the audio files.
public class TestDBFiles
{
	static String fileSeparator = System.getProperty("file.separator");
	public static void main(String[] args)
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql:///test"+"?socketFactory=com.mysql.jdbc.NamedPipeSocketFactory", "root", "secret");

			//Class.forName("sun.jdbc.odbc.JdbcOdbcDriver").newInstance();
			//Connection con = DriverManager.getConnection("jdbc:odbc:;DRIVER=Microsoft Access Driver (*.mdb);DBQ=E:\\AudioCataloger\\Access_DB_Versions\\index-14.mdb;PWD=mypass","","");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Path, FileName FROM Chapters"); //  WHERE sheekhid <> 2                   '!='  the same as  '<>'

			Vector<String> dbFiles = new Vector<String>();
			while(rs.next())
				dbFiles.add("E:\\Maknoon Audio Cataloger Audios\\"+rs.getString("Path")+"\\"+rs.getString("FileName")+".rm");

			con.close();

			Vector<String> files = new Vector<String>();
			getFileList(new File("E:\\Maknoon Audio Cataloger Audios"), files);

			System.out.println(dbFiles.size());
			System.out.println(files.size());
			/*
			for(int i=0; i<files.size(); i++)
			{
				if(dbFiles.indexOf(files.elementAt(i)) == -1)
					System.out.println(files.elementAt(i));
			}
			*/
			for(int i=0; i<dbFiles.size(); i++)
			{
				if(files.indexOf(dbFiles.elementAt(i)) == -1)
					System.out.println(dbFiles.elementAt(i));
			}
		}
		catch(Exception e){e.printStackTrace();}
	}

	public static void getFileList(File root, Vector<String> result)
	{
		if(root.isDirectory())
		{
			File [] child = root.listFiles();
			if(child!=null && child.length>0)
			{
				for(int i=0; i<child.length; i++)
					getFileList(child[i], result);
			}
		}
		else
		{
			result.add(root.toString());
			//System.out.println(root);
		}
	}
}