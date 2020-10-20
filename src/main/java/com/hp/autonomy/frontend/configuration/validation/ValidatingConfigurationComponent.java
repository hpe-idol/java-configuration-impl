/*
 * (c) Copyright 2013-2015 Micro Focus or one of its affiliates.
 *
 * Licensed under the MIT License (the "License"); you may not use this file
 * except in compliance with the License.
 *
 * The only warranties for products and services of Micro Focus and its affiliates
 * and licensors ("Micro Focus") are as may be set forth in the express warranty
 * statements accompanying such products and services. Nothing herein should be
 * construed as constituting an additional warranty. Micro Focus shall not be
 * liable for technical or editorial errors or omissions contained herein. The
 * information contained herein is subject to change without notice.
 */

package com.hp.autonomy.frontend.configuration.validation;

/**
 * A {@link OptionalConfigurationComponent} which can be validated without external dependencies
 */
public interface ValidatingConfigurationComponent<C extends ValidatingConfigurationComponent<C>> extends OptionalConfigurationComponent<C> {

    /**
     * @return A {@link ValidationResult} which is valid if the component is valid
     */
    ValidationResult<?> validate();

}
