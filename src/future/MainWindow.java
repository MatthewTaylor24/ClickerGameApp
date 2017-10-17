package future;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.ProgressBar;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainWindow  {
	protected Shell shell;
	
	public long userScore = 0;
	public long clickerBonus = 2, clickerLevel = 0, clickerUpgradeCost = 20;
	public long workerCost = 100, workerCount = 0, workerStrength = 5, workerStrengthCost = 200, workerScorePerSecond = 0;
	public boolean hasThreadStarted = false;
	private Text textUserScore;
	private Text textWorkerCount;
	private Text textWorkerScorePerSecond;
	
	

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		
		/*
		 * Auto Clicker Thread Execution
		 */
		
		Thread th = new Thread() {
			public void run() {
				while(true) {
					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							userScore += workerStrength * workerCount;
							textUserScore.setText("" + userScore);
							
						}
					});
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		
		th.setDaemon(true);
		th.start();
		
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(679, 355);
		shell.setText("SWT Application");
		
//		if (!hasThreadStarted) {
//			workerThread();
//			hasThreadStarted = true;
//		}
		
		
		// TEXTS
		
		// User Score Text
		textUserScore = new Text(shell, SWT.BORDER);
		textUserScore.setText("0");
		textUserScore.setBounds(98, 7, 140, 21);
		
		// Worker Score Per Second
		textWorkerScorePerSecond = new Text(shell, SWT.BORDER);
		textWorkerScorePerSecond.setText("0");
		textWorkerScorePerSecond.setBounds(446, 79, 107, 21);
		
		// Worker Count
		textWorkerCount = new Text(shell, SWT.BORDER);
		textWorkerCount.setText("0");
		textWorkerCount.setBounds(446, 52, 107, 21);
		
		
		
		// LABELS
		
		// Label for User Score
		Label lblUserScore = new Label(shell, SWT.NONE);
		lblUserScore.setBounds(10, 10, 55, 15);
		lblUserScore.setText("User Score:");
		
		// Label for Worker Count
		Label lblWorkerCount = new Label(shell, SWT.NONE);
		lblWorkerCount.setBounds(314, 55, 83, 15);
		lblWorkerCount.setText("Worker Count:");
		
		// Label for Worker's Score Per Second
		Label lblScorePerSecond = new Label(shell, SWT.NONE);
		lblScorePerSecond.setBounds(314, 82, 107, 15);
		lblScorePerSecond.setText("Score Per Second:");
		
		
		
		
		// BUTTONS
		
		// Clicker Button
		Button clickerButton = new Button(shell, SWT.NONE);
		clickerButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				userScore += clickerBonus;
				textUserScore.setText("" + userScore);
			}
		});
		clickerButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
		
		clickerButton.setBounds(10, 182, 228, 49);
		clickerButton.setText("Add +" + clickerBonus);
		
		
		// Upgrade Clicker Button
		Button btnUpgradeClicker = new Button(shell, SWT.NONE);
		btnUpgradeClicker.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (userScore >= clickerUpgradeCost) {
					clickerBonus += 3;
					clickerButton.setText("Add +" + clickerBonus);
					
					userScore -= clickerUpgradeCost;
					textUserScore.setText(""+userScore);
					clickerLevel++;
					clickerUpgradeCost = (int) Math.round((20+5*clickerLevel*Math.pow(clickerLevel, 1.04)));
					btnUpgradeClicker.setText("Upgrade Clicker -- Cost: " + clickerUpgradeCost);
				}
			}
		});
		btnUpgradeClicker.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnUpgradeClicker.setBounds(10, 237, 228, 25);
		btnUpgradeClicker.setText("Upgrade Clicker -- Cost: " + clickerUpgradeCost);
		
		
		// Purchase Worker Button
		Button btnPurchaseWorker = new Button(shell, SWT.NONE);
		btnPurchaseWorker.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (userScore >= workerCost) {
					userScore -= workerCost;
					textUserScore.setText(""+userScore);
					workerCount++;
					workerCost = (int) (100 + Math.round(25*Math.pow(workerCount, 1.33)));
					workerScorePerSecond = workerCount * workerStrength;
					btnPurchaseWorker.setText("Purchase Worker -- Cost: " + workerCost);
					textWorkerCount.setText("" + workerCount);
					textWorkerScorePerSecond.setText("" + workerScorePerSecond);
				}
			}
		});
		btnPurchaseWorker.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnPurchaseWorker.setBounds(304, 26, 249, 25);
		btnPurchaseWorker.setText("Purchase Worker -- Cost: " + workerCost);
		
		Button btnUpgradeWorkerStrength = new Button(shell, SWT.NONE);
		btnUpgradeWorkerStrength.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if(userScore >= workerStrengthCost) {
					userScore -= workerStrengthCost;
					textUserScore.setText("" + userScore);
					workerStrength *= 3;
					workerStrengthCost += (int) Math.round(60*Math.pow(workerStrength, 1.71));
					btnUpgradeWorkerStrength.setText("Upgrade Worker Strength -- Cost: " + workerStrengthCost);
					workerScorePerSecond = workerCount * workerStrength;
					textWorkerScorePerSecond.setText("" + workerScorePerSecond);
				}
			}
		});
		btnUpgradeWorkerStrength.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnUpgradeWorkerStrength.setBounds(10, 268, 228, 25);
		btnUpgradeWorkerStrength.setText("Upgrade Worker Strength -- Cost: " + workerStrengthCost);
		
		
		
	}
}
