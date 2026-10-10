package br.com.unisinos.academia.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

	@Autowired
	private MessageSource messageSource;

	public String getMessage(String key) {
		return getMessage(key, null);
	}

	public String getMessage(String key, Object[] objects) {
		return messageSource.getMessage(key, objects, Locale.getDefault());
	}

}
