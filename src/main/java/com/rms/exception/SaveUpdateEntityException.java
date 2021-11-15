package com.rms.exception;


public class SaveUpdateEntityException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 2046942307286998891L;

    public SaveUpdateEntityException() {
        super();
    }

    public SaveUpdateEntityException(String message) {
        super(message);
    }
}
