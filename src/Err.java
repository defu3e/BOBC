import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.alee.laf.WebLookAndFeel;
/*Класс для обработки ошибочных ситуаций*/
class Err extends Exception 
{ 

	private static final long serialVersionUID = 1L;
	
	public Err(int num)
    {
		WebLookAndFeel.install();
		
        String[] errArray = 
        {
       /*0*/ "Ошибка создания главного окна программы.",
       /*1*/ "Нет выполняющихся процессов для остановки.",
       /*2*/ "Ошибка сбора сведений о системе.",
       /*3*/ "Черточка не является алгоритмом. Она служит для разделения пунктов списка.\nВыберите один из пяти вариантов.",
       /*4*/ "Недостаточно памяти для генерации заданного набора данных.\nПопробуйте задать другие параметры.",
       /*5*/ "Недостаточно памяти для процесса шифрования.\nДля корректной работы программы необходимо 2 Гб оперативной памяти",
       /*6*/ "Ошибка открытия файла для записи результатов тестирования.",
       /*7*/ "Мы обнаружили, что у вас недостаточно оперативной памяти для корректной работы программы.\n"+
       		 "Требуется не менее 2 Гб доступной памяти.\nПрограмма всё равно будет запущена, но некоторые функции будут ограничены.",
       	/*8*/"Не заданы параметры запуска программы. Пожалуйста, запускайте программу через скрипт для своей операционной системы."
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