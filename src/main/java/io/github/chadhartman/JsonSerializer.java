package io.github.chadhartman;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.jboss.logging.Logger;

/**
 * {@link User} to JSON Serializer.
 */
@ApplicationScoped
public class JsonSerializer implements Serializer<User, String> {

	@Inject
	Logger logger;

	private final DatumReader<User> reader = new SpecificDatumReader<>(User.class);
	private final DatumWriter<User> writer = new SpecificDatumWriter<>(User.class);

	@Override
	public String serialize(User user) throws IOException {
		try (var baos = new ByteArrayOutputStream()) {
			var encoder = EncoderFactory.get().jsonEncoder(User.getClassSchema(), baos, true);
			writer.write(user, encoder);
			encoder.flush();
			var json = baos.toString();
			if (logger.isDebugEnabled()) {
				logger.debug("Json: " + json);
			}
			return json;
		}
	}

	@Override
	public User deserialize(String data) throws IOException {
		var decoder = DecoderFactory.get().jsonDecoder(User.getClassSchema(), data);
		return reader.read(null, decoder);
	}
}
