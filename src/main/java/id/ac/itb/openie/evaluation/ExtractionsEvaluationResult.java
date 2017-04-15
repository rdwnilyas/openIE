package id.ac.itb.openie.evaluation;

import id.ac.itb.openie.relation.Relations;

import java.util.HashMap;

/**
 * Created by elvanowen on 4/15/17.
 */
public class ExtractionsEvaluationResult {
    private HashMap<String, Relations> correctRelationsByFilename = new HashMap<>();
    private Relations correctRelations = new Relations();
    private ExtractionsEvaluationModel extractionsEvaluationModel;
    private String recallFormula = "#Correct / #Extractions";
    private String precisionFormula = "#Correct / #Relations";
    private String fMeasureFormula = "2PR / (P + R)";

    public double getRecall() {
        return getnCorrect() / getnExtractions();
    }

    public double getPrecision() {
        return getnCorrect() / getnRelations();
    }

    public double getfMeasure() {
        return 2 * getPrecision() * getRecall() / ( getPrecision() + getRecall() );
    }

    public int getnCorrect() {
        return correctRelations.getRelations().size();
    }

    public int getnExtractions() {
        return extractionsEvaluationModel.getExtractedRelations().getRelations().size();
    }

    public int getnRelations() {
        return extractionsEvaluationModel.getLabelledRelations().getRelations().size();
    }

    public String getRecallFormula() {
        return recallFormula;
    }

    public String getPrecisionFormula() {
        return precisionFormula;
    }

    public String getfMeasureFormula() {
        return fMeasureFormula;
    }

    public String getRecallFormulaValue() {
        return String.format("%1$d / %2$d", getnCorrect(), getnExtractions());
    }

    public String getPrecisionFormulaValue() {
        return String.format("%1$d / %2$d", getnCorrect(), getnRelations());
    }

    public String getfMeasureFormulaValue() {
        return String.format("2 * %1$f * %2$f / (%1$f + %2$f)", getPrecision(), getRecall());
    }

    public HashMap<String, Relations> getCorrectRelationsByFilename() {
        return correctRelationsByFilename;
    }

    public void setCorrectRelationsByFilename(HashMap<String, Relations> correctRelationsByFilename) {
        this.correctRelationsByFilename = correctRelationsByFilename;
    }

    public Relations getCorrectRelations() {
        return correctRelations;
    }

    public void setCorrectRelations(Relations correctRelations) {
        this.correctRelations = correctRelations;
    }

    public ExtractionsEvaluationModel getExtractionsEvaluationModel() {
        return extractionsEvaluationModel;
    }

    public void setExtractionsEvaluationModel(ExtractionsEvaluationModel extractionsEvaluationModel) {
        this.extractionsEvaluationModel = extractionsEvaluationModel;
    }
}