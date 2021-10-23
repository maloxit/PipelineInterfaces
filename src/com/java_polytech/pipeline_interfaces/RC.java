package com.java_polytech.pipeline_interfaces;

public class RC {

    public enum RCType {
        CODE_SUCCESS,

        CODE_MANAGER_CUSTOM_ERROR,
        CODE_READER_CUSTOM_ERROR,
        CODE_WRITER_CUSTOM_ERROR,
        CODE_EXECUTOR_CUSTOM_ERROR,

        CODE_MANAGER_INVALID_ARGUMENT,
        CODE_MANAGER_FAILED_PIPELINE_CONSTRUCTION,

        CODE_MANAGER_INVALID_INPUT_FILE,
        CODE_MANAGER_INVALID_OUTPUT_FILE,


        CODE_MANAGER_CONFIG_FILE_ERROR,
        CODE_MANAGER_CONFIG_GRAMMAR_ERROR,
        CODE_MANAGER_CONFIG_SEMANTIC_ERROR,

        CODE_READER_CONFIG_FILE_ERROR,
        CODE_READER_CONFIG_GRAMMAR_ERROR,
        CODE_READER_CONFIG_SEMANTIC_ERROR,
        CODE_READER_FAILED_TO_READ,

        CODE_WRITER_CONFIG_FILE_ERROR,
        CODE_WRITER_CONFIG_GRAMMAR_ERROR,
        CODE_WRITER_CONFIG_SEMANTIC_ERROR,
        CODE_WRITER_FAILED_TO_WRITE,

        CODE_EXECUTOR_CONFIG_FILE_ERROR,
        CODE_EXECUTOR_CONFIG_GRAMMAR_ERROR,
        CODE_EXECUTOR_CONFIG_SEMANTIC_ERROR,
    }

    public final RCType type;
    public final String info;

    public RC(RCType type, String info)
    {
        this.type = type;
        this.info = info;
    }

    public boolean isSuccess(RC rc){
        return rc.type == RCType.CODE_SUCCESS;
    }

    public static final RC RC_SUCCESS = new RC(RCType.CODE_SUCCESS, "No errors have occurred.");

    public static final RC RC_MANAGER_INVALID_ARGUMENT = new RC(RCType.CODE_MANAGER_INVALID_ARGUMENT, "Invalid argument for manager.");
    public static final RC RC_MANAGER_INVALID_READER_CLASS = new RC(RCType.CODE_MANAGER_FAILED_PIPELINE_CONSTRUCTION, "Couldn't find valid IReader class.");
    public static final RC RC_MANAGER_INVALID_WRITER_CLASS = new RC(RCType.CODE_MANAGER_FAILED_PIPELINE_CONSTRUCTION, "Couldn't find valid IWriter class.");
    public static final RC RC_MANAGER_INVALID_EXECUTOR_CLASS = new RC(RCType.CODE_MANAGER_FAILED_PIPELINE_CONSTRUCTION, "Couldn't find valid IExecutor class.");
    public static final RC RC_MANAGER_INVALID_INPUT_FILE = new RC(RCType.CODE_MANAGER_INVALID_INPUT_FILE, "Couldn't open input file.");
    public static final RC RC_MANAGER_INVALID_OUTPUT_FILE = new RC(RCType.CODE_MANAGER_INVALID_OUTPUT_FILE, "Couldn't open output file.");
    public static final RC RC_MANAGER_CONFIG_FILE_ERROR = new RC(RCType.CODE_MANAGER_CONFIG_FILE_ERROR, "Couldn't open manager config file.");
    public static final RC RC_MANAGER_CONFIG_GRAMMAR_ERROR = new RC(RCType.CODE_MANAGER_CONFIG_GRAMMAR_ERROR, "Some grammar error in manager config file.");
    public static final RC RC_MANAGER_CONFIG_SEMANTIC_ERROR = new RC(RCType.CODE_MANAGER_CONFIG_SEMANTIC_ERROR, "Some semantic error in manager config file.");

    public static final RC RC_READER_CONFIG_FILE_ERROR = new RC(RCType.CODE_READER_CONFIG_FILE_ERROR, "Couldn't open reader config file.");
    public static final RC RC_READER_CONFIG_GRAMMAR_ERROR = new RC(RCType.CODE_READER_CONFIG_GRAMMAR_ERROR, "Some grammar error in reader config file.");
    public static final RC RC_READER_CONFIG_SEMANTIC_ERROR = new RC(RCType.CODE_READER_CONFIG_SEMANTIC_ERROR, "Some semantic error in reader config file.");
    public static final RC RC_READER_FAILED_TO_READ = new RC(RCType.CODE_READER_FAILED_TO_READ, "Some input error has occurred while reading from input file.");

    public static final RC RC_WRITER_CONFIG_FILE_ERROR = new RC(RCType.CODE_WRITER_CONFIG_FILE_ERROR, "Couldn't open writer config file.");
    public static final RC RC_WRITER_CONFIG_GRAMMAR_ERROR = new RC(RCType.CODE_WRITER_CONFIG_GRAMMAR_ERROR, "Some grammar error in writer config file.");
    public static final RC RC_WRITER_CONFIG_SEMANTIC_ERROR = new RC(RCType.CODE_WRITER_CONFIG_SEMANTIC_ERROR, "Some semantic error in writer config file.");
    public static final RC RC_WRITER_FAILED_TO_WRITE = new RC(RCType.CODE_WRITER_FAILED_TO_WRITE, "Some output error has occurred while writing to output file.");

    public static final RC RC_EXECUTOR_CONFIG_FILE_ERROR = new RC(RCType.CODE_EXECUTOR_CONFIG_FILE_ERROR, "Couldn't open executor config file.");
    public static final RC RC_EXECUTOR_CONFIG_GRAMMAR_ERROR = new RC(RCType.CODE_EXECUTOR_CONFIG_GRAMMAR_ERROR, "Some grammar error in executor config file.");
    public static final RC RC_EXECUTOR_CONFIG_SEMANTIC_ERROR = new RC(RCType.CODE_EXECUTOR_CONFIG_SEMANTIC_ERROR, "Some semantic error in executor config file.");

}
