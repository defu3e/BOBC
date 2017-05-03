import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.alee.laf.WebLookAndFeel;
/*����� ��� ��������� ��������� ��������*/
class Err extends Exception 
{ 

	private static final long serialVersionUID = 1L;
	
	public Err(int num)
    {
		WebLookAndFeel.install();
		
        String[] errArray = 
        {
       /*0*/ "������ �������� �������� ���� ���������.",
       /*1*/ "��� ������������� ��������� ��� ���������.",
       /*2*/ "������ ����� �������� � �������.",
       /*3*/ "�������� �� �������� ����������. ��� ������ ��� ���������� ������� ������.\n�������� ���� �� ���� ���������.",
       /*4*/ "������������ ������ ��� ��������� ��������� ������ ������.\n���������� ������ ������ ���������.",
       /*5*/ "������������ ������ ��� �������� ����������.\n��� ���������� ������ ��������� ���������� 2 �� ����������� ������",
       /*6*/ "������ �������� ����� ��� ������ ����������� ������������.",
       /*7*/ "�� ����������, ��� � ��� ������������ ����������� ������ ��� ���������� ������ ���������.\n"+
       		 "��������� �� ����� 2 �� ��������� ������.\n��������� �� ����� ����� ��������, �� ��������� ������� ����� ����������.",
       	/*8*/"�� ������ ��������� ������� ���������. ����������, ���������� ��������� ����� ������ ��� ����� ������������ �������."
        };
        String errMessage = message(errArray,num); 
        JFrame frm = new JFrame();
        JOptionPane.showMessageDialog(frm, errMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }
        public String message (String[] errArray,int num) 
        {
            String mes="";
                for (int i=0;i<errArray.length;i++) 
                     if (i==num) mes = errArray[i]  ;                              
            return (mes);
        }
}