package com.francofral.artistapi.problem;

import org.springframework.http.HttpStatus;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * It represents the common schema of a problem in the system.
 *
 * @author Franco Arratia
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExceptionHandlerSchema {

    /**
     * Get the associated status code.
     *
     * @return the status code.
     */
    HttpStatus code();

    /**
     * Get the associated title of the problem.
     *
     * @return the title of the problem.
     */
    String title();
}
