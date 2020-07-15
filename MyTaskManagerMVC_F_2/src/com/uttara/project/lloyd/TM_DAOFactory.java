package com.uttara.project.lloyd;

public class TM_DAOFactory {

	public static TaskManagerDAO getInstance(int id) {
		
		switch(id)
		{
		case 1 : 
			return new FSDAO();
			
		case 2 : 
			return new HSQLDBDAO();
			
		default :  
			throw new IllegalArgumentException("this id is not supported yet");
		
		}
		
	}
	
}
