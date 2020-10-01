package it.albertus.acodec.gui.listener;

import java.io.File;

import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;

import it.albertus.acodec.gui.CodecGui;

public class ShellDropListener extends ProcessFileAction implements DropTargetListener {

	public ShellDropListener(final CodecGui gui) {
		super(gui);
	}

	@Override
	public void drop(final DropTargetEvent event) {
		if (gui.getAlgorithm() != null) {
			if (event.data instanceof String[]) { // file
				final String[] data = (String[]) event.data;
				if (data.length == 1) {
					processFile(new File(data[0]));
				}
			}
			else if (event.data instanceof String) { // text
				processText(event.data.toString());
			}
		}
	}

	private void processText(final String text) {
		gui.getInputText().setText(text);
	}

	private void processFile(final File file) {
		if (file.exists() && !file.isDirectory()) {
			final String sourceFileName = file.getPath();
			final String destinationFileName = getDestinationFile(sourceFileName);
			if (destinationFileName != null && destinationFileName.length() > 0) {
				execute(sourceFileName, destinationFileName);
			}
		}
	}

	@Override
	public void dragEnter(final DropTargetEvent event) {/* Ignore */}

	@Override
	public void dragLeave(final DropTargetEvent event) {/* Ignore */}

	@Override
	public void dragOperationChanged(final DropTargetEvent event) {/* Ignore */}

	@Override
	public void dragOver(final DropTargetEvent event) {/* Ignore */}

	@Override
	public void dropAccept(final DropTargetEvent event) {/* Ignore */}

}
