package com.encentral.ems.user.impl;

import com.encentral.ems.user.api.IUser;
import com.google.inject.AbstractModule;

public class UserModule  extends AbstractModule {

    @Override
    protected void configure(){
        bind(IUser.class).to(DefaultUserImpl.class);
    }
}
