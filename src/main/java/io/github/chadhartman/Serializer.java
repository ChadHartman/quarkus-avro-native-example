package io.github.chadhartman;

import java.io.IOException;

public interface Serializer<T, F> {

	/**
	 * Serialize instance T to intermediary format F.
	 *
	 * @param instance the instance to serialize
	 * @return formatted instance
	 */
	F serialize(T instance) throws IOException;

	/**
	 * Deserialize instance T from intermediary format F.
	 *
	 * @param data the data to deserialize
	 * @return instance
	 */
	T deserialize(F data) throws IOException;
}
