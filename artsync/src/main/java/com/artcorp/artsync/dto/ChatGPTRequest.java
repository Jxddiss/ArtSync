package com.artcorp.artsync.dto;

public class ChatGPTRequest {
    private String model;
    private String prompt;

    private double temperature = 0.7; //CONTROLE LA CREATIVITE DE LA REPONSE
    private int max_tokens = 125; //CONTROLE LA LONGUEUR DE LA REPONSE
    private double top_p = 0.8; //CONTROLE LA PROBABILITE D'APPARITION DES MOTS
    private double frequency_penalty = 0.5; //CONTROLE LA DIVERSITE DES MOTS
    private double presence_penalty = 0.5; //CONTROLE LA PROBABILITE D'APPARITION DES MOTS
    private int n = 1;

    public ChatGPTRequest() {
    }

    public ChatGPTRequest(String model, String prompt) {
        this.model = model;
        this.prompt = prompt;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getMax_tokens() {
        return max_tokens;
    }

    public void setMax_tokens(int max_tokens) {
        this.max_tokens = max_tokens;
    }

    public double getTop_p() {
        return top_p;
    }

    public void setTop_p(double top_p) {
        this.top_p = top_p;
    }

    public double getFrequency_penalty() {
        return frequency_penalty;
    }

    public void setFrequency_penalty(double frequency_penalty) {
        this.frequency_penalty = frequency_penalty;
    }

    public double getPresence_penalty() {
        return presence_penalty;
    }

    public void setPresence_penalty(double presence_penalty) {
        this.presence_penalty = presence_penalty;
    }

    public int getN() {
        return n;
    }
    public void setN(int n) {
        this.n = n;
    }

    @Override
    public String toString() {
        return "ChatGPTRequest{" +
                "model='" + model + '\'' +
                ", prompt='" + prompt + '\'' +
                ", temperature=" + temperature +
                ", maxTokens=" + max_tokens +
                ", topP=" + top_p +
                ", frequencyPenalty=" + frequency_penalty +
                ", presencePenalty=" + presence_penalty +
                '}';
    }
}
