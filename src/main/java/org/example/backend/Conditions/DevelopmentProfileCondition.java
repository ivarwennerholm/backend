package org.example.backend.Conditions;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

    /*
    The purpose of this class
    is check if the active profile i
    "development". This condition is then
    used in the main app to make sure the @Bean
    part is only executed when running w/ a
    development profile.
    */

public class DevelopmentProfileCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return context.getEnvironment().acceptsProfiles("development");
    }
}