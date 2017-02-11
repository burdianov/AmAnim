package com.crackncrunch.amanim.data.network.error;

public class NetworkAvailableError extends Throwable {
    public NetworkAvailableError() {
        super("The Internet is not accessable. Try again later");
    }
}
