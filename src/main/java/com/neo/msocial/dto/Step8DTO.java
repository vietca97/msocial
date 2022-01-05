package com.neo.msocial.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Step8DTO {

    private String SPAM_TEMPLATE_NAME;
    public static final String spamTemplateName = "SPAM_TEMPLATE_NAME";

    private String SPAM_TEMPLATE_VALUE;
    public static final String spamTemplateValue = "SPAM_TEMPLATE_VALUE";

    private String HOUR_IN_LOCK;
    public static final String hourInLock = "HOUR_IN_LOCK";

    private String SPAM_TYPE_NAME;
    public static final String spamTypeName = "SPAM_TYPE_NAME";

    private String ACTION_TYPE;
    public static final String actionType = "ACTION_TYPE";


    @Override
    public String toString() {
        return "Step8DTO{" +
                "SPAM_TEMPLATE_NAME='" + SPAM_TEMPLATE_NAME + '\'' +
                ", SPAM_TEMPLATE_VALUE='" + SPAM_TEMPLATE_VALUE + '\'' +
                ", HOUR_IN_LOCK='" + HOUR_IN_LOCK + '\'' +
                ", SPAM_TYPE_NAME='" + SPAM_TYPE_NAME + '\'' +
                ", ACTION_TYPE='" + ACTION_TYPE + '\'' +
                '}';
    }
}
