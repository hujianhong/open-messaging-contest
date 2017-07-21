package io.openmessaging.demo;

import io.openmessaging.BytesMessage;

public interface IReader {

	BytesMessage read() throws Exception;
}
