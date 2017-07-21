package io.openmessaging.demo;

import java.io.IOException;


public interface IWriter {
	
	
	void write(DefaultBytesMessage message) throws IOException;
	
	void close() throws IOException;

}
