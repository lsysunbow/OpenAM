/*
 * The contents of this file are subject to the terms of the Common Development and
 * Distribution License (the License). You may not use this file except in compliance with the
 * License.
 *
 * You can obtain a copy of the License at legal/CDDLv1.0.txt. See the License for the
 * specific language governing permission and limitations under the License.
 *
 * When distributing Covered Software, include this CDDL Header Notice in each file and include
 * the License file at legal/CDDLv1.0.txt. If applicable, add the following below the CDDL
 * Header, with the fields enclosed by brackets [] replaced by your own identifying
 * information: "Portions Copyrighted [year] [name of copyright owner]".
 *
 * Copyright 2015 ForgeRock AS.
 */

package org.forgerock.openam.sts.soap.token.validator;

import org.apache.cxf.sts.request.ReceivedToken;
import org.apache.cxf.sts.token.validator.TokenValidator;
import org.apache.cxf.sts.token.validator.TokenValidatorParameters;
import org.apache.cxf.sts.token.validator.TokenValidatorResponse;
import org.apache.cxf.ws.security.sts.provider.STSException;
import org.forgerock.json.resource.ResourceException;
import org.forgerock.openam.sts.TokenValidationException;
import org.forgerock.openam.sts.soap.bootstrap.SoapSTSAccessTokenProvider;
import org.forgerock.openam.sts.token.CTSTokenIdGenerator;
import org.forgerock.openam.sts.token.provider.TokenServiceConsumer;

/**
 * Base class for the TokenValidator instances plugged into the TokenValidateOperation. These TokenValidator implementations
 * will only consult the TokenService to determine if a token with the specified id has been generated by the sts. No
 * authN module will be consumed - hence the 'Simple' prefix. This functionality may be enhanced if/when a SAML2 authN
 * module is written.
 */
public abstract class SimpleTokenValidatorBase implements TokenValidator {
    private final TokenServiceConsumer tokenServiceConsumer;
    private final SoapSTSAccessTokenProvider soapSTSAccessTokenProvider;
    protected final CTSTokenIdGenerator ctsTokenIdGenerator;

    protected SimpleTokenValidatorBase(TokenServiceConsumer tokenServiceConsumer, SoapSTSAccessTokenProvider soapSTSAccessTokenProvider,
                                       CTSTokenIdGenerator ctsTokenIdGenerator) {
        this.tokenServiceConsumer = tokenServiceConsumer;
        this.soapSTSAccessTokenProvider = soapSTSAccessTokenProvider;
        this.ctsTokenIdGenerator = ctsTokenIdGenerator;
    }

    private String getTokenServiceConsumptionToken() throws TokenValidationException {
        try {
            return soapSTSAccessTokenProvider.getAccessToken();
        } catch (ResourceException e) {
            throw new TokenValidationException(e.getCode(), e.getMessage(), e);
        }
    }

    private void invalidateTokenGenerationServiceConsumptionToken(String consumptionToken) {
        soapSTSAccessTokenProvider.invalidateAccessToken(consumptionToken);
    }

    @Override
    public TokenValidatorResponse validateToken(TokenValidatorParameters tokenParameters) {
        TokenValidatorResponse response = new TokenValidatorResponse();
        ReceivedToken validateTarget = tokenParameters.getToken();
        response.setToken(validateTarget);
        String tokenServiceConsumptionToken = null;
        try {
            final String tokenId = generateIdFromValidateTarget(validateTarget);
            tokenServiceConsumptionToken = getTokenServiceConsumptionToken();
            final boolean isTokenValid = tokenServiceConsumer.validateToken(tokenId, tokenServiceConsumptionToken);
            validateTarget.setState(isTokenValid ? ReceivedToken.STATE.VALID : ReceivedToken.STATE.INVALID);
            return response;
        } catch (TokenValidationException e) {
            throw new STSException("Exception caught validating issued token: " + e.getMessage(), e);
        } finally {
            if (tokenServiceConsumptionToken != null) {
                invalidateTokenGenerationServiceConsumptionToken(tokenServiceConsumptionToken);
            }
        }
    }

    protected abstract String generateIdFromValidateTarget(ReceivedToken validateTarget) throws TokenValidationException;
}