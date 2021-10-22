package com.java_polytech.pipeline_interfaces;

public interface IConsumer {
    // if ba is NULL, then it is end and prepare for destroy
    RC consume(byte[] buff);
}

