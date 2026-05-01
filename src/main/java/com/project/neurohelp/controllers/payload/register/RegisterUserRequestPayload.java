package com.project.neurohelp.controllers.payload.register;

import com.project.neurohelp.platform.constants.Roles;
import com.project.neurohelp.platform.constants.Status;
import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
public record RegisterUserRequestPayload(
       String name,
       String email,
       String address,
       String citizenShip,
       Roles roles,
       Status status
) { }