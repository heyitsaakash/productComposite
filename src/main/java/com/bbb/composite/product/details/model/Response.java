package com.bbb.composite.product.details.model;

import com.fasterxml.jackson.annotation.JsonProperty;
public class Response
{
    private String start;

    @JsonProperty("docs")
    private Docs[] docs;

    private String numFound;

    public String getStart ()
    {
        return start;
    }

    public void setStart (String start)
    {
        this.start = start;
    }

    public Docs[] getDocs ()
    {
        return docs;
    }

    public void setDocs (Docs[] docs)
    {
        this.docs = docs;
    }

    public String getNumFound ()
    {
        return numFound;
    }

    public void setNumFound (String numFound)
    {
        this.numFound = numFound;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [start = "+start+", docs = "+docs+", numFound = "+numFound+"]";
    }
}