class Main {
	
       public static void main (String args[]) throws Err {
    	   
    	new SplashScreen();/*����� ��������*/
    	
    	try 
    	{
    		if (args[0].equals("war_msg")) new Err(7);
    	}
    	catch (ArrayIndexOutOfBoundsException E) {new Err(8); return;}
    	
		new GUI();		
		GUI.main_GUI();	/*�������� ���������� ������ GUI*/


    }
}