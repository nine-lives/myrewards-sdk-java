package com.nls.myrewards;

public final class MyRewardsRegistrationAnswerAttribute {
    private int registrationQuestionId;
    private String answer;

    private MyRewardsRegistrationAnswerAttribute() {
    }

    public MyRewardsRegistrationAnswerAttribute(MyRewardsRegistrationAnswerAttribute copy) {
        this(copy.registrationQuestionId, copy.answer);
    }

    public MyRewardsRegistrationAnswerAttribute(int registrationQuestionId, String answer) {
        this.registrationQuestionId = registrationQuestionId;
        this.answer = answer;
    }

    public int getRegistrationQuestionId() {
        return registrationQuestionId;
    }

    public String getAnswer() {
        return answer;
    }
}
