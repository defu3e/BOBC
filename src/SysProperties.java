import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

//  ласс дл¤ получени¤ сведений о системе
public class SysProperties extends Thread {
	
	public final int bInMb = 1000000;
	public JTextArea textArea;
	public JProgressBar prBar;
	public JButton buttn;
	
	public SysProperties(JTextArea textArea, JProgressBar prBar, JButton buttn)
	{
		this.textArea = textArea;
		this.prBar	  = prBar;
		this.buttn	  = buttn;
	}

	public void run()
	{
		
		textArea.setText(null);
		buttn.setEnabled(false);
		prBar.setIndeterminate (true);	
		prBar.setString ("Please wait...");
		
		CpuInfo cpu = null;
		Mem	    mem = null;
		
		try {
			sleep(500);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}	
		
			try {
				cpu = new Sigar().getCpuInfoList()[0];
				mem = new Sigar().getMem();
			} catch (SigarException e) {
				new Err(2);
				return;
			}
		
		
		String[][] prprts = 
			{
				{"\t========== Operation system ==========",		"\n"},
				{"\nName:\t",			System.getProperty("os.name")},
				{"\nVersion:\t",			System.getProperty("os.version")},
				{"\nArchitecture:\t",		System.getProperty("os.arch")},
				
				{"\n\n\n\t========== CPU ==========",		"\n"},
				{"\nIdentifier:\t",			String.valueOf (System.getenv("PROCESSOR_IDENTIFIER"))},
				{"\nArchitecture:\t",		String.valueOf (System.getenv("PROCESSOR_ARCHITECTURE"))},
				{"\nVendor:\t", 				cpu.getVendor()},
				{"\nModel:\t",				cpu.getModel()},
				{"\nFrequency:\t",			String.valueOf(cpu.getMhz())+" Mhz"},
				{"\nPhisical cores:\t",		String.valueOf (cpu.getTotalSockets())},
				{"\nLogical cores:\t",		String.valueOf (cpu.getTotalCores())},
				
				
				{"\n\n\n\t========== Memory ==========",		"\n"},
				{"\nTotal memory:\t",				String.valueOf(mem.getTotal()/bInMb)+" MB"},
				{"\nActual used:\t",				String.valueOf (mem.getActualUsed()/bInMb)+" MB ("+String.valueOf(String.format("%.2f", mem.getUsedPercent()))+" %)"},
				{"\nActual free:\t",				String.valueOf(mem.getActualFree()/bInMb)+" MB"},
				
				{"\n\n\n\t========== Java ==========",		"\n"},
				{"\nJRE version:\t", 				System.getProperty("java.version")},
				{"\nAvailable processors:\t",		String.valueOf (Runtime.getRuntime().availableProcessors())},
				{"\nMax memory for Java:\t",		String.valueOf (Runtime.getRuntime().maxMemory())},
				{"\nTotal memory for Java:\t",		String.valueOf (Runtime.getRuntime().totalMemory()/bInMb)+" MB"}
				
			};

		
		for (int i=0; i<prprts.length; i++)
		{
			textArea.append(prprts[i][0]);
			textArea.append(prprts[i][1]);
		}
		
		
		prBar.setString ("Done!");
		prBar.setIndeterminate (false);
		buttn.setEnabled(true);
		
	}// end run  
} // end class
