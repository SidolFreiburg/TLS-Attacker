/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.core.protocol.preparator.extension;

import de.rub.nds.tlsattacker.core.protocol.message.extension.ExtendedMasterSecretExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.serializer.extension.ExtendedMasterSecretExtensionSerializer;
import de.rub.nds.tlsattacker.core.workflow.TlsContext;

/**
 *
 * @author Matthias Terlinde <matthias.terlinde@rub.de>
 */
public class ExtendedMasterSecretExtensionPreparator extends ExtensionPreparator<ExtendedMasterSecretExtensionMessage> {

    public ExtendedMasterSecretExtensionPreparator(TlsContext context, ExtendedMasterSecretExtensionMessage message,
            ExtendedMasterSecretExtensionSerializer serializer) {
        super(context, message, serializer);
    }

    /**
     * The extension has no data, so there is nothing to prepare.
     */
    @Override
    public void prepareExtensionContent() {
        LOGGER.debug("Prepared Extended Master Secret Extension.");
    }

}
