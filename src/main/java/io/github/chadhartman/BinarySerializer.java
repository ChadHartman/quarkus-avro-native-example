package io.github.chadhartman;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

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
 * {@link User} to binary serializer.
 */
@ApplicationScoped
public class BinarySerializer implements Serializer<User, byte[]> {

	@Inject
	Logger logger;

	private final DatumWriter<User> writer = new SpecificDatumWriter<>(User.class);
	private final DatumReader<User> reader = new SpecificDatumReader<>(User.class);

	@Override
	public byte[] serialize(User user) throws IOException {
		try (var baos = new ByteArrayOutputStream()) {
			var encoder = EncoderFactory.get().binaryEncoder(baos, null);
			writer.write(user, encoder);
			encoder.flush();
			var bytes = baos.toByteArray();
			if (logger.isDebugEnabled()) {
				logger.debug("Bytes: " + Base64.getEncoder().encodeToString(bytes));
			}
			return bytes;
		}
	}

	@Override
	public User deserialize(byte[] data) throws IOException {
		var decoder = DecoderFactory.get().binaryDecoder(data, null);
		return reader.read(null, decoder);
	}
}
