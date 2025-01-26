package org.example.beccareidtest;

public enum JobTitles {
    ARCHITECT("Architect"),
    SOFTWARE_ENGINEER("Software Engineer"),
    QUANTITY_SURVEYOR("Quantity Surveyor"),
    ACCOUNTANT("Accountant");

    private final String jobTitle;

    JobTitles(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobTitle() {
        return jobTitle;
    }
}
