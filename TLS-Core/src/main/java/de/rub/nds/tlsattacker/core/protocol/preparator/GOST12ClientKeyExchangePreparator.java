/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.core.protocol.preparator;

import de.rub.nds.tlsattacker.core.constants.GOSTCurve;
import de.rub.nds.tlsattacker.core.crypto.ec.CustomECPoint;
import de.rub.nds.tlsattacker.core.protocol.message.GOSTClientKeyExchangeMessage;
import de.rub.nds.tlsattacker.core.util.GOSTUtils;
import de.rub.nds.tlsattacker.core.workflow.chooser.Chooser;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.rosstandart.RosstandartObjectIdentifiers;

public class GOST12ClientKeyExchangePreparator extends GOSTClientKeyExchangePreparator {

    public GOST12ClientKeyExchangePreparator(Chooser chooser, GOSTClientKeyExchangeMessage msg) {
        super(chooser, msg);
    }

    @Override
    protected GOSTCurve getServerCurve() {
        return chooser.getServerGost12Curve();
    }

    @Override
    protected String getKeyAgreementAlgorithm() {
        return "ECGOST3410-2012-256";
    }

    @Override
    protected String getKeyPairGeneratorAlgorithm() {
        return "ECGOST3410-2012";
    }

    @Override
    protected ASN1ObjectIdentifier getEncryptionParameters() {
        return RosstandartObjectIdentifiers.id_tc26_gost_28147_param_Z;
    }

    @Override
    protected boolean areParamSpecsEqual() {
        return chooser.getSelectedCipherSuite().usesGOSTR34112012()
                && getServerCurve().equals(chooser.getClientGost12Curve());
    }

    @Override
    protected PrivateKey generatePrivateKey(BigInteger s) {
        return GOSTUtils.generate12PrivateKey(getServerCurve(), s);
    }

    @Override
    protected PublicKey generatePublicKey(CustomECPoint point) {
        return GOSTUtils.generate12PublicKey(getServerCurve(), point);
    }

    @Override
    protected BigInteger getClientPrivateKey() {
        return chooser.getClientGost12PrivateKey();
    }

    @Override
    protected CustomECPoint getClientPublicKey() {
        return chooser.getClientGost12PublicKey();
    }

    @Override
    protected BigInteger getServerPrivateKey() {
        return chooser.getServerGost12PrivateKey();
    }

    @Override
    protected CustomECPoint getServerPublicKey() {
        return chooser.getServerGost12PublicKey();
    }

}