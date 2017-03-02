/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2016 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.tls.protocol.parser;

import de.rub.nds.tlsattacker.tls.constants.HandshakeByteLength;
import de.rub.nds.tlsattacker.tls.constants.HandshakeMessageType;
import de.rub.nds.tlsattacker.tls.protocol.message.ClientHelloMessage;

/**
 *
 * @author Robert Merget - robert.merget@rub.de
 */
public class ClientHelloParser extends HelloParser<ClientHelloMessage> {

    public ClientHelloParser(int pointer, byte[] array) {
        super(pointer, array, HandshakeMessageType.CLIENT_HELLO);
    }

    @Override
    protected void parseHandshakeMessageContent(ClientHelloMessage msg) {
        parseProtocolVersion(msg);
        parseUnixtime(msg);
        parseRandom(msg);
        parseSessionIDLength(msg);
        parseSessionID(msg);
        msg.setCipherSuiteLength(parseIntField(HandshakeByteLength.CIPHER_SUITES_LENGTH));
        msg.setCipherSuites(parseByteArrayField(msg.getCipherSuiteLength().getValue()));
        msg.setCompressionLength(parseIntField(HandshakeByteLength.COMPRESSION_LENGTH));
        msg.setCompressions(parseByteArrayField(msg.getCompressionLength().getValue()));
        if (hasExtensionLengthField(msg)) {
            parseExtensionLength(msg);
            if (hasExtensions(msg)) {
                parseExtensionBytes(msg);
            }
        }
    }

    @Override
    protected ClientHelloMessage createHandshakeMessage() {
        return new ClientHelloMessage();
    }
}