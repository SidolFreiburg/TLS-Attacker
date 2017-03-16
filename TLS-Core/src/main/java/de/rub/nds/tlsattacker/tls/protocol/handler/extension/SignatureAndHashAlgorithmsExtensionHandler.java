/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2016 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.tls.protocol.handler.extension;

import de.rub.nds.tlsattacker.tls.constants.HandshakeByteLength;
import de.rub.nds.tlsattacker.tls.constants.NamedCurve;
import de.rub.nds.tlsattacker.tls.constants.SignatureAndHashAlgorithm;
import de.rub.nds.tlsattacker.tls.exceptions.AdjustmentException;
import de.rub.nds.tlsattacker.tls.protocol.message.extension.SignatureAndHashAlgorithmsExtensionMessage;
import de.rub.nds.tlsattacker.tls.protocol.parser.extension.SignatureAndHashAlgorithmsExtensionParser;
import de.rub.nds.tlsattacker.tls.protocol.preparator.extension.SignatureAndHashAlgorithmsExtensionPreparator;
import de.rub.nds.tlsattacker.tls.protocol.serializer.extension.SignatureAndHashAlgorithmsExtensionSerializer;
import de.rub.nds.tlsattacker.tls.workflow.TlsContext;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Juraj Somorovsky <juraj.somorovsky@rub.de>
 * @author Matthias Terlinde <matthias.terlinde@rub.de>
 */
public class SignatureAndHashAlgorithmsExtensionHandler extends
        ExtensionHandler<SignatureAndHashAlgorithmsExtensionMessage> {

    public SignatureAndHashAlgorithmsExtensionHandler(TlsContext context) {
        super(context);
    }

    @Override
    protected void adjustTLSContext(SignatureAndHashAlgorithmsExtensionMessage message) {
        List<SignatureAndHashAlgorithm> algoList = new LinkedList<>();
        byte[] signatureAndHashBytes = message.getSignatureAndHashAlgorithms().getValue();
        if(signatureAndHashBytes.length % HandshakeByteLength.SIGNATURE_HASH_ALGORITHM != 0)
        {
            throw new AdjustmentException("Cannot adjust ClientSupportedSignature and Hash algorithms to a resonable Value");
        }
        for(int i = 0; i < signatureAndHashBytes.length; i = i+HandshakeByteLength.SIGNATURE_HASH_ALGORITHM)
        {
            byte[] algoBytes = Arrays.copyOfRange(signatureAndHashBytes, i, i+HandshakeByteLength.SIGNATURE_HASH_ALGORITHM);
            SignatureAndHashAlgorithm algo = SignatureAndHashAlgorithm.getSignatureAndHashAlgorithm(algoBytes);
            algoList.add(algo);
        }
        context.setClientSupportedSignatureAndHashAlgorithms(algoList);
    }

    @Override
    public SignatureAndHashAlgorithmsExtensionParser getParser(byte[] message, int pointer) {
        return new SignatureAndHashAlgorithmsExtensionParser(pointer, message);
    }

    @Override
    public SignatureAndHashAlgorithmsExtensionPreparator getPreparator(
            SignatureAndHashAlgorithmsExtensionMessage message) {
        return new SignatureAndHashAlgorithmsExtensionPreparator(context, message);
    }

    @Override
    public SignatureAndHashAlgorithmsExtensionSerializer getSerializer(
            SignatureAndHashAlgorithmsExtensionMessage message) {
        return new SignatureAndHashAlgorithmsExtensionSerializer(message);
    }

}
