package it.albertus.acodec.cli.resources;

import java.util.Collection;
import java.util.Locale;

import it.albertus.acodec.common.resources.FallbackMessages;
import it.albertus.acodec.common.resources.MessageBundle;
import it.albertus.acodec.common.resources.Messages;
import lombok.NonNull;

public enum ConsoleMessages implements Messages {

	INSTANCE;

	private static final Messages fallbackMessages = FallbackMessages.INSTANCE;

	private final MessageBundle bundle = new MessageBundle(getClass().getName().toLowerCase(Locale.ROOT));

	@Override
	public String get(@NonNull final String key) {
		return bundle.getMessage(key, fallbackMessages::get);
	}

	@Override
	public String get(@NonNull final String key, @NonNull final Object... params) {
		return bundle.getMessage(key, params, fallbackMessages::get);
	}

	@Override
	public Collection<String> getKeys() {
		return bundle.getKeys(fallbackMessages::getKeys);
	}

}
