package com.nls.myrewards;

public final class MyRewardsRegistrationAnswerAttribute {
    private String registrationQuestionId;
    private String answer;
    private MyRewardsRegistrationQuestionAttribute question;

    private MyRewardsRegistrationAnswerAttribute() {
    }

    public MyRewardsRegistrationAnswerAttribute(MyRewardsRegistrationAnswerAttribute copy) {
        this(copy.getRegistrationQuestionId(), copy.answer);
    }

    public MyRewardsRegistrationAnswerAttribute(int registrationQuestionId, String answer) {
        this.registrationQuestionId = String.valueOf(registrationQuestionId);
        this.answer = answer;
    }

    public int getRegistrationQuestionId() {
        return Integer.parseInt(registrationQuestionId);
    }

    public MyRewardsRegistrationQuestionAttribute getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
