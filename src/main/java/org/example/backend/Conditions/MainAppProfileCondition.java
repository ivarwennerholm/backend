package org.example.backend.Conditions;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

    /*
    The purpose of this class
    is to mark the main app in BackendApplication
    so that the @Bean part of the main class is
    only executed when running the main app, and
    not when running other console apps.
    */

public class MainAppProfileCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return context.getEnvironment().acceptsProfiles("mainapp");
    }
}