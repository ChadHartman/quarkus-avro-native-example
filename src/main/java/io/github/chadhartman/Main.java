package io.github.chadhartman;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.Map;

import javax.inject.Inject;

import org.jboss.logging.Logger;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class Main implements QuarkusApplication {

	@Inject
	JsonSerializer jsonSerializer;

	@Inject
	BinarySerializer binarySerializer;

	@Inject
	Logger logger;

	@Override
	public int run(String... args) throws Exception {
		serializeWith(binarySerializer);
		serializeWith(jsonSerializer);
		return 0;
	}

	private <F> void serializeWith(Serializer<User, F> serializer) throws IOException {

		var type = serializer.getClass().getSimpleName();
		var address = Address.newBuilder()
				.setStreet("Civic Ctr Plz")
				.setCity("San Diego")
				.setState(State.CA)
				.setZip("92101")
				.build();

		var event = UserEvent.newBuilder()
				.setTimestamp(Instant.now())
				.setProperties(Map.of("serializer", type))
				.build();

		var user = User.newBuilder()
				.setName("John")
				.setAddress(address)
				.setEvents(Collections.singletonList(event))
				.build();

		var data = serializer.serialize(user);
		var copy = serializer.deserialize(data);
		logger.infof(
				"Serializing using %s:\n"
						+ "\toriginal: %s\n"
						+ "\t    copy: %s",
				type,
				user.toString(),
				copy.toString()
		);
	}
}
