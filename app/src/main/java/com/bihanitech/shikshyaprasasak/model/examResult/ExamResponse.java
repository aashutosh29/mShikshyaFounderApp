package com.bihanitech.shikshyaprasasak.model.examResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExamResponse {
    @SerializedName("routine")
    @Expose
    private Routine routine;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;

    public Routine getRoutine() {
        return routine;
    }

    public void setRoutine(Routine routine) {
        this.routine = routine;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }
}
