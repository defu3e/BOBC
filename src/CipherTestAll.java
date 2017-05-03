import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class CipherTestAll implements Runnable {

	public static final int mbTob = 1000000;

	public final int TEXT_SIZE;
	public int KEY_SIZE;
	public int IV_SIZE;
	public int ciph_mode;
	public int lang;
	public Process process;

	String ciph_name;

	String[] exe_names;

	public JTextArea textArea;
	public JProgressBar progBar;
	public JButton stBut;

	public FileWriter lst;

	int[][] ciph_params = { { 16, 16 }, // AES
			{ 16, 8 }, // Blowfish
			{ 16, 8 }, // CAST5
			{ 24, 8 } // 3DES
	};

	public final String[] ciph_names = { "AES", "Blowfish", "CAST5", "DESede" };

	/* конструктор для теста на Java */
	public CipherTestAll(int TEXT_SIZE, JTextArea textArea,
			JProgressBar progBar, JButton stBut, String lstName) {
		Security.addProvider(new BouncyCastleProvider());

		this.TEXT_SIZE = TEXT_SIZE * mbTob;

		this.textArea = textArea;
		this.progBar = progBar;
		this.stBut = stBut;
		this.lang = 0;

		try {
			lst = new FileWriter(lstName, true);
		} catch (IOException e) {
			new Err(6); // Ошибка открытия файла для записи результатов теста
			return;
		}

	}

	/* конструктор для теста на C++ */
	public CipherTestAll(int TEXT_SIZE, JTextArea textArea,
			JProgressBar progBar, JButton stBut, String lstName,
			String[] exe_names) {
		Security.addProvider(new BouncyCastleProvider());

		this.TEXT_SIZE = TEXT_SIZE * mbTob;

		this.textArea = textArea;
		this.progBar = progBar;
		this.stBut = stBut;
		this.lang = 1;
		this.exe_names = exe_names;

		try {
			lst = new FileWriter(lstName, true);
		} catch (IOException e) {
			new Err(6); // Ошибка открытия файла для записи результатов теста
			return;
		}

	}

	/* Шифрование на Java */
	public byte[] encryptOrDecrypt(int mode, String alg, byte[] key, byte[] IV,
			byte[] in) {
		SecretKeySpec KS = new SecretKeySpec(key, alg);
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance(alg + "/CBC/NoPadding", "BC");
		} catch (NoSuchAlgorithmException | NoSuchProviderException
				| NoSuchPaddingException e1) {
			e1.printStackTrace();
		}

		IvParameterSpec ivParameterSpec = new IvParameterSpec(IV);

		try {
			if (mode == Cipher.ENCRYPT_MODE) {
				try {
					cipher.init(Cipher.ENCRYPT_MODE, KS, ivParameterSpec);
				} catch (InvalidKeyException e) {
					e.printStackTrace();
				} catch (InvalidAlgorithmParameterException e) {
					e.printStackTrace();
				}
				try {
					return cipher.doFinal(in);
				} catch (IllegalBlockSizeException e) {
					e.printStackTrace();
				} catch (BadPaddingException e) {
					e.printStackTrace();
				}
			}
		} catch (OutOfMemoryError e) {
			new Err(5);
			textArea.setText(null);
		}

		return null;
	}

	public String bytesToHex(byte[] bytes) {
		char[] hexArray = "0123456789abcdef".toCharArray();
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	public double printOpTime(long st) {
		long en = System.nanoTime();
		st = en - st;
		double seconds = (double) st / 1000000000.0;
		textArea.append("\nEncryption time: " + String.format("%.3f", seconds)
				+ " seconds");
		writeToLst("\nEncryption time: " + String.format("%.3f", seconds)
				+ " seconds");
		double spd = (TEXT_SIZE / mbTob) / seconds;
		return spd;
	}
	
	void writeToLst(String str) {
		try {
			lst.append(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		int pbValue = 0;

		switch (lang) {
		case 0: // Тестирование алгоритмов на Java

			double[] speedResults = new double[ciph_names.length];

			for (int i = 0; i < ciph_names.length; i++) {

				this.KEY_SIZE = ciph_params[i][0];
				this.IV_SIZE = ciph_params[i][1];

				textArea.append("\n=== " + ciph_names[i] + " ===");
				writeToLst("\n=== " + ciph_names[i] + " ===");

				pbValue += 5;

				progBar.setValue(pbValue);

				textArea.append("\nInput data generation...");
				writeToLst("\nInput data generation...");

				byte[] in = null, key = null, IV = null;

				try {
					in = new byte[TEXT_SIZE];
					key = new byte[KEY_SIZE];
					IV = new byte[IV_SIZE];
				} catch (OutOfMemoryError e) {
					stBut.setEnabled(true);
					textArea.setText(null);
					progBar.setValue(0);
					new Err(4);
					return;
				}

				Random r = new Random();

				r.nextBytes(in);
				r.nextBytes(key);
				r.nextBytes(IV);

				pbValue += 5;

				progBar.setValue(pbValue);

				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					stBut.setEnabled(true);
					return;
				}

				textArea.append(" Successful");
				writeToLst(" Successful");

				textArea.append("\nText size: " + TEXT_SIZE / mbTob + " MB");
				textArea.append("\nGenerated key:\t " + bytesToHex(key));
				textArea.append("\nGenerated IV:\t " + bytesToHex(IV));

				writeToLst("\nText size: " + TEXT_SIZE / mbTob + " MB");
				writeToLst("\nGenerated key:\t " + bytesToHex(key));
				writeToLst("\nGenerated IV:\t " + bytesToHex(IV));

				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					stBut.setEnabled(true);
					return;
				}

				pbValue += 5;

				progBar.setValue(pbValue);

				// ===================================
				// Encryption=========================================
				textArea.append("\nEncription...");
				writeToLst("\nEncription...");
				long st = System.nanoTime();

				if (encryptOrDecrypt(Cipher.ENCRYPT_MODE, ciph_names[i], key,
						IV, in) == null) {
					stBut.setEnabled(true);
					progBar.setValue(0);
					return;
				}

				pbValue += 5;
				progBar.setValue(pbValue);

				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					stBut.setEnabled(true);
					return;
				}

				writeToLst(" Done!");
				textArea.append(" Done!");
				speedResults[i] = printOpTime(st);

				pbValue += 5;
				progBar.setValue(pbValue);
			}

			textArea.append("\n\n===== SPEED RESULTS =====\n\nAlgorithm\tSpeed\n-------------------------------"
					+ "\nAES\t"
					+ String.format("%.3f", speedResults[0])
					+ " MB/s"
					+ "\nBlowfish\t"
					+ String.format("%.3f", speedResults[1])
					+ " MB/s"
					+ "\nCAST-128\t"
					+ String.format("%.3f", speedResults[2])
					+ " MB/s "
					+ "\nTriple-DES\t"
					+ String.format("%.3f", speedResults[3]) + " MB/s");

			writeToLst("\n\n===== SPEED RESULTS =====\n\nAlgorithm\tSpeed\n-------------------------------"
					+ "\nAES\t"
					+ String.format("%.3f", speedResults[0])
					+ " MB/s"
					+ "\nBlowfish\t"
					+ String.format("%.3f", speedResults[1])
					+ " MB/s"
					+ "\nCAST-128\t"
					+ String.format("%.3f", speedResults[2])
					+ " MB/s "
					+ "\nTriple-DES\t"
					+ String.format("%.3f", speedResults[3]) + " MB/s");

			try {
				lst.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		// //////////////////////////////////////////////////////////
		case 1:// /C++

			String parPT[][] = {
					{ exe_names[0], Integer.toString(TEXT_SIZE / mbTob) },
					{ exe_names[1], Integer.toString(TEXT_SIZE / mbTob) },
					{ exe_names[2], Integer.toString(TEXT_SIZE / mbTob) },
					{ exe_names[3], Integer.toString(TEXT_SIZE / mbTob) } };

			for (int i = 0; i < exe_names.length; i++) {
				ProcessBuilder builder = new ProcessBuilder(parPT[i]);
				builder.redirectErrorStream(true);
				pbValue += 5;
				progBar.setValue(pbValue);
				try {
					process = builder.start();
				} catch (IOException e) {
					e.printStackTrace();
				}

				if (Thread.currentThread().isInterrupted())
					process.destroy();

				pbValue += 5;
				progBar.setValue(pbValue);
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

					pbValue += 5;
					progBar.setValue(pbValue);

					while ((line = br.readLine()) != null) {
						writeToLst(line + "\n");
						textArea.append(line + "\n");
					}

					pbValue += 5;
					progBar.setValue(pbValue);

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

			} // end for
			break;
		} // end swith

		try {lst.close();} 
		catch (Exception e) {}
		
		progBar.setValue(100);
		stBut.setEnabled(true);
	}
}
