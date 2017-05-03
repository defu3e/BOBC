import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Security;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;











import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class CipherTest implements Runnable {

	public static final int mbTob = 1000000;	// байтов в 1 МБ
	
	public int TEXT_SIZE;	// размер текста
	public int KEY_SIZE;	// длина ключа
	public int IV_SIZE;		// длина инициализирующего вектора
	public int ciph_mode;	// идентификатор шифра
	public int lang;		// язык реализации
	
	public Process process;	// процесс для запуска exe файлов
	
	String ciph_name;		// название алгаритма
	
	/*Переменные графического интерфейса*/
	public JTextArea textArea;
	public JProgressBar progBar;
	public JButton stBut;
	
	/*Переменная для записи в текстовый файл*/
	public FileWriter lst;
	
	int[][] ciph_params = { /*размер ключа и длина блока*/
							{16,16},	// AES
							{16,8},		// Blowfish
							{16,8},		// CAST5
							{24,8}		// 3DES
							};
	
	String[] ciph_names = {"AES", "Blowfish", "CAST5", "DESede"}; 
	
	/*Конструктор для теста скоростей шифров на Java*/
	public CipherTest (int ciph_mode, int TEXT_SIZE, JTextArea textArea, JProgressBar progBar, JButton stBut,String lstName, int lang)
	{
		Security.addProvider(new BouncyCastleProvider());
		
		this.TEXT_SIZE 	= TEXT_SIZE*mbTob;
		this.ciph_mode 	= ciph_mode;
		this.KEY_SIZE	= ciph_params	[ciph_mode][0];
		this.IV_SIZE	= ciph_params	[ciph_mode][1];
		this.ciph_name 	= ciph_names	[ciph_mode];
		
		this.textArea	= textArea;
		this.progBar 	= progBar;
		this.stBut		= stBut;
		this.lang		= lang;
		
		try {
			lst = new FileWriter(lstName, true);
		} catch (IOException e) {
			new Err(6);	// Ошибка открытия файла для записи результатов теста
			return;
		}
		
	}
	
	/*Конструктор для теста скоростей шифров на C++*/
	public CipherTest (int TEXT_SIZE, JTextArea textArea, JProgressBar progBar, JButton stBut,String lstName, String exe_name, int lang)
	{
		
		this.TEXT_SIZE 	= TEXT_SIZE*mbTob;
				
		this.textArea	= textArea;
		this.progBar 	= progBar;
		this.stBut		= stBut;
		this.lang		= lang;
		this.ciph_name	= exe_name;
		
		try {
			lst = new FileWriter(lstName, true);
		} catch (IOException e) {
			new Err(6);	// Ошибка открытия файла для записи результатов теста
			return;
		}
	}
	

/* Шифрование на Java */
public byte[] encryptOrDecrypt(int mode, byte[] key, byte[] IV , byte[] in) throws Throwable {
	SecretKeySpec KS = new SecretKeySpec(key, ciph_name);
	Cipher cipher = Cipher.getInstance(ciph_name+"/CBC/NoPadding", "BC");
	
	IvParameterSpec ivParameterSpec = new IvParameterSpec(IV);
	
	progBar.setValue(60);
	
	try {
	if (mode == Cipher.ENCRYPT_MODE) {
		cipher.init(Cipher.ENCRYPT_MODE, KS, ivParameterSpec);
		return cipher.doFinal(in);
	} else if (mode == Cipher.DECRYPT_MODE) {
		cipher.init(Cipher.DECRYPT_MODE, KS, ivParameterSpec);
		return cipher.doFinal(in);
	}
	return IV;
	}
	catch (OutOfMemoryError e){new Err(5); textArea.setText(null);}
	
	return null;
}

/*Перевод байтового массива в HEX-строку*/
public String bytesToHex(byte[] bytes)  {
	char[] hexArray = "0123456789Abcdef".toCharArray();
		char[] hexChars = new char[bytes.length * 2];
		for ( int j = 0; j < bytes.length; j++ ) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

/*Запись строки в файл*/
void writeToLst (String str) {
	try {
		lst.append(str);
	} catch (IOException e) {		//e.printStackTrace();
	}
}

/*Замер времени шифрования*/
 public void printOpTime (long st)
    {
    	long en = System.nanoTime();
		st = en-st;
		double seconds = (double)st / 1000000000.0;
		textArea.append ("\nEncryption time: " +  String.format("%.3f", seconds) + " seconds");
		textArea.append ("\nEncryption speed: " +  String.format("%.3f", (TEXT_SIZE/mbTob)/seconds) + " MB/s"); 
		
		writeToLst ("\nEncryption time: " +  String.format("%.3f", seconds) + " seconds");
		writeToLst ("\nEncryption speed: " +  String.format("%.3f", (TEXT_SIZE/mbTob)/seconds) + " MB/s"); 
    }
 
@Override
public void run() {
	
	switch (lang)
	{
	case 0: // тест алгоритма на Java
	
	textArea.append("\n=== "+ ciph_name+ " ===");
	writeToLst("\n=== "+ ciph_name+ " ===");
	
	progBar.setValue(10);
	
	textArea.append("\nInput data generation...");
	writeToLst("\nInput data generation...");
	
	byte[] in = null, key = null, IV = null;
	
	try 
	{
		in 	= new byte[TEXT_SIZE];
		key = new byte[KEY_SIZE];
		IV 	= new byte[IV_SIZE];
	}
		catch (OutOfMemoryError e) {
			stBut.setEnabled(true); 
			textArea.setText(null); 
			progBar.setValue(0);
			new Err(4);  return;
			}
	
	Random r = new Random ();
	
	r.nextBytes(in);
	r.nextBytes(key);
	r.nextBytes(IV);
	
	progBar.setValue(50);
	
	try {
		Thread.sleep(500);
	} catch (InterruptedException e1) {
		stBut.setEnabled(true);
		return;
	}
	
	textArea.append(" Successful");
	writeToLst(" Successful");

	textArea.append("\nText size: "+TEXT_SIZE/mbTob+" MB");
	textArea.append("\nGenerated key:\t " + bytesToHex(key));
	textArea.append("\nGenerated IV:\t " + bytesToHex(IV));
	
	writeToLst("\nText size: "+TEXT_SIZE/mbTob+" MB");
	writeToLst("\nGenerated key:\t " + bytesToHex(key));
	writeToLst("\nGenerated IV:\t " + bytesToHex(IV));
	
	try {
		Thread.sleep(500);
	} catch (InterruptedException e1) {
		System.out.println("Interrupted");
		stBut.setEnabled(true);
		return;
	}
	
	progBar.setValue(55);
	
	//=================================== Encryption=========================================
	 textArea.append("\nEncription...");
	 long st = System.nanoTime();
		try {
			if (encryptOrDecrypt(Cipher.ENCRYPT_MODE, key, IV, in)==null)
				{
				stBut.setEnabled(true);
				progBar.setValue(0);
				return;
				}
		} catch (Throwable e) {
			System.out.println("ERROR?");
		}
	
	 progBar.setValue(90);
	 
	 try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			System.out.println("Interrupted");
			stBut.setEnabled(true);
			return;
		}
	 
	 textArea.append (" Done!");
	 writeToLst(" Done!");
	 printOpTime(st);
	 	 
	 progBar.setValue(100);
	 
	 stBut.setEnabled(true);
	 
	 try {
			lst.close();
		} catch (IOException e) {//e.printStackTrace();
		}
	 
	 break;
	 /****************************************/
	 
	case 1:	//C++
		 progBar.setValue(5);

		 String parPT[] = {ciph_name, Integer.toString(TEXT_SIZE / mbTob) };
		 		 
		 	ProcessBuilder builder = new ProcessBuilder(parPT);
			builder.redirectErrorStream(true);
			progBar.setValue(25);
			try {
				process = builder.start();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (Thread.currentThread().isInterrupted())
				process.destroy();

			try {
				BufferedReader br = new BufferedReader(
						new InputStreamReader(process.getInputStream()));

				if (Thread.currentThread().isInterrupted())
					process.destroy();
				
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					stBut.setEnabled(true);
					return;
				}

				String line;

				if (Thread.currentThread().isInterrupted())
					process.destroy();

				progBar.setValue(55);

				while ((line = br.readLine()) != null) {
					writeToLst(line + "\n");
					textArea.append(line + "\n");
				}

				progBar.setValue(75);

				if (Thread.currentThread().isInterrupted())
					process.destroy();

				try {

					if (!Thread.currentThread().isInterrupted())
						process.waitFor();
					else
						process.destroy();

				} catch (InterruptedException consumed) {
					process.destroy();
					return;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			progBar.setValue(100);
		 stBut.setEnabled(true);
		 try {
			lst.close();
		} catch (Exception e) {}
	} 
}

}	