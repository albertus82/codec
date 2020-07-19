package it.albertus.codec.gui.listener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;

import it.albertus.codec.engine.CodecAlgorithm;
import it.albertus.codec.engine.CodecMode;
import it.albertus.codec.gui.CodecGui;

public class AlgorithmComboSelectionListener extends SelectionAdapter {

	private final CodecGui gui;

	public AlgorithmComboSelectionListener(final CodecGui gui) {
		this.gui = gui;
	}

	@Override
	public void widgetSelected(final SelectionEvent se) {
		final CodecAlgorithm algorithm = CodecAlgorithm.values()[gui.getAlgorithmCombo().getSelectionIndex()];
		gui.getEngine().setAlgorithm(algorithm);

		/* Attivazione drag and drop e pulsante elaborazione file */
		if (!gui.getProcessFileButton().getEnabled()) {
			gui.getProcessFileButton().setEnabled(true);
			gui.getMenuBar().getFileProcessMenuItem().setEnabled(true);
			gui.getShellDropTarget().setTransfer(FileTransfer.getInstance(), TextTransfer.getInstance());
		}

		/* Gestione radio */
		final Button encodeRadio = gui.getModeRadios().get(CodecMode.ENCODE);
		final Button decodeRadio = gui.getModeRadios().get(CodecMode.DECODE);

		if (algorithm.getModes().contains(CodecMode.DECODE)) {
			if (!decodeRadio.getEnabled()) {
				decodeRadio.setEnabled(true);
			}
		}
		else {
			if (decodeRadio.getEnabled()) {
				decodeRadio.setSelection(false);
				decodeRadio.setEnabled(false);
				encodeRadio.setSelection(true);
				encodeRadio.notifyListeners(SWT.Selection, null);
			}
		}

		gui.getInputText().notifyListeners(SWT.Modify, null);
	}

}
