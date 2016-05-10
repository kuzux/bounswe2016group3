package com.bounswe2016group3.safa;

import org.apache.jena.query.QuerySolution;

/**
 * Created by sand94 on 10.05.2016.
 */
public class ChessMaster {
    private int ELO;
    private String name;
    private String title;

    public ChessMaster(QuerySolution sol){
        name = sol.get("?FullName").asLiteral().getString();
        title = sol.get("?Title").asLiteral().getString();
        ELO = 0;
        if(sol.get("?ELO") != null)
            ELO = sol.get("?ELO").asLiteral().getInt();
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public int getELO() {
        return ELO;
    }

    @Override
    public String toString() {
        String description = "";
        description += name + "\n";
        description += title + "\n";
        if(ELO != 0) description += Integer.toString(ELO) + "\n";
        return description;
    }
}
