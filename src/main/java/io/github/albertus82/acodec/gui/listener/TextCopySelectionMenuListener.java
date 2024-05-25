package io.github.albertus82.acodec.gui.listener;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Text;

public class TextCopySelectionMenuListener extends TextListener implements SelectionListener {

	public TextCopySelectionMenuListener(final Text text) {
		super(text);
	}

	@Override
	public void widgetSelected(final SelectionEvent e) {
		copySelection();
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {/* Ignore */}

}
