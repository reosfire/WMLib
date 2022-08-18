package ru.reosfire.WMLib.Utils;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDConverter
{
    public static byte[] ToBytes(UUID uuid)
    {
        byte[] converted = new byte[16];
        ByteBuffer.wrap(converted)
                .putLong(uuid.getMostSignificantBits())
                .putLong(uuid.getLeastSignificantBits());
        return converted;
    }

    public static UUID FromBytes(byte[] bytes)
    {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put(bytes);
        buffer.flip();
        return new UUID(buffer.getLong(), buffer.getLong());
    }
}