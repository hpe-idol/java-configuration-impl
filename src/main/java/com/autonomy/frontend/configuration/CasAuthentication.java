package com.autonomy.frontend.configuration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/*
 * $Id:$
 *
 * Copyright (c) 2014, Autonomy Systems Ltd.
 *
 * Last modified by $Author:$ on $Date:$
 */
@Data
@JsonDeserialize(builder = CasAuthentication.Builder.class)
public class CasAuthentication implements Authentication<CasAuthentication>, ValidatingConfigurationComponent {

    private final DefaultLogin defaultLogin;
    private final String method;
    private final CasConfig cas;

    private CasAuthentication(final Builder builder) {
        this.cas = builder.cas;
        this.defaultLogin = builder.defaultLogin;
        this.method = builder.method;
    }

    @Override
    public CasAuthentication merge(final CasAuthentication other) {
        if(other != null) {
            final Builder builder = new Builder(this);

            builder.setDefaultLogin(this.defaultLogin == null ? other.defaultLogin : this.defaultLogin.merge(other.defaultLogin));
            builder.setCas(this.cas == null ? other.cas : this.cas.merge(other.cas));
            builder.setMethod(this.method == null ? other.method : this.method);

            return builder.build();
        }
        else {
            return this;
        }
    }

    @Override
    public void basicValidate() throws ConfigException {
        if(LoginTypes.CAS.equalsIgnoreCase(method)) {
            cas.basicValidate();
        }
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public UsernameAndPassword getDefaultLogin() {
        return defaultLogin.getDefaultLogin();
    }

    @Override
    public ValidationResult<?> validate() {
        try {
            cas.basicValidate();

            return new ValidationResult<Void>(true);
        }
        catch(ConfigException e) {
            return new ValidationResult<>(false, "");
        }
    }

    @Override
    public String getClassName() {
        return getClass().getCanonicalName();
    }

    @Override
    public CasAuthentication generateDefaultLogin() {
        final Builder builder = new Builder(this);

        builder.defaultLogin = builder.defaultLogin.generateDefaultLogin();

        return builder.build();
    }

    @Override
    public CasAuthentication withoutDefaultLogin() {
        final Builder builder = new Builder(this);

        builder.defaultLogin = null;

        return builder.build();
    }

    @Override
    public CasAuthentication withHashedPasswords() {
        return this;
    }

    @Override
    public CasAuthentication withoutPasswords() {
        return this;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    @NoArgsConstructor
    @JsonPOJOBuilder(withPrefix = "set")
    @Accessors(chain = true)
    @Setter
    public static class Builder {

        private CasConfig cas;
        private DefaultLogin defaultLogin;
        private String method;

        public Builder(final CasAuthentication casAuthentication) {
            this.cas = casAuthentication.cas;
            this.defaultLogin = casAuthentication.defaultLogin;
            this.method = casAuthentication.method;
        }

        public CasAuthentication build() {
            return new CasAuthentication(this);
        }
    }

}