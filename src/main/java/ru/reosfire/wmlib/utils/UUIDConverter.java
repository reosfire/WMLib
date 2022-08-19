package ru.reosfire.wmlib.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDConverter
{
    public static byte[] toBytes(UUID uuid)
    {
        byte[] converted = new byte[16];
        ByteBuffer.wrap(converted)
                .putLong(uuid.getMostSignificantBits())
                .putLong(uuid.getLeastSignificantBits());
        return converted;
    }

    public static UUID fromBytes(byte[] bytes)
    {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put(bytes);
        buffer.flip();
        return new UUID(buffer.getLong(), buffer.getLong());
    }
}