package id.ac.itb.openie.relation;

import id.ac.itb.openie.utils.Utilities;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by elvanowen on 2/23/17.
 */
public class Relations {
    private ArrayList<Relation> relations = new ArrayList<Relation>();

    public Relations() {}

    public Relations(Relation relation) {
        addRelation(relation);
    }

    public Relations(Relations relations) {
        addRelations(relations);
    }

    public Relations addRelation(Relation relation) {
        relations.add(relation);

        return this;
    }

    public void removeRelation(int index) {
        relations.remove(index);
    }

    public Relations addRelations(Relations relations) {
        this.relations.addAll(relations.getRelations());
        return this;
    }

    public Relations(File file) {
        String relationsString = Utilities.getFileContent(file);

        Pattern p = Pattern.compile("Source:\\s(.*)\\nKalimat ke-(.*?):\\s(.*)\\nRelasi:\\s(.*?)\\((.*),\\s(.*)\\)\\n");
        Matcher m = p.matcher(relationsString);

        while (m.find()) relations.add(new Relation(m.group(5), m.group(4), m.group(6), m.group(1), Integer.valueOf(m.group(2)), m.group(3)));
    }

    public String toString() {
        return StringUtils.join(relations, "\n");
    }

    public ArrayList<Relation> getRelations() {
        return relations;
    }

    public Relations intersect(Relations anotherRelations) {
        return new Relations();
    }
}