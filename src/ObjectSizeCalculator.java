import java.lang.reflect.*;

public class ObjectSizeCalculator {
    private static final long HEADER_SIZE = 12;
    private static final long REFERENCE_SIZE = 4;

    public long sizeOfObject(Object input) throws IllegalAccessException {
        long result = HEADER_SIZE + REFERENCE_SIZE;
        for (Field field : input.getClass().getDeclaredFields()) {
            if (field.getType().equals(String.class)) {
                field.setAccessible(true);
                String value = (String) field.get(input);
                result = result + sizeOfString(value);
            } else {
                result = result + sizeOfPrimitiveType(field.getType());
            }

        }
        return result;
    }

    private long sizeOfPrimitiveType(Class<?> primitiveType) {
        if (primitiveType.equals(int.class)) {
            return 4;
        } else if (primitiveType.equals(long.class)) {
            return 8;
        } else if (primitiveType.equals(float.class)) {
            return 4;
        } else if (primitiveType.equals(double.class)) {
            return 8;
        } else if (primitiveType.equals(byte.class)) {
            return 1;
        } else if (primitiveType.equals(short.class)) {
            return 2;
        }
        throw new IllegalArgumentException(String.format("Type: %s is not supported", primitiveType));
    }

    private long sizeOfString(String inputString) {
        int stringBytesSize = inputString.getBytes().length;
        return HEADER_SIZE + REFERENCE_SIZE + stringBytesSize;
    }
}