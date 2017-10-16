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

public class MainWindow {

	protected Shell shell;
	
	public int userScore = 0, clickerBonus = 1, bonusLevel = 0;
	private Text text;
	private Text text_1;

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
		shell.setSize(510, 352);
		shell.setText("SWT Application");
		
		// User Score Text
		text = new Text(shell, SWT.BORDER);
		text.setBounds(71, 7, 76, 21);
		
		// Upgrade Clicker Button Text
		text_1 = new Text(shell, SWT.BORDER);
		text_1.setText("20");
		text_1.setBounds(168, 241, 76, 21);
		
		// Clicker Button
		Button clickerButton = new Button(shell, SWT.NONE);
		clickerButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				userScore += clickerBonus;
				text.setText("" + userScore);
			}
		});
		clickerButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
			
			
		});
		
		clickerButton.setBounds(10, 182, 228, 49);
		clickerButton.setText("Add +" + clickerBonus);
		
		Label lblUserScore = new Label(shell, SWT.NONE);
		lblUserScore.setBounds(10, 10, 55, 15);
		lblUserScore.setText("User Score:");
		
		// Upgrade Clicker Button
		Button btnUpgradeClicker = new Button(shell, SWT.NONE);
		btnUpgradeClicker.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (userScore >= Math.round((20+17*bonusLevel*Math.pow(bonusLevel, 1.59)))) {
					clickerBonus *= 2;
					clickerButton.setText("Add +" + clickerBonus);
					userScore -= Math.round((20+17*bonusLevel*Math.pow(bonusLevel, 1.59)));
					text.setText(""+userScore);
					bonusLevel++;
					text_1.setText("" + Math.round((20+17*bonusLevel*Math.pow(bonusLevel, 1.59))));
				}
			}
		});
		btnUpgradeClicker.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnUpgradeClicker.setBounds(10, 237, 152, 25);
		btnUpgradeClicker.setText("Upgrade Clicker");
		
		
		
		

	}
}
