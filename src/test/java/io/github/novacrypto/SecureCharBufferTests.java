package io.github.novacrypto;

import org.junit.Test;

import static io.github.novacrypto.TestHelpers.*;
import static org.junit.Assert.assertEquals;

public final class SecureCharBufferTests {

    @Test
    public void initialLengthIsZero() {
        SecureCharBuffer buffer = new SecureCharBuffer();
        assertEquals(0, buffer.length());
    }

    @Test
    public void defaultCapacity() {
        SecureCharBuffer buffer = new SecureCharBuffer();
        assertEquals(512, buffer.capacity());
    }

    @Test
    public void canSpecifyCapacity() {
        SecureCharBuffer buffer = SecureCharBuffer.withCapacity(10);
        assertEquals(10, buffer.capacity());
    }

    @Test
    public void capacityIsNumberOfChars() {
        SecureCharBuffer buffer = SecureCharBuffer.withCapacity(1);
        buffer.append('a');
    }

    @Test
    public void appendIncreasesLength() {
        SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append('a');
        assertEquals(1, buffer.length());
    }

    @Test
    public void twoAppendsIncreaseLength() {
        SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append('a');
        buffer.append('b');
        assertEquals(2, buffer.length());
    }

    @Test
    public void afterAppendCanReadBufferContents() {
        SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append('a');
        assertEquals(1, buffer.length());
        assertEquals('a', buffer.get(0));
    }

    @Test
    public void afterAppendCanReadBufferContentsZ() {
        SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append('z');
        assertEquals(1, buffer.length());
        assertEquals('z', buffer.get(0));
    }

    @Test
    public void canReadManyChars() {
        SecureCharBuffer buffer = new SecureCharBuffer();
        appendString(buffer, "abc");
        assertEquals("abc", readWholeBufferAsString(buffer));
    }

    @Test
    public void dataIsZeroedOutAfterClose() {
        SecureCharBuffer buffer = new SecureCharBuffer();
        appendString(buffer, "abc");
        buffer.close();
        final String read = readWholeBufferAsString(buffer);
        assertEquals(512, buffer.length());
        assertEquals(512, read.length());
        for (int i = 0; i < read.length(); i++) {
            assertEquals('\0', read.charAt(i));
        }
    }

}
