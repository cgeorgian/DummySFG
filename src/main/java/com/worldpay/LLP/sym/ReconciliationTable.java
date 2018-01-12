package com.worldpay.LLP.sym;

import java.util.Map;

/**
 * Created by georgianc on 12.01.2018.
 */
public class ReconciliationTable {

    private Map<String,LLPInstruction> table;

    public Map<String, LLPInstruction> getTable() {
        return table;
    }

    public ReconciliationTable(){

    }

    public ReconciliationTable(Map<String, LLPInstruction> table) {
        this.table = table;
    }
}
