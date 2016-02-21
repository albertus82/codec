package it.albertus.codec;

import it.albertus.codec.gui.Images;

import org.apache.commons.codec.binary.Base64;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class Codec {

	private CodecOption codec;

	public enum CodecOption {
		BASE64(0, "Base64");

		private final int index;
		private final String name;

		private CodecOption(int index, String name) {
			this.index = index;
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}

		public int getIndex() {
			return index;
		}

		public String getName() {
			return name;
		}

		public static String[] getAll() {
			String[] names = new String[CodecOption.values().length];
			for (int i = 0; i < CodecOption.values().length; i++) {
				names[i] = CodecOption.values()[i].name;
			}
			return names;
		}

	}

	public static void main(String[] args) {
		Codec codec = new Codec();
		if (args.length > 0) {
			// TODO Console version
		}
		else {
			final Display display = new Display();
			final Shell shell = codec.createShell(display);
			shell.open();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
			display.dispose();
		}
	}

	private Shell createShell(Display display) {
		final Shell shell = new Shell(display);
		shell.setImages(Images.ICONS);
		shell.setText("Codec");
		shell.setSize(500, 150);
		shell.setLayout(new GridLayout(6, false));

		final Label inputLabel = new Label(shell, SWT.NONE);
		inputLabel.setText("Input:");

		final Text inputText = new Text(shell, SWT.BORDER);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 5;
		inputText.setLayoutData(gridData);

		final Label outputLabel = new Label(shell, SWT.NONE);
		outputLabel.setText("Output:");
		// gridData = new GridData();
		// outputLabel.setLayoutData(gridData);

		final Text outputText = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 5;
		outputText.setLayoutData(gridData);

		final Label codecLabel = new Label(shell, SWT.NONE);
		codecLabel.setText("Codec:");
		gridData = new GridData();
		codecLabel.setLayoutData(gridData);

		final Combo codecCombo = new Combo(shell, SWT.DROP_DOWN | SWT.READ_ONLY);
		codecCombo.setItems(CodecOption.getAll());
		codecCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println(CodecOption.values()[(codecCombo.getSelectionIndex())]);
				codec = CodecOption.values()[(codecCombo.getSelectionIndex())];
			}
		});

		inputText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
//				Color normalColor = outputText.getForeground();
//				String input = inputText.getText();
				String input = ((Text) e.widget).getText();
				String output;
				if (codec != null) {
					switch (codec) {
					case BASE64:
						output = Base64.encodeBase64String(input.getBytes());
						break;
					default:
						output = "-- Select codec --";
					}
				}
				else {
					output = "-- Select codec --";
					outputText.getForeground();
//					outputText.setForeground(outputText.getDisplay().getSystemColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND));
				}
				outputText.setText(output);
			}
		});

		shell.open();
		return shell;
	}

}
