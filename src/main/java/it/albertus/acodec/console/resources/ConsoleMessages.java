package it.albertus.acodec.console.resources;

import java.util.ResourceBundle;

import it.albertus.acodec.common.resources.CommonMessages;
import it.albertus.acodec.common.resources.ConfigurableMessages;
import it.albertus.acodec.common.resources.Messages;
import lombok.NonNull;

public enum ConsoleMessages implements Messages {

	INSTANCE;

	private static final ConfigurableMessages commonMessages = CommonMessages.INSTANCE;

	private ResourceBundle resourceBundle = ResourceBundle.getBundle(getClass().getName().toLowerCase(), ResourceBundle.Control.getNoFallbackControl(ResourceBundle.Control.FORMAT_PROPERTIES));

	@Override
	public String get(@NonNull final String key) {
		return Defaults.get(key, resourceBundle, commonMessages::get);
	}

	@Override
	public String get(@NonNull final String key, final Object... params) {
		return Defaults.get(key, params, resourceBundle, commonMessages::get);
	}

}
