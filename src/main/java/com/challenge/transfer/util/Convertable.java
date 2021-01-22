package com.challenge.transfer.util;

public interface Convertable<F, T> {

    /**
     * Convert one object to another.
     * @param from Source object.
     * @return Target object
     * @throws ConversionException In case of exception occured during
     * conversion
     */
    default T convertFrom(F from) throws ConversionException {
        throw new UnsupportedOperationException(
                "Conversion from not implemented in " + getClass().getName()
        );
    };

    /**
     * Convert one object to another.
     * @param to Source object
     * @return Target object
     * @throws ConversionException In case of exception occured during
     * conversion
     */
    default F convertTo(T to) throws ConversionException {
        throw new UnsupportedOperationException(
                "Conversion to not implemented in " + getClass().getName()
        );
    };

}
