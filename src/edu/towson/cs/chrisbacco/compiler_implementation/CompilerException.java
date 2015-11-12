package edu.towson.cs.chrisbacco.compiler_implementation;
/**
 * Parsing exception. For simplicity, this is used for both lexical errors as
 * well as parsing errors. A more complete design would include a hierarchy of
 * specific error types.
 *
 * Developed by Adam Conover (2012) and modified by Josh Dehlinger (2013), used with permission.
 *
 */
public class CompilerException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Instantiates a new CompilerException.
     *
     * @param errorMessage the error message to be printed
     */
    public CompilerException(String errorMessage) {
        super(errorMessage);
    }

    /**
     * Gets the error message.
     *
     * @return the error message
     */
    public String getErrorMessage() {
        return super.getMessage();
    }
}
