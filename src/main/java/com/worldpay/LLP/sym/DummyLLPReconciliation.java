package com.worldpay.LLP.sym;

import java.util.List;

/**
 * Created by georgianc on 11.01.2018.
 */
public class DummyLLPReconciliation {

    private List<LLPInstruction> instructions;

    public List<LLPInstruction> getInstructions() {
        return instructions;
    }

    public DummyLLPReconciliation(List<LLPInstruction> instructions) {
        this.instructions = instructions;
    }
}
