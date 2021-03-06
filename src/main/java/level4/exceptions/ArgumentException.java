package level4.exceptions;

import level4.constants.Languages;

import java.util.Arrays;

/**
 * Custom exception that handles argument isssues, extends RunTimeException so no propagation is forced by Java
 */
public class ArgumentException extends RuntimeException {
    /**
     * Constructor that will exit the system as soon as the error is printed
     *
     * @param type the type of the exception to handle
     */
    public ArgumentException(String type) {
        switch (type) {
            case "invalid-language":
                System.err.println("Error : You provided an invalid language, languages supported are : " + Arrays.toString(Languages.values()));
                break;
            case "missing-arg":
                System.err.println("Error : Option code must be used with one of the following argument : " + Arrays.toString(Languages.values()));
                break;
            case "wrong-function-arg":
                System.err.println("Error : wrong function argument provided");
        }
        System.exit(5);
    }
}
