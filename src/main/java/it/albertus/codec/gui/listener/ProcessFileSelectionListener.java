package it.albertus.codec.gui.listener;

import it.albertus.codec.engine.CodecMode;
import it.albertus.codec.gui.CodecGui;
import it.albertus.codec.gui.ProcessFileJob;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.FileDialog;

public class ProcessFileSelectionListener extends SelectionAdapter {

	private final CodecGui gui;

	public ProcessFileSelectionListener(CodecGui gui) {
		this.gui = gui;
	}

	@Override
	public void widgetSelected(SelectionEvent event) {
		/* Selezione file sorgente */
		final FileDialog openDialog = new FileDialog(gui.getShell(), SWT.OPEN);
		if (CodecMode.DECODE.equals(gui.getEngine().getMode())) {
			openDialog.setFilterExtensions(new String[] { "*." + gui.getEngine().getAlgorithm().name().toLowerCase() + "; *." + gui.getEngine().getAlgorithm().name().toUpperCase(), "*.*" });
		}
		final String sourceFileName = openDialog.open();
		if (sourceFileName != null && sourceFileName.length() > 0) {
			/* Selezione file destinazione */
			final FileDialog saveDialog = new FileDialog(gui.getShell(), SWT.SAVE);
			saveDialog.setOverwrite(true);
			if (CodecMode.ENCODE.equals(gui.getEngine().getMode())) {
				saveDialog.setFilterExtensions(new String[] { "*." + gui.getEngine().getAlgorithm().name().toLowerCase() + "; *." + gui.getEngine().getAlgorithm().name().toUpperCase(), "*.*" });
				saveDialog.setFileName(sourceFileName + '.' + gui.getEngine().getAlgorithm().name().toLowerCase());
			}
			else {
				if (sourceFileName.indexOf('.') != -1) {
					saveDialog.setFileName(sourceFileName.substring(0, sourceFileName.lastIndexOf('.')));
				}
			}
			final String destinationFileName = saveDialog.open();
			if (destinationFileName != null && destinationFileName.length() > 0) {
				/* Disabilitazione controlli durante l'esecuzione */
				gui.disableControls();

				/* Impostazione puntatore del mouse "Occupato" */
				gui.getShell().setCursor(gui.getShell().getDisplay().getSystemCursor(SWT.CURSOR_WAIT));
				
				new ProcessFileJob(gui, new File(sourceFileName), new File(destinationFileName)).schedule();
			}
		}
	}

}
